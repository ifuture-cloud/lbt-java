package com.lordz.lbt.model.params;

import com.lordz.lbt.utils.LBTUtils;
import com.lordz.lbt.utils.StringUtils;
import lombok.Data;
import com.lordz.lbt.model.dto.base.InputConverter;
import com.lordz.lbt.model.entity.Tag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Tag param.
 *
 *
 * @date 3/20/19
 */
@Data
public class TagParam implements InputConverter<Tag> {

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 255, message = "标签名称的字符长度不能超过 {max}")
    private String name;

    @Size(max = 255, message = "标签别名的字符长度不能超过 {max}")
    private String slugName;

    @Override
    public Tag convertTo() {
        if (org.apache.commons.lang3.StringUtils.isBlank(slugName)) {
            // Handle slug name
            slugName = StringUtils.slugify(name);
        }

        slugName = LBTUtils.initializeUrlIfBlank(slugName);

        return InputConverter.super.convertTo();
    }
}
