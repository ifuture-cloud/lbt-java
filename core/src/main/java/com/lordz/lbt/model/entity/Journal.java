package com.lordz.lbt.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Journal entity
 *
 *
 * @date 3/22/19
 */
@Data
@Entity
@Table(name = "journals")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Journal extends UserBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content", columnDefinition = "varchar(1023) not null")
    private String content;

    @Column(name = "likes", columnDefinition = "bigint default 0")
    private Long likes;

    @Override
    public void prePersist() {
        super.prePersist();

        id = null;

        if (likes == null || likes < 0) {
            likes = 0L;
        }
    }
}
