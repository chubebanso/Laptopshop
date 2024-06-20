package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hoidanit.laptopshop.domain.Product;
import java.util.List;

//crud: create, read, update, delete
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product eric);

    void deleteById(long id);

    Product findById(long id);
}
