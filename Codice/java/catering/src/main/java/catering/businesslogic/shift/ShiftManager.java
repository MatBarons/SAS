package catering.businesslogic.shift;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ShiftManager {

    private ArrayList<Shift> shiftBoard;

    public ArrayList<Shift> getShiftBoard(int serviceID) {
        return shiftBoard;
    }

    public ArrayList<KitchenShift> getKitchenShiftBoard(int serviceID) {
        return shiftBoard.stream().filter(s -> s instanceof KitchenShift).map(s -> (KitchenShift) s).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<ServiceShift> getServiceShiftBoard(int serviceID) {
        return shiftBoard.stream().filter(s -> s instanceof ServiceShift).map(s -> (ServiceShift) s).collect(Collectors.toCollection(ArrayList::new));
    }
}