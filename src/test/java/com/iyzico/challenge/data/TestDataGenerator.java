package com.iyzico.challenge.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyzico.challenge.data.dto.FlightRequestDto;
import com.iyzico.challenge.data.dto.SeatRequestDto;
import com.iyzico.challenge.data.view.FlightListView;
import com.iyzico.challenge.data.view.FlightView;
import com.iyzico.challenge.data.view.SeatListView;
import com.iyzico.challenge.data.view.SeatView;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Seat;
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

    public static String convertToJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
