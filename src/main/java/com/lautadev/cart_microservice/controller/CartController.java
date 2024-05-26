package com.lautadev.cart_microservice.controller;

import com.lautadev.cart_microservice.dto.CartDTO;
import com.lautadev.cart_microservice.model.Cart;
import com.lautadev.cart_microservice.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService cartServ;

    @PostMapping("/save")
    public String saveCart(@RequestBody CartDTO cartDTO){
        cartServ.saveCart(cartDTO.getIdProducts());
        return "Cart saved successfully";
    }

    @GetMapping("/get")
    public List<CartDTO> getCarts(){
        return cartServ.getCarts();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCart(@PathVariable Long id){
        cartServ.deleteCart(id);
        return "Cart deleted";
    }

    @PutMapping("/edit")
    public Cart editCart(@RequestBody Cart cart){
        cartServ.editCart(cart.getId(), cart);
        return cartServ.findCart(cart.getId());
    }

}