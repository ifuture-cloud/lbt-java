package com.lordz.lbt.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Post entity.
 *
 *
 */
@Entity(name = "Post")
@DiscriminatorValue(value = "0")
public class Post extends BasePost {

}
