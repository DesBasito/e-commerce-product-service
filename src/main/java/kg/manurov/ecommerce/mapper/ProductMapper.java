package kg.manurov.ecommerce.mapper;

import jakarta.validation.constraints.NotNull;
import kg.manurov.ecommerce.dto.CategoryDto;
import kg.manurov.ecommerce.dto.ProductPurchaseResponse;
import kg.manurov.ecommerce.dto.ProductRequest;
import kg.manurov.ecommerce.dto.ProductResponse;
import kg.manurov.ecommerce.entity.Category;
import kg.manurov.ecommerce.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        if (request == null){
            return null;
        }
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(Category.builder().id(request.categoryId()).build())
                .build();
    }

    public ProductResponse toDto(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                new CategoryDto(product.getCategory().getId(),
                        product.getCategory().getName(),
                        product.getCategory().getDescription())
        );
    }

    public ProductPurchaseResponse toProductPurchase(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
