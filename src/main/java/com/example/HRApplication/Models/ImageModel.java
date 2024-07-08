package com.example.HRApplication.Models;


import jakarta.persistence.*;

@Entity
@Table(name = "image_model")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @Column(length = 5000000)
    private byte[] ImgByte;

    public ImageModel(){

    }

    public ImageModel(String name, String type, byte[] imgByte) {
        this.name = name;
        this.type = type;
        ImgByte = imgByte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImgByte() {
        return ImgByte;
    }

    public void setImgByte(byte[] imgByte) {
        ImgByte = imgByte;
    }
}
