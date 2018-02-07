package com.nguyen.service;

import com.nguyen.common.ServerResponse;

import java.util.List;

/**
 * @author RWM
 * @date 2018/1/29
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
