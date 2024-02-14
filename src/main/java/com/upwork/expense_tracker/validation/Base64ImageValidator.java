package com.upwork.expense_tracker.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * The validator checks whether the string is a base64 image.
 * */
public class Base64ImageValidator implements ConstraintValidator<Base64Image, String> {

    @Override
    public void initialize(Base64Image constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if (value == null || value.isEmpty())
                return false;
            byte[] imageBytes = Base64.decodeBase64(value);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bis);
            return image != null;
        } catch (IOException e) {
            return false;
        }
    }

}
