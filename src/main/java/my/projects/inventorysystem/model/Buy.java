package my.projects.inventorysystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@Table(name = "compra")
public class Buy {

    @Id
    @Column(name = "compra_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buyId;
    @Column(name = "fecha")
    private LocalDateTime date;
    @Column(name = "cliente_tipo_id")
    private String clientIdType;
    @Column(name = "cliente_id")
    private long clientId;
    @Column(name = "cliente_nombre")
    private String clientName;
    @OneToMany(
            mappedBy = "buy",
            cascade = CascadeType.ALL
    )
    private Set<BuyProduct> products = new HashSet<>();

    public Buy(){
        var products = this.products;
        for(BuyProduct buyProduct : products) buyProduct.setBuy(this);
        this.products = products;
    }

    public Buy(int buyId, LocalDateTime date, String clientIdType, long clientId, String clientName, BuyProduct... buyProducts) {
        this.buyId = buyId;
        this.date = date;
        this.clientIdType = clientIdType;
        this.clientId = clientId;
        this.clientName = clientName;
        for(BuyProduct buyProduct : buyProducts) buyProduct.setBuy(this);
        this.products = Stream.of(buyProducts).collect(Collectors.toSet());
    }

}
