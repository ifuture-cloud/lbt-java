package com.lordz.lbt.model.enums.converter;

import com.lordz.lbt.model.enums.CommentStatus;

import javax.persistence.Converter;

/**
 * PostComment status converter.
 *
 *
 * @date 3/27/19
 */
@Converter(autoApply = true)
public class CommentStatusConverter extends AbstractConverter<CommentStatus, Integer> {

    public CommentStatusConverter() {
        super(CommentStatus.class);
    }

}
