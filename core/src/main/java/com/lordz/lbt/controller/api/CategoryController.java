package com.lordz.lbt.controller.api;

import com.lordz.lbt.auth.annotation.Auth;
import com.lordz.lbt.model.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import com.lordz.lbt.model.dto.CategoryDTO;
import com.lordz.lbt.model.entity.Category;
import com.lordz.lbt.model.params.CategoryParam;
import com.lordz.lbt.model.vo.CategoryVO;
import com.lordz.lbt.service.CategoryService;
import com.lordz.lbt.service.PostCategoryService;

import javax.validation.Valid;
import java.util.List;

import static com.lordz.lbt.model.support.LBTConst.USER_API_PREFIX;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Category controller.
 */
@RestController
@RequestMapping(USER_API_PREFIX + "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final PostCategoryService postCategoryService;

    public CategoryController(CategoryService categoryService,
                              PostCategoryService postCategoryService) {
        this.categoryService = categoryService;
        this.postCategoryService = postCategoryService;
    }

    @GetMapping("{categoryId:\\d+}")
    public CategoryDTO getBy(@PathVariable("categoryId") Integer categoryId) {
        return categoryService.convertTo(categoryService.getById(categoryId));
    }

    @GetMapping
    public List<? extends CategoryDTO> listAll(
            @Auth User user,
            @SortDefault(sort = "updateTime", direction = DESC) Sort sort,
            @RequestParam(name = "more", required = false, defaultValue = "false") boolean more) {
/*        if (more) {
            return postCategoryService.listCategoryWithPostCountDto(sort);
        }
        return categoryService.convertTo(categoryService.listAll(sort));*/
        return categoryService.convertTo(categoryService.findAllByCreateUser(user.getId(),sort));
    }

    @GetMapping("tree_view")
    public List<CategoryVO> listAsTree(@SortDefault(sort = "name", direction = ASC) Sort sort) {
        return categoryService.listAsTree(sort);
    }

    @PostMapping
    public CategoryDTO createBy(@RequestBody @Valid CategoryParam categoryParam) {
        // Convert to category
        Category category = categoryParam.convertTo();

        // Save it
        return categoryService.convertTo(categoryService.create(category));
    }

    @PutMapping("{categoryId:\\d+}")
    public CategoryDTO updateBy(@PathVariable("categoryId") Integer categoryId,
                                @RequestBody @Valid CategoryParam categoryParam) {
        Category categoryToUpdate = categoryService.getById(categoryId);
        categoryParam.update(categoryToUpdate);
        return categoryService.convertTo(categoryService.update(categoryToUpdate));
    }

    @DeleteMapping("{categoryId:\\d+}")
    public void deletePermanently(@PathVariable("categoryId") Integer categoryId) {
        categoryService.removeCategoryAndPostCategoryBy(categoryId);
    }
}
