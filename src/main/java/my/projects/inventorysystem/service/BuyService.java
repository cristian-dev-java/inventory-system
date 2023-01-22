package my.projects.inventorysystem.service;

import my.projects.inventorysystem.dto.BuyDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BuyService {


    ResponseEntity<List<BuyDto>> getBuys();
    ResponseEntity<Void> saveBuy(BuyDto buyDto);

}
