package apap.tugas.sipes.controller;

import apap.tugas.sipes.model.*;
import apap.tugas.sipes.service.PenerbanganService;
import apap.tugas.sipes.model.PenerbanganModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PenerbanganController {

    @Autowired
    private PenerbanganService penerbanganService;

    @GetMapping("/penerbangan")
    public String listPenerbangan(Model model){

        List<PenerbanganModel> listPenerbangan = penerbanganService.getAll();
        model.addAttribute("listPenerbangan", listPenerbangan);

        List<String> formattedDate = new ArrayList<String>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for(PenerbanganModel pm : listPenerbangan){
            String formatDateTime = pm.getWaktuBerangkat().format(formatter);
            formattedDate.add(formatDateTime);
        }
        model.addAttribute("listWaktuPenerbangan", formattedDate);

        return "view-all-penerbangan";
    }

    @GetMapping(value="/penerbangan/tambah")
    public String addPenerbanganFormPage(Model model){

        PenerbanganModel penerbangan = new PenerbanganModel();
        model.addAttribute("penerbangan", penerbangan);

        return "form-add-penerbangan";
    }

    @PostMapping(value = "/penerbangan/tambah")
    public String addPenerbanganSubmit(
            @ModelAttribute PenerbanganModel penerbangan,
            Model model){

        List<String> listNoPenerbangan = penerbanganService.getAllNomorPenerbangan();

        if (penerbangan.getNomorPenerbangan().length() != 16 ||
            listNoPenerbangan.contains(penerbangan.getNomorPenerbangan())){
            String errorNoPenerbangan = "Kode penerbangan harus 16 digit yang unik!";
            model.addAttribute("error", errorNoPenerbangan);
            model.addAttribute("penerbangan", penerbangan);
            return "form-add-penerbangan";
        }

        penerbanganService.add(penerbangan);
        model.addAttribute("penerbangan", penerbangan);
        return "add-penerbangan";
    }

    @GetMapping(value = "/penerbangan/{id}")
    public String viewDetailPenerbangan(
            @PathVariable Long id,
            Model model){

        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
        model.addAttribute("penerbangan", penerbangan);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String waktuBerangkat = penerbangan.getWaktuBerangkat().format(formatter);
        model.addAttribute("waktuBerangkat", waktuBerangkat);

        return "view-penerbangan";
    }

    @GetMapping(value = "/penerbangan/ubah/{id}")
    public String changePenerbanganForm(
            @PathVariable Long id,
            Model model){

        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
        model.addAttribute("penerbangan", penerbangan);

        LocalDateTime date = LocalDateTime.now();
        model.addAttribute("date", date);

        return "form-change-penerbangan";
    }

    @PostMapping(value = "/penerbangan/ubah")
    public String changePenerbanganSubmit(
            @ModelAttribute PenerbanganModel penerbangan,
            Model model){
        List<String> listNoPenerbangan = penerbanganService.getAllNomorPenerbangan();

        if (penerbangan.getNomorPenerbangan().length() != 16 ||
                listNoPenerbangan.contains(penerbangan.getNomorPenerbangan())){
            String errorNoPenerbangan = "Kode penerbangan harus 16 digit yang unik!";
            model.addAttribute("error", errorNoPenerbangan);
            model.addAttribute("penerbangan", penerbangan);
            return "form-change-penerbangan";
        }

        PenerbanganModel penerbanganBaru = penerbanganService.update(penerbangan);

        model.addAttribute("penerbangan", penerbanganBaru);

        return "change-penerbangan";
    }

    @PostMapping(value = "/penerbangan/hapus/{id}")
    public String deletePenerbangan(
            @PathVariable Long id,
            Model model){

        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
        penerbanganService.delete(penerbangan);
        model.addAttribute("penerbangan", penerbangan);
        return "delete-penerbangan";
    }
}
