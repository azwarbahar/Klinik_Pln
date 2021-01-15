package com.example.klinik_pln.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.activity.DokterActivity;
import com.example.klinik_pln.activity.RegisterActivity;
import com.example.klinik_pln.api.ApiRequestAntrian;
import com.example.klinik_pln.api.RetroServerAntrian;
import com.example.klinik_pln.fragment.MenungguFragment;
import com.example.klinik_pln.model.GetDataUserModel;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.example.klinik_pln.model.getDataAntrianOneModel;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AntrianAdapter extends RecyclerView.Adapter<AntrianAdapter.MyHolderView> {

    ProgressDialog pd;

    private Context mContext;
    private List<getDataAntrianOneModel> modelList;

    public AntrianAdapter(Context mContext, List<getDataAntrianOneModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_antrian, parent, false);
        MyHolderView myHolderView = new MyHolderView(view);
        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolderView holder, final int position) {

        final String kode_db = modelList.get(position).getKode_antrian();
        final String jam = modelList.get(position).getJam_antrian();
        switch (jam) {
            case "08.00":
                holder.tv_nomor.setText("PS-" + "01-" + "00" + kode_db);
                break;
            case "11.00":
                holder.tv_nomor.setText("PS-" + "02-" + "00" + kode_db);
                break;
            case "14.00":
                holder.tv_nomor.setText("PS-" + "03-" + "00" + kode_db);
                break;
        }
        holder.tv_nama.setText(modelList.get(position).getNama_pasien());
        final String tgl = modelList.get(position).getTanggal_antrian();

        if (modelList.get(position).getStatus().equals("Selesai")) {
            holder.tv_panggil.setVisibility(View.GONE);
        } else {
            holder.tv_panggil.setVisibility(View.VISIBLE);

            holder.tv_panggil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Panggil Antrian")
                            .setContentText("Ingin Memanggil Antrian yang dipilih ? ?")
                            .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                    pd.setMessage("Proses ... ");
                                    pd.setCancelable(false);
                                    pd.show();

                                    ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
                                    Call<ResponsModelTambah> updateStatus = apiRequestAntrian.updateData(kode_db, tgl, "Selesai",jam);
                                    updateStatus.enqueue(new Callback<ResponsModelTambah>() {
                                        @Override
                                        public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                                            pd.hide();
                                            Log.d("RETRO", "response : " + response.body().toString());
                                            String kode = response.body().getKode();
                                            if (kode.equals("1")) {
                                                new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Success..")
                                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                sweetAlertDialog.dismiss();
                                                                modelList.remove(position);
                                                                notifyItemRemoved(position);
                                                                notifyItemRangeChanged(position, modelList.size());
                                                            }
                                                        })
                                                        .show();
                                            } else {
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponsModelTambah> call, Throwable t) {

                                            pd.hide();
                                        }
                                    });
                                }
                            })
                            .show();

                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_nomor, tv_nama, tv_panggil;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            pd = new ProgressDialog(mContext);
            tv_nomor = itemView.findViewById(R.id.tv_nomor);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_panggil = itemView.findViewById(R.id.tv_panggil);
        }
    }
}
