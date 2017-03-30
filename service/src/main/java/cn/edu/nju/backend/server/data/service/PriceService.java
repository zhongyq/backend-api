package cn.edu.nju.backend.server.data.service;

import cn.edu.nju.backend.server.data.dto.PriceDto;
import cn.edu.nju.backend.server.data.model.ShippingOrder;
import cn.edu.nju.backend.server.data.model.Price;
import cn.edu.nju.backend.server.data.model.User;

import java.util.List;

/**
 * Created by zhongyq on 17/3/9.
 */
public interface PriceService {

    public Price getPriceById(Long priceId);

    public void addPrice(User user, ShippingOrder order, Double currentPrice);

    public void deletePrice(ShippingOrder shippingOrder);

    public void deletePriceById(Long id);

    public List<Price> getMyPrice(Long userId);

    public List<Price> getPriceByOrder(Long orderId);

    public List<Price> getOrderPriceByDriver(Long driverId, Long orderId);

    public Price getPriceByDriverAndOrder(Long orderId, Long userId);
}
