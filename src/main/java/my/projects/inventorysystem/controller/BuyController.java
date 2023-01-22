package my.projects.inventorysystem.controller;

import lombok.RequiredArgsConstructor;
import my.projects.inventorysystem.dto.BuyDto;
import my.projects.inventorysystem.service.BuyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/buy")
public class BuyController {

    private final BuyService buyService;

    @GetMapping(value = "/buy")
    public ResponseEntity<List<BuyDto>> getBuy() {
        return buyService.getBuys();
    }

    @PostMapping(value = "/buy")
    public ResponseEntity<Void> saveBuy(@RequestBody BuyDto buyDto) {
        return buyService.saveBuy(buyDto);
    }

}
