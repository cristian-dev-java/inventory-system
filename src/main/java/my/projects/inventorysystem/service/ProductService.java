package my.projects.inventorysystem.service;

import my.projects.inventorysystem.dto.CustomPageResponse;
import my.projects.inventorysystem.dto.ProductDto;
import my.projects.inventorysystem.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    /**
     * Método que permité consultar los productos paginados
     *
     * @param page número de pagina que se desea consultar
     * @param size número de elementos por pagina que se desea retornar
     * @return Objeto con la información de paginación y la lista de productos
     */
    ResponseEntity<CustomPageResponse> getProduct(int page, int size);

    /**
     * Método que permite guardar productos
     *
     * @param productDtoList lista de productos a guardar
     * @return
     */
    ResponseEntity<Void> saveProduct(List<ProductDto> productDtoList);

    /**
     * Método que permite actualizar un producto
     *
     * @param productDto producto a actualizar
     * @return
     */
    ResponseEntity<Void> updateProduct(ProductDto productDto, int productId);

    /**
     * Método que permite desactivar un producto (ya que no es una buena práctica eliminar información de la bd)
     *
     * @param productId id del producto a desactivar
     * @return
     */
    ResponseEntity<Void> deleteProduct(int productId);

}
