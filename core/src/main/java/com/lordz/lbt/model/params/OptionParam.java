package com.lordz.lbt.model.params;

import lombok.Data;
import com.lordz.lbt.model.dto.base.InputConverter;
import com.lordz.lbt.model.entity.Option;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Optiona param.
 *
 *
 * @date 3/20/19
 */
@Data
public class OptionParam implements InputConverter<Option> {

    @NotBlank(message = "Option key must not be blank")
    @Size(max = 100, message = "Length of option key must not be more than {max}")
    private String key;


    @Size(max = 1023, message = "Length of option value must not be more than {max}")
    private String value;
}
