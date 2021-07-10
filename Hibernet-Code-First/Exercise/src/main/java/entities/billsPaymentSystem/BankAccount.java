package entities.billsPaymentSystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BillingDetail {

    private String bankName;
    private String swift;

    public BankAccount() {
    }

    public String getBankName() {
        return bankName;
    }

    @Column(name = "bank_name",length = 50)
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "swift")
    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }
}
