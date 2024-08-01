package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.config.HibernateConfig;
import org.example.dao.AddressDao;
import org.example.entity.Address;
import org.example.entity.Agency;
import org.example.entity.House;
import org.example.entity.Owner;
import org.example.exception.MyException;
import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressDaoImpl implements AddressDao {
    private final EntityManagerFactory entityManagerFactory= HibernateConfig.getEntityManagerFactory();
    @Override
    public List<Agency> getAllAddressWithAgency() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Agency> resultList = entityManager.createQuery("select a from Agency a join fetch a.address", Agency.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return resultList;
    }

    @Override
    public String updateAddress(Long id, Address address) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Address findedAddress = entityManager.find(Address.class, id);
            if (findedAddress ==null) {
                entityManager.getTransaction().rollback();
                throw new MyException("Not found address");
            }
                findedAddress.setCity(address.getCity());
                findedAddress.setRegion(address.getRegion());
                findedAddress.setStreet(address.getStreet());
                entityManager.getTransaction().commit();
                entityManager.close();
                return "Address successfully updated";

        }catch (HibernateException |MyException ee){
            System.out.println(ee.getMessage());
        }
        return "";
    }

    @Override
    public List<Address> getAllAddress() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Address> selectAFromAddressA = entityManager.createQuery("select a from Address a", Address.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return selectAFromAddressA;
    }

    @Override
    public Address getAddressById(Long id) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Address address = entityManager.find(Address.class, id);
            if (address==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Address");
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return address;
        }catch (HibernateException |MyException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public void deleteAddress(Long id) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Address address = entityManager.find(Address.class, id);
            Agency agency = entityManager.createQuery("select a from Agency a inner join a.address aa where aa.id=:id", Agency.class)
                    .setParameter("id", id)
                    .getSingleResult();
            House house = entityManager.createQuery("select h from House h inner join h.address ha where ha.id=:id", House.class)
                    .setParameter("id", id)
                    .getSingleResult();
            List<Owner> owners = entityManager.createQuery("select o from Owner o inner join o.houses oh inner join oh.address oha where oha.id=:id", Owner.class)
                    .setParameter("id", id)
                    .getResultList();
            for (int i = 0; i < owners.size(); i++) {
                owners.get(i).getHouses().get(i).setOwner(null);
            }
            house.setAddress(null);
            agency.setAddress(null);
            if (address==null){
                transaction.rollback();
                throw new MyException("Not found");
            }
            entityManager.remove(address);
            transaction.commit();
            entityManager.close();
            System.out.println("Successfully deleted");
        }catch (HibernateException |MyException exception ){
            System.out.println(exception.getMessage());
        }

    }

    @Override
    public Long getCountAgency(String city) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Long count = entityManager.createQuery("select count(agency.id) from Agency agency inner join agency.address a where a.city=:city", Long.class)
                    .setParameter("city", city)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            System.out.print("Count of agency from " + city + " ");
            return  count;
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return 0L;
    }




    //TODO implement this method;



    @Override
    public Map<String, List<Agency>> groupByRegion() {
        Map<String ,List<Agency>>regionAgency= new HashMap<>();
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<String> regions = entityManager.createQuery("select distinct a.region from Address a inner join a.agency", String.class)
                    .getResultList();
            for (String s:regions){
                List<Agency> agencies = entityManager.createQuery("select a from Agency a inner join a.address aa where aa.region=:s", Agency.class)
                        .setParameter("s", s)
                        .getResultList();
                regionAgency.put(s,agencies);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return regionAgency;
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return new HashMap<>();

    }
}
