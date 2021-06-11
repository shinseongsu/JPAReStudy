package com.spring.reactive.service;

import com.spring.reactive.dto.Cart;
import com.spring.reactive.dto.CartItem;
import com.spring.reactive.repository.CartRepository;
import com.spring.reactive.repository.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService  {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Mono<Cart> addToCart(String cartid, String id) {
        return this.cartRepository.findById(cartid)
                .defaultIfEmpty(new Cart(cartid))
                .flatMap(cart -> cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getItem()
                        .getId().equals(id))
                    .findAny()
                    .map(cartItem -> {
                        cartItem.increment();
                        return Mono.just(cart);
                    })
                    .orElseGet(() ->
                            this.itemRepository.findById(id)
                                .map(CartItem::new)
                                .doOnNext(cartItem -> cart.getCartItems().add(cartItem))
                                .map(cartItem -> cart)))
                .flatMap(this.cartRepository::save);
    }

}
