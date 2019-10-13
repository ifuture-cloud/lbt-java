package com.lordz.lbt.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.lordz.lbt.model.dto.post.BasePostSimpleDTO;

/**
 * Sheet list dto.
 *
 *
 * @date 19-4-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SheetListVO extends BasePostSimpleDTO {

    private Long commentCount;
}
