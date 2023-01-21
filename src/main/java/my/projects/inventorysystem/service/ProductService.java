package my.projects.inventorysystem.service;

import my.projects.inventorysystem.dto.ProductDto;
import my.projects.inventorysystem.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<List<ProductDto>> getProduct();

    ResponseEntity<Void> saveProduct(List<ProductDto> productDtoList);

    ResponseEntity<Void> updateProduct(ProductDto productDto);

    ResponseEntity<Void> deleteProduct(int productId);

}
