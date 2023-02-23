package com.example.lca.service;

import com.example.lca.models.LcsRequest;
import com.example.lca.models.LcsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LcsService {
    public ResponseEntity<LcsResponse> getLca(LcsRequest lcsRequest) throws Exception {

        throw new Exception("Something");
    }
}
