package my.projects.inventorysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private int productId;
    private String name;
    private int inInventory;
    private boolean enabled;
    private int min;
    private int max;

}
