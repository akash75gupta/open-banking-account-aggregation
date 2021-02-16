package io.akash.accountaggregator.fipsimulator.model.entities;

import java.util.List;

public class Banks {
    private List<Bank> banks;

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}

	public List<Bank> getBanks() {
		return banks;
	}
}