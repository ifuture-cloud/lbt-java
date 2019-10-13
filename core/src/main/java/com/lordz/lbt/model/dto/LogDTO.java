package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.Log;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lordz.lbt.model.dto.base.OutputConverter;
import com.lordz.lbt.model.enums.LogType;

import java.util.Date;

/**
 *
 * @date 3/19/19
 */
@Data
@ToString
@EqualsAndHashCode
public class LogDTO implements OutputConverter<LogDTO, Log> {

    private Long id;

    private String logKey;

    private LogType type;

    private String content;

    private String ipAddress;

    private Date createTime;
}
