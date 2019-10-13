package com.lordz.lbt.model.enums.converter;

import com.lordz.lbt.model.enums.AttachmentType;

import javax.persistence.Converter;

/**
 * Attachment type converter
 *
 *
 * @date 3/27/19
 */
@Converter(autoApply = true)
public class AttachmentTypeConverter extends AbstractConverter<AttachmentType, Integer> {

    public AttachmentTypeConverter() {
        super(AttachmentType.class);
    }
}
