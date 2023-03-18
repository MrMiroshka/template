package ru.miroshka.market.core.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.miroshka.market.api.exceptions.ResourceNotFoundException;
import ru.miroshka.market.core.data.Order;
import ru.miroshka.market.core.data.Product;
import ru.miroshka.market.core.repositories.ProductDao;
import ru.miroshka.market.core.repositories.specifications.ProductsSpecifications;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Lazy
public class ProductService {
    private final ProductDao productDao;
    private final Map<Long, Product> productMap = new HashMap();
    //private final CartService cartService;


    public List<Product> findAll() {
        return productDao.findAll();
    }


    public Page<Product> find(Integer minCost, Integer maxCost, String nameProduct, Integer page, Integer pageSize) {
        Specification<Product> spec = Specification.where(null);
        if (minCost != null) {
            spec = spec.and(ProductsSpecifications.costGreaterOrEqualsThen(minCost));
        }
        if (maxCost != null) {
            spec = spec.and(ProductsSpecifications.costLesserOrEqualsThen(maxCost));
        }
        if (nameProduct != null) {
            spec = spec.and(ProductsSpecifications.nameLike(nameProduct));
        }
        return this.productDao.findAll(spec, PageRequest.of(page - 1, pageSize));

    }

    public List<Product> getProduct(Long id) {
        return this.productDao.findById(id).stream().toList();
    }

    public Optional<Product> findById(Long id) {
        // return productDao.findById(id);
        //productMap.put(product.getId(), person);
        //productMap.get(key);
        if (productMap.containsKey(id)) {
            return Optional.ofNullable(productMap.get(id));
        } else {
            Optional<Product> product = Optional.ofNullable(productDao.findById(id).orElseThrow(() -> new ResourceNotFoundException
                    ("Такой продукт не найден  id =  " + id)));
            productMap.put(product.get().getId(), product.get());
            return product;
        }
    }

    public Product addProduct(Product product) {
        return this.productDao.save(product);
    }

    public void deleteById(Long id) {
        this.productDao.deleteById(id);
        //this.cartService.delProductCartById(id); TODO допилить удаление из корзины
    }


    @Transactional
    public void changeProduct(Product product) {
        Product productChange = this.productDao.findById(product.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Такой продукт не найден id - " + product.getId()));
        if (product.getCost() != null && product.getCost().compareTo(new BigDecimal(0)) > 0) {
            productChange.setCost(product.getCost());
        }
        if (!product.getTitle().isEmpty() && product.getTitle().length() > 3) {
            productChange.setTitle(product.getTitle());
        }
    }
}
