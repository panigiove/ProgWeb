package web.example.progweb.model.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String getFormattedExpirationDate() {
        // Definisce il formato di data originale e quello desiderato
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Parso la data nel formato originale
            Date date = originalFormat.parse(expiration_date);
            // Ritorno la data nel formato desiderato
            return targetFormat.format(date);
        } catch (ParseException e) {
            // Gestisci l'errore di parsing, se la data non è nel formato atteso
            return null;
        }
    }

    public String getNomeEvent(){
        return nomeEvent;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
