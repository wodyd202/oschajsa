package com.ljy.oschajsa.services.store.command.application;

import com.ljy.oschajsa.common.file.FileUploader;
import com.ljy.oschajsa.services.store.command.application.model.*;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ljy.oschajsa.services.store.command.application.StoreServiceHelper.getStore;

/**
 * 업체 정보 변경 서비스
 */
@Service
@Transactional
@AllArgsConstructor
public class ChangeStoreService {
    private StoreRepository storeRepository;
    private FileUploader fileUploader;

    /**
     * @param businessNumber
     * @param changeBusinessName
     * @param changer
     * # 업체 정보 변경
     */
    public StoreModel changeBusinessName(BusinessNumber businessNumber,
                                         ChangeBusinessName changeBusinessName,
                                         OwnerId changer) {
        Store store = getStore(storeRepository, businessNumber);
        store.changeBusinessName(BusinessName.of(changeBusinessName.getBusinessName()), changer);

        storeRepository.save(store);
        return store.toModel();
    }

    /**
     * @param businessNumber
     * @param changeTel
     * @param changer
     * # 업체 전화번호 변경
     */
    public StoreModel changeTel(BusinessNumber businessNumber, ChangeTel changeTel, OwnerId changer) {
        Store store = getStore(storeRepository, businessNumber);
        store.changeTel(BusinessTel.of(changeTel.getTel()), changer);

        storeRepository.save(store);
        return store.toModel();
    }

    /**
     * @param businessNumber
     * @param changeBusinessHour
     * @param changer
     * # 업체 운영 시간 변경
     */
    public StoreModel changeBusinessHour(BusinessNumber businessNumber, ChangeBusinessHour changeBusinessHour, OwnerId changer) {
        Store store = getStore(storeRepository, businessNumber);
        store.changeBusinessHour(BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(
                changeBusinessHour.getWeekdayStart(),
                changeBusinessHour.getWeekdayEnd(),
                changeBusinessHour.getWeekendStart(),
                changeBusinessHour.getWeekendEnd()
        ), changer);

        storeRepository.save(store);
        return store.toModel();
    }

    /**
     * @param businessNumber
     * @param removeTag
     * @param remover
     * # 업체 태그 제거
     */
    public StoreModel removeTag(BusinessNumber businessNumber, RemoveTag removeTag, OwnerId remover) {
        Store store = getStore(storeRepository, businessNumber);
        store.removeTag(Tag.of(removeTag.getTag()), remover);

        storeRepository.save(store);
        return store.toModel();
    }

    /**
     * @param businessNumber
     * @param addTag
     * @param adder
     * # 업체 태그 추가
     */
    public StoreModel addTag(BusinessNumber businessNumber, AddTag addTag, OwnerId adder) {
        Store store = getStore(storeRepository, businessNumber);
        store.addTag(Tag.of(addTag.getTag()), adder);

        storeRepository.save(store);
        return store.toModel();
    }

    /**
     * @param changeLogo
     * @param businessNumber
     * @param owner
     * # 업체 로고 변경
     */
    public StoreModel changeLogo(ChangeLogo changeLogo, BusinessNumber businessNumber, OwnerId owner) {
        Store store = getStore(storeRepository, businessNumber);
        // 기존 로고가 존재할 경우 로고파일 삭제
        if(store.hasLogo()){
            fileUploader.removeFile(store.getLogo());
        }
        store.changeLogo(owner, changeLogo.getFile());
        StoreModel storeModel = store.toModel();
        fileUploader.uploadFile(changeLogo.getFile(), storeModel.getLogo());
        return storeModel;
    }
}
