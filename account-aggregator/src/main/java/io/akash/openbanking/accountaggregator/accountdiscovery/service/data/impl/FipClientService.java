package io.akash.openbanking.accountaggregator.accountdiscovery.service.data.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.constant.GeneralConstants;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.constant.URLConstants;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryFipRequestDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.AccountDiscoveryFipResponseDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.data.api.IFipClientService;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.AccountManagementService;
import io.akash.openbanking.accountaggregator.helper.exception.CustomException;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Purpose:-
 * Handles all the networks calls to the FIPs 
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-09
 * 
 **/

@Service
public class FipClientService implements IFipClientService{
	
    private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);
    
	/**
	 * @param  accountDiscoveryRequestApp(AccountDiscoveryFipRequestDTO) and fip(RegistryEntity)
	 * @return accountDiscoveryFipResponseDTO(AccountDiscoveryFipResponseDTO) represent the fetched account and fip details
	 * @throws ResponseStatusException extends RuntimeException internally
	 */
    
	@Override
	public AccountDiscoveryFipResponseDTO discoverAccounts(AccountDiscoveryFipRequestDTO accountDiscoveryFipRequestDTO, 
														   RegistryEntity fip) {  
		log.info("Executing FipClientService.discoverAccounts() with Param1 - "
				+"accountDiscoveryFipRequestDTO(AccountDiscoveryFipRequestDTO): {} "
				+"and Param2 - fip(RegistryEntity): {}",accountDiscoveryFipRequestDTO,fip);

		String urlDomain = fip.getApis().get(0).getUrl();
		
		String url = URLConstants.URL_SCHEME_HTTP + urlDomain + URLConstants.ACCOUNT_DISCOVERY_END_POINT; 
		
		String accountDiscoveryResponseJson = null;

		AccountDiscoveryFipResponseDTO accountDiscoveryFipResponseDTO = null;
		
		try {
			MediaType JSON = MediaType.get(GeneralConstants.MEDIA_TYPE);

			String accDiscoveryDTOJson = new Gson().toJson(accountDiscoveryFipRequestDTO);

			RequestBody body = RequestBody.create(JSON, accDiscoveryDTOJson);

			OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

			clientBuilder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
						 .writeTimeout(5, TimeUnit.MINUTES)   // write timeout
						 .readTimeout(5, TimeUnit.MINUTES);   // read timeout

			OkHttpClient client = clientBuilder.build();
			// TODO: credentials are hard coded, need to be updated
			Request request = new Request.Builder().url(url)
					.addHeader("Authorization", Credentials.basic(GeneralConstants.USERNAME, GeneralConstants.PASSWORD))
					.post(body).build();
			
			Response fipResponse = client.newCall(request).execute();
			Integer fipResponseStatus = fipResponse.code();
			
			if(fipResponseStatus != 200) {
				StringBuilder expMessageBuilder = new StringBuilder()
									.append("Error! Account Discovery Request failed for FIP: "+fip.getName())
									.append(System.getProperty("line.separator"))
									.append("RCA: The FIP responded back with the code "+fipResponseStatus)
									.append("and Message:")
									.append(fipResponse.message() != null && fipResponse.message().isEmpty()?fipResponse.message():"No message");
				
				throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, expMessageBuilder.toString());
			}

			accountDiscoveryResponseJson = fipResponse.body().string();
			accountDiscoveryFipResponseDTO = new Gson().fromJson(accountDiscoveryResponseJson, AccountDiscoveryFipResponseDTO.class);
			
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
					.append("RCA: The response- "+accountDiscoveryResponseJson)
					.append(System.getProperty("line.separator"))
					.append("received from FIP-"+fip.getName())
					.append("does not match the expected JSON format.")
					.append(System.getProperty("line.separator"))
					.append("The expected format must comply with the object "+AccountDiscoveryFipResponseDTO.class.getCanonicalName());
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR ,expMessageBuilder.toString(),e);	
		}catch(IllegalStateException e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Inappropriate or out of turn request!")
					.append(System.getProperty("line.separator"))
					.append("RCA: Call for Account Discovery Request has already been made to "+fip.getName());
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,expMessageBuilder.toString(),e);
		}catch (IOException e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Did not receive a timely response from from FIP "+fip.getName())
					.append(System.getProperty("line.separator"))
					.append("RCA: Either FIP server is down or there is a network problem.");
			throw new CustomException(HttpStatus.GATEWAY_TIMEOUT,expMessageBuilder.toString(),e);
		}catch(CustomException e) {
			throw e;
		}catch (Exception e) {
			StringBuilder expMessageBuilder = new StringBuilder()
					.append("Unrecognized exception! Need to handle.");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
					expMessageBuilder.toString(),e);
		}
		
		return accountDiscoveryFipResponseDTO;
	}
}
