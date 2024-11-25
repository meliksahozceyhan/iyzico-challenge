package com.iyzico.challenge.service;

import com.iyzico.challenge.data.dto.BankPaymentResponse;
import com.iyzipay.Options;
import com.iyzipay.model.Payment;
import com.iyzipay.request.CreatePaymentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IyzicoPaymentClient {
    @Value("${iyzico.api.key}")
    private String apiKey;

    @Value("${iyzico.api.secret}")
    private String secretKey;

    @Value("${iyzico.api.url}")
    private String baseUrl;


    public BankPaymentResponse payWithIyzico(CreatePaymentRequest paymentRequest) {
        Payment payment = Payment.create(paymentRequest, getOptions());

        return new BankPaymentResponse(payment.getStatus());
    }

    private Options getOptions() {
        Options options = new Options();
        options.setApiKey(apiKey);
        options.setSecretKey(secretKey);
        options.setBaseUrl(baseUrl);

        return options;
    }
}
