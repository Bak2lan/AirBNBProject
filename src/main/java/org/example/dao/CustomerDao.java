package org.example.dao;

import org.example.entity.Customer;
import org.example.entity.Owner;
import org.example.entity.Rent_Info;

import java.time.LocalDate;
import java.util.List;

public interface CustomerDao {
    String saveCustomer(Customer customer);
    String saveCustomerWithHouse(Customer customer, Rent_Info rentInfo, Owner owner);
    String assignHouseToCustomer(Long houseId, Long customerId, Long ownerId, LocalDate checkin,LocalDate checkout,Long agencyId);
    Customer getCustomerById(Long id);
    String updateCustomer(Long oldCustomerId,Customer customer);
    List<Customer>getAllCustomers();
    String deleteCustomer(Long id);


}
