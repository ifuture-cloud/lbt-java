package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.Journal;
import lombok.Data;
import com.lordz.lbt.model.dto.base.OutputConverter;

import java.util.Date;

/**
 * Journal dto.
 *
 *
 * @date 19-4-24
 */
@Data
public class JournalDTO implements OutputConverter<JournalDTO, Journal> {

    private Integer id;

    private String content;

    private Long likes;

    private Date createTime;
}
