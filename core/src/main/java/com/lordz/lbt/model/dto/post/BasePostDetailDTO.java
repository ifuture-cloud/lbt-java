package com.lordz.lbt.model.dto.post;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base post detail output dto.
 *
 *
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class BasePostDetailDTO extends BasePostSimpleDTO {

    private String originalContent;

    private String formatContent;
}
