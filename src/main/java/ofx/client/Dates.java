package ofx.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.common.base.Throwables.propagate;

public final class Dates {
    private Dates() {}

    public static Date newDate(String date) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
           throw propagate(e);
        }
    }
}
