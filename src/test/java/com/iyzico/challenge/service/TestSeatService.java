package com.iyzico.challenge.service;

import com.iyzico.challenge.core.exception.EntityNotFoundException;
import com.iyzico.challenge.core.exception.SeatPurchasedException;
import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.SeatRequestDto;
import com.iyzico.challenge.data.view.SeatListView;
import com.iyzico.challenge.data.view.SeatView;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.mapper.SeatMapper;
import com.iyzico.challenge.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class TestSeatService {

    private SeatService seatService;

    private SeatMapper seatMapper;

    private SeatRepository seatRepository;

    private TestDataGenerator testDataGenerator;

    @BeforeEach
    void setupTestSeatService() {
        //Mock necessary classes
        seatMapper = mock(SeatMapper.class);
        seatRepository = mock(SeatRepository.class);

        //Instantiate Data Generator
        testDataGenerator = new TestDataGenerator();

        //Instantiate Service to be tested.
        seatService = new SeatService(seatRepository, seatMapper);
    }

    @Test
    void getAllSeatsAndPaginateTest_whenGetAllSeatsAndPaginateCalledWithFlightIdAndPageable_shouldReturnPageOfSeatListView() {
        // given
        Page<SeatListView> testSeatListView = testDataGenerator.getTestSeatListViewPage();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "updatedAt"));

        //when
        when(seatRepository.findAllByFlightId(TestDataGenerator.TEST_FLIGHT_ID, pageable)).thenReturn(testSeatListView);

        //then
        Page<SeatListView> result = seatService.getAllSeatsAndPaginate(TestDataGenerator.TEST_FLIGHT_ID, pageable);
        assertEquals(testSeatListView, result);

        //verify
        verify(seatRepository, times(1)).findAllByFlightId(TestDataGenerator.TEST_FLIGHT_ID, pageable);
    }

    @Test
    void getSeatByIdTest_whenGetSeatByIdCalledWithExistingId_shouldReturnSeatView() {
        //given
        SeatView testSeatView = testDataGenerator.generateSeatViewForTest();

        //when
        when(seatRepository.getSeatById(testSeatView.getId())).thenReturn(Optional.of(testSeatView));

        //then
        SeatView result = seatService.getSeatById(testSeatView.getId());
        assertEquals(testSeatView, result);

        //verify
        verify(seatRepository, times(1)).getSeatById(testSeatView.getId());
    }

    @Test
    void getSeatByIdTest_whenGetSeatByIdCalledWithNonExistingId_shouldThrowEntityNotFoundException() {
        //given
        Long nonExistingId = -1L;

        //when
        when(seatRepository.getSeatById(nonExistingId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> seatService.getSeatById(nonExistingId));

        //verify
        verify(seatRepository, times(1)).getSeatById(nonExistingId);
    }

    @Test
    void findSeatByIdTest_whenFindSeatByIdCalledWithExistingId_shouldReturnSeat() {
        //given
        Seat testSeat = testDataGenerator.generateSeatForTest();

        //when
        when(seatRepository.findById(testSeat.getId())).thenReturn(Optional.of(testSeat));

        //then
        Seat result = seatService.findSeatById(testSeat.getId());
        assertEquals(testSeat, result);

        //verify
        verify(seatRepository, times(1)).findById(testSeat.getId());
    }

    @Test
    void findSeatByIdTest_whenFindSeatByIdCalledWithNonExistingId_shouldThrowEntityNotFoundException() {
        //given
        Long nonExistingId = -1L;

        //when
        when(seatRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> seatService.findSeatById(nonExistingId));

        //verify
        verify(seatRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void saveSeatTest_whenSaveSeatCalledWithSeatRequestDto_ShouldReturnSeat() {
        //given
        SeatRequestDto testSeatRequestDto = testDataGenerator.generateSeatRequestDtoForTest();
        Seat testSeat = testDataGenerator.generateSeatForTest();

        //when
        when(seatMapper.toEntity(testSeatRequestDto)).thenReturn(testSeat);
        when(seatRepository.save(testSeat)).thenReturn(testSeat);

        //then
        Seat result = seatService.saveSeat(testSeatRequestDto);
        assertEquals(testSeat, result);

        //verify
        verify(seatMapper, times(1)).toEntity(testSeatRequestDto);
        verify(seatRepository, times(1)).save(testSeat);
    }

    @Test
    void updateSeatTest_whenUpdateSeatCalledWithExistingIdAndSeatRequestDto_shouldReturnSeat() {
        //given
        Seat testSeat = testDataGenerator.generateSeatForTest();
        SeatRequestDto testSeatRequestDto = testDataGenerator.generateSeatRequestDtoForTest();

        //when
        when(seatRepository.findById(testSeat.getId())).thenReturn(Optional.of(testSeat));
        when(seatMapper.updateEntity(testSeat, testSeatRequestDto)).thenReturn(testSeat);
        when(seatRepository.save(testSeat)).thenReturn(testSeat);

        //then
        Seat result = seatService.updateSeat(testSeat.getId(), testSeatRequestDto);
        assertEquals(testSeat, result);

        verify(seatRepository, times(1)).findById(testSeat.getId());
        verify(seatRepository, times(1)).save(testSeat);
        verify(seatMapper, times(1)).updateEntity(testSeat, testSeatRequestDto);
    }

    @Test
    void purchaseSeatTest_whenPurchaseSeatCalledWithExistingId_shouldReturnSeat() {
        //given
        Seat testSeat = testDataGenerator.generateSeatForTest();

        //when
        when(seatRepository.findSeatByIdForPurchase(testSeat.getId())).thenReturn(Optional.of(testSeat));
        when(seatRepository.save(testSeat)).thenReturn(testSeat);

        //then
        Seat result = seatService.purchaseSeat(testSeat.getId());
        assertEquals(testSeat, result);

        //verify
        verify(seatRepository, times(1)).findSeatByIdForPurchase(testSeat.getId());
        verify(seatRepository, times(1)).save(testSeat);
    }

    @Test
    void purchaseSeatTest_whenPurchaseSeatCalledWithNonExistingId_shouldThrowEntityNotFoundException() {
        //given
        Long nonExistingId = -1L;

        //when
        when(seatRepository.findSeatByIdForPurchase(nonExistingId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> seatService.purchaseSeat(nonExistingId));

        //verify
        verify(seatRepository, times(1)).findSeatByIdForPurchase(nonExistingId);
    }

    @Test
    void purchaseSeatTest_whenPurchaseSeatCalledWithExistingIdAndNotAvailableSeat_shouldThrowSeatPurchasedException() {
        //given
        Seat testSeatNotAvailable = testDataGenerator.generateNotAvailableSeatForTest();

        //when
        when(seatRepository.findSeatByIdForPurchase(testSeatNotAvailable.getId())).thenReturn(Optional.of(testSeatNotAvailable));

        //then
        assertThrows(SeatPurchasedException.class, () -> seatService.purchaseSeat(testSeatNotAvailable.getId()));

        //verify
        verify(seatRepository, times(1)).findSeatByIdForPurchase(testSeatNotAvailable.getId());
    }

    @Test
    void deleteSeatTest_whenDeleteSeatCalledWithExistingId_ShouldReturnVoid() {
        //given
        Seat testSeat = testDataGenerator.generateSeatForTest();

        //when
        when(seatRepository.findById(testSeat.getId())).thenReturn(Optional.of(testSeat));

        //then
        seatService.deleteSeat(testSeat.getId());

        //verify
        verify(seatRepository, times(1)).findById(testSeat.getId());
        verify(seatRepository, times(1)).delete(testSeat);
    }

    @Test
    void deleteSeatTest_whenDeleteSeatCalledWithNonExistingId_ShouldThrowEntityNotFoundException() {
        Long nonExistingId = -1L;

        //when
        when(seatRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> seatService.deleteSeat(nonExistingId));

        //verify
        verify(seatRepository, times(1)).findById(nonExistingId);
    }
}
