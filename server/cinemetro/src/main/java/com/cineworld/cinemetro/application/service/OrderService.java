package com.cineworld.cinemetro.application.service;

import com.cineworld.cinemetro.application.dto.order.OrderRequestDto;
import com.cineworld.cinemetro.application.dto.order.OrderResponseDto;
import com.cineworld.cinemetro.application.mapper.OrderMapper;
import com.cineworld.cinemetro.domain.exceptions.order.OrderNotFoundException;
import com.cineworld.cinemetro.domain.model.order.Order;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import com.cineworld.cinemetro.domain.model.product.Product;
import com.cineworld.cinemetro.persistence.repository.OrderRepository;
import com.cineworld.cinemetro.persistence.repository.ProductRepository;
import com.cineworld.cinemetro.persistence.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final TicketRepository ticketRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        // 1️⃣ Convert DTO to entity
        Order order = OrderMapper.toEntity(dto);

        // 2️⃣ Fetch related tickets
        List<Ticket> tickets = ticketRepository.findAllById(dto.getTicketIds());
        List<Product> products = productRepository.findAllById(dto.getProductIds());

        order.setTickets(tickets);
        order.setProducts(products);

        // 3️⃣ Calculate total
        BigDecimal total = calculateTotal(tickets, products, dto.getDiscountValue());
        order.setTotalAmount(total);

        // 4️⃣ Save to DB
        Order saved = orderRepository.save(order);

        // 5️⃣ Return response DTO
        return OrderMapper.toDto(saved);
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
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
}