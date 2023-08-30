package catering.businesslogic.shift;

import javafx.collections.ObservableList;

public class ShiftManager{

    private ObservableList<Shift> shiftBoard;

    public ObservableList<Shift> getShiftBoard(int serviceID){
        return shiftBoard;
    }
}