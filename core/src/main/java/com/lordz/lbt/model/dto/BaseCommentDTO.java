package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.BaseComment;
import com.lordz.lbt.model.enums.CommentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lordz.lbt.model.dto.base.OutputConverter;

import java.util.Date;

/**
 * Base comment output dto.
 *
 *
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseCommentDTO implements OutputConverter<BaseCommentDTO, BaseComment> {

    private Long id;

    private String author;

    private String email;

    private String ipAddress;

    private String authorUrl;

    private String gravatarMd5;

    private String content;

    private CommentStatus status;

    private String userAgent;

    private Long parentId;

    private Boolean isAdmin;

    private Date createTime;

}
