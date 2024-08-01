package org.example.menu;

import org.example.config.HibernateConfig;
import org.example.entity.*;
import org.example.enums.Gender;
import org.example.enums.HouseType;
import org.example.exception.MyException;
import org.example.service.impl.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MenuAirBNB {
    public static void main(String[] args) {
        System.out.println(HibernateConfig.getEntityManagerFactory());

    }

    public void menu() {
        System.out.println(HibernateConfig.getEntityManagerFactory());

        AddressServiceImpl addressService = new AddressServiceImpl();
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        HouseServiceImpl houseService = new HouseServiceImpl();
        OwnerServiceImpl ownerService = new OwnerServiceImpl();
        AgencyServiceImpl agencyService = new AgencyServiceImpl();
        Rent_InfoServiceImpl rentInfoService = new Rent_InfoServiceImpl();

        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        int intValue1;
        while (true) {
            System.out.println("""
                                        
                    Welcome my friend!!!
                                        
                    1>>>Create all table                  |  28>>> Get owner by Agency id 
                    2>>>Save Agency                       |  29>>> Get Owner first name and age
                    3>>>Get by Agency by ID               |  30>>> Save House (alone)
                    4>>>Update Agency                     |  31>>> Save House with Owner
                    5>>>Delete Agency                     |  32>>> Get house by id
                    6>>>Get all Agencies                  |  33>>> Update house
                    7>>>Get all addresses with agencies   |  34>>> Get all houses
                    8>>>Update addresses                  |  35>>> Delete House
                    9>>>Get all addresses                 |  36>>> Get all houses by Region name
                    10>>>Get address by id                |  37>>> Get all house by Agency id
                    11>>>Delete address                   |  38>>> Get all house by Owner id
                    12>>>Get count of Agency by City name |  39>>> Get all house between date
                    13>>>Get all Agency for each Region
                    14>>>Save Customer
                    15>>>SaveCustomer with House
                    16>>>Assign House to Customer
                    17>>>Get Customer by Id
                    18>>>Update Customer
                    19>>>Get all Customer
                    20>>>Delete Customer
                    21>>>Save Owner
                    22>>>Save owner with hose
                    23>>>Assign Owner to Agency
                    24>>>Get Owner by id
                    25>>>Update Owner
                    26>>>Get all Owners
                    27>>>Delete Owners
                                        
                    """);
            intValue1 = scannerInt.nextInt();
            switch (intValue1) {
                case 1:
                    System.out.println(HibernateConfig.getEntityManagerFactory());
                    break;
                case 2:
                    System.out.println("Write Agency name: ");
                    String agencyName = scannerString.nextLine();
                    System.out.println("Write phone number (phone number must be start with +996: ");
                    String phoneNumber = scannerString.nextLine();
                    System.out.println("Address:");
                    System.out.println("Write City :");
                    String cityAgency = scannerString.nextLine();
                    System.out.println("Write region:");
                    String regionAgency = scannerString.nextLine();
                    System.out.println("Write street:");
                    String agencyStreet = scannerString.nextLine();
                    System.out.println(agencyService.saveAgency(new Agency(agencyName, phoneNumber, new Address(cityAgency, regionAgency, agencyStreet))));
                    break;
                case 3:
                    System.out.println("Write Agency ID to get:");
                    Long agencyId = scannerInt.nextLong();
                    System.out.println(agencyService.getAgencyById(agencyId));
                    break;
                case 4:
                    System.out.println("Write Agency ID to update:");
                    Long oldAgencyID = scannerInt.nextLong();
                    System.out.println("Write new Agency name:");
                    String newAgencyName = scannerString.nextLine();
                    System.out.println("Write new Agency phone number:");
                    String newAgencyPhoneNumber = scannerString.nextLine();
                    System.out.println("Address:");
                    System.out.println("Write new Agency City:");
                    String newAgencyCity = scannerString.nextLine();
                    System.out.println("Write new Agency Region:");
                    String newAgencyRegion = scannerString.nextLine();
                    System.out.println("Write new Agency street:");
                    String newAgencyStreet = scannerString.nextLine();
                    System.out.println(agencyService.updateAgency(oldAgencyID, new Agency(newAgencyName, newAgencyPhoneNumber, new Address(newAgencyCity, newAgencyRegion, newAgencyStreet))));
                    break;
                case 5:
                    System.out.println("Write Agency id to delete:");
                    Long agencyIdToDelete = scannerInt.nextLong();
                    System.out.println(agencyService.deleteAgency(agencyIdToDelete));
                    break;
                case 6:
                    agencyService.getAllAgency().forEach(System.out::println);
                    break;
                case 7:
                    addressService.getAllAddressWithAgency().forEach(System.out::println);
                    break;
                case 8:
                    System.out.println("Update");
                    System.out.println("Write address id to update:");
                    Long addressIdToUpdate = scannerInt.nextLong();
                    System.out.println("Write new city:");
                    String newCityAddress = scannerString.nextLine();
                    System.out.println("Write new region:");
                    String newRegionAddress = scannerString.nextLine();
                    System.out.println("Write new street:");
                    String newStreetAddress = scannerString.nextLine();
                    System.out.println(addressService.updateAddress(addressIdToUpdate, new Address(newCityAddress, newRegionAddress, newStreetAddress)));
                    break;
                case 9:
                    addressService.getAllAddress().forEach(System.out::println);
                    break;
                case 10:
                    System.out.println("Write Address id:");
                    Long addressId = scannerInt.nextLong();
                    System.out.println(addressService.getAddressById(addressId));
                    break;
                case 11:
                    System.out.println("Write address id tp delete:");
                    Long addressIdToDelete = scannerInt.nextLong();
                    addressService.deleteAddress(addressIdToDelete);
                    break;
                case 12:
                    System.out.println("Write City name to get count of Agency");
                    String cityName = scannerString.nextLine();
                    System.out.println(addressService.getCountAgency(cityName));
                    break;
                case 13:
                    System.out.println(addressService.groupByRegion());
                    break;
                case 14:
                    System.out.println("Write first name:");
                    String customerFirsName = scannerString.nextLine();
                    System.out.println("Write last name:");
                    String customerLastName = scannerString.nextLine();
                    System.out.println("Write email:");
                    String customerEmail = scannerString.nextLine();
                    System.out.println("Write date of birth of customer (format YYYY-MM-DD)");
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String dateOfBirth = scannerString.nextLine();
                    try {
                        LocalDate dateOfBirthCustomer = LocalDate.parse(dateOfBirth, dateTimeFormatter);

                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write gender (MALE || FEMALE):");
                    try {
                        String genderCustomer = scannerString.nextLine().toUpperCase();
                        if (!genderCustomer.equalsIgnoreCase("MALE") && !genderCustomer.equalsIgnoreCase("FEMALE")) {
                            throw new MyException("gender must be written MALE or FEMALE");
                        }
                        System.out.println("Write Nationality:");
                        String customerNationality = scannerString.nextLine();
                        System.out.println(customerService.saveCustomer(new Customer(customerFirsName, customerLastName, customerEmail, LocalDate.parse(dateOfBirth), Gender.valueOf(genderCustomer), customerNationality)));
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 15:
                    System.out.println("Write first name:");
                    String customerFirsName1 = scannerString.nextLine();
                    System.out.println("Write last name:");
                    String customerLastName1 = scannerString.nextLine();
                    System.out.println("Write email:");
                    String customerEmail1 = scannerString.nextLine();
                    System.out.println("Write date of birth of customer (format YYYY-MM-DD)");
                    DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String dateOfBirth1 = scannerString.nextLine();
                    try {
                        LocalDate dateOfBirthCustomer = LocalDate.parse(dateOfBirth1, dateTimeFormatter1);

                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write gender (MALE || FEMALE):");
                    String genderCustomer1 = null;
                    try {
                        genderCustomer1 = scannerString.nextLine().toUpperCase();
                        if (!genderCustomer1.equalsIgnoreCase("MALE") && !genderCustomer1.equalsIgnoreCase("FEMALE")) {
                            throw new MyException("Gender must be MALE or FEMALE");
                        }
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write Nationality:");
                    String customerNationality1 = scannerString.nextLine();
                    System.out.println("House:");
                    System.out.println("Write check-in date (format: yyyy-MM-dd)");
                    DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String checkIn = scannerString.nextLine();
                    try {
                        LocalDate checkInDate = LocalDate.parse(checkIn, dateTimeFormatter2);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write check_out date (format yyyy-MM-dd): ");
                    DateTimeFormatter dateTimeFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String checkOut = scannerString.nextLine();
                    try {
                        LocalDate checkOutHouse = LocalDate.parse(checkOut, dateTimeFormatter3);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write house type:" + """
                            APARTMENT,
                                DETACHED_HOUSE,
                                SEMI_DETACHED_HOUSE,
                                TERRACED_HOUSE,
                                BUNGALOW,
                                COTTAGE,
                                VILLA,
                                MOBILE_HOME,
                                STUDIO,
                                DUPLEX
                            """);
                    String newHouseType11 = null;
                    try {
                        newHouseType11 = scannerString.nextLine().toUpperCase();
                        if (!newHouseType11.equalsIgnoreCase("APARTMENT") &&
                            !newHouseType11.equalsIgnoreCase("SEMI_DETACHED_HOUSE") &&
                            !newHouseType11.equalsIgnoreCase("TERRACED_HOUSE") &&
                            !newHouseType11.equalsIgnoreCase("BUNGALOW") &&
                            !newHouseType11.equalsIgnoreCase("COTTAGE") &&
                            !newHouseType11.equalsIgnoreCase("VILLA") &&
                            !newHouseType11.equalsIgnoreCase("MOBILE_HOME") &&
                            !newHouseType11.equalsIgnoreCase("STUDIO") &&
                            !newHouseType11.equalsIgnoreCase("DUPLEX")) {
                            throw new MyException("House type must be only APARTMENT,\n" +
                                                  "                                DETACHED_HOUSE,\n" +
                                                  "                                SEMI_DETACHED_HOUSE,\n" +
                                                  "                                TERRACED_HOUSE,\n" +
                                                  "                                BUNGALOW,\n" +
                                                  "                                COTTAGE,\n" +
                                                  "                                VILLA,\n" +
                                                  "                                MOBILE_HOME,\n" +
                                                  "                                STUDIO,\n" +
                                                  "                                DUPLEX");

                        }
                        ;
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write price :");
                    BigDecimal priceHouse = scannerInt.nextBigDecimal();
                    System.out.println("Write rating:");
                    double ratingHouse = scannerInt.nextDouble();
                    System.out.println("Write description:");
                    String description = scannerString.nextLine();
                    System.out.println("write room of house:");
                    int roomHouse = scannerInt.nextInt();
                    System.out.println("Choose furniture (true|| false)");
                    String trueOrFalse = scannerString.nextLine();
                    boolean isHasFurniture = Boolean.parseBoolean(trueOrFalse);
                    System.out.println("Address:");
                    System.out.println("Write new city:");
                    String cityAddress = scannerString.nextLine();
                    System.out.println("Write new region:");
                    String regionAddress = scannerString.nextLine();
                    System.out.println("Write new street:");
                    String streetAddress = scannerString.nextLine();
                    System.out.println("Owner");
                    System.out.println("Write  Owner first name:");
                    String firstNameOwner = scannerString.nextLine();
                    System.out.println("Write Owner last name:");
                    String lastNameOwner = scannerString.nextLine();
                    System.out.println("Write Owner email:");
                    String emailOwner = scannerString.nextLine();
                    System.out.println("Write owner date of birth (format yyyy-MM-dd)");
                    String dateOfBirthOwner = scannerString.nextLine();
                    DateTimeFormatter dateTimeFormatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate dateofBirthOwner = LocalDate.parse(dateOfBirthOwner, dateTimeFormatter4);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write Gender (MALE || FEMALE)");
                    String genderOwner = scannerString.nextLine().toUpperCase();
                    System.out.println(customerService.saveCustomerWithHouse(new Customer(customerFirsName1, customerLastName1, customerEmail1, LocalDate.parse(checkIn), Gender.valueOf(genderCustomer1), customerNationality1),
                            new Rent_Info(LocalDate.parse(checkIn), LocalDate.parse(checkOut), new House(HouseType.valueOf(newHouseType11), priceHouse, ratingHouse, description, roomHouse, isHasFurniture, new Address(cityAddress, regionAddress, streetAddress))), new Owner(firstNameOwner, lastNameOwner, emailOwner, LocalDate.parse(dateOfBirthOwner), Gender.valueOf(genderOwner))));
                    break;
                case 16:
                    System.out.println("Write House id:");
                    Long houseId1 = scannerInt.nextLong();
                    System.out.println("Write Customer id :");
                    Long customerId1 = scannerInt.nextLong();
                    System.out.println("Write owner id:");
                    Long ownerId1 = scannerInt.nextLong();
                    System.out.println("Write check-in date:");
                    String checkInDate = scannerString.nextLine();
                    DateTimeFormatter dateTimeFormatter5 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate dateofBirthOwner = LocalDate.parse(checkInDate, dateTimeFormatter5);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write check_out date (format yyyy-MM-dd):");
                    String checkOutDate = scannerString.nextLine();
                    DateTimeFormatter dateTimeFormatter6 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate dateofBirthOwner = LocalDate.parse(checkOutDate, dateTimeFormatter6);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write Agency id:");
                    Long agencyId1 = scannerInt.nextLong();
                    System.out.println(customerService.assignHouseToCustomer(houseId1, customerId1, ownerId1, LocalDate.parse(checkInDate), LocalDate.parse(checkOutDate), agencyId1));
                    break;
                case 17:
                    System.out.println("Write Customer id:");
                    Long customerId = scannerString.nextLong();
                    System.out.println(customerService.getCustomerById(customerId));
                    break;
                case 18:
                    System.out.println("Write old Customer id to update:");
                    Long oldCustomerID = scannerInt.nextLong();
                    System.out.println("Write first name:");
                    String newCustomerFirsName = scannerString.nextLine();
                    System.out.println("Write last name:");
                    String newCustomerLastName = scannerString.nextLine();
                    System.out.println("Write email:");
                    String newCustomerEmail = scannerString.nextLine();
                    System.out.println("Write date of birth of customer (format YYYY-MM-DD)");
                    DateTimeFormatter dateTimeFormatterr = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String dateOfBirth0 = scannerString.nextLine();
                    try {
                        LocalDate dateOfBirthCustomer = LocalDate.parse(dateOfBirth0, dateTimeFormatterr);

                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write gender (MALE || FEMALE):");
                    String newGenderCustomer = scannerString.nextLine().toUpperCase();
                    System.out.println("Write Nationality:");
                    String newCustomerNationality = scannerString.nextLine();
                    System.out.println(customerService.updateCustomer(oldCustomerID, new Customer(newCustomerFirsName, newCustomerLastName, newCustomerEmail, LocalDate.parse(dateOfBirth0), Gender.valueOf(newGenderCustomer), newCustomerNationality)));
                    break;
                case 19:
                    customerService.getAllCustomers().forEach(System.out::println);
                    break;
                case 20:
                    System.out.println("Write customer Id to delete:");
                    Long customerIDToDelete = scannerString.nextLong();
                    System.out.println(customerService.deleteCustomer(customerIDToDelete));
                    break;
                case 21:
                    System.out.println("Write  Owner first name:");
                    String firstNameOwner1 = scannerString.nextLine();
                    System.out.println("Write Owner last name:");
                    String lastNameOwner1 = scannerString.nextLine();
                    System.out.println("Write Owner email:");
                    String emailOwner1 = scannerString.nextLine();
                    System.out.println("Write owner date of birth (format yyyy-MM-dd)");
                    String dateOfBirthOwner1 = scannerString.nextLine();
                    DateTimeFormatter dateTimeFormatter0 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate dateofBirthOwner = LocalDate.parse(dateOfBirthOwner1, dateTimeFormatter0);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write Gender (MALE || FEMALE)");
                    String genderOwner1 = scannerString.nextLine().toUpperCase();

                    System.out.println(ownerService.saveOwner(new Owner(firstNameOwner1, lastNameOwner1, emailOwner1, LocalDate.parse(dateOfBirthOwner1), Gender.valueOf(genderOwner1))));
                    break;
                case 22:
                    System.out.println("Write  Owner first name:");
                    String firstNameOwner2 = scannerString.nextLine();
                    System.out.println("Write Owner last name:");
                    String lastNameOwner2 = scannerString.nextLine();
                    System.out.println("Write Owner email:");
                    String emailOwner2 = scannerString.nextLine();
                    System.out.println("Write owner date of birth (format yyyy-MM-dd)");
                    String dateOfBirthOwner2 = scannerString.nextLine();
                    DateTimeFormatter dateTimeFormatter01 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate dateofBirthOwner = LocalDate.parse(dateOfBirthOwner2, dateTimeFormatter01);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write Gender (MALE || FEMALE)");
                    String genderOwner2 = scannerString.nextLine().toUpperCase();
                    System.out.println("Write house type:" + """
                            APARTMENT,
                                DETACHED_HOUSE,
                                SEMI_DETACHED_HOUSE,
                                TERRACED_HOUSE,
                                BUNGALOW,
                                COTTAGE,
                                VILLA,
                                MOBILE_HOME,
                                STUDIO,
                                DUPLEX
                            """);
                    String houseType1 = null;

                    try {
                        houseType1 = scannerString.nextLine().toUpperCase();
                        if (!houseType1.equalsIgnoreCase("APARTMENT") &&
                            !houseType1.equalsIgnoreCase("SEMI_DETACHED_HOUSE") &&
                            !houseType1.equalsIgnoreCase("TERRACED_HOUSE") &&
                            !houseType1.equalsIgnoreCase("BUNGALOW") &&
                            !houseType1.equalsIgnoreCase("COTTAGE") &&
                            !houseType1.equalsIgnoreCase("VILLA") &&
                            !houseType1.equalsIgnoreCase("MOBILE_HOME") &&
                            !houseType1.equalsIgnoreCase("STUDIO") &&
                            !houseType1.equalsIgnoreCase("DUPLEX")) {
                            throw new MyException("House type must be only APARTMENT,\n" +
                                                  "                                DETACHED_HOUSE,\n" +
                                                  "                                SEMI_DETACHED_HOUSE,\n" +
                                                  "                                TERRACED_HOUSE,\n" +
                                                  "                                BUNGALOW,\n" +
                                                  "                                COTTAGE,\n" +
                                                  "                                VILLA,\n" +
                                                  "                                MOBILE_HOME,\n" +
                                                  "                                STUDIO,\n" +
                                                  "                                DUPLEX");

                        }
                        ;
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write price :");
                    BigDecimal priceHouse1 = scannerInt.nextBigDecimal();
                    System.out.println("Write rating:");
                    double ratingHouse1 = scannerInt.nextDouble();
                    System.out.println("Write description:");
                    String description1 = scannerString.nextLine();
                    System.out.println("write room of house:");
                    int roomHouse1 = scannerInt.nextInt();
                    System.out.println("Choose furniture (true|| false)");
                    String trueOrFalse1 = scannerString.nextLine();
                    boolean isHasFurniture1 = Boolean.parseBoolean(trueOrFalse1);
                    System.out.println("Address:");
                    System.out.println("Write new city:");
                    String cityAddress1 = scannerString.nextLine();
                    System.out.println("Write new region:");
                    String regionAddress1 = scannerString.nextLine();
                    System.out.println("Write new street:");
                    String streetAddress1 = scannerString.nextLine();
                    System.out.println(ownerService.saveOwnerWithHouse(new Owner(firstNameOwner2, lastNameOwner2, emailOwner2, LocalDate.parse(dateOfBirthOwner2), Gender.valueOf(genderOwner2)), new House(HouseType.valueOf(houseType1), priceHouse1, ratingHouse1, description1, roomHouse1, isHasFurniture1, new Address(cityAddress1, regionAddress1, streetAddress1))));
                    break;
                case 23:
                    System.out.println("Write Owner id:");
                    Long ownerId = scannerInt.nextLong();
                    System.out.println("Write Agency id: ");
                    Long agencyID = scannerInt.nextLong();
                    System.out.println(ownerService.assignOwnerToAgency(ownerId, agencyID));
                    break;
                case 24:
                    System.out.println("Write Owner id:");
                    Long ownerID = scannerInt.nextLong();
                    System.out.println(ownerService.getById(ownerID));
                    break;
                case 25:
                    System.out.println("Write new Owner id to update:");
                    Long updatedOwnerId = scannerInt.nextLong();
                    System.out.println("Write new Owner first name:");
                    String newOwnerFirstName = scannerString.nextLine();
                    System.out.println("Write new Owner last name:");
                    String newOwnerLastName = scannerString.nextLine();
                    System.out.println("Write new Owner email:");
                    String newOwnerEmail = scannerString.nextLine();
                    System.out.println("Write new owner date of birth (format yyyy-MM-dd)");
                    String newOwnerDateOfBirth = scannerString.nextLine();
                    DateTimeFormatter dateTimeNewFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate dateofBirthOwner = LocalDate.parse(newOwnerDateOfBirth, dateTimeNewFormatter);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write new Gender (MALE || FEMALE)");
                    String newGenderOwner = scannerString.nextLine().toUpperCase();
                    System.out.println(ownerService.updateOwner(updatedOwnerId, new Owner(newOwnerFirstName, newOwnerLastName, newOwnerEmail, LocalDate.parse(newOwnerDateOfBirth), Gender.valueOf(newGenderOwner))));
                    break;
                case 26:
                    ownerService.getAllOwners().forEach(System.out::println);
                    break;
                case 27:
                    System.out.println("Write Owner Id to delete:");
                    Long deletedOwnerId = scannerInt.nextLong();
                    System.out.println(ownerService.deleteOwner(deletedOwnerId));
                    break;
                case 28:
                    System.out.println("Write Agency id to get Owner:");
                    Long agencyIdToGetOwner = scannerInt.nextLong();
                    System.out.println(ownerService.getOwnerByAgencyId(agencyIdToGetOwner));
                    break;
                case 29:
                    System.out.println(ownerService.getOwnerNameAndAge());
                    break;
                case 30:
                    System.out.println("Write house type:" + """
                            APARTMENT,
                                DETACHED_HOUSE,
                                SEMI_DETACHED_HOUSE,
                                TERRACED_HOUSE,
                                BUNGALOW,
                                COTTAGE,
                                VILLA,
                                MOBILE_HOME,
                                STUDIO,
                                DUPLEX
                            """);
                    String houseType10 = null;
                    try {
                        houseType10 = scannerString.nextLine().toUpperCase();
                        if (!houseType10.equalsIgnoreCase("APARTMENT") &&
                            !houseType10.equalsIgnoreCase("SEMI_DETACHED_HOUSE") &&
                            !houseType10.equalsIgnoreCase("TERRACED_HOUSE") &&
                            !houseType10.equalsIgnoreCase("BUNGALOW") &&
                            !houseType10.equalsIgnoreCase("COTTAGE") &&
                            !houseType10.equalsIgnoreCase("VILLA") &&
                            !houseType10.equalsIgnoreCase("MOBILE_HOME") &&
                            !houseType10.equalsIgnoreCase("STUDIO") &&
                            !houseType10.equalsIgnoreCase("DUPLEX")) {
                            throw new MyException("House type must be only APARTMENT,\n" +
                                                  "                                DETACHED_HOUSE,\n" +
                                                  "                                SEMI_DETACHED_HOUSE,\n" +
                                                  "                                TERRACED_HOUSE,\n" +
                                                  "                                BUNGALOW,\n" +
                                                  "                                COTTAGE,\n" +
                                                  "                                VILLA,\n" +
                                                  "                                MOBILE_HOME,\n" +
                                                  "                                STUDIO,\n" +
                                                  "                                DUPLEX");

                        }
                        ;
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write price :");
                    BigDecimal priceHouse10 = scannerInt.nextBigDecimal();
                    System.out.println("Write rating:");
                    double ratingHouse10 = scannerInt.nextDouble();
                    System.out.println("Write description:");
                    String description10 = scannerString.nextLine();
                    System.out.println("write room of house:");
                    int roomHouse10 = scannerInt.nextInt();
                    System.out.println("Choose furniture (true|| false)");
                    String trueOrFalse10 = scannerString.nextLine();
                    boolean isHasFurniture10 = Boolean.parseBoolean(trueOrFalse10);
                    System.out.println("Address:");
                    System.out.println("Write new city:");
                    String cityAddress10 = scannerString.nextLine();
                    System.out.println("Write new region:");
                    String regionAddress10 = scannerString.nextLine();
                    System.out.println("Write new street:");
                    String streetAddress10 = scannerString.nextLine();
                    System.out.println(houseService.save(new House(HouseType.valueOf(houseType10), priceHouse10, ratingHouse10, description10, roomHouse10, isHasFurniture10, new Address(cityAddress10, regionAddress10, streetAddress10))));
                    break;
                case 31:
                    System.out.println("Write house type:" + """
                            APARTMENT,
                                DETACHED_HOUSE,
                                SEMI_DETACHED_HOUSE,
                                TERRACED_HOUSE,
                                BUNGALOW,
                                COTTAGE,
                                VILLA,
                                MOBILE_HOME,
                                STUDIO,
                                DUPLEX
                            """);
                    String houseType11 = null;
                    try {
                        houseType11 = scannerString.nextLine().toUpperCase();
                        if (!houseType11.equalsIgnoreCase("APARTMENT") &&
                            !houseType11.equalsIgnoreCase("SEMI_DETACHED_HOUSE") &&
                            !houseType11.equalsIgnoreCase("TERRACED_HOUSE") &&
                            !houseType11.equalsIgnoreCase("BUNGALOW") &&
                            !houseType11.equalsIgnoreCase("COTTAGE") &&
                            !houseType11.equalsIgnoreCase("VILLA") &&
                            !houseType11.equalsIgnoreCase("MOBILE_HOME") &&
                            !houseType11.equalsIgnoreCase("STUDIO") &&
                            !houseType11.equalsIgnoreCase("DUPLEX")) {
                            throw new MyException("House type must be only APARTMENT,\n" +
                                                  "                                DETACHED_HOUSE,\n" +
                                                  "                                SEMI_DETACHED_HOUSE,\n" +
                                                  "                                TERRACED_HOUSE,\n" +
                                                  "                                BUNGALOW,\n" +
                                                  "                                COTTAGE,\n" +
                                                  "                                VILLA,\n" +
                                                  "                                MOBILE_HOME,\n" +
                                                  "                                STUDIO,\n" +
                                                  "                                DUPLEX");

                        }
                        ;
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write price :");
                    BigDecimal priceHouse11 = scannerInt.nextBigDecimal();
                    System.out.println("Write rating:");
                    double ratingHouse11 = scannerInt.nextDouble();
                    System.out.println("Write description:");
                    String description11 = scannerString.nextLine();
                    System.out.println("write room of house:");
                    int roomHouse11 = scannerInt.nextInt();
                    System.out.println("Choose furniture (true|| false)");
                    String trueOrFalse11 = scannerString.nextLine();
                    boolean isHasFurniture11 = Boolean.parseBoolean(trueOrFalse11);
                    System.out.println("Address:");
                    System.out.println("Write new city:");
                    String cityAddress11 = scannerString.nextLine();
                    System.out.println("Write new region:");
                    String regionAddress11 = scannerString.nextLine();
                    System.out.println("Write new street:");
                    String streetAddress11 = scannerString.nextLine();
                    System.out.println("Owner");
                    System.out.println("Write  Owner first name:");
                    String firstNameOwner11 = scannerString.nextLine();
                    System.out.println("Write Owner last name:");
                    String lastNameOwner11 = scannerString.nextLine();
                    System.out.println("Write Owner email:");
                    String emailOwner11 = scannerString.nextLine();
                    System.out.println("Write owner date of birth (format yyyy-MM-dd)");
                    String dateOfBirthOwner11 = scannerString.nextLine();
                    DateTimeFormatter dateTimeFormatter11 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate dateofBirthOwner = LocalDate.parse(dateOfBirthOwner11, dateTimeFormatter11);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write Gender (MALE || FEMALE)");
                    String genderOwner11 = scannerString.nextLine().toUpperCase();
                    System.out.println(houseService.saveWithOwner(new House(HouseType.valueOf(houseType11), priceHouse11, ratingHouse11, description11, roomHouse11, isHasFurniture11, new Address(cityAddress11, regionAddress11, streetAddress11)), new Owner(
                            firstNameOwner11, lastNameOwner11, emailOwner11, LocalDate.parse(dateOfBirthOwner11), Gender.valueOf(genderOwner11)
                    )));
                    break;
                case 32:
                    System.out.println("Write House id to get:");
                    Long houseIdToGet = scannerInt.nextLong();
                    System.out.println(houseService.getHouseById(houseIdToGet));
                    break;
                case 33:
                    System.out.println("Write House id to update:");
                    Long updatedHouse = scannerInt.nextLong();
                    System.out.println("Write house type:" + """
                            APARTMENT,
                                DETACHED_HOUSE,
                                SEMI_DETACHED_HOUSE,
                                TERRACED_HOUSE,
                                BUNGALOW,
                                COTTAGE,
                                VILLA,
                                MOBILE_HOME,
                                STUDIO,
                                DUPLEX
                            """);
                    String newHouseType111 = null;
                    try {
                        newHouseType111 = scannerString.nextLine().toUpperCase();
                        if (!newHouseType111.equalsIgnoreCase("APARTMENT") &&
                            !newHouseType111.equalsIgnoreCase("SEMI_DETACHED_HOUSE") &&
                            !newHouseType111.equalsIgnoreCase("TERRACED_HOUSE") &&
                            !newHouseType111.equalsIgnoreCase("BUNGALOW") &&
                            !newHouseType111.equalsIgnoreCase("COTTAGE") &&
                            !newHouseType111.equalsIgnoreCase("VILLA") &&
                            !newHouseType111.equalsIgnoreCase("MOBILE_HOME") &&
                            !newHouseType111.equalsIgnoreCase("STUDIO") &&
                            !newHouseType111.equalsIgnoreCase("DUPLEX")) {
                            throw new MyException("House type must be only APARTMENT,\n" +
                                                  "                                DETACHED_HOUSE,\n" +
                                                  "                                SEMI_DETACHED_HOUSE,\n" +
                                                  "                                TERRACED_HOUSE,\n" +
                                                  "                                BUNGALOW,\n" +
                                                  "                                COTTAGE,\n" +
                                                  "                                VILLA,\n" +
                                                  "                                MOBILE_HOME,\n" +
                                                  "                                STUDIO,\n" +
                                                  "                                DUPLEX");

                        }
                        ;
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write price :");
                    BigDecimal newPriceHouse11 = scannerInt.nextBigDecimal();
                    System.out.println("Write rating:");
                    double newRatingHouse11 = scannerInt.nextDouble();
                    System.out.println("Write description:");
                    String newDescription11 = scannerString.nextLine();
                    System.out.println("write room of house:");
                    int newRoomHouse11 = scannerInt.nextInt();
                    System.out.println("Choose furniture (true|| false)");
                    String newTrueOrFalse11 = scannerString.nextLine();
                    boolean newIsHasFurniture11 = Boolean.parseBoolean(newTrueOrFalse11);
                    System.out.println("Address:");
                    System.out.println("Write new city:");
                    String newCityAddress11 = scannerString.nextLine();
                    System.out.println("Write new region:");
                    String newRegionAddress11 = scannerString.nextLine();
                    System.out.println("Write new street:");
                    String newStreetAddress11 = scannerString.nextLine();
                    System.out.println(houseService.updateHouse(updatedHouse, new House(HouseType.valueOf(newHouseType111), newPriceHouse11, newRatingHouse11, newDescription11, newRoomHouse11, newIsHasFurniture11, new Address(newCityAddress11, newRegionAddress11, newStreetAddress11))));
                    break;
                case 34:
                    houseService.getAllHouse().forEach(System.out::println);
                    break;
                case 35:
                    System.out.println("Write house id to delete:");
                    Long deletedHouse = scannerInt.nextLong();
                    houseService.deleteHouse(deletedHouse);
                    break;
                case 36:
                    System.out.println("Write Region name to get all houses:");
                    String regionName = scannerString.nextLine();
                    System.out.println(houseService.getAllHouseByRegion(regionName));
                    break;
                case 37:
                    System.out.println("Write agency id to get all Houses:");
                    Long agencyIdToGetAllHouses = scannerInt.nextLong();
                    System.out.println(houseService.getAllHouseByAgencyId(agencyIdToGetAllHouses));
                    break;
                case 38:
                    System.out.println("Write Owner id to get all houses:");
                    Long ownerIdToGetAllHouses = scannerInt.nextLong();
                    System.out.println(houseService.getAllHouseByOwnerId(ownerIdToGetAllHouses));
                    break;
                case 39:
                    System.out.println("Write first check_in date (format yyyy-MM-dd) : ");
                    String checkInDateFirst = scannerString.nextLine();
                    DateTimeFormatter dateTimeFormatter7 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate localDate = LocalDate.parse(checkInDateFirst, dateTimeFormatter7);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Write second check-in date (format yyyy-MM-dd):");
                    String checkInDateSecond = scannerString.nextLine();
                    DateTimeFormatter dateTimeFormatter8 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate localDate = LocalDate.parse(checkInDateSecond, dateTimeFormatter8);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(houseService.getAllHouseBetweenDate(LocalDate.parse(checkInDateFirst), LocalDate.parse(checkInDateSecond)));
                    break;
                default:
                    System.out.println("Invalid");


            }
        }

    }
}
