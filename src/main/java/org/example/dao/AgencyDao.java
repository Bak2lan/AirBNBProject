package org.example.dao;

import org.example.entity.Address;
import org.example.entity.Agency;

import java.util.List;

public interface AgencyDao {

    String saveAgency(Agency agency);
    Agency getAgencyById(Long id);
    String updateAgency(Long oldAgencyId,Agency newAgency);
    String deleteAgency(Long agencyId);
    List<Agency>getAllAgency();

}
