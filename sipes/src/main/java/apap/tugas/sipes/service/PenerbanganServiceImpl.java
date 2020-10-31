package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;
import apap.tugas.sipes.repository.PenerbanganDb;
import apap.tugas.sipes.repository.PesawatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PenerbanganServiceImpl implements PenerbanganService{

    @Autowired
    PenerbanganDb penerbanganDb;

    @Override
    public PenerbanganModel add(PenerbanganModel penerbangan) {
        penerbanganDb.save(penerbangan);
        return penerbangan;
    }

    @Override
    public List<String> getAllNomorPenerbangan(){
        List<String> listNomorPenerbangan = new ArrayList<String>();

        List<PenerbanganModel> listPenerbangan = penerbanganDb.findAll();

        for(PenerbanganModel pm : listPenerbangan){
            listNomorPenerbangan.add(pm.getNomorPenerbangan());
        }
        return listNomorPenerbangan;
    }

    @Override
    public List<PenerbanganModel> getAll(){
        List<PenerbanganModel> listPenerbangan = penerbanganDb.findAll();
        return listPenerbangan;
    }

    @Override
    public PenerbanganModel getPenerbanganById(Long id){
        if (penerbanganDb.findById(id).isPresent()) {
            return penerbanganDb.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public PenerbanganModel update(PenerbanganModel penerbangan){
        PenerbanganModel targetPenerbangan;

        if (penerbanganDb.findById(penerbangan.getId()).isPresent()) {
            targetPenerbangan = penerbanganDb.findById(penerbangan.getId()).get();
        } else {
            targetPenerbangan= null;
        }
        try{
            targetPenerbangan.setNomorPenerbangan(penerbangan.getNomorPenerbangan());
            targetPenerbangan.setKodeBandaraAsal(penerbangan.getKodeBandaraAsal());
            targetPenerbangan.setKodeBandaraTujuan(penerbangan.getKodeBandaraTujuan());
            targetPenerbangan.setWaktuBerangkat(penerbangan.getWaktuBerangkat());
            targetPenerbangan.setPesawat(penerbangan.getPesawat());
            penerbanganDb.save(targetPenerbangan);
            return targetPenerbangan;
        } catch (NullPointerException nullException){
            return null;
        }
    }

    @Override
    public void delete(PenerbanganModel penerbangan) {
        penerbanganDb.delete(penerbangan);
    }
}
