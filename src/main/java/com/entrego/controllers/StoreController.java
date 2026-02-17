package com.entrego.controllers;

import java.util.List;

import com.entrego.domain.Address;
import com.entrego.dtos.auth.*;
import com.entrego.dtos.store.request.*;
import com.entrego.dtos.store.response.*;
import com.entrego.dtos.order.*;
import com.entrego.dtos.product.*;
import com.entrego.dtos.address.*;
import com.entrego.dtos.common.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.entrego.domain.Store;
import com.entrego.services.StoreService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	private StoreService storeService;

	@PostMapping
	private StoreResponseDTO saveStore(@RequestBody RegisterStoreRequestDTO data) {
		return new StoreResponseDTO(storeService.createStore(data));
	}
	
	@GetMapping
	private List<StoreResponseDTO> allStores(){
		return this.storeService.findAllStores().stream().map(StoreResponseDTO::new).collect(Collectors.toList());
	}
	
	//@GetMapping
	//@RequestMapping("/{id}")
	//public Store findStoreById(@PathVariable String id) throws Exception {
	//	return this.storeService.findStoreById(id);
	//}
	@GetMapping
	@RequestMapping("/category")
	public List<StoreResponseDTO> findStoresByCategory(@RequestBody RequestStoreByCategory data) {
		return storeService.findStoresByCategory(data.category()).stream().map(StoreResponseDTO::new).collect(Collectors.toList());
	}

	@PutMapping
	@RequestMapping("/update-status")
	public StoreResponseDTO updateStoreStatus(@RequestBody RequestUpdateStoreStatus data) throws Exception {
		return new StoreResponseDTO(storeService.updateStoreStatus(data));
	}

	@PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@RequestMapping("update-cover/{email}")
	public ResponseEntity<ImageDTO> updateStoreCoverImage(@PathVariable String email, @RequestParam("file") MultipartFile file) throws Exception {

		String urlImage = this.storeService.updateStoreCoverImage(email, file);
		ImageDTO dto = new ImageDTO(urlImage);

		return ResponseEntity.ok(dto);

	}

	@PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@RequestMapping("update-profile/{email}")
	public ResponseEntity<ImageDTO> updateStoreProfileImage(@PathVariable String email, @RequestParam("file") MultipartFile file) throws Exception {
		String urlImage =  this.storeService.updateStoreProfileImage(email, file);
		ImageDTO dto = new ImageDTO(urlImage);

		return ResponseEntity.ok(dto);
	}

	@PutMapping
	@RequestMapping("/update/{email}")
	public StoreResponseDTO updateStore(@PathVariable String email, @RequestBody RequestUpdateStore data) throws Exception {
		return new StoreResponseDTO(this.storeService.updateStore(email, data));
	}


	@GetMapping
	@RequestMapping("/{email}")
	public StoreResponseDTO findStoreByEmail(@PathVariable String email) throws Exception {
		return new StoreResponseDTO(this.storeService.findStoreByEmail(email));
	}

	@GetMapping
	@RequestMapping("/address/{email}")
	public Address findAddressByStoreEmail(@PathVariable String email) throws Exception {
		return this.storeService.findAddressByStoreEmail(email);
	}




}
