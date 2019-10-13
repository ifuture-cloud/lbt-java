package com.lordz.lbt.model.enums.converter;

import com.lordz.lbt.model.enums.PostCreateFrom;

import javax.persistence.Converter;

/**
 * Post create from converter.
 */
@Converter(autoApply = true)
public class PostCreateFromConverter extends AbstractConverter<PostCreateFrom, Integer> {

    public PostCreateFromConverter() {
        super(PostCreateFrom.class);
    }
}
