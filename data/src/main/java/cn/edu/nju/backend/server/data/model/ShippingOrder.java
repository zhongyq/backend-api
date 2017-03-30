package cn.edu.nju.backend.server.data.model;

import javax.persistence.*;

/**
 * Created by zhongyq on 17/3/9.
 */
@Entity
@Table(name="shipping_order")
public class ShippingOrder {

    private Long id;
    private Long customer;

    private String orderName;
    private String orderDescription;

    private String startingPointName;
    private Double startingPointLongitude;
    private Double startingPointLatitude;

    private String destinationName;
    private Double destinationLongitude;
    private Double destinationLatitude;

    private int status; // 0为未接单, 1为正在进行, 2为完成, 3为取消

    private Long beginTimeMills;    // 开始时间
    private Long latestTimeMills;   //最晚订单报价时间

    private Long finishTimeMills;

    private Long driver;

    private Long price;

    private Long createTimeMills;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="customer")
    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    @Column(name="order_name", nullable = false)
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Column(name="order_description", nullable = false)
    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    @Column(name="starting_point_name", nullable = false)
    public String getStartingPointName() {
        return startingPointName;
    }

    public void setStartingPointName(String startingPointName) {
        this.startingPointName = startingPointName;
    }

    @Column(name = "starting_point_longitude")
    public Double getStartingPointLongitude() {
        return startingPointLongitude;
    }

    public void setStartingPointLongitude(Double startingPointLongitude) {
        this.startingPointLongitude = startingPointLongitude;
    }

    @Column(name = "starting_point_latitude")
    public Double getStartingPointLatitude() {
        return startingPointLatitude;
    }

    public void setStartingPointLatitude(Double startingPointLatitude) {
        this.startingPointLatitude = startingPointLatitude;
    }

    @Column(name="destination_name", nullable = false)
    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    @Column(name = "destination_longitude")
    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    @Column(name = "destination_latitude")
    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    @Column(name="begin_time_mills", nullable = false)
    public Long getBeginTimeMills() {
        return beginTimeMills;
    }

    public void setBeginTimeMills(Long beginTimeMills) {
        this.beginTimeMills = beginTimeMills;
    }

    @Column(name = "latest_time_mills")
    public Long getLatestTimeMills() {
        return latestTimeMills;
    }

    public void setLatestTimeMills(Long latestTimeMills) {
        this.latestTimeMills = latestTimeMills;
    }

    @Column(name="finish_time_mills")
    public Long getFinishTimeMills() {
        return finishTimeMills;
    }

    public void setFinishTimeMills(Long finishTimeMills) {
        this.finishTimeMills = finishTimeMills;
    }

    @Column(name="status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name="driver")
    public Long getDriver() {
        return driver;
    }

    public void setDriver(Long driver) {
        this.driver = driver;
    }

    @Column(name="price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Column(name="create_time_mills")
    public Long getCreateTimeMills() {
        return createTimeMills;
    }

    public void setCreateTimeMills(Long createTimeMills) {
        this.createTimeMills = createTimeMills;
    }
}
