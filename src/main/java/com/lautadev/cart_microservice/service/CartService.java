package com.lautadev.cart_microservice.service;

import com.lautadev.cart_microservice.dto.CartDTO;
import com.lautadev.cart_microservice.dto.ProductsDTO;
import com.lautadev.cart_microservice.model.Cart;
import com.lautadev.cart_microservice.repository.ICartRepository;
import com.lautadev.cart_microservice.repository.IProductsAPIClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService{
    @Autowired
    private ICartRepository cartRepo;

    @Autowired
    private IProductsAPIClient productAPI;

    @Override
    @CircuitBreaker(name="products-service", fallbackMethod = "fallbackGetProducts")
    @Retry(name = "products-service")
    public void saveCart(List<Long> idProducts) {
        List<ProductsDTO> listProductsDTO = productAPI.findProductsByIds(idProducts);
        double totalPriceProducts = 0;

        for(ProductsDTO product: listProductsDTO){
            totalPriceProducts += product.getIndividualPrice();
        }

        Cart cart = new Cart();
        cart.setTotalPrice(totalPriceProducts);
        cart.setIdProducts(idProducts);

        cartRepo.save(cart);

    }

    public String fallbackGetProducts(Throwable throwable){
        return "Fail";
    }

    @Override
    @CircuitBreaker(name="products-service", fallbackMethod = "fallbackGetProductsForCarts")
    @Retry(name = "products-service")
    public List<CartDTO> getCarts() {
        List<Cart> cartList = cartRepo.findAll();
        List<CartDTO> cartDTOList = new LinkedList<>();

        // Recorro todos los carritos y guardo en un Array todos los IDs de los productos.
        List<Long> allProductIds = new ArrayList<>();
        for (Cart cart : cartList) {
            allProductIds.addAll(cart.getIdProducts());
        }

        // Llamo a la API una vez para obtener todos los productos necesarios.
        List<ProductsDTO> allProducts = productAPI.findProductsByIds(allProductIds);

        // Mapeo los productos obtenidos por su ID.
        Map<Long, ProductsDTO> productsMap = allProducts.stream()
                .collect(Collectors.toMap(ProductsDTO::getCode, product -> product));

        for (Cart cart : cartList) {
            List<ProductsDTO> listProductsDTO = cart.getIdProducts().stream()
                    .map(productsMap::get)
                    .collect(Collectors.toList());

            cartDTOList.add(new CartDTO(cart,listProductsDTO));
        }

        return cartDTOList;
    }

    public List<CartDTO> fallbackGetProductsForCarts(Throwable throwable) {
        return new ArrayList<>();
    }

    @Override
    @CircuitBreaker(name="products-service", fallbackMethod = "fallbackGetProductsForFindCarts")
    @Retry(name = "products-service")
    public CartDTO findCart(Long id) {
        Cart cart = cartRepo.findById(id).orElse(null);

        assert cart != null;
        List<ProductsDTO> listProducts = productAPI.findProductsByIds(cart.getIdProducts());

        return new CartDTO(cart,listProducts);
    }

    public CartDTO fallbackGetProductsForFindCarts(Long id, Throwable throwable) {
        return new CartDTO();
    }

    @Override
    public void deleteCart(Long id) {
        cartRepo.deleteById(id);
    }

    @Override
    public void editCart(Long id, Cart cart) {
        Cart cartEdit = cartRepo.findById(id).orElse(null);

        assert cartEdit != null;
        cartEdit.setTotalPrice(cart.getTotalPrice());
        cartEdit.setIdProducts(cart.getIdProducts());

        cartRepo.save(cartEdit);

    }
}
