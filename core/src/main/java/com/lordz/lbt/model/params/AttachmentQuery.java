package com.lordz.lbt.model.params;

import lombok.Data;
import com.lordz.lbt.model.enums.AttachmentType;

/**
 * Attachment query params.
 *
 * @author ryanwang
 * @date : 2019/04/18
 */
@Data
public class AttachmentQuery {

    /**
     * Keyword.
     */
    private String keyword;

    private String mediaType;

    private AttachmentType attachmentType;

    private Long userId;
}
