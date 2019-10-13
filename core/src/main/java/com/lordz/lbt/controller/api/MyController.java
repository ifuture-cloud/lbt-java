package com.lordz.lbt.controller.api;

import com.lordz.lbt.model.entity.Post;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.params.PostQuery;
import com.lordz.lbt.model.vo.PostListVO;
import com.lordz.lbt.service.PostService;
import com.lordz.lbt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.lordz.lbt.model.support.LBTConst.USER_API_PREFIX;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author ：zzz
 */
@RestController
@RequestMapping(USER_API_PREFIX + "/my")
public class MyController {


    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @GetMapping("/posts")
    public Page<PostListVO> pageBy(@PageableDefault(sort = "updateTime", direction = DESC) Pageable pageable,
                                   PostQuery postQuery) {
        Optional<User> user = userService.getCurrentUser();
        if (user.isPresent()) {
            postQuery.setUserId(user.get().getId());
            Page<Post> postPage = postService.pageBy(postQuery, pageable);
            return postService.convertToListVo(postPage);
        }
        return null;
    }
}
