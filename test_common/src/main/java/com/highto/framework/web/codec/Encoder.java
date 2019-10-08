package com.highto.framework.web.codec;

import java.io.IOException;

/**
 * Description:
 * User: tan
 * DateTime: 2018/1/31 13:37
 */
public interface Encoder<T> {
    Object encode(T message) throws IOException;
}
