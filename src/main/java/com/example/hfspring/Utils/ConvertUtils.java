package com.example.hfspring.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class ConvertUtils {

    // return 0 for string, 1 for jsonobject, 2 for jsonArray
    public static int isJSONValid(String str) {
        try {
            JSONObject.parseObject(str);
            return ConstantUtils.JSONObjectRet;
        } catch (Exception e) {
            try {
                JSONObject.parseArray(str);
                return ConstantUtils.JSONArrayRet;
            } catch (Exception e1) {
                return ConstantUtils.StringRet;
            }
        }
    }


    public static JSONObject parseResult(String result) {
        JSONObject jsonObject = new JSONObject();
        int jsonValidRet = isJSONValid(result);
        switch (jsonValidRet) {
            case ConstantUtils.StringRet:
                jsonObject.put("data", result);
                break;
            case ConstantUtils.JSONArrayRet:
                jsonObject.put("data", JSONObject.parseArray(result));
                break;
            case ConstantUtils.JSONObjectRet:
                jsonObject.put("data", JSONObject.parseObject(result));
                break;
            default:
                break;

        }
        return jsonObject;


    }

    public static void List(String as){

    }

}
