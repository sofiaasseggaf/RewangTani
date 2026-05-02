package com.rewangTani.rewangtani.model.wilayah;

public class District {
    private String id;
    private String id_kabupaten;
    private String name;

    public String getId() { return id; }
    public String getIdKabupaten() { return id_kabupaten; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}