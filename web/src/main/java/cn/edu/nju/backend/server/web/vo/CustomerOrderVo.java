package cn.edu.nju.backend.server.web.vo;

/**
 * Created by zhongyq on 17/3/9.
 */
public class CustomerOrderVo {

    private Long id;

    private String orderName;
    private String orderDescription;

    private String startingPointName;
    private Double startingPointLongitude;
    private Double startingPointLatitude;

    private String destinationName;
    private Double destinationLongitude;
    private Double destinationLatitude;

    private int status; // 0为未接单, 1为正在进行, 2为完成, 3为取消

    private String beginTime; // 开始时间
    private String latestTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getStartingPointName() {
        return startingPointName;
    }

    public void setStartingPointName(String startingPointName) {
        this.startingPointName = startingPointName;
    }

    public Double getStartingPointLongitude() {
        return startingPointLongitude;
    }

    public void setStartingPointLongitude(Double startingPointLongitude) {
        this.startingPointLongitude = startingPointLongitude;
    }

    public Double getStartingPointLatitude() {
        return startingPointLatitude;
    }

    public void setStartingPointLatitude(Double startingPointLatitude) {
        this.startingPointLatitude = startingPointLatitude;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }
}
