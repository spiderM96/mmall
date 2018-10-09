package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

public interface ICategoryService {
    public ServerResponse addCategory(String categoryName,Integer parentId);

    public ServerResponse setCategoryName(String categoryName,Integer categoryId);

    public ServerResponse<List<Category>> getChildrenParalellCategory(Integer categoryId);

    public ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
