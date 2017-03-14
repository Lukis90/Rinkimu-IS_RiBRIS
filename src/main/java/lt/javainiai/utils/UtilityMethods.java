package lt.javainiai.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.web.multipart.MultipartFile;

public class UtilityMethods {

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double roundUp(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.UP);
        return bd.doubleValue();
    }

    public static File multipartToFile(MultipartFile file) {
        File convFile = null;
        try {
            convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return convFile;
    }

}