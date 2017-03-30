package cn.edu.nju.backend.server.data.mapper;

import cn.edu.nju.backend.server.data.model.Price;
import cn.edu.nju.backend.server.data.model.ShippingOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhongyq on 17/3/9.
 */
@Mapper
public interface PriceMapper {

    @Select("select * from price where id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "shippingOrder", column = "shipping_order"),
            @Result(property = "driver", column = "driver"),
            @Result(property = "price", column = "price")
    })
    public Price getPriceById(@Param("id") Long id);

    @Insert("insert into price(shipping_order, driver, price) values (#{price.shippingOrder}, #{price.driver}, #{price.price})")
    public void insertPrice(@Param("price") Price price);

    @Delete("delete from price where shipping_order = #{shipping_order.id}")
    public void deletePrice(@Param("shipping_order") ShippingOrder shippingOrder);

    @Delete("delete from price where id=#{id}")
    public void deletePriceById(@Param("id") Long id);

    @Select("select * from price where driver=#{userId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "shippingOrder", column = "shipping_order"),
            @Result(property = "driver", column = "driver"),
            @Result(property = "price", column = "price")
    })
    public List<Price> getMyPrice(@Param("userId") Long userId);

    @Select("select * from price where shipping_order=#{shippingOrder}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "shippingOrder", column = "shipping_order"),
            @Result(property = "driver", column = "driver"),
            @Result(property = "price", column = "price")
    })
    public List<Price> getPriceByOrder(@Param("shippingOrder") Long shippingOrder);


    @Select("select * from price where shipping_order=#{shippingOrder} and driver=#{driver}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "shippingOrder", column = "shipping_order"),
            @Result(property = "driver", column = "driver"),
            @Result(property = "price", column = "price")
    })
    public List<Price> getOrderPriceByDriver(@Param("shippingOrder") Long shippingOrder, @Param("driver") Long driver);


    @Update("update price set price=#{price.price} where id=#{price.id}")
    public void updatePrice(@Param("price") Price price);

    @Select("select * from price where shipping_order=#{orderId} and driver=#{driverId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "shippingOrder", column = "shipping_order"),
            @Result(property = "driver", column = "driver"),
            @Result(property = "price", column = "price")
    })
    public List<Price> getPriceByDriverAndOrder(@Param("orderId") Long orderId, @Param("driverId") Long driverId);
}

