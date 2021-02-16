package io.akash.accountaggregator.fipsimulator.data.interfaces;

import java.util.List;

import io.akash.accountaggregator.fipsimulator.model.entities.Account;

public interface IAccountDataService {
	List<Account> getAccountsByCustomer(String customerUsername);
}
