package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:Chengkangxin
 * @Description:
 * @Date:Create in 15:41 2018/10/9
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;
    public ServerResponse saveOrUpdateProduct(Product product){
        if(product != null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                product.setMainImage(subImageArray[0]);
            }
            if(product.getId() != null){
                int resultCount = productMapper.updateByPrimaryKey(product);
                if(resultCount > 0){
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                return ServerResponse.createByErrorMessage("更新产品失败");
            }else {
                int resultCount = productMapper.insert(product);
                if(resultCount > 0){
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                return ServerResponse.createBySuccessMessage("新增产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    public ServerResponse setSaleStatus(Integer productId,Integer status){
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if(status != null){
            Product product = new Product();
            product.setStatus(status);
            product.setId(productId);
            productMapper.updateByPrimaryKey(product);
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }
}
