package com.nguyen.shop.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nguyen.shop.common.Const;
import com.nguyen.shop.common.ResponseCode;
import com.nguyen.shop.common.ServerResponse;
import com.nguyen.shop.dto.response.CartProductInfo;
import com.nguyen.shop.dto.response.CartResponse;
import com.nguyen.shop.mapper.CartMapper;
import com.nguyen.shop.mapper.ProductMapper;
import com.nguyen.shop.pojo.Cart;
import com.nguyen.shop.pojo.Product;
import com.nguyen.shop.service.ICartService;
import com.nguyen.shop.utils.BigDecimalUtil;
import com.nguyen.shop.utils.PropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author RWM
 * @date 2018/2/7
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartResponse> list(Integer userId) {
        return ServerResponse.createBySuccess(getCartVoLimit(userId));
    }

    @Override
    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null) {
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartMapper.insert(cartItem);
        } else {
            cart.setQuantity(cart.getQuantity() + count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartResponse> update(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKey(cart);
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartResponse> deleteProduct(Integer userId, String productIds) {
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productList)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId, productList);
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartResponse> selectOrUnSelect(Integer userId, Integer productId, Integer checked) {
        cartMapper.checkedOrUncheckedProduct(userId, productId, checked);
        return this.list(userId);
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        if (userId == null) {
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }

    private CartResponse getCartVoLimit(Integer userId) {
        CartResponse cartResponse = new CartResponse();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductInfo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                CartProductInfo cartProductInfo = new CartProductInfo();
                cartProductInfo.id = cartItem.getId();
                cartProductInfo.userId = userId;
                cartProductInfo.productId = cartItem.getProductId();

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    cartProductInfo.productMainImage = (product.getMainImage());
                    cartProductInfo.productName = (product.getName());
                    cartProductInfo.productSubtitle = (product.getSubtitle());
                    cartProductInfo.productStatus = (product.getStatus());
                    cartProductInfo.productPrice = (product.getPrice());
                    cartProductInfo.productStock = (product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        //库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductInfo.limitQuantity = (Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductInfo.limitQuantity = (Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductInfo.quantity = buyLimitCount;
                    //计算总价
                    cartProductInfo.productTotalPrice = BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductInfo.quantity);
                    cartProductInfo.productChecked = cartItem.getChecked();
                }

                if (cartItem.getChecked() == Const.Cart.CHECKED) {
                    //如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductInfo.productTotalPrice.doubleValue());
                }
                cartProductVoList.add(cartProductInfo);
            }
        }
        cartResponse.cartTotalPrice = cartTotalPrice;
        cartResponse.cartProductVoList = cartProductVoList;
        cartResponse.allChecked = getAllCheckedStatus(userId);
        cartResponse.imageHost = PropertiesUtil.getProperty("ftp.server.http.prefix");

        return cartResponse;
    }

    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
