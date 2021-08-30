package com.ljy.oschajsa.oschajsa;

import com.ljy.oschajsa.oschajsa.store.command.domain.StoreTagRepository;
import com.ljy.oschajsa.oschajsa.store.command.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private StoreTagRepository storeTagRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        storeTagRepository.save(Tag.of("태그1"));
        storeTagRepository.save(Tag.of("태그2"));
        storeTagRepository.save(Tag.of("태그3"));
        storeTagRepository.save(Tag.of("태그4"));
    }
}
