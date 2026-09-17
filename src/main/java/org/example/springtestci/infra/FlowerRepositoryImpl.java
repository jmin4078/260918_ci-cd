package org.example.springtestci.infra;

import org.example.springtestci.domain.Flower;
import org.example.springtestci.domain.FlowerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlowerRepositoryImpl implements FlowerRepository {
    @Override
    public long count() {
        return 0;
    }

    @Override
    public Flower save(Flower flower) {
        return null;
    }

    @Override
    public List<Flower> findAll() {
        return List.of();
    }
}
