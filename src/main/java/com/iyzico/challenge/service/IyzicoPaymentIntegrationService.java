package com.iyzico.challenge.service;

import com.iyzico.challenge.data.dto.BankPaymentResponse;
import com.iyzico.challenge.data.dto.SeatPurchaseRequestDto;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.mapper.AddressMapper;
import com.iyzico.challenge.mapper.BuyerMapper;
import com.iyzico.challenge.mapper.PaymentCardMapper;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class IyzicoPaymentIntegrationService {


    private final AddressMapper addressMapper;

    private final BuyerMapper buyerMapper;

    private final PaymentCardMapper paymentCardMapper;

    private final IyzicoPaymentClient iyzicoPaymentClient;

    public IyzicoPaymentIntegrationService(AddressMapper addressMapper, BuyerMapper buyerMapper, PaymentCardMapper paymentCardMapper, IyzicoPaymentClient iyzicoPaymentClient) {
        this.addressMapper = addressMapper;
        this.buyerMapper = buyerMapper;
        this.paymentCardMapper = paymentCardMapper;
        this.iyzicoPaymentClient = iyzicoPaymentClient;
    }

    public BankPaymentResponse paySeat(Seat seat, SeatPurchaseRequestDto dto){
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId("123456789");
        request.setPrice(seat.getPrice());
        request.setPaidPrice(seat.getPrice());
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);
        request.setBasketId("B67832");
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());

        request.setPaymentCard(paymentCardMapper.toPaymentCard(dto.getPaymentCard()));
        request.setBuyer(buyerMapper.toBuyer(dto.getBuyer()));
        request.setBillingAddress(addressMapper.toAddress(dto.getBillingAddress()));
        request.setShippingAddress(addressMapper.toAddress(dto.getShippingAddress()));
        request.setBasketItems(Collections.singletonList(createBasketItemFromSeat(seat)));

        return iyzicoPaymentClient.payWithIyzico(request);
    }


    private BasketItem createBasketItemFromSeat(Seat seat){
        BasketItem basketItem = new BasketItem();
        basketItem.setId("BI101");
        basketItem.setName(seat.getSeatNumber());
        basketItem.setCategory1("Flight");
        basketItem.setCategory2("Seat");
        basketItem.setItemType(BasketItemType.VIRTUAL.name());
        basketItem.setPrice(seat.getPrice());

        return basketItem;
    }
}
