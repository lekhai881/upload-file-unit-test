package spring_boot.test070322.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot.test070322.models.Product;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository< Product,Long > {
	//    @Query("select p from Product p where p.name like %?1%")
//    List<Product> searchProduct(String name);
    List < Product > findByNameContaining(String name) ;
    List<Product>findByName(String name);
    Page <Product> findByNameContaining(String name, Pageable pageable);

}
