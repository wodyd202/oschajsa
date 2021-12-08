package com.ljy.oschajsa.services.store.domain.value;

import com.ljy.oschajsa.services.store.domain.Store;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags {
    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tag> tags;

    private Tags(Set<Tag> tags) {
        tagsValidation(tags);
        this.tags = tags;
    }

    private static final String MAXIMUM_OF_3_TAGS_IS_ALLOWED_IN_THE_LIST_OF_TAGS = "업체 태그는 1개 이상 3개 이하 까지 등록 가능합니다.";
    private void tagsValidation(Set<Tag> tags) {
        int size = tags.size();
        if(size == 0 || size > 3){
            throw new IllegalArgumentException(MAXIMUM_OF_3_TAGS_IS_ALLOWED_IN_THE_LIST_OF_TAGS);
        }
    }

    public static Tags withTags(List<Tag> tags) {
        return new Tags(Objects.requireNonNull(tags).stream().collect(Collectors.toSet()));
    }

    public void remove(Tag tag) {
        if(tags.size() == 1){
            throw new IllegalStateException("업체 태그는 최소 하나 이상 존재해야합니다.");
        }
        if(!tags.remove(tag)){
            throw new IllegalArgumentException("태그 정보가 존재하지 않습니다.");
        }
    }

    public void add(Tag tag, Store store) {
        if(tags.size() == 3){
            throw new IllegalStateException("업체 태그는 최대 3개까지 등록 가능합니다.");
        }
        tag.setStore(store);
        tags.add(tag);
    }

    public Set<Tag> get() {
        return tags == null ? Collections.EMPTY_SET : tags;
    }

    public void setStore(Store store) {
        for (Tag tag : tags) {
            tag.setStore(store);
        }
    }
}
