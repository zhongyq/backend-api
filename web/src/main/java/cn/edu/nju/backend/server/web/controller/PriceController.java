package cn.edu.nju.backend.server.web.controller;

import cn.edu.nju.backend.server.common.auth.Auth;
import cn.edu.nju.backend.server.common.auth.AuthLevel;
import cn.edu.nju.backend.server.common.contants.ResponseMessage;
import cn.edu.nju.backend.server.common.contants.RoleType;
import cn.edu.nju.backend.server.common.contants.StatusCode;
import cn.edu.nju.backend.server.common.contants.UserContants;
import cn.edu.nju.backend.server.common.result.ErrorResult;
import cn.edu.nju.backend.server.common.result.SuccessResult;
import cn.edu.nju.backend.server.common.util.Converter;
import cn.edu.nju.backend.server.data.dto.PriceDto;
import cn.edu.nju.backend.server.data.model.Price;
import cn.edu.nju.backend.server.data.model.ShippingOrder;
import cn.edu.nju.backend.server.data.model.User;
import cn.edu.nju.backend.server.data.service.OrderService;
import cn.edu.nju.backend.server.data.service.PriceService;
import cn.edu.nju.backend.server.data.service.UserService;
import cn.edu.nju.backend.server.web.init.SessionMap;
import cn.edu.nju.backend.server.web.vo.CustomerPriceVo;
import cn.edu.nju.backend.server.web.vo.DriverPriceVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongyq on 17/3/9.
 */
@RestController
@EnableConfigurationProperties
@RequestMapping(path = "/price")
public class PriceController {

    @Autowired
    private PriceService priceService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    /**
     * 司机添加报价
     *
     * @param priceDto
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.DRIVER_ROLE_ID})
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Map<String, Object> add(@RequestBody @NotNull PriceDto priceDto,
                                      HttpServletRequest request) throws Exception {
//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
        // HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));
        User user = (User) session.getAttribute("user");
        ShippingOrder order = orderService.getOrderById(priceDto.getOrderId());
        priceService.addPrice(user, order, priceDto.getPrice());
        return new SuccessResult();
    }

    /**
     * 司机删除报价
     *
     * @param orderId
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.DRIVER_ROLE_ID})
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public Map<String, Object> cancel(@RequestParam(name = "orderId", required = true) Long orderId,
                                      HttpServletRequest request) throws Exception {
//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));

        User user = (User) session.getAttribute("user");
        Price price = priceService.getPriceByDriverAndOrder(orderId, user.getId());
        if(price != null) {
            if(price.getDriver() != user.getId()) {
                return new ErrorResult(StatusCode.ILLEGAL_ACCESS);
            }
            priceService.deletePriceById(price.getId());
            return new SuccessResult();
        }
        return new ErrorResult(StatusCode.PARAMETER_ERROR);
    }

    /**
     *
     * 查看所有的报价
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.DRIVER_ROLE_ID})
    @RequestMapping(path = "/showMyPrice", method = RequestMethod.GET)
    public Map<String, Object> showMyPrice(HttpServletRequest request) throws Exception {
//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));

        User user = (User) session.getAttribute("user");
        List<Price> prices = priceService.getMyPrice(user.getId());
        List<DriverPriceVo> voList = Lists.transform(prices, price -> {
            DriverPriceVo vo = new DriverPriceVo();
            ShippingOrder shippingOrder = orderService.getOrderById(price.getShippingOrder());
            vo.setOrderName(shippingOrder.getOrderName());
            vo.setStartingPointName(shippingOrder.getStartingPointName());
            vo.setDestinationName(shippingOrder.getDestinationName());
            vo.setPrice(price.getPrice() / 100.0);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setBeginTime(sdf.format(new Date(shippingOrder.getBeginTimeMills())));
            return vo;
        });
        SuccessResult successResult = new SuccessResult();
        successResult.put(ResponseMessage.List_RESULT, voList);
        return successResult;
    }


    /**
     *
     * 用户查看所有的报价
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.CUSTOMER_ROLE_ID})
    @RequestMapping(path = "/orderPrice", method = RequestMethod.GET)
    public Map<String, Object> orderPrice(@RequestParam(name = "orderId") Long orderId,
            HttpServletRequest request) throws Exception {
//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));
        User user = (User) session.getAttribute("user");
        ShippingOrder shippingOrder = orderService.getOrderById(orderId);
        if(shippingOrder != null) {
            if(shippingOrder.getCustomer() != user.getId()) {
                return new ErrorResult(StatusCode.ILLEGAL_ACCESS);
            }
            List<Price> orderPrice = priceService.getPriceByOrder(shippingOrder.getId());
            List<CustomerPriceVo> priceVos = new ArrayList<>();
            for(Price price : orderPrice) {
                CustomerPriceVo vo = new CustomerPriceVo();
                vo.setId(price.getId());
                vo.setDriverId(price.getDriver());
                User u = userService.getUserNameAndPhone(price.getDriver());
                vo.setDriverName(u.getUsername());
                vo.setDriverPhone(u.getPhone());
                vo.setPrice(price.getPrice() / 100.0);
                priceVos.add(vo);
            }
            SuccessResult successResult = new SuccessResult();
            successResult.put(ResponseMessage.List_RESULT, priceVos);
            return successResult;
        }
        return new ErrorResult(StatusCode.PARAMETER_ERROR);
    }

    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.DRIVER_ROLE_ID})
    @RequestMapping(path = "/myOrderPrice", method = RequestMethod.GET)
    public Map<String, Object> driverPrice(@RequestParam(name = "orderId") Long orderId,
                                          HttpServletRequest request) throws Exception {
//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));
        User user = (User) session.getAttribute("user");
        List<Price> orderPrice = priceService.getOrderPriceByDriver(user.getId(), orderId);
        Double price = null;
        if(orderPrice.size() == 1) {
            price = orderPrice.get(0).getPrice() / 100.0;
        }
        SuccessResult successResult = new SuccessResult();
        successResult.put(ResponseMessage.ITEM_RESULT, price);
        return successResult;
    }



}