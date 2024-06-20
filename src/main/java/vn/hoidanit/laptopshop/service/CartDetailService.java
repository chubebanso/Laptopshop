package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;

@Service
public class CartDetailService {
    final private CartDetailRepository cartDetailRepository;
    final private CartService cartService;
    final private UserService userService;

    public CartDetailService(CartDetailRepository cartDetailRepository, CartService cartService,
            UserService userService) {
        this.cartDetailRepository = cartDetailRepository;
        this.cartService = cartService;
        this.userService = userService;
    }

    public List<CartDetail> getCartDetail(String email) {
        User user = this.userService.getAllUsersByEmail(email);
        Cart cart = this.cartService.findByUser(user);
        List<CartDetail> cartDetails = this.cartDetailRepository.findByCart(cart);
        return cartDetails;

    }

    public CartDetail getCartDetailById(long id) {
        return this.cartDetailRepository.findById(id);
    }

    public void deleteById(long id) {
        this.cartDetailRepository.deleteById(id);
    }
}
