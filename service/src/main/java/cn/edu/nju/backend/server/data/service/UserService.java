package cn.edu.nju.backend.server.data.service;

import cn.edu.nju.backend.server.data.dto.LoginDto;
import cn.edu.nju.backend.server.data.dto.UserDto;
import cn.edu.nju.backend.server.data.model.User;

import java.util.List;

/**
 * Created by zhongyq on 17/3/8.
 */
public interface UserService {

    User login(LoginDto loginDto);

    void register(UserDto userDto);

    List<User> findAllUser();

    User findUserById(Long id);

    void verifyDriver(Long id, int status);

    User getUserNameAndPhone(Long id);
}
