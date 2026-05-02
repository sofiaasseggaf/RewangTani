package com.rewangTani.rewangtani.data.entity.product;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "keranjang")
public class DatumKeranjangLocal
{

    @PrimaryKey
    @NonNull
    public String productId;
    public int quantity;
    public boolean isChecked;

    public DatumKeranjangLocal(String productId, int quantity, boolean isChecked) {
        this.productId = productId;
        this.quantity = quantity;
        this.isChecked = isChecked;
    }

}
