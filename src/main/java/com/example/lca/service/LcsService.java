package com.example.lca.service;

import com.example.lca.models.LcsRequest;
import com.example.lca.models.LcsResponse;
import com.example.lca.models.StringData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LcsService {
    public ResponseEntity<LcsResponse> getLca(LcsRequest lcsRequest) throws Exception {

        List<String> stringList = lcsRequest
                .getSetOfStrings()
                .stream()
                .map(sd -> sd.getValue())
                .collect(Collectors.toList());

        Set<String> out = new TreeSet<>();
        List<String> subStrings = generateSubStrings(stringList.get(0));
        int max = 0;
        for (String s : subStrings) {
            boolean b = false;

            for (int i = 1; i < stringList.size(); i++) {
                if (!stringList.get(i).contains(s)) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                if (s.length() > max) {
                    out = new TreeSet<>();
                    max = s.length();
                    out.add(s);
                } else if (s.length() == max) {
                    max = s.length();
                    out.add(s);
                }
            }
        }
        return outputMapper(out);
    }

    private ResponseEntity<LcsResponse> outputMapper(Set<String> out) {
        LcsResponse lcsResponse = new LcsResponse();
        List<StringData> stringDataList = out.stream().map(st -> StringData.builder().value(st).build()).collect(Collectors.toList());
        lcsResponse.setLcs(stringDataList);
        return new ResponseEntity<>(lcsResponse, HttpStatus.OK);
    }

    private List<String> generateSubStrings(String s) {
        List<String> subStringsList = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            for (int k = i + 1; k <= s.length(); k++) {
                subStringsList.add(s.substring(i, k));
            }
        }
        return subStringsList;
    }
}
