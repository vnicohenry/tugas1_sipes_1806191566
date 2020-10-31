package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PesawatTeknisiModel;
import apap.tugas.sipes.repository.PesawatTeknisiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PesawatTeknisiServiceImpl implements PesawatTeknisiService{

    @Autowired
    PesawatTeknisiDb pesawatTeknisiDb;

    @Override
    public void add(PesawatTeknisiModel pesawatTeknisi) {
        pesawatTeknisiDb.save(pesawatTeknisi);
    }
}
