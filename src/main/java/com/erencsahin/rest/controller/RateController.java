package com.erencsahin.rest.controller;

import com.erencsahin.rest.response.RateData;
import com.erencsahin.rest.exception.SymbolNotFoundException;
import com.erencsahin.rest.service.RateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
public class RateController {
    private final RateService rateService;
    public RateController(RateService rateService){
        this.rateService=rateService;
    }

    @GetMapping(value = "/rates",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RateData getRate(@RequestParam("symbol") String symbol) {
        if (!rateService.hasSymbol(symbol)) {
            throw new SymbolNotFoundException("Not found: " + symbol);
        }
        return rateService.getRate(symbol);
    }
}
