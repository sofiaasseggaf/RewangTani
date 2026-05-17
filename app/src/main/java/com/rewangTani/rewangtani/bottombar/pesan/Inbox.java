package com.rewangTani.rewangtani.bottombar.pesan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterInbox;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.warungku.PesananWarungku;
import com.rewangTani.rewangtani.data.entity.inbox.DatumInbox;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.data.repository.ChatRepo;
import com.rewangTani.rewangtani.databinding.BottombarPesanInboxBinding;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat.DatumChat;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.DatumInboxParticipant;
import com.rewangTani.rewangtani.ui.home.Home;
import com.rewangTani.rewangtani.ui.home.HomeViewModel;
import com.rewangTani.rewangtani.ui.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.NavigationManager;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inbox extends AppCompatActivity implements AdapterInbox.OnInboxItemClickListener
{

    BottombarPesanInboxBinding binding;
    HomeViewModel viewModel;
    AdapterInbox itemList;

    private final BroadcastReceiver chatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            getDataProfil();
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesan_inbox);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        initLayout();
        initEvent();
        initObserver();
//        getData();

    }

    private void initLayout()
    {

    }

    private void initEvent()
    {

        binding.btnHome.setOnClickListener( v -> NavigationManager.startActivity(this, Home.class));
        binding.btnAkun.setOnClickListener( v -> NavigationManager.startActivity(this, BerandaProfile.class) );
        binding.btnLahan.setOnClickListener( v -> NavigationManager.startActivity(this, ListProfileLahan.class) );
        binding.btnWarungku.setOnClickListener( v -> NavigationManager.startActivity(this, PesananWarungku.class) );
    }

    private void initObserver()
    {
        String myId = PreferenceUtils.getIdProfil(this);

        viewModel.fetchAllChat(myId, new ChatRepo.ChatDataCallback() {
            @Override
            public void onLoaded(List<DatumProfil> profils, List<DatumInbox> inboxes, List<DatumInboxParticipant> participants, List<DatumChat> chats)
            {
                binding.viewLoading.setVisibility(View.GONE);
                itemList = new AdapterInbox(inboxes, profils, participants, Inbox.this, new AdapterInbox.OnInboxItemClickListener() {
                    @Override
                    public void onInboxItemClick(DatumInbox datumInbox, String otherProfile) {
                        if ( datumInbox.getReadFlag().equalsIgnoreCase("N") && !datumInbox.getLastSender().equalsIgnoreCase(myId) )
                        {
                            updateReadFlag(datumInbox);
                        }
                        // When an inbox item is clicked, navigate to the chat screen
                        Intent intent = new Intent(Inbox.this, Chat.class);
                        intent.putExtra(Global.ID_INBOX, datumInbox.getIdInbox());
                        intent.putExtra(Global.NAMA_INBOX, otherProfile);
                        startActivity(intent);
                    }
                });
                binding.rvInbox.setLayoutManager(new LinearLayoutManager(Inbox.this));
                binding.rvInbox.setAdapter(itemList);
            }

            @Override
            public void onError(String error)
            {
                binding.viewLoading.setVisibility(View.GONE);
                Toast.makeText(Inbox.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateReadFlag( DatumInbox datumInbox )
    {

        viewModel.updateIsReadStatus(datumInbox.getIdInbox());

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idInbox", datumInbox.getIdInbox());
        jsonParams.put("lastText", datumInbox.getLastText());
        jsonParams.put("lastSender", datumInbox.getLastSender());
        jsonParams.put("readFlag", "Y");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateInbox(body);
        response.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
               // do nothing
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
               // do nothing / taro di log
            }
        });
    }

    public void goToChat( String idInbox ){
        Intent a = new Intent(Inbox.this, Chat.class);
        a.putExtra("idInbox", idInbox);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(Inbox.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToWarungku(){
        Intent a = new Intent(Inbox.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(Inbox.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(Inbox.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                chatReceiver,
                new IntentFilter(Global.INTENT_ACTION_REFRESH_INBOX)
        );
    }

    @Override
    protected void onPause()
    {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(chatReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }

    @Override
    public void onInboxItemClick(DatumInbox datumInbox, String otherProfile)
    {
//        String idProfile = PreferenceUtils.getIdProfil(this);
//        if ( datumInbox.getReadFlag().equalsIgnoreCase("N") && !datumInbox.getLastSender().equalsIgnoreCase(idProfile) )
//        {
//            updateReadFlag(datumInbox);
//        }
//        // When an inbox item is clicked, navigate to the chat screen
//        Intent intent = new Intent(Inbox.this, Chat.class);
//        intent.putExtra(Global.ID_INBOX, datumInbox.getIdInbox());
//        intent.putExtra(Global.NAMA_INBOX, otherProfile);
//        startActivity(intent);
    }
}