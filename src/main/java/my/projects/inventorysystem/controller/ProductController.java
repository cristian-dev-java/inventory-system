package my.projects.inventorysystem.controller;

import lombok.RequiredArgsConstructor;
import my.projects.inventorysystem.dto.ProductDto;
import my.projects.inventorysystem.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/product")
    public ResponseEntity<List<ProductDto>> getProduct() {
        return productService.getProduct();
    }

    @PostMapping(value = "/product")
    public ResponseEntity<Void> saveProduct(@RequestBody List<ProductDto> productDtoList) {
        return productService.saveProduct(productDtoList);
    }

    @PutMapping(value = "/product")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping(value = "/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
        return productService.deleteProduct(productId);
    }

}
