package com.lordz.lbt.model.enums.converter;

import com.lordz.lbt.model.enums.PostStatus;

import javax.persistence.Converter;

/**
 * PostStatus converter.
 *
 *
 * @date 3/27/19
 */
@Converter(autoApply = true)
public class PostStatusConverter extends AbstractConverter<PostStatus, Integer> {

    public PostStatusConverter() {
        super(PostStatus.class);
    }
}
