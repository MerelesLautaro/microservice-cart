package com.lautadev.cart_microservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {

    private Long code;
    private String name;
    private String brand;
    private double individualPrice;
}
