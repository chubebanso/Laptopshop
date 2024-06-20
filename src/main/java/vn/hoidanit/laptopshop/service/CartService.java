package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.eclipse.tags.shaded.org.apache.regexp.recompile;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartRepository;

@Service
public class CartService {
    final private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart findByUser(User User) {
        return this.cartRepository.findByUser(User);
    }

    public Cart saveCart(Cart cart) {
        return this.cartRepository.save(cart);
    }

    public Cart findById(long id) {
        return this.cartRepository.findById(id);
    }

    public void deleteById(long id) {
        this.cartRepository.deleteCartById(id);
    }
}
