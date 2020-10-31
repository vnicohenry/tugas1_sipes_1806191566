package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;
import apap.tugas.sipes.repository.TeknisiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TeknisiServiceImpl implements TeknisiService{

    @Autowired
    TeknisiDb teknisiDb;

    @Override
    public void addTeknisi(TeknisiModel teknisi) {
        teknisiDb.save(teknisi);
    }

    @Override
    public List<TeknisiModel> getTeknisiList() {
        return teknisiDb.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public TeknisiModel getTeknisiById(Long idTeknisi){
        if (teknisiDb.findById(idTeknisi).isPresent()) {
            return teknisiDb.findById(idTeknisi).get();
        } else {
            return null;
        }
    }

    @Override
    public List<TeknisiModel> getAll(){
        List<TeknisiModel> listTeknisi = teknisiDb.findAll();
        return listTeknisi;
    }

//    @Override
//    public TeknisiModel update(TeknisiModel teknisi){
//
//        TeknisiModel targetTeknisi;
//
//        if (teknisiDb.findById(teknisi.getId()).isPresent()) {
//            targetTeknisi = teknisiDb.findById(teknisi.getId()).get();
//        } else {
//            targetTeknisi= null;
//        }
//
//        try{
//            targetTeknisi.setNamaTeknisi(teknisi.getNamaTeknisi());
//            targetTeknisi.setNoTelepon(teknisi.getNoTelepon());
//            targetTeknisi.setListPesawatTeknisi(teknisi.getListPesawatTeknisi());
//            teknisiDb.save(targetTeknisi);
//            return targetTeknisi;
//        } catch (NullPointerException nullException){
//            return null;
//        }
//    }

}
