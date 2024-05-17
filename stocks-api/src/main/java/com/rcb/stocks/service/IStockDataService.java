package com.rcb.stocks.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rcb.stocks.dto.StockDataResource;
import com.rcb.stocks.entity.StockData;
import com.rcb.stocks.repositories.StockDataRepository;
import com.rcb.stocks.utilities.CsvFileMapper;

@Service
public interface IStockDataService {
	List<StockDataResource> getStockData(String stock);
	StockDataResource addMissingStockData(String clientId, StockDataResource stockDataResource);
	List<StockData> bulkInsertStockData(String clientld, MultipartFile stockDataFile) throws Exception;
}