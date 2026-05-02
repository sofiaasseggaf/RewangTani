package com.rewangTani.rewangtani.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.rewangTani.rewangtani.data.entity.product.DatumKeranjangLocal;
import com.rewangTani.rewangtani.data.entity.product.DatumProduk;
import com.rewangTani.rewangtani.data.local.dao.AkunDao;
import com.rewangTani.rewangtani.data.local.dao.KeranjangDao;
import com.rewangTani.rewangtani.data.local.dao.ProdukDao;
import com.rewangTani.rewangtani.data.local.dao.ProfilDao;
import com.rewangTani.rewangtani.data.entity.akun.DatumAkun;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.local.dao.RencanaTanamDao;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;

@Database(
        entities = {
                DatumAkun.class,
                DatumProfil.class,
                DatumRencanaTanam.class,
                DatumProduk.class,
                DatumKeranjangLocal.class
        },
        version = 3,
        exportSchema = false
)
public abstract class RewangTaniDB extends RoomDatabase
{
        private static volatile RewangTaniDB INSTANCE;
        public abstract AkunDao akunDao();
        public abstract ProfilDao profilDao();
        public abstract RencanaTanamDao rencanaTanamDao();
        public abstract ProdukDao produkDao();
        public abstract KeranjangDao keranjangDao();

        public static RewangTaniDB getInstance(Context context) {
                if (INSTANCE == null) {
                        synchronized (RewangTaniDB.class) {
                                if (INSTANCE == null) {
                                        INSTANCE = Room.databaseBuilder(
                                                        context.getApplicationContext(),
                                                        RewangTaniDB.class,
                                                        "rewangtani_db"
                                                )
                                                .fallbackToDestructiveMigration()
                                                .build();
                                }
                        }
                }
                return INSTANCE;
        }

        @Override
        public void clearAllTables() {

        }

        @NonNull
        @Override
        protected InvalidationTracker createInvalidationTracker() {
                return null;
        }

        @NonNull
        @Override
        protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
                return null;
        }
}
