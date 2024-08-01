package org.example.service.impl;

import org.example.dao.impl.Rent_infoDaoImpl;
import org.example.entity.Rent_Info;
import org.example.service.Rent_InfoService;

import java.time.LocalDate;
import java.util.List;

public class Rent_InfoServiceImpl implements Rent_InfoService {
    private final Rent_infoDaoImpl rentInfoDao= new Rent_infoDaoImpl();
    @Override
    public List<Rent_Info> getRentInfoByCheckOut(LocalDate localDate1, LocalDate localDate2) {
        return rentInfoDao.getRentInfoByCheckOut(localDate1,localDate2) ;
    }

    @Override
    public List<Rent_Info> getAllRentInfo() {
        return rentInfoDao.getAllRentInfo();
    }

    @Override
    public List<Rent_Info> getAllRentInfoByAgencyId(Long id) {
        return rentInfoDao.getAllRentInfoByAgencyId(id);
    }
}
