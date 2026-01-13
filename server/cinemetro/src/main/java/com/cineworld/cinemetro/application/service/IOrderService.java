package com.cineworld.cinemetro.application.service;

import com.cineworld.cinemetro.application.dto.order.OrderRequestDto;
import com.cineworld.cinemetro.application.dto.order.OrderResponseDto;

import java.util.List;

public interface IOrderService {

    OrderResponseDto createOrder(OrderRequestDto requestDto);

    OrderResponseDto getOrderById(Long id);

    List<OrderResponseDto> getAllOrders();

    void deleteOrder(Long id);
}
