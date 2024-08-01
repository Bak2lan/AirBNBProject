package org.example.service;

import org.example.entity.House;
import org.example.entity.Owner;

import java.time.LocalDate;
import java.util.List;

public interface HouseService {
    String save(House house);
    String saveWithOwner(House house, Owner owner);
    House getHouseById(Long id);
    String updateHouse(Long id ,House newHouse);
    List<House> getAllHouse();
    void deleteHouse(Long id);
    List<House>getAllHouseByRegion(String region);
    List<House>getAllHouseByAgencyId(Long agencyId);
    List<House>getAllHouseByOwnerId(Long ownerId);
    List<House>getAllHouseBetweenDate(LocalDate localDate, LocalDate localDate2);

}
