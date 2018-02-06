package com.nguyen.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.nguyen.common.ServerResponse;
import com.nguyen.dao.ProductMapper;
import com.nguyen.dto.response.ProductResponse;
import com.nguyen.pojo.Product;
import com.nguyen.service.IProductService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RWM
 * @date 2018/2/2
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null){
            if (null == product.getCategoryId()){
                return ServerResponse.createByErrorMessage("分类ID不能为空");
            }
            if (StringUtils.isBlank(product.getName())){
                return ServerResponse.createByErrorMessage("商品名称不能为空");
            }
            if (null == product.getPrice()){
                return ServerResponse.createByErrorMessage("商品价格不能为空");
            }
            if (null == product.getStock()){
                return ServerResponse.createByErrorMessage("商品库存不能为空");
            }
            if (StringUtils.isNotBlank(product.getSubImages())){
                List<String> subImages = Splitter.on(",").splitToList(product.getSubImages());
                if (!CollectionUtils.isEmpty(subImages)){
                    product.setMainImage(subImages.get(0));
                }
            }
            if (product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0){
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                return ServerResponse.createByErrorMessage("更新产品失败");
            }else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0){
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                return ServerResponse.createByErrorMessage("新增产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServerResponse setProductStatus(Integer productId, Integer status) {
        if (productId == null){
            return ServerResponse.createByErrorMessage("产品ID不能为空");
        }
        if (status == null){
            return ServerResponse.createByErrorMessage("产品销售状态不能为空");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

    @Override
    public ServerResponse getProductDetail(Integer productId) {
        if (productId == null){
            return ServerResponse.createByErrorMessage("产品ID不能为空");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product != null){
            return ServerResponse.createBySuccess(product.response());
        }
        return ServerResponse.createByErrorMessage("产品已下架或已删除");
    }

    @Override
    public ServerResponse getProductList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductResponse> productResponseList = productList.stream().map(product -> product.response()).collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productResponseList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);
        List<ProductResponse> productResponseList = productList.stream().map(product -> product.response()).collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productResponseList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
