package com.iyzico.challenge.controller;

import com.iyzico.challenge.core.exception.EntityNotFoundException;
import com.iyzico.challenge.core.exception.handler.DefaultExceptionHandler;
import com.iyzico.challenge.core.service.LocaleService;
import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.SeatRequestDto;
import com.iyzico.challenge.data.view.SeatListView;
import com.iyzico.challenge.data.view.SeatView;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestSeatController extends TestBaseRestController {

    @MockBean
    private SeatService seatService;

    private TestDataGenerator testDataGenerator;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setupTestSeatController() {
        PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver();
        testDataGenerator = new TestDataGenerator();
        mockMvc = MockMvcBuilders.standaloneSetup(new SeatController(seatService))
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(DefaultExceptionHandler.class)
                .build();
    }

    @Test
    void getSeatTest_whenGetSeatCalledWithValidId_shouldReturnSeatView() throws Exception {
        //given
        SeatView testSeatView = testDataGenerator.generateSeatViewForTest();

        //when
        when(seatService.getSeatById(testSeatView.getId())).thenReturn(testSeatView);

        //then
        mockMvc.perform(get("/api/seats/{id}", testSeatView.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(testSeatView.getId().intValue())))
                .andExpect(jsonPath("$.data.seatNumber", is(testSeatView.getSeatNumber())))
                .andExpect(jsonPath("$.data.isAvailable", is(testSeatView.getIsAvailable())))
                .andExpect(jsonPath("$.data.price", is(testSeatView.getPrice().doubleValue())))
                .andExpect(jsonPath("$.data.flight.id", is(testSeatView.getFlight().getId().intValue())))
                .andExpect(jsonPath("$.data.flight.name", is(testSeatView.getFlight().getName())))
                .andExpect(jsonPath("$.data.flight.fromLocation", is(testSeatView.getFlight().getFromLocation())))
                .andExpect(jsonPath("$.data.flight.toLocation", is(testSeatView.getFlight().getToLocation())))
                .andExpect(jsonPath("$.data.flight.description", is(testSeatView.getFlight().getDescription())))
                .andExpect(jsonPath("$.message", is(LocaleService.getMessage("seat.success"))))
        ;

        // verify
        verify(seatService, times(1)).getSeatById(testSeatView.getId());
    }

    @Test
    void getSeatTest_whenGetSeatCalledWithNonExistingId_shouldThrowEntityNotFoundException() throws Exception {
        //given
        Long nonExistingId = -1L;

        //when
        when(seatService.getSeatById(nonExistingId)).thenThrow(new EntityNotFoundException("seat"));

        //then
        mockMvc.perform(get("/api/seats/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.errorMessage", is(LocaleService.getMessage("errors.entity_not_found"))))
                .andExpect(jsonPath("$.causeMessage", is(LocaleService.getMessage("seat"))));

        //verify
        verify(seatService, times(1)).getSeatById(nonExistingId);
    }

    @Test
    void getSeatsOfFlightTest_whenGetSeatsOfFlightCalledWithFlightId_shouldReturnPageOfSeatListView() throws Exception {
        //given
        Flight testFlight = testDataGenerator.generateFlightForTest();
        Page<SeatListView> testSeatListView = testDataGenerator.getTestSeatListViewPage();
        Pageable pageable = PageRequest.of(0, 10);

        //when
        when(seatService.getAllSeatsAndPaginate(testFlight.getId(), pageable)).thenReturn(testSeatListView);

        //then
        mockMvc.perform(get("/api/seats/flight/{flightId}", testFlight.getId()).param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id", is(testSeatListView.getContent().get(0).getId().intValue())))
                .andExpect(jsonPath("$.data.content[0].seatNumber", is(testSeatListView.getContent().get(0).getSeatNumber())))
                .andExpect(jsonPath("$.data.content[0].price", is(testSeatListView.getContent().get(0).getPrice().doubleValue())))
                .andExpect(jsonPath("$.data.content[0].isAvailable", is(testSeatListView.getContent().get(0).getIsAvailable())))
                .andExpect(jsonPath("$.message", is(LocaleService.getMessage("seat.success"))));

        //verify
        verify(seatService, times(1)).getAllSeatsAndPaginate(testFlight.getId(), pageable);
    }

    @Test
    void saveSeatTest_whenSaveSeatCalledWithValidBody_shouldReturnSeatId() throws Exception {
        //given
        Seat testSeat = testDataGenerator.generateSeatForTest();
        SeatRequestDto testSeatRequestDto = testDataGenerator.generateSeatRequestDtoForTest();

        //when
        when(seatService.saveSeat(any(SeatRequestDto.class))).thenReturn(testSeat);

        //then
        mockMvc.perform(post("/api/seats")
                        .contentType("application/json")
                        .content(TestDataGenerator.convertToJsonString(testSeatRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data", is(testSeat.getId().intValue())))
                .andExpect(jsonPath("$.message", is(LocaleService.getMessage("seat.created"))));

        verify(seatService, times(1)).saveSeat(any(SeatRequestDto.class));
    }

    @Test
    void updateSeatTest_whenUpdateSeatCalledWithValidBodyAndId_shouldReturnSeat() throws Exception {
        //given
        Seat testSeat = testDataGenerator.generateSeatForTest();
        SeatRequestDto testSeatRequestDto = testDataGenerator.generateSeatRequestDtoForTest();

        //when
        when(seatService.updateSeat(eq(testSeat.getId()), any(SeatRequestDto.class))).thenReturn(testSeat);

        //then
        mockMvc.perform(put("/api/seats/{id}", testSeat.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataGenerator.convertToJsonString(testSeatRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(testSeat.getId().intValue())))
                .andExpect(jsonPath("$.message", is(LocaleService.getMessage("seat.updated"))));

        //verify
        verify(seatService, times(1)).updateSeat(eq(testSeat.getId()), any(SeatRequestDto.class));
    }

    @Test
    void deleteSeatTest_whenDeleteSeatCalledWithValidId_shouldReturnVoid() throws Exception {
        //given
        Long testSeatId = TestDataGenerator.TEST_SEAT_ID;

        //when
        doNothing().when(seatService).deleteSeat(testSeatId);

        //then
        mockMvc.perform(delete("/api/seats/{id}", testSeatId))
                .andExpect(status().isNoContent());

        //verify
        verify(seatService, times(1)).deleteSeat(testSeatId);
    }
}
