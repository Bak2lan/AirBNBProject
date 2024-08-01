package org.example.service;

import org.example.entity.Address;
import org.example.entity.Agency;

import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Agency> getAllAddressWithAgency();
    String updateAddress(Long id,Address address);
    List<Address> getAllAddress();
    Address getAddressById(Long id);
    void deleteAddress(Long id);
    Long getCountAgency(String city);
    Map<String, List<Agency>> groupByRegion();
}
