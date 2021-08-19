package com.ljy.oschajsa.oschajsa.store.command.domain;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Tags {
    private final Set<Tag> tags;
    private Tags(Set<Tag> tags) {
        tagsValidation(tags);
        this.tags = tags;
    }

    private static final String MAXIMUM_OF_3_TAGS_IS_ALLOWED_IN_THE_LIST_OF_TAGS = "maximum of 3 tags is allowed in the list of tags.";
    private void tagsValidation(Set<Tag> tags) {
        int size = tags.size();
        if(size == 0 || size > 3){
            throw new InvalidTagException(MAXIMUM_OF_3_TAGS_IS_ALLOWED_IN_THE_LIST_OF_TAGS);
        }
    }

    public static Tags withTags(List<Tag> tags) {
        return new Tags(Objects.requireNonNull(tags).stream().collect(Collectors.toSet()));
    }

    public Set<Tag> get() {
        return tags;
    }
}
