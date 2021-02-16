package io.akash.accountaggregator.crsimulator.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.google.gson.Gson;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.akash.accountaggregator.crsimulator.model.Aggregator;
import io.akash.accountaggregator.crsimulator.model.Bank;
import io.akash.accountaggregator.crsimulator.model.NBFC;
import io.akash.accountaggregator.crsimulator.model.dtos.RegistryResponseDTO;
import io.akash.accountaggregator.crsimulator.repositories.AggregatorRepository;
import io.akash.accountaggregator.crsimulator.repositories.BankRepository;
import io.akash.accountaggregator.crsimulator.repositories.NBFCRepository;
import lombok.extern.slf4j.Slf4j;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class RegistryServiceTest {
	
    private static final Logger log= LoggerFactory.getLogger(RegistryServiceTest.class);
	
	private MongodExecutable 	mongodExecutable;
	private MongoTemplate 		mongoTemplate;
	  
	//@Autowired
	//Environment env;
	
	@Mock
	BankRepository bankRepo;
	
	@Mock
	NBFCRepository nbfcRepo;
	
	@Mock
	AggregatorRepository aggregatorRepo;
	
	@InjectMocks
	RegistryService registryService;
	
	@Before
	public void setup() throws Exception {
		String ip = "localhost";
		int port = 27018;

		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(ip, port, Network.localhostIsIPv6())).build();

		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongodExecutable = starter.prepare(mongodConfig);
		mongodExecutable.start();
		mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "test");
	}
	

    @After
    public void clean() {
        mongodExecutable.stop();
    }
	
	@Test
	public void getRegistry_dataAvailable() throws Exception {
		log.info("Testing RegistryService getRegistry() for data available (all 3 entities)");
		
		String fileName = "registry-response-data-available.json";
		
		RegistryResponseDTO expectedData = fetchTestData(fileName);
	
		 // given
		loadEmbeddedMongoWithData(expectedData);

		List<Bank> banks = mongoTemplate.findAll(Bank.class, "banks");
		List<NBFC> nbfcs = mongoTemplate.findAll(NBFC.class, "nbfcs");
		List<Aggregator> aggregators = mongoTemplate.findAll(Aggregator.class, "aggregators");
		
		when(bankRepo.findAll()).thenReturn(banks);
		when(nbfcRepo.findAll()).thenReturn(nbfcs);
		when(aggregatorRepo.findAll()).thenReturn(aggregators);
		
		assertEquals(expectedData, registryService.getRegistry());
	}

	@Test
	public void getRegistry_dataNotAvailable() throws Exception {
		log.info("Testing RegistryService getRegistry() for data not available (for the 3 entities)");
		
		String fileName = "registry-response-data-not-available.json";
		
		RegistryResponseDTO expectedData = fetchTestData(fileName);
	
		 // given
		loadEmbeddedMongoWithData(expectedData);

		List<Bank> banks = mongoTemplate.findAll(Bank.class, "banks");
		List<NBFC> nbfcs = mongoTemplate.findAll(NBFC.class, "nbfcs");
		List<Aggregator> aggregators = mongoTemplate.findAll(Aggregator.class, "aggregators");
		
		when(bankRepo.findAll()).thenReturn(banks);
		when(nbfcRepo.findAll()).thenReturn(nbfcs);
		when(aggregatorRepo.findAll()).thenReturn(aggregators);
		
		assertEquals(expectedData, registryService.getRegistry());
	}
	
	private void loadEmbeddedMongoWithData(RegistryResponseDTO data) {
	       
        for(Bank bank:data.getFips()) {
        	 mongoTemplate.save(bank, "banks");
        }
        
        for(NBFC nbfc:data.getFius()) {
       	 mongoTemplate.save(nbfc, "nbfcs");
       }
        
        for(Aggregator aggregator:data.getAccountAggregators()) {
       	 	mongoTemplate.save(aggregator, "aggregators");
       }
	}
	
	private RegistryResponseDTO fetchTestData(String fileName) throws Exception {
		Gson gson = new Gson();
//		String testDataPath = env.getProperty("test.resource.path") 
//									+this.getClass().getSimpleName().toLowerCase()+"\\"
//									+fileName;
		
		String testDataPath = "src/test/resources/"
					+this.getClass().getSimpleName().toLowerCase()+"\\"
					+fileName;
				
		RegistryResponseDTO response = gson.fromJson(new FileReader(new File(testDataPath)), 
											RegistryResponseDTO.class);
		return response;
	}
}
