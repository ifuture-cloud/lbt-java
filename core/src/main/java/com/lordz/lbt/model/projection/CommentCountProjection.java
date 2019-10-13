package com.lordz.lbt.model.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PostComment count projection
 *
 *
 * @date 3/22/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCountProjection {

    private Long count;

    private Integer postId;
}
