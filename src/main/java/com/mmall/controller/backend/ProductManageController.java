package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.utils.CookieUtil;
import com.mmall.utils.JsonUtil;
import com.mmall.utils.PropertiesUtil;
import com.mmall.utils.RedisPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:Chengkangxin
 * @Description:
 * @Date:Create in 15:17 2018/10/9
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    IUserService iUserService;

    @Autowired
    IProductService iProductService;

    @Autowired
    IFileService iFileService;

    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest httpServletRequest, Product product){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.saveOrUpdateProduct(product);
        }else{
            return ServerResponse.createByErrorMessage("没有权限");
        }
    }

    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest httpServletRequest,Integer productId,Integer status){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.setSaleStatus(productId,status);
        }else{
            return ServerResponse.createByErrorMessage("没有权限");
        }
    }


    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse manageProductDetail(HttpServletRequest httpServletRequest,Integer productId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.getProductDetail(productId);
        }else{
            return ServerResponse.createByErrorMessage("没有权限");
        }
    }

    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getList(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum", defaultValue = "1" ) int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.getProductList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("没有权限");
        }
    }

    @RequestMapping(value = "search.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse searchProduct(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum", defaultValue = "1" ) int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize,String productName,Integer productId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("没有权限");
        }
    }

    @RequestMapping(value = "upload.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(HttpServletRequest httpServletRequest,@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            Map fileMap = new HashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
        }else{
            return ServerResponse.createByErrorMessage("没有权限");
        }

    }
}
