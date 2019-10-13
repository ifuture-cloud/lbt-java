package com.lordz.lbt.model.enums.converter;

import com.lordz.lbt.model.enums.LogType;

import javax.persistence.Converter;

/**
 * Log type converter.
 *
 *
 * @date 3/27/19
 */
@Converter(autoApply = true)
public class LogTypeConverter extends AbstractConverter<LogType, Integer> {

    public LogTypeConverter() {
        super(LogType.class);
    }
}
