package org.example.service.impl;

import org.example.dao.impl.CustomerDaoImpl;
import org.example.entity.Customer;
import org.example.entity.Owner;
import org.example.entity.Rent_Info;
import org.example.service.CustomerService;

import java.time.LocalDate;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDaoImpl customerDao= new CustomerDaoImpl();
    @Override
    public String saveCustomer(Customer customer){
        return customerDao.saveCustomer(customer);
    }

    @Override
    public String saveCustomerWithHouse(Customer customer, Rent_Info rentInfo, Owner owner) {
        return customerDao.saveCustomerWithHouse(customer,rentInfo,owner );
    }

    @Override
    public String assignHouseToCustomer(Long houseId, Long customerId, Long ownerId, LocalDate checkin, LocalDate checkout,Long agencyId) {
        return customerDao.assignHouseToCustomer(houseId,customerId,ownerId,checkin,checkout,agencyId );
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerDao.getCustomerById(id);
    }

    @Override
    public String updateCustomer(Long oldCustomerId, Customer customer) {
        return customerDao.updateCustomer(oldCustomerId,customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    @Override
    public String deleteCustomer(Long id) {
        return customerDao.deleteCustomer(id);
    }


}
