package kg.manurov.ecommerce.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import kg.manurov.ecommerce.dto.ProductPurchaseRequest;
import kg.manurov.ecommerce.dto.ProductPurchaseResponse;
import kg.manurov.ecommerce.dto.ProductRequest;
import kg.manurov.ecommerce.dto.ProductResponse;
import kg.manurov.ecommerce.entity.Product;
import kg.manurov.ecommerce.errors.ProductPurchaseException;
import kg.manurov.ecommerce.mapper.ProductMapper;
import kg.manurov.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest productRequest) {
        Product product = mapper.toProduct(productRequest);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        List<Integer> productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more product does not exist :|");
        }

        List<ProductPurchaseRequest> storesRequest = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        List<ProductPurchaseResponse> purchaseProducts = new ArrayList<>();

        for (int i = 0; i < storesRequest.size(); i++) {
            Product product = storedProducts.get(i);
            ProductPurchaseRequest productRequest = storesRequest.get(i);
            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException(String.format("Insufficient stock quantity for product: %s. In store: %f, u want: %f"
                        ,product.getName(), product.getAvailableQuantity(), productRequest.quantity()));
            }

            double newAvailableQnt = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQnt);
            repository.save(product);

            purchaseProducts.add(mapper.toProductPurchase(product, productRequest.quantity()));
        }

        return purchaseProducts;
    }

    public ProductResponse getById(Integer id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(
                () -> new EntityNotFoundException(String.format("Product by ID::%d not found!%n", id))
        );
    }

    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }
}
