package com.rewangTani.rewangtani.data.entity.product;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CartWithProduct {

    @Embedded
    public DatumKeranjangLocal keranjangLocal;

    @Relation(
            parentColumn = "productId",
            entityColumn = "idProduk"
    )
    public DatumProduk product;
}
