package ru.skypro.homework.staticClasses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.skypro.homework.dto.CreateOrUpdateAd;

public class CreateOrUpdateAdParser {
    public static CreateOrUpdateAd parse(String stringOriginal) throws JsonProcessingException {
        return (new ObjectMapper()).readValue(stringOriginal, CreateOrUpdateAd.class);
    }
}
