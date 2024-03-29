package com.lordz.lbt.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.lordz.lbt.model.dto.TagDTO;
import com.lordz.lbt.model.entity.Tag;
import com.lordz.lbt.service.base.CrudService;

import java.util.List;


/**
 * Tag service.
 *
 *
 */
public interface TagService extends CrudService<Tag, Integer> {

    /**
     * Get tag by slug name
     *
     * @param slugName slug name
     * @return Tag
     */
    @NonNull
    Tag getBySlugNameOfNonNull(@NonNull String slugName);

    /**
     * Get tag by tag name.
     *
     * @param name name
     * @return Tag
     */
    @Nullable
    Tag getByName(@NonNull String name);

    /**
     * Converts to tag dto.
     *
     * @param tag tag must not be null
     * @return tag dto
     */
    @NonNull
    TagDTO convertTo(@NonNull Tag tag);

    /**
     * Converts to tag dtos.
     *
     * @param tags tag list
     * @return a list of tag output dto
     */
    @NonNull
    List<TagDTO> convertTo(@Nullable List<Tag> tags);
}
