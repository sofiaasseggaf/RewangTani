package com.rewangTani.rewangtani.model.wilayah;

public class City {
    private String id;
    private String id_provinsi;
    private String name;

    public String getId() { return id; }
    public String getIdProvinsi() { return id_provinsi; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}