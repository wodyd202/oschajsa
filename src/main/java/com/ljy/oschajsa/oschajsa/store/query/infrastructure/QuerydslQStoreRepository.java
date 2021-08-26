package com.ljy.oschajsa.oschajsa.store.query.infrastructure;

import com.ljy.oschajsa.oschajsa.core.infrastructure.QuerydslRepository;
import com.ljy.oschajsa.oschajsa.store.query.model.QStoreRepository;
import com.ljy.oschajsa.oschajsa.store.query.model.QueryStore;
import org.springframework.stereotype.Repository;

@Repository
public class QuerydslQStoreRepository extends QuerydslRepository<QueryStore> implements QStoreRepository {

}
