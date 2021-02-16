package io.akash.accountaggregator.crsimulator.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import io.akash.accountaggregator.crsimulator.model.dtos.RegistryResponseDTO;
import io.akash.accountaggregator.crsimulator.services.RegistryService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CentralRegistryControllerTest {
    private static final Logger log= LoggerFactory.getLogger(CentralRegistryControllerTest.class);

	@Autowired
	Environment env;
	
	@MockBean
	RegistryService registryService;
	
	@Autowired
	CentralRegistryController registryController;
	
	@Test
	public void getRegistry_dataAvailable() throws Exception {
		log.info("Testing get registry for data available");
		String fileName = "registry-response-data-available.json";
		
		RegistryResponseDTO testData = fetchTestData(fileName);
		
		when(registryService.getRegistry()).thenReturn(testData);
		assertEquals(fetchTestData(fileName), registryController.getRegistry());
	}
	
	@Test
	public void getRegistry_dataNotAvailable() throws Exception {
		log.info("Testing get registry for not data not available");
		String fileName = "registry-response-data-not-available.json";
		
		RegistryResponseDTO testData = fetchTestData(fileName);
		
		when(registryService.getRegistry()).thenReturn(testData);
		assertEquals(fetchTestData(fileName), registryController.getRegistry());
	}

	private RegistryResponseDTO fetchTestData(String fileName) throws Exception {
		Gson gson = new Gson();
		String testDataPath = env.getProperty("test.resource.path") 
									+this.getClass().getSimpleName().toLowerCase()+"\\"
									+fileName;
		RegistryResponseDTO response = gson.fromJson(new FileReader(new File(testDataPath)), 
											RegistryResponseDTO.class);
		return response;
	}
}
