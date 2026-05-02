package com.rewangTani.rewangtani.model.wilayah;

public class Village {
    private String id;
    private String id_kecamatan;
    private String name;

    public String getId() { return id; }
    public String getIdKecamatan() { return id_kecamatan; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}
