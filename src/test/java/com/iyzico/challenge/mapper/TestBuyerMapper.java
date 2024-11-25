package com.iyzico.challenge.mapper;

import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.BuyerDto;
import com.iyzipay.model.Buyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBuyerMapper {

    private BuyerMapper buyerMapper;

    private TestDataGenerator testDataGenerator;

    @BeforeEach
    void setupTestBuyerMapper(){
        testDataGenerator = new TestDataGenerator();
        buyerMapper = new BuyerMapper();
    }

    @Test
    void toBuyerTest_whenGivenBuyerDto_shouldReturnBuyer(){
        //given
        BuyerDto buyerDtoTest = testDataGenerator.generateBuyerDtoForTest();

        //then
        Buyer result = buyerMapper.toBuyer(buyerDtoTest);
        assertEquals(buyerDtoTest.getId(), result.getId());
        assertEquals(buyerDtoTest.getName(), result.getName());
        assertEquals(buyerDtoTest.getSurname(), result.getSurname());
        assertEquals(buyerDtoTest.getGsmNumber(), result.getGsmNumber());
        assertEquals(buyerDtoTest.getEmail(), result.getEmail());
        assertEquals(buyerDtoTest.getIdentityNumber(), result.getIdentityNumber());
        assertEquals(buyerDtoTest.getLastLoginDate(), result.getLastLoginDate());
        assertEquals(buyerDtoTest.getRegistrationDate(), result.getRegistrationDate());
        assertEquals(buyerDtoTest.getRegistrationAddress(), result.getRegistrationAddress());
        assertEquals(buyerDtoTest.getIp(), result.getIp());
        assertEquals(buyerDtoTest.getCity(), result.getCity());
        assertEquals(buyerDtoTest.getCountry(), result.getCountry());
        assertEquals(buyerDtoTest.getZipCode(), result.getZipCode());

    }
}
