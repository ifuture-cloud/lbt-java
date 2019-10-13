package com.lordz.lbt.model.vo;

import com.lordz.lbt.model.dto.post.BasePostMinimalDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

/**
 * Year archive vo.
 *
 *
 * @date 4/2/19
 */
@Data
@ToString
@EqualsAndHashCode
public class ArchiveYearVO {

    private Integer year;

    private List<BasePostMinimalDTO> posts;

    public static class ArchiveComparator implements Comparator<ArchiveYearVO> {

        @Override
        public int compare(ArchiveYearVO left, ArchiveYearVO right) {
            return right.getYear() - left.getYear();
        }
    }
}
