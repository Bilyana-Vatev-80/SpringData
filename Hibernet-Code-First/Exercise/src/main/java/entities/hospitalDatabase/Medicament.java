package entities.hospitalDatabase;

import entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "midicaments")
public class Medicament extends BaseEntity {
    private String name;
    private Set<Patient> patientSet;

    public Medicament() {
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @ManyToMany(mappedBy = "medicaments", targetEntity = Patient.class)
    public Set<Patient> getPatientSet() {
        return patientSet;
    }

    public void setPatientSet(Set<Patient> patientSet) {
        this.patientSet = patientSet;
    }

}
