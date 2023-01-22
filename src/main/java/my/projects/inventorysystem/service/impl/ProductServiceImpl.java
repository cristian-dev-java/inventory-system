package my.projects.inventorysystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.projects.inventorysystem.dto.CustomPageResponse;
import my.projects.inventorysystem.dto.ProductDto;
import my.projects.inventorysystem.model.Product;
import my.projects.inventorysystem.repository.ProductRepository;
import my.projects.inventorysystem.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<CustomPageResponse> getProduct(int page, int size) {
        log.info("Init getProduct with: {}");
        ResponseEntity<CustomPageResponse> response;
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Product> productPage = productRepository.findAll(pageable);
            Page<ProductDto> productDtoPage = productPage.map(p -> ProductDto.builder()
                    .productId(p.getProductId())
                    .name(p.getName())
                    .inInventory(p.getInInventory())
                    .enabled(p.isEnabled())
                    .min(p.getMin())
                    .max(p.getMax()).build());
            CustomPageResponse customPageResponse = CustomPageResponse.builder()
                    .lastPage(productDtoPage.isLast())
                    .totalPage(productDtoPage.getTotalPages())
                    .totalElements(productDtoPage.getTotalElements())
                    .data(productDtoPage.getContent()).build();
            response = ResponseEntity.ok(customPageResponse);
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
                        .enabled(productDto.getEnabled())
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
    public ResponseEntity<Void> updateProduct(ProductDto productDto, int productÍd) {
        log.info("Init updateProduct with: {}", productDto);
        ResponseEntity<Void> response;
        try {
            Optional<Product> productOptional = productRepository.findById(productÍd);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setInInventory(productDto.getInInventory());
                product.setName(productDto.getName());
                product.setEnabled(productDto.getEnabled());
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
