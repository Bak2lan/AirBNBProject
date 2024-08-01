package org.example.service.impl;

import org.example.dao.impl.AddressDaoImpl;
import org.example.entity.Address;
import org.example.entity.Agency;
import org.example.service.AddressService;

import java.util.List;
import java.util.Map;

public class AddressServiceImpl implements AddressService {
    private final AddressDaoImpl addressDao= new AddressDaoImpl();
    @Override
    public List<Agency> getAllAddressWithAgency() {
        return addressDao.getAllAddressWithAgency();
    }

    @Override
    public String updateAddress(Long id, Address address) {
        return addressDao.updateAddress(id,address);
    }

    @Override
    public List<Address> getAllAddress() {
        return addressDao.getAllAddress();
    }

    @Override
    public Address getAddressById(Long id) {
        return addressDao.getAddressById(id);
    }

    @Override
    public void deleteAddress(Long id) {
        addressDao.deleteAddress(id);
    }

    @Override
    public Long getCountAgency(String city) {
        return addressDao.getCountAgency(city);
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        return addressDao.groupByRegion();
    }
}
