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
              <form class="form-horizontal">
                  <div class="form-group">
                      <label class="col-sm-2 control-label">商品名</label>
                      <div class="col-sm-10">
                          <input type="text" class="form-control" id="productName" placeholder="请输入商品名...">
                      </div>
                  </div>
                  <div class="form-group">
                      <label class="col-sm-2 control-label">商品价格</label>
                      <div class="col-sm-10">
                          <input type="text" class="form-control"  id="price" placeholder="请输入商品价格...">
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
                          <input type="text" class="form-control"  id="createDate" placeholder="请输入生产日期...">
                      </div>
                  </div>
                  <div class="form-group" id="typeSelectDiv">
                      <label class="col-sm-2 control-label">商品分类</label>
                  </div>
                  <div class="form-group">
                      <label  class="col-sm-2 control-label">图片</label>
                      <div class="col-sm-10">
                          <input id="filePath" name="imageInfo" type="file"  class="form-control">
                          <input type="text"  id="oldImg" >
                          <input type="text"  id="titleImage" >
                      </div>
                  </div>

                  <div style="text-align: center;">
                      <button type="button" class="btn btn-primary" onclick="updateProduct();"><i class="glyphicon glyphicon-ok"></i>修改</button>
                      <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                  </div>
              </form>
           </div>
       </div>
    </div>
</body>
<jsp:include page="/common/script.jsp"/>
   <script>

       $(function(){

           initBrand();

       })

       function upload(imageArr){
           //初始化新增用户表单中的用户图片文件域
           //初始化fileInput
           $("#filePath").fileinput({
               uploadUrl:"/file/uploadFile.do",//图片上传的路径
               browseClass:"btn btn-success", //按钮样式
               dropZoneEnabled: true,//是否显示拖拽区域
               language:"zh",//显示中文的图标
               initialPreview:imageArr,//回显的时候
               initialPreviewAsData:true
           }).on("fileuploaded", function(a, data, previewiId, index) {
               console.log(data);
               //把文件的访问路径  赋给隐藏域
               $("#titleImage").val(data.response.data);
           });
       }
       function showDate(){
           $("#createDate").datetimepicker({
               format:"YYYY-MM-DD",
               locale:"zh-CN",
           });
       }
       var v_type1;
       var v_type2;
       var v_type3;
       function findProductById(){
           var v_id = '${param.id}';

           $.ajax({
               type:"post",
               url:"/product/findProductById.do",
               data:{"id":v_id},
               success:function(result){
                  if(result.code==200){
                      var data = result.data;
                      v_type1 = data.type1;
                      v_type2 = data.type2;
                      v_type3 = data.type3;
                      $("#productName").val(data.name);
                      $("#price").val(data.price);
                      $("#selectId").val(data.brandId);
                      $("#createDate").val(data.createDate);
                      $("#oldImg").val(data.filePath);
                      $("#typeSelectDiv").append("<span id='editTypeName'>"+data.typeName+"</span>"+"<button type='button' class='btn btn-warning' onclick='editType()'><span id='editButton'>编辑</span></button>")
                      var filePathArry = [];
                      filePathArry.push(data.filePath);
                      upload(filePathArry);
                  }
               },

               error:function(){
                   alert("回显失败")
               }
           })

       }
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
                           v_selectHtml+="<div class='col-sm-2' name='typeDiv'><select class='form-control' name='selectType' onchange='findType(this.value,this)'>"
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
       var v_edit=0;
       var v_html;
       function editType(){
           if(v_edit==0){
               findType(1);
               v_html = $("#editTypeName").html();
               $("#editTypeName").html("");
               $("#editButton").html("取消编辑");
               v_edit=1;
           }else{
               $("#editButton").html("编辑");
               $("#editTypeName").html(v_html);
               $("#typeSelectDiv div[name=typeDiv]").remove();
               v_edit=0;
           }
       }

               function updateProduct(){
                   var v_name = $("#productName").val();
                   var v_price = $("#price").val();
                   var v_id = '${param.id}';

                   var v_param = {};
                   if($("select[name=selectType]")[0]){
                       v_type1 = $("select[name=selectType]")[0].value;
                   }
                   if($("select[name=selectType]")[1]){
                       v_type2 = $("select[name=selectType]")[1].value;
                   }
                   if($("select[name=selectType]")[2]){
                       v_type3 = $("select[name=selectType]")[2].value;
                   }
                   v_param.type1 = v_type1;
                   v_param.type2 = v_type2;
                   v_param.type3 = v_type3;
                   v_param.name=v_name;
                   v_param.price=v_price;
                   v_param.id=v_id;
                   v_param.brandId = $("#selectId").val();
                   v_param.createDate = $("#createDate").val();
                   v_param.filePath= $("#titleImage").val();
                   v_param.oldImg = $("#oldImg").val();
                   $.ajax({
                       type:"post",
                       url:"/product/updateProduct.do",
                       data:v_param,
                       success:function(result){
                             location.href="/product/toIndex.do"
                       },
                       error:function(){
                           alert("添加失败")
                       }
                   })
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
                       findProductById();
                       showDate();
                       b_html+="</select>"
                       $("#brandDiv").html(b_html);
                   }
               }
           })
       }

   </script>
</html>
