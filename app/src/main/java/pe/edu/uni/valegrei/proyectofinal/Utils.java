package pe.edu.uni.valegrei.proyectofinal;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String md5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String fechaFormato(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        //format.setTimeZone(TimeZone.getTimeZone("America/Lima"));
        return format.format(date);
    }
}
