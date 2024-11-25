package com.iyzico.challenge.mapper;

import com.iyzico.challenge.data.dto.PaymentCardDto;
import com.iyzipay.model.PaymentCard;
import org.springframework.stereotype.Component;

@Component
public class PaymentCardMapper {

    public PaymentCard toPaymentCard(PaymentCardDto dto){
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(dto.getCardHolderName());
        paymentCard.setCardNumber(dto.getCardNumber());
        paymentCard.setExpireMonth(dto.getExpireMonth());
        paymentCard.setExpireYear(dto.getExpireYear());
        paymentCard.setCvc(dto.getCvc());
        paymentCard.setRegisterCard(0);
        return paymentCard;
    }
}
