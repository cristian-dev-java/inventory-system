package my.projects.inventorysystem.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.projects.inventorysystem.dto.BuyDetailDto;
import my.projects.inventorysystem.dto.BuyDto;
import my.projects.inventorysystem.model.Buy;
import my.projects.inventorysystem.model.BuyProduct;
import my.projects.inventorysystem.model.Product;
import my.projects.inventorysystem.repository.BuyRepository;
import my.projects.inventorysystem.repository.ProductRepository;
import my.projects.inventorysystem.service.BuyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuyServiceImpl implements BuyService {

    private final BuyRepository buyRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<List<BuyDto>> getBuys() {
        log.info("Init getBuys");
        ResponseEntity<List<BuyDto>> response;
        try {
            List<Buy> buyList = buyRepository.findAll();
            List<BuyDto> buyDtoList = buyList.stream().map(buy -> BuyDto.builder()
                    .date(buy.getDate())
                    .clientIdType(buy.getClientIdType())
                    .clientId(buy.getClientId())
                    .clientName(buy.getClientName())
                    .products(buy.getProducts().stream().map(buyProduct -> BuyDetailDto.builder()
                            .productId(buyProduct.getProduct().getProductId())
                            .name(buyProduct.getProduct().getName())
                            .quantity(buyProduct.getQuantity()).build())
                            .toList()).build())
                    .toList();
            response = ResponseEntity.ok(buyDtoList);
        } catch (Exception e) {
            log.info("Error in Init with messageError: {}", e.getMessage());
            response = ResponseEntity.internalServerError().build();
        }
        return response;
    }

    @Override
    public ResponseEntity<String> saveBuy(BuyDto buyDto) {
        log.info("Init saveBuy with: {}", buyDto);
        ResponseEntity<String> response;
        try {
            String responseisAValidPurchase = isAValidPurchase(buyDto.getProducts());
            if(StringUtils.isBlank(responseisAValidPurchase) ){
                Buy buy = Buy.builder()
                        .date(buyDto.getDate())
                        .clientIdType(buyDto.getClientIdType())
                        .clientId(buyDto.getClientId())
                        .clientName(buyDto.getClientName())
                        .products(buyDto.getProducts()
                                .stream()
                                .map(buyDetailDto -> BuyProduct.builder()
                                        .product(Product.builder()
                                                .productId(buyDetailDto.getProductId()).build())
                                        .quantity(buyDetailDto.getQuantity()).build()).collect(Collectors.toSet())).build();
                buy.getProducts().forEach(buyProduct -> buyProduct.setBuy(buy));
                buyRepository.save(buy);
                deductUnits(buyDto.getProducts());
                response = ResponseEntity.ok().build();
            }else{
                response = ResponseEntity.badRequest().body(responseisAValidPurchase);
            }
        } catch (Exception e) {
            log.info("Error in saveBuy with messageError: {}", e.getMessage());
            response = ResponseEntity.internalServerError().build();
        }
        return response;
    }

    /**
     * Método que permite validar las restricciones de compra
     *
     * @param buyDetailDtoList lista de products
     * @return mensaje de error en caso de no pasar alguna restricción
     */
    private String isAValidPurchase(List<BuyDetailDto> buyDetailDtoList){
        //Validar si está disponible
        String response = "";
        for(BuyDetailDto buyDetailDto: buyDetailDtoList){
            Optional<Product> productOptional = productRepository.findById(buyDetailDto.getProductId());
            if(productOptional.isPresent()){
                Product product = productOptional.get();
                // Validar disponibilidad
                if (!product.isEnabled()) {
                    response = "El producto no se encuentra disponible";
                    break;
                }
                if (buyDetailDto.getQuantity() > product.getInInventory()) {
                    response = "La cantidad solicitada no se encuentra en inventario";
                    break;
                }
                if (buyDetailDto.getQuantity() < product.getMin()) {
                    response = "La cantidad solicitada no cumple con el minimo para realizar la compra";
                    break;
                }
                if (buyDetailDto.getQuantity() > product.getMax()) {
                    response = "La cantidad solicitada excede el maximo permitido por compra";
                    break;
                }
            }
        }
        return response;
    }

    /**
     * Método que permite descontar las unidades en inventario luego de cada compra
     *
     * @param buyDetailDtoList lista de productos
     */
    private void deductUnits(List<BuyDetailDto> buyDetailDtoList){
        for(BuyDetailDto buyDetailDto: buyDetailDtoList){
            Optional<Product> productOptional = productRepository.findById(buyDetailDto.getProductId());
            if(productOptional.isPresent()){
                Product product = productOptional.get();
                product.setInInventory(product.getInInventory() - buyDetailDto.getQuantity());
                productRepository.save(product);
            }
        }
    }

}
