package com.lordz.lbt.model.support;

import javax.validation.GroupSequence;

/**
 * All check for hibernate validation.
 *
 */
@GroupSequence({CreateCheck.class, UpdateCheck.class})
public interface AllCheck {
}
