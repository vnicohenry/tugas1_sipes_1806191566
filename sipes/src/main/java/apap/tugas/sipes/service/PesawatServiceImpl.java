package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;
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
public class PesawatServiceImpl implements PesawatService {

    @Autowired
    PesawatDb pesawatDb;

    @Override
    public PesawatModel add(PesawatModel pesawat) {
        pesawatDb.save(pesawat);
        return pesawat;
    }

    @Override
    public String calculateNomorSeri(PesawatModel pesawat) {
        String hasil = "";

        if(pesawat.getJenisPesawat().equals("Komersial")){
            hasil += "1";
        }
        else{hasil += "2";}

        if(pesawat.getTipe().getNamaTipe().equals("Boeing")){
            hasil += "BO";
        }
        else if (pesawat.getTipe().getNamaTipe().equals("ATR")){
            hasil += "AT";
        }
        else if(pesawat.getTipe().getNamaTipe().equals("Airbus")){
            hasil += "AB";
        }
        else {hasil += "BB";}

        LocalDate localDate = pesawat.getTanggalDibuat().toLocalDate();
        int tahun = localDate.getYear();
        String tahunString = Integer.toString(tahun);
        String reverse = new StringBuffer(tahunString).reverse().toString();
        hasil += reverse;

        int tahun8 = tahun + 8;
        hasil += Integer.toString(tahun8);

        Random r = new Random();
        char huruf1 = (char)(r.nextInt(26) + 'A');
        char huruf2 = (char)(r.nextInt(26) + 'A');
        String gabung = "" + huruf1 + huruf2;
        hasil += gabung;

        return hasil;
    }

    @Override
    public List<String> getAllNomorSeri(){
        List<PesawatModel> listPesawat = pesawatDb.findAll();
        List<String> listNomorSeri = new ArrayList<String>();
        for(PesawatModel pm : listPesawat){
            listNomorSeri.add(pm.getNomorSeri());
        }
        return listNomorSeri;
    }

    @Override
    public List<PesawatModel> getAll(){
        List<PesawatModel> listPesawat = pesawatDb.findAll();
        return listPesawat;
    }

    @Override
    public PesawatModel getPesawatById(Long id){
        if (pesawatDb.findById(id).isPresent()) {
            return pesawatDb.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public PesawatModel update(PesawatModel pesawat){

        PesawatModel targetPesawat;

        if (pesawatDb.findById(pesawat.getId()).isPresent()) {
            targetPesawat = pesawatDb.findById(pesawat.getId()).get();
        } else {
            targetPesawat= null;
        }

        try{
            targetPesawat.setMaskapai(pesawat.getMaskapai());
            targetPesawat.setTanggalDibuat(pesawat.getTanggalDibuat());
            targetPesawat.setTempatDibuat(pesawat.getTempatDibuat());
            targetPesawat.setJenisPesawat(pesawat.getJenisPesawat());
            targetPesawat.setNomorSeri(pesawat.getNomorSeri());
            targetPesawat.setListPenerbangan(pesawat.getListPenerbangan());
            pesawatDb.save(targetPesawat);
            return targetPesawat;
        } catch (NullPointerException nullException){
            return null;
        }
    }

    @Override
    public void delete(PesawatModel pesawat) {
        pesawatDb.delete(pesawat);
    }
}
