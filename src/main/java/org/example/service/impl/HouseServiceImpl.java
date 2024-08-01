package org.example.service.impl;

import org.example.dao.impl.HouseDaoImpl;
import org.example.entity.House;
import org.example.entity.Owner;
import org.example.service.HouseService;

import java.time.LocalDate;
import java.util.List;

public class    HouseServiceImpl implements HouseService {
    private final HouseDaoImpl houseDao= new HouseDaoImpl();
    @Override
    public String save(House house) {
        return houseDao.save(house);
    }

    @Override
    public String saveWithOwner(House house, Owner owner) {
        return houseDao.saveWithOwner(house,owner);
    }

    @Override
    public House getHouseById(Long id) {
        return houseDao.getHouseById(id);
    }

    @Override
    public String updateHouse(Long id, House newHouse) {
        return houseDao.updateHouse(id,newHouse);
    }

    @Override
    public List<House> getAllHouse() {
        return houseDao.getAllHouse();
    }

    @Override
    public void deleteHouse(Long id) {
        houseDao.deleteHouse(id);
    }

    @Override
    public List<House> getAllHouseByRegion(String region) {
        return houseDao.getAllHouseByRegion(region);
    }

    @Override
    public List<House> getAllHouseByAgencyId(Long agencyId) {
        return houseDao.getAllHouseByAgencyId(agencyId);
    }

    @Override
    public List<House> getAllHouseByOwnerId(Long ownerId) {
        return houseDao.getAllHouseByOwnerId(ownerId);
    }

    @Override
    public List<House> getAllHouseBetweenDate(LocalDate localDate, LocalDate localDate2) {
        return houseDao.getAllHouseBetweenDate(localDate,localDate2);
    }
}
