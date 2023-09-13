package com.entrego.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrego.dtos.RequestDTO;
import com.entrego.entity.Request;
import com.entrego.services.RequestService;

@RestController
@RequestMapping("request")
public class RequestController {

	@Autowired
	private RequestService requestService;
	
	@PostMapping
	public Request createRequest(RequestDTO data) {
		Request request = new Request(data);
		return request;
	}
	
}
