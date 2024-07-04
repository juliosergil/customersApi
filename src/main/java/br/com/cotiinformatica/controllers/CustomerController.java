package br.com.cotiinformatica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.CustomerRequest;
import br.com.cotiinformatica.dtos.CustomerResponse;
import br.com.cotiinformatica.services.CustomerService;

@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {
	
	@Autowired CustomerService customerDomainService;

	@PostMapping("create")
	public ResponseEntity<CustomerResponse> post(@RequestBody CustomerRequest request) throws Exception {
		return ResponseEntity.status(201).body(customerDomainService.create(request));
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<CustomerResponse> put(@PathVariable("id") Long id, @RequestBody CustomerRequest request) throws Exception {
		return ResponseEntity.status(200).body(customerDomainService.update(id, request));
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<CustomerResponse> delete(@PathVariable("id") Long id) throws Exception {
		return ResponseEntity.status(200).body(customerDomainService.delete(id));
	}
	
	@GetMapping("list")
	public ResponseEntity<List<CustomerResponse>> getAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy
			) throws Exception {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		
		return ResponseEntity.status(200).body(customerDomainService.getAll(pageable));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CustomerResponse> getById(@PathVariable("id") Long id) throws Exception {
		return ResponseEntity.status(200).body(customerDomainService.getById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getAll() throws Exception{
		return ResponseEntity.status(200).body(customerDomainService.getAll());
	}
}
