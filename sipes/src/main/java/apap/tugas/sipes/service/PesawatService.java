package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PesawatModel;

import java.util.List;

public interface PesawatService {
    // Method untuk menambah hotel
    PesawatModel add(PesawatModel pesawat);

    String calculateNomorSeri(PesawatModel pesawat);

    List<String> getAllNomorSeri();

    List<PesawatModel> getAll();

    PesawatModel getPesawatById(Long id);

    PesawatModel update(PesawatModel pesawat);

    void delete(PesawatModel pesawat);
}
