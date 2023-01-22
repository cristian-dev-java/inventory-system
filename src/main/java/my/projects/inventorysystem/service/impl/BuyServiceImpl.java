package my.projects.inventorysystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.projects.inventorysystem.dto.BuyDetailDto;
import my.projects.inventorysystem.dto.BuyDto;
import my.projects.inventorysystem.model.Buy;
import my.projects.inventorysystem.model.BuyProduct;
import my.projects.inventorysystem.model.Product;
import my.projects.inventorysystem.repository.BuyRepository;
import my.projects.inventorysystem.service.BuyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuyServiceImpl implements BuyService {

    private final BuyRepository buyRepository;

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
    public ResponseEntity<Void> saveBuy(BuyDto buyDto) {
        log.info("Init saveBuy with: {}", buyDto);
        ResponseEntity<Void> response;
        try {
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
            response = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("Error in saveBuy with messageError: {}", e.getMessage());
            response = ResponseEntity.internalServerError().build();
        }
        return response;
    }

}
