package io.akash.accountaggregator.fipsimulator.data.services;
//package io.akash.accountaggregator.fipsimulator.data.services;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.http.HttpStatus;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.mongodb.MongoClient;
//import io.akash.accountaggregator.fipsimulator.data.repositories.CustomerRepository;
//import io.akash.accountaggregator.fipsimulator.exception.exceptions.CustomException;
//import io.akash.accountaggregator.fipsimulator.model.entities.Customer;
//import io.akash.accountaggregator.fipsimulator.utilities.FileUtil;
//
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.IMongodConfig;
//import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
//import de.flapdoodle.embed.mongo.config.Net;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.process.runtime.Network;
//import lombok.extern.slf4j.Slf4j;
//
//@RunWith(MockitoJUnitRunner.Silent.class)
//@Slf4j
//public class CustomerDataServiceTest{
//	private static MongodExecutable mongodExecutable;
//	private static MongoTemplate mongoTemplate;
//
//	private static final Customer CUSTOMER_EXPECTED_DATA_NEGETIVE;
//	private static final Customer CUSTOMER_EXPECTED_DATA_POSITIVE;
//	
//	private static final String   AADHAR_INPUT_POSITIVE;
//	private static final String   PAN_INPUT_POSITIVE;
//	private static final String   MOBILE_INPUT_POSITIVE;
//	private static final String   EMAIL_INPUT_POSITIVE;
//	
//	private static final String   AADHAR_INPUT_NEGATIVE;
//	private static final String   PAN_INPUT_NEGATIVE;
//	private static final String   MOBILE_INPUT_NEGATIVE;
//	private static final String   EMAIL_INPUT_NEGATIVE;
//
//	static{
//		Customer sampleCustomer = new Customer();
//		
//		sampleCustomer.setUsername("robert.uk.29@example.com");
//		sampleCustomer.setPassword("d9c663");
//		sampleCustomer.setEmail("robert.uk.29@example.com");
//		sampleCustomer.setPhone("6372347018");
//		sampleCustomer.setAadhar("595967236139");
//		sampleCustomer.setPan("EWIPG3516X");
//		
//		CUSTOMER_EXPECTED_DATA_POSITIVE = sampleCustomer;
//		
//		CUSTOMER_EXPECTED_DATA_NEGETIVE = null;
//		
//		AADHAR_INPUT_POSITIVE   = "595967236139";
//		PAN_INPUT_POSITIVE 		= "EWIPG3516X";
//		MOBILE_INPUT_POSITIVE	= "6372347018";
//		EMAIL_INPUT_POSITIVE 	= "robert.uk.29@example.com";
//		
//		AADHAR_INPUT_NEGATIVE   = "0000000000000";
//		PAN_INPUT_NEGATIVE 		= "ABCDEFGH";
//		MOBILE_INPUT_NEGATIVE	= "111111111";
//		EMAIL_INPUT_NEGATIVE 	= "abc@gmail.com";
//	}
//	
//	@Mock
//	CustomerRepository customerRepository;
//
//	@InjectMocks
//	CustomerDataService customerDataService;
//
//	@BeforeClass
//	public static void setup()  {
//		String ip = "localhost";
//		int port = 27018;
//
//		IMongodConfig mongodConfig = null;
//		try {
//			mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
//					.net(new Net(ip, port, Network.localhostIsIPv6())).build();
//		} catch (IOException e) {
//			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
//												"Error configuring embedded mongo!",e);
//		}
//
//		MongodStarter starter = MongodStarter.getDefaultInstance();
//		mongodExecutable = starter.prepare(mongodConfig);
//		try {
//			mongodExecutable.start();
//		} catch (IOException e) {
//			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
//												"Error starting embedded mongo!",e);
//		}
//		mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "test");
//		
//		loadDBWithData();
//	}
//
//	public static void loadDBWithData() {
//		loadEmbeddedMongoWithCustomerData();
//	}
//
//	private static void loadEmbeddedMongoWithCustomerData() {
//		log.info("Load mongo with NBFC data");
//
//		String fileName = null;
//		
//		fileName = "customers.json";
//	
//		String data = FileUtil.fetchTestDataForClass(fileName, CustomerDataServiceTest.class.getSimpleName());
//
//		Type listType = new TypeToken<Set<Customer>>() {
//		}.getType();
//
//		Set<Customer> customers = new Gson().fromJson(data, listType);
//
//		for (Customer customer : customers) {
//			mongoTemplate.save(customer, "users");
//		}
//	}
//
//	@AfterClass
//	public static void clean() {
//		mongodExecutable.stop();
//	}
//
//	@Test
//	public void getCustomerByMobileTest_Positive() {
//		log.info("Testing RegistryService getRegistry() for positive usecase");
//		
//		Query findQuery = new Query();
//		
//		findQuery.addCriteria(Criteria.where("phone").is(MOBILE_INPUT_POSITIVE));
//				
//		List<Customer> queryResult = mongoTemplate.find(findQuery,Customer.class);
//		
//		Customer customer = null;
//		if(null != queryResult && !queryResult.isEmpty()) { 
//			customer = queryResult.get(0);
//		}
//		
//		when(customerRepository.findByPhone(MOBILE_INPUT_POSITIVE))
//					.thenReturn(customer);
//		
//		assertThat(CUSTOMER_EXPECTED_DATA_POSITIVE)
//		.isEqualToComparingFieldByField(customerDataService.getCustomerByMobile(MOBILE_INPUT_POSITIVE));
//	}
//	
//	@Test
//	public void getCustomerByMobileTest_Negative()  {
//		log.info("Testing RegistryService getRegistry() for negative usecase");
//		
//		Query findQuery = new Query();
//		
//		findQuery.addCriteria(Criteria.where("phone").is(MOBILE_INPUT_NEGATIVE));
//		
//		List<Customer> queryResult = mongoTemplate.find(findQuery,Customer.class);
//		
//		Customer customer = null;
//		if(null != queryResult && !queryResult.isEmpty()) { 
//			customer = queryResult.get(0);
//		}
//		
//		when(customerRepository.findByPhone(MOBILE_INPUT_NEGATIVE))
//					.thenReturn(customer);
//		
//		assertEquals(CUSTOMER_EXPECTED_DATA_NEGETIVE, customerDataService.getCustomerByMobile(MOBILE_INPUT_NEGATIVE));
//	}
//
//	@Test
//	public void getCustomerByAadharTest_Positive() {
//		log.info("Testing RegistryService getCustomerByAadhar() for positive usecase");
//		
//		Query findQuery = new Query();
//		
//		findQuery.addCriteria(Criteria.where("aadhar").is(AADHAR_INPUT_POSITIVE));
//		
//		List<Customer> queryResult = mongoTemplate.find(findQuery,Customer.class);
//		
//		Customer customer = null;
//		if(null != queryResult && !queryResult.isEmpty()) { 
//			customer = queryResult.get(0);
//		}
//		
//		when(customerRepository.findByAadhar(AADHAR_INPUT_POSITIVE))
//					.thenReturn(customer);
//		
//		assertThat(CUSTOMER_EXPECTED_DATA_POSITIVE)
//		.isEqualToComparingFieldByField(customerDataService.getCustomerByAadhar(AADHAR_INPUT_POSITIVE));
//	}
//	
//	@Test
//	public void getCustomerByAadharTest_Negative() {
//		log.info("Testing RegistryService getCustomerByAadhar() for negative usecase");
//
//		Query findQuery = new Query();
//		
//		findQuery.addCriteria(Criteria.where("aadhar").is(AADHAR_INPUT_NEGATIVE));
//		
//		List<Customer> queryResult = mongoTemplate.find(findQuery,Customer.class);
//		
//		Customer customer = null;
//		if(null != queryResult && !queryResult.isEmpty()) { 
//			customer = queryResult.get(0);
//		}
//		
//		when(customerRepository.findByPhone(AADHAR_INPUT_NEGATIVE))
//					.thenReturn(customer);
//		
//		assertEquals(CUSTOMER_EXPECTED_DATA_NEGETIVE, customerDataService.getCustomerByAadhar(AADHAR_INPUT_NEGATIVE));
//	}
//
//	@Test
//	public void getCustomerByPanTest_Positive() {
//		log.info("Testing RegistryService getCustomerByPan() for positive usecase");
//		
//		Query findQuery = new Query();
//		
//		findQuery.addCriteria(Criteria.where("pan").is(PAN_INPUT_POSITIVE));
//		
//		List<Customer> queryResult = mongoTemplate.find(findQuery,Customer.class);
//		
//		Customer customer = null;
//		
//		if(null != queryResult && !queryResult.isEmpty()) { 
//			customer = queryResult.get(0);
//		}
//		
//		when(customerRepository.findByPan(PAN_INPUT_POSITIVE)).thenReturn(customer);
//		
//		assertThat(CUSTOMER_EXPECTED_DATA_POSITIVE).isEqualToComparingFieldByField(customerDataService.getCustomerByPan(PAN_INPUT_POSITIVE));
//	}
//	
//	@Test
//	public void getCustomerByPanTest_Negative() {
//		log.info("Testing RegistryService getCustomerByPan() for negative usecase");
//		
//		Query findQuery = new Query();
//		
//		findQuery.addCriteria(Criteria.where("pan").is(PAN_INPUT_NEGATIVE));
//		
//		List<Customer> queryResult = mongoTemplate.find(findQuery,Customer.class);
//		
//		Customer customer = null;
//		if(null != queryResult && !queryResult.isEmpty()) { 
//			customer = queryResult.get(0);
//		}
//		
//		when(customerRepository.findByPhone(PAN_INPUT_NEGATIVE)).thenReturn(customer);
//		
//		assertEquals(CUSTOMER_EXPECTED_DATA_NEGETIVE, customerDataService.getCustomerByPan(PAN_INPUT_NEGATIVE));
//	}
//	
//	@Test
//	public void getCustomerByEmailTest_Positive() {
//		log.info("Testing RegistryService getCustomerByEmail() for positive usecase");
//		
//		Query findQuery = new Query();
//		
//		findQuery.addCriteria(Criteria.where("email").is(EMAIL_INPUT_POSITIVE));
//		
//		List<Customer> queryResult = mongoTemplate.find(findQuery,Customer.class);
//		
//		Customer customer = null;
//		if(null != queryResult && !queryResult.isEmpty()) { 
//			customer = queryResult.get(0);
//		}
//		
//		when(customerRepository.findByEmail(EMAIL_INPUT_POSITIVE)).thenReturn(customer);
//		
//		assertThat(CUSTOMER_EXPECTED_DATA_POSITIVE).isEqualToComparingFieldByField(customerDataService.getCustomerByEmail(EMAIL_INPUT_POSITIVE));
//	}
//	
//	@Test
//	public void getCustomerByEmailTest_Negative() {
//		log.info("Testing RegistryService getCustomerByEmail() for negative usecase");
//		
//		Query findQuery = new Query();
//		
//		findQuery.addCriteria(Criteria.where("email").is(EMAIL_INPUT_NEGATIVE));
//		
//		List<Customer> queryResult = mongoTemplate.find(findQuery,Customer.class);
//		
//		Customer customer = null;
//		if(null != queryResult && !queryResult.isEmpty()) { 
//			customer = queryResult.get(0);
//		}
//		
//		when(customerRepository.findByPhone(EMAIL_INPUT_NEGATIVE))
//					.thenReturn(customer);
//		
//		assertEquals(CUSTOMER_EXPECTED_DATA_NEGETIVE, customerDataService.getCustomerByEmail(EMAIL_INPUT_NEGATIVE));
//	}
//}
