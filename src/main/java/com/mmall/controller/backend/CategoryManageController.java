package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.utils.CookieUtil;
import com.mmall.utils.JsonUtil;
import com.mmall.utils.RedisPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author:Chengkangxin
 * @Description:
 * @Date:Create in 9:45 2018/10/9
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {
    @Autowired
    IUserService iUserService;
    @Autowired
    ICategoryService iCategoryService;

    @RequestMapping(value = "add_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest httpServletRequest, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录");
        }
        //校验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员,业务逻辑
            return iCategoryService.addCategory(categoryName,parentId);

        }else{
                return ServerResponse.createByErrorMessage("不是管理员，没有操作权限");
        }
    }

    @RequestMapping(value = "set_category_name.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest,Integer categoryId,String categoryName){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录");
        }
        //校验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员,业务逻辑
            return iCategoryService.setCategoryName(categoryName,categoryId);

        }else{
            return ServerResponse.createByErrorMessage("不是管理员，没有操作权限");
        }
    }

    @RequestMapping(value = "get_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录");
        }
        //校验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员,业务逻辑
            return iCategoryService.getChildrenParalellCategory(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("不是管理员，没有操作权限");
        }
    }

    @RequestMapping(value = "get_deep_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCateforyAndDeepCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录");
        }
        //校验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员,业务逻辑
            return iCategoryService.selectCategoryAndChildrenById(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("不是管理员，没有操作权限");
        }
    }

}
