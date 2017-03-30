package cn.edu.nju.backend.server.web.init;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhongyq on 17/3/10.
 */
public class SessionMap {

    public static Map<String, HttpSession> userMap = new HashMap<>();

    public static void add(HttpSession session) {
        userMap.put(session.getId(), session);
    }

    public static void remove(HttpSession session) {
        userMap.remove(session.getId());
    }

    public static HttpSession get(String sessionId) {
        HttpSession session = userMap.get(sessionId);
        return session;
    }
}
