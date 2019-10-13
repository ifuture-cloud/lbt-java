package com.lordz.lbt.model.enums.converter;

import com.lordz.lbt.model.enums.DataType;

import javax.persistence.Converter;

/**
 * Data type converter.
 *
 *
 * @date 4/10/19
 */
@Converter(autoApply = true)
public class DataTypeConverter extends AbstractConverter<DataType, Integer> {

    public DataTypeConverter() {
        super(DataType.class);
    }
}
