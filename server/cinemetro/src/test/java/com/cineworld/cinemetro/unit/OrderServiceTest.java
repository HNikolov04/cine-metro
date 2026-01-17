package com.cineworld.cinemetro.unit;

import com.cineworld.cinemetro.application.dto.order.OrderRequestDto;
import com.cineworld.cinemetro.application.dto.order.OrderResponseDto;
import com.cineworld.cinemetro.application.mapper.ticket.TicketMapper;
import com.cineworld.cinemetro.application.service.order.OrderService;
import com.cineworld.cinemetro.domain.enums.order.DiscountType;
import com.cineworld.cinemetro.domain.exceptions.order.OrderValidationException;
import com.cineworld.cinemetro.domain.exceptions.product.ProductNotFoundException;
import com.cineworld.cinemetro.domain.model.cinema.Hall;
import com.cineworld.cinemetro.domain.model.cinema.Seat;
import com.cineworld.cinemetro.domain.model.order.Order;
import com.cineworld.cinemetro.domain.model.product.Product;
import com.cineworld.cinemetro.domain.model.screening.Screening;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import com.cineworld.cinemetro.persistence.repository.cinema.SeatRepository;
import com.cineworld.cinemetro.persistence.repository.order.OrderRepository;
import com.cineworld.cinemetro.persistence.repository.product.ProductRepository;
import com.cineworld.cinemetro.persistence.repository.screening.ScreeningRepository;
import com.cineworld.cinemetro.persistence.repository.ticket.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private TicketMapper ticketMapper;

    @InjectMocks
    private OrderService orderService;

    private Screening screening;
    private Seat seat1;
    private Seat seat2;

    @BeforeEach
    void setup() {
        Hall hall = Hall.builder().id(10L).build();
        screening = Screening.builder()
                .id(1L)
                .hall(hall)
                .basePrice(BigDecimal.valueOf(10))
                .startTime(LocalDateTime.now())
                .build();

        seat1 = Seat.builder().id(1L).hall(hall).rowNumber(1).seatNumber(1).build();
        seat2 = Seat.builder().id(2L).hall(hall).rowNumber(1).seatNumber(2).build();
    }

    @Test
    void createOrder_success() {
        OrderRequestDto request = new OrderRequestDto(1L, List.of(1L, 2L), List.of(7L), DiscountType.NONE, BigDecimal.ZERO);

        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat1));
        when(seatRepository.findById(2L)).thenReturn(Optional.of(seat2));
        when(ticketRepository.existsByScreening_IdAndSeat_Id(1L, 1L)).thenReturn(false);
        when(ticketRepository.existsByScreening_IdAndSeat_Id(1L, 2L)).thenReturn(false);

        Ticket ticket1 = Ticket.builder().screening(screening).seat(seat1).price(BigDecimal.valueOf(10)).build();
        Ticket ticket2 = Ticket.builder().screening(screening).seat(seat2).price(BigDecimal.valueOf(10)).build();
        when(ticketMapper.toEntity(screening, seat1, screening.getBasePrice())).thenReturn(ticket1);
        when(ticketMapper.toEntity(screening, seat2, screening.getBasePrice())).thenReturn(ticket2);

        Product product = Product.builder().id(7L).price(BigDecimal.valueOf(5)).build();
        when(productRepository.findAllById(any())).thenReturn(List.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArguments()[0]);

        OrderResponseDto response = orderService.createOrder(request);

        assertEquals(BigDecimal.valueOf(25), response.totalAmount());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void createOrder_duplicateSeats_throws() {
        OrderRequestDto request = new OrderRequestDto(1L, List.of(1L, 1L), List.of(), DiscountType.NONE, BigDecimal.ZERO);
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));

        OrderValidationException ex = assertThrows(OrderValidationException.class,
                () -> orderService.createOrder(request));

        assertEquals("Duplicate seats are not allowed.", ex.getMessage());
    }

    @Test
    void createOrder_missingProduct_throws() {
        OrderRequestDto request = new OrderRequestDto(1L, List.of(1L), List.of(99L), DiscountType.NONE, BigDecimal.ZERO);

        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat1));
        when(ticketRepository.existsByScreening_IdAndSeat_Id(1L, 1L)).thenReturn(false);
        when(ticketMapper.toEntity(screening, seat1, screening.getBasePrice()))
                .thenReturn(Ticket.builder().screening(screening).seat(seat1).price(BigDecimal.valueOf(10)).build());
        when(productRepository.findAllById(any())).thenReturn(List.of());

        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class,
                () -> orderService.createOrder(request));

        assertEquals("Product not found with ID: 99", ex.getMessage());
    }
}
