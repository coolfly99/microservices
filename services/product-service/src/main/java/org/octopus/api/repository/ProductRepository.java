package org.octopus.api.repository;

import org.octopus.api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "product-controller")
@RepositoryRestResource(collectionResourceRel = "type", path = "product")
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	@ApiOperation("find all products that are associated with a given name")
	Page<Product> findByNameIgnoringCase(@Param("name") @ApiParam(value = "name of the product") String name,
			Pageable pageable);
}
