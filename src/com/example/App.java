package com.example;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class App {

    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("Flat");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add client");
                    System.out.println("2: search by params");
                    System.out.println("3: delete client");
                    System.out.println("4: change client");
                    System.out.println("5: view clients");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addClient(sc);
                            break;
                        case "2":
                            searchByParams(sc);
                            break;
                        case "3":
                            deleteClient(sc);
                            break;
                        case "4":
                            changeClient(sc);
                            break;
                        case "5":
                            viewClients();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void addClient(Scanner sc) {
        System.out.print("Enter flat district: ");
        String district = sc.nextLine();
        System.out.print("Enter flat address: ");
        String address = sc.nextLine();
        System.out.print("Enter flat square: ");
        String sSquare = sc.nextLine();
        double square = Double.parseDouble(sSquare);
        System.out.print("Enter count of rooms of the flat: ");
        String sCountOfRooms = sc.nextLine();
        int countOfRooms = Integer.parseInt(sCountOfRooms);
        System.out.print("Enter price of the flat: ");
        String sPrice = sc.nextLine();
        double price = Double.parseDouble(sPrice);

        em.getTransaction().begin();
        try {
            Flat flat = new Flat(district, address, square, countOfRooms, price);
            em.persist(flat);
            em.getTransaction().commit();

            System.out.println(flat.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteClient(Scanner sc) {
        System.out.print("Enter flat id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        Flat flat = em.getReference(Flat.class, id);
        if (flat == null) {
            System.out.println("Flat not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(flat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeClient(Scanner sc) {
        System.out.print("Enter flat id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        System.out.print("Enter new flat district: ");
        String district = sc.nextLine();
        System.out.print("Enter new flat address: ");
        String address = sc.nextLine();
        System.out.print("Enter new flat square: ");
        String sSquare = sc.nextLine();
        double square = Double.parseDouble(sSquare);
        System.out.print("Enter new count of rooms of the flat: ");
        String sCountOfRooms = sc.nextLine();
        int countOfRooms = Integer.parseInt(sCountOfRooms);
        System.out.print("Enter new price of the flat: ");
        String sPrice = sc.nextLine();
        double price = Double.parseDouble(sPrice);

        Flat flat;
        try {
            Query query = em.createQuery(
                    "SELECT x FROM Flat x WHERE x.id = :id", Flat.class);
            query.setParameter("id", id);
            flat = (Flat) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Flat not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }

        em.getTransaction().begin();
        try {
            flat.setDistrict(district);
            flat.setAddress(address);
            flat.setSquare(square);
            flat.setCountOfRooms(countOfRooms);
            flat.setPrice(price);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewClients() {
        Query query = em.createQuery(
                "SELECT flat FROM Flat flat", Flat.class);
        List<Flat> list = (List<Flat>) query.getResultList();

        for (Flat flat : list)
            System.out.println(flat);
    }

    private static void searchByParams(Scanner sc) {
        String sqlCond = conditionForSearchingByParams(sc);
        System.out.println("My sqlCond is: " + sqlCond);
        String s="SELECT flat FROM Flat flat ";
        String sql =s + sqlCond;
        System.out.println("sql:"+sql);
        try {
            Query query = em.createQuery(sql);
            List<Flat> list = (List<Flat>) query.getResultList();

            for (Flat flat : list)
                System.out.println(flat);
        } catch (Exception e) {
            System.err.println("Not correct query");
        }

    }

    private static String conditionForSearchingByParams(Scanner sc) {
        StringBuilder cond = new StringBuilder("WHERE ");
        for (; ; ) {
            System.out.println("Searching operations:");
            System.out.println("1: search by district");
            System.out.println("2: search by address");
            System.out.println("3: search by square");
            System.out.println("4: search by the count of rooms");
            System.out.println("5: search by price");
            System.out.print("-> ");
            String option = sc.nextLine();
            System.out.println("Value");
            System.out.print("-> ");
            String value=sc.nextLine();
            switch (option) {
                case "1":
                    cond.append("district=")
                            .append("'").append(value).append("'").append(" AND ");
                    break;
                case "2":
                    cond.append("address=")
                            .append("'").append(value).append("'").append(" AND ");
                    break;
                case "3":
                    cond.append("square=").append(value).append(" AND ");
                    break;
                case "4":
                    cond.append("countOfRooms=").append(value).append(" AND ");
                    break;
                case "5":
                    cond.append("price=").append(value).append(" AND ");
                    break;
                case "":
                    return cond.delete(cond.length() - 5, cond.length() - 1).toString();
            }
        }
    }
}

