package com.ljy.oschajsa.services.store.command.infrastructure;

import com.ljy.oschajsa.services.store.domain.value.StoreTagRepository;
import com.ljy.oschajsa.services.store.domain.value.Tag;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InmemoryStoreTagRepository implements StoreTagRepository {
    private final List<Tag> repo = new ArrayList<>();

    @Override
    public void save(Tag tag) {
        repo.add(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        for(Tag tag : repo){
            if(tag.get().equals(name)){
                return Optional.of(tag);
            }
        }
        return Optional.ofNullable(null);
    }
}
