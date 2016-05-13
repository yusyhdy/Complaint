package com.smkpgri2.alaska.core.commons;

import java.io.Serializable;

/**
 * Created by smkpgri2 on 13/05/16.
 */
public class Bank implements Serializable {
    private String bankName;
    private String accountName;
    private String accountNumber;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}

