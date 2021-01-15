package com.example.klinik_pln.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klinik_pln.R;
import com.example.klinik_pln.adapter.AllAntrianAdapter;
import com.example.klinik_pln.adapter.AntrianAdapter;
import com.example.klinik_pln.api.ApiRequestAntrian;
import com.example.klinik_pln.api.RetroServerAntrian;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.example.klinik_pln.model.getDataAntrianOneModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAntrianFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    private RecyclerView rv_antrian;
    private ProgressDialog pd;
    private TextView tv_kosong;
    private List<getDataAntrianOneModel> getAllDataAntrian;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_antrian, container, false);
        rv_antrian = view.findViewById(R.id.rv_antrian);
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
                LoadallData();
            }
        });

        return view;
    }

    private void LoadallData() {
        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        final Call<ResponsModelTambah> getallAntrian = apiRequestAntrian.getdataantrian("a");
        getallAntrian.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                pd.hide();
                String kode = response.body().getKode();
                if (kode.equals("1")) {
                    if (response.body().getResultAllAntrian().isEmpty()) {
                        tv_kosong.setText("Belum Ada Antrian");
                    } else {
                        getAllDataAntrian = response.body().getResultAllAntrian();
                        AllAntrianAdapter adapter = new AllAntrianAdapter(getContext(),getAllDataAntrian);
                        rv_antrian.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_antrian.setAdapter(adapter);
                        tv_kosong.setText("");
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                pd.hide();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onRefresh() {
LoadallData();
    }
}
