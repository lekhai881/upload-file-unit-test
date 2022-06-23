package spring_boot.test070322.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import spring_boot.test070322.models.Product;
import spring_boot.test070322.models.ResponseProduct;
import spring_boot.test070322.repository.IProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductService implements IProductService {
    @Autowired
    IProductRepository repository;

    @Override
    public ResponseProduct getProduct(String txtSearch) {
        List < Product > foundProduct = repository.findByNameContaining(txtSearch);
        if (!foundProduct.isEmpty()) {
            return new ResponseProduct("ok", "Truy vấn dữ liệu thành công!", foundProduct);
        } else {
            return new ResponseProduct("failed", "Không tìm thấy kết quả với từ: " + txtSearch, "");
        }
    }

    @Override
    public ResponseProduct createProduct(Product product) {
        List < Product > foundProduct = repository.findByName(product.getName().trim());
        if (foundProduct.size() > 0) {
            return new ResponseProduct("failed", "Thông tin đã tồn tại!!", "");
        } else {
            Product product1 = repository.save(product);
            return new ResponseProduct("ok", "Thêm mới thành công", product1);
        }
    }

    @Override
    public ResponseProduct updateProduct(Product product, Long id) {
        Product product1 = repository.findById(id)
                .map(product2 -> {
                    product2.setName(product.getName());
                    return repository.save(product2);
                }).orElseGet(() -> {
                    product.setId(id);
                    return repository.save(product);
                });
        return new ResponseProduct("ok", "Cập nhật thông tin thành công!", product1);

    }

    @Override
    public ResponseProduct deleteProduct(Long id) {
        repository.deleteById(id);
        return new ResponseProduct("ok", "Xóa thông tin thành công!", "");
    }

    @Override
    public ResponseProduct paginationProduct(Integer activePage, Integer limit, String txtSearch) {
        Pageable pageable = PageRequest.of(activePage-1, limit);
        Page < Product > listPagination = repository.findByNameContaining(txtSearch, pageable);
        List < Product > list = listPagination.stream().collect(Collectors.toList());
        int totalPage = listPagination.getTotalPages();
        return new ResponseProduct("ok", "Truy vấn thông tin thành công!", list, totalPage,activePage);
    }
}
