package com.lordz.lbt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import com.lordz.lbt.model.dto.TagWithPostCountDTO;
import com.lordz.lbt.model.entity.Post;
import com.lordz.lbt.model.entity.PostTag;
import com.lordz.lbt.model.entity.Tag;
import com.lordz.lbt.service.base.CrudService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Post tag service interface.
 *
 *
 * @date 3/19/19
 */
public interface PostTagService extends CrudService<PostTag, Integer> {

    /**
     * Lists tags by post id.
     *
     * @param postId post id must not be null
     * @return a list of tag
     */
    @NonNull
    List<Tag> listTagsBy(@NonNull Integer postId);

    /**
     * List tag with post count output dtos.
     *
     * @param sort sort info
     * @return a list of tag with post count output dto
     */
    @NonNull
    List<TagWithPostCountDTO> listTagWithCountDtos(@NonNull Sort sort);

    /**
     * Lists tags list map by post id.
     *
     * @param postIds post id collection
     * @return tag map (key: postId, value: a list of tags)
     */
    @NonNull
    Map<Integer, List<Tag>> listTagListMapBy(@Nullable Collection<Integer> postIds);

    /**
     * Lists posts by tag id.
     *
     * @param tagId tag id must not be null
     * @return a list of post
     */
    @NonNull
    List<Post> listPostsBy(@NonNull Integer tagId);

    /**
     * Pages posts by tag id.
     *
     * @param tagId    must not be null
     * @param pageable must not be null
     * @return a page of post
     */
    Page<Post> pagePostsBy(@NonNull Integer tagId, Pageable pageable);

    /**
     * Merges or creates post tags by post id and tag id set if absent.
     *
     * @param postId post id must not be null
     * @param tagIds tag id set
     * @return a list of post tag
     */
    @NonNull
    List<PostTag> mergeOrCreateByIfAbsent(@NonNull Integer postId, @Nullable Set<Integer> tagIds);

    /**
     * Lists post tags by post id.
     *
     * @param postId post id must not be null
     * @return a list of post tag
     */
    @NonNull
    List<PostTag> listByPostId(@NonNull Integer postId);

    /**
     * Lists post tags by tag id.
     *
     * @param tagId tag id must not be null
     * @return a list of post tag
     */
    @NonNull
    List<PostTag> listByTagId(@NonNull Integer tagId);

    /**
     * Lists tag id set by post id.
     *
     * @param postId post id must not be null
     * @return a set of tag id
     */
    @NonNull
    Set<Integer> listTagIdsByPostId(@NonNull Integer postId);

    /**
     * Removes post tags by post id.
     *
     * @param postId post id must not be null
     * @return a list of post tag
     */
    @NonNull
    @Transactional
    List<PostTag> removeByPostId(@NonNull Integer postId);

    /**
     * Removes post tags by tag id.
     *
     * @param tagId tag id must not be null
     * @return a list of post tag
     */
    @NonNull
    @Transactional
    List<PostTag> removeByTagId(@NonNull Integer tagId);
}
