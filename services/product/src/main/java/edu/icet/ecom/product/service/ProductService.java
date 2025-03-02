package edu.icet.ecom.product.service;

import edu.icet.ecom.product.dto.ProductPurchaseRequest;
import edu.icet.ecom.product.dto.ProductPurchaseResponse;
import edu.icet.ecom.product.dto.ProductRequest;
import edu.icet.ecom.product.dto.ProductResponse;
import edu.icet.ecom.product.repository.ProductRepository;
import edu.icet.ecom.product.util.customException.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(@Valid ProductRequest productRequest) {
        return productRepository.save(
                productMapper.toProduct(productRequest))
                .getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(
            @Valid List<ProductPurchaseRequest> productPurchaseRequests) {
        // user requested product Id list
        var productIds = productPurchaseRequests
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        // available product Id list
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("Requested Product list contains unavailable products.");
        }

        // sort the user requested product id list
        var storedRequests = productPurchaseRequests
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        // to store the Response
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        // if control is here, all requested products are available
        for (int i = 0; i < storedProducts.size(); i++) {
            // both are in sorted order
            //so both will get the same id product
            var product = storedProducts.get(i);
            var requestedProduct = storedRequests.get(i);

            if (product.getAvailableQuantity() < requestedProduct.quantity()) {
                throw new ProductPurchaseException("Requested quantity is more than available quantity");
            }

            // update available quantity
            product.setAvailableQuantity(product.getAvailableQuantity() - requestedProduct.quantity());
            productRepository.save(product);

            // save the response
            purchasedProducts.add(productMapper
                    .toProductPurchaseResponse(product, requestedProduct.quantity()));
        }
        return null;
    }

    public ProductResponse getProduct(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
}
