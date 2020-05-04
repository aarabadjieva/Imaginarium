package project.imaginarium.service.services.user.impl;

import project.imaginarium.service.services.user.HashingService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class HashingServiceImpl implements HashingService {
    @Override
    public String hash(String url) {
        return DigestUtils.sha3_256Hex(url);
    }
}
