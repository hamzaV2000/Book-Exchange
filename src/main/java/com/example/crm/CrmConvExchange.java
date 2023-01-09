package com.example.crm;

public class CrmConvExchange {

    private Long exchange_id;

    private String initiator;
    private String username;
    private String image;

    private Long my_book_id;

    private Long his_book_id;

    public CrmConvExchange(Long exchange_id, String initiator, String username, String image, Long my_book_id, Long his_book_id) {
        this.exchange_id = exchange_id;
        this.initiator = initiator;
        this.username = username;
        this.image = image;
        this.my_book_id = my_book_id;
        this.his_book_id = his_book_id;
    }

    public CrmConvExchange() {
    }

    public Long getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(Long exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getMy_book_id() {
        return my_book_id;
    }

    public void setMy_book_id(Long my_book_id) {
        this.my_book_id = my_book_id;
    }

    public Long getHis_book_id() {
        return his_book_id;
    }

    public void setHis_book_id(Long his_book_id) {
        this.his_book_id = his_book_id;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }
}
