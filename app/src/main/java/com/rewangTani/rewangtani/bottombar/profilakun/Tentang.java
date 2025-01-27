package com.rewangTani.rewangtani.bottombar.profilakun;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPaTentangBinding;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat.DatumChat;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat.ModelChat;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tentang extends AppCompatActivity {

    BottombarPaTentangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pa_tentang);

        binding.img.setOnClickListener(v -> {
            getChat();
        });

    }

    private void getChat()
    {
        List<DatumChat> listChat = new ArrayList<>();
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelChat> dataRT = apiInterface.getDataChat();
        dataRT.enqueue(new Callback<ModelChat>()
        {
            @Override
            public void onResponse( Call<ModelChat> call, retrofit2.Response<ModelChat> response )
            {
                ModelChat modelChat = response.body();
                if ( response.body() != null )
                {
                    for ( int i = 0; i < modelChat.getTotalData(); i++ )
                    {
                        listChat.add(modelChat.getData().get(i));
                    }

                    if ( listChat.size() > 0 )
                    {
                        for ( DatumChat chat : listChat )
                        {
                            deleteChat(chat.getIdChat());
                        }
                    }
                }
            }
            @Override
            public void onFailure( Call<ModelChat> call, Throwable t )
            { }
        });
    }


    public void deleteChat(String idChat){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelChat> dataRT = apiInterface.deleteChat(idChat);
        dataRT.enqueue(new Callback<ModelChat>() {
            @Override
            public void onResponse(Call<ModelChat> call, Response<ModelChat> response) {

            }
            @Override
            public void onFailure(Call<ModelChat> call, Throwable t) {

            }
        });
    }

    public void goToBerandaProfil(){
        Intent a = new Intent(Tentang.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        goToBerandaProfil();
    }
}