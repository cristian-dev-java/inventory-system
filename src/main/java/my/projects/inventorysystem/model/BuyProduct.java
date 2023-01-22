package my.projects.inventorysystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "compra_producto")
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "product", schema = "movieschallenge")
public class BuyProduct {

    @Id
    @Column(name = "compra_producto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buyProductId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "compra_id" )
    private Buy buy;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id")
    private Product product;
    @Column(name = "cantidad")
    private int quantity;

}
