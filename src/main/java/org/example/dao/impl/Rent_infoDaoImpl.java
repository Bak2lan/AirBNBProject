package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.config.HibernateConfig;
import org.example.dao.Rent_InfoDao;
import org.example.entity.Rent_Info;
import org.example.exception.MyException;
import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.util.List;

public class Rent_infoDaoImpl implements Rent_InfoDao {

private final EntityManagerFactory entityManagerFactory= HibernateConfig.getEntityManagerFactory();
    @Override
    public List<Rent_Info> getRentInfoByCheckOut(LocalDate localDate1, LocalDate localDate2) {
        EntityManager entityManager=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Rent_Info> rentInfos = entityManager.createQuery("select r from Rent_Info r where r.checkOut between :localDate1 and :localDate2", Rent_Info.class)
                    .setParameter("localDate1", localDate1)
                    .setParameter("localDate2", localDate2)
                    .getResultList();
            if (rentInfos.isEmpty()){
                entityManager.getTransaction().rollback();

                throw new MyException("Not found check out rent info between these date");
            }
            entityManager.getTransaction().commit();
            return rentInfos;
        }catch (HibernateException | MyException exception){
            if (entityManager!=null&&entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            System.out.println(exception.getMessage());
        }finally {
            if (entityManager!=null){
                entityManager.close();
            }
        }
        return null;
    }

    @Override
    public List<Rent_Info> getAllRentInfo() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Rent_Info> rentInfos = entityManager.createQuery("select r from Rent_Info r", Rent_Info.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return rentInfos;
    }

    @Override
    public List<Rent_Info> getAllRentInfoByAgencyId(Long id) {
        EntityManager entityManager=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Rent_Info> rentInfos = entityManager.createQuery("select ri from Rent_Info ri inner join ri.owner rio inner join rio.agencies rioa where rioa.id=:id", Rent_Info.class)
                    .setParameter("id", id)
                    .getResultList();
            if (rentInfos.isEmpty()){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Agency id");

            }
            entityManager.getTransaction().commit();
            return rentInfos;
        }catch (HibernateException|MyException e){
            if (entityManager!=null&&entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }finally{
            if (entityManager!=null){
                entityManager.close();
            }
        }
        return null;
    }
}
