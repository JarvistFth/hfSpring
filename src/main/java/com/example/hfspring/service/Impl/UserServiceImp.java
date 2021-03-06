package com.example.hfspring.service.Impl;

import com.example.hfspring.Dao.UsersMapper;
import com.example.hfspring.Model.ResponseCode;
import com.example.hfspring.Model.Users;
import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.demo.FabricStore;
import com.example.hfspring.demo.FabricUser;
import com.example.hfspring.demo.HFConfig;
import com.example.hfspring.fabric.FabricManager;
import com.example.hfspring.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Transactional
@Service
public class UserServiceImp implements UserService {

    private static Log logger = LogFactory.getLog(UserServiceImp.class);

    @Autowired
    private UsersMapper usersMapper;

    private HFConfig config = HFConfig.getConfig();

    private FabricManager manager = new FabricManager();


    @Override
    public ResponseCode userRregister(Users users){
        String userName = users.getName();
        Users SQLusers = new Users();
        try{
            SQLusers = usersMapper.selectByName(userName);

        }catch (Exception e){
            e.printStackTrace();
        }
        if(SQLusers == null){
            try{
                File tmpStoreFile = new File("src/main/resources/" + userName  +".properties");
                if(!tmpStoreFile.exists()){
                    tmpStoreFile.createNewFile();
                }
                FabricStore fabricStore = new FabricStore(tmpStoreFile);
                manager.setFabricStore(fabricStore);
                FabricUser user = manager.getFabricStore().getMember(userName,config.clientOrg.getName());
                if(!user.isRegistered()){
                    logger.info("registering...");
                    boolean ok =  manager.registerOnHF(user,userName,"org1.department1",users.getPassword());
                    logger.info(ok);
                }

                if(!user.isEnrolled()){
                    logger.info("enrolling on...");
                    manager.enrollOnHF(user,userName,users.getPassword());
                }
                users.setCreatetime(new Date());
                users.setUpdatetime(new Date());
                users.setBalance(0);
                usersMapper.insert(users);
            }catch (Exception e){
                return new ResponseCode(ConstantUtils.REQUEST_ERROR,e.getMessage());
            }

            return new ResponseCode(ConstantUtils.REQUEST_OK,"注册成功!");
        }else{
            return new ResponseCode(ConstantUtils.REQUEST_ERROR,"用户名已注册");
        }

    }

    @Override
    public boolean loginVerified(Users users) {
        String password = users.getPassword();
        String varifiedPwd = usersMapper.getUserPassword(users.getName());
        logger.info(password + "varified pwd :  " + varifiedPwd);
        if(password.equals(varifiedPwd)){
            return true;
        }
        return false;
    }

    @Override
    public Users getUsers(Long id) {
        return usersMapper.selectByPrimaryKey(new Integer(id.toString()));
    }

    @Override
    public Users getUsers(String username) {
        return usersMapper.selectByName(username);
    }

    @Override
    public int updateUsers(Users users){
        return usersMapper.updatePersonalInfo(users);
    }

    @Override
    public int updatePassword(Users users) {
        return usersMapper.updatePassword(users);
    }

    @Override
    public int updateAvatar(Users users) {
        return usersMapper.updateAvatar(users);
    }

    @Override
    public String getAvatar(String name) {
        return usersMapper.getUserAvatar(name);
    }
}
