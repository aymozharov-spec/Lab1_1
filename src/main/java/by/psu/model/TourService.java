package by.psu.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
public abstract class TourService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private BigDecimal price;

    @Column(name = "\"from\"")  // ← ВАЖНО: экранируем кавычками
    private LocalDate from;

    @Column(name = "\"to\"")    // ← ВАЖНО: экранируем кавычками
    private LocalDate to;

    public TourService() {}

    public TourService(Integer id, String name, BigDecimal price, LocalDate from, LocalDate to) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.from = from;
        this.to = to;
    }

    public abstract BigDecimal calculateTotalPrice(int participants);

    // Геттеры и сеттеры
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDate getFrom() { return from; }
    public void setFrom(LocalDate from) { this.from = from; }

    public LocalDate getTo() { return to; }
    public void setTo(LocalDate to) { this.to = to; }

    public boolean isAvailableOn(LocalDate date) {
        return !(from.isAfter(date) || to.isBefore(date));
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return "TourService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", from=" + (from != null ? from.format(formatter) : "null") +
                ", to=" + (to != null ? to.format(formatter) : "null") +
                '}';
    }
}