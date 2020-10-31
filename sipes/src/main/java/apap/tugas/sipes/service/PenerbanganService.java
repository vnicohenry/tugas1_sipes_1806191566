package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;

import java.util.List;

public interface PenerbanganService {
    // Method untuk menambah hotel
    PenerbanganModel add(PenerbanganModel penerbangan);

    List<String> getAllNomorPenerbangan();

    List<PenerbanganModel> getAll();

    PenerbanganModel getPenerbanganById(Long id);

    PenerbanganModel update(PenerbanganModel penerbangan);

    void delete(PenerbanganModel penerbangan);
}
