package cn.edu.nju.backend.server.data.model;

import javax.persistence.*;

/**
 * Created by zhongyq on 17/3/9.
 */
@Entity
@Table(name = "price")
public class Price {

    private Long id;
    private Long shippingOrder;
    private Long driver;
    private Long price;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="shipping_order", nullable = false, updatable = false)
    public Long getShippingOrder() {
        return shippingOrder;
    }

    public void setShippingOrder(Long shippingOrder) {
        this.shippingOrder = shippingOrder;
    }

    @Column(name="driver", nullable = false, updatable = false)
    public Long getDriver() {
        return driver;
    }

    public void setDriver(Long driver) {
        this.driver = driver;
    }

    @Column(name = "price", nullable = false)
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
