package com.cn.company.plan.repository;

import com.cn.company.plan.entity.ProductEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Remember to add {@code EnableJpaRepositories} in the start Application
 * <br>
 * Created by Edison Xu on 2017/3/14.
 */
@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductEntryRepository extends PagingAndSortingRepository<ProductEntry, String> {

}
