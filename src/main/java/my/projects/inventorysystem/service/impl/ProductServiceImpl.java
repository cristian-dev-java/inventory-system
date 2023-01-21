package my.projects.inventorysystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.projects.inventorysystem.dto.ProductDto;
import my.projects.inventorysystem.model.Product;
import my.projects.inventorysystem.repository.ProductRepository;
import my.projects.inventorysystem.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<List<ProductDto>> getProduct() {
        log.info("Init getProduct with: {}");
        ResponseEntity<List<ProductDto>> response;
        try {
            List<ProductDto> productDtoList = new ArrayList<>();
            List<Product> productList = productRepository.findAll();
            for (Product product : productList) {
                ProductDto productDto = ProductDto.builder()
                        .productId(product.getProductId())
                        .name(product.getName())
                        .inInventory(product.getInInventory())
                        .enabled(product.isEnabled())
                        .min(product.getMin())
                        .max(product.getMax()).build();
                productDtoList.add(productDto);
            }
            response = ResponseEntity.ok(productDtoList);
        } catch (Exception e) {
            log.info("Error in getProduct : {}", e.getMessage());
            response = ResponseEntity.internalServerError().build();
        }
        log.info("Finish getProduct");
        return response;
    }

    @Override
    public ResponseEntity<Void> saveProduct(List<ProductDto> productDtoList) {
        log.info("Init saveProduct with: {}", productDtoList);
        ResponseEntity<Void> response;
        try {
            for (ProductDto productDto : productDtoList) {
                Product product = Product.builder()
                        .name(productDto.getName())
                        .inInventory(productDto.getInInventory())
                        .enabled(productDto.isEnabled())
                        .min(productDto.getMin())
                        .max(productDto.getMax()).build();
                productRepository.save(product);
            }
            response = ResponseEntity.ok().build();
            log.info("Finish saveProduct");
        } catch (Exception e) {
            log.info("Error in saveProduct : {}", e.getMessage());
            response = ResponseEntity.internalServerError().build();
        }
        return response;
    }

    @Override
    public ResponseEntity<Void> updateProduct(ProductDto productDto) {
        log.info("Init updateProduct with: {}", productDto);
        ResponseEntity<Void> response;
        try {
            Optional<Product> productOptional = productRepository.findById(productDto.getProductId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setInInventory(productDto.getInInventory());
                product.setName(productDto.getName());
                product.setEnabled(productDto.isEnabled());
                product.setMin(productDto.getMin());
                product.setMax(productDto.getMax());
                productRepository.save(product);
                response = ResponseEntity.ok().build();
            } else {
                response = ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            log.info("Error in updateProduct : {}", e.getMessage());
            response = ResponseEntity.internalServerError().build();
        }
        log.info("Finish updateProduct");
        return response;
    }

    @Override
    public ResponseEntity<Void> deleteProduct(int productId) {
        log.info("Init deleteProduct with id: {}", productId);
        ResponseEntity<Void> response;
        try {
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setEnabled(Boolean.FALSE);
                productRepository.save(product);
                response = ResponseEntity.ok().build();
            } else {
                response = ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            log.info("Error in updateProduct : {}", e.getMessage());
            response = ResponseEntity.internalServerError().build();
        }
        log.info("Finish deleteProduct");
        return response;
    }


}
