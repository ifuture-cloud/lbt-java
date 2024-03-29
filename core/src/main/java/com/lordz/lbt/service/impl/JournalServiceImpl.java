package com.lordz.lbt.service.impl;

import com.lordz.lbt.service.JournalCommentService;
import com.lordz.lbt.service.JournalService;
import com.lordz.lbt.utils.ServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import com.lordz.lbt.model.dto.JournalDTO;
import com.lordz.lbt.model.dto.JournalWithCmtCountDTO;
import com.lordz.lbt.model.entity.Journal;
import com.lordz.lbt.model.params.JournalParam;
import com.lordz.lbt.model.params.JournalQuery;
import com.lordz.lbt.repository.JournalRepository;
import com.lordz.lbt.service.base.AbstractCrudService;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Journal service implementation.
 *
 *
 * @author ryanwang
 * @date 19-4-24
 */
@Service
public class JournalServiceImpl extends AbstractCrudService<Journal, Integer> implements JournalService {

    private final JournalRepository journalRepository;

    private final JournalCommentService journalCommentService;

    public JournalServiceImpl(JournalRepository journalRepository,
                              JournalCommentService journalCommentService) {
        super(journalRepository);
        this.journalRepository = journalRepository;
        this.journalCommentService = journalCommentService;
    }

    @Override
    public Journal createBy(JournalParam journalParam) {
        Assert.notNull(journalParam, "Journal param must not be null");

        return create(journalParam.convertTo());
    }

    @Override
    public Page<Journal> pageLatest(int top) {
        return listAll(ServiceUtils.buildLatestPageable(top));
    }

    @Override
    public Page<Journal> pageBy(JournalQuery journalQuery, Pageable pageable) {
        Assert.notNull(journalQuery, "Journal query must not be null");
        Assert.notNull(pageable, "Page info must not be null");
        return journalRepository.findAll(buildSpecByQuery(journalQuery), pageable);
    }

    @Override
    public JournalDTO convertTo(Journal journal) {
        Assert.notNull(journal, "Journal must not be null");

        return new JournalDTO().convertFrom(journal);
    }

    @Override
    public List<JournalWithCmtCountDTO> convertToCmtCountDto(List<Journal> journals) {
        if (CollectionUtils.isEmpty(journals)) {
            return Collections.emptyList();
        }

        // Get journal ids
        Set<Integer> journalIds = ServiceUtils.fetchProperty(journals, Journal::getId);

        // Get comment count map
        Map<Integer, Long> journalCommentCountMap = journalCommentService.countByPostIds(journalIds);

        return journals.stream()
                .map(journal -> {
                    JournalWithCmtCountDTO journalWithCmtCountDTO = new JournalWithCmtCountDTO().convertFrom(journal);
                    // Set comment count
                    journalWithCmtCountDTO.setCommentCount(journalCommentCountMap.getOrDefault(journal.getId(), 0L));
                    return journalWithCmtCountDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<JournalWithCmtCountDTO> convertToCmtCountDto(Page<Journal> journalPage) {
        Assert.notNull(journalPage, "Journal page must not be null");

        // Convert
        List<JournalWithCmtCountDTO> journalWithCmtCountDTOS = convertToCmtCountDto(journalPage.getContent());

        // Build and return
        return new PageImpl<>(journalWithCmtCountDTOS, journalPage.getPageable(), journalPage.getTotalElements());
    }


    /**
     * Build specification by journal query.
     *
     * @param journalQuery query query must not be null
     * @return a query specification
     */
    @NonNull
    private Specification<Journal> buildSpecByQuery(@NonNull JournalQuery journalQuery) {
        Assert.notNull(journalQuery, "Journal query must not be null");

        return (Specification<Journal>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();

            if (journalQuery.getKeyword() != null) {
                // Format like condition
                String likeCondition = String.format("%%%s%%", StringUtils.strip(journalQuery.getKeyword()));

                // Build like predicate
                Predicate contentLike = criteriaBuilder.like(root.get("content"), likeCondition);

                predicates.add(criteriaBuilder.or(contentLike));
            }

            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}
