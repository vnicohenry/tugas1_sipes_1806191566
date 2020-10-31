package apap.tugas.sipes.service;


import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;
import apap.tugas.sipes.repository.TipeDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TipeServiceImpl implements TipeService{

    @Autowired
    TipeDb tipeDb;

    @Override
    public void addTipe(TipeModel tipe) {
        tipeDb.save(tipe);
    }

    @Override
    public List<TipeModel> getTipeList() {
        return tipeDb.findAll();
    }

    @Override
    public TipeModel getTipeById(Long idTipe){
        if (tipeDb.findById(idTipe).isPresent()) {
            return tipeDb.findById(idTipe).get();
        } else {
            return null;
        }
    }

    @Override
    public List<TipeModel> getAll(){
        List<TipeModel> listTipe = tipeDb.findAll();
        return listTipe;
    }
}
