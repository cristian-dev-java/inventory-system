package my.projects.inventorysystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@Entity
@Builder
@Table(name = "compra")
@NoArgsConstructor
@AllArgsConstructor
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
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<BuyProduct> products = new HashSet<>();

/*    public void setBuyId(int buyId) {
        this.buyId = buyId;
        for(BuyProduct buyProduct : this.products) buyProduct.setBuy(this);
    }*/

}
