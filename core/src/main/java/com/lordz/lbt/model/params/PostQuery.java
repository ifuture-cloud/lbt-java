package com.lordz.lbt.model.params;

import lombok.Data;
import com.lordz.lbt.model.enums.PostStatus;

/**
 * Post query.
 *
 */
@Data
public class PostQuery {

    /**
     * Keyword.
     */
    private String keyword;

    /**
     * Post status.
     */
    private PostStatus status;

    /**
     * Category id.
     */
    private Integer categoryId;

    private Long userId;

}
