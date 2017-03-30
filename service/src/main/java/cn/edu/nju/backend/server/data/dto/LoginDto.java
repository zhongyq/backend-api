package cn.edu.nju.backend.server.data.dto;

/**
 * Created by zhongyq on 17/3/8.
 */
public class LoginDto {
    private String phone;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
