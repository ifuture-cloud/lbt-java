package com.lordz.lbt.controller.api;

import com.lordz.lbt.auth.annotation.Auth;
import com.lordz.lbt.model.dto.AttachmentDTO;
import com.lordz.lbt.model.entity.Attachment;
import com.lordz.lbt.model.entity.User;
import com.lordz.lbt.model.params.AttachmentParam;
import com.lordz.lbt.model.params.AttachmentQuery;
import com.lordz.lbt.service.AttachmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

import static com.lordz.lbt.model.support.LBTConst.USER_API_PREFIX;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Attachment controller.
 *
 * @author johnniang
 * @date 3/21/19
 */
@RestController
@RequestMapping(USER_API_PREFIX + "/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;


    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    /**
     * List of attachment.
     *
     * @param pageable pageable
     * @return Page<AttachmentDTO>
     */
    @GetMapping
    public Page<AttachmentDTO> pageBy(@PageableDefault(sort = "updateTime", direction = DESC) Pageable pageable,
                                      AttachmentQuery attachmentQuery,
                                      @Auth User user) {
        attachmentQuery.setUserId(user.getId());
        return attachmentService.pageDtosBy(pageable, attachmentQuery);
    }

    /**
     * Get attachment by id.
     *
     * @param id attachment id
     * @return AttachmentDTO
     */
    @GetMapping("{id:\\d+}")
    public AttachmentDTO getBy(@PathVariable("id") Integer id) {
        Attachment attachment = attachmentService.getById(id);
        return attachmentService.convertToDto(attachment);
    }

    @PutMapping("{attachmentId:\\d+}")
    public AttachmentDTO updateBy(@PathVariable("attachmentId") Integer attachmentId,
                                  @RequestBody @Valid AttachmentParam attachmentParam) {
        Attachment attachment = attachmentService.getById(attachmentId);
        attachmentParam.update(attachment);
        return new AttachmentDTO().convertFrom(attachmentService.update(attachment));
    }

    /**
     * Delete attachment by id
     *
     * @param id id
     */
    @DeleteMapping("{id:\\d+}")
    public AttachmentDTO deletePermanently(@PathVariable("id") Integer id) {
        return attachmentService.convertToDto(attachmentService.removePermanently(id));
    }

    @PostMapping("upload")
    public AttachmentDTO uploadAttachment(@RequestPart("file") MultipartFile file) {
        return attachmentService.convertToDto(attachmentService.upload(file));
    }

    @PostMapping(value = "uploads")
    public List<AttachmentDTO> uploadAttachments(@RequestPart("files") MultipartFile[] files) {
        List<AttachmentDTO> result = new LinkedList<>();

        for (MultipartFile file : files) {
            // Upload single file
            Attachment attachment = attachmentService.upload(file);
            // Convert and add
            result.add(attachmentService.convertToDto(attachment));
        }

        return result;
    }

    @GetMapping("media_types")
    public List<String> listMediaTypes() {
        return attachmentService.listAllMediaType();
    }
}
