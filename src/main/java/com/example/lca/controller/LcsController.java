package com.example.lca.controller;

import com.example.lca.models.LcsRequest;
import com.example.lca.models.LcsResponse;
import com.example.lca.service.LcsService;
import com.example.lca.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LcsController {
    private final LcsService lcsService;
    private final RequestValidator requestValidator;

    @PostMapping("/lcs")
    public LcsResponse getLca(@RequestBody(required = false) LcsRequest lcsRequest){

        requestValidator.validateRequest(lcsRequest);
        return lcsService.getLca(lcsRequest);
    }
}
