package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.jms.core.JmsTemplate;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/product")
public class ProductServiceController {
	
	@RequestMapping(value = "/health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMessage() {

		ResponseEntity<String> response = null;  
		response = new ResponseEntity<>("ProductService is live", HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ERPProductMap> processOrder(@Parameter(description="Order") @RequestBody Order order) {

		ResponseEntity<ERPProductMap> response = null;
		
		ERPProductMap myMap  = new ERPProductMap(new ArrayList<Item>(), new ArrayList<Item>());
		
		Map<String, String> master = new HashMap<>();
		master.put("860322","MP1");
		master.put("861304","MP1");
		master.put("DSX200S11","PRD");
		master.put("1054260B","PRD");
		
		if(order.getItems() != null) {
			List<Item> items = order.getItems();
			for (int i = 0; i < items.size(); i++) {
				Item item = items.get(i);
				if(master.get(item.getCode()).equals("PRD")) {
					myMap.getPrdProducts().add(item);
				}else {
					myMap.getMp1Products().add(item);
				}
			}
		}
		
		response = new ResponseEntity<ERPProductMap>(myMap, HttpStatus.OK);
		
		return response;
	}

}