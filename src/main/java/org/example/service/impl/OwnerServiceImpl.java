package org.example.service.impl;

import org.example.dao.impl.OwnerDaoImpl;
import org.example.entity.House;
import org.example.entity.Owner;
import org.example.service.OwnerService;

import java.util.List;

public class OwnerServiceImpl implements OwnerService {
    private final OwnerDaoImpl ownerDao= new OwnerDaoImpl();
    @Override
    public String saveOwner(Owner owner) {
        return ownerDao.saveOwner(owner);
    }

    @Override
    public String saveOwnerWithHouse(Owner owner, House house) {
        return ownerDao.saveOwnerWithHouse(owner,house);
    }

    @Override
    public String assignOwnerToAgency(Long id, Long agencyId) {
        return ownerDao.assignOwnerToAgency(id,agencyId);
    }

    @Override
    public Owner getById(Long id) {
        return ownerDao.getById(id);
    }

    @Override
    public Owner updateOwner(Long id, Owner owner) {
        return ownerDao.updateOwner(id,owner);
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerDao.getAllOwners();
    }

    @Override
    public String deleteOwner(Long id) {
        return ownerDao.deleteOwner(id);
    }

    @Override
    public Owner getOwnerByAgencyId(Long agencyId) {
        return ownerDao.getOwnerByAgencyId(agencyId);
    }

    @Override
    public String getOwnerNameAndAge() {
        return ownerDao.getOwnerNameAndAge();
    }
}
