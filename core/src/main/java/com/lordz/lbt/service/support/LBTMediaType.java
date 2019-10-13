package com.lordz.lbt.service.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Halo Media type.
 *
 */
@Slf4j
public class LBTMediaType extends MediaType {

    /**
     * Public constant media type of {@code application/zip}
     */
    public static final MediaType APPLICATION_ZIP;

    /**
     * A String equivalent of {@link LBTMediaType#APPLICATION_ZIP}
     */
    public static final String APPLICATION_ZIP_VALUE = "application/zip";

    public static final MediaType APPLICATION_GIT;

    public static final String APPLICATION_GIT_VALUE = "application/git";


    static {
        APPLICATION_ZIP = valueOf(APPLICATION_ZIP_VALUE);
        APPLICATION_GIT = valueOf(APPLICATION_GIT_VALUE);
    }

    public LBTMediaType(String type) {
        super(type);
    }

    public LBTMediaType(String type, String subtype) {
        super(type, subtype);
    }

    public LBTMediaType(String type, String subtype, Charset charset) {
        super(type, subtype, charset);
    }

    public LBTMediaType(String type, String subtype, double qualityValue) {
        super(type, subtype, qualityValue);
    }

    public LBTMediaType(MediaType other, Charset charset) {
        super(other, charset);
    }

    public LBTMediaType(MediaType other, Map<String, String> parameters) {
        super(other, parameters);
    }

    public LBTMediaType(String type, String subtype, Map<String, String> parameters) {
        super(type, subtype, parameters);
    }

}
