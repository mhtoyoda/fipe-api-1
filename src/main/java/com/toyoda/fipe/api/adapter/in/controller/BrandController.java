package com.toyoda.fipe.api.adapter.in.controller;

import com.toyoda.fipe.api.application.port.input.FullImportBrandsUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandController {

    private FullImportBrandsUseCase fullImportBrandsUseCase;

    public BrandController(FullImportBrandsUseCase fullImportBrandsUseCase) {
        this.fullImportBrandsUseCase = fullImportBrandsUseCase;
    }

    @GetMapping("/brands/import")
    public ResponseEntity<Void> fullImportBrands() {
        fullImportBrandsUseCase.execute();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
