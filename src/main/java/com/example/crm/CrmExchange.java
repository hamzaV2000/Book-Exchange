package com.example.crm;

public class CrmExchange {



    private String his_username;

    private String his_profile_image_url;

    private String his_book_cover_image;

    private Long his_owned_book_id;

    private Long his_book_id;

    public CrmExchange() {
    }

    public CrmExchange(String his_username, String his_profile_image_url, String his_book_cover_image, Long his_owned_book_id, Long his_book_id) {
        this.his_username = his_username;
        this.his_profile_image_url = his_profile_image_url;
        this.his_book_cover_image = his_book_cover_image;
        this.his_owned_book_id = his_owned_book_id;
        this.his_book_id = his_book_id;
    }

    public String getHis_username() {
        return his_username;
    }

    public void setHis_username(String his_username) {
        this.his_username = his_username;
    }

    public String getHis_profile_image_url() {
        return his_profile_image_url;
    }

    public void setHis_profile_image_url(String his_profile_image_url) {
        this.his_profile_image_url = his_profile_image_url;
    }

    public Long getHis_owned_book_id() {
        return his_owned_book_id;
    }

    public void setHis_owned_book_id(Long his_owned_book_id) {
        this.his_owned_book_id = his_owned_book_id;
    }

    public Long getHis_book_id() {
        return his_book_id;
    }

    public void setHis_book_id(Long his_book_id) {
        this.his_book_id = his_book_id;
    }

    public String getHis_book_cover_image() {
        return his_book_cover_image;
    }

    public void setHis_book_cover_image(String his_book_cover_image) {
        this.his_book_cover_image = his_book_cover_image;
    }
}
