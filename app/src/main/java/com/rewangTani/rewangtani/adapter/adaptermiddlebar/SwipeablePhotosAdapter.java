package com.rewangTani.rewangtani.adapter.adaptermiddlebar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistmonitor.AdapterListWarungTenagaKerjaMonitor;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class SwipeablePhotosAdapter extends PagerAdapter {

    private Context context;
    private String imageUri;

    public SwipeablePhotosAdapter(Context context, String imageUri) {
        this.context = context;
        this.imageUri = imageUri;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.i("SOFIA", "SwipeablePhotosAdapter - instantiateItem()");
        View itemView = LayoutInflater.from(context).inflate(R.layout.image_carousel_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageView);
        String image0 = imageUri + "0";
        String image1 = imageUri + "1";
        String image2 = imageUri + "2";
        String[] listImageUris = {image0, image1, image2};
        Log.i("SOFIA", "SwipeablePhotosAdapter - instantiateItem()");
        Log.i("SOFIA", "SwipeablePhotosAdapter - instantiateItem() - uri = " + listImageUris[position]);
        Picasso.get().load(listImageUris[position]).networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
