package cn.edu.nju.backend.server.common.auth;

/**
 * Created by zhongyq on 17/3/1.
 */
public enum AuthLevel {

    NOAUTH(Integer.MIN_VALUE),
    SELF(1),
    LOGGED(2),
    ALL(Integer.MAX_VALUE);

    private int level;

    AuthLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
