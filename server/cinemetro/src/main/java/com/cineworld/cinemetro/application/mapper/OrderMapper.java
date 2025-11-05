package com.cineworld.cinemetro.application.mapper;

import com.cineworld.cinemetro.application.dto.order.*;
import com.cineworld.cinemetro.domain.model.order.Order;
import com.cineworld.cinemetro.domain.model.product.Product;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OrderMapper {

    // 🟢 Converts OrderRequestDto -> Order Entity (used in creation)
    public Order toEntity(OrderRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return Order.builder()
                .discountType(dto.getDiscountType())
                .discountValue(dto.getDiscountValue())
                .build();
    }

    // 🟢 Converts Order -> OrderResponseDto (for returning to frontend)
    public OrderResponseDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .tickets(toItemList(order.getTickets(), "TICKET"))
                .products(toItemList(order.getProducts(), "PRODUCT"))
                .status(order.getStatus())
                .discountType(order.getDiscountType())
                .discountValue(order.getDiscountValue())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .build();
    }

    // 🟢 Helper method: converts both Tickets and Products into OrderItemResponseDto
    private List<OrderItemResponseDto> toItemList(List<?> items, String type) {
        if (items == null) return List.of();

        return items.stream().map(item -> {
            if (item instanceof Product product) {
                return OrderItemResponseDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .type(type)
                        .build();
            } else if (item instanceof Ticket ticket) {
                return OrderItemResponseDto.builder()
                        .id(ticket.getId())
                        .name(ticket.getSeat() != null
                                ? "Seat " + ticket.getSeat().getSeatNumber()
                                : "Ticket #" + ticket.getId())
                        .price(ticket.getPrice())
                        .type(type)
                        .build();
            }
            return null;
        }).filter(i -> i != null).collect(Collectors.toList());
    }
}
