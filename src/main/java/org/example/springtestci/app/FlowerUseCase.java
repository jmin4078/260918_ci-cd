package org.example.springtestci.app;

import org.example.springtestci.domain.Flower;

import java.util.List;

// UI 등에서 어떻게 domain을 사용할 것인지에 대한 계약
public interface FlowerUseCase {
    long count();
    Flower save(Flower flower);
    List<Flower> findAll();
}