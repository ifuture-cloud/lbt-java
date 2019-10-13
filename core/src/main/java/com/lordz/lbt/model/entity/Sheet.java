package com.lordz.lbt.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Page entity.
 *
 *
 * @date 3/22/19
 */
@Entity(name = "Sheet")
@DiscriminatorValue("1")
public class Sheet extends BasePost {

}
