package com.lordz.lbt.model.vo;

import com.lordz.lbt.model.dto.PhotoDTO;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Link team vo.
 *
 * @author ryanwang
 * @date : 2019/3/22
 */
@Data
@ToString
public class PhotoTeamVO {

    private String team;

    private List<PhotoDTO> photos;
}
