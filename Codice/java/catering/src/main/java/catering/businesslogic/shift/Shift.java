package catering.businesslogic.shift;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Shift {

    int ID;
    LocalTime start;
    LocalTime end;

    String place;

    LocalDate date;

    int serviceID;

    public Shift() {
    }

    public Shift(int ID, LocalTime start, LocalTime end, String place, LocalDate date, int serviceID) {
        this.ID = ID;
        this.start = start;
        this.end = end;
        this.place = place;
        this.date = date;
        this.serviceID = serviceID;
    }

    public static ArrayList<Shift> loadShiftByServiceID(int serviceID) {
        String query = "SELECT * FROM Shifts WHERE service_id =" + serviceID;

        ArrayList<Shift> shifts = new ArrayList<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String type = rs.getString("type");
                Shift shift;

                if (type.equals("kitchen")) {
                    shift = new KitchenShift(
                            rs.getInt("id"),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime(),
                            rs.getString("place"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("service_id")
                    );
                } else {
                    shift = new ServiceShift(
                            rs.getInt("id"),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime(),
                            rs.getString("place"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("service_id")
                    );
                }

                shifts.add(shift);
            }
        });
        return shifts;
    }

    public static Shift loadShiftById(int shift_id) {
        String query = "SELECT * FROM Shifts WHERE id =" + shift_id;

        AtomicReference<Shift> shiftRef = new AtomicReference<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Shift shift;
                String type = rs.getString("type");
                if (type.equals("kitchen")) {
                    shift = new KitchenShift(
                            rs.getInt("id"),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime(),
                            rs.getString("place"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("service_id")
                    );
                } else {
                    shift = new ServiceShift(
                            rs.getInt("id"),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime(),
                            rs.getString("place"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("service_id")
                    );
                }
                shiftRef.set(shift);
            }
        });
        return shiftRef.get();
    }

    public long getDuration() {
        return Duration.between(start, end).toMinutes();
    }

    // toString
    public String toString() {
        return "Shift{ID: " + ID + " Start: " + start + " End: " + end + " Date: " + date + " Place: " + place + "}";
    }

    public int getID() {
        return ID;
    }
}