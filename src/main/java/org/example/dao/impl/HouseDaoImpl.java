package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.config.HibernateConfig;
import org.example.dao.HouseDao;
import org.example.entity.Agency;
import org.example.entity.House;
import org.example.entity.Owner;
import org.example.entity.Rent_Info;
import org.example.exception.MyException;
import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.util.List;

public class HouseDaoImpl implements HouseDao {
private final EntityManagerFactory entityManagerFactory = HibernateConfig.getEntityManagerFactory();
    @Override
    public String save(House house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(house);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "Successfully saved";
    }

    @Override
    public String saveWithOwner(House house, Owner owner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(house);
        house.setOwner(owner);
        entityManager.persist(owner);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "House successfully saved with owner";
    }

    @Override
    public House getHouseById(Long id) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            House house = entityManager.find(House.class, id);
            if (house==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found House");
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return house;
        }catch (HibernateException |MyException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String updateHouse(Long id, House newHouse) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            House house = entityManager.find(House.class, id);
            if (house==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found");
            }
            house.setHouseType(newHouse.getHouseType());
            house.setAddress(newHouse.getAddress());
            house.setPrice(newHouse.getPrice());
            house.setDescription(newHouse.getDescription());
            house.setFurniture(newHouse.isFurniture());
            house.setRoom(newHouse.getRoom());
            house.setRating(newHouse.getRating());
            entityManager.getTransaction().commit();
            entityManager.close();
        }catch (HibernateException | MyException exception){
            System.out.println(exception.getMessage());
        }
        return "";
    }

    @Override
    public List<House> getAllHouse() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<House> houses = entityManager.createQuery("select h from House h ", House.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return houses;
    }

    @Override
    public void deleteHouse(Long id) {
        EntityManager entityManager=null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            House house = entityManager.find(House.class, id);
            List<Rent_Info> rentInfos = entityManager.createQuery("select ren from Rent_Info ren inner join ren.house where ren.house.id=:id", Rent_Info.class)
                    .setParameter("id", id)
                    .getResultList();
            List<Owner> owners = entityManager.createQuery("select o from Owner o inner join o.houses oh where oh.id=:id", Owner.class)
                    .setParameter("id", id)
                    .getResultList();
            List<Agency> agencies = entityManager.createQuery("select a from Agency a inner join a.rentInfos ar inner join ar.house arh where arh.id=:id", Agency.class)
                    .setParameter("id", id)
                    .getResultList();

            if (house==null){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found House");
            }
            for (Rent_Info rentInfo:rentInfos){
                if (rentInfo.getCheckOut().isAfter(LocalDate.now())){
                    entityManager.getTransaction().rollback();
                    System.out.println("House has rented");
                    return;
                }
            }
            for (Rent_Info rentInfo:rentInfos){
                rentInfo.setHouse(null);
                entityManager.remove(rentInfo);
            }
            for (Owner o :owners){
                o.setHouses(null);
            }
            for (Agency agency:agencies){
                agency.setRentInfos(null);
            }
            entityManager.remove(house);
            entityManager.getTransaction().commit();
            System.out.println("Successfully deleted");


        }catch (HibernateException |MyException e){
            if (entityManager!=null&&entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }finally {
            if (entityManager!=null){
                entityManager.close();
            }
        }
    }

    @Override
    public List<House> getAllHouseByRegion(String region) {
        EntityManager entityManager=null;
        try {
           entityManager= entityManagerFactory.createEntityManager();
           entityManager.getTransaction().begin();
            List<House> house = entityManager.createQuery("select h from House h inner join h.address ha where ha.region=:region", House.class)
                    .setParameter("region", region)
                    .getResultList();
            if (house.isEmpty()){
                entityManager.getTransaction().rollback();
                throw new MyException("not found region");
            }
            entityManager.getTransaction().commit();
            return house;
        }catch (HibernateException |MyException e){
            if (entityManager!=null&&entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }finally {
            if (entityManager!=null){
                entityManager.close();
            }
        }
        return null;
    }

    @Override
    public List<House> getAllHouseByAgencyId(Long agencyId) {
            EntityManager entityManager=null;
            try{
                entityManager=entityManagerFactory.createEntityManager();
                entityManager.getTransaction().begin();
                List<House> houses = entityManager.createQuery("select h from House h inner join h.owner ho inner join ho.agencies hoa where hoa.id=:agencyId", House.class)
                        .setParameter("agencyId", agencyId)
                        .getResultList();
                if (houses.isEmpty()){
                    entityManager.getTransaction().rollback();
                    throw new MyException("Not found Agency id ");
                }
                entityManager.getTransaction().commit();
                return houses;
            }catch (HibernateException |MyException exception){
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
    public List<House> getAllHouseByOwnerId(Long ownerId) {
        EntityManager entityManager=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<House> houses = entityManager.createQuery("select h from House h where h.owner.id=:ownerId", House.class)
                    .setParameter("ownerId", ownerId)
                    .getResultList();
            if (houses.isEmpty()){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found");
            }
            entityManager.getTransaction().commit();
            return houses;
        }catch (HibernateException | MyException exception){
            if (entityManager!=null&&entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            System.out.println(exception.getMessage());
        }finally{
            if (entityManager!=null){
                entityManager.close();
            }
        }
        return null;
    }

    @Override
    public List<House> getAllHouseBetweenDate(LocalDate localDate, LocalDate localDate2) {
        EntityManager entityManager=null;
        try{
            entityManager=entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<House> houses = entityManager.createQuery("select h from House h inner join h.owner ho inner join ho.rentInfos horent where horent.checkIn between :localDate and :localDate2", House.class)
                    .setParameter("localDate", localDate).setParameter("localDate2", localDate2).getResultList();
            if (houses.isEmpty()){
                entityManager.getTransaction().rollback();
                throw new MyException("Not found rented houses between these date");
            }
            entityManager.getTransaction().commit();
            return houses;
        }catch (HibernateException|MyException exception){
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
}
