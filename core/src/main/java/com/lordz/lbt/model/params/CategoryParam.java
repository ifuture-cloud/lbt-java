package com.lordz.lbt.model.params;

import com.lordz.lbt.utils.LBTUtils;
import com.lordz.lbt.utils.StringUtils;
import lombok.Data;
import com.lordz.lbt.model.dto.base.InputConverter;
import com.lordz.lbt.model.entity.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Category param.
 *
 *
 * @date 3/21/19
 */
@Data
public class CategoryParam implements InputConverter<Category> {

    /**
     * Category name.
     */
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50, message = "分类名称的字符长度不能超过 {max}")
    private String name;

    /**
     * Category slug name.
     */
    @Size(max = 50, message = "分类别名的字符长度不能超过 {max}")
    private String slugName;

    /**
     * Category description.
     */
    @Size(max = 100, message = "分类描述的字符长度不能超过 {max}")
    private String description;

    /**
     * Parent category.
     */
    private Integer parentId = 0;

    @Override
    public Category convertTo() {
        // Handle default value
        if (org.apache.commons.lang3.StringUtils.isBlank(slugName)) {
            slugName = StringUtils.slugify(name);

            if (org.apache.commons.lang3.StringUtils.isBlank(slugName)) {
                slugName = LBTUtils.initializeUrlIfBlank(slugName);
            }
        }

        return InputConverter.super.convertTo();
    }
}
