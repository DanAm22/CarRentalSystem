
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CarRentalSystem {

    private static Scanner sc = new Scanner(System.in);
    private static HashMap<String, String> rentedCars = new HashMap<String, String>(100, 0.5f);
    private static HashMap<String, RentedCars> driverCars = new HashMap<>(100,0.5f);

    private static String getPlateNumber() {
        System.out.println("Enter the license plate:");
        return sc.nextLine();
    }

    private static String getDriverName() {
        System.out.println("Enter the drivers's name:");
        return sc.nextLine();
    }

    private static boolean isCarRent(String plateNo) {
        return rentedCars.containsKey(plateNo);
    }

    private static String getCarRent(String plateNo) {
        return "Driver's name is: " + rentedCars.get(plateNo);
    }

    private void rentCar(String plateNo, String driverName) {
        if (!isCarRent(plateNo)){
            // Verify if the owner is already a client, if not we'll register him
            if(!driverCars.containsKey(driverName)){
                RentedCars rented = new RentedCars();
                driverCars.put(driverName,rented);
            }
            // We'll give the car to the new owner and add it into his collection
            rentedCars.put(plateNo, driverName);
            driverCars.get(driverName).add(plateNo);
        }
        else System.out.println("Masina este deja inchiriata.");
    }

    private void returnCar(String plateNo) {
        if (isCarRent(plateNo)){
            // This line removes the car from owner collection
            driverCars.get(rentedCars.get(plateNo)).remove(plateNo);
            // This line returns the car to the car rental company
            rentedCars.remove(plateNo);
        }
        else System.out.println("Masina nu este inchiriata.");
    }

    // Returns the number of cars rented
    private int totalRented(){
        return rentedCars.size();
    }

    // Returns the cars list rented by a driver
    private List<String> getCarsList(String driverName){
        return driverCars.get(driverName).getCarsList();
    }

    // Returns the number of cars rented by a driver
    private int getCarsNo(String driverName){
        return driverCars.get(driverName).size();
    }

    private static void printCommandsList() {
        System.out.println("help         - Displays this command list");
        System.out.println("add          - Add a new pair (car, owner)");
        System.out.println("check        - Check if a car is already taken");
        System.out.println("remove       - Returning a car to car rental company");
        System.out.println("getOwner     - Displays the current owner of the car");
        System.out.println("getCarsList  - Displays all cars lent to the owner");
        System.out.println("getCarsNo    - Displays all cars lent to the owner");
        System.out.println("totalRented  - Displays all cars lent to the owner");
        System.out.println("quit         - Close application");
    }

    public void run() {
        boolean quit = false;
        while (!quit) {
            System.out.println("Asteapta comanda: (help - Afiseaza lista de comenzi)");
            String command = sc.nextLine();
            switch (command) {
                case "help":
                    printCommandsList();
                    break;
                case "add":
                    rentCar(getPlateNumber(), getDriverName());
                    break;
                case "check":
                    System.out.println(isCarRent(getPlateNumber()));
                    break;
                case "remove":
                    returnCar(getPlateNumber());
                    break;
                case "getOwner":
                    System.out.println(getCarRent(getPlateNumber()));
                    break;
                case "getCarsList":
                    System.out.println(getCarsList(getDriverName()));
                    break;
                case "getCarsNo":
                    System.out.println(getCarsNo(getDriverName()));
                    break;
                case "totalRented":
                    System.out.println("Number of cars rented is: " + totalRented());
                    break;
                case "quit":
                    System.out.println("Application is shuting down...");
                    quit = true;
                    break;
                default:
                    System.out.println("Unknown command. Choose from:");
                    printCommandsList();
            }
        }
    }

    public static void main(String[] args) {
        new CarRentalSystem().run();
    }
}
