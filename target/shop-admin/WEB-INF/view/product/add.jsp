
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/common/link.jsp"/>
</head>




<body>
<jsp:include page="../common/nav.jsp"></jsp:include>
    <div class="container">
       <div class="row">
           <div class="col-md-12">
              <form class="form-horizontal" >
                  <div class="form-group">
                      <label class="col-sm-2 control-label">商品名</label>
                      <div class="col-sm-10">
                          <input type="text" class="form-control" id="productName" placeholder="请输入商品名...">
                      </div>
                  </div>
                  <div class="form-group">
                      <label class="col-sm-2 control-label">商品价格</label>
                      <div class="col-sm-10">
                          <input type="text" class="form-control" id="price" placeholder="请输入商品价格...">
                      </div>
                  </div>
                  <div class="form-group">
                      <label class="col-sm-2 control-label">商品品牌</label>
                      <div class="col-sm-10" id="brandDiv">

                      </div>
                  </div>
                  <div class="form-group">
                      <label class="col-sm-2 control-label">生产日期</label>
                      <div class="col-sm-10">
                          <input type="text" class="form-control" id="createDate" placeholder="请输入生产日期...">
                      </div>
                  </div>

                  <div class="form-group">
                      <label class="col-sm-2 control-label">商品分类</label>
                      <div class="col-sm-10" id="typeSelectDiv">
                      </div>
                  </div>
                  <div class="form-group">
                      <label class="col-sm-2 control-label">商品图片</label>
                      <div class="col-sm-10">
                          <input type="file" class="form-control"  name="imageInfo" id="filePath" />
                          <input type="text" id="titleImage" />
                      </div>
                  </div>
                  <div style="text-align: center;">
                      <button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                      <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                  </div>
              </form>
           </div>
       </div>
    </div>
</body>
<<jsp:include page="/common/script.jsp"/>
   <script>


       function findType(id,obj){
           if(obj){
               $(obj).parent().nextAll().remove();}
               $.ajax({
                   type:"post",
                   url:"/type/findTypeByPid.do",
                   data:{"pid":id},
                   success:function(result){
                       if(result.code==200){
                           var v_selectHtml="";
                           var type = result.data;
                           if(type.length>0){
                               v_selectHtml+="<div class='col-sm-4'><select class='form-control' name='selectType' onchange='findType(this.value,this)'>"
                               v_selectHtml+="<option value='-1'>请选择</option>"
                               for(var i=0;i<type.length;i++){
                                   v_selectHtml+="<option value='"+type[i].id+"'>"+type[i].typeName+"</option>";
                               }
                               v_selectHtml+="</select></div>"
                               $("#typeSelectDiv").append(v_selectHtml);
                           }


                       }
                   },
                   error:function(){
                       alert("失败");
                   }
               })

       }
               function addProduct(){

                   var v_name = $("#productName").val();
                   var v_price = $("#price").val();
                   var v_brandId = $("#selectId").val();
                   var v_param = {};
                   v_param.name=v_name;
                   v_param.price=v_price;
                   v_param.brandId = v_brandId;
                   v_param.createDate = $("#createDate").val();
                   v_param.filePath = $("#titleImage ").val();
                   var le = $("select[name=selectType]").length;
                   if(le==1){
                         v_param.type1 = $("select[name=selectType]")[0].value;
                     }
                   if(le==2){
                       v_param.type1 = $("select[name=selectType]")[0].value;
                       v_param.type2 = $("select[name=selectType]")[1].value;
                   }
                   if(le==3){
                       v_param.type1 = $("select[name=selectType]")[0].value;
                       v_param.type2 = $("select[name=selectType]")[1].value;
                       v_param.type3 = $("select[name=selectType]")[2].value;
                   }
                   $.ajax({
                       type:"post",
                       url:"/product/addProduct.do",
                       data:v_param,
                       success:function(result){
                             location.href="/product/toIndex.do"
                       },
                       error:function(){
                           alert("添加失败")
                       }
                   })
               }
$(function(){
    initBrand();
    findType(1);
    showDate();
    initFileAdd();
})

       function initFileAdd() {
           //initialPreview:imageArr,//回显的时候
           // initialPreviewAsData:true,
           //初始化fileInput
           $("#filePath").fileinput({
               uploadUrl:"/file/uploadFile.do",//图片上传的路径
               browseClass:"btn btn-success", //按钮样式
               dropZoneEnabled: true,//是否显示拖拽区域
               language:"zh"//显示中文的图标
           }).on("fileuploaded", function(a, data, previewiId, index) {
               console.log(data);
               //把文件的访问路径  赋给隐藏域
               $("#titleImage").val(data.response.data);
           });
       }





               function initBrand(){
                   $.ajax({
                       type:"post",
                       url:"/brand/findBrand.do",
                       data:{},
                       success:function(result){
                                var b_html="<select id='selectId' class=\"form-control\"><option value='-1'>-----请选择-----</option>"
                           if(result.code==200){
                               var brandList = result.data;
                               console.log(brandList);
                             for(var i=0;i<brandList.length;i++){
                                var v_brand =  brandList[i];
                                 b_html+="<option value='"+v_brand.id+"'>"+v_brand.brandName+"</option>"
                             }
                               b_html+="</select>"
                               $("#brandDiv").html(b_html);
                           }
                       }
                   })
               }
               function showDate(){
                   $("#createDate").datetimepicker({
                       format:"YYYY-MM-DD",
                       locale:"zh-CN",
                   });
               }
   </script>
</html>
