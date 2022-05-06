package com.pseudonym.audi.service;

import com.pseudonym.audi.domain.Request;
import com.pseudonym.audi.dto.RequestFormDto;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    public static Request submit(RequestFormDto requestFormDto) {
        Request request = new Request();
        return request;
    }
}
