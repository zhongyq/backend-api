package cn.edu.nju.backend.server.data.service.impl;

import cn.edu.nju.backend.server.data.dto.OrderDto;
import cn.edu.nju.backend.server.data.mapper.OrderMapper;
import cn.edu.nju.backend.server.data.model.ShippingOrder;
import cn.edu.nju.backend.server.data.model.Price;
import cn.edu.nju.backend.server.data.model.User;
import cn.edu.nju.backend.server.data.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhongyq on 17/3/9.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ShippingOrder getOrderById(Long id) {
        ShippingOrder order = orderMapper.getOrderById(id);
        return order;
    }

    @Override
    public ShippingOrder getDetailOrderById(Long id) {
        return orderMapper.getDetailOrderById(id);
    }

    @Override
    public void addOrder(User user, OrderDto orderDto) throws ParseException {
        ShippingOrder order = new ShippingOrder();
        order.setCustomer(user.getId());
        order.setOrderName(orderDto.getOrderName());
        order.setOrderDescription(orderDto.getOrderDescription());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        order.setBeginTimeMills(sdf.parse(orderDto.getBeginTime()).getTime());
        if(orderDto.getLatestTime() != null) {
            order.setLatestTimeMills(sdf.parse(orderDto.getLatestTime()).getTime());
        }else {
            order.setLatestTimeMills(order.getBeginTimeMills() - 60 * 60 * 1000);
        }
        order.setCreateTimeMills(System.currentTimeMillis());
        order.setDestinationName(orderDto.getDestinationName());
        order.setDestinationLatitude(orderDto.getDestinationLatitude());
        order.setDestinationLongitude(orderDto.getDestinationLongitude());
        order.setStartingPointName(orderDto.getStartingPointName());
        order.setStartingPointLatitude(orderDto.getStartingPointLatitude());
        order.setStartingPointLongitude(orderDto.getStartingPointLongitude());
        order.setStatus(0);
        orderMapper.insertOrder(order);
    }

    @Override
    public void confirmOrder(ShippingOrder order, Price price) {
        orderMapper.confirmOrder(order.getId(), price.getDriver(), price.getPrice());
    }

    @Override
    public void finishOrder(Long id) {
        Long time = System.currentTimeMillis();
        orderMapper.finishOrder(id, time);
    }

    @Override
    public void cancelOrder(Long id) {
        orderMapper.cancelOrder(id);
    }

    @Override
    public List<ShippingOrder> findCustomerOrder(Long userId) {
        return orderMapper.findCustomerOrder(userId);
    }

    @Override
    public List<ShippingOrder> findNearlyOrder(Double longitude, Double latitude, Double radius) {
        Double minusLatitude = radius / 111.0;
        Double minusLongtitude = radius / 111 * Math.cos(latitude);
        return orderMapper.findNearlyOrder(longitude - minusLongtitude, longitude + minusLongtitude, latitude - minusLatitude, latitude + minusLatitude);
    }

    @Override
    public List<ShippingOrder> findAll() {
        return orderMapper.findAllOrder();
    }

    @Override
    public void updatePriceForOrder(Long orderId, Price price) {
        orderMapper.updatePriceForOrder(orderId, price);
    }

    @Override
    public List<ShippingOrder> showDriverOrder(Long userId) {
        return orderMapper.findDriverOrderById(userId);
    }

    @Override
    public List<ShippingOrder> showOrderByIds(String ids) {
        return orderMapper.findDriverOrderByOrderList(ids);
    }


}
