package edu.iuh.fit.backEnd.converters;

import edu.iuh.fit.backEnd.enums.ProductStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProductStatusConverter implements AttributeConverter<ProductStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProductStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return (int) attribute.getValue();
    }

    @Override
    public ProductStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(ProductStatus.values())
                .filter(c -> c.getValue() == dbData.byteValue())
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }



}
