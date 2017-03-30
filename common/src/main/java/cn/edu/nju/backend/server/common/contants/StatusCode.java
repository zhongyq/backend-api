package cn.edu.nju.backend.server.common.contants;

/**
 * Created by zhongyq on 17/3/8.
 */
public interface StatusCode {
    int SUCCESS = 2000;   //成功操作返回
    int INNER_ERROR = 3000; // 内部错误
    int DATABASE_ERROR = 3001; // 数据库错误
    int PARAMETER_ERROR = 3002; //参数错误
    int ILLEGAL_ACCESS = 3003; //无权限访问
    int USER_AUTH_SESSION_FAILED = 3004; // Session认证失败，可能失效，或未携带
}
