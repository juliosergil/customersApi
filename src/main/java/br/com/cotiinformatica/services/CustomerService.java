package br.com.cotiinformatica.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.CustomerRequest;
import br.com.cotiinformatica.dtos.CustomerResponse;
import br.com.cotiinformatica.entities.Customer;
import br.com.cotiinformatica.repositories.CustomerRepository;

@Service
public class CustomerService {

	@Autowired CustomerRepository customerRepository;
	@Autowired ModelMapper modelMapper;
	
	public CustomerResponse create(CustomerRequest request) throws Exception {
		
		Customer customer = modelMapper.map(request, Customer.class);
		customerRepository.save(customer);
		
		return modelMapper.map(customer, CustomerResponse.class);
	}
	
	public CustomerResponse update(Long id, CustomerRequest request) throws Exception {
		
		Customer customer = customerRepository
				.findById(id)
				.orElseThrow(() -> new Exception("Customer not found!"));
		
		customer.setName(request.getName());
		customer.setEmail(request.getEmail());
		customer.setPhone(request.getPhone());
		
		customerRepository.save(customer);
		
		return modelMapper.map(customer, CustomerResponse.class);
	}
	
	public CustomerResponse delete(Long id) throws Exception {
		
		Customer customer = customerRepository
				.findById(id)
				.orElseThrow(() -> new Exception("Customer not found!"));		
		
		customerRepository.delete(customer);
		
		return modelMapper.map(customer, CustomerResponse.class);
	}
	
	public CustomerResponse getById(Long id) throws Exception {
		
		Customer customer = customerRepository
				.findById(id)
				.orElseThrow(() -> new Exception("Customer not found!"));		
		
		return modelMapper.map(customer, CustomerResponse.class);
	}
	
	public List<CustomerResponse> getAll(Pageable pageable){
		
		Page<Customer> customers = customerRepository.findAll(pageable);

		List<CustomerResponse> response = customers
				.stream()
				.map(customer -> modelMapper.map(customer, CustomerResponse.class))
				.collect(Collectors.toList());
		
		return response;	
	}
	
	public List<CustomerResponse> getAll() throws Exception {
		
		List<CustomerResponse> response = customerRepository.findAll()
				.stream()
				.map(todo -> modelMapper.map(todo, CustomerResponse.class))
				.collect(Collectors.toList());
		return response;
	}
}
