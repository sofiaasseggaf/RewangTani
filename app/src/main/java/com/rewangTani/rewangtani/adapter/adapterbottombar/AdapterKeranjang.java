package com.rewangTani.rewangtani.adapter.adapterbottombar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.data.entity.product.CartWithProduct;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.ui.keranjang.KeranjangViewModel;
import com.rewangTani.rewangtani.utility.Global;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterKeranjang extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<CartWithProduct> keranjangList = new ArrayList<>();
    private final KeranjangViewModel viewModel;
    Context context;

    public AdapterKeranjang(KeranjangViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    public void setData(List<CartWithProduct> newList)
    {
        this.keranjangList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_keranjang, parent, false);
        return new Penampung(view);
    }

    @Override
    public void onBindViewHolder( @NonNull RecyclerView.ViewHolder holder, int position )
    {
        CartWithProduct item = keranjangList.get(position);

        ((Penampung) holder).namaToko.setText(getProfileNameById(item.product.getIdProfil()));
        ((Penampung) holder).namaProduk.setText(item.product.getIdProduk());
        ((Penampung) holder).biayaProduk.setText(item.product.getHargaProduk());
        ((Penampung) holder).jmlPesanan.setText(item.keranjangLocal.quantity);
        ((Penampung)holder).checkBox.setImageResource(checkCheckboxVisibility(item));
        ((Penampung)holder).btnAdd.setOnClickListener(v ->
                viewModel.increase(item.keranjangLocal.productId)
        );
        ((Penampung)holder).btnMinus.setOnClickListener(v ->
                viewModel.decrease(item.keranjangLocal.productId)
        );
        ((Penampung)holder).checkBox.setOnClickListener( v ->
                viewModel.updateIsChecked(item.product.idProduk, !item.keranjangLocal.isChecked)
        );

        if ( item.product.getFoto() != null ){
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+ item.product.getFoto() + "0";
            Picasso.get().load(imageUri).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(((Penampung) holder).imgWarung);
        }
    }

    @Override
    public int getItemCount()
    {
        return keranjangList.size();
    }

    class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView namaToko, namaProduk, biayaProduk, jmlPesanan;
        public ImageView checkBox, imgWarung;
        public FrameLayout btnAdd, btnMinus;
        public Penampung(View itemView)
        {
            super(itemView);
            namaToko = itemView.findViewById(R.id.namaToko);
            namaProduk = itemView.findViewById(R.id.namaProduk);
            biayaProduk = itemView.findViewById(R.id.biayaProduk);
            jmlPesanan = itemView.findViewById(R.id.jmlPesanan);
            checkBox = itemView.findViewById(R.id.checkBox);
            imgWarung = itemView.findViewById(R.id.imgWarung);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            jmlPesanan = itemView.findViewById(R.id.jmlPesanan);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }

        @Override
        public void onClick( View v )
        { }
    }

    private String getProfileNameById(String profileId) {
        List<DatumProfil> listDataProfil = viewModel.getProfiles().getData();
        for (DatumProfil profil : listDataProfil) {
            if (profil.getIdProfile().equalsIgnoreCase(profileId)) {
                return profil.getNamaDepan() + " " + profil.getNamaBelakang();
            }
        }
        return Global.STRING_DEFAULT_VALUE;
    }

    private int checkCheckboxVisibility(CartWithProduct item)
    {
        if (item.keranjangLocal.isChecked)
        {
            return R.drawable.ic_checkbox_unchecked;
        } else {
            return R.drawable.ic_checkbox_checked;
        }
    }
}
