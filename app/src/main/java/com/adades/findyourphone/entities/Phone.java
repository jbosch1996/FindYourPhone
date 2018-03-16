package com.adades.findyourphone.entities;

/**
 * Created by alu2012081 on 09/03/2018.
 */
// NO PODEM PARLAR MACHETE AL MACHOTE
public class Phone {

    private String id;
    private String name;
    private String imageUri;
    private String cpu;
    private String storage;
    private String ram;
    private String so;

    public Phone() {
    }

    public Phone(String id, String name, String imageUri, String cpu, String storage, String ram, String so) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
        this.cpu = cpu;
        this.storage = storage;
        this.ram = ram;
        this.so = so;
    }

    public Phone(String id, String name,String imageUri) {
        this.id = id;
        this.name = name;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }
}
