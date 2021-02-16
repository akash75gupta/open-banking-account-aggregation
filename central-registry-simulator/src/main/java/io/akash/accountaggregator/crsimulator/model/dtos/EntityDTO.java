package io.akash.accountaggregator.crsimulator.model.dtos;

import java.util.List;

import lombok.Data;

@Data
public class EntityDTO {
	private EntityTypeDTO type;
	private String id;
	private String name;
	private List<String> phones;
	private List<String> emails;
	private List<ApiDTO> apis;
	private List<String> docs;
	private List<KeyDTO> keys;
	public EntityTypeDTO getType() {
		return type;
	}
	public void setType(EntityTypeDTO type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPhones() {
		return phones;
	}
	public void setPhones(List<String> phones) {
		this.phones = phones;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	public List<ApiDTO> getApis() {
		return apis;
	}
	public void setApis(List<ApiDTO> apis) {
		this.apis = apis;
	}
	public List<String> getDocs() {
		return docs;
	}
	public void setDocs(List<String> docs) {
		this.docs = docs;
	}
	public List<KeyDTO> getKeys() {
		return keys;
	}
	public void setKeys(List<KeyDTO> keys) {
		this.keys = keys;
	}
}
