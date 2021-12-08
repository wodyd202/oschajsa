package com.ljy.oschajsa.services.store.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
public class Tags {
    @ElementCollection
    private final Set<Tag> tags;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected Tags(){tags = null;}

    private Tags(Set<Tag> tags) {
        tagsValidation(tags);
        this.tags = tags;
    }

    private static final String MAXIMUM_OF_3_TAGS_IS_ALLOWED_IN_THE_LIST_OF_TAGS = "maximum of 3 tags is allowed in the list of tags.";
    private void tagsValidation(Set<Tag> tags) {
        int size = tags.size();
        if(size == 0 || size > 3){
            throw new IllegalArgumentException(MAXIMUM_OF_3_TAGS_IS_ALLOWED_IN_THE_LIST_OF_TAGS);
        }
    }

    public static Tags withTags(List<Tag> tags) {
        return new Tags(Objects.requireNonNull(tags).stream().collect(Collectors.toSet()));
    }

    public Set<Tag> get() {
        return tags;
    }
}
