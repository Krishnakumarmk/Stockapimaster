package com.rcb.stocks.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rcb.stocks.dto.StockDataResource;
import com.rcb.stocks.service.impl.StockDataServiceImpl;

@RestController
@RequestMapping("/api-stockdata")
public class StockDataController {

	@Autowired
	StockDataServiceImpl stockDataService;

	@GetMapping("/{stock}")
	public ResponseEntity<List<StockDataResource>> getStockData(@RequestParam("x-client_id") String clientid,
			@PathVariable String stock) {
		List<StockDataResource> stockDataResourceList = stockDataService.getStockData(stock);
		return new ResponseEntity<>(stockDataResourceList, HttpStatus.OK);
	}

	@PostMapping("/missing-stock")
	public ResponseEntity<StockDataResource> addMissingStockData(@RequestParam("x-client_id") String clientid,
			@Valid @RequestBody StockDataResource stockDataResource) {
		StockDataResource addedStockDataResource = stockDataService.addMissingStockData(clientid, stockDataResource);
		return new ResponseEntity<>(addedStockDataResource, HttpStatus.CREATED);
	}

	//During testing the below method in Postman, user has to select the csv file stored in the code repository
	//While generating the request using Post, create a input parameter key name as file and select the files from the repository
	//The files are located in the application code base resources file folder with file name stockdata 
	@PostMapping(value = "/bulk-insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> bulkInsertStockData(@RequestHeader("x-client_id") String clientId,
			@RequestParam(value = "file", required = true) MultipartFile stockDataFile) throws Exception {
		stockDataService.bulkInsertStockData(clientId, stockDataFile);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
