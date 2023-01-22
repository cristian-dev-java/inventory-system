package my.projects.inventorysystem.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import my.projects.inventorysystem.dto.CustomPageResponse;
import my.projects.inventorysystem.dto.ProductDto;
import my.projects.inventorysystem.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/product")
    public ResponseEntity<CustomPageResponse> getProduct(
            @RequestParam @Min(0) Integer page,
            @RequestParam @Min(1) Integer size) {
        return productService.getProduct(page, size);
    }

    @PostMapping(value = "/product")
    public ResponseEntity<Void> saveProduct(@RequestBody @Valid List<ProductDto> productDtoList) {
        return productService.saveProduct(productDtoList);
    }

    @PutMapping(value = "/product/{productId}")
    public ResponseEntity<Void> updateProduct(
            @RequestBody @Valid ProductDto productDto,
            @PathVariable @Min(value = 1) int productId) {
        return productService.updateProduct(productDto, productId);
    }

    @DeleteMapping(value = "/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Min(1) int productId) {
        return productService.deleteProduct(productId);
    }

}
