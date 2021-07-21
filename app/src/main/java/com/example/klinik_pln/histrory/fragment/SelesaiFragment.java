package com.example.klinik_pln.histrory.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.klinik_pln.R;
import com.example.klinik_pln.api.ApiRequestAntrian;
import com.example.klinik_pln.api.RetroServerAntrian;
import com.example.klinik_pln.histrory.HistoryPasienActivity;
import com.example.klinik_pln.histrory.adapter.RiwayatAdapter;
import com.example.klinik_pln.model.getDataAntrianOneModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelesaiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    View view;
    private RecyclerView rv_riwayat;
    private ProgressDialog pd;
    private TextView tv_kosong;
    private ArrayList<getDataAntrianOneModel> getDataAntrianOneModels;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String nama;

    private SharedPreferences mPreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_riwayat_pasien, container, false);

        mPreferences = getActivity().getApplicationContext().getSharedPreferences(my_shared_preferences, getActivity().MODE_PRIVATE);
        nama = mPreferences.getString("NAMA", "");
        rv_riwayat = view.findViewById(R.id.rv_riwayat);
        pd = new ProgressDialog(getContext());
        tv_kosong = view.findViewById(R.id.tv_kosong);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_continer);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                LoadallData(nama);
            }
        });

        return view;
    }

    private void LoadallData(String nama) {

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<List<getDataAntrianOneModel>> listCall = apiRequestAntrian.read_antrian_user_status(nama, "Selesai");
        listCall.enqueue(new Callback<List<getDataAntrianOneModel>>() {
            @Override
            public void onResponse(Call<List<getDataAntrianOneModel>> call, Response<List<getDataAntrianOneModel>> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        tv_kosong.setVisibility(View.VISIBLE);
                        rv_riwayat.setVisibility(View.GONE);
                    } else {
                        getDataAntrianOneModels = (ArrayList<getDataAntrianOneModel>) response.body();
                        if (getDataAntrianOneModels.size() < 1) {
                            tv_kosong.setVisibility(View.VISIBLE);
                            rv_riwayat.setVisibility(View.GONE);
                        } else {
                            tv_kosong.setVisibility(View.GONE);
                            rv_riwayat.setVisibility(View.VISIBLE);
                            RiwayatAdapter riwayatAdapter = new RiwayatAdapter(getActivity(), getDataAntrianOneModels);
                            rv_riwayat.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv_riwayat.setAdapter(riwayatAdapter);
                        }
                    }

                } else {
                    tv_kosong.setVisibility(View.VISIBLE);
                    rv_riwayat.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<getDataAntrianOneModel>> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                tv_kosong.setVisibility(View.VISIBLE);
                rv_riwayat.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onRefresh() {
        LoadallData(nama);
    }
}
