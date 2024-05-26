package com.lautadev.cart_microservice.repository;

import com.lautadev.cart_microservice.dto.ProductsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="products-service")
public interface IProductsAPIClient {

    @GetMapping("/products/findProductsByIds")
    public List<ProductsDTO> findProductsByIds(@RequestParam List<Long> idProducts);
}
