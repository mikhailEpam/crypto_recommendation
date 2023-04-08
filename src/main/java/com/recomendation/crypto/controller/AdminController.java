package com.recomendation.crypto.controller;

import com.recomendation.crypto.model.request.ExclusionCurrencyRequest;
import com.recomendation.crypto.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/admin")
//TODO should be secured
public class AdminController {

    private AdminService adminService;

    @GetMapping(value = "/currency/exclusion")
    public Collection<String> getAllExcludedCurrencies() {
        return adminService.getExcludedCurrencies();
    }

    @PostMapping(value = "/currency/exclusion")
    public void addCurrencyToExclusion(@RequestBody ExclusionCurrencyRequest exclusionRequest) {
        adminService.addCurrencyToExclusion(exclusionRequest);
    }

    @DeleteMapping(value = "/currency/exclusion")
    public void removeCurrencyFromExclusion(@RequestBody ExclusionCurrencyRequest exclusionRequest) {
        adminService.removeCurrencyFromExclusion(exclusionRequest);
    }

}
