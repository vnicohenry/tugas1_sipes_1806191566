package apap.tugas.sipes.controller;


import apap.tugas.sipes.model.*;
import apap.tugas.sipes.service.PesawatService;
import apap.tugas.sipes.service.TeknisiService;
import apap.tugas.sipes.service.TipeService;
import apap.tugas.sipes.service.PesawatTeknisiService;
import apap.tugas.sipes.service.PenerbanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PesawatController {

    @Autowired
    private PesawatService pesawatService;

    @Autowired
    private TeknisiService teknisiService;

    @Autowired
    private TipeService tipeService;

    @Autowired
    private PesawatTeknisiService pesawatTeknisiService;

    @Autowired
    private PenerbanganService penerbanganService;

    @GetMapping("/pesawat")
    public String listPesawat(Model model){

        List<PesawatModel> listPesawat = pesawatService.getAll();
        model.addAttribute("listPesawat", listPesawat);

        return "viewall-pesawat";
    }

    @GetMapping(value="/pesawat/add")
    public String addPesawatFormPage(Model model){

        PesawatModel pesawat = new PesawatModel();
        model.addAttribute("pesawat", pesawat);

        List<TeknisiModel> kumpulanTeknisiBaru = new ArrayList<TeknisiModel>();
        kumpulanTeknisiBaru.add(new TeknisiModel());
        pesawat.setListTeknisi(kumpulanTeknisiBaru);

        List<TipeModel> tipeList = tipeService.getTipeList();
        model.addAttribute("listTipe", tipeList);

        List<TeknisiModel> teknisiList = teknisiService.getTeknisiList();
        model.addAttribute("listTeknisi", teknisiList);

        PesawatTeknisiModel ptmBaru = new PesawatTeknisiModel();
        model.addAttribute("ptm", ptmBaru);

        return "form-add-pesawat";
    }

    @PostMapping(value = "/pesawat/add", params = {"save"})
    public String addPesawatSubmit(
            @ModelAttribute PesawatModel pesawat,
            Model model){

        pesawat.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));
        String noSeri = pesawatService.calculateNomorSeri(pesawat);

        List<String> listNomorSeri = pesawatService.getAllNomorSeri();
        for (int i = 0 ; i < listNomorSeri.size(); i++){
            if (listNomorSeri.get(i).equals(noSeri)){
                noSeri = pesawatService.calculateNomorSeri(pesawat);
            }
        }
        pesawat.setNomorSeri(noSeri);
        pesawat = pesawatService.add(pesawat);

        List<TeknisiModel> teknisiPesawat = new ArrayList<TeknisiModel>();
        for(TeknisiModel tm : pesawat.getListTeknisi()){
            PesawatTeknisiModel ptmBaru = new PesawatTeknisiModel();
            ptmBaru.setPesawat(pesawat);
            teknisiPesawat.add(teknisiService.getTeknisiById(tm.getId()));
            ptmBaru.setTeknisi(teknisiService.getTeknisiById(tm.getId()));
            pesawatTeknisiService.add(ptmBaru);
        }
        pesawat.setListTeknisi(teknisiPesawat);

        model.addAttribute("pesawat", pesawat);
        return "add-pesawat";
    }


    @PostMapping(value ="/pesawat/add", params = {"addTeknisi"})
    public String addRowTeknisi(
            @ModelAttribute PesawatModel pesawat,
            Model model){

        pesawat.getListTeknisi().add(new TeknisiModel());

        List<TeknisiModel> teknisiList = teknisiService.getTeknisiList();
        model.addAttribute("listTeknisi", teknisiList);

        List<TipeModel> tipeList = tipeService.getTipeList();
        model.addAttribute("listTipe", tipeList);

        model.addAttribute("pesawat", pesawat);

        Date date = new Date(System.currentTimeMillis());

        model.addAttribute("date", date);

        return "form-add-pesawat";
    }

    @GetMapping(value = "/pesawat/{id}")
    public String viewDetailPesawat(
            @PathVariable Long id,
            Model model){

        PesawatModel pesawat = pesawatService.getPesawatById(id);
        model.addAttribute("pesawat", pesawat);
        List<TeknisiModel> listTeknisi = pesawat.getListTeknisi();
        model.addAttribute("listTeknisi", listTeknisi);


        List<PenerbanganModel> penerbanganTersedia = new ArrayList<PenerbanganModel>();
        List<PenerbanganModel> listPenerbangan = penerbanganService.getAll();
        for (PenerbanganModel p : listPenerbangan){
            if (!pesawat.getListPenerbangan().contains(p)){
                penerbanganTersedia.add(p);
            }
        }
        model.addAttribute("listPenerbangan", penerbanganTersedia);

        boolean isi = !penerbanganTersedia.isEmpty();
        model.addAttribute("isi",isi);


        return "view-pesawat";
    }

    @PostMapping(value = "/pesawat/{id}/tambah-penerbangan")
    public String assignPenerbangan(
            @PathVariable Long id,
            @ModelAttribute PesawatModel pesawat,
            Model model){

        PesawatModel targetPesawat = pesawatService.getPesawatById(id);

        PenerbanganModel pm = penerbanganService.getPenerbanganById(pesawat.getListPenerbangan().get(0).getId());

        targetPesawat.getListPenerbangan().add(pm);
        targetPesawat = pesawatService.update(targetPesawat);
        pm.setPesawat(targetPesawat);
        penerbanganService.update(pm);
        model.addAttribute("pesawat", targetPesawat);

        List<TeknisiModel> listTeknisi = targetPesawat.getListTeknisi();
        model.addAttribute("listTeknisi", listTeknisi);

        List<PenerbanganModel> penerbanganTersedia = new ArrayList<PenerbanganModel>();
        List<PenerbanganModel> listPenerbangan = penerbanganService.getAll();
        for (PenerbanganModel p : listPenerbangan){
            if (!targetPesawat.getListPenerbangan().contains(p)){
                penerbanganTersedia.add(p);
            }
        }
        model.addAttribute("listPenerbangan", penerbanganTersedia);

        boolean isi = !penerbanganTersedia.isEmpty();
        model.addAttribute("isi",isi);

        return "view-pesawat";
    }

    @GetMapping(value = "/pesawat/ubah/{id}")
    public String changePesawatForm(
            @PathVariable Long id,
            Model model){

        PesawatModel pesawat = pesawatService.getPesawatById(id);
        model.addAttribute("pesawat", pesawat);

        Date date = new Date(System.currentTimeMillis());
        model.addAttribute("date", date);

        return "form-change-pesawat";
    }

    @PostMapping(value = "/pesawat/ubah")
    public String changePesawatSubmit(
            @ModelAttribute PesawatModel pesawat,
            Model model){

        pesawat.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));

        PesawatModel pesawatLama = pesawatService.getPesawatById(pesawat.getId());

        if (!pesawatLama.getJenisPesawat().equals(pesawat.getJenisPesawat()) ||
                pesawatLama.getTanggalDibuat().compareTo(pesawat.getTanggalDibuat()) != 0){
            String noSeri = pesawatService.calculateNomorSeri(pesawat);
            pesawat.setNomorSeri(noSeri);
        }

        PesawatModel pesawatBaru = pesawatService.update(pesawat);
        model.addAttribute("pesawat", pesawatBaru);

        return "change-pesawat";
    }

    @GetMapping(value = "/pesawat/filter")
    public String filterPesawat(
            @RequestParam(value = "idPenerbangan", required = true) Long idPenerbangan,
            @RequestParam(value = "idTipe", required = true) Long idTipe,
            @RequestParam(value = "idTeknisi", required = true) Long idTeknisi,
            Model model){

        List<PenerbanganModel> listPenerbangan = penerbanganService.getAll();
        model.addAttribute("listPenerbangan", listPenerbangan);
        List<TipeModel> listTipe = tipeService.getAll();
        model.addAttribute("listTipe", listTipe);
        List<TeknisiModel> listTeknisi = teknisiService.getAll();
        model.addAttribute("listTeknisi", listTeknisi);

        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
        model.addAttribute("penerbangan", penerbangan);

        TipeModel tipe = tipeService.getTipeById(idTipe);
        model.addAttribute("tipe", tipe);

        TeknisiModel teknisi = teknisiService.getTeknisiById(idTeknisi);
        model.addAttribute("teknisi", teknisi);

        Set<PesawatModel> targetPesawat = new LinkedHashSet<PesawatModel>();
        List<PesawatModel> listPesawat = pesawatService.getAll();

        for(PesawatModel p : listPesawat){
            List<PenerbanganModel> ppm = p.getListPenerbangan();
            TipeModel ptm = p.getTipe();
            List<TeknisiModel> ptekm = p.getListTeknisi();
            if(ppm.contains(penerbangan) &&
                    ptm.getId() == tipe.getId() &&
                    ptekm.contains(teknisi)){
                targetPesawat.add(p);
            }
        }
        model.addAttribute("listPesawat", targetPesawat);

        boolean notFound = true;
        if(!(targetPesawat.size() == 0)){
            notFound = false;
        }
        model.addAttribute("notFound", notFound);

        return "pesawat-filter";
    }
    @GetMapping(value = "/pesawat/pesawat-tua")
    public String pesawatTua(
            Model model){

        LocalDate dateNow = LocalDate.now();

        List<PesawatModel> listSemuaPesawat = pesawatService.getAll();
        List<PesawatModel> listPesawat = new ArrayList<PesawatModel>();
        List<Integer> listTahun = new ArrayList<Integer>();

        for (PesawatModel p : listSemuaPesawat){
            LocalDate datePesawat = p.getTanggalDibuat().toLocalDate();
            int umur = dateNow.getYear() - datePesawat.getYear();
            if(umur >= 10){
                listPesawat.add(p);
                listTahun.add(umur);
            }
        }
        model.addAttribute("listPesawat", listPesawat);
        model.addAttribute("listTahun", listTahun);

        return "pesawat-tua";
    }

    @GetMapping(value = "/pesawat/jumlah-teknisi")
    public String jumlahTeknisi(
            Model model){

        List<PesawatModel> listPesawat = pesawatService.getAll();
        List<Integer> jumlahTeknisi = new ArrayList<Integer>();

        for (PesawatModel p : listPesawat){
            jumlahTeknisi.add(p.getListTeknisi().size());
        }

        model.addAttribute("listPesawat", listPesawat);
        model.addAttribute("jumlahTeknisi", jumlahTeknisi);

        return "jumlah-teknisi-pesawat";
    }
    @GetMapping(value = "/pesawat/hapus/{id}")
    public String deletePesawat(
            @PathVariable Long id,
            Model model){

        PesawatModel pesawat = pesawatService.getPesawatById(id);
        pesawatService.delete(pesawat);
        model.addAttribute("pesawat", pesawat);
        return "delete-pesawat";
    }

}
