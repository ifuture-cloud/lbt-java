package com.lordz.lbt.model.dto;

import com.lordz.lbt.model.entity.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.lordz.lbt.model.dto.base.OutputConverter;

/**
 * Option output dto.
 *
 *
 * @date 3/20/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO implements OutputConverter<OptionDTO, Option> {

    private String key;

    private Object value;

}
