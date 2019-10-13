package com.lordz.lbt.handler.file;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2FileVersion;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import com.backblaze.b2.client.structures.B2UploadListener;
import com.backblaze.b2.client.structures.B2UploadProgress;
import com.lordz.lbt.exception.FileOperationException;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.enums.AttachmentType;
import com.lordz.lbt.model.support.UploadResult;
import com.lordz.lbt.service.UserService;
import com.lordz.lbt.utils.FilenameUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ：zzz
 * https://www.backblaze.com/b2/docs/versions.html
 */
@Slf4j
@Component
public class BackblazeFileHandler implements FileHandler {

    @Value("${lbt.oss.backblaze.app-key-id}")
    private String APP_KEY_ID;

    @Value("${lbt.oss.backblaze.app-key}")
    private String APP_KEY;

    @Autowired
    private UserService userService;

    private String keySeparator = "..lbt..";

    B2StorageClient client;


    private B2StorageClient getClient(){
        if (client == null) {
            client = B2StorageClientFactory
                    .createDefaultFactory()
                    .create(APP_KEY_ID, APP_KEY, "official ifuture.pro cserver");
        }
        return client;

    }

    @Override
    public UploadResult upload(MultipartFile file) {
        Optional<User> user = userService.getCurrentUser();
        if (!user.isPresent()) {
            return null;
        }
        try {
            B2UploadFileRequest.Builder builder = B2UploadFileRequest.builder("78d0e6fe8e4f549460d40c1e",user.get().getUsername() + "/" + file.getOriginalFilename(),file.getContentType(), B2ByteArrayContentSource.build(file.getBytes()));
            builder.setListener(progress -> {
                System.out.println(progress.toString());
            });
            B2FileVersion version = getClient().uploadSmallFile(builder.build());
            if (version == null) {
                throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到oss.ifuture.pro失败 ");
            }
            String basename = FilenameUtils.getBasename(file.getOriginalFilename());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            UploadResult uploadResult = new UploadResult();
            uploadResult.setFilename(basename);
            uploadResult.setFilePath("https://oss.ifuture.pro/b2api/v1/b2_download_file_by_id?fileId=" + version.getFileId());
            uploadResult.setKey(version.getFileId()+keySeparator+version.getFileName());
            uploadResult.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));
            uploadResult.setSuffix(extension);
            uploadResult.setSize(file.getSize());

            return uploadResult;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (B2Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(String key) {
        String the[] = key.split(keySeparator);
        if (the.length ==2) {
            try {
                getClient().deleteFileVersion(the[1],the[0]);
            } catch (B2Exception e) {
                e.printStackTrace();
            }
        }else {
            log.error("Can not parse file key " + key);
        }
    }

    @Override
    public boolean supportType(AttachmentType type) {
        return AttachmentType.B2.equals(type);
    }
}
