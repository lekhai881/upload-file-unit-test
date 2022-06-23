package spring_boot.test070322.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import spring_boot.test070322.models.Product;
import spring_boot.test070322.models.ResponseProduct;

@Service
@Primary
public interface IProductService{
    ResponseProduct createProduct(Product product) ;

    ResponseProduct getProduct(String txtSearch);

    ResponseProduct updateProduct(Product product, Long id);

    ResponseProduct deleteProduct(Long id);

    ResponseProduct paginationProduct(Integer activePage, Integer limit, String txtSearch);
}
