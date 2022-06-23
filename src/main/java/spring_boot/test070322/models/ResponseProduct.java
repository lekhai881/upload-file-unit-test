package spring_boot.test070322.models;

import lombok.Data;

@Data
public class ResponseProduct {
    String status;
    String message;
    Object data;
    int totalPage;
    int activePage;

    public ResponseProduct() {
    }

    public ResponseProduct(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseProduct(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseProduct(String status, String message, Object data, int totalPage, int activePage) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.totalPage = totalPage;
        this.activePage = activePage;
    }
}
