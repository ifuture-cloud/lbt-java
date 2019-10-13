package com.lordz.lbt.model.dto.post;

import com.lordz.lbt.model.entity.BasePost;
import com.lordz.lbt.model.enums.PostStatus;
import com.lordz.lbt.model.enums.PostType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lordz.lbt.model.dto.base.OutputConverter;

import java.util.Date;

/**
 * Base post minimal output dto.
 *
 *
 */
@Data
@ToString
@EqualsAndHashCode
public class BasePostMinimalDTO implements OutputConverter<BasePostMinimalDTO, BasePost> {

    private Integer id;

    private String title;

    private PostStatus status;

    private String url;

    private PostType type;

    private Date updateTime;

    private Date createTime;

    private Date editTime;

    private Long createUser;
}
