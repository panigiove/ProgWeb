package web.example.progweb.model.entity;

import java.math.BigDecimal;

public class Discount {
    private int id_discount;
    private int id_event;
    private String nomeEvent;
    private String expiration_date;
    private BigDecimal discount;

    public Discount(int id_discount, int id_event, String nomeEvent, String expiration_date, BigDecimal discount) {
        this.id_discount = id_discount;
        this.id_event = id_event;
        this.nomeEvent = nomeEvent;
        this.expiration_date = expiration_date;
        this.discount = discount;
    }

    public int getId_discount() {
        return id_discount;
    }

    public int getId_event() {
        return id_event;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
