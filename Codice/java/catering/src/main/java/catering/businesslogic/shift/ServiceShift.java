package catering.businesslogic.shift;

import java.time.LocalDate;
import java.time.LocalTime;

public class ServiceShift extends Shift {

    public ServiceShift(int id, LocalTime start_time, LocalTime end_time, String place, LocalDate date, int service_id) {
        super(id, start_time, end_time, place, date, service_id);
    }
}