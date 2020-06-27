import java.util.ArrayList;
import java.util.List;

public class RentedCars {

    private List<String> rentedCars = new ArrayList<String>();

    public List<String> getCarsList(){
        return this.rentedCars;
    }

    public void add(String plateNo){
        rentedCars.add(plateNo);
    }

    public void remove(String plateNo){
        rentedCars.remove(plateNo);
    }

    public int size(){
        return rentedCars.size();
    }

}
