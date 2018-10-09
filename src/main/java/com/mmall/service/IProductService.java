package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleStatus(Integer productId,Integer status);


}
