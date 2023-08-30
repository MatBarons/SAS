package catering.businesslogic.shift;

import java.time.Instant;
import java.time.Duration;

abstract class Shift{

    Instant start;
    Instant end;
    String place;

    public long getDuration(){
        return Duration.between(start, end).toMinutes();
    }

}