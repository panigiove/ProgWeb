package web.example.progweb.model.entity;

/*
CREATE TABLE SCONTI_EVENTO (
    id_sconto INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_evento INT,
    data_scadenza DATE NOT NULL,
    sconto DECIMAL(5, 2) NOT NULL,
    FOREIGN KEY (id_evento) REFERENCES EVENTI(id_evento)
);
 */

public class Discount {
    private String id_sconto;
    private String id_evento;
    private String data_scadenza;
    private double sconto;

    public Discount(String id_sconto, String id_evento, String data_scadenza, double sconto) {
        this.id_sconto = id_sconto;
        this.id_evento = id_evento;
        this.data_scadenza = data_scadenza;
        this.sconto = sconto;
    }

    public String getId_sconto() {
        return id_sconto;
    }

    public String getId_evento() {
        return id_evento;
    }

    public String getData_scadenza() {
        return data_scadenza;
    }

    public double getSconto() {
        return sconto;
    }
}
