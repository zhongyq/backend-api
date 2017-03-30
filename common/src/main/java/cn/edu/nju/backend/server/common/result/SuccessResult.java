package cn.edu.nju.backend.server.common.result;

import cn.edu.nju.backend.server.common.contants.ResponseMessage;
import cn.edu.nju.backend.server.common.contants.StatusCode;

import java.util.HashMap;

/**
 * Created by zhongyq on 17/3/8.
 */
public class SuccessResult extends HashMap<String, Object> {
    public SuccessResult() {
        put(ResponseMessage.Error_Code, StatusCode.SUCCESS);
    }
}