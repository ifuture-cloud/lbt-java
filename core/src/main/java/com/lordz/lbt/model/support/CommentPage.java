package com.lordz.lbt.model.support;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * PostComment page implementation.
 *
 *
 * @date 3/25/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentPage<T> extends PageImpl<T> {

    /**
     * Total comment (Contains sub comments)
     */
    private final long commentCount;

    public CommentPage(List<T> content, Pageable pageable, long topTotal, long commentCount) {
        super(content, pageable, topTotal);

        this.commentCount = commentCount;
    }

    public CommentPage(List<T> content, long commentCount) {
        super(content);

        this.commentCount = commentCount;
    }
}
