package com.lautadev.cart_microservice.dto;

import com.lautadev.cart_microservice.model.Cart;
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

    public CartDTO(Cart cart, List<ProductsDTO> listProductsDTO) {
        this.id = cart.getId();
        this.idProducts = cart.getIdProducts();
        this.listProducts = listProductsDTO;
        this.totalPrice = cart.getTotalPrice();
    }
}
