package io.akash.accountaggregator.fipsimulator.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.akash.accountaggregator.fipsimulator.data.interfaces.IAccountDataService;
import io.akash.accountaggregator.fipsimulator.data.repositories.AccountRepository;
import io.akash.accountaggregator.fipsimulator.exception.exceptions.CustomException;
import io.akash.accountaggregator.fipsimulator.model.entities.Account;

@Service
public class AccountDataService implements IAccountDataService{
	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<Account> getAccountsByCustomer(String customerUsername) {
		List<Account> accounts = accountRepository.findByOwnersId(customerUsername);
		
		if(null == accounts) {
			throw new CustomException(HttpStatus.NOT_FOUND,
					"No accounts found for the customer: "+customerUsername);
		}
	
		return accounts;
	}
}
