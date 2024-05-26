package com.lautadev.cart_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long id;
    private List<Long> idProducts;
    private List<ProductsDTO> listProducts;
    private double totalPrice;

}
