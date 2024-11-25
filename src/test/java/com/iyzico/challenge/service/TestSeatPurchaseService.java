package com.iyzico.challenge.service;


import com.iyzico.challenge.core.exception.SeatPurchasedException;
import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.BankPaymentResponse;
import com.iyzico.challenge.data.dto.SeatPurchaseRequestDto;
import com.iyzico.challenge.entity.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TestSeatPurchaseService {
    private SeatPurchaseService seatPurchaseService;
    private SeatService seatService;
    private IyzicoPaymentIntegrationService iyzicoPaymentIntegrationService;

    private TestDataGenerator testDataGenerator;

    @BeforeEach
    void setupTestSeatPurchaseService() {
        testDataGenerator = new TestDataGenerator();
        seatService = mock(SeatService.class);
        iyzicoPaymentIntegrationService = mock(IyzicoPaymentIntegrationService.class);
        seatPurchaseService = new SeatPurchaseService(seatService, iyzicoPaymentIntegrationService);
    }

    @Test
    void purchaseSeatTest_whenTwoConcurrentRequestsHappen_OneShouldSucceedOneShouldFail() {
        //given
        Seat testSeat = testDataGenerator.generateSeatForTest();
        SeatPurchaseRequestDto testSeatPurchaseRequestDto = testDataGenerator.generateSeatPurchaseRequestDtoForTest();
        int concurrentRequestCount = 2;
        int desiredSuccessCount = 1;
        int successCount = 0;
        int failCount = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(concurrentRequestCount);
        AtomicBoolean seatAvailable = new AtomicBoolean(true);

        //when
        when(iyzicoPaymentIntegrationService.paySeat(testSeat, testSeatPurchaseRequestDto)).thenReturn(testDataGenerator.generateBankPaymentResponseForTest());
        when(seatService.purchaseSeat(testSeat.getId())).thenAnswer(ans -> {
            if (seatAvailable.getAndSet(false)) {
                testSeat.setAvailable(false);
                return testSeat;
            }
            throw new SeatPurchasedException("seat.purchased.exception");
        });

        //then
        List<CompletableFuture<BankPaymentResponse>> purchaseFutures = new ArrayList<>();
        for (int i = 0; i < concurrentRequestCount; i++) {
            purchaseFutures.add(CompletableFuture.supplyAsync(()-> {
                try {
                    return seatPurchaseService.purchaseSeat(testSeat.getId(),testSeatPurchaseRequestDto);
                }
                catch (Exception e){
                    throw new CompletionException(e);
                }
            },executorService));
        }

        for (CompletableFuture<BankPaymentResponse> future: purchaseFutures){
            try {
                BankPaymentResponse completedPayment = future.join();
                assertNotNull(completedPayment);
                successCount++;
            }
            catch (CompletionException e){
                failCount++;
                assertInstanceOf(SeatPurchasedException.class,e.getCause());
            }
        }

        assertEquals(desiredSuccessCount,successCount);
        assertEquals(concurrentRequestCount-desiredSuccessCount, failCount);

        verify(seatService,times(concurrentRequestCount)).purchaseSeat(testSeat.getId());
        verify(iyzicoPaymentIntegrationService,times(desiredSuccessCount)).paySeat(testSeat,testSeatPurchaseRequestDto);
    }
}
