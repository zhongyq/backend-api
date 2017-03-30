package cn.edu.nju.backend.server.data.dto;

/**
 * Created by zhongyq on 17/3/9.
 */
public class PriceDto {
    private Long orderId;
    private Double price;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
