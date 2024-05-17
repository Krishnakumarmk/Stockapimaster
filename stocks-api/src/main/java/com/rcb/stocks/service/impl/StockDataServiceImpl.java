package com.rcb.stocks.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rcb.stocks.dto.StockDataResource;
import com.rcb.stocks.entity.StockData;
import com.rcb.stocks.repositories.StockDataRepository;
import com.rcb.stocks.service.IStockDataService;
import com.rcb.stocks.utilities.CsvFileMapper;

@Service
@Transactional
public class StockDataServiceImpl implements IStockDataService {

	private StockDataRepository stockDataRepository;

	@Async
	public List<StockDataResource> getStockData(String stock) {
		return stockDataRepository.findAllByStock(stock).stream().map(StockDataResource::new)
				.collect(Collectors.toList());
	}

	@Async
	public StockDataResource addMissingStockData(String clientId, StockDataResource stockDataResource) {
		StockData missingStockData = new StockData(stockDataResource);
		return new StockDataResource(stockDataRepository.save(missingStockData));
	}

	@Async
	public List<StockData> bulkInsertStockData(String clientld, MultipartFile stockDataFile) throws Exception {
		Iterable<StockData> stockDataRet = null;
		try {
			List<StockData> stockDataToInsert = CsvFileMapper.read(StockData.class, stockDataFile.getInputStream());

			if (stockDataToInsert.size() > 0) {
				stockDataRet = stockDataRepository.saveAll(stockDataToInsert);
			}
		} catch (Exception e) {
			throw new Exception(
					"An Internal error occurs during bulkInsertStockData operatio" + e.getLocalizedMessage());
		}
		return (List<StockData>) stockDataRet;

	}

}