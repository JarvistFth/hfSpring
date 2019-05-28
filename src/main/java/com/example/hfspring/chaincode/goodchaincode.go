package main

import (
	"encoding/json"
	"errors"
	"strconv"

	//"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

type Good struct {
	Id string `json:"id"`
	Name string `json:"name"`
	Owner OwnerRelation `json:"owner"`
}


type OwnerRelation struct {
	Id string `json:"id"`
	Username string `json:"username"`
	Organization string `json:"Organization"`

}

type Owner struct {
	Id string `json:"id"`
	Username string `json:"username"`
	Enable bool `json:"enabled"`
}

type SimpleChaincode struct {

}


func main(){
	err:=shim.Start(new(SimpleChaincode))
	if err != nil{
		fmt.Printf("error starting simple chaincode")
	}


}

func(t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response{
	fmt.Println("Chaincode Is Starting Up")
	owners := []Owner{
		{Id:"2015212154",Username:"jjw",Enable:false},
	}
	relation := []OwnerRelation{
		{Id:"2015212154",Username:"jjw",Organization:"bupt"},
	}
	goods := []Good{
		{Id:"201901240945",Name:"test1",Owner:relation[0]},
	}
	ownerAsBytes,_ := json.Marshal(owners[0])
	stub.PutState(owners[0].Id,ownerAsBytes)
	fmt.Println("Added",owners[0])

	relationAsbytes,_ := json.Marshal(relation[0])
	stub.PutState(relation[0].Id,relationAsbytes)
	fmt.Println("Added",relation[0])

	goodsAsBytes,_ := json.Marshal(goods[0])
	stub.PutState(goods[0].Id,goodsAsBytes)
	fmt.Println("Added",goods[0])

	//self-test pass
	return shim.Success(goodsAsBytes)
}




func(t* SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response{
	function,args := stub.GetFunctionAndParameters()
	fmt.Println("start invoke")

	if function == "init"{
		return t.Init(stub)
	} else if function == "getHistory"{
		return t.getHistory(stub,args)
	} else if function == "initUser" {
		return t.initUser(stub, args)
	} else if function == "initGood"{
		return t.initGood(stub,args)
	} else if function == "queryUsers"{
		return t.queryUsers(stub,args)
	}

	fmt.Println("receive unknown invoke function name : " + function)
	return shim.Error("receive unknown invoke function name : " + function)

}

func (t *SimpleChaincode) Query(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Error("Unknown supported call - Query()")
}

func (t *SimpleChaincode) queryUsers(stub shim.ChaincodeStubInterface,args []string) pb.Response{
	fmt.Println("query useres from id")
	if len(args) != 1{
		return shim.Error("incorrect args number")
	}
	userAsBytes,_ := stub.GetState(args[0])
	return shim.Success(userAsBytes)
}



func (t *SimpleChaincode) initUser(stub shim.ChaincodeStubInterface,args []string) pb.Response{
	var err error
	fmt.Println("start init user")
	if len(args) != 2{
		return shim.Error(err.Error())
	}

	err = sanitize_arguments(args)
	if err != nil{
		return shim.Error(err.Error())
	}
	var owner Owner
	owner.Id = args[0]
	owner.Username = args[1]
	owner.Enable = true
	fmt.Println(owner)
	_,err = t.getOwner(stub,owner.Id)
	if err != nil{
		fmt.Println("user already exist")
		return shim.Error(err.Error())
	}

	ownerAsBytes,_ := json.Marshal(owner)
	err = stub.PutState(owner.Id,ownerAsBytes)
	if err != nil{
		fmt.Println("could not save user")
		return shim.Error(err.Error())
	}

	fmt.Println("succeed saving user")
	return shim.Success(nil)
}
func (t *SimpleChaincode) initGood(stub shim.ChaincodeStubInterface,args[] string) pb.Response{
	var err error
	fmt.Printf("goods starting up")
	if len(args) != 3{
		fmt.Println("error args number needs 5")
	}
	err = sanitize_arguments(args)
	if err != nil{
		return shim.Error(err.Error())
	}
	goodsid := args[0]
	goodsname := args[1]
	goodsownerid := args[2]
	owner,err := t.getOwner(stub,goodsownerid)
	if err != nil {
		fmt.Println("Failed to find owner - " + goodsownerid)
		return shim.Error(err.Error())
	}

	good, err := t.getGoods(stub, goodsid)
	if err == nil {
		fmt.Println("This good already exists - " + goodsid)
		fmt.Println(good)
		return shim.Error("This good already exists - " + goodsid)  //all stop a marble by this id exists
	}
	str1 := `{
		"id": "` + goodsid + `", 
		"name": "` + goodsname + `", 
		"owner": {
			"id": "` + goodsownerid+ `", 
			"username": "` + owner.Username + `",
		}
	}`
	err = stub.PutState(goodsid,[]byte(str1))
	if err != nil{
		return shim.Error(err.Error())
	}



	fmt.Println("end init goods")
	return shim.Success(nil)
}

func (t *SimpleChaincode) getHistory(stub shim.ChaincodeStubInterface,args []string) pb.Response{
	type AuditHistory struct {
		TxId string `json:"txId"`
		Value Good `json:"value"`
	}

	var history []AuditHistory
	var good Good
	if len(args) != 1{
		return shim.Error("incorrect args number")
	}

	goodId := args[0]
	fmt.Printf("start getHistoryFrom: %s\n",goodId)

	resultIterator,err := stub.GetHistoryForKey(goodId)
	if err!=nil{
		return shim.Error(err.Error())
	}

	defer resultIterator.Close()

	for resultIterator.HasNext(){
		historyData,err := resultIterator.Next()
		if err!=nil{
			return shim.Error(err.Error())
		}
		var tx AuditHistory
		tx.TxId  = historyData.TxId
		json.Unmarshal(historyData.Value, &good)
		if historyData.Value == nil{
			var emptygood Good
			tx.Value = emptygood
		}else{
			json.Unmarshal(historyData.Value, &good)
			tx.Value = good
		}
		history = append(history,tx)
	}

	fmt.Printf("get history returning with: \n %s",history)
	historyAsbytes , _ := json.Marshal(history)
	return shim.Success(historyAsbytes)

}


func sanitize_arguments(strs []string) error{
	for i, val:= range strs {
		if len(val) <= 0 {
			return errors.New("Argument " + strconv.Itoa(i) + " must be a non-empty string")
		}
		if len(val) > 32 {
			return errors.New("Argument " + strconv.Itoa(i) + " must be <= 32 characters")
		}
	}
	return nil

}

func (t *SimpleChaincode) getOwner(stub shim.ChaincodeStubInterface, id string) (Owner, error) {
	var owner Owner
	ownerAsBytes, err := stub.GetState(id)                     //getState retreives a key/value from the ledger
	if err != nil {                                            //this seems to always succeed, even if key didn't exist
		return owner, errors.New("Failed to get owner - " + id)
	}
	json.Unmarshal(ownerAsBytes, &owner)                       //un stringify it aka JSON.parse()

	if len(owner.Username) == 0 {                              //test if owner is actually here or just nil
		return owner, errors.New("Owner does not exist - " + id + ", '" + owner.Username)
	}

	return owner, nil
}

func (t *SimpleChaincode) getGoods(stub shim.ChaincodeStubInterface, id string) (Good, error) {
	var good Good
	marbleAsBytes, err := stub.GetState(id)                  //getState retreives a key/value from the ledger
	if err != nil {                                          //this seems to always succeed, even if key didn't exist
		return good, errors.New("Failed to find good - " + id)
	}
	json.Unmarshal(marbleAsBytes, &good)                   //un stringify it aka JSON.parse()

	if good.Id != id { //test if marble is actually here or just nil
		return good, errors.New("good does not exist - " + id)
	}

	return good, nil
}

