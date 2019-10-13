package com.lordz.lbt.controller.api;

import com.lordz.lbt.auth.annotation.Auth;
import com.lordz.lbt.auth.annotation.Role;
import com.lordz.lbt.exception.ForbiddenException;
import com.lordz.lbt.model.dto.CategoryDTO;
import com.lordz.lbt.model.dto.post.BasePostSimpleDTO;
import com.lordz.lbt.model.entity.Post;
import com.lordz.lbt.model.enums.PostStatus;
import com.lordz.lbt.model.params.PostQuery;
import com.lordz.lbt.model.support.CreateCheck;
import com.lordz.lbt.service.CategoryService;
import com.lordz.lbt.service.PostCategoryService;
import com.lordz.lbt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lordz.lbt.model.dto.UserDTO;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.params.PasswordParam;
import com.lordz.lbt.model.params.UserParam;
import com.lordz.lbt.model.support.BaseResponse;
import com.lordz.lbt.model.support.UpdateCheck;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.ValidationUtils;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static com.lordz.lbt.model.support.LBTConst.USER_API_PREFIX;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * User controller.

 */
@RestController
@RequestMapping(USER_API_PREFIX + "/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;



    @Cacheable(cacheNames = "user")
    @GetMapping("{username:\\S+}")
    public UserDTO getProfile(@PathVariable("username") String username) {
        return new UserDTO().convertFrom(userService.getByUsernameOfNonNull(username));
    }

    @Cacheable(cacheNames = "user")
    @GetMapping("/i{userId:\\d+}")
    public UserDTO getProfileById(@PathVariable("userId") Long id) {
        return new UserDTO().convertFrom(userService.getById(id));
    }

    @GetMapping("{username}/posts")
    public Page<BasePostSimpleDTO> pageBy(@PathVariable("username") String username, @PageableDefault(sort = "updateTime", direction = DESC) Pageable pageable) {

        Optional<User> loginUser = Optional.empty();
        try{
            loginUser = userService.getCurrentUser();
        }catch (ForbiddenException e){

        }
        User user = userService.getByUsernameOfNonNull(username);

        PostQuery query = new PostQuery();
        query.setUserId(user.getId());
        query.setStatus(PostStatus.PUBLISHED);
        if (loginUser.isPresent()) {
            if (loginUser.get().getId().compareTo(user.getId()) == 0) {
                query.setStatus(null);
            }
        }
        Page<Post> postPage = postService.pageBy(query, pageable);
        return postService.convertToSimple(postPage);
    }


    @PutMapping("profiles")
    @CacheEvict(cacheNames = "user")
    public UserDTO updateProfile(@RequestBody UserParam userParam, @Auth User user) {
        // Validate the user param
        ValidationUtils.validate(userParam, UpdateCheck.class);

        // Update properties
        userParam.update(user);

        // Update user and convert to dto
        return new UserDTO().convertFrom(userService.update(user));
    }

    @PutMapping("password")
    public BaseResponse updatePassword(@RequestBody @Valid PasswordParam passwordParam, @Auth User user) {
        userService.updatePassword(passwordParam.getOldPassword(), passwordParam.getNewPassword(), user.getId());
        return BaseResponse.ok("密码修改成功");
    }
}
