package org.example.springtestci.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerJpaRepository extends JpaRepository<FlowerJpaEntity, Long> {
}