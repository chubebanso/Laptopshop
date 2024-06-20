package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.CartDetailService;
import vn.hoidanit.laptopshop.service.CartService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CartController {
    private final CartDetailService cartDetailService;
    private final CartService cartService;

    public CartController(CartDetailService cartDetailService, CartService cartService) {
        this.cartDetailService = cartDetailService;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        long cartTotal = 0;
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        List<CartDetail> cartDetails = this.cartDetailService.getCartDetail(email);
        for (CartDetail cartDetail : cartDetails) {
            cartTotal += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("cartTotal", cartTotal);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(Model model, @PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        CartDetail cartDetail = this.cartDetailService.getCartDetailById(id);
        Cart cart = cartDetail.getCart();
        this.cartDetailService.deleteById(id);
        if (cart.getSum() > 1) {
            cart.setSum(cart.getSum() - 1);
        } else if (cart.getSum() == 1) {
            this.cartService.deleteById(cart.getId());
        }
        Long sum = (Long) session.getAttribute("sum");
        if (sum > 1) {
            session.setAttribute("sum", sum - 1);
        } else if (sum == 1) {
            session.setAttribute("sum", 0);
        }
        session.setAttribute("sum", cart.getSum() - 1);
        return "redirect:/cart";
    }

}
