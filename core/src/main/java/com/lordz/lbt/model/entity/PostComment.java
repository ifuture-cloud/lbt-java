package com.lordz.lbt.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * PostComment entity.
 *
 *
 */
@Entity(name = "PostComment")
@DiscriminatorValue("0")
public class PostComment extends BaseComment {

}
