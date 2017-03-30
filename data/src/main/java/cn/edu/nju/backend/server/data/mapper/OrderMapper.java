package cn.edu.nju.backend.server.data.mapper;

import cn.edu.nju.backend.server.data.model.Price;
import cn.edu.nju.backend.server.data.model.ShippingOrder;
import cn.edu.nju.backend.server.data.model.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zhongyq on 17/3/9.
 */

@Mapper
public interface OrderMapper {

    @Insert("insert into shipping_order(customer, order_name, order_description, starting_point_name, starting_point_longitude, starting_point_latitude," +
            " destination_name, destination_longitude, destination_latitude, status, begin_time_mills, latest_time_mills, finish_time_mills, driver, create_time_mills) values" +
            "(#{shipping_order.customer},#{shipping_order.orderName},#{shipping_order.orderDescription},#{shipping_order.startingPointName}," +
            "#{shipping_order.startingPointLongitude},#{shipping_order.startingPointLatitude},#{shipping_order.destinationName},#{shipping_order.destinationLongitude}," +
            "#{shipping_order.destinationLatitude},#{shipping_order.status},#{shipping_order.beginTimeMills},#{shipping_order.latestTimeMills},#{shipping_order.finishTimeMills},#{shipping_order.driver},#{shipping_order.createTimeMills})")
    public void insertOrder(@Param("shipping_order") ShippingOrder shippingOrder);

    @Select("select * from shipping_order where id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "destinationName", column = "destination_name"),
            @Result(property = "destinationLongitude", column = "destination_longitude"),
            @Result(property = "destinationLatitude", column = "destination_latitude"),
            @Result(property = "startingPointName", column = "starting_point_name"),
            @Result(property = "startingPointLongitude", column = "starting_point_longitude"),
            @Result(property = "startingPointLatitude", column = "starting_point_latitude"),
            @Result(property = "orderName", column = "order_name"),
            @Result(property = "orderDescription", column = "order_description"),
            @Result(property = "status", column = "status"),
            @Result(property = "beginTimeMills", column = "begin_time_mills"),
            @Result(property = "latestTimeMills", column = "latest_time_mills")
    })
    public ShippingOrder getOrderById(@Param("id") Long id);

    @Update("update shipping_order set status=1, driver=#{driver}, price=#{price} where id=#{id}")
    public void confirmOrder(@Param("id") Long id, @Param("driver") Long driver, @Param("price") Long price);

    @Update("update shipping_order set status=2, finish_time_mills=#{finishTime} where id=#{id}")
    public void finishOrder(@Param("id") Long id, @Param("finishTime") Long finishTime);

    @Update("update shipping_order set status=3 where id=#{id}")
    public void cancelOrder(@Param("id") Long id);

    @Select("select id, destination_name, destination_longitude, destination_latitude, starting_point_name," +
            " starting_point_longitude, starting_point_latitude, order_name, order_description, status, begin_time_mills, latest_time_mills" +
            " from shipping_order where (status=0 or status=1) and customer=#{user}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "destinationName", column = "destination_name"),
            @Result(property = "destinationLongitude", column = "destination_longitude"),
            @Result(property = "destinationLatitude", column = "destination_latitude"),
            @Result(property = "startingPointName", column = "starting_point_name"),
            @Result(property = "startingPointLongitude", column = "starting_point_longitude"),
            @Result(property = "startingPointLatitude", column = "starting_point_latitude"),
            @Result(property = "orderName", column = "order_name"),
            @Result(property = "orderDescription", column = "order_description"),
            @Result(property = "status", column = "status"),
            @Result(property = "beginTimeMills", column = "begin_time_mills"),
            @Result(property = "latestTimeMills", column = "latest_time_mills")
    })
    public List<ShippingOrder> findCustomerOrder(@Param("user") Long user);

    @Select("select * from shipping_order where status = 0 and (starting_point_longitude > #{minLongitude} and starting_point_longitude < #{maxLongitude})" +
            " and (starting_point_latitude > #{minLatitude} and starting_point_latitude < #{maxLatitude})")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "destinationName", column = "destination_name"),
            @Result(property = "destinationLongitude", column = "destination_longitude"),
            @Result(property = "destinationLatitude", column = "destination_latitude"),
            @Result(property = "startingPointName", column = "starting_point_name"),
            @Result(property = "startingPointLongitude", column = "starting_point_longitude"),
            @Result(property = "startingPointLatitude", column = "starting_point_latitude"),
            @Result(property = "orderName", column = "order_name"),
            @Result(property = "orderDescription", column = "order_description"),
            @Result(property = "status", column = "status"),
            @Result(property = "beginTimeMills", column = "begin_time_mills"),
            @Result(property = "latestTimeMills", column = "latest_time_mills")
    })
    public List<ShippingOrder> findNearlyOrder(@Param("minLongitude") Double minLongitude, @Param("maxLongitude") Double maxLongitude, @Param("minLatitude") Double minLatitude, @Param("maxLatitude") Double maxLatitude);

    @Select("select * from shipping_order")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "destinationName", column = "destination_name"),
            @Result(property = "destinationLongitude", column = "destination_longitude"),
            @Result(property = "destinationLatitude", column = "destination_latitude"),
            @Result(property = "startingPointName", column = "starting_point_name"),
            @Result(property = "startingPointLongitude", column = "starting_point_longitude"),
            @Result(property = "startingPointLatitude", column = "starting_point_latitude"),
            @Result(property = "orderName", column = "order_name"),
            @Result(property = "orderDescription", column = "order_description"),
            @Result(property = "status", column = "status"),
            @Result(property = "beginTimeMills", column = "begin_time_mills"),
            @Result(property = "latestTimeMills", column = "latest_time_mills"),
            @Result(property = "customer", column = "customer"),
            @Result(property = "finishTimeMills", column = "finish_time_mills"),
            @Result(property = "driver", column = "driver"),
            @Result(property = "price", column = "price")
    })
    public List<ShippingOrder> findAllOrder();

    @Select("select * from shipping_order where id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "destinationName", column = "destination_name"),
            @Result(property = "destinationLongitude", column = "destination_longitude"),
            @Result(property = "destinationLatitude", column = "destination_latitude"),
            @Result(property = "startingPointName", column = "starting_point_name"),
            @Result(property = "startingPointLongitude", column = "starting_point_longitude"),
            @Result(property = "startingPointLatitude", column = "starting_point_latitude"),
            @Result(property = "orderName", column = "order_name"),
            @Result(property = "orderDescription", column = "order_description"),
            @Result(property = "status", column = "status"),
            @Result(property = "beginTimeMills", column = "begin_time_mills"),
            @Result(property = "latestTimeMills", column = "latest_time_mills"),
            @Result(property = "customer", column = "customer"),
            @Result(property = "finishTimeMills", column = "finish_time_mills"),
            @Result(property = "driver", column = "driver"),
            @Result(property = "price", column = "price")
    })
    public ShippingOrder getDetailOrderById(@Param("id") Long id);


    @Update("update shipping_order set status=1, driver=#{price.driver}, price=#{price.price} where id=#{id}")
    public void updatePriceForOrder(@Param("id") Long id, @Param("price") Price price);

    @Select("select * from shipping_order where status=1 and driver=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "destinationName", column = "destination_name"),
            @Result(property = "destinationLongitude", column = "destination_longitude"),
            @Result(property = "destinationLatitude", column = "destination_latitude"),
            @Result(property = "startingPointName", column = "starting_point_name"),
            @Result(property = "startingPointLongitude", column = "starting_point_longitude"),
            @Result(property = "startingPointLatitude", column = "starting_point_latitude"),
            @Result(property = "orderName", column = "order_name"),
            @Result(property = "orderDescription", column = "order_description"),
            @Result(property = "status", column = "status"),
            @Result(property = "beginTimeMills", column = "begin_time_mills"),
            @Result(property = "latestTimeMills", column = "latest_time_mills")
    })
    public List<ShippingOrder> findDriverOrderById(@Param("id") Long id);

    @Select("select * from shipping_order where id in ${orderList}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "destinationName", column = "destination_name"),
            @Result(property = "destinationLongitude", column = "destination_longitude"),
            @Result(property = "destinationLatitude", column = "destination_latitude"),
            @Result(property = "startingPointName", column = "starting_point_name"),
            @Result(property = "startingPointLongitude", column = "starting_point_longitude"),
            @Result(property = "startingPointLatitude", column = "starting_point_latitude"),
            @Result(property = "orderName", column = "order_name"),
            @Result(property = "orderDescription", column = "order_description"),
            @Result(property = "status", column = "status"),
            @Result(property = "beginTimeMills", column = "begin_time_mills"),
            @Result(property = "latestTimeMills", column = "latest_time_mills")
    })
    public List<ShippingOrder> findDriverOrderByOrderList(@Param("orderList") String orderList);
}
