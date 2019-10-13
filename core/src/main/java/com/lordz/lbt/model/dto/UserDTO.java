package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lordz.lbt.model.dto.base.OutputConverter;

import java.io.Serializable;
import java.util.Date;

/**
 * User output dto.
 *
 *
 * @date 3/16/19
 */
@Data
@ToString
@EqualsAndHashCode
public class UserDTO implements OutputConverter<UserDTO, User>, Serializable {

    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String avatar;

    private String description;

    private Date createTime;

    private Date updateTime;

    private String parallax;
}
