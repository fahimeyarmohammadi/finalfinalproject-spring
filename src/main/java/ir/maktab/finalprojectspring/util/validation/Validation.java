package ir.maktab.finalprojectspring.util.validation;

import ir.maktab.finalprojectspring.exception.InvalidInputException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public class Validation {

    public static ValidateInterface validate = (s, r, m) -> {
        if (s.equals("") || !s.matches(r))
            throw new InvalidInputException(m);
    };

    public static void validateName(String name) throws InvalidInputException {

        validate.accept(name, "^[a-zA-Z ]{2,}", "Invalid input");

    }

    public static void validatePassword(String pass) throws InvalidInputException {

        validate.accept(pass, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8}$",
                "Invalid input");

    }

    public static void validateEmail(String email) throws InvalidInputException {

        validate.accept(email,  "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
                "Invalid input");

    }

    public static byte[]validateImage(String imagePath) throws IOException {

        //size validation
        File file = new File(imagePath);
        double imageSize = file.length();
        double imageSIzeInKb = (imageSize / 1024);
        if (imageSIzeInKb > 300) {
            throw new InvalidInputException("Invalid input");
        }

        //format validation
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
        String format = "";

        while (imageReaders.hasNext()) {
            ImageReader reader = imageReaders.next();
            format = reader.getFormatName();
        }
        if (!format.equals("JPEG")) {
            System.out.println(format);
            throw new InvalidInputException("Invalid input");
        }

        //reading file

        BufferedImage bImage = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);

        return bos.toByteArray();
    }

}
