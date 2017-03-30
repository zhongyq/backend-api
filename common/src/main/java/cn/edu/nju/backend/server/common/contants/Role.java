package cn.edu.nju.backend.server.common.contants;

/**
 * Created by zhongyq on 17/3/9.
 */
public enum Role {
    CUSTOMER("customer", RoleType.CUSTOMER_ROLE_ID),
    DRIVER("worker", RoleType.DRIVER_ROLE_ID),
    MANAGER("manager", RoleType.MANAGER_ROLE_ID);

    private String role;

    private int roleId;

    Role(String role, int roleId) {
        this.role = role;
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public int getRoleId() {
        return roleId;
    }

    public static Role fromInt(int type) throws IllegalArgumentException {
        if (type == RoleType.CUSTOMER_ROLE_ID) return Role.CUSTOMER;
        else if (type == RoleType.DRIVER_ROLE_ID) return Role.DRIVER;
        else if (type == RoleType.MANAGER_ROLE_ID) return Role.MANAGER;
        else throw new IllegalArgumentException("role type not defined");
    }

    public static Role parse(String role) throws IllegalArgumentException {
        if (role.equals(RoleType.CUSTOMER_ROLE_STR)) return Role.CUSTOMER;
        else if (role.equals(RoleType.DRIVER_ROLE_STR)) return Role.DRIVER;
        else if (role.equals(RoleType.MANAGER_ROLE_STR)) return Role.MANAGER;
        else throw new IllegalArgumentException("role type not defined");
    }

}
