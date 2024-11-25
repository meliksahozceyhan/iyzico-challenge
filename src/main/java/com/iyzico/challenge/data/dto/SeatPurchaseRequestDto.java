package com.iyzico.challenge.data.dto;

public class SeatPurchaseRequestDto {
    private Long seatId;

    private AddressDto shippingAddress;

    private AddressDto billingAddress;

    private BuyerDto buyer;

    private PaymentCardDto paymentCard;

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public AddressDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressDto getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDto billingAddress) {
        this.billingAddress = billingAddress;
    }

    public BuyerDto getBuyer() {
        return buyer;
    }

    public void setBuyer(BuyerDto buyer) {
        this.buyer = buyer;
    }

    public PaymentCardDto getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCardDto paymentCard) {
        this.paymentCard = paymentCard;
    }
}
