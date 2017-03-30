package cn.edu.nju.backend.server.web.interceptor;

import cn.edu.nju.backend.server.common.auth.Auth;
import cn.edu.nju.backend.server.common.auth.AuthLevel;
import cn.edu.nju.backend.server.common.contants.StatusCode;
import cn.edu.nju.backend.server.common.result.ErrorResult;
import cn.edu.nju.backend.server.web.init.SessionMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongyq on 17/3/1.
 */
public class AuthInterceptor implements HandlerInterceptor {


    private MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Auth auth = method.getAnnotation(Auth.class);
        if (auth == null) {
            return true;
        } else {
            if (auth.value().equals(AuthLevel.ALL)) {
                return true;
            } else {
                String id = request.getHeader("session");
                if(id == null) {
                    forbidden(request, response);
                    return false;
                }
                HttpSession session = SessionMap.get(id);
                if (session == null) {
                    forbidden(request, response);
                    return false;
                } else {
                    Integer userRole = (Integer)session.getAttribute("role");
                    if(userRole == null) {
                        forbidden(request, response);
                        return false;
                    }

                    List<Integer> allowedRoles = new ArrayList<>();
                    for(int role : auth.roles()) {
                        allowedRoles.add(role);
                    }
                    if(allowedRoles.contains(userRole)) {
                        return true;
                    }else {
                        accessDenied(request, response);
                        return false;
                    }
                }
            }
        }
    }


    private void forbidden(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        jsonView.setUpdateContentLength(true);
        jsonView.render(new ErrorResult(StatusCode.USER_AUTH_SESSION_FAILED), request, response);
    }

    private void accessDenied(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        jsonView.setUpdateContentLength(true);
        jsonView.render(new ErrorResult(StatusCode.ILLEGAL_ACCESS), request, response);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
