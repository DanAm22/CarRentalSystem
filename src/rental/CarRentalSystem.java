package rental;

import exceptions.*;

import java.io.*;
import java.util.*;


public class CarRentalSystem implements Serializable {

    private static Scanner sc = new Scanner(System.in);
    private static HashMap<String, String> rentedCars = new HashMap<String, String>(100, 0.5f);
    private static HashMap<String, RentedCars> driverCars = new HashMap<>(100, 0.5f);

    private static final long serializeUID = 1L;

    private static String getPlateNumber() throws InputMismatchException, PlateNumberException, CountyException {
        System.out.println("Enter the license plate:");
        String plateNumber = sc.nextLine();
        if (!(plateNumber.length() == 6 || plateNumber.length() == 7)) {
            throw new InputMismatchException("Plate number length is incorrect");
        }
        if (!(Character.isDigit(plateNumber.charAt(plateNumber.length() - 5)) && Character.isDigit(plateNumber.charAt(plateNumber.length() - 4)) &&
                Character.isLetter(plateNumber.charAt(plateNumber.length() - 3)) && Character.isLetter(plateNumber.charAt(plateNumber.length() - 2)) &&
                Character.isLetter(plateNumber.charAt(plateNumber.length() - 1)))) {
            throw new PlateNumberException("Plate number format is incorrect");
        }
        if (!isCountyValid(plateNumber)) {
            throw new CountyException("Invalid County");
        }
        return plateNumber;
    }

    private static boolean isCountyValid(String plateNumber) {
        for (County c : County.values()) {
            if (plateNumber.startsWith(c.getMessage())) {
                return true;
            }
        }
        return false;
    }

    private static String getDriverName() throws DriverNameException {
        System.out.println("Enter the drivers's name:");
        String driverName = sc.nextLine();
        for (Character c : driverName.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new DriverNameException("Driver name is incorrect.");
            }
        }
        return driverName;
    }

    private static boolean isCarRent(String plateNo) {
        return rentedCars.containsKey(plateNo);
    }

    private static String getCarRent(String plateNo) {
        return "Driver's name is: " + rentedCars.get(plateNo);
    }

    private void rentCar(String plateNo, String driverName) {
        if (!isCarRent(plateNo)) {
            // Verify if the owner is already a client, if not we'll register him
            if (!driverCars.containsKey(driverName)) {
                RentedCars rented = new RentedCars();
                driverCars.put(driverName, rented);
            }
            // We'll give the car to the new owner and add it into his collection
            rentedCars.put(plateNo, driverName);
            driverCars.get(driverName).add(plateNo);
        } else System.out.println("The car's already rented.");
    }

    private void returnCar(String plateNo) {
        if (isCarRent(plateNo)) {
            // This line removes the car from owner collection
            driverCars.get(rentedCars.get(plateNo)).remove(plateNo);
            // This line returns the car to the car rental company
            rentedCars.remove(plateNo);
        } else System.out.println("The car is not rented.");
    }

    // Returns the number of cars rented
    private int totalRented() {
        return rentedCars.size();
    }

    // Returns the cars list rented by a driver
    private List<String> getCarsList(String driverName) {
        return driverCars.get(driverName).getCarsList();
    }

    // Returns the number of cars rented by a driver
    private int getCarsNo(String driverName) {
        return driverCars.get(driverName).size();
    }


    // Write the HashMaps to a binary file
    private static void writeRentedCarsToBinaryFile(Map<String, String> rentedCars) throws IOException {
        try (ObjectOutputStream binaryFileOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("rentedCars.dat")))) {
            binaryFileOut.writeObject(rentedCars);
        } catch (IOException e) {
            System.out.println("IOException thrown: " + e.getMessage());
            return;
        }
    }

    // Write the HashMaps to a binary file
    private static void writeDriverCarsToBinaryFile(Map<String, RentedCars> driverCars) throws IOException {
        try (ObjectOutputStream binaryFileOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("driverCars.dat")))) {
            binaryFileOut.writeObject(driverCars);
        } catch (IOException e) {
            System.out.println("IOException thrown: " + e.getMessage());
            return;
        }
    }


    // Read rentedCars HashMap from binary file
    private static HashMap<String, String> readRentedCarsFromBinaryFile() throws IOException {
        HashMap<String, String> data = new HashMap<>();
        try (ObjectInputStream binaryFileIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("rentedCars.dat")))) {
            data = (HashMap<String, String>) binaryFileIn.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("A class not found exception: " + e.getMessage());
        }
        return data;
    }

    // Read driverCars HashMap from binary file
    private static HashMap<String, RentedCars> readDriverCarsFromBinaryFile() throws IOException {
        HashMap<String, RentedCars> data = new HashMap<>();
        try (ObjectInputStream binaryFileIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("driverCars.dat")))) {
            data = (HashMap<String, RentedCars>) binaryFileIn.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("A class not found exception: " + e.getMessage());
        }
        return data;
    }

    private static void printCommandsList() {
        System.out.println("help         - Displays this command list");
        System.out.println("add          - Add a new pair (car, owner)");
        System.out.println("check        - Check if a car is already taken");
        System.out.println("remove       - Returning a car to car rental company");
        System.out.println("getOwner     - Displays the current owner of the car");
        System.out.println("getCarsList  - Displays all cars lent to the owner");
        System.out.println("getCarsNo    - Displays the number of cars rented to the owner");
        System.out.println("totalRented  - Displays all cars rented");
        System.out.println("quit         - Close application");
    }

    public void run() throws InputMismatchException, PlateNumberException, CountyException, DriverNameException {

        try {
            rentedCars = readRentedCarsFromBinaryFile();
            driverCars = readDriverCarsFromBinaryFile();
        } catch (IOException e) {

        }

        boolean quit = false;
        while (!quit) {
            System.out.println("Wait command: (help - Displays this command list)");
            String command = sc.nextLine();
            switch (command) {
                case "help":
                    printCommandsList();
                    break;
                case "add":
                    while (true) {
                        try {
                            rentCar(getPlateNumber(), getDriverName());
                            writeDriverCarsToBinaryFile(driverCars);
                            writeRentedCarsToBinaryFile(rentedCars);
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Plate number length should be 6 or 7. Try again!");
                        } catch (PlateNumberException e) {
                            System.out.println("Plate number format is incorrect. Try again!");
                        } catch (CountyException e) {
                            System.out.println("Invalid county. Try again!");
                        } catch (DriverNameException e) {
                            System.out.println("Driver name is incorrect.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "check":
                    while (true) {
                        try {
                            System.out.println(isCarRent(getPlateNumber()));
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Plate number length is incorrect. Try again!");
                        } catch (PlateNumberException e) {
                            System.out.println("Plate number format is incorrect Try again!");
                        } catch (CountyException e) {
                            System.out.println("Invalid county. Try again!");
                        }
                    }
                    break;
                case "remove":
                    while (true) {
                        try {
                            returnCar(getPlateNumber());
                            writeDriverCarsToBinaryFile(driverCars);
                            writeRentedCarsToBinaryFile(rentedCars);
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Plate number length is incorrect. Try again!");
                        } catch (PlateNumberException e) {
                            System.out.println("Plate number format is incorrect Try again!");
                        } catch (CountyException e) {
                            System.out.println("Invalid county. Try again!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "getOwner":
                    while (true) {
                        try {
                            System.out.println(getCarRent(getPlateNumber()));
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Plate number length is incorrect. Try again!");
                        } catch (PlateNumberException e) {
                            System.out.println("Plate number format is incorrect Try again!");
                        } catch (CountyException e) {
                            System.out.println("Invalid county. Try again!");
                        }
                    }
                    break;
                case "getCarsList":
                    while (true) {
                        try {
                            System.out.println(getCarsList(getDriverName()));
                            break;
                        } catch (DriverNameException e) {
                            System.out.println("Driver name is incorrect.");
                        }
                    }
                    break;
                case "getCarsNo":
                    while (true) {
                        try {
                            System.out.println(getCarsNo(getDriverName()));
                            break;
                        } catch (DriverNameException e) {
                            System.out.println("Driver name is incorrect.");
                        }
                    }
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
