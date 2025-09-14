package com.mycompany.stockservice.controller;

import com.mycompany.stockservice.entity.StockEntity;
import com.mycompany.stockservice.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/{code}")
    public boolean stockAvailable(@PathVariable String code){

        Optional<StockEntity> optStock = stockRepository.findByCode(code);
        optStock.orElseThrow(() -> new RuntimeException("Cannot find the product "+code));

        return optStock.get().getQuantity() > 0;
    }
}
