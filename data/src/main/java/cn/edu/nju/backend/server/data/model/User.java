package cn.edu.nju.backend.server.data.model;

import javax.persistence.*;

/**
 * Created by zhongyq on 17/2/27.
 */
@Entity
@Table(name="user")
public class User {
    private Long id;
    private String username;
    private String phone;
    private String sex;

    private String password;
    private String role;        // customer or driver

    private String creditCard;  // 银行卡
    private String identity;    // 身份证
    private int pendingStatus;  // 审核状态

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="sex", nullable = false)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name="phone", unique = true, nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name="password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name="credit_card")
    public String getCreditCard() {
        return creditCard;
    }


    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Column(name="identity")
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Column(name="pending_status")
    public int getPendingStatus() {
        return pendingStatus;
    }

    public void setPendingStatus(int pendingStatus) {
        this.pendingStatus = pendingStatus;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", sex=" + sex + ", pendingStatus=" + pendingStatus + "]";
    }

}
