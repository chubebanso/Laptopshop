package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;

@Service
public class ProductService {

    final private ProductRepository productRepository;
    final private UserService userService;
    final private CartService cartService;
    final private CartDetailRepository cartDetailRepository;

    public ProductService(ProductRepository productRepository, CartService cartService, UserService userService,
            CartDetailRepository cartDetailRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.cartDetailRepository = cartDetailRepository;

    }

    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    public Product handleSave(Product product) {
        Product chubebanso = this.productRepository.save(product);
        return chubebanso;
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public Product getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void handleAddProductoCart(String email, long productId, HttpSession session) {
        User user = this.userService.getAllUsersByEmail(email);
        Cart cart = this.cartService.findByUser(user);
        if (cart == null) {
            Cart otheCart = new Cart();
            otheCart.setSum(0);
            otheCart.setUser(user);
            cart = this.cartService.saveCart(otheCart);
        }
        Product product = this.productRepository.findById(productId);
        if (product != null) {
            CartDetail oldCartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
            if (oldCartDetail == null) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setPrice(product.getPrice());
                cartDetail.setQuantity(1);
                cartDetail.setProduct(product);
                this.cartDetailRepository.save(cartDetail);
                long sum = cart.getSum() + 1;
                cart.setSum(sum);
                cart = this.cartService.saveCart(cart);
                session.setAttribute("sum", sum);
            } else {
                oldCartDetail.setQuantity(oldCartDetail.getQuantity() + 1);
                this.cartDetailRepository.save(oldCartDetail);
            }
        }
    }
}
