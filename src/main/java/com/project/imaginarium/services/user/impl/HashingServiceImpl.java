package com.project.imaginarium.services.user.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import com.project.imaginarium.services.user.HashingService;

@Service
public class HashingServiceImpl implements HashingService {
    @Override
    public String hash(String url) {
        return DigestUtils.sha3_256Hex(url);
    }
}
