package com.lautadev.cart_microservice.service;

import com.lautadev.cart_microservice.dto.CartDTO;
import com.lautadev.cart_microservice.model.Cart;

import java.util.List;

public interface ICartService {
    public void saveCart(List<Long> idProducts);
    public List<CartDTO> getCarts();
    public Cart findCart(Long id);
    public void deleteCart(Long id);
    public void editCart(Long id, Cart cart);
}
