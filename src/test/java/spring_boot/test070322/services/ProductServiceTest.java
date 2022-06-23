package spring_boot.test070322.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import spring_boot.test070322.models.Product;
import spring_boot.test070322.models.ResponseProduct;
import spring_boot.test070322.repository.IProductRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductService productServiceUnderTest;

    @BeforeEach
    void setUp() {
        productServiceUnderTest = new ProductService();
        productServiceUnderTest.repository = mock(IProductRepository.class);
    }

    @Test
    void testGetProduct() {
        // Setup
        final ResponseProduct expectedResult = new ResponseProduct("status", "message", "data", 0, 0);
        when(productServiceUnderTest.repository.findByNameContaining("txtSearch"))
                .thenReturn(Arrays.asList(new Product(0L, "name")));

        // Run the test
        final ResponseProduct result = productServiceUnderTest.getProduct("txtSearch");

        // Verify the results
        org.junit.jupiter.api.Assertions.assertNotNull(result);
    }

    @Test
    void testGetProduct_IProductRepositoryReturnsNoItems() {
        // Setup
        final ResponseProduct expectedResult = new ResponseProduct("status", "message", "data", 0, 0);
        when(productServiceUnderTest.repository.findByNameContaining("txtSearch")).thenReturn(Collections.emptyList());

        // Run the test
        final ResponseProduct result = productServiceUnderTest.getProduct("txtSearch");

        // Verify the results
        org.junit.jupiter.api.Assertions.assertNotNull(result);
    }

    @Test
    void testCreateProduct() {
        // Setup
        final Product product = new Product(0L, "name");
        final ResponseProduct expectedResult = new ResponseProduct("status", "message", "data", 0, 0);
        when(productServiceUnderTest.repository.findByName("name")).thenReturn(Arrays.asList(new Product(0L, "name")));
        when(productServiceUnderTest.repository.save(new Product(0L, "name"))).thenReturn(new Product(0L, "name"));

        // Run the test
        final ResponseProduct result = productServiceUnderTest.createProduct(product);

        // Verify the results
        org.junit.jupiter.api.Assertions.assertNotNull(result);
    }

    @Test
    void testCreateProduct_IProductRepositoryFindByNameReturnsNoItems() {
        // Setup
        final Product product = new Product(0L, "name");
        final ResponseProduct expectedResult = new ResponseProduct("status", "message", "data", 0, 0);
        when(productServiceUnderTest.repository.findByName("name")).thenReturn(Collections.emptyList());
        when(productServiceUnderTest.repository.save(new Product(0L, "name"))).thenReturn(new Product(0L, "name"));

        // Run the test
        final ResponseProduct result = productServiceUnderTest.createProduct(product);

        // Verify the results
        org.junit.jupiter.api.Assertions.assertNotNull(result);
    }

    @Test
    void testUpdateProduct() {
        // Setup
        final Product product = new Product(0L, "name");
        final ResponseProduct expectedResult = new ResponseProduct("status", "message", "data", 0, 0);
        when(productServiceUnderTest.repository.findById(0L)).thenReturn(Optional.of(new Product(0L, "name")));
        when(productServiceUnderTest.repository.save(new Product(0L, "name"))).thenReturn(new Product(0L, "name"));

        // Run the test
        final ResponseProduct result = productServiceUnderTest.updateProduct(product, 0L);

        // Verify the results
        org.junit.jupiter.api.Assertions.assertNotNull(result);
    }

    @Test
    void testUpdateProduct_IProductRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Product product = new Product(0L, "name");
        final ResponseProduct expectedResult = new ResponseProduct("status", "message", "data", 0, 0);
        when(productServiceUnderTest.repository.findById(0L)).thenReturn(Optional.empty());
        when(productServiceUnderTest.repository.save(new Product(0L, "name"))).thenReturn(new Product(0L, "name"));

        // Run the test
        final ResponseProduct result = productServiceUnderTest.updateProduct(product, 0L);

        // Verify the results
        org.junit.jupiter.api.Assertions.assertNotNull(result);
    }

    @Test
    void testDeleteProduct() {
        // Setup
        final ResponseProduct expectedResult = new ResponseProduct("status", "message", "data", 0, 0);

        // Run the test
        final ResponseProduct result = productServiceUnderTest.deleteProduct(0L);

        // Verify the results
        org.junit.jupiter.api.Assertions.assertNotNull(result);
        verify(productServiceUnderTest.repository).deleteById(0L);
    }

    @Test
    void testPaginationProduct() {
        // Setup
        final ResponseProduct expectedResult = new ResponseProduct("status", "message", "data", 0, 0);

        // Configure IProductRepository.findByNameContaining(...).
        final Page < Product > products = new PageImpl <>(Arrays.asList(new Product(0L, "name")));
        when(productServiceUnderTest.repository.findByNameContaining(eq("txtSearch"), any(Pageable.class)))
                .thenReturn(products);

        // Run the test
        final ResponseProduct result = productServiceUnderTest.paginationProduct(1, 1, "txtSearch");

        // Verify the results
        org.junit.jupiter.api.Assertions.assertNotNull(result);
    }

    @Test
    void testPaginationProduct_IProductRepositoryReturnsNoItems() {
        // Setup
        final ResponseProduct expectedResult = new ResponseProduct("status", "message", "data", 0, 0);
        when(productServiceUnderTest.repository.findByNameContaining(eq("txtSearch"), any(Pageable.class)))
                .thenReturn(new PageImpl <>(
                        Collections.emptyList()));

        // Run the test
        final ResponseProduct result = productServiceUnderTest.paginationProduct(1, 1, "txtSearch");

        // Verify the results
        org.junit.jupiter.api.Assertions.assertNotNull(result);
    }
}
