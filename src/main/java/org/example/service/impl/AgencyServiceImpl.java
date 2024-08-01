package org.example.service.impl;

import org.example.dao.impl.AgencyDaoImpl;
import org.example.entity.Agency;
import org.example.service.AgencyService;

import java.util.List;

public class AgencyServiceImpl implements AgencyService {
    private final AgencyDaoImpl agencyDao= new AgencyDaoImpl();
    @Override
    public String saveAgency(Agency agency) {
        return agencyDao.saveAgency(agency);
    }

    @Override
    public Agency getAgencyById(Long id) {
        return agencyDao.getAgencyById(id);
    }

    @Override
    public String updateAgency(Long oldAgencyId, Agency newAgency) {
        return agencyDao.updateAgency(oldAgencyId,newAgency);
    }

    @Override
    public String deleteAgency(Long agencyId) {
        return agencyDao.deleteAgency(agencyId);
    }

    @Override
    public List<Agency> getAllAgency() {
        return agencyDao.getAllAgency();
    }
}
