package com.iyzico.challenge.service;

import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.BankPaymentResponse;
import com.iyzico.challenge.data.dto.SeatPurchaseRequestDto;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.mapper.AddressMapper;
import com.iyzico.challenge.mapper.BuyerMapper;
import com.iyzico.challenge.mapper.PaymentCardMapper;
import com.iyzipay.Options;
import com.iyzipay.model.Payment;
import com.iyzipay.request.CreatePaymentRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestIyzicoPaymentIntegrationService {

    private AddressMapper addressMapper;

    private BuyerMapper buyerMapper;

    private PaymentCardMapper paymentCardMapper;

    private TestDataGenerator testDataGenerator;

    private IyzicoPaymentIntegrationService iyzicoPaymentIntegrationService;

    private IyzicoPaymentClient iyzicoPaymentClient;

    @BeforeEach
    void setupTestIyzicoPaymentIntegrationService() {
        //mock
        addressMapper = mock(AddressMapper.class);
        buyerMapper = mock(BuyerMapper.class);
        paymentCardMapper = mock(PaymentCardMapper.class);
        iyzicoPaymentClient = mock(IyzicoPaymentClient.class);
        testDataGenerator = new TestDataGenerator();

        iyzicoPaymentIntegrationService = new IyzicoPaymentIntegrationService(addressMapper, buyerMapper, paymentCardMapper,iyzicoPaymentClient);
    }

    @Test
    void paySeatTest_whenCalledWithSeatAndSeatPurchaseDto_shouldReturnBankPaymentResponse() {
        //given
        Seat testSeat = testDataGenerator.generateSeatForTest();
        SeatPurchaseRequestDto testSeatPurchaseRequestDto = testDataGenerator.generateSeatPurchaseRequestDtoForTest();
        BankPaymentResponse testBankPaymentResponse = testDataGenerator.generateBankPaymentResponseForTest();

        //when
        when(addressMapper.toAddress(testSeatPurchaseRequestDto.getBillingAddress())).thenReturn(testDataGenerator.generateBillingAddressForTest());
        when(addressMapper.toAddress(testSeatPurchaseRequestDto.getShippingAddress())).thenReturn(testDataGenerator.generateShippingAddressForTest());
        when(buyerMapper.toBuyer(testSeatPurchaseRequestDto.getBuyer())).thenReturn(testDataGenerator.generateBuyerForTest());
        when(paymentCardMapper.toPaymentCard(testSeatPurchaseRequestDto.getPaymentCard())).thenReturn(testDataGenerator.generatePaymentCardForTest());
        when(iyzicoPaymentClient.payWithIyzico(any())).thenReturn(new BankPaymentResponse("success"));

        //then
        BankPaymentResponse result = iyzicoPaymentIntegrationService.paySeat(testSeat, testSeatPurchaseRequestDto);

        assertEquals(testBankPaymentResponse.getResultCode(), result.getResultCode());

    }
}
