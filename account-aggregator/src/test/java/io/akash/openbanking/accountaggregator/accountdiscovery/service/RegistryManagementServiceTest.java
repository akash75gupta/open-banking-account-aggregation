package io.akash.openbanking.accountaggregator.accountdiscovery.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.FileUtil;
import io.akash.openbanking.accountaggregator.accountdiscovery.model.RegistryEntity;
import io.akash.openbanking.accountaggregator.accountdiscovery.repository.RegistryEntityRepository;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.AccountManagementService;
import io.akash.openbanking.accountaggregator.accountdiscovery.service.restful.impl.RegistryManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistryManagementServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);

	@MockBean
	RegistryEntityRepository registryEntityRepository;
	
	@Autowired
	RegistryManagementService registryManagementService;
	
	@Test
	public void getFips_Positive() throws Exception
	{
		log.info("Testing getFips() functionality of AccountManagementService for a positive test case.");

		String datafromRegistry =
				FileUtil.fetchTestDataForClass("listOfDataFromRegistry.json",AccountManagementServiceTest.class.getSimpleName());

		Type listType = new TypeToken<ArrayList<RegistryEntity>>() {
		}.getType();
		List<RegistryEntity> listofDataFromRegistryFromDB=new Gson().fromJson(datafromRegistry,listType);
        when(registryEntityRepository.findAll()).thenReturn(listofDataFromRegistryFromDB);
        assertTrue(registryManagementService.getFips().size()>=1);

	}
	
	@Test
	public void getFips_Negetive() throws Exception{
		log.info("Testing getFips() functionality of AccountManagementService for a negative test case.");

		String datafromRegistry =
				FileUtil.fetchTestDataForClass("emptylistOfDataFromRegistry.json",AccountManagementServiceTest.class.getSimpleName() );

		Type listType = new TypeToken<ArrayList<RegistryEntity>>() {
		}.getType();
		List<RegistryEntity> listofDataFromRegistryFromDB=new Gson().fromJson(datafromRegistry,listType);
		when(registryEntityRepository.findAll()).thenReturn(listofDataFromRegistryFromDB);
		assertTrue(registryManagementService.getFips().size()==0);

	}

}
