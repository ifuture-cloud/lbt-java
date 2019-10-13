package com.lordz.lbt.config;

import com.lordz.lbt.model.support.LBTConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * configuration properties.
 *
 */
@Data
@ConfigurationProperties("lbt")
public class LBTProperties {

    /**
     * Doc api disabled. (Default is true)
     */
    private boolean docDisabled = true;

    /**
     * Production env. (Default is true)
     */
    private boolean productionEnv = true;

    /**
     * Authentication enabled
     */
    private boolean authEnabled = true;

    /**
     * Work directory.
     */
    private String workDir = LBTConst.USER_HOME + "/.lbt/";

    public LBTProperties() throws IOException {
        // Create work directory if not exist
        Files.createDirectories(Paths.get(workDir));
    }
}
