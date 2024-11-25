package com.iyzico.challenge.service;

import com.iyzico.challenge.core.exception.EntityNotFoundException;
import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.FlightRequestDto;
import com.iyzico.challenge.data.view.FlightView;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.mapper.FlightMapper;
import com.iyzico.challenge.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TestFlightService {

    private FlightService flightService;

    private FlightRepository flightRepository;

    private FlightMapper flightMapper;

    private TestDataGenerator dataGenerator;

    @BeforeEach
    void setupTestFlightService(){
        //Mock necessary classes
        flightRepository = mock(FlightRepository.class);
        flightMapper = mock(FlightMapper.class);
        //Instantiate Data Generator
        dataGenerator = new TestDataGenerator();
        //Instantiate Service to be tested.
        flightService = new FlightService(flightRepository,flightMapper);
    }

    @Test
    void getFlightWithAvailableSeatsByIdTest_whenGetFlightWithAvailableSeatsByIdCalledWithExistedId_shouldReturnFlightViewWithGivenId(){
        //given values
        FlightView testFlightView = dataGenerator.generateFlightViewForTest();

        //when
        when(flightRepository.getFlightWithAvailableSeats(testFlightView.getId())).thenReturn(Optional.of(testFlightView));

        //then
        FlightView result = flightService.getFlightWithAvailableSeatsById(testFlightView.getId());
        assertEquals(testFlightView,result);

        //verify
        verify(flightRepository,times(1)).getFlightWithAvailableSeats(testFlightView.getId());
    }

    @Test
    void getFlightWithAvailableSeatsByIdTest_whenGetFlightWithAvailableSeatsByIdCalledWithNonExistingId_shouldReturnEntityNotFoundException(){
        //given values
        Long nonExistingId = -1L;
        //when
        when(flightRepository.getFlightWithAvailableSeats(nonExistingId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, ()-> flightService.getFlightWithAvailableSeatsById(nonExistingId));

        //verify
        verify(flightRepository,times(1)).getFlightWithAvailableSeats(nonExistingId);
    }

    @Test
    void getAllFlightsWithAvailableSeatsTest_whenGetAllFlightsWithAvailableSeatsCalledWithPageable_shouldReturnPageOfFlightView(){
        //given values
        Page<FlightView> testFlightViewPage = dataGenerator.getTestFlightViewPage();
        Pageable testPageable = PageRequest.of(0,10);

        //when
        when(flightRepository.getAllFlightsWithSeatsAvailable(any())).thenReturn(testFlightViewPage);

        //then
        Page<FlightView> result = flightService.getAllFlightsWithAvailableSeats(testPageable);
        assertEquals(testFlightViewPage,result);

        //verify
        verify(flightRepository,times(1)).getAllFlightsWithSeatsAvailable(any());
    }

    @Test
    void findFlightByIdTest_whenFindFlightByIdCalledWithExistedId_shouldReturnFlightWithGivenId(){
        //given values
        Flight testFlight = dataGenerator.generateFlightForTest();

        //when
        when(flightRepository.findById(testFlight.getId())).thenReturn(Optional.of(testFlight));

        //then
        Flight result = flightService.findFlightById(testFlight.getId());
        assertEquals(testFlight,result);

        //verify
        verify(flightRepository,times(1)).findById(testFlight.getId());
    }

    @Test
    void findFlightByIdTest_whenFindFlightByIdCalledWithNonExistingId_shouldReturnEntityNotFoundException(){
        //given values
        Long nonExistingId = -1L;

        //when
        when(flightRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class,()-> flightService.findFlightById(nonExistingId));

        //verify
        verify(flightRepository,times(1)).findById(nonExistingId);
    }

    @Test
    void saveFlightTest_whenSaveFlightCalledWithFlightRequestDto_shouldReturnFlight(){
        //given values
        Flight testFlight = dataGenerator.generateFlightForTest();
        FlightRequestDto testFlightRequestDto = dataGenerator.generateFlightRequestDtoForTest();

        //when
        when(flightMapper.toEntity(testFlightRequestDto)).thenReturn(testFlight);
        when(flightRepository.save(testFlight)).thenReturn(testFlight);

        //then
        Flight result = flightService.saveFlight(testFlightRequestDto);
        assertEquals(testFlight,result);

        //verify
        verify(flightRepository,times(1)).save(testFlight);
        verify(flightMapper,times(1)).toEntity(testFlightRequestDto);
    }

    @Test
    void updateFlightTest_whenUpdateFlightCalledWithIdAndFlightRequestDto_shouldReturnFlight(){
        //given
        Flight testFlight = dataGenerator.generateFlightForTest();
        FlightRequestDto testFlightRequestDto = dataGenerator.generateFlightRequestDtoForTest();

        //when
        when(flightRepository.findById(testFlight.getId())).thenReturn(Optional.of(testFlight));
        when(flightMapper.updateEntity(testFlight,testFlightRequestDto)).thenReturn(testFlight);
        when(flightRepository.save(testFlight)).thenReturn(testFlight);

        //then
        Flight result = flightService.updateFlight(testFlight.getId(), testFlightRequestDto);
        assertEquals(testFlight,result);

        //verify
        verify(flightRepository,times(1)).findById(testFlight.getId());
        verify(flightRepository,times(1)).save(testFlight);
        verify(flightMapper,times(1)).updateEntity(testFlight,testFlightRequestDto);
    }


    @Test
    void deleteFlightTest_whenDeleteFlightCalledWithExistingId_shouldReturnVoid(){
        //given
        Flight testFlight = dataGenerator.generateFlightForTest();

        //when
        when(flightRepository.findById(testFlight.getId())).thenReturn(Optional.of(testFlight));

        //then
        flightService.deleteFlight(testFlight.getId());

        //verify
        verify(flightRepository,times(1)).findById(testFlight.getId());
        verify(flightRepository,times(1)).delete(testFlight);
    }

    @Test
    void deleteFlightTest_whenDeleteFlightCalledWithNonExistingId_shouldThrowEntityNotFoundException(){
        //given
        Long nonExistingId = -1L;

        //when
        when(flightRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, ()-> flightService.deleteFlight(nonExistingId));

        //verify
        verify(flightRepository,times(1)).findById(nonExistingId);
    }
}
