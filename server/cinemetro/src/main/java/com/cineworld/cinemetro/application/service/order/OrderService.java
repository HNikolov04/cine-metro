package com.cineworld.cinemetro.application.service.order;

import com.cineworld.cinemetro.application.dto.order.OrderRequestDto;
import com.cineworld.cinemetro.application.dto.order.OrderResponseDto;
import com.cineworld.cinemetro.application.mapper.order.OrderMapper;
import com.cineworld.cinemetro.application.mapper.ticket.TicketMapper;
import com.cineworld.cinemetro.domain.exceptions.cinema.seat.SeatNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.seat.SeatNotInHallException;
import com.cineworld.cinemetro.domain.exceptions.order.OrderNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.order.OrderValidationException;
import com.cineworld.cinemetro.domain.exceptions.product.ProductNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.screening.ScreeningNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.ticket.TicketAlreadyExistsException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final TicketRepository ticketRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final TicketMapper ticketMapper;

    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Order order = OrderMapper.toEntity(dto);

        Screening screening = screeningRepository.findById(dto.screeningId())
                .orElseThrow(() -> new ScreeningNotFoundException(dto.screeningId()));

        List<Ticket> tickets = toUniqueSeatIds(dto.seatIds()).stream()
                .map(seatId -> buildTicket(screening, seatId))
                .toList();
        List<Product> products = findProducts(dto.productIds());

        order.setTickets(tickets);
        order.setProducts(products);

        BigDecimal total = calculateTotal(tickets, products, dto.discountValue());
        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);
        return OrderMapper.toDto(saved);
    }

    public OrderResponseDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }

    private BigDecimal calculateTotal(List<Ticket> tickets, List<Product> products, BigDecimal discount) {
        BigDecimal ticketSum = tickets.stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal productSum = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = ticketSum.add(productSum);

        if (discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discountAmount = total.multiply(discount).divide(BigDecimal.valueOf(100));
            total = total.subtract(discountAmount);
        }

        return total;
    }

    private Set<Long> toUniqueSeatIds(List<Long> seatIds) {
        Set<Long> unique = new LinkedHashSet<>(seatIds);
        if (unique.size() != seatIds.size()) {
            throw new OrderValidationException("Duplicate seats are not allowed.");
        }
        return unique;
    }

    private List<Product> findProducts(List<Long> productIds) {
        if (productIds.isEmpty()) {
            return List.of();
        }

        Set<Long> unique = new LinkedHashSet<>(productIds);
        List<Product> products = productRepository.findAllById(unique);
        if (products.size() != unique.size()) {
            Set<Long> found = new LinkedHashSet<>();
            for (Product product : products) {
                found.add(product.getId());
            }
            for (Long id : unique) {
                if (!found.contains(id)) {
                    throw new ProductNotFoundException("Product not found with ID: " + id);
                }
            }
        }
        return products;
    }

    private Ticket buildTicket(Screening screening, Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException(seatId));

        if (!seat.getHall().getId().equals(screening.getHall().getId())) {
            throw new SeatNotInHallException(seatId, screening.getHall().getId());
        }

        if (ticketRepository.existsByScreening_IdAndSeat_Id(screening.getId(), seat.getId())) {
            throw new TicketAlreadyExistsException(screening.getId(), seat.getId());
        }

        return ticketMapper.toEntity(screening, seat, screening.getBasePrice());
    }
}
