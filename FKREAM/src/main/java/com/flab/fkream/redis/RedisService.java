package com.flab.fkream.redis;

import com.flab.fkream.error.exception.GenerateAddressKeyException;
import javax.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisService {

    @Resource(name = "redisValueOperations")
    private ValueOperations<String, Object> valueOps;

    public Long getAddressId() {
        Long addressId = 0L;
        try {
            addressId = valueOps.increment("spring:redis:getAddressId", 1L);
        } catch (Exception e) {
            log.error(e.toString());
            throw new GenerateAddressKeyException();
        }
        return addressId;
    }

    public void initAddressId() {
        valueOps.set("spring:redis:getAddressId", 0L);
    }
}
