package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.constant.URLConstants;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.RegistryDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.data.api.ICentralRegistryClientService;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.AccountManagementService;
import io.akash.openbanking.accountaggregator.helper.exception.CustomException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Purpose:- 
 * Handles all networks calls to the central registry   
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-09
 * 
 **/

@Service
public class CentralRegistryClientService implements ICentralRegistryClientService {
	
	@Autowired
	Environment environment;

    private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);

    @Override
	public RegistryDTO getRegistry() {
		log.info("Executing CentralRegistryClientService.getRegistry() without any params - "
				+"fetching registry details from the Central Registry");
		
		RegistryDTO registryDto				= null;
		Response 	registryResponse 		= null;
		String 	 	registryResponseJson 	= null;
		    
		String url = URLConstants.URL_SCHEME_HTTP 
								+ environment.getProperty("central.registry.domain") 
																+ URLConstants.CENTRAL_REGISTRY_END_POINT; 	
		try {
			Request request = new Request.Builder().url(url).get().build();
			registryResponse = new OkHttpClient().newCall(request).execute();
			
			Integer registryResponseStatus = registryResponse.code();
			
			if(registryResponseStatus!= 200) {
				StringBuilder expMessageBuilder = new StringBuilder()
									.append("Error! Request to central registry failed")
									.append(System.getProperty("line.separator"))
									.append("RCA: The Central Registry responded back with the code "+registryResponseStatus)
									.append("and Message:")
									.append(registryResponse.message() != null && registryResponse.message().isEmpty()?registryResponse.message():"No message");
				
				throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, expMessageBuilder.toString());
			}
			
			registryResponseJson = registryResponse.body().string();
		    registryDto = new Gson().fromJson(registryResponseJson, RegistryDTO.class);
		    
		}catch(IllegalArgumentException e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Invalid URL!")
					.append(System.getProperty("line.separator"))
					.append("RCA: The given url- "+url+" is not a valid HTTP or HTTPS URL");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR ,expMessageBuilder.toString(),e);
		}catch (JsonSyntaxException e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Invalid response format!")
					.append(System.getProperty("line.separator"))
					.append("RCA: The response- "+registryResponseJson)
					.append(System.getProperty("line.separator"))
					.append("received from the Central Registry does not match the expected JSON format.")
					.append(System.getProperty("line.separator"))
					.append("The expected format must comply with the object "+RegistryDTO.class.getCanonicalName());
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR ,expMessageBuilder.toString(),e);	
		}catch(IllegalStateException e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Inappropriate or out of turn request!")
					.append(System.getProperty("line.separator"))
					.append("RCA: Call for fetching registry details has already been made to the central registry");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,expMessageBuilder.toString(),e);
		}catch (IOException e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Did not receive a timely response from the central registry.")
					.append(System.getProperty("line.separator"))
					.append("RCA: Either Central Registry server is down or there is a network problem.");
			throw new CustomException(HttpStatus.GATEWAY_TIMEOUT,expMessageBuilder.toString(),e);
		}catch(CustomException e) {
			throw e;
		}catch (Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Unrecognized exception! Need to handle.");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
					expMessageBuilder.toString(),e);
		}
		
        log.info("Returning from CentralRegistryClientService.getRegistry() with value RegistryDTO: {}",registryDto);
        
		return registryDto;
	}
}
