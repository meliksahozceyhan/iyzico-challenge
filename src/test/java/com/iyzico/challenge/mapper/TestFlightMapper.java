package com.iyzico.challenge.mapper;

import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.FlightRequestDto;
import com.iyzico.challenge.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestFlightMapper {

    private FlightMapper flightMapper;
    private TestDataGenerator testDataGenerator;
    @BeforeEach
    void setupTestFlightMapper(){
        testDataGenerator = new TestDataGenerator();
        flightMapper = new FlightMapper();
    }

    @Test
    void toEntityTest_whenToEntityCalledWithFlightRequestDto_shouldReturnFlightWithCorrectValues(){
        //given
        FlightRequestDto testFlightRequestDto = testDataGenerator.generateFlightRequestDtoForTest();

        //then
        Flight result = flightMapper.toEntity(testFlightRequestDto);
        assertEquals(testFlightRequestDto.getDescription(),result.getDescription());
        assertEquals(testFlightRequestDto.getName(), result.getName());
        assertEquals(testFlightRequestDto.getFromLocation(), result.getFromLocation());
        assertEquals(testFlightRequestDto.getToLocation(), result.getToLocation());
    }

    @Test
    void updateEntityTest_whenUpdateEntityCalledWithFlightAndFlightRequestDto_shouldReturnFlightWithCorrectValues(){
        //given
        Flight testFlight = testDataGenerator.generateFlightForTest();
        FlightRequestDto testFlightRequestDto = new FlightRequestDto();
        testFlightRequestDto.setFromLocation("Updated fromLocation");
        testFlightRequestDto.setToLocation("Updated toLocation");
        testFlightRequestDto.setName("Updated name");
        testFlightRequestDto.setDescription("Updated description");

        //then
        Flight result = flightMapper.updateEntity(testFlight,testFlightRequestDto);

        assertEquals(testFlightRequestDto.getDescription(),result.getDescription());
        assertEquals(testFlightRequestDto.getName(), result.getName());
        assertEquals(testFlightRequestDto.getFromLocation(), result.getFromLocation());
        assertEquals(testFlightRequestDto.getToLocation(), result.getToLocation());
    }
}
