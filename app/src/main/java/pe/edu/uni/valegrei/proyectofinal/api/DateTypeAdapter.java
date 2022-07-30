package pe.edu.uni.valegrei.proyectofinal.api;


import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DateTypeAdapter extends TypeAdapter<Date> {

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        //format.setTimeZone(TimeZone.getTimeZone("America/Lima"));
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        out.value(format.format(value));
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        Date date = null;
        String timestamp = in.nextString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        //format.setTimeZone(TimeZone.getTimeZone("America/Lima"));
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            date = format.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
