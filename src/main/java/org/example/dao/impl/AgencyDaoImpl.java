package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import lombok.ToString;
import org.example.config.HibernateConfig;
import org.example.dao.AgencyDao;
import org.example.entity.Address;
import org.example.entity.Agency;
import org.example.exception.MyException;
import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.List;

public class AgencyDaoImpl implements AgencyDao {
    private final EntityManagerFactory entityManagerFactory= HibernateConfig.getEntityManagerFactory();
    @Override
    public String saveAgency(Agency agency) {
        Address address= new Address();
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(agency);
            entityManager.getTransaction().commit();
            entityManager.close();
            return "Agency Successfully saved";
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public Agency getAgencyById(Long id) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Agency agency = entityManager.find(Agency.class, id);
            if (agency==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Agency id");
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return agency;
        }catch (HibernateException|MyException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public String updateAgency(Long oldAgencyId, Agency newAgency) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Agency findedAgency =entityManager.find(Agency.class,oldAgencyId);
            if (findedAgency==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Agency Id");
            }
            findedAgency.setName(newAgency.getName());
            findedAgency.setPhoneNumber(newAgency.getPhoneNumber());
            findedAgency.setAddress(newAgency.getAddress());
            entityManager.persist(findedAgency);
            entityManager.getTransaction().commit();
            entityManager.close();
            return "Successfully updated";
        }catch (HibernateException |MyException exception){
            System.out.println(exception.getMessage());
        }
        return "";
    }

    @Override
    public String deleteAgency(Long agencyId) {
        try{
         EntityManager entityManager= entityManagerFactory.createEntityManager();
         entityManager.getTransaction().begin();
            Agency agency = entityManager.createQuery("select a from Agency a where a.id=:agencyId", Agency.class)
                    .setParameter("agencyId", agencyId)
                    .getSingleResult();
            entityManager.remove(agency);
            entityManager.getTransaction().commit();
            entityManager.close();
        }catch (HibernateException | NoResultException exception){
            System.out.println(exception.getMessage());
        }
        return "";
    }

    @Override
    public List<Agency> getAllAgency() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Agency> agencies = entityManager.createQuery("select a from Agency a", Agency.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return agencies;
    }
}
