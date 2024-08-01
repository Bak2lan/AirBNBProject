package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.config.HibernateConfig;
import org.example.dao.CustomerDao;
import org.example.entity.*;
import org.example.exception.MyException;
import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private final EntityManagerFactory entityManagerFactory= HibernateConfig.getEntityManagerFactory();
    @Override
    public String saveCustomer(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "Customer successfully saved";
    }

    @Override
    public String saveCustomerWithHouse(Customer customer, Rent_Info rentInfo, Owner owner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.persist(owner);
        rentInfo.setOwner(owner);
        House house = rentInfo.getHouse();
        house.setOwner(owner);
        rentInfo.setCustomer(customer);
        entityManager.persist(rentInfo);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "Successfully saved ";

    }

    @Override
    public String assignHouseToCustomer(Long houseId, Long customerId, Long ownerId, LocalDate checkin, LocalDate checkout, Long agencyId) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Rent_Info>rentInfos= new ArrayList<>();
            Rent_Info rentInfo1= new Rent_Info();
            List<Agency>agencies= new ArrayList<>();
            List<Owner>owners= new ArrayList<>();

            House house = entityManager.find(House.class, houseId);
            if (house==null){
                entityManager.getTransaction().rollback();
                throw new MyException("House not found");
            }
            Agency agency = entityManager.find(Agency.class, agencyId);
            if (agency==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found agency id");
            }
            Customer customer = entityManager.find(Customer.class, customerId);
            if (customer==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Customer");

            }
            if (house.getOwner().getId()!=ownerId){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Owner id");
            }
            agency.setRentInfos(rentInfos);
            owners.add(house.getOwner());
            agency.setOwners(owners);
            agencies.add(agency);
            rentInfo1.setHouse(house);
            rentInfos.add(rentInfo1);
            rentInfo1.setCheckIn(checkin);
            rentInfo1.setCheckOut(checkout);
            rentInfo1.setOwner(house.getOwner());
            house.getOwner().setAgencies(agencies);
            customer.setRentInfos(rentInfos);
            rentInfo1.setCustomer(customer);
            entityManager.persist(rentInfo1);
            entityManager.getTransaction().commit();
            entityManager.close();
            return "House successfully assigned to Customer";


        }catch (HibernateException |MyException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public Customer getCustomerById(Long id) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Customer customer = entityManager.find(Customer.class, id);
            if (customer==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Customer");
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return customer;
        }catch (HibernateException |MyException exception ){
            System.out.println(exception.getMessage());
        }

        return null;
    }

    @Override
    public String updateCustomer(Long oldCustomerId, Customer customer) {
         try{
             EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
             Customer customer1 = entityManager.find(Customer.class, oldCustomerId);
             if (customer1==null){
                 entityManager.getTransaction().rollback();
                 throw new MyException("Not found Customer for updating");
             }
             customer1.setFirstName(customer.getFirstName());
             customer1.setLastName(customer.getLastName());
             customer1.setEmail(customer.getEmail());
             customer1.setGender(customer.getGender());
             customer1.setDateOfBirth(customer.getDateOfBirth());
             customer1.setNationality(customer.getNationality());
             entityManager.getTransaction().commit();
             entityManager.close();
             return "Successfully updated";
         }catch (HibernateException |MyException exception){
             System.out.println(exception.getMessage());
         }
        return "";
    }

    @Override
    public List<Customer> getAllCustomers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Customer> selectCFromCustomerC = entityManager.createQuery("select c from Customer c ", Customer.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return selectCFromCustomerC;
    }

    @Override
    public String deleteCustomer(Long id) {
        EntityManager entityManager=null;
        boolean isFalse=false;
        try{
             entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Customer customer = entityManager.find(Customer.class, id);
            if (customer==null){
                entityManager.getTransaction().rollback();
                throw new MyException("not found");
            }
            List<Rent_Info> rentInfos = customer.getRentInfos();
            if (rentInfos==null||rentInfos.isEmpty()){
                isFalse=true;
                entityManager.remove(customer);
                entityManager.getTransaction().commit();
                entityManager.close();
                return "Successfully deleted";

            }for (Rent_Info rentInfo:rentInfos){
                if (rentInfo.getCheckOut().isBefore(LocalDate.now())){
                    entityManager.remove(customer);
                    entityManager.getTransaction().commit();
                    entityManager.close();
                    return "Successfully deleted";
                } else{
                    return "Customer has rent";
                }

            }
            entityManager.getTransaction().commit();


        }catch (HibernateException |MyException e){
            if (entityManager!=null&&entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
        finally {
            if (entityManager!=null){
                entityManager.close();
            }
        }
        return "";
    }
}
