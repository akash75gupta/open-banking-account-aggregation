package io.akash.accountaggregator.fipsimulator.business.services;
//package io.akash.accountaggregator.fipsimulator.business.services;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import io.akash.accountaggregator.fipsimulator.data.services.CustomerDataService;
//import io.akash.accountaggregator.fipsimulator.model.dtos.IdentifierDTO;
//import io.akash.accountaggregator.fipsimulator.model.entities.Customer;
//import io.akash.accountaggregator.fipsimulator.model.enums.IdentifierCategory;
//import io.akash.accountaggregator.fipsimulator.model.enums.IdentifierType;
//import io.akash.accountaggregator.fipsimulator.utilities.FileUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
//@RunWith(MockitoJUnitRunner.Silent.class) 
//@Slf4j
//public class CustomerValidatorTest {
//	
//	private static Boolean EXPECTED_DATA_POSITIVE;
//	private static Boolean EXPECTED_DATA_NEGETIVE;
//	
//	private static final IdentifierDTO TEST_DATA_VALID_MOBILE_IDENTIFIER;
//	private static final IdentifierDTO TEST_DATA_INVALID_MOBILE_IDENTIFIER;
//	private static final IdentifierDTO TEST_DATA_STRONG_IDENTIFIER;
//	private static final IdentifierDTO TEST_DATA_WEAK_IDENTIFIER;
//	private static final List<IdentifierDTO> TEST_DATA_IDENTIFIERS;
//	
//	private static Set<Customer> TEST_CUSTOMERS;
//	
//	private static final String CUSTOMERS_FILE_NAME = "customers.json"; 
//	
//	/***
//	 *************** Initialization Block ***************
//	 *
//	 * All the mock test data are created and initialized
//	 * Testing is performed against these data
//	 * 
//	 ****/
//	
//	static {
//		EXPECTED_DATA_POSITIVE = true;
//		EXPECTED_DATA_NEGETIVE = false;
//		
//		IdentifierDTO identifierMobileValid = new IdentifierDTO();
//		identifierMobileValid.setCategory(IdentifierCategory.STRONG);
//		identifierMobileValid.setType(IdentifierType.MOBILE);
//		identifierMobileValid.setValue("6372347018");
//		
//		TEST_DATA_VALID_MOBILE_IDENTIFIER = identifierMobileValid;
//		TEST_DATA_STRONG_IDENTIFIER = identifierMobileValid;
//		
//		//Creating a mobile identifier for testing negative use case
//		IdentifierDTO identifierMobileInvalid = new IdentifierDTO();
//		identifierMobileInvalid.setCategory(IdentifierCategory.STRONG);
//		identifierMobileInvalid.setType(IdentifierType.MOBILE);
//		identifierMobileInvalid.setValue("10000000001");
//		
//		TEST_DATA_INVALID_MOBILE_IDENTIFIER = identifierMobileInvalid;
//	
//		//Creating a weak identifier for testing
//		IdentifierDTO identifierWeak = new IdentifierDTO();
//		identifierWeak.setCategory(IdentifierCategory.WEAK);
//		identifierWeak.setType(IdentifierType.PAN);
//		identifierWeak.setValue("EWIPG3516X");
//	
//		TEST_DATA_WEAK_IDENTIFIER = identifierWeak;
//		
//		//Creating a list of identifiers for testing
//		TEST_DATA_IDENTIFIERS = new ArrayList<IdentifierDTO>();
//		
//		TEST_DATA_IDENTIFIERS.add(TEST_DATA_WEAK_IDENTIFIER);
//		TEST_DATA_IDENTIFIERS.add(TEST_DATA_STRONG_IDENTIFIER);
//	}
//
//	@Mock
//	CustomerDataService customerDataService;
//	
//	@InjectMocks
//	CustomerValidator customerValidator;
//	
//	@Before
//	public void init() {
//		log.info("Loading all the necessary test data from files");	
//		
//		String customerData = FileUtil.fetchTestDataForClass(CUSTOMERS_FILE_NAME, 
//														CustomerValidatorTest.class.getSimpleName());
//		
//		Type listType = new TypeToken<Set<Customer>>() {}.getType();
//			
//		TEST_CUSTOMERS= new Gson().fromJson(customerData, listType);
//	}
//	
//	@Test
//	public void isIdentifierValidTest_ValidMobileIdentifier() {
//		log.info("Testing areIdentifiersValid() method of CustomerValidator positive case "
//				+ "with a valid mobile number");	
//		
//		Customer testCustomer = TEST_CUSTOMERS.iterator().next();
//		
//		when(customerDataService.getCustomerByMobile(TEST_DATA_VALID_MOBILE_IDENTIFIER.getValue()))
//		.thenReturn(testCustomer);
//		
//		assertEquals(EXPECTED_DATA_POSITIVE, customerValidator.isIdentifierValid(TEST_DATA_VALID_MOBILE_IDENTIFIER));
//	}
//
//	@Test
//	public void isIdentifierValidTest_InvalidMobileIdentifier() {
//		log.info("Testing isIdentifiersValid() method of CustomerValidator positive case "
//				+ "with an invalid mobile number");
//		
//		
//		when(customerDataService.getCustomerByMobile(TEST_DATA_INVALID_MOBILE_IDENTIFIER.getValue()))
//		.thenReturn(null);
//		
//		assertEquals(EXPECTED_DATA_NEGETIVE, customerValidator.isIdentifierValid(TEST_DATA_INVALID_MOBILE_IDENTIFIER));
//	}
//	
//	@Test
//	public void areIdentifiersValidTest_ValidIdentifiers() {
//		log.info("Testing areIdentifiersValid() method of CustomerValidator positive case");
//		
//		Customer testCustomer = TEST_CUSTOMERS.iterator().next();
//		
//		when(customerDataService.getCustomerByMobile(TEST_DATA_STRONG_IDENTIFIER.getValue()))
//		.thenReturn(testCustomer);
//		
//		when(customerDataService.getCustomerByMobile(TEST_DATA_WEAK_IDENTIFIER.getValue()))
//		.thenReturn(testCustomer);
//		
//		assertEquals(EXPECTED_DATA_POSITIVE, customerValidator.areIdentifiersValid(TEST_DATA_IDENTIFIERS));
//	}
//
//	@Test
//	public void isIdentifierStrongTest_PositiveCase() {
//		log.info("Testing isIdentifierStrong() method of CustomerValidator positive case");
//		
//		Customer testCustomer = TEST_CUSTOMERS.iterator().next();
//
//		when(customerDataService.getCustomerByMobile(TEST_DATA_VALID_MOBILE_IDENTIFIER.getValue()))
//		.thenReturn(testCustomer);
//		
//		assertEquals(EXPECTED_DATA_POSITIVE, customerValidator.isIdentifierStrong(TEST_DATA_STRONG_IDENTIFIER));
//	}
//
//	@Test
//	public void isIdentifierStrongTest_NegativeCase() {
//		log.info("Testing isIdentifierStrong() method of CustomerValidator negative case");
//		
//		Customer testCustomer = TEST_CUSTOMERS.iterator().next();
//
//		when(customerDataService.getCustomerByMobile(TEST_DATA_INVALID_MOBILE_IDENTIFIER.getValue()))
//		.thenReturn(testCustomer);
//		
//		assertEquals(EXPECTED_DATA_NEGETIVE, customerValidator.isIdentifierStrong(TEST_DATA_WEAK_IDENTIFIER));
//	}
//	
//	public void isIdentifierWeakTest() {
//		//Ancillary identifiers are not used right now. 
//		//TODO:Test cases need to be written when in use.
//	}
//
//	public void isIdentifierAncillaryTest(IdentifierDTO identifier) {
//		//Ancillary identifiers are not used right now. 
//		//TODO:Test cases need to be written when in use.
//	}
//
//	public void isCustomerValidTest() {
//		//isCustomerValid() in CustomerValidator is not used right now. 
//		//TODO:Test cases need to be written when in use.
//	}
//}
