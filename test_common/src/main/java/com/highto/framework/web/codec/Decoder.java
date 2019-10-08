package com.highto.framework.web.codec;

import java.io.IOException;

/**
 * Description:对于Decoder的思考：要不要泛型类？
 * User: tan
 * DateTime: 2018/1/31 13:37
 */
interface Decoder<R> {
     R decode(Object message) throws IOException;
}
