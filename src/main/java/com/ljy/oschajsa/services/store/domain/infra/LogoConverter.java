package com.ljy.oschajsa.services.store.domain.infra;

import com.ljy.oschajsa.services.store.domain.Logo;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class LogoConverter implements AttributeConverter<Logo, String> {

    @Override
    public String convertToDatabaseColumn(Logo logo) {
        if(Objects.isNull(logo)){
            return null;
        }
        return logo.getPath();
    }

    @Override
    public Logo convertToEntityAttribute(String s) {
        return Logo.of(s);
    }
}
