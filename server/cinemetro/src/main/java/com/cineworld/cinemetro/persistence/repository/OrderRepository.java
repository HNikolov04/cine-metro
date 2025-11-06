package com.cineworld.cinemetro.persistence.repository;

import com.cineworld.cinemetro.domain.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // You can add custom queries here later
}