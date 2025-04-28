package com.reznikov.smartenergycustomer.services;


import com.reznikov.smartenergycustomer.dto.SupplierFullDto;
import com.reznikov.smartenergycustomer.repositories.SupplierRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class OrganizationRestTemplateClient {
	@Resource
	RestTemplate restTemplate;

	@Resource
	SupplierRedisRepository redisRepository;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

	public SupplierFullDto getOrganization(Long supplierId){
		//logger.debug("In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());

        SupplierFullDto supplierFullDto = checkRedisCache(supplierId);

        if (supplierFullDto != null){
            //logger.debug("I have successfully retrieved an organization {} from the redis cache: {}", supplierId, organization);
            return supplierFullDto;
        }

        //logger.debug("Unable to locate organization from the redis cache: {}.", organizationId);
        
		ResponseEntity<SupplierFullDto> restExchange =
				restTemplate.exchange(
						"http://gateway:8072/smartenergy/api/suppliers/{supplierId}",
						HttpMethod.GET,
						null, SupplierFullDto.class, supplierId);
		
		/*Save the record from cache*/
        supplierFullDto = restExchange.getBody();
        if (supplierFullDto != null) {
			cacheSupplierFullDtoObject(supplierFullDto);
        }

		return restExchange.getBody();
	}

	private SupplierFullDto checkRedisCache(Long supplierId) {
		try {
			return redisRepository.findById(supplierId).orElse(null);
		}catch (Exception ex){
			logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", supplierId, ex);
			return null;
		}
	}
	
	private void cacheSupplierFullDtoObject(SupplierFullDto supplierFullDto) {
        try {
        	redisRepository.save(supplierFullDto);
        }catch (Exception ex){
            logger.error("Unable to cache organization {} in Redis. Exception {}", supplierFullDto.getId(), ex);
        }
    }
}
