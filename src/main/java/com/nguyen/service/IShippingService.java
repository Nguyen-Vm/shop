package com.nguyen.service;

import com.github.pagehelper.PageInfo;
import com.nguyen.common.ServerResponse;
import com.nguyen.pojo.Shipping; /**
 * @author RWM
 * @date 2018/2/8
 */
public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse del(Integer userId, Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
