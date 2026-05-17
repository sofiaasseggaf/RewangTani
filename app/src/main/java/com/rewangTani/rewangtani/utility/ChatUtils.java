package com.rewangTani.rewangtani.utility;

import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;

import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.bottombar.pesan.Chat;
import com.rewangTani.rewangtani.data.entity.inbox.DatumInbox;
import com.rewangTani.rewangtani.data.entity.inbox.ModelInbox;
import com.rewangTani.rewangtani.data.entity.inbox.ResponseInbox;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.DatumInboxParticipant;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.ModelInboxParticipant;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.ResponseInboxParticipant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ChatUtils {

    static Context context;

    public ChatUtils(Context context) {
        ChatUtils.context = context;
    }

    public void createInboxParticipant(String idProfile, String idOther, String namaOther, String namaProduk)
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfilA", idProfile);
        jsonParams.put("idProfilB", idOther);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseInboxParticipant> response = apiInterface.sendInboxParticipant(body);
        response.enqueue(new Callback<ResponseInboxParticipant>() {
            @Override
            public void onResponse(Call<ResponseInboxParticipant> call, retrofit2.Response<ResponseInboxParticipant> rawResponse) {
                try {
                    Log.d("SOFIA", String.valueOf(rawResponse.body()));
                    if (rawResponse.body() != null) {
                        ResponseInboxParticipant responseInboxParticipant = rawResponse.body();
                        try {
                            String idInboxParticipant = responseInboxParticipant.getData().getIdInboxParticipant();
                            if ( idInboxParticipant != null )
                            {
                                createInbox(idProfile, idInboxParticipant, namaOther, namaProduk);
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseInboxParticipant> call, Throwable throwable) {
                call.cancel();
            }
        });
    }

    public void createInbox(String idProfile, String idInboxParticipant, String namaOther, String namaProduk)
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idInboxParticipant", idInboxParticipant);
        jsonParams.put("lastText", Global.STRING_DEFAULT_VALUE);
        jsonParams.put("lastSender", idProfile);
        jsonParams.put("readFlag", "N");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseInbox> response = apiInterface.sendInbox(body);
        response.enqueue(new Callback<ResponseInbox>() {
            @Override
            public void onResponse(Call<ResponseInbox> call, retrofit2.Response<ResponseInbox> rawResponse) {
                try {
                    Log.d("SOFIA", String.valueOf(rawResponse.body()));
                    if (rawResponse.body() != null) {
                        ResponseInbox responseInbox = rawResponse.body();
                        try {
                            String idInbox = responseInbox.getData().getIdInboxParticipant();
                            if (idInbox != null) {
                                Intent intent = new Intent(context, Chat.class);
                                intent.putExtra(Global.ID_INBOX, idInbox);
                                intent.putExtra(Global.NAMA_INBOX, namaOther);
                                intent.putExtra(Global.START_CHAT_IMMIDIATELY, true);
                                intent.putExtra(Global.NAMA_PRODUK, namaProduk);
                                context.startActivity(intent);
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseInbox> call, Throwable throwable) {
                call.cancel();
            }
        });

    }

    public void goToInbox(String idProfile, String idOther, String namaOther, String namaProduk)
    {
        List<DatumInboxParticipant> listInboxParticipant = new ArrayList<>();
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelInboxParticipant> dataRT = apiInterface.getDataInboxParticipant();
        dataRT.enqueue(new Callback<ModelInboxParticipant>()
        {
            @Override
            public void onResponse( Call<ModelInboxParticipant> call, retrofit2.Response<ModelInboxParticipant> response )
            {
                ModelInboxParticipant modelInboxParticipant = response.body();

                if ( response.body() != null )
                {
                    for ( int i = 0; i < modelInboxParticipant.getTotalData(); i++ )
                    {
                        try
                        {
                            String idA = modelInboxParticipant.getData().get(i).getIdProfilA();
                            String idB = modelInboxParticipant.getData().get(i).getIdProfilB();
                            boolean isMatch = (idProfile.equalsIgnoreCase(idA) && idOther.equalsIgnoreCase(idB)) ||
                                            (idProfile.equalsIgnoreCase(idB) && idOther.equalsIgnoreCase(idA));
                            if (isMatch)
                            {
                                listInboxParticipant.add(modelInboxParticipant.getData().get(i));
                            }
                        }
                        catch ( Exception e ) { }
                    }

                    if (!listInboxParticipant.isEmpty())
                    {
                        getDataInbox(idProfile, idOther, namaOther, listInboxParticipant, namaProduk);
                    }
                    else
                    {
                        createInboxParticipant(idProfile, idOther, namaOther, namaProduk);
                    }
                }
            }
            @Override
            public void onFailure( Call<ModelInboxParticipant> call, Throwable t )
            { }
        });
    }

    private void getDataInbox(String idProfile, String idOther, String namaOther, List<DatumInboxParticipant> listInboxParticipant, String namaProduk)
    {
        List<DatumInbox> listInbox = new ArrayList<>();
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelInbox> dataRT = apiInterface.getDataInbox();
        dataRT.enqueue(new Callback<ModelInbox>()
        {
            @Override
            public void onResponse( Call<ModelInbox> call, retrofit2.Response<ModelInbox> response )
            {
                ModelInbox modelInbox = response.body();
                if ( response.body() != null )
                {
                    boolean isIntent = false;
                    for ( int i = 0; i < modelInbox.getTotalData(); i++ )
                    {
                        for ( int j = 0; j < listInboxParticipant.size(); j++ )
                        {
                            if ( modelInbox.getData().get(i).getIdInboxParticipant().equalsIgnoreCase(listInboxParticipant.get(j).getIdInboxParticipant()) )
                            {
                                isIntent = true;
                                Intent intent = new Intent(context, Chat.class);
                                intent.putExtra(Global.ID_INBOX, modelInbox.getData().get(i).getIdInbox());
                                intent.putExtra(Global.NAMA_INBOX, namaOther);
                                intent.putExtra(Global.START_CHAT_IMMIDIATELY, true);
                                intent.putExtra(Global.NAMA_PRODUK, namaProduk);
                                context.startActivity(intent);
                            }
                        }
                    }

                    if ( !isIntent )
                    {
                        createInboxParticipant(idProfile, idOther, namaOther, namaProduk);
                    }
                }
            }
            @Override
            public void onFailure( Call<ModelInbox> call, Throwable t )
            { }
        });
    }
}
