package com.lordz.lbt.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.lordz.lbt.model.support.ThemeFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author ryanwang
 * @date : 2019/3/26
 */
public interface ThemeService {
/*

    */
/**
     * Theme property file name.
     *//*

    String THEME_PROPERTY_FILE_NAME = "theme.yaml";

    */
/**
     * Theme property file name.
     *//*

    String[] THEME_PROPERTY_FILE_NAMES = {"theme.yaml", "theme.yml"};


    */
/**
     * Configuration file name.
     *//*

    String[] SETTINGS_NAMES = {"settings.yaml", "settings.yml"};

    */
/**
     * The type of file that can be modified.
     *//*

    String[] CAN_EDIT_SUFFIX = {".ftl", ".css", ".js", ".yaml", ".yml", ".properties"};

    */
/**
     * These file names cannot be displayed.
     *//*

    String[] FILTER_FILES = {".git", ".DS_Store", "theme.yaml", "theme.yml", "settings.yaml", "settings.yml"};

    */
/**
     * Theme folder location.
     *//*

    String THEME_FOLDER = "templates/themes";

    */
/**
     * Theme screenshots name.
     *//*

    String THEME_SCREENSHOTS_NAME = "screenshot";


    */
/**
     * Render template.
     *//*

    String RENDER_TEMPLATE = "themes/%s/%s";

    */
/**
     * Theme cache key.
     *//*

    String THEMES_CACHE_KEY = "themes";

    */
/**
     * Custom sheet prefix.
     *//*

    String CUSTOM_SHEET_PREFIX = "sheet_";

    */
/**
     * Theme provider remote name.
     *//*

    String THEME_PROVIDER_REMOTE_NAME = "theme-provider";

    */
/**
     * Default remote branch name.
     *//*

    String DEFAULT_REMOTE_BRANCH = "master";

    */
/**
     * Get theme property by theme id.
     *
     * @param themeId must not be blank
     * @return theme property
     *//*

    @NonNull
    ThemeProperty getThemeOfNonNullBy(@NonNull String themeId);

    */
/**
     * Get theme property by theme id.
     *
     * @param themeId theme id
     * @return a optional theme property
     *//*

    @NonNull
    Optional<ThemeProperty> getThemeBy(@Nullable String themeId);

    */
/**
     * Gets all themes
     *
     * @return set of themes
     *//*

    @NonNull
    Set<ThemeProperty> getThemes();

    */
/**
     * Lists theme folder by absolute path.
     *
     * @param absolutePath absolutePath
     * @return List<ThemeFile>
     *//*

    List<ThemeFile> listThemeFolder(@NonNull String absolutePath);

    */
/**
     * Lists theme folder by theme name.
     *
     * @param themeId theme id
     * @return List<ThemeFile>
     *//*

    List<ThemeFile> listThemeFolderBy(@NonNull String themeId);

    */
/**
     * Lists a set of custom template, such as sheet_xxx.ftl, and xxx will be template name
     *
     * @param themeId theme id must not be blank
     * @return a set of templates
     *//*

    Set<String> listCustomTemplates(@NonNull String themeId);

    */
/**
     * Judging whether template exists under the specified theme
     *
     * @param template template must not be blank
     * @return boolean
     *//*

    boolean templateExists(@Nullable String template);

    */
/**
     * Checks whether theme exists under template path
     *
     * @param themeId theme id
     * @return boolean
     *//*

    boolean themeExists(@Nullable String themeId);

    */
/**
     * Gets theme base path.
     *
     * @return theme base path
     *//*

    Path getBasePath();

    */
/**
     * Gets template content by template absolute path.
     *
     * @param absolutePath absolute path
     * @return template content
     *//*

    String getTemplateContent(@NonNull String absolutePath);

    */
/**
     * Saves template content by template absolute path.
     *
     * @param absolutePath absolute path
     * @param content      new content
     *//*

    void saveTemplateContent(@NonNull String absolutePath, @NonNull String content);

    */
/**
     * Deletes a theme by key.
     *
     * @param themeId theme id must not be blank
     *//*

    void deleteTheme(@NonNull String themeId);

    */
/**
     * Fetches theme configuration.
     *
     * @param themeId must not be blank
     * @return theme configuration
     *//*

    @NonNull
    List<Group> fetchConfig(@NonNull String themeId);

    */
/**
     * Renders a theme page.
     *
     * @param pageName must not be blank
     * @return full path of the theme page
     *//*

    @NonNull
    String render(@NonNull String pageName);

    */
/**
     * Gets current theme id.
     *
     * @return current theme id
     *//*

    @NonNull
    String getActivatedThemeId();

    */
/**
     * Gets activated theme property.
     *
     * @return activated theme property
     *//*

    @NonNull
    ThemeProperty getActivatedTheme();

    */
/**
     * Actives a theme.
     *
     * @param themeId theme id must not be blank
     * @return theme property
     *//*

    @NonNull
    ThemeProperty activateTheme(@NonNull String themeId);

    */
/**
     * Upload theme.
     *
     * @param file multipart file must not be null
     * @return theme info
     *//*

    @NonNull
    ThemeProperty upload(@NonNull MultipartFile file);

    */
/**
     * Adds a new theme.
     *
     * @param themeTmpPath theme temporary path must not be null
     * @return theme property
     *//*

    @NonNull
    ThemeProperty add(@NonNull Path themeTmpPath) throws IOException;

    */
/**
     * Fetches a new theme.
     *
     * @param uri theme remote uri must not be null
     * @return theme property
     *//*

    @NonNull
    ThemeProperty fetch(@NonNull String uri);

    */
/**
     * Reloads themes
     *//*

    void reload();

    */
/**
     * Updates theme by theme id.
     *
     * @param themeId theme id must not be blank
     * @return theme property
     *//*

    @NonNull
    ThemeProperty update(@NonNull String themeId);
*/
}
