package com.lordz.lbt.model.enums.converter;

import com.lordz.lbt.model.enums.PostType;

import javax.persistence.Converter;

/**
 * PostType converter.
 *
 *
 * @date 3/27/19
 */
@Converter(autoApply = true)
public class PostTypeConverter extends AbstractConverter<PostType, Integer> {

    public PostTypeConverter() {
        super(PostType.class);
    }
}
