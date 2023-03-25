package com.saiamul.ProductService.services;

import com.saiamul.ProductService.entity.Product;
import com.saiamul.ProductService.error.ProductNotFoundException;
import com.saiamul.ProductService.model.ProductRequest;
import com.saiamul.ProductService.model.ProductResponse;
import com.saiamul.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;


    @Override
    public Long saveProduct(ProductRequest productRequest) {
        log.info("Adding Product..");
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);
        productRepository.save(product);
        log.info("Product Created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("get the product for ProductId {}", productId );
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not Found for Product ID: "+productId, "PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("reduce quantity for Product id {} and number of quantity is {}", productId, quantity);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not Found for Product ID: "+productId, "PRODUCT_NOT_FOUND"));

        if(product.getQuantity() < quantity){
            log.error("Product Quantity less than required quantity, expected : {} But available is {} ", quantity, product.getQuantity());
            throw new ProductNotFoundException("Product Quantity less than required quantity expected : "+quantity +" But available is "+product.getQuantity(), "QUANTITY_INSUFFICIENT");
        }

        product.setQuantity(product.getQuantity()-quantity);

        productRepository.save(product);

        log.info("Product id: {} quantity reduced from {} to {}", productId, product.getQuantity()+quantity, product.getQuantity());
    }
}
