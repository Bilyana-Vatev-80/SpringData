package entities.billsPaymentSystem;

import entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetail extends BaseEntity {
    private String number;
    private BankUser bankUser;

    public BillingDetail() {
    }

    @Column(name = "number",nullable = false,unique = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    public BankUser getUser() {
        return bankUser;
    }

    public void setUser(BankUser bankUser) {
        this.bankUser = bankUser;
    }
}
