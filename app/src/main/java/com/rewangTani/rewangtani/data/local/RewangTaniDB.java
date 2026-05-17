package com.rewangTani.rewangtani.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.rewangTani.rewangtani.data.entity.akun.DatumAkun;
import com.rewangTani.rewangtani.data.entity.inbox.DatumInbox;
import com.rewangTani.rewangtani.data.entity.product.DatumKeranjangLocal;
import com.rewangTani.rewangtani.data.entity.product.DatumProduk;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.entity.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.data.entity.rencanatanam.DraftRencanaTanam;
import com.rewangTani.rewangtani.data.entity.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.data.entity.warungbpp.DatumBpp;
import com.rewangTani.rewangtani.data.entity.warungsewamesin.DatumSewaMesin;
import com.rewangTani.rewangtani.data.entity.warungtenagakerja.DatumTenagaKerja;
import com.rewangTani.rewangtani.data.local.dao.AkunDao;
import com.rewangTani.rewangtani.data.local.dao.DraftRencanaTanamDao;
import com.rewangTani.rewangtani.data.local.dao.InboxDao;
import com.rewangTani.rewangtani.data.local.dao.KeranjangDao;
import com.rewangTani.rewangtani.data.local.dao.ProdukDao;
import com.rewangTani.rewangtani.data.local.dao.ProfilDao;
import com.rewangTani.rewangtani.data.local.dao.ProfilLahanDao;
import com.rewangTani.rewangtani.data.local.dao.RencanaTanamDao;
import com.rewangTani.rewangtani.data.local.dao.SudahTanamDao;
import com.rewangTani.rewangtani.data.local.dao.WarungBppDao;
import com.rewangTani.rewangtani.data.local.dao.WarungSewaMesinDao;
import com.rewangTani.rewangtani.data.local.dao.WarungTenagaKerjaDao;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;

import java.util.concurrent.Executors;

@Database(
        entities = {
                DatumAkun.class,
                DatumProfil.class,
                DatumProfilLahan.class,
                DatumRencanaTanam.class,
                DraftRencanaTanam.class,
                DatumSudahTanam.class,
                DatumProduk.class,
                DatumKeranjangLocal.class,
                DatumBpp.class,
                DatumSewaMesin.class,
                DatumTenagaKerja.class,
                DatumInbox.class
        },
        version = 4,
        exportSchema = false
)
public abstract class RewangTaniDB extends RoomDatabase
{
        private static volatile RewangTaniDB INSTANCE;
        public abstract AkunDao akunDao();
        public abstract ProfilDao profilDao();
        public abstract ProfilLahanDao profilLahanDao();
        public abstract RencanaTanamDao rencanaTanamDao();
        public abstract DraftRencanaTanamDao draftDao();
        public abstract SudahTanamDao sudahTanamDao();
        public abstract ProdukDao produkDao();
        public abstract KeranjangDao keranjangDao();
        public abstract WarungBppDao warungBppDao();
        public abstract WarungSewaMesinDao warungSewaMesinDao();
        public abstract WarungTenagaKerjaDao warungTenagaKerjaDao();
        public abstract InboxDao inboxDao();

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
        public void clearAllTables()
        {
                Executors.newSingleThreadExecutor().execute(() -> {
                        akunDao().deleteAll();
                        profilDao().deleteAll();
                        profilLahanDao().deleteAll();
                        rencanaTanamDao().deleteAll();
                        draftDao().clearDraft();
                        sudahTanamDao().deleteAll();
                        produkDao().deleteAll();
                        keranjangDao().deleteAll();
                        warungBppDao().deleteAll();
                        warungSewaMesinDao().deleteAll();
                        warungTenagaKerjaDao().deleteAll();
                        inboxDao().deleteAll();
                });
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
