package com.saiamul.ProductService.services;

import com.saiamul.ProductService.model.ProductRequest;
import com.saiamul.ProductService.model.ProductResponse;

public interface ProductService {

    Long saveProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(long productId, long quantity);
}
