package com.lordz.lbt.model.enums;

/**
 * Post create from type.
 *
 */
public enum PostCreateFrom implements ValueEnum<Integer> {

    /**
     * 发布来源：官方
     */
    OFFICIAL(0),

    /**
     * 发布来源：微信
     */
    WECHAT(1);

    private final Integer value;

    PostCreateFrom(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
