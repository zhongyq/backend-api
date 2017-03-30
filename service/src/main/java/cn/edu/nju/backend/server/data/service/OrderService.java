package cn.edu.nju.backend.server.data.service;

import cn.edu.nju.backend.server.data.dto.OrderDto;
import cn.edu.nju.backend.server.data.model.ShippingOrder;
import cn.edu.nju.backend.server.data.model.Price;
import cn.edu.nju.backend.server.data.model.User;

import java.text.ParseException;
import java.util.List;

/**
 * Created by zhongyq on 17/3/9.
 */
public interface OrderService {

    public ShippingOrder getOrderById(Long id);

    public ShippingOrder getDetailOrderById(Long id);

    public void addOrder(User user, OrderDto orderDto) throws ParseException;

    public void confirmOrder(ShippingOrder order, Price price);

    public void finishOrder(Long id);

    public void cancelOrder(Long id);

    public List<ShippingOrder> findCustomerOrder(Long userId);

    public List<ShippingOrder> findNearlyOrder(Double longitude, Double latitude, Double radius);

    public List<ShippingOrder> findAll();

    public void updatePriceForOrder(Long orderId, Price price);

    public List<ShippingOrder> showDriverOrder(Long userId);

    public List<ShippingOrder> showOrderByIds(String ids);

}
