package io.akash.accountaggregator.fipsimulator.business.services;
//package io.akash.accountaggregator.fipsimulator.business.services;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.env.Environment;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import io.akash.accountaggregator.fipsimulator.model.dtos.FIPDetailsDTO;
//
//import lombok.extern.slf4j.Slf4j;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class FIPValidatorTest {
//
//	@Autowired
//	private Environment env;
//
//	@Autowired
//	FIPValidator fipValidator;
//
//	private FIPDetailsDTO TEST_DATA_FIP_DETAILS_VALID;
//	private FIPDetailsDTO TEST_DATA_FIP_DETAILS_INVALID;
//
//	private Boolean EXPECTED_DATA_POSITIVE;
//	private Boolean EXPECTED_DATA_NEGETIVE;
//
//	@Before
//	public void init() {
//
//		FIPDetailsDTO sampleFipDetailsValid = new FIPDetailsDTO();
//		sampleFipDetailsValid.setId(env.getProperty("fip.id"));
//		sampleFipDetailsValid.setName(env.getProperty("fip.name"));
//
//		TEST_DATA_FIP_DETAILS_VALID = sampleFipDetailsValid;
//
//		FIPDetailsDTO sampleFipDetailsInvalid = new FIPDetailsDTO();
//		sampleFipDetailsInvalid.setId("abc");
//		sampleFipDetailsInvalid.setName("ABC Bank");
//
//		TEST_DATA_FIP_DETAILS_INVALID = sampleFipDetailsInvalid;
//
//		EXPECTED_DATA_POSITIVE = true;
//		EXPECTED_DATA_NEGETIVE = false;
//	}
//
//	@Test
//	public void areFIPDetailsValidTest_ValidFIPDetails() {
//		log.info("Testing areFIPDetailsValid() method of FIPValidator");
//		assertEquals(EXPECTED_DATA_POSITIVE, fipValidator.areFIPDetailsValid(TEST_DATA_FIP_DETAILS_VALID));
//	}
//
//	@Test
//	public void areFIPDetailsValidTest_InvalidFIPDetails() {
//		log.info("Testing areFIPDetailsValid() method of FIPValidator");
//		assertEquals(EXPECTED_DATA_NEGETIVE, fipValidator.areFIPDetailsValid(TEST_DATA_FIP_DETAILS_INVALID));
//	}
//
//}
