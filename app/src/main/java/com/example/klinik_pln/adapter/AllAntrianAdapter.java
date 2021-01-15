package com.example.klinik_pln.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.model.getDataAntrianOneModel;

import java.util.List;

public class AllAntrianAdapter extends RecyclerView.Adapter<AllAntrianAdapter.myHolderView> {

    private Context mContext;
    private List<getDataAntrianOneModel> modelList;

    public AllAntrianAdapter(Context mContext, List<getDataAntrianOneModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public AllAntrianAdapter.myHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_all_antrian, parent, false);
        AllAntrianAdapter.myHolderView myHolderView = new AllAntrianAdapter.myHolderView(view);
        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull AllAntrianAdapter.myHolderView holder, int position) {

        final String kode_db = modelList.get(position).getKode_antrian();
        final String jam = modelList.get(position).getJam_antrian();
        switch (jam) {
            case "08.00":
                holder.tv_kode.setText("PS-" + "01-" + "00" + kode_db);
                break;
            case "11.00":
                holder.tv_kode.setText("PS-" + "02-" + "00" + kode_db);
                break;
            case "14.00":
                holder.tv_kode.setText("PS-" + "03-" + "00" + kode_db);
                break;
        }
        holder.tv_nama.setText(modelList.get(position).getNama_pasien());
        holder.tv_tgl.setText(modelList.get(position).getTanggal_antrian());
        holder.tv_jam.setText(modelList.get(position).getJam_antrian());
        holder.tv_status.setText(modelList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class myHolderView extends RecyclerView.ViewHolder {

        TextView tv_kode, tv_nama, tv_tgl, tv_jam, tv_status;

        public myHolderView(@NonNull View itemView) {
            super(itemView);

            tv_kode = itemView.findViewById(R.id.tv_kode);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_tgl = itemView.findViewById(R.id.tv_tgl);
            tv_jam = itemView.findViewById(R.id.tv_jam);
            tv_status = itemView.findViewById(R.id.tv_status);

        }
    }
}
