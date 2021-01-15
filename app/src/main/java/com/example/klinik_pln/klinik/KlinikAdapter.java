package com.example.klinik_pln.klinik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.api.ApiRequestKlinik;
import com.example.klinik_pln.api.RetroServerKlinik;
import com.example.klinik_pln.model.ResponsModelTambah;

import java.net.URI;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KlinikAdapter extends RecyclerView.Adapter<KlinikAdapter.MyholderView> {

    ProgressDialog pd;

    private List<KlinikModel> klinikModelList;
    private Context mContext;

    public KlinikAdapter(Context mContext, List<KlinikModel> klinikModelList) {

        this.mContext = mContext;
        this.klinikModelList = klinikModelList;
    }


    @NonNull
    @Override
    public KlinikAdapter.MyholderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_klinik, parent, false);
        return new MyholderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final KlinikAdapter.MyholderView holder, final int position) {

        final KlinikModel km = klinikModelList.get(position);
        holder.title.setText(km.getNama());
        holder.tv_lokasi.setText(km.getLokasi());
        final String id_klinik = km.getId();

//        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "Edit", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, DetileKlinikActivity.class);
//                intent.putExtra(DetileKlinikActivity.EXTRA_DATA, klinikModelList.get(position));
//                mContext.startActivity(intent);
//            }
//        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setCancelable(true);
                dialog.setMessage("Hapus Klinik ?");
                dialog.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        pd.setMessage("Proses ... ");
                        pd.setCancelable(false);
                        pd.show();


                        ApiRequestKlinik apiRequestKlinik = RetroServerKlinik.getClient().create(ApiRequestKlinik.class);
                        Call<ResponsModelTambah> deleteKlinik = apiRequestKlinik.deleteData(id_klinik);
                        deleteKlinik.enqueue(new Callback<ResponsModelTambah>() {
                            @Override
                            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                                pd.hide();
                                Log.d("Retro", "onRespon");
                                assert response.body() != null;

                                klinikModelList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, klinikModelList.size());

                            }

                            @Override
                            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                                pd.hide();
                            }
                        });
                    }
                });

                dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MapsApotekActivity.class);
                intent.putExtra("EXTRA_DATA", klinikModelList.get(position));
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return klinikModelList.size();
    }

    public class MyholderView extends RecyclerView.ViewHolder {
        TextView title, tv_lokasi;
        ImageView btn_edit, btn_delete;

        public MyholderView(@NonNull View itemView) {
            super(itemView);
            pd = new ProgressDialog(mContext);

            title = itemView.findViewById(R.id.tv_title);
            tv_lokasi = itemView.findViewById(R.id.tv_lokasi);
//            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }


}
