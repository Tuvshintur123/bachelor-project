package com.example.myhealth.model;


public class CompactFood {

    private String name;
    private String url;
    private String type;
    private Long id;
    private String description;
    private String brandName;

    public CompactFood(Long id, String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.description = description;
    }

    public CompactFood(Long id, String name, String type, String description, String brandName) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.description = description;
        this.brandName = brandName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
