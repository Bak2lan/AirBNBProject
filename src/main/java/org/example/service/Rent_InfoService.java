package org.example.service;

import org.example.entity.Rent_Info;

import java.time.LocalDate;
import java.util.List;

public interface Rent_InfoService {
    List<Rent_Info> getRentInfoByCheckOut(LocalDate localDate1 , LocalDate localDate2);
    List<Rent_Info> getAllRentInfo();
    List<Rent_Info>getAllRentInfoByAgencyId(Long id);
}
