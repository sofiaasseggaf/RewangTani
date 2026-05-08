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
import com.rewangTani.rewangtani.data.entity.product.CartItemUI;
import com.rewangTani.rewangtani.ui.keranjang.KeranjangViewModel;
import com.rewangTani.rewangtani.utility.TextUtil;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterKeranjang extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<CartItemUI > keranjangList = new ArrayList<>();
    private final KeranjangViewModel viewModel;
    Context context;

    public AdapterKeranjang(KeranjangViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    public void setData(List<CartItemUI> newList)
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
        CartItemUI item = keranjangList.get(position);

        ((Penampung) holder).namaToko.setText(item.profile.getNamaDepan());
        ((Penampung) holder).namaProduk.setText(item.productName);
        ((Penampung) holder).biayaProduk.setText(TextUtil.checkDesimal(String.valueOf(item.product.getHargaProduk())));
        ((Penampung) holder).jmlPesanan.setText(String.valueOf(item.cart.quantity));
        ((Penampung)holder).checkBox.setImageResource(checkCheckboxVisibility(item));
        ((Penampung)holder).btnAdd.setOnClickListener(v ->
                viewModel.increase(item.cart.productId)
        );
        ((Penampung)holder).btnMinus.setOnClickListener(v ->
                viewModel.decrease(item.cart.productId)
        );
        ((Penampung)holder).checkBox.setOnClickListener( v ->
                viewModel.updateIsChecked(item.product.idProduk, !item.cart.isChecked)
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

    private int checkCheckboxVisibility(CartItemUI item)
    {
        if (item.cart.isChecked)
        {
            return R.drawable.ic_checkbox_checked;
        } else {
            return R.drawable.ic_checkbox_unchecked;
        }
    }
}
