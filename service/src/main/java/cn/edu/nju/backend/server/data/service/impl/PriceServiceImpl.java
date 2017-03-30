package cn.edu.nju.backend.server.data.service.impl;

import cn.edu.nju.backend.server.data.dto.PriceDto;
import cn.edu.nju.backend.server.data.mapper.PriceMapper;
import cn.edu.nju.backend.server.data.model.ShippingOrder;
import cn.edu.nju.backend.server.data.model.Price;
import cn.edu.nju.backend.server.data.model.User;
import cn.edu.nju.backend.server.data.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhongyq on 17/3/9.
 */
@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceMapper priceMapper;

    @Override
    public Price getPriceById(Long priceId) {
        return priceMapper.getPriceById(priceId);
    }

    @Override
    public void addPrice(User user, ShippingOrder order, Double currentPrice) {
        // 获取已有的price
        List<Price> prices = priceMapper.getOrderPriceByDriver(order.getId(), user.getId());
        if(prices.size() == 1) {
            Price price = prices.get(0);
            price.setPrice((long)(currentPrice * 100));
            priceMapper.updatePrice(price);
        }else {
            Price price = new Price();
            price.setDriver(user.getId());
            price.setShippingOrder(order.getId());
            price.setPrice((long) (currentPrice * 100));
            priceMapper.insertPrice(price);
        }
    }

    @Override
    public void deletePrice(ShippingOrder shippingOrder) {
        priceMapper.deletePrice(shippingOrder);
    }

    @Override
    public void deletePriceById(Long id) {
        priceMapper.deletePriceById(id);
    }

    @Override
    public List<Price> getMyPrice(Long userId) {
        return priceMapper.getMyPrice(userId);
    }

    @Override
    public List<Price> getPriceByOrder(Long orderId) {
        return priceMapper.getPriceByOrder(orderId);
    }

    @Override
    public List<Price> getOrderPriceByDriver(Long driverId, Long orderId) {
        return priceMapper.getOrderPriceByDriver(orderId, driverId);
    }

    @Override
    public Price getPriceByDriverAndOrder(Long orderId, Long userId) {
        List<Price> prices = priceMapper.getPriceByDriverAndOrder(orderId, userId);
        if(prices.size() > 0) {
            return prices.get(0);
        }
        return null;
    }
}
