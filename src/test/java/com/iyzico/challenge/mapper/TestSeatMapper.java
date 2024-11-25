package com.iyzico.challenge.mapper;

import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.SeatRequestDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestSeatMapper {

    private SeatMapper seatMapper;

    private FlightService flightService;

    private TestDataGenerator testDataGenerator;

    @BeforeEach
    void setupTestSeatMapper(){
        testDataGenerator = new TestDataGenerator();
        flightService = mock(FlightService.class);
        seatMapper = new SeatMapper(flightService);
    }

    @Test
    void toEntityTest_whenToEntityCalledWithSeatRequestDto_shouldReturnSeat(){
        //given
        SeatRequestDto testSeatRequestDto = testDataGenerator.generateSeatRequestDtoForTest();
        Flight testFlight = testDataGenerator.generateFlightForTest();

        //when
        when(flightService.findFlightById(testSeatRequestDto.getFlightId())).thenReturn(testFlight);

        //then
        Seat result = seatMapper.toEntity(testSeatRequestDto);
        assertEquals(testSeatRequestDto.getSeatNumber(),result.getSeatNumber());
        assertEquals(testSeatRequestDto.getPrice(),result.getPrice());
        assertEquals(testSeatRequestDto.getFlightId(),result.getFlight().getId());

        //verify
        verify(flightService,times(1)).findFlightById(testSeatRequestDto.getFlightId());
    }

    @Test
    void updateEntityTest_whenUpdateEntityCalledWithSeatAndSeatRequestDto_shouldReturnSeat(){
        //given
        Seat testSeat = testDataGenerator.generateSeatForTest();
        Flight testFlight = testDataGenerator.generateFlightForTest();
        SeatRequestDto testSeatRequestDto = new SeatRequestDto();
        testSeatRequestDto.setSeatNumber("Updated seatNumber");
        testSeatRequestDto.setPrice(BigDecimal.valueOf(200));
        testSeatRequestDto.setFlightId(TestDataGenerator.TEST_FLIGHT_ID);

        //when
        when(flightService.findFlightById(testSeatRequestDto.getFlightId())).thenReturn(testFlight);

        //then
        Seat result = seatMapper.updateEntity(testSeat,testSeatRequestDto);
        assertEquals(testSeatRequestDto.getSeatNumber(),result.getSeatNumber());
        assertEquals(testSeatRequestDto.getPrice(),result.getPrice());
        assertEquals(testSeatRequestDto.getFlightId(),result.getFlight().getId());

        //verify
        verify(flightService,times(1)).findFlightById(testSeatRequestDto.getFlightId());
    }
}
