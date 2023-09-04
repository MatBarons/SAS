package catering.businesslogic.shift;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ShiftManager {

    private ArrayList<Shift> shiftBoard;

    public ShiftManager() {
    }

    public ArrayList<Shift> getShiftBoard(int serviceID) {
        return Shift.loadShiftByServiceID(serviceID);
    }

    public ArrayList<KitchenShift> getKitchenShiftBoard(int serviceID) {
        return getShiftBoard(serviceID).stream().filter(s -> s instanceof KitchenShift).map(s -> (KitchenShift) s).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<ServiceShift> getServiceShiftBoard(int serviceID) {
        return getShiftBoard(serviceID).stream().filter(s -> s instanceof ServiceShift).map(s -> (ServiceShift) s).collect(Collectors.toCollection(ArrayList::new));
    }
}