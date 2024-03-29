package com.spring.reactive.controller;

import com.spring.reactive.dto.Cart;
import com.spring.reactive.repository.CartRepository;
import com.spring.reactive.repository.ItemRepository;
import com.spring.reactive.service.CartService;
import com.spring.reactive.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;
    private CartService cartService;
    private InventoryService inventoryService;

    public HomeController(ItemRepository itemRepository,
                          CartRepository cartRepository,
                          CartService cartService,
                          InventoryService inventoryService) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public Mono<Rendering> home() {
        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", this.itemRepository.findAll().doOnNext(System.out::println))
                .modelAttribute("cart", this.cartRepository.findById("My Cart")
                                                        .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }

    @PostMapping("/add/{id}")
    public Mono<String> addToCart(@PathVariable String id) {
        return this.cartService.addToCart("My Cart", id)
                .thenReturn("redirect:/");
    }

    @GetMapping("/search")
    public Mono<Rendering> search(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String description,
                                  @RequestParam boolean useAnd) {
        return Mono.just(Rendering.view("home.html")
            .modelAttribute("results", inventoryService.searchByExample(name, description, useAnd))
            .build());
    }

}
