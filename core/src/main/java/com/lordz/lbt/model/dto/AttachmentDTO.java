package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.Attachment;
import com.lordz.lbt.model.enums.AttachmentType;
import lombok.Data;
import com.lordz.lbt.model.dto.base.OutputConverter;

import java.util.Date;

/**
 * Attachment output dto.
 *
 *
 * @date 3/21/19
 */
@Data
public class AttachmentDTO implements OutputConverter<AttachmentDTO, Attachment> {

    private Integer id;

    private String name;

    private String path;

    private String fileKey;

    private String thumbPath;

    private String mediaType;

    private String suffix;

    private Integer width;

    private Integer height;

    private Long size;

    private AttachmentType type;

    private Date createTime;
}
