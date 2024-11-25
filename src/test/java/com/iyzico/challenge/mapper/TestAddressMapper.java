package com.iyzico.challenge.mapper;

import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.AddressDto;
import com.iyzipay.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAddressMapper {

    private AddressMapper addressMapper;
    private TestDataGenerator testDataGenerator;
    @BeforeEach
    void setupTestAddressMapper(){
        testDataGenerator = new TestDataGenerator();
        addressMapper = new AddressMapper();
    }

    @Test
    void toAddressTest_whenGivenTestAddressDto_shouldReturnAddress(){
        //given
        AddressDto testBillingAddressDto = testDataGenerator.generateBillingAddressDtoForTest();

        //then
        Address result = addressMapper.toAddress(testBillingAddressDto);
        assertEquals(testBillingAddressDto.getAddress(),result.getAddress());
        assertEquals(testBillingAddressDto.getCity(),result.getCity());
        assertEquals(testBillingAddressDto.getContactName(),result.getContactName());
        assertEquals(testBillingAddressDto.getCountry(),result.getCountry());
        assertEquals(testBillingAddressDto.getZipCode(),result.getZipCode());
    }
}
