package io.akash.openbanking.accountaggregator.accountdiscovery.repository;

import java.util.List;

public interface IRedisRepository {

	void save(String key, Object value);

	Long getListSize(String key);

	void removeListElement(String key, Object fip);

	List<? extends Object> getValueByKey(String key);

	Object getByIndex(String key, Integer index);
}
