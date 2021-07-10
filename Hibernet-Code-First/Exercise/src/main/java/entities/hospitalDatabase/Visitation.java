package entities.hospitalDatabase;

import entities.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    private LocalDate date;
    private String comment;
    private Patient patients;



    public Visitation() {
    }
    @Column(name = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    public Patient getPatients() {
        return patients;
    }

    public void setPatients(Patient patient) {
        this.patients = patient;
    }
}
