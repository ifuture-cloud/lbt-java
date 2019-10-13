package com.lordz.lbt.repository;

import com.lordz.lbt.repository.base.BasePostRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import com.lordz.lbt.model.entity.Sheet;
import com.lordz.lbt.model.enums.PostStatus;

import java.util.Optional;

/**
 * Sheet repository.
 *
 *
 * @date 3/22/19
 */
public interface SheetRepository extends BasePostRepository<Sheet> {

    @Override
    @Query("select sum(p.visits) from Sheet p")
    Long countVisit();

    @Override
    @Query("select sum(p.likes) from Sheet p")
    Long countLike();

    @NonNull
    Optional<Sheet> getByUrlAndStatus(@NonNull String url, @NonNull PostStatus status);
}
