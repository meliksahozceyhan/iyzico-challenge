package com.iyzico.challenge.data.dto;

public class BankPaymentResponse {

    private String resultCode;

    public BankPaymentResponse(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}

