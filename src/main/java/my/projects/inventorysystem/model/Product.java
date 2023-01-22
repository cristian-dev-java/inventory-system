package my.projects.inventorysystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "producto")
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "product", schema = "movieschallenge")
public class Product {

    @Id
    @Column(name = "producto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @Column(name = "nombre")
    private String name;
    @Column(name = "en_inventario")
    private int inInventory;
    @Column(name = "habilitado")
    private boolean enabled;
    @Column(name = "minimo")
    private int min;
    @Column(name = "maximo")
    private int max;
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    Set<BuyProduct> buys = new HashSet<>();

    public Product(int productId) {
        super();
        this.productId = productId;
    }

}
