package catering.businesslogic.shift;

import java.time.Instant;
import java.time.Duration;

class Shift {

    int ID;
    Instant start;
    Instant end;
    String place;

    public long getDuration() {
        return Duration.between(start, end).toMinutes();
    }

    // toString
    public String toString() {
        return "Shift{ID: " + ID + " Start: " + start + " End: " + end + " Place: " + place + "}";
    }

    public int getID() {
        return ID;
    }
}