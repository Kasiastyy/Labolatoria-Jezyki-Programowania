package dao.impl;

import dao.SalonDao;
import data.models.Salon;

import java.util.List;
import java.util.Optional;

public class SalonDaoSqlite implements SalonDao {

    @Override
    public Optional<Salon> getByOwnerId(int ownerId) {
        return Optional.empty();
    }

    @Override
    public void add(Salon item) {

    }

    @Override
    public Optional<Salon> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Salon> getAll() {
        return List.of();
    }

    @Override
    public void update(Salon item) {

    }

    @Override
    public void delete(int id) {

    }
}