package com.cn.company.plan.repository;

import com.cn.company.plan.entity.OrderEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Edison Xu on 2017/3/15.
 */
@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderEntryRepository extends PagingAndSortingRepository<OrderEntry, String> {
}
