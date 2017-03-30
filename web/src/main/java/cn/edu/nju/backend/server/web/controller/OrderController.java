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
import cn.edu.nju.backend.server.data.dto.OrderDto;
import cn.edu.nju.backend.server.data.model.ShippingOrder;
import cn.edu.nju.backend.server.data.model.Price;
import cn.edu.nju.backend.server.data.model.User;
import cn.edu.nju.backend.server.data.service.OrderService;
import cn.edu.nju.backend.server.data.service.PriceService;
import cn.edu.nju.backend.server.data.service.UserService;
//import cn.edu.nju.backend.server.web.init.SessionMap;
import cn.edu.nju.backend.server.web.init.SessionMap;
import cn.edu.nju.backend.server.web.vo.CustomerOrderVo;
import cn.edu.nju.backend.server.web.vo.DetailOrderVo;
import cn.edu.nju.backend.server.web.vo.ManagerOrderVo;
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
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private UserService userService;

    /**
     * 添加订单
     *
     * @param orderDto
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.CUSTOMER_ROLE_ID})
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Map<String, Object> add(@RequestBody @NotNull OrderDto orderDto,
                                     HttpServletRequest request) throws Exception {

        if(orderDto.getBeginTime() == null || orderDto.getOrderName() == null
                || orderDto.getDestinationName() == null || orderDto.getStartingPointName() == null) {
            return new ErrorResult(StatusCode.PARAMETER_ERROR);
        }

//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));

        User user = (User) session.getAttribute("user");
        try {
            orderService.addOrder(user, orderDto);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ErrorResult(StatusCode.PARAMETER_ERROR);
        }
        return new SuccessResult();
    }

    /**
     * 确认订单报价
     *
     * @param orderId
     * @param priceId
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.CUSTOMER_ROLE_ID})
    @RequestMapping(path = "/confirm/{orderId}", method = RequestMethod.GET)
    public Map<String, Object> confirm(@PathVariable @NotNull Long orderId,
                                       @RequestParam(name = "priceId", required = true) Long priceId,
                                       HttpServletRequest request) throws Exception {
        ShippingOrder order = orderService.getOrderById(orderId);
        if(order != null) {
//            HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//            HttpSession session = request.getSession(false);
            HttpSession session = SessionMap.get(request.getHeader("session"));

            User user = (User) session.getAttribute("user");
            if(order.getCustomer() != user.getId()) {
                return new ErrorResult(StatusCode.ILLEGAL_ACCESS);
            }

            Price price = priceService.getPriceById(priceId);
            if(price != null) {
                orderService.confirmOrder(order, price);
                priceService.deletePrice(order);
                return new SuccessResult();
            }
            ErrorResult errorResult = new ErrorResult(StatusCode.PARAMETER_ERROR);
            errorResult.put("message", "无此报价");
            return errorResult;
        }
        ErrorResult errorResult = new ErrorResult(StatusCode.PARAMETER_ERROR);
        errorResult.put("message", "无此订单");
        return errorResult;
    }

    /**
     * 完成订单
     *
     * @param orderId
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.CUSTOMER_ROLE_ID})
    @RequestMapping(path = "/finish/{orderId}", method = RequestMethod.GET)
    public Map<String, Object> finish(@PathVariable @NotNull Long orderId,
                                       HttpServletRequest request) throws Exception {
        ShippingOrder order = orderService.getOrderById(orderId);
        if(order != null) {
//            HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//            HttpSession session = request.getSession(false);
            HttpSession session = SessionMap.get(request.getHeader("session"));

            User user = (User) session.getAttribute("user");
            if(order.getCustomer() != user.getId()) {
                return new ErrorResult(StatusCode.ILLEGAL_ACCESS);
            }

            orderService.finishOrder(order.getId());
            return new SuccessResult();
        }
        ErrorResult errorResult = new ErrorResult(StatusCode.PARAMETER_ERROR);
        errorResult.put("message", "无此订单");
        return errorResult;
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.CUSTOMER_ROLE_ID})
    @RequestMapping(path = "/cancel/{orderId}", method = RequestMethod.GET)
    public Map<String, Object> cancel(@PathVariable @NotNull Long orderId,
                                      HttpServletRequest request) throws Exception {
        ShippingOrder order = orderService.getOrderById(orderId);
        if(order != null) {
//            HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//            HttpSession session = request.getSession(false);
            HttpSession session = SessionMap.get(request.getHeader("session"));

            User user = (User) session.getAttribute("user");
            if(order.getCustomer() != user.getId()) {
                return new ErrorResult(StatusCode.ILLEGAL_ACCESS);
            }

            if(order.getBeginTimeMills() >= (System.currentTimeMillis() - 60 * 60 * 1000) && order.getStatus() == 1) {
                ErrorResult errorResult = new ErrorResult(StatusCode.ILLEGAL_ACCESS);
                errorResult.put("message", "已进行的订单无法取消,请联系管理员!");
                return errorResult;
            }

            orderService.cancelOrder(orderId);
            return new SuccessResult();
        }

        ErrorResult errorResult = new ErrorResult(StatusCode.PARAMETER_ERROR);
        errorResult.put("message", "无此订单");
        return errorResult;
    }

    /**
     * 用户查看未完成订单和正在进行的订单
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.CUSTOMER_ROLE_ID})
    @RequestMapping(path = "/showCustomerOrder", method = RequestMethod.GET)
    public Map<String, Object> customerOrder(HttpServletRequest request) throws Exception {
//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));

        User user = (User) session.getAttribute("user");
        List<ShippingOrder> myOrders = orderService.findCustomerOrder(user.getId());
        List<CustomerOrderVo> vos = Lists.transform(myOrders, order -> {
            CustomerOrderVo vo = Converter.convert(CustomerOrderVo.class, order);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setBeginTime(sdf.format(new Date(order.getBeginTimeMills())));
            vo.setLatestTime(sdf.format(new Date(order.getLatestTimeMills())));
            return vo;
        });

        SuccessResult successResult = new SuccessResult();
        successResult.put(ResponseMessage.List_RESULT, vos);
        return successResult;
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.CUSTOMER_ROLE_ID, RoleType.DRIVER_ROLE_ID})
    @RequestMapping(path = "/detail", method = RequestMethod.GET)
    public Map<String, Object> detail(@RequestParam(name = "orderId", required = true) Long orderId,
                                      HttpServletRequest request) throws Exception {
//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));

        User user = (User) session.getAttribute("user");
        ShippingOrder myOrder = orderService.getDetailOrderById(orderId);
        if(user.getRole().equals(RoleType.CUSTOMER_ROLE_STR) && myOrder.getCustomer() != user.getId()) {
            return new ErrorResult(StatusCode.ILLEGAL_ACCESS);
        }
        DetailOrderVo vo = Converter.convert(DetailOrderVo.class, myOrder);
        if(myOrder.getDriver() != null && myOrder.getStatus() == 1) {
            User driver = userService.getUserNameAndPhone(myOrder.getDriver());
            vo.setPrice(myOrder.getPrice() / 100.0);
            vo.setDriverName(driver.getUsername());
            vo.setDriverPhone(driver.getPhone());
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vo.setBeginTime(sdf.format(new Date(myOrder.getBeginTimeMills())));
        vo.setLatestTime(sdf.format(new Date(myOrder.getLatestTimeMills())));
        SuccessResult successResult = new SuccessResult();
        successResult.put(ResponseMessage.ITEM_RESULT, vo);
        return successResult;
    }


    /**
     * 司机查看周边订单
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.DRIVER_ROLE_ID})
    @RequestMapping(path = "/showUnconfirmedOrder", method = RequestMethod.GET)
    public Map<String, Object> driverOrder(@RequestParam(name = "longitude", required = true) Double longitude,
                                           @RequestParam(name = "latitude", required = true) Double latitude,
                                           @RequestParam(name = "radius", required = true) Double radius,
                                           HttpServletRequest request) throws Exception {
//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));

        User user = (User) session.getAttribute("user");
        List<ShippingOrder> orders = orderService.findNearlyOrder(longitude, latitude, radius);
        List<Price> prices = priceService.getMyPrice(user.getId());
        List<Long> orderIds = new ArrayList<>();
        for(Price price : prices) {
            orderIds.add(price.getShippingOrder());
        }
        List<CustomerOrderVo> vos = new ArrayList<>();
        for(ShippingOrder order : orders) {
            if(!orderIds.contains(order.getId())) {
                CustomerOrderVo vo = Converter.convert(CustomerOrderVo.class, order);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                vo.setBeginTime(sdf.format(new Date(order.getBeginTimeMills())));
                vo.setLatestTime(sdf.format(new Date(order.getLatestTimeMills())));
                vos.add(vo);
            }
        }

        SuccessResult successResult = new SuccessResult();
        successResult.put(ResponseMessage.List_RESULT, vos);
        return successResult;
    }

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.DRIVER_ROLE_ID})
    @RequestMapping(path = "/showDriverOrder", method = RequestMethod.GET)
    public Map<String, Object> driverIngOrder(HttpServletRequest request) throws Exception {
//        HttpSession session = SessionMap.get(request.getHeader(UserContants.MY_TOKEN));
//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));

        User user = (User) session.getAttribute("user");
        List<ShippingOrder> orders = orderService.showDriverOrder(user.getId());
        List<Price> prices = priceService.getMyPrice(user.getId());
        if(prices.size() > 0) {
            StringBuffer sb = new StringBuffer("(");
            for (Price price : prices) {
                sb.append(price.getShippingOrder());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            List<ShippingOrder> unconfirmedOrders = orderService.showOrderByIds(sb.toString());
            orders.addAll(unconfirmedOrders);
        }
        List<CustomerOrderVo> vos = Lists.transform(orders, order -> {
            CustomerOrderVo vo = Converter.convert(CustomerOrderVo.class, order);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setBeginTime(sdf.format(new Date(order.getBeginTimeMills())));
            vo.setLatestTime(sdf.format(new Date(order.getLatestTimeMills())));
            return vo;
        });

        SuccessResult successResult = new SuccessResult();
        successResult.put(ResponseMessage.List_RESULT, vos);
        return successResult;
    }


    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.CUSTOMER_ROLE_ID})
    @RequestMapping(path = "/selectPrice/{orderId}", method = RequestMethod.GET)
    public Map<String, Object> selectPrice(@PathVariable @NotNull Long orderId,
                                           @RequestParam(name = "priceId", required = true) Long priceId,
                                           HttpServletRequest request) throws Exception {
        ShippingOrder order = orderService.getOrderById(orderId);

//        HttpSession session = request.getSession(false);
        HttpSession session = SessionMap.get(request.getHeader("session"));

        User user = (User) session.getAttribute("user");

        if(order == null) {
            return new ErrorResult(StatusCode.PARAMETER_ERROR);
        }

        if(order.getCustomer() != user.getId()) {
            return new ErrorResult(StatusCode.ILLEGAL_ACCESS);
        }

        if(order.getLatestTimeMills() < System.currentTimeMillis()) {
            return new ErrorResult(StatusCode.ILLEGAL_ACCESS);
        }

        Price price = priceService.getPriceById(priceId);
        if(price == null) {
            return new ErrorResult(StatusCode.PARAMETER_ERROR);
        }

        orderService.updatePriceForOrder(orderId, price);
        priceService.deletePrice(order);
        return new SuccessResult();
    }


    @Auth(value = AuthLevel.LOGGED, roles = {RoleType.MANAGER_ROLE_ID})
    @RequestMapping(path = "", method = RequestMethod.GET)
    public Map<String, Object> allOrder() throws Exception {
        List<ShippingOrder> orders = orderService.findAll();
        List<ManagerOrderVo> vos = Lists.transform(orders, order -> {
            ManagerOrderVo vo = Converter.convert(ManagerOrderVo.class, order);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setBeginTime(sdf.format(new Date(order.getBeginTimeMills())));
            vo.setLatestTime(sdf.format(new Date(order.getLatestTimeMills())));
            if(order.getFinishTimeMills() != null) {
                vo.setFinishTime(sdf.format(new Date(order.getFinishTimeMills())));
            }
            if (order.getPrice() != null) {
                vo.setPrice(order.getPrice() / 100.0);
            }
            return vo;
        });

        SuccessResult successResult = new SuccessResult();
        successResult.put(ResponseMessage.List_RESULT, vos);
        return successResult;
    }

}