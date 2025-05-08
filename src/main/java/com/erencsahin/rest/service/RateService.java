package com.erencsahin.rest.service;

import com.erencsahin.rest.generator.RateGenerator;
import com.erencsahin.rest.dto.RateData;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class RateService {
    private final Map<String, RateGenerator> rateGeneratorMap = new ConcurrentHashMap<>();
    private final Map<String, RateData> currentRates = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final List<String> symbols = Arrays.asList("PF2_USDTRY", "PF2_EURUSD","PF2_GBPUSD");

    @PostConstruct
    public void init(){
        rateGeneratorMap.put("PF2_USDTRY", new RateGenerator(38.41904, 38.41904));
        rateGeneratorMap.put("PF2_EURUSD", new RateGenerator(1.13538, 1.13538));
        rateGeneratorMap.put("PF2_GBPUSD", new RateGenerator(1.33068, 1.33068));

        currentRates.put("PF2_USDTRY", new RateData("PF2_USDTRY", 38.41904, 38.41904, LocalDateTime.now().toString()));
        currentRates.put("PF2_EURUSD", new RateData("PF2_EURTRY", 1.13538, 1.13538, LocalDateTime.now().toString()));
        currentRates.put("PF2_GBPUSD", new RateData("PF2_GBPTRY", 1.33068, 1.33068, LocalDateTime.now().toString()));


        long updateInterval = 1000;
        scheduler.scheduleAtFixedRate(this::updateRates, updateInterval, updateInterval, TimeUnit.MILLISECONDS);
    }

    private void updateRates() {
        for (String symbol : symbols) {
            RateGenerator generator = rateGeneratorMap.get(symbol);
            double newAsk = generator.getNextAskValue();
            double newBid = generator.getNextBidValue();
            RateData data = new RateData(symbol, newAsk, newBid, LocalDateTime.now().toString());
            currentRates.put(symbol, data);
        }
    }

    public RateData getRate(String symbol){
        RateData data = currentRates.get(symbol);
        return new RateData(data.getSymbol(), data.getAsk(), data.getBid(), data.getTimestamp());
    }

    public boolean hasSymbol(String symbol){
        return currentRates.containsKey(symbol);
    }
}
