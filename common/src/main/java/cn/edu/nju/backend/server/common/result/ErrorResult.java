package cn.edu.nju.backend.server.common.result;

import cn.edu.nju.backend.server.common.contants.ResponseMessage;
import cn.edu.nju.backend.server.common.contants.StatusCode;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhongyq on 17/3/8.
 */
public class ErrorResult extends HashMap<String, Object> {

    private static Map<Integer, String> errorCodeMap = new HashMap<>();

    static {
        Field[] fields = StatusCode.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                errorCodeMap.put(field.getInt(StatusCode.class), field.getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


    }

    public ErrorResult(int errorCode) {
        put(ResponseMessage.Error_Code, errorCode);

        String msg = errorCodeMap.get(errorCode);
        if (msg == null) {
            msg = "Unknown error";
        }

        put(ResponseMessage.Error_MSG, msg);
    }

}
