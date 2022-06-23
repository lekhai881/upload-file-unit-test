package spring_boot.test070322.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring_boot.test070322.models.Product;
import spring_boot.test070322.services.IProductService;

@Controller // Báo cho thằng Java Spring biết rằng thằng class này là Controller
@RequestMapping("api/product") // Định tuyến hay routing cho Controller. Với đường link như này sẽ gửi request đến cho Controller này. Tiếp theo chi tiết là từng method bên trong
//@ResponseBody
public class ProductController {
    @Autowired
    private IProductService iProductService;  //Chứa các function, method để đại diện cho từng request một. Có bao nhiêu request thì có bấy nhiêu method:

    @GetMapping("get-product")
    public ResponseEntity < ? > getProduct(@RequestParam(defaultValue = "") String txtSearch) {
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.getProduct(txtSearch));
    }

    @PostMapping("create-product")
    public ResponseEntity < ? > createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.createProduct(product));
    }

    @PutMapping("update-product/{id}")
    public ResponseEntity<?>updateProduct(@RequestBody Product product, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.updateProduct(product,id));
    }

    @DeleteMapping("delete-product")
    public ResponseEntity<?>deleteProduct(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.deleteProduct(id));
    }

    @GetMapping("pagination-product")
    public ResponseEntity<?>paginationProduct(
            @RequestParam(name = "activePage", required = false,defaultValue = "1") Integer activePage,
            @RequestParam(name = "limit", required = false,defaultValue = "3") Integer limit,
            @RequestParam(name = "txtSearch",required = false,defaultValue = "") String txtSearch
            ){
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.paginationProduct(activePage,limit,txtSearch));
    }
}
