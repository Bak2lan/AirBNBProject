package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.config.HibernateConfig;
import org.example.dao.OwnerDao;
import org.example.entity.Agency;
import org.example.entity.House;
import org.example.entity.Owner;
import org.example.entity.Rent_Info;
import org.example.exception.MyException;
import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unchecked")


public class OwnerDaoImpl implements OwnerDao {

    private final EntityManagerFactory entityManagerFactory= HibernateConfig.getEntityManagerFactory();
    @Override
    public String saveOwner(Owner owner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LocalDate currentDate=LocalDate.now();
        int age=Period.between(owner.getDateOfBirth(),currentDate).getYears();
        if (age>=18) {
            entityManager.persist(owner);
            entityManager.getTransaction().commit();
            entityManager.close();
            return "Successfully saved";
        }else {
            entityManager.getTransaction().rollback();
            return "Owner age must be equals or more 18";
        }
    }

    @Override
    public String saveOwnerWithHouse(Owner owner, House house) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            List<House>houses= new ArrayList<>();
            LocalDate currentDate=LocalDate.now();
           int age= Period.between(owner.getDateOfBirth(),currentDate).getYears();
            if (age>=18) {
                houses.add(house);
                entityManager.persist(owner);
                owner.setHouses(houses);
                house.setOwner(owner);
                entityManager.persist(house);
                entityManager.getTransaction().commit();
                entityManager.close();
                return "Owner and House successfully saved";

            }else {
                entityManager.getTransaction().rollback();
                return "Owner age must be equals or more 18";
            }

        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public String assignOwnerToAgency(Long id, Long agencyId) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
//            List<Owner> owners = entityManager.createQuery("select o from Owner o where o.id=:id", Owner.class)
//                    .setParameter("id", id)
//                    .getResultList();
//            List<Agency> agencies = entityManager.createQuery("select a from Agency a where a.id=:agencyId", Agency.class)
//                    .setParameter("agencyId", agencyId)
//                    .getResultList();
            Owner owner = entityManager.find(Owner.class, id);
            Agency agency = entityManager.find(Agency.class, agencyId);
            if (owner==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Owner");
            }
            if (agency==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Agency");
            }
            owner.getAgencies().add(agency);
            agency.getOwners().add(owner);
            entityManager.getTransaction().commit();
            entityManager.close();
            return "Owner successfully assigned to Agency";
        }catch (HibernateException |MyException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public Owner getById(Long id) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Owner> owners = entityManager.createQuery("select o from Owner o where o.id=:id", Owner.class)
                    .setParameter("id", id)
                    .getResultList();
            if (owners.isEmpty()){
                entityManager.getTransaction().rollback();
                throw new MyException("Owner not found");
            }else {
                entityManager.getTransaction().commit();
                entityManager.close();
                return owners.get(0);
            }
        }catch (HibernateException | MyException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public Owner updateOwner(Long id, Owner owner) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Owner> owners = entityManager.createQuery("select o from Owner o where o.id=:id", Owner.class)
                    .setParameter("id", id)
                    .getResultList();
            if (owners.isEmpty()){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found");
            }else {
                owners.get(0).setFirstName(owner.getFirstName());
                owners.get(0).setLastName(owner.getLastName());
                owners.get(0).setEmail(owner.getEmail());
                owners.get(0).setGender(owner.getGender());
                owners.get(0).setDateOfBirth(owner.getDateOfBirth());
                entityManager.getTransaction().commit();
                entityManager.close();
                System.out.println("Owner successfully updated");
                return owners.get(0);
            }
        }catch (HibernateException | MyException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Owner> getAllOwners() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Owner> owners = entityManager.createQuery("select o from Owner o ", Owner.class)
                .getResultList();
        if (owners.isEmpty()){
            System.out.println("There are not owners");
        }
        return owners;
    }

    @Override
    public String deleteOwner(Long id) {
        EntityManager entityManager=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Owner> owners = entityManager.createQuery("select o from Owner o where o.id=:id", Owner.class)
                    .setParameter("id", id)
                    .getResultList();
            List<House> h = entityManager.createQuery("select h from House h inner join Owner o on h.owner.id=o.id where h.owner.id=:id", House.class)
                    .setParameter("id", id)
                    .getResultList();
            List<Rent_Info> rentInfos = entityManager.createQuery("select ri from Rent_Info ri inner join House h on ri.house.id=h.id inner join Owner o on ri.owner.id=o.id where ri.owner.id=:id", Rent_Info.class)
                    .setParameter("id", id)
                    .getResultList();
             List<Agency>agencies= entityManager.createNativeQuery("select a.* from agencies a inner join agencies_owners ao on a.id=ao.agencies_id where ao.owners_id=:id", Agency.class)
                    .setParameter("id", id)
                    .getResultList();
            if (owners.isEmpty()){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found");
            }
            Owner owner = owners.get(0);
            for (Agency agency:agencies){
                agency.setOwners(null);
            }

            for (Rent_Info rentInfo:rentInfos){
                if (rentInfo!=null&&rentInfo.getCheckOut().isAfter(LocalDate.now())){
                    return "House has rented";
                }
            }
            for (Rent_Info rentInfo:rentInfos){
                rentInfo.setHouse(null);
                rentInfo.setOwner(null);
                entityManager.merge(rentInfo);
            }
            for (House house:h){
                entityManager.remove(house);
            }
            entityManager.remove(owner);
            entityManager.getTransaction().commit();
            entityManager.close();
            return "Owner successfully deleted with house";

        }catch (HibernateException | MyException e){
            if (entityManager!=null&&entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }finally {
            if (entityManager!=null){
                entityManager.close();
            }
        }
        return "";
    }

    @Override
    public Owner getOwnerByAgencyId(Long agencyId) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Owner> owners = entityManager.createQuery("select o from Owner o inner join o.agencies oa  where oa.id=:agencyId", Owner.class)
                    .setParameter("agencyId", agencyId)
                    .getResultList();
            if (owners.isEmpty()){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found Agency id");
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return owners.get(0);
        }catch (HibernateException |MyException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String getOwnerNameAndAge() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Owner> owners = entityManager.createQuery("select o from Owner o ", Owner.class)
                .getResultList();
        for (Owner owner:owners){
            int age=Period.between(owner.getDateOfBirth(),LocalDate.now()).getYears();
            System.out.println("Name: "+owner.getFirstName()+", Age: "+age);
        }
        return "";
    }
}
