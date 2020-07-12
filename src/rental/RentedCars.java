package rental;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RentedCars implements Serializable {

    private List<String> rentedCars = new ArrayList<String>();
    private static final long serializeUID = 1L;

    public List<String> getCarsList() {
        return this.rentedCars;
    }

    public void add(String plateNo) {
        rentedCars.add(plateNo);
    }

    public void remove(String plateNo) {
        rentedCars.remove(plateNo);
    }

    public int size() {
        return rentedCars.size();
    }

}
