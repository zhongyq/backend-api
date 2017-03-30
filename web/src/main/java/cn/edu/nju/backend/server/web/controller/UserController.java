package cn.edu.nju.backend.server.web.controller;

import cn.edu.nju.backend.server.common.auth.Auth;
import cn.edu.nju.backend.server.common.auth.AuthLevel;
import cn.edu.nju.backend.server.common.contants.*;
import cn.edu.nju.backend.server.common.result.ErrorResult;
import cn.edu.nju.backend.server.common.result.SuccessResult;
import cn.edu.nju.backend.server.data.dto.LoginDto;
import cn.edu.nju.backend.server.data.dto.UserDto;
import cn.edu.nju.backend.server.data.model.User;
import cn.edu.nju.backend.server.data.service.UserService;
import cn.edu.nju.backend.server.common.util.Converter;
import cn.edu.nju.backend.server.web.init.SessionMap;
import cn.edu.nju.backend.server.web.vo.UserVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongyq on 17/2/27.
 */
@RestController
@EnableConfigurationProperties
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody LoginDto loginDto,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = userService.login(loginDto);
        if (user != null) {
            UserVo vo = Converter.convert(UserVo.class, user);
            Integer role = Role.parse(user.getRole()).getRoleId();
            if (role == 0) {
                // do nothing
            } else if (role == 1) {
                if (user.getPendingStatus() == 0) {
                    vo.setPendingStatus("审核中");
                } else if (user.getPendingStatus() == 1) {
                    vo.setPendingStatus("审核通过");
                } else {
                    vo.setPendingStatus("审核不通过");
                }
            } else {
                vo.setRole("manager");
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("role", role);
            session.setMaxInactiveInterval(24 * 60 * 60);
            vo.setSession(session.getId());

            SessionMap.add(session);

            SuccessResult successResult = new SuccessResult();
            successResult.put(ResponseMessage.ITEM_RESULT, vo);
            return successResult;
        }
        return new ErrorResult(StatusCode.PARAMETER_ERROR);
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public Map<String, Object> getDetailedVul(@RequestBody UserDto userDto) {
        // TODO 属性限制判断
        try {
            userService.register(userDto);
        }catch (Exception e) {
            return new ErrorResult(StatusCode.PARAMETER_ERROR);
        }
        return new SuccessResult();
    }

    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.CUSTOMER_ROLE_ID, RoleType.MANAGER_ROLE_ID, RoleType.DRIVER_ROLE_ID})
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public Map<String, Object> logout(HttpServletRequest request) {
        // TODO 属性限制判断
        HttpSession session = SessionMap.get(request.getHeader("session"));
        session.invalidate();
        return new SuccessResult();
    }


    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.MANAGER_ROLE_ID})
    @RequestMapping(path = "", method = RequestMethod.GET)
    public Map<String, Object> all() {
        List<User> users = userService.findAllUser();
        List<UserVo> vos = Lists.transform(users, user -> {
            UserVo vo = Converter.convert(UserVo.class, user);
            vo.setPendingStatus(user.getPendingStatus() + "");
            return vo;
        });
        SuccessResult successResult = new SuccessResult();
        successResult.put(ResponseMessage.ITEM_RESULT, vos);
        return successResult;
    }

    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.MANAGER_ROLE_ID})
    @RequestMapping(path = "/verify/{userId}", method = RequestMethod.GET)
    public Map<String, Object> verify(@PathVariable @NotNull Long userId,
                                      @RequestParam(name = "pass") Integer pass) {
        User user = userService.findUserById(userId);
        if(user.getRole().equals(RoleType.DRIVER_ROLE_STR)) {
            userService.verifyDriver(userId, pass);
            return new SuccessResult();
        }
        return new ErrorResult(StatusCode.PARAMETER_ERROR);
    }

}