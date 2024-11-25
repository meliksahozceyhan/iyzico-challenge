package com.iyzico.challenge.controller;

import com.iyzico.challenge.core.exception.EntityNotFoundException;
import com.iyzico.challenge.core.exception.handler.DefaultExceptionHandler;
import com.iyzico.challenge.core.service.LocaleService;
import com.iyzico.challenge.data.TestDataGenerator;
import com.iyzico.challenge.data.dto.FlightRequestDto;
import com.iyzico.challenge.data.view.FlightView;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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

public class TestFlightController extends TestBaseRestController {

    @MockBean
    private FlightService flightService;

    @Autowired
    MockMvc mockMvc;

    private TestDataGenerator testDataGenerator;

    @BeforeEach
    void setUp() {
        PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver();
        testDataGenerator = new TestDataGenerator();
        mockMvc = MockMvcBuilders.standaloneSetup(new FlightController(flightService))
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(DefaultExceptionHandler.class)
                .build();
    }

    @Test
    void getAllAndPaginateTest_whenGetAllAndPaginateCalled_shouldReturnFlightViewList() throws Exception {
        //given
        FlightView testFlightView = testDataGenerator.generateFlightViewForTest();
        Pageable pageable = PageRequest.of(0, 10);

        //when
        when(flightService.getAllFlightsWithAvailableSeats(pageable)).thenReturn(testDataGenerator.getTestFlightViewPage());

        //then
        mockMvc.perform(get("/api/flights").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id", is(testFlightView.getId().intValue())))
                .andExpect(jsonPath("$.data.content[0].name", is(testFlightView.getName())))
                .andExpect(jsonPath("$.data.content[0].fromLocation", is(testFlightView.getFromLocation())))
                .andExpect(jsonPath("$.data.content[0].toLocation", is(testFlightView.getToLocation())))
                .andExpect(jsonPath("$.data.content[0].description", is(testFlightView.getDescription())))
                .andExpect(jsonPath("$.data.content[0].seats[0].id", is(testFlightView.getSeats().get(0).getId().intValue())))
                .andExpect(jsonPath("$.data.content[0].seats[0].seatNumber", is(testFlightView.getSeats().get(0).getSeatNumber())))
                .andExpect(jsonPath("$.data.content[0].seats[0].isAvailable", is(testFlightView.getSeats().get(0).getIsAvailable())))
                .andExpect(jsonPath("$.data.content[0].seats[0].price", is(testFlightView.getSeats().get(0).getPrice().doubleValue())))
                .andExpect(jsonPath("$.message", is(LocaleService.getMessage("flight.success"))));

        //verify
        verify(flightService, times(1)).getAllFlightsWithAvailableSeats(pageable);
    }

    @Test
    void getOneByIdTest_whenGetOneByIdCalledWithExistingId_shouldReturnFlightView() throws Exception {
        //given
        FlightView testFlightView = testDataGenerator.generateFlightViewForTest();
        Long testFlightId = 1L;

        //when
        when(flightService.getFlightWithAvailableSeatsById(testFlightId)).thenReturn(testFlightView);

        //then
        mockMvc.perform(get("/api/flights/{id}", testFlightId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(testFlightView.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is(testFlightView.getName())))
                .andExpect(jsonPath("$.data.fromLocation", is(testFlightView.getFromLocation())))
                .andExpect(jsonPath("$.data.toLocation", is(testFlightView.getToLocation())))
                .andExpect(jsonPath("$.data.description", is(testFlightView.getDescription())))
                .andExpect(jsonPath("$.data.seats[0].id", is(testFlightView.getSeats().get(0).getId().intValue())))
                .andExpect(jsonPath("$.data.seats[0].seatNumber", is(testFlightView.getSeats().get(0).getSeatNumber())))
                .andExpect(jsonPath("$.data.seats[0].isAvailable", is(testFlightView.getSeats().get(0).getIsAvailable())))
                .andExpect(jsonPath("$.data.seats[0].price", is(testFlightView.getSeats().get(0).getPrice().doubleValue())))
                .andExpect(jsonPath("$.message", is(LocaleService.getMessage("flight.success"))));

        //verify
        verify(flightService, times(1)).getFlightWithAvailableSeatsById(testFlightId);
    }

    @Test
    void getOneByIdTest_whenGetOneByIdCalledWithNonExistingId_shouldThrowEntityNotFoundException() throws Exception {
        //given
        Long nonExistingTestFlightId = -1L;

        //when
        when(flightService.getFlightWithAvailableSeatsById(nonExistingTestFlightId)).thenThrow(new EntityNotFoundException("flight"));

        //then
        mockMvc.perform(get("/api/flights/{id}", nonExistingTestFlightId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.errorMessage", is(LocaleService.getMessage("errors.entity_not_found"))))
                .andExpect(jsonPath("$.causeMessage", is(LocaleService.getMessage("flight"))));

        //verify
        verify(flightService, times(1)).getFlightWithAvailableSeatsById(nonExistingTestFlightId);
    }

    @Test
    void saveFlightTest_whenSaveFlightCalledWithValidRequest_shouldReturnFlightId() throws Exception {
        //given
        FlightRequestDto testFlightRequestDto = testDataGenerator.generateFlightRequestDtoForTest();
        Flight testFlight = testDataGenerator.generateFlightForTest();

        //when
        when(flightService.saveFlight(any(FlightRequestDto.class))).thenReturn(testFlight);

        //then
        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataGenerator.convertToJsonString(testFlightRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data", is(testFlight.getId().intValue())))
                .andExpect(jsonPath("$.message", is(LocaleService.getMessage("flight.created"))));

        //verify
        verify(flightService, times(1)).saveFlight(any(FlightRequestDto.class));
    }

    @Test
    void updateFlightTest_whenUpdateFlightCalledWithValidRequest_shouldReturnFlightId() throws Exception {
        //given
        FlightRequestDto testFlightRequestDto = testDataGenerator.generateFlightRequestDtoForTest();
        Flight testFlight = testDataGenerator.generateFlightForTest();

        //when
        when(flightService.updateFlight(eq(testFlight.getId()), any(FlightRequestDto.class))).thenReturn(testFlight);

        //then
        mockMvc.perform(put("/api/flights/{id}", testFlight.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataGenerator.convertToJsonString(testFlightRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(testFlight.getId().intValue())))
                .andExpect(jsonPath("$.message", is(LocaleService.getMessage("flight.updated"))));

        //verify
        verify(flightService, times(1)).updateFlight(eq(testFlight.getId()), any(FlightRequestDto.class));
    }

    @Test
    void deleteFlightTest_whenDeleteFlightCalledWithExistingId_shouldReturnVoidWithNoContent() throws Exception {
        //given
        Long existingId = TestDataGenerator.TEST_FLIGHT_ID;

        //when
        doNothing().when(flightService).deleteFlight(existingId);

        //then
        mockMvc.perform(delete("/api/flights/{id}", existingId))
                .andExpect(status().isNoContent());

        //verify
        verify(flightService, times(1)).deleteFlight(existingId);
    }
}
