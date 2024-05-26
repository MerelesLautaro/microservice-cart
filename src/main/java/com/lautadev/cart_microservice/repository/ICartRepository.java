package com.lautadev.cart_microservice.repository;

import com.lautadev.cart_microservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository  extends JpaRepository<Cart, Long> {
}
