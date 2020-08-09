package com.fh.controller.type;

import com.fh.biz.type.ITypeService;
import com.fh.common.ServerResponse;
import com.fh.po.type.Type;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("type")
public class TypeController {

    @Resource(name="typeService")
    private ITypeService typeService;

    //跳转展示
    @RequestMapping("toIndex")
    public String toIndex(){
        return "type/index";
    }

    //查询
    @RequestMapping("findType")
    @ResponseBody
    public ServerResponse findType(){
        List<Type>typeList = typeService.findType();
        return ServerResponse.success(typeList);
    }
    //添加
    @RequestMapping("addType")
    @ResponseBody
    public ServerResponse addType(Type type){
        typeService.addType(type);
        return ServerResponse.success(type.getId());
    }
    //删除
    @RequestMapping("deleteType")
    @ResponseBody
    public ServerResponse deleteType(@RequestParam("ids[]") List<Long> ids){
        typeService.deleteType(ids);
        return ServerResponse.success();
    }
    //根据id来查询要修改的数据
    @RequestMapping("findTypeById")
    @ResponseBody
    public ServerResponse findTypeById(Long id){
       Type type =  typeService.findTypeById(id);
        return ServerResponse.success(type);
    }
    //修改
    @RequestMapping("editType")
    @ResponseBody
    public ServerResponse editType(Type type){
        typeService.editType(type);
        return ServerResponse.success();
    }
    @RequestMapping("findTypeByPid")
    @ResponseBody
    public ServerResponse findTypeByPid(Long pid){
        List<Type> typeList = typeService.findTypeByPid(pid);
        return ServerResponse.success(typeList);
    }
}
