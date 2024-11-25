package com.iyzico.challenge.mapper;

import com.iyzico.challenge.data.dto.BuyerDto;
import com.iyzipay.model.Buyer;
import org.springframework.stereotype.Component;

@Component
public class BuyerMapper {

    public Buyer toBuyer(BuyerDto dto){
        Buyer buyer = new Buyer();

        buyer.setId(dto.getId());
        buyer.setName(dto.getName());
        buyer.setSurname(dto.getSurname());
        buyer.setGsmNumber(dto.getGsmNumber());
        buyer.setEmail(dto.getEmail());
        buyer.setIdentityNumber(dto.getIdentityNumber());
        buyer.setLastLoginDate(dto.getLastLoginDate());
        buyer.setRegistrationDate(dto.getRegistrationDate());
        buyer.setRegistrationAddress(dto.getRegistrationAddress());
        buyer.setIp(dto.getIp());
        buyer.setCity(dto.getCity());
        buyer.setCountry(dto.getCountry());
        buyer.setZipCode(dto.getZipCode());

        return buyer;
    }
}
