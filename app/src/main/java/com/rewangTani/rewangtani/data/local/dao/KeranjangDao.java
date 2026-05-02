package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.rewangTani.rewangtani.data.entity.product.CartWithProduct;
import com.rewangTani.rewangtani.data.entity.product.DatumKeranjangLocal;

import java.util.List;

@Dao
public interface KeranjangDao
{

    @Transaction
    @Query("SELECT * FROM keranjang")
    LiveData<List<CartWithProduct>> getAllCarts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DatumKeranjangLocal cart);

    @Query("UPDATE keranjang SET quantity = quantity + 1 WHERE productId = :id")
    void increase(String id);

    @Query("UPDATE keranjang SET quantity = quantity - 1 WHERE productId = :id AND quantity > 1")
    void decrease(String id);

    @Query("UPDATE keranjang SET isChecked = :check WHERE productId = :id")
    void updateIsChecked(String id, boolean check);

    @Query("DELETE FROM keranjang WHERE productId = :id")
        void delete(String id);

    @Query("SELECT EXISTS(SELECT 1 FROM keranjang WHERE productId = :id)")
    boolean isExist(String id);

    @Query("SELECT quantity FROM keranjang WHERE productId = :id LIMIT 1")
    int getQuantity(String id);

    @Query("DELETE FROM keranjang")
    void deleteAll();
}
