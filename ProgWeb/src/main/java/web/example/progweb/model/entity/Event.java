package web.example.progweb.model.entity;

/*
CREATE TABLE EVENTI (
    id_evento INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_categoria INT,
    id_localita INT,
    nome VARCHAR(255) NOT NULL,
    inizio TIMESTAMP NOT NULL,
    fine TIMESTAMP NOT NULL,
    totale_poltrona INT NOT NULL,
    disponibilita_poltrona INT NOT NULL,
    totale_in_piedi INT NOT NULL,
    disponibilita_in_piedi INT NOT NULL,
    prezzi_poltrona DECIMAL(10, 2) NOT NULL,
    prezzi_in_piedi DECIMAL(10, 2) NOT NULL,
    n_click INT DEFAULT 0,
    FOREIGN KEY (id_localita) REFERENCES LOCALITA(id_localita),
    FOREIGN KEY (id_categoria) REFERENCES CATEGORIA(id_categoria)
);
 */

import java.math.BigDecimal;

public class Event {
    private final int id;
    private final int idCategory;
    private final int idLocation;
    private final String nomeLocation;
    private final String nomeCategory;
    private final String name;
    private final String descrizione;
    private final String start;
    private final String end;
    private final int totalSeats;
    private final int availableSeats;
    private final int totalStanding;
    private final int availableStanding;
    private final BigDecimal seatPrice;
    private final BigDecimal standingPrice;
    private final int nClick;
    private final String imageName;

    public Event (int id, int idCategory, int idLocation, String nomeLocation, String nomeCategory, String name, String descrizione,String start, String end, int totalSeats, int availableSeats, int totalStanding, int availableStanding, BigDecimal seatPrice, BigDecimal standingPrice, int nClick, String imageName) {
       this.id = id;
        this.idCategory = idCategory;
        this.idLocation = idLocation;
        this.nomeLocation = nomeLocation;
        this.nomeCategory = nomeCategory;
        this.name = name;
        this.descrizione = descrizione;
        this.start = start;
        this.end = end;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.totalStanding = totalStanding;
        this.availableStanding = availableStanding;
        this.seatPrice = seatPrice;
        this.standingPrice = standingPrice;
        this.nClick = nClick;
        this.imageName = imageName;
    }

    public int getId() {
        return this.id;
    }

    public int getnClick() {
        return this.nClick;
    }

    public String getNomeLocation() {
        return nomeLocation;
    }

    public String getNomeCategory(){
        return nomeCategory;
    }

    public String getName() {
        return this.name;
    }

    public String getStart() {
        return this.start;
    }

    public String getEnd() {
        return this.end;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getTotalSeats() {
        return this.totalSeats;
    }

    public int getAvailableSeats() {
        return this.availableSeats;
    }

    public int getTotalStanding() {
        return this.totalStanding;
    }

    public int getAvailableStanding() {
        return this.availableStanding;
    }

    public BigDecimal getSeatPrice() {
        return this.seatPrice;
    }

    public BigDecimal getStandingPrice() {
        return this.standingPrice;
    }

    public String getImageName(){
        return this.imageName;
    }

    public static String formatData (String data) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.text.SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("dd MMMM yyyy HH:mm");
            java.util.Date date = sdf.parse(data);
            return outputFormat.format(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return data;
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", idCategory='" + idCategory + '\'' +
                ", idLocation='" + idLocation + '\'' +
                ", nome location='" + nomeLocation + '\'' +
                ", name='" + name + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", totalSeats='" + totalSeats + '\'' +
                ", availableSeats='" + availableSeats + '\'' +
                ", totalStanding='" + totalStanding + '\'' +
                ", availableStanding='" + availableStanding + '\'' +
                ", seatPrice='" + seatPrice + '\'' +
                ", standingPrice='" + standingPrice + '\'' +
                ", nClick=" + nClick +
                ", imageName=" + imageName +
                '}';
    }
}
