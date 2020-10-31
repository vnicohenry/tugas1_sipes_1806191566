package apap.tugas.sipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "teknisi")
public class TeknisiModel implements Serializable{

    @Id
    @Size(max = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "namaTeknisi", nullable = false)
    private String namaTeknisi;

    @NotNull
    @Column(name = "noTeleponTeknisi", nullable = false)
    private Long noTelepon;

    @OneToMany(mappedBy = "teknisi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PesawatTeknisiModel> listPesawatTeknisi;

    @ManyToMany(mappedBy = "listTeknisi")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PesawatModel> listPesawat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaTeknisi() {
        return namaTeknisi;
    }

    public void setNamaTeknisi(String namaTeknisi) {
        this.namaTeknisi = namaTeknisi;
    }

    public Long getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(Long noTelepon) {
        this.noTelepon = noTelepon;
    }

    public List<PesawatTeknisiModel> getListPesawatTeknisi() {
        return listPesawatTeknisi;
    }

    public void setListPesawatTeknisi(List<PesawatTeknisiModel> listPesawatTeknisi) {
        this.listPesawatTeknisi = listPesawatTeknisi;
    }
}
