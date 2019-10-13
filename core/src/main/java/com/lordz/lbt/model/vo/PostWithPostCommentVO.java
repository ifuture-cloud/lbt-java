package com.lordz.lbt.model.vo;

import com.lordz.lbt.utils.BeanUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.domain.Page;

/**
 * @author ：zzz
 * @date ：Created in 7/25/19 10:33 AM
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class PostWithPostCommentVO extends PostDetailVO {

    private Page<BaseCommentVO> commentPage;

    private int[] commentPageRainbow;

    public PostWithPostCommentVO(PostDetailVO postDetailVO) {
        BeanUtils.updateProperties(postDetailVO,this);
    }
}
