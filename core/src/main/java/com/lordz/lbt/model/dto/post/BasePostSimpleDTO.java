package com.lordz.lbt.model.dto.post;

import com.lordz.lbt.model.enums.PostCreateFrom;
import com.lordz.lbt.model.enums.PostType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base page simple output dto.
 *
 *
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class BasePostSimpleDTO extends BasePostMinimalDTO {

    private PostType type;

    private String summary;

    private String thumbnail;

    private Long visits = 0L;

    private Boolean disallowComment;

    private String template;

    private Integer topPriority = 0;

    private PostCreateFrom createFrom;

    private Long likes = 0L;
}
