package com.lordz.lbt.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Journal comment.
 *
 *
 * @date 19-4-25
 */
@Entity(name = "JournalComment")
@DiscriminatorValue("2")
public class JournalComment extends BaseComment {

}
