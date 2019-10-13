package com.lordz.lbt.service.impl;

import com.lordz.lbt.handler.file.FileHandlers;
import com.lordz.lbt.service.AttachmentService;
import com.lordz.lbt.service.OptionService;
import com.lordz.lbt.utils.LBTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.lordz.lbt.exception.AlreadyExistsException;
import com.lordz.lbt.model.dto.AttachmentDTO;
import com.lordz.lbt.model.entity.Attachment;
import com.lordz.lbt.model.enums.AttachmentType;
import com.lordz.lbt.model.params.AttachmentQuery;
import com.lordz.lbt.model.properties.AttachmentProperties;
import com.lordz.lbt.model.support.UploadResult;
import com.lordz.lbt.repository.AttachmentRepository;
import com.lordz.lbt.service.base.AbstractCrudService;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * AttachmentService implementation
 *
 * @author ryanwang
 * @date : 2019-03-14
 */
@Slf4j
@Service
public class AttachmentServiceImpl extends AbstractCrudService<Attachment, Integer> implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final OptionService optionService;

    private final FileHandlers fileHandlers;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository,
                                 OptionService optionService,
                                 FileHandlers fileHandlers) {
        super(attachmentRepository);
        this.attachmentRepository = attachmentRepository;
        this.optionService = optionService;
        this.fileHandlers = fileHandlers;
    }

    @Override
    public Page<AttachmentDTO> pageDtosBy(@NonNull Pageable pageable, AttachmentQuery attachmentQuery) {
        Assert.notNull(pageable, "Page info must not be null");

        // List all
        Page<Attachment> attachmentPage = attachmentRepository.findAll(buildSpecByQuery(attachmentQuery), pageable);

        // Convert and return
        return attachmentPage.map(this::convertToDto);
    }

    @NonNull
    private Specification<Attachment> buildSpecByQuery(@NonNull AttachmentQuery attachmentQuery) {
        Assert.notNull(attachmentQuery, "Attachment query must not be null");

        return (Specification<Attachment>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();

            if (attachmentQuery.getMediaType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("mediaType"), attachmentQuery.getMediaType()));
            }

            if (attachmentQuery.getAttachmentType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), attachmentQuery.getAttachmentType()));
            }

            if (attachmentQuery.getKeyword() != null) {

                String likeCondition = String.format("%%%s%%", StringUtils.strip(attachmentQuery.getKeyword()));

                Predicate nameLike = criteriaBuilder.like(root.get("name"), likeCondition);

                predicates.add(criteriaBuilder.or(nameLike));
            }

            if (attachmentQuery.getUserId() != null) {

            }

            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }

    @Override
    public Attachment upload(MultipartFile file) {
        Assert.notNull(file, "Multipart file must not be null");

        AttachmentType attachmentType = getAttachmentType();

        log.debug("Starting uploading... type: [{}], file: [{}]", attachmentType, file.getOriginalFilename());

        // Upload file
        UploadResult uploadResult = fileHandlers.upload(file, attachmentType);

        log.debug("Attachment type: [{}]", attachmentType);
        log.debug("Upload result: [{}]", uploadResult);

        // Build attachment
        Attachment attachment = new Attachment();
        attachment.setName(uploadResult.getFilename());
        // Convert separator
        attachment.setPath(LBTUtils.changeFileSeparatorToUrlSeparator(uploadResult.getFilePath()));
        attachment.setFileKey(uploadResult.getKey());
        attachment.setThumbPath(uploadResult.getThumbPath());
        attachment.setMediaType(uploadResult.getMediaType().toString());
        attachment.setSuffix(uploadResult.getSuffix());
        attachment.setWidth(uploadResult.getWidth());
        attachment.setHeight(uploadResult.getHeight());
        attachment.setSize(uploadResult.getSize());
        attachment.setType(attachmentType);

        log.debug("Creating attachment: [{}]", attachment);

        // Create and return
        return create(attachment);
    }

    @Override
    public Attachment removePermanently(Integer id) {
        // Remove it from database
        Attachment deletedAttachment = removeById(id);

        // Remove the file
        fileHandlers.delete(deletedAttachment);

        log.debug("Deleted attachment: [{}]", deletedAttachment);

        return deletedAttachment;
    }

    @Override
    public AttachmentDTO convertToDto(Attachment attachment) {
        Assert.notNull(attachment, "Attachment must not be null");

        // Get blog base url
        String blogBaseUrl = optionService.getBlogBaseUrl();

        // Convert to output dto
        AttachmentDTO attachmentDTO = new AttachmentDTO().convertFrom(attachment);

        if (Objects.equals(attachmentDTO.getType(), AttachmentType.LOCAL)) {
            // Append blog base url to path and thumbnail
            String fullPath = StringUtils.join(blogBaseUrl, "/", attachmentDTO.getPath());
            String fullThumbPath = StringUtils.join(blogBaseUrl, "/", attachmentDTO.getThumbPath());

            // Set full path and full thumb path
            attachmentDTO.setPath(fullPath);
            attachmentDTO.setThumbPath(fullThumbPath);
        }

        return attachmentDTO;
    }

    @Override
    public List<String> listAllMediaType() {
        return attachmentRepository.findAllMediaType();
    }

    @Override
    public Attachment create(Attachment attachment) {
        Assert.notNull(attachment, "Attachment must not be null");

        // Check attachment path
        pathMustNotExist(attachment);

        return super.create(attachment);
    }

    /**
     * Attachment path must not be exist.
     *
     * @param attachment attachment must not be null
     */
    private void pathMustNotExist(@NonNull Attachment attachment) {
        Assert.notNull(attachment, "Attachment must not be null");

        long pathCount = attachmentRepository.countByPath(attachment.getPath());

        if (pathCount > 0) {
            throw new AlreadyExistsException("附件路径为 " + attachment.getPath() + " 已经存在");
        }
    }

    /**
     * Get attachment type from options.
     *
     * @return attachment type
     */
    @NonNull
    private AttachmentType getAttachmentType() {
        //return optionService.getEnumByPropertyOrDefault(AttachmentProperties.ATTACHMENT_TYPE, AttachmentType.class, AttachmentType.B2);
        return AttachmentType.B2;
    }
}