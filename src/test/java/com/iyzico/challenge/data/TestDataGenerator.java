package com.iyzico.challenge.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyzico.challenge.data.dto.*;
import com.iyzico.challenge.data.view.FlightListView;
import com.iyzico.challenge.data.view.FlightView;
import com.iyzico.challenge.data.view.SeatListView;
import com.iyzico.challenge.data.view.SeatView;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestDataGenerator {

    public static Long TEST_FLIGHT_ID = 1L;
    public static String TEST_FLIGHT_NAME = "TK1178";
    public static String TEST_FLIGHT_TO_LOCATION = "Vienna";
    public static String TEST_FLIGHT_FROM_LOCATION = "Ankara";
    public static String TEST_FLIGHT_DESCRIPTION = "Flight to Vienna From Ankara";

    public static Long TEST_SEAT_ID = 1L;
    public static String TEST_SEAT_NUMBER = "1A";
    public static BigDecimal TEST_SEAT_PRICE = BigDecimal.valueOf(150.0);
    public static boolean TEST_SEAT_AVAILABLE = false;


    public Flight generateFlightForTest() {
        Flight flight = new Flight() {
            @Override
            public Long getId() {
                return TEST_FLIGHT_ID;
            }

            @Override
            public List<Seat> getSeats() {
                List<Seat> seats = new ArrayList<>();
                seats.add(generateSeatForTest());
                return seats;
            }
        };

        flight.setId(TEST_FLIGHT_ID);
        flight.setName(TEST_FLIGHT_NAME);
        flight.setToLocation(TEST_FLIGHT_TO_LOCATION);
        flight.setFromLocation(TEST_FLIGHT_FROM_LOCATION);
        flight.setDescription(TEST_FLIGHT_DESCRIPTION);


        return flight;
    }

    public Seat generateSeatForTest() {
        Seat seat = new Seat() {
            @Override
            public Long getId() {
                return TEST_SEAT_ID;
            }
        };

        seat.setSeatNumber(TEST_SEAT_NUMBER);
        seat.setPrice(TEST_SEAT_PRICE);
        seat.setAvailable(TEST_SEAT_AVAILABLE);
        seat.setAvailable(true);
        seat.setFlight(generateFlightForTest());
        seat.setId(TEST_SEAT_ID);
        return seat;
    }

    public Seat generateNotAvailableSeatForTest() {
        Seat seat = new Seat() {
            @Override
            public Long getId() {
                return TEST_SEAT_ID;
            }
        };

        seat.setSeatNumber(TEST_SEAT_NUMBER);
        seat.setPrice(TEST_SEAT_PRICE);
        seat.setAvailable(TEST_SEAT_AVAILABLE);
        seat.setAvailable(false);
        seat.setFlight(generateFlightForTest());

        return seat;
    }

    public SeatListView generateSeatListViewForTest() {
        return new SeatListView() {
            @Override
            public Long getId() {
                return TEST_FLIGHT_ID;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public LocalDateTime getUpdatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public String getSeatNumber() {
                return TEST_SEAT_NUMBER;
            }

            @Override
            public Boolean getIsAvailable() {
                return TEST_SEAT_AVAILABLE;
            }

            @Override
            public BigDecimal getPrice() {
                return TEST_SEAT_PRICE;
            }
        };
    }

    public SeatView generateSeatViewForTest() {
        return new SeatView() {
            @Override
            public Long getId() {
                return TEST_FLIGHT_ID;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public LocalDateTime getUpdatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public String getSeatNumber() {
                return TEST_SEAT_NUMBER;
            }

            @Override
            public Boolean getIsAvailable() {
                return TEST_SEAT_AVAILABLE;
            }

            @Override
            public BigDecimal getPrice() {
                return TEST_SEAT_PRICE;
            }

            @Override
            public FlightListView getFlight() {
                return generateFlightListViewForTest();
            }
        };
    }

    private FlightListView generateFlightListViewForTest() {
        return new FlightListView() {
            @Override
            public Long getId() {
                return TEST_FLIGHT_ID;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public LocalDateTime getUpdatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public String getName() {
                return TEST_FLIGHT_NAME;
            }

            @Override
            public String getFromLocation() {
                return TEST_FLIGHT_FROM_LOCATION;
            }

            @Override
            public String getToLocation() {
                return TEST_FLIGHT_TO_LOCATION;
            }

            @Override
            public String getDescription() {
                return TEST_FLIGHT_DESCRIPTION;
            }
        };
    }

    public FlightView generateFlightViewForTest() {
        return new FlightView() {
            @Override
            public Long getId() {
                return TEST_FLIGHT_ID;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public LocalDateTime getUpdatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public String getName() {
                return TEST_FLIGHT_NAME;
            }

            @Override
            public String getFromLocation() {
                return TEST_FLIGHT_FROM_LOCATION;
            }

            @Override
            public String getToLocation() {
                return TEST_FLIGHT_TO_LOCATION;
            }

            @Override
            public String getDescription() {
                return TEST_FLIGHT_DESCRIPTION;
            }

            @Override
            public List<SeatListView> getSeats() {
                List<SeatListView> seatListViews = new ArrayList<>();
                seatListViews.add(generateSeatListViewForTest());
                return seatListViews;
            }
        };
    }

    public FlightRequestDto generateFlightRequestDtoForTest() {
        FlightRequestDto flightRequestDto = new FlightRequestDto();
        flightRequestDto.setName(TEST_FLIGHT_NAME);
        flightRequestDto.setToLocation(TEST_FLIGHT_TO_LOCATION);
        flightRequestDto.setFromLocation(TEST_FLIGHT_FROM_LOCATION);
        flightRequestDto.setDescription(TEST_FLIGHT_DESCRIPTION);
        return flightRequestDto;
    }

    public SeatRequestDto generateSeatRequestDtoForTest() {
        SeatRequestDto seatRequestDto = new SeatRequestDto();
        seatRequestDto.setSeatNumber(TEST_SEAT_NUMBER);
        seatRequestDto.setPrice(TEST_SEAT_PRICE);
        seatRequestDto.setFlightId(TEST_FLIGHT_ID);
        return seatRequestDto;
    }

    public Page<FlightView> getTestFlightViewPage() {
        return new PageImpl<>(Collections.singletonList(generateFlightViewForTest()));
    }

    public Page<SeatListView> getTestSeatListViewPage() {
        return new PageImpl<>(Collections.singletonList(generateSeatListViewForTest()));
    }

    public AddressDto generateShippingAddressDtoForTest(){
        AddressDto shippingAddress = new AddressDto();
        shippingAddress.setContactName("Jane Doe");
        shippingAddress.setCity("Istanbul");
        shippingAddress.setCountry("Turkey");
        shippingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        shippingAddress.setZipCode("34742");
        return shippingAddress;
    }

    public AddressDto generateBillingAddressDtoForTest(){
        AddressDto billingAddress = new AddressDto();
        billingAddress.setContactName("Jane Doe");
        billingAddress.setCity("Istanbul");
        billingAddress.setCountry("Turkey");
        billingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        billingAddress.setZipCode("34742");
        return billingAddress;
    }

    public BuyerDto generateBuyerDtoForTest(){
        BuyerDto buyer = new BuyerDto();
        buyer.setId("BY789");
        buyer.setName("John");
        buyer.setSurname("Doe");
        buyer.setGsmNumber("+905350000000");
        buyer.setEmail("email@email.com");
        buyer.setIdentityNumber("74300864791");
        buyer.setLastLoginDate("2015-10-05 12:43:35");
        buyer.setRegistrationDate("2013-04-21 15:12:09");
        buyer.setRegistrationAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        buyer.setIp("85.34.78.112");
        buyer.setCity("Istanbul");
        buyer.setCountry("Turkey");
        buyer.setZipCode("34732");
        return buyer;
    }

    public PaymentCardDto generatePaymentCardDtoForTest(){
        PaymentCardDto paymentCard = new PaymentCardDto();
        paymentCard.setCardHolderName("John Doe");
        paymentCard.setCardNumber("5528790000000008");
        paymentCard.setExpireMonth("12");
        paymentCard.setExpireYear("2030");
        paymentCard.setCvc("123");
        return paymentCard;
    }

    public SeatPurchaseRequestDto generateSeatPurchaseRequestDtoForTest(){
        SeatPurchaseRequestDto seatPurchaseRequestDto = new SeatPurchaseRequestDto();
        seatPurchaseRequestDto.setSeatId(TEST_SEAT_ID);
        seatPurchaseRequestDto.setBuyer(generateBuyerDtoForTest());
        seatPurchaseRequestDto.setBillingAddress(generateBillingAddressDtoForTest());
        seatPurchaseRequestDto.setShippingAddress(generateShippingAddressDtoForTest());
        seatPurchaseRequestDto.setPaymentCard(generatePaymentCardDtoForTest());
        return seatPurchaseRequestDto;
    }

    public PaymentCard generatePaymentCardForTest(){
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName("John Doe");
        paymentCard.setCardNumber("5528790000000008");
        paymentCard.setExpireMonth("12");
        paymentCard.setExpireYear("2030");
        paymentCard.setCvc("123");
        paymentCard.setRegisterCard(0);
        return paymentCard;
    }

    public Buyer generateBuyerForTest(){
        Buyer buyer = new Buyer();
        buyer.setId("BY789");
        buyer.setName("John");
        buyer.setSurname("Doe");
        buyer.setGsmNumber("+905350000000");
        buyer.setEmail("email@email.com");
        buyer.setIdentityNumber("74300864791");
        buyer.setLastLoginDate("2015-10-05 12:43:35");
        buyer.setRegistrationDate("2013-04-21 15:12:09");
        buyer.setRegistrationAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        buyer.setIp("85.34.78.112");
        buyer.setCity("Istanbul");
        buyer.setCountry("Turkey");
        buyer.setZipCode("34732");
        return buyer;
    }

    public Address generateShippingAddressForTest(){
        Address shippingAddress = new Address();
        shippingAddress.setContactName("Jane Doe");
        shippingAddress.setCity("Istanbul");
        shippingAddress.setCountry("Turkey");
        shippingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        shippingAddress.setZipCode("34742");
        return shippingAddress;
    }

    public Address generateBillingAddressForTest(){
        Address billingAddress = new Address();
        billingAddress.setContactName("Jane Doe");
        billingAddress.setCity("Istanbul");
        billingAddress.setCountry("Turkey");
        billingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        billingAddress.setZipCode("34742");
        return billingAddress;
    }

    public BankPaymentResponse generateBankPaymentResponseForTest(){
        return new BankPaymentResponse("success");
    }
    public BasketItem generateBasketItemForTest(){
        BasketItem basketItem = new BasketItem();
        basketItem.setId("BI101");
        basketItem.setName(TEST_SEAT_NUMBER);
        basketItem.setCategory1("Flight");
        basketItem.setCategory2("Seat");
        basketItem.setItemType(BasketItemType.VIRTUAL.name());
        basketItem.setPrice(TEST_SEAT_PRICE);

        return basketItem;
    }
    public static String convertToJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
