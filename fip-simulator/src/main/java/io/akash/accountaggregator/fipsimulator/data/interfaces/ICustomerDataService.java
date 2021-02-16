package io.akash.accountaggregator.fipsimulator.data.interfaces;

import io.akash.accountaggregator.fipsimulator.model.entities.Customer;

public interface ICustomerDataService {

	public Customer getCustomerByMobile(String mobile);

	public Customer getCustomerById(String id);

	public Customer getCustomerByAadhar(String aadhar);

	public Customer getCustomerByPan(String pan);

	public Customer getCustomerByEmail(String email);

}
