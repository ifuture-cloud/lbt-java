package com.lordz.lbt.model.vo;

import com.lordz.lbt.model.dto.CategoryDTO;
import com.lordz.lbt.model.dto.TagDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lordz.lbt.model.dto.post.BasePostSimpleDTO;

import java.util.List;

/**
 * Post list vo.
 *
 *
 * @date 3/19/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostListVO extends BasePostSimpleDTO {

    private Long commentCount;

    private List<TagDTO> tags;

    private List<CategoryDTO> categories;

}
