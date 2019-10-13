package com.lordz.lbt.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lordz.lbt.model.dto.UserDTO;
import com.lordz.lbt.utils.ServletUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

/**
 * @author ：zzz
 * @date ：Created in 7/16/19 8:49 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authorized extends UserDTO {

    /**
     * Access token.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Expired in. (seconds)
     */
    @JsonProperty("expired_in")
    private int expiredIn;

}
