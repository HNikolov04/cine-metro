package com.cineworld.cinemetro.application.mapper.order;

import com.cineworld.cinemetro.application.dto.order.OrderItemResponseDto;
import com.cineworld.cinemetro.application.dto.order.OrderRequestDto;
import com.cineworld.cinemetro.application.dto.order.OrderResponseDto;
import com.cineworld.cinemetro.domain.model.order.Order;
import com.cineworld.cinemetro.domain.model.product.Product;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OrderMapper {

    public Order toEntity(OrderRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return Order.builder()
                .discountType(dto.discountType())
                .discountValue(dto.discountValue())
                .build();
    }

    public OrderResponseDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderResponseDto(
                order.getId(),
                order.getUser() != null ? order.getUser().getId() : null,
                toItemList(order.getTickets(), "TICKET"),
                toItemList(order.getProducts(), "PRODUCT"),
                order.getStatus(),
                order.getDiscountType(),
                order.getDiscountValue(),
                order.getTotalAmount(),
                order.getCreatedAt()
        );
    }

    private List<OrderItemResponseDto> toItemList(List<?> items, String type) {
        if (items == null) return List.of();

        return items.stream().map(item -> {
            if (item instanceof Product product) {
                return new OrderItemResponseDto(
                        product.getId(),
                        product.getName(),
                        type,
                        product.getPrice()
                );
            } else if (item instanceof Ticket ticket) {
                return new OrderItemResponseDto(
                        ticket.getId(),
                        ticket.getSeat() != null
                                ? "Seat " + ticket.getSeat().getSeatNumber()
                                : "Ticket #" + ticket.getId(),
                        type,
                        ticket.getPrice()
                );
            }
            return null;
        }).filter(i -> i != null).collect(Collectors.toList());
    }
}