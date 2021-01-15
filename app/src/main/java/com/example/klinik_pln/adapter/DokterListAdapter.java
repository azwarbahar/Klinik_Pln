package com.example.klinik_pln.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.api.ApiRequestUser;
import com.example.klinik_pln.api.RetroServerUser;
import com.example.klinik_pln.model.GetDataUserModel;
import com.example.klinik_pln.model.ResponsModelTambah;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DokterListAdapter extends RecyclerView.Adapter<DokterListAdapter.MyHolderView> {

    private Context mContext;
    private List<GetDataUserModel> userModels;

    private ProgressDialog pd;

    public DokterListAdapter(Context mContext, List<GetDataUserModel> userModels) {
        this.mContext = mContext;
        this.userModels = userModels;
    }

    public DokterListAdapter(){

    }

    @NonNull
    @Override
    public DokterListAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pasien,parent,false);
        return new DokterListAdapter.MyHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DokterListAdapter.MyHolderView holder, final int position) {
        final GetDataUserModel gdum = userModels.get(position);
        holder.tv_namalengkap.setText(gdum.getNama());
        holder.tv_telpon.setText(gdum.getTelpon());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setCancelable(true);
                dialog.setMessage("Hapus Data?");
                dialog.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        pd.setMessage("Proses ... ");
                        pd.setCancelable(false);
                        pd.show();

                        ApiRequestUser apiRequestUser = RetroServerUser.getClient().create(ApiRequestUser.class);
                        Call<ResponsModelTambah> deleteUser = apiRequestUser.deleteUser(userModels.get(position).getNama());
                        deleteUser.enqueue(new Callback<ResponsModelTambah>() {
                            @Override
                            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                                pd.hide();
                                Toast.makeText(mContext, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show();
                                userModels.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,userModels.size());
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
            }
        });

    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        TextView tv_namalengkap,tv_telpon;
        ImageView btn_delete;
        public MyHolderView(@NonNull View itemView) {
            super(itemView);
            pd = new ProgressDialog(mContext);
            tv_namalengkap = itemView.findViewById(R.id.tv_namalengkap);
            tv_telpon = itemView.findViewById(R.id.tv_telpon);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
