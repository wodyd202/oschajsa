package com.ljy.oschajsa.services.store.command.application;

import com.ljy.oschajsa.common.file.FileUploader;
import com.ljy.oschajsa.services.store.command.application.exception.NotChangeStoreException;
import com.ljy.oschajsa.services.store.command.application.model.*;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.*;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
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
    private final StoreRepository storeRepository;
    private final FileUploader fileUploader;

    /**
     * @param businessNumber
     * @param changeStore
     * @param changer
     * # 업체 정보 변경
     */
    @CachePut(value = "store", key = "#businessNumber.get()")
    public StoreModel changeStore(BusinessNumber businessNumber, ChangeStore changeStore, OwnerId changer) throws NotChangeStoreException {
        Store store = getStore(storeRepository, businessNumber);

        boolean isChangeBusinessName = false;
        boolean isChangeBusinessTel = false;
        boolean isChangeBusinessHour = false;

        // 업체명 변경
        if(changeStore.getBusinessName() != null){
            isChangeBusinessName = store.changeBusinessName(BusinessName.of(changeStore.getBusinessName()), changer);
        }

        // 업체 전화번호 변경
        if(changeStore.getBusinessTel() != null){
            isChangeBusinessTel = store.changeTel(BusinessTel.of(changeStore.getBusinessTel()), changer);
        }

        // 업체 운영시간 변경
        if(changeStore.getBusinessHour() != null){
            ChangeBusinessHour changeBusinessHour = changeStore.getBusinessHour();
            isChangeBusinessHour = store.changeBusinessHour(BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(
                    changeBusinessHour.getWeekdayStart(),
                    changeBusinessHour.getWeekdayEnd(),
                    changeBusinessHour.getWeekendStart(),
                    changeBusinessHour.getWeekendEnd()
            ), changer);
        }

        if(isNotChange(isChangeBusinessName, isChangeBusinessTel, isChangeBusinessHour)){
            throw new NotChangeStoreException();
        }

        storeRepository.save(store);
        return store.toModel();
    }

    private boolean isNotChange(boolean isChangeBusinessName, boolean isChangeBusinessTel, boolean isChangeBusinessHour) {
        return !isChangeBusinessName && !isChangeBusinessTel && !isChangeBusinessHour;
    }

    /**
     * @param businessNumber
     * @param ownerId
     * # 업체 재오픈
     */
    @CachePut(value = "store", key = "#businessNumber.get()")
    public StoreModel reOpen(BusinessNumber businessNumber, OwnerId ownerId) {
        Store store = getStore(storeRepository, businessNumber);
        store.reOpen(ownerId);

        storeRepository.save(store);

        return store.toModel();
    }

    /**
     * @param businessNumber
     * @param closer
     * # 업체 폐업 준비
     */
    @CachePut(value = "store", key = "#businessNumber.get()")
    public StoreModel preparedClose(BusinessNumber businessNumber, OwnerId closer) {
        Store store = getStore(storeRepository, businessNumber);
        store.preparedClose(closer);

        storeRepository.save(store);

        return store.toModel();
    }

    /**
     * @param businessNumber
     * @param changer
     * # 업체 운영 중지
     */
    @CachePut(value = "store", key = "#businessNumber.get()")
    public StoreModel stop(BusinessNumber businessNumber, OwnerId changer) {
        Store store = getStore(storeRepository, businessNumber);
        store.stop(changer);

        storeRepository.save(store);

        return store.toModel();
    }

    /**
     * @param businessNumber
     * @param changeBusinessName
     * @param changer
     * # 업체 정보 변경
     */
    @CachePut(value = "store", key = "#businessNumber.get()")
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
    @CachePut(value = "store", key = "#businessNumber.get()")
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
    @CachePut(value = "store", key = "#businessNumber.get()")
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
    @CachePut(value = "store", key = "#businessNumber.get()")
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
    @CachePut(value = "store", key = "#businessNumber.get()")
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
    @CachePut(value = "store", key = "#businessNumber.get()")
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
