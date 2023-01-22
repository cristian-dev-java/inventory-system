package my.projects.inventorysystem.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import my.projects.inventorysystem.dto.CustomPageResponse;
import my.projects.inventorysystem.dto.ProductDto;
import my.projects.inventorysystem.model.Buy;
import my.projects.inventorysystem.repository.BuyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/buy")
public class BuyController {

    private final BuyRepository buyRepository;

    @GetMapping(value = "/buy")
    public ResponseEntity<List<Buy>> getBuy() {
        return ResponseEntity.ok(buyRepository.findAll());
    }

    @PostMapping(value = "/buy")
    public ResponseEntity<Void> saveBuy(@RequestBody Buy buy) {
        buyRepository.save(buy);
        return ResponseEntity.ok().build();
    }

}
