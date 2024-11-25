package com.iyzico.challenge.mapper;

import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.PaymentCardDto;
import com.iyzipay.model.PaymentCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPaymentCardMapper {

    private PaymentCardMapper paymentCardMapper;

    private TestDataGenerator testDataGenerator;

    @BeforeEach
    void setupTestPaymentCardMapper(){
        testDataGenerator = new TestDataGenerator();
        paymentCardMapper = new PaymentCardMapper();
    }

    @Test
    void toPaymentCardTest_whenGivenPaymentCardDto_shouldReturnPayment(){
        //given
        PaymentCardDto testPaymentCardDto = testDataGenerator.generatePaymentCardDtoForTest();

        //then
        PaymentCard result = paymentCardMapper.toPaymentCard(testPaymentCardDto);
        assertEquals(testPaymentCardDto.getCardHolderName(), result.getCardHolderName());
        assertEquals(testPaymentCardDto.getCardNumber(), result.getCardNumber());
        assertEquals(testPaymentCardDto.getExpireMonth(), result.getExpireMonth());
        assertEquals(testPaymentCardDto.getExpireYear(), result.getExpireYear());
        assertEquals(testPaymentCardDto.getCvc(), result.getCvc());
        assertEquals(0, result.getRegisterCard());

    }
}
