package entities.hospitalDatabase;

import entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseEntity {
    private String name;
    private String comment;
    private Set<Patient> patientSet;

    public Diagnose() {
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    @ManyToMany(mappedBy = "diagnoses", targetEntity = Patient.class)
    public Set<Patient> getPatientSet() {
        return patientSet;
    }

    public void setPatientSet(Set<Patient> patientSet) {
        this.patientSet = patientSet;
    }
}
