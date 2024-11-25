package com.iyzico.challenge.mapper;

import com.iyzico.challenge.data.dto.AddressDto;
import com.iyzipay.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toAddress(AddressDto dto){
        Address address = new Address();
        address.setContactName(dto.getContactName());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setAddress(dto.getAddress());
        address.setZipCode(dto.getZipCode());
        return address;
    }
}
