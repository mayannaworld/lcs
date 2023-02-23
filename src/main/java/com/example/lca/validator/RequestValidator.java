package com.example.lca.validator;

import com.example.lca.Exception.RequestException;
import com.example.lca.models.LcsRequest;
import com.example.lca.models.StringData;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class RequestValidator {
    public void validateRequest(LcsRequest lcsRequest) {
        if(Objects.isNull(lcsRequest)){
            throw new RequestException("The Request is null/Invalid");
        }
        if(CollectionUtils.isEmpty(lcsRequest.getSetOfStrings())){
            throw new RequestException("Request doesnt contain the required strings");
        }
        Set stringsSet = new HashSet();

        for (StringData sd : lcsRequest.getSetOfStrings()){
            if(!stringsSet.add(sd.getValue())){
                throw new RequestException("The  strings supplied must be unique");
            }
        }
    }
}
