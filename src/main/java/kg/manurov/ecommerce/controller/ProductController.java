package kg.manurov.ecommerce.controller;

import jakarta.validation.Valid;
import kg.manurov.ecommerce.dto.ProductPurchaseRequest;
import kg.manurov.ecommerce.dto.ProductPurchaseResponse;
import kg.manurov.ecommerce.dto.ProductRequest;
import kg.manurov.ecommerce.dto.ProductResponse;
import kg.manurov.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody @Valid ProductRequest productRequest){
        return ResponseEntity.ok(service.createProduct(productRequest));
    }

    @PostMapping("/purchases")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<ProductPurchaseRequest> request){
        return ResponseEntity.ok(service.purchaseProducts(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findByAll(){
        return ResponseEntity.ok(service.getAllProducts());
    }
}
