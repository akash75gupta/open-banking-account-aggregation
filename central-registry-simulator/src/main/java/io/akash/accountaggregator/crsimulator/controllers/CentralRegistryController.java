package io.akash.accountaggregator.crsimulator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.akash.accountaggregator.crsimulator.model.dtos.RegistryResponseDTO;
import io.akash.accountaggregator.crsimulator.services.RegistryService;

@CrossOrigin
@RestController
public class CentralRegistryController {
	
	@Autowired
	RegistryService registryService;
	
	@RequestMapping("/registry")
	public RegistryResponseDTO getRegistry() {
		return registryService.getRegistry();
	}
}
