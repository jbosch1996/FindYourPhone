package com.adades.findyourphone.entities;

/**
 * Created by alu2012081 on 09/03/2018.
 */

public class Phone {

    private String id;
    private String name;
    private String imageUri;

    public Phone() {
    }

    public Phone(String id, String name, String imageUri) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
    }
    public Phone(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
