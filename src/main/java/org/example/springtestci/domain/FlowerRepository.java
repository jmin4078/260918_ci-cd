package org.example.springtestci.domain;

import java.util.List;

public interface FlowerRepository {
    long count();
    Flower save(Flower flower);
    List<Flower> findAll();
}