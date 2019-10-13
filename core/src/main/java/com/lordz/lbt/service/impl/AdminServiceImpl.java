package com.lordz.lbt.service.impl;

import cn.hutool.core.lang.Validator;
import com.lordz.lbt.auth.AuthInfo;
import com.lordz.lbt.auth.Authorized;
import com.lordz.lbt.auth.secret.ISecretService;
import com.lordz.lbt.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.lordz.lbt.exception.BadRequestException;
import com.lordz.lbt.exception.NotFoundException;
import com.lordz.lbt.exception.ServiceException;
import com.lordz.lbt.model.dto.EnvironmentDTO;
import com.lordz.lbt.model.dto.StatisticDTO;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.enums.CommentStatus;
import com.lordz.lbt.model.enums.Mode;
import com.lordz.lbt.model.enums.PostStatus;
import com.lordz.lbt.model.params.LoginParam;
import com.lordz.lbt.model.support.LBTConst;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Admin service implementation.
 *
 *
 * @author ryanwang
 * @date 19-4-29
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final PostService postService;

    private final SheetService sheetService;

    private final AttachmentService attachmentService;

    private final PostCommentService postCommentService;

    private final SheetCommentService sheetCommentService;

    private final JournalCommentService journalCommentService;

    private final OptionService optionService;

    private final UserService userService;

    private final LinkService linkService;

    private final ISecretService secretService;

    private final String driverClassName;

    private final String mode;

    public AdminServiceImpl(PostService postService,
                            SheetService sheetService,
                            AttachmentService attachmentService,
                            PostCommentService postCommentService,
                            SheetCommentService sheetCommentService,
                            JournalCommentService journalCommentService,
                            OptionService optionService,
                            UserService userService,
                            LinkService linkService,
                            ISecretService secretService,
                            @Value("${spring.datasource.driver-class-name}") String driverClassName,
                            @Value("${spring.profiles.active:prod}") String mode) {
        this.postService = postService;
        this.sheetService = sheetService;
        this.attachmentService = attachmentService;
        this.postCommentService = postCommentService;
        this.sheetCommentService = sheetCommentService;
        this.journalCommentService = journalCommentService;
        this.optionService = optionService;
        this.userService = userService;
        this.linkService = linkService;
        this.secretService = secretService;
        this.driverClassName = driverClassName;
        this.mode = mode;
    }

    @Override
    public Authorized authenticate(LoginParam loginParam) {
        Assert.notNull(loginParam, "Login param must not be null");

        String username = loginParam.getUsername();

        String mismatchTip = "用户名或者密码不正确";

        final User user;

        try {
            // Get user by username or email
            user = Validator.isEmail(username) ?
                    userService.getByEmailOfNonNull(username) : userService.getByUsernameOfNonNull(username);
        } catch (NotFoundException e) {
            log.error("Failed to find user by name: " + username, e);
            throw new BadRequestException(mismatchTip);
        }

        userService.mustNotExpire(user);

        if (!userService.passwordMatch(user, loginParam.getPassword())) {
            // If the password is mismatch
            throw new BadRequestException(mismatchTip);
        }

        // Generate new token
        try {
            return buildAuthToken(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("TOKEN ERROR");
        }
    }


    @Override
    public StatisticDTO getCount() {
        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setPostCount(postService.countByStatus(PostStatus.PUBLISHED));
        statisticDTO.setAttachmentCount(attachmentService.count());

        // Handle comment count
        long postCommentCount = postCommentService.countByStatus(CommentStatus.PUBLISHED);
        long sheetCommentCount = sheetCommentService.countByStatus(CommentStatus.PUBLISHED);
        long journalCommentCount = journalCommentService.countByStatus(CommentStatus.PUBLISHED);

        statisticDTO.setCommentCount(postCommentCount + sheetCommentCount + journalCommentCount);

        long birthday = optionService.getBirthday();
        long days = (System.currentTimeMillis() - birthday) / (1000 * 24 * 3600);
        statisticDTO.setEstablishDays(days);
        statisticDTO.setBirthday(birthday);

        statisticDTO.setLinkCount(linkService.count());

        statisticDTO.setVisitCount(postService.countVisit() + sheetService.countVisit());
        statisticDTO.setLikeCount(postService.countLike() + sheetService.countLike());
        return statisticDTO;
    }

    @Override
    public EnvironmentDTO getEnvironments() {
        EnvironmentDTO environmentDTO = new EnvironmentDTO();

        // Get application start time.
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        environmentDTO.setStartTime(runtimeMXBean.getStartTime());

        environmentDTO.setDatabase("org.h2.Driver".equals(driverClassName) ? "H2" : "MySQL");

        environmentDTO.setVersion(LBTConst.LBT_VERSION);

        environmentDTO.setMode(Mode.valueFrom(this.mode));

        return environmentDTO;
    }

    @Override
    public Authorized refreshToken(String refreshToken) {
        Assert.hasText(refreshToken, "Refresh token must not be blank");

        try {
            String token = secretService.generateToken(secretService.getInfoFromToken(refreshToken));
            return new Authorized(token,secretService.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AuthInfo verifyToken(String token) throws Exception {
        if (token != null) {
            return secretService.getInfoFromToken(token);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateAdminAssets() {
        // Request github api
/*        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(LBTConst.HALO_ADMIN_RELEASES_LATEST, Map.class);

        if (responseEntity == null ||
                responseEntity.getStatusCode().isError() ||
                responseEntity.getBody() == null) {
            log.debug("Failed to request remote url: [{}]", HALO_ADMIN_RELEASES_LATEST);
            throw new ServiceException("系统无法访问到 Github 的 API").setErrorData(HALO_ADMIN_RELEASES_LATEST);
        }

        Object assetsObject = responseEntity.getBody().get("assets");

        if (assetsObject instanceof List) {
            try {
                List assets = (List) assetsObject;
                Map assetMap = (Map) assets.stream()
                        .filter(assetPredicate())
                        .findFirst()
                        .orElseThrow(() -> new ServiceException("Halo admin 最新版暂无资源文件，请稍后再试"));

                Object browserDownloadUrl = assetMap.getOrDefault("browser_download_url", "");
                // Download the assets
                ResponseEntity<byte[]> downloadResponseEntity = restTemplate.getForEntity(browserDownloadUrl.toString(), byte[].class);

                if (downloadResponseEntity == null ||
                        downloadResponseEntity.getStatusCode().isError() ||
                        downloadResponseEntity.getBody() == null) {
                    throw new ServiceException("Failed to request remote url: " + browserDownloadUrl.toString()).setErrorData(browserDownloadUrl.toString());
                }

                String adminTargetName = haloProperties.getWorkDir() + HALO_ADMIN_RELATIVE_PATH;

                Path adminPath = Paths.get(adminTargetName);
                Path adminBackupPath = Paths.get(haloProperties.getWorkDir(), HALO_ADMIN_RELATIVE_BACKUP_PATH);

                backupAndClearAdminAssetsIfPresent(adminPath, adminBackupPath);

                // Create temp folder
                Path assetTempPath = FileUtils.createTempDirectory()
                        .resolve(assetMap.getOrDefault("name", "halo-admin-latest.zip").toString());

                // Unzip
                FileUtils.unzip(downloadResponseEntity.getBody(), assetTempPath);

                // Copy it to template/admin folder
                FileUtils.copyFolder(FileUtils.tryToSkipZipParentFolder(assetTempPath), adminPath);
            } catch (Throwable t) {
                log.error("Failed to update halo admin", t);
                throw new ServiceException("更新 Halo admin 失败");
            }
        } else {
            throw new ServiceException("Github API 返回内容有误").setErrorData(assetsObject);
        }*/
    }

/*    @NonNull
    @SuppressWarnings("unchecked")
    private Predicate<Object> assetPredicate() {
        return asset -> {
            if (!(asset instanceof Map)) {
                return false;
            }
            Map aAssetMap = (Map) asset;
            // Get content-type
            String contentType = aAssetMap.getOrDefault("content_type", "").toString();

            Object name = aAssetMap.getOrDefault("name", "");
            return name.toString().matches(HALO_ADMIN_VERSION_REGEX) && contentType.equalsIgnoreCase("application/zip");
        };
    }

    private void backupAndClearAdminAssetsIfPresent(@NonNull Path sourcePath, @NonNull Path backupPath) throws IOException {
        Assert.notNull(sourcePath, "Source path must not be null");
        Assert.notNull(backupPath, "Backup path must not be null");

        if (!FileUtils.isEmpty(sourcePath)) {
            // Clone this assets
            Path adminPathBackup = Paths.get(haloProperties.getWorkDir(), HALO_ADMIN_RELATIVE_BACKUP_PATH);

            // Delete backup
            FileUtils.deleteFolder(backupPath);

            // Copy older assets into backup
            FileUtils.copyFolder(sourcePath, backupPath);

            // Delete older assets
            FileUtils.deleteFolder(sourcePath);
        } else {
            FileUtils.createIfAbsent(sourcePath);
        }
    }*/

    /**
     * Builds authentication token.
     *
     * @param user user info must not be null
     * @return authentication token
     */
    @NonNull
    private Authorized buildAuthToken(@NonNull User user) throws Exception {
        Assert.notNull(user, "User must not be null");

        // Generate new token
        Authorized token = new Authorized().convertFrom(user);

        token.setAccessToken(secretService.generateToken(new AuthInfo(user.getUsername(),user.getId()+"",user.getNickname())));
        token.setExpiredIn(secretService.getExpire());

        return token;
    }
}
