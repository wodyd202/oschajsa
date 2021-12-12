package com.ljy.oschajsa.services.store.domain.value;

import com.ljy.oschajsa.services.store.domain.Store;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * 업체 태그
 */
@Entity
@Table(name = "store_tags", indexes = @Index(columnList = "store_business_number"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    // 태그 이름
    @Column(nullable = false, length = 10)
    private String tag;

    @ManyToOne
    @JoinColumn(name="store_business_number", nullable=false)
    private Store store;

    private Tag(String tag) {
        verifyNotEmptyTag(tag);
        this.tag = tag;
    }

    private static final String TAG_MUST_NOT_BE_EMPTY = "업체 태그를 입력해주세요.";
    private void verifyNotEmptyTag(String tag) {
        if(tag.isEmpty()){
            throw new IllegalArgumentException(TAG_MUST_NOT_BE_EMPTY);
        }
    }

    public static Tag of(String tag) {
        return new Tag(Objects.requireNonNull(tag));
    }

    public String get() {
        return tag;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "seq=" + seq +
                ", tag='" + tag + '\'' +
                ", store=" + store +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag1 = (Tag) o;
        return Objects.equals(tag, tag1.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
