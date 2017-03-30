package cn.edu.nju.backend.server.data.mapper;

import cn.edu.nju.backend.server.data.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhongyq on 17/3/3.
 */

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{name}")
    @Results(value = {@Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "sex", column = "sex")
    })
    public List<User> findUserByName(@Param("name") String name);

    @Insert("insert into user(username, phone, sex, password, role, credit_card, identity, pending_status) values" +
            "(#{user.username},#{user.phone},#{user.sex},#{user.password},#{user.role},#{user.creditCard},#{user.identity},#{user.pendingStatus})")
    public void insertUser(@Param("user") User user);


    @Select("select * from user where phone=#{phone} and password=#{password}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "role", column = "role"),
            @Result(property = "pendingStatus", column = "pending_status"),
            @Result(property = "creditCard", column = "credit_card"),
            @Result(property = "identity", column = "identity")
    })
    public User login(@Param("phone") String phone, @Param("password") String password);

    @Select("select * from user")
    public List<User> findAllUser();

    @Select("select * from user where id=#{id}")
    public User findUserById(@Param("id") Long id);

    @Update("update user set pending_status=#{status} where id=#{id}")
    public void verifyDriver(@Param("id") Long id, @Param("status") int status);

    @Select("select id, username, phone from user where id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "phone", column = "phone"),
    })
    public User getUserNameAndPhoneById(@Param("id") Long id);
}
