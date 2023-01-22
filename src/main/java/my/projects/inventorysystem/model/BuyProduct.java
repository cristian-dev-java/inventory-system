package my.projects.inventorysystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "compra_producto")
@NoArgsConstructor
@AllArgsConstructor
public class BuyProduct {

    @Id
    @Column(name = "compra_producto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buyProductId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "compra_id" )
    @JsonIgnore
    private Buy buy;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "producto_id")
    private Product product;
    @Column(name = "cantidad")
    private int quantity;

}
