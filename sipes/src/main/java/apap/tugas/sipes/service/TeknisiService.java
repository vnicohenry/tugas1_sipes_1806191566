package apap.tugas.sipes.service;

import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;

import java.util.List;

public interface TeknisiService {

    //method untuk menambah teknisi
    void addTeknisi(TeknisiModel teknisi);

    // Method untuk mendapatkan daftar Hotel yang telah tersimpan
    List<TeknisiModel> getTeknisiList();

    TeknisiModel getTeknisiById(Long idTeknisi);

    List<TeknisiModel> getAll();
}
