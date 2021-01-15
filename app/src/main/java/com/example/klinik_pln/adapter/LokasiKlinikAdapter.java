package com.example.klinik_pln.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.activity.ProfilePasienActivity;
import com.example.klinik_pln.klinik.KlinikAdapter;
import com.example.klinik_pln.klinik.KlinikModel;
import com.example.klinik_pln.klinik.MapsApotekActivity;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LokasiKlinikAdapter extends RecyclerView.Adapter<LokasiKlinikAdapter.MyHolderView> {

    private List<KlinikModel> klinikModelList;
    private Context mContext;

    public LokasiKlinikAdapter(Context mContext,List<KlinikModel> klinikModelList) {
        this.klinikModelList = klinikModelList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public LokasiKlinikAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_klinik,parent,false);
        return new LokasiKlinikAdapter.MyHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LokasiKlinikAdapter.MyHolderView holder, final int position) {
        final KlinikModel km = klinikModelList.get(position);
        holder.title.setText(km.getNama());
        final String url_lokas = km.getLokasi();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
//                Uri uri = Uri.parse(url_lokas);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                mContext.startActivity(intent);

                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(km.getNama())
                        .setContentText("Buka Lokasi Map ?")
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
                                Intent intent = new Intent(mContext, MapsApotekActivity.class);
                                intent.putExtra("EXTRA_DATA", klinikModelList.get(position));
                                mContext.startActivity(intent);
                            }
                        })
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return klinikModelList.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        TextView title;
        public MyHolderView(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }
    }
}
