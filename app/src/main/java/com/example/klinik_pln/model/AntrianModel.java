package com.example.klinik_pln.model;

public class AntrianModel {

    private String nomor_antrian;

    public String getNomor_antrian() {
        return nomor_antrian;
    }

    public void setNomor_antrian(String nomor_antrian) {
        this.nomor_antrian = nomor_antrian;
    }

    public AntrianModel(String nomor_final) {
        this.nomor_antrian = nomor_final;
    }
}
