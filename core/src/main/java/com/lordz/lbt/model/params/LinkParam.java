package com.lordz.lbt.model.params;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import com.lordz.lbt.model.dto.base.InputConverter;
import com.lordz.lbt.model.entity.Link;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Link param.
 *
 *
 * @date 4/3/19
 */
@Data
public class LinkParam implements InputConverter<Link> {

    @NotBlank(message = "友情链接名称不能为空")
    @Size(max = 255, message = "友情链接名称的字符长度不能超过 {max}")
    private String name;

    @NotBlank(message = "友情链接地址不能为空")
    @Size(max = 1023, message = "友情链接地址的字符长度不能超过 {max}")
    @URL(message = "友情链接地址格式不正确")
    private String url;

    @Size(max = 1023, message = "友情链接 Logo 的字符长度不能超过 {max}")
    private String logo;

    @Size(max = 255, message = "友情链接描述的字符长度不能超过 {max}")
    private String description;

    @Size(max = 255, message = "友情链接分组的字符长度 {max}")
    private String team;

}
