package com.nguyen.shop.mapper;

import com.nguyen.shop.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> selectByOrderNoUserId(@Param("orderNo") Long orderNo, @Param("userId") Integer userId);

    void batchInsert(List<OrderItem> orderItemList);

    List<OrderItem> getByOrderNoUserId(Long orderNo, Integer userId);

    List<OrderItem> getByOrderNo(Long orderNo);
}