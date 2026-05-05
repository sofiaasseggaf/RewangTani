package com.rewangTani.rewangtani.data.entity.product;

import com.rewangTani.rewangtani.data.local.dao.WarungBppDao;
import com.rewangTani.rewangtani.data.local.dao.WarungSewaMesinDao;
import com.rewangTani.rewangtani.data.local.dao.WarungTenagaKerjaDao;
import com.rewangTani.rewangtani.utility.Global;

public class ProductResolver
{

    private final WarungBppDao warungBppDao;
    private final WarungSewaMesinDao warungSewaMesinDao;
    private final WarungTenagaKerjaDao warungTenagaKerjaDao;

    public ProductResolver(WarungBppDao warungBppDao, WarungSewaMesinDao warungSewaMesinDao, WarungTenagaKerjaDao warungTenagaKerjaDao)
    {
        this.warungBppDao = warungBppDao;
        this.warungSewaMesinDao = warungSewaMesinDao;
        this.warungTenagaKerjaDao  =warungTenagaKerjaDao;
    }
    public String resolveName(DatumProduk product)
    {
        String productId = product.getIdProduk();
        switch (product.getIdTipeProduk())
        {
            case "BIBIT":
            case "PUPUK":
            case "PESTISIDA":
                String a = warungBppDao.getNameByProductId(productId);
                return a != null ? a : Global.STRING_DEFAULT_VALUE;

            case "SEWA MESIN":
                String d = warungSewaMesinDao.getNameByProductId(productId);
                return d != null ? d : Global.STRING_DEFAULT_VALUE;

            case "TENAGA KERJA":
                String e = warungTenagaKerjaDao.getNameByProductId(productId);
                return e != null ? e : Global.STRING_DEFAULT_VALUE;
        }

        return null;
    }
}
