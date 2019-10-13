package com.lordz.lbt.controller.api;

import cn.hutool.core.util.PageUtil;
import com.lordz.lbt.auth.annotation.Auth;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.support.CreateCheck;
import com.lordz.lbt.model.vo.BaseCommentVO;
import com.lordz.lbt.model.vo.PostWithPostCommentVO;
import com.lordz.lbt.service.PostCommentService;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.lordz.lbt.model.dto.post.BasePostMinimalDTO;
import com.lordz.lbt.model.dto.post.BasePostSimpleDTO;
import com.lordz.lbt.model.entity.Post;
import com.lordz.lbt.model.enums.PostStatus;
import com.lordz.lbt.model.params.PostParam;
import com.lordz.lbt.model.params.PostQuery;
import com.lordz.lbt.model.vo.PostDetailVO;
import com.lordz.lbt.model.vo.PostListVO;
import com.lordz.lbt.service.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.lordz.lbt.model.support.LBTConst.USER_API_PREFIX;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Post controller.
 *
 * @author zzz
 */
@RestController
@RequestMapping(USER_API_PREFIX + "/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostCommentService postCommentService;


    @GetMapping("{url}")
    public PostWithPostCommentVO post(@PathVariable("url") String url,
                       @RequestParam(value = "cp", defaultValue = "1") Integer cp,
                       @SortDefault(sort = "createTime", direction = DESC) Sort sort) {
        Post post = postService.getBy(PostStatus.PUBLISHED, url);

        PostDetailVO postDetailVO = postService.convertToDetailVo(post);


        Page<BaseCommentVO> comments = postCommentService.pageVosBy(post.getId(), PageRequest.of(cp, 5, sort));
        final int[] pageRainbow = PageUtil.rainbow(cp, comments.getTotalPages(), 3);

        PostWithPostCommentVO vo = new PostWithPostCommentVO(postDetailVO);
        vo.setCommentPage(comments);
        vo.setCommentPageRainbow(pageRainbow);
        return vo;
    }



    @GetMapping("/i/{postId:\\d+}")
    public PostDetailVO getBy(@PathVariable("postId") Integer postId) {
        Post post = postService.getById(postId);
        return postService.convertToDetailVo(post);
    }

    @PutMapping("{postId:\\d+}/likes")
    public void likes(@PathVariable("postId") Integer postId) {
        postService.increaseLike(postId);
    }

    @PostMapping
    public PostDetailVO createBy(@Valid @RequestBody PostParam postParam,
                                 @RequestParam(value = "autoSave", required = false, defaultValue = "false") Boolean autoSave) {
        ValidationUtils.validate(postParam, CreateCheck.class);

        Post post = postParam.convertTo();

        return postService.createBy(post, postParam.getTagIds(), postParam.getCategoryIds(), autoSave);
    }

    @PutMapping("{postId:\\d+}")
    public PostDetailVO updateBy(@Valid @RequestBody PostParam postParam,
                                 @PathVariable("postId") Integer postId,
                                 @RequestParam(value = "autoSave", required = false, defaultValue = "false") Boolean autoSave) {
        // Get the post info
        Post postToUpdate = postService.getById(postId);

        postParam.update(postToUpdate);

        return postService.updateBy(postToUpdate, postParam.getTagIds(), postParam.getCategoryIds(), autoSave);
    }

    @PutMapping("{postId:\\d+}/status/{status}")
    public void updateStatusBy(
            @PathVariable("postId") Integer postId,
            @PathVariable("status") PostStatus status,
            @Auth User user) {
        Post post = postService.getById(postId);

        if (user.getId().compareTo(post.getCreateUser()) == 0) {
            post.setStatus(status);

            postService.update(post);
        }

    }

    @DeleteMapping("{postId:\\d+}")
    public void deletePermanently(@PathVariable("postId") Integer postId ,@Auth User user) {
        Post post = postService.getById(postId);
        if (post != null && post.getCreateUser().compareTo(user.getId()) == 0) {
            postService.removeById(postId);
        }
    }

}
