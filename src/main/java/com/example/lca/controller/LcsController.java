package com.example.lca.controller;

import com.example.lca.models.LcsRequest;
import com.example.lca.models.LcsResponse;
import com.example.lca.service.LcsService;
import com.example.lca.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LcsController {
    LcsService lcsService;
    RequestValidator requestValidator;

    @Autowired
    public LcsController(LcsService lcsService, RequestValidator requestValidator) {
        this.lcsService = lcsService;
        this.requestValidator = requestValidator;
    }

    @PostMapping("/lcs")
    public ResponseEntity<LcsResponse> getLca(@RequestBody LcsRequest lcsRequest) throws Exception {

        requestValidator.validateRequest(lcsRequest);
        return lcsService.getLca(lcsRequest);
    }
}
