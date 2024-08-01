package org.example.service;

import org.example.entity.House;
import org.example.entity.Owner;

import java.util.List;

public interface OwnerService {
    String saveOwner(Owner owner);
    String saveOwnerWithHouse(Owner owner, House house);
    String assignOwnerToAgency(Long id,Long agencyId);
    Owner getById(Long id);
    Owner updateOwner(Long id,Owner owner);
    List<Owner> getAllOwners();
    String deleteOwner(Long id);
    Owner getOwnerByAgencyId(Long agencyId);
    String getOwnerNameAndAge();

}
