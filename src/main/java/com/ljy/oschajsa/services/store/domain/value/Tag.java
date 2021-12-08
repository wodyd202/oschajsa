package com.ljy.oschajsa.services.store.domain.value;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Tag {
    private final String tag;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected Tag(){
        tag = null;
    }

    private Tag(String tag) {
        verifyNotEmptyTag(tag);
        this.tag = tag;
    }

    private static final String TAG_MUST_NOT_BE_EMPTY = "tag must not be empty";
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
                "tag='" + tag + '\'' +
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
}
