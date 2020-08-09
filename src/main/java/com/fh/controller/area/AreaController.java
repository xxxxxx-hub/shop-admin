package com.fh.controller.area;


import com.fh.biz.area.IAreaService;
import com.fh.common.ServerResponse;
import com.fh.po.area.Area;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("area")
@CrossOrigin
public class AreaController {

     @Resource(name = "areaService")
     private IAreaService areaService;

     /*跳转到地区列表页面*/
     @RequestMapping("toAreaList")
     public  String toAreaList(){
          return  "areaList";
     }

     //查询地区列表
     @RequestMapping("/findAreaList")
     public List<Area> findAreaList(){
          return  areaService.findAreaList();
     }

     //新增地区节点
     @RequestMapping("/addArea")
     @ResponseBody
     public ServerResponse addArea(Area area){
          return areaService.addArea(area);
     }

      //删除 地区节点
     @RequestMapping("/deleteArea")
     public ServerResponse deleteArea(@RequestParam("ids[]") Long[] ids){
          return areaService.deleteArea(ids);
     }

     /*queryAreaById  回显*/
     @RequestMapping("queryAreaById")
     public  ServerResponse queryAreaById(Long id){
          return  areaService.queryAreaById(id);
     }

     /*修改  updatearea*/
     @RequestMapping("updateArea")
     @ResponseBody
     public  ServerResponse updateArea(Area area){
          return  areaService.updateArea(area);
     }


    @RequestMapping("/findChilds")
    public ServerResponse findChilds(Long id){
        return  areaService.findChilds(id);
    }

}
