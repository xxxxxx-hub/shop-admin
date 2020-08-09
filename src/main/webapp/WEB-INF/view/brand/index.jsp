
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>

<jsp:include page="/common/link.jsp"/>
<body>

<!-- 修改 -->
<div id="updateDiv" style="display:none">
    <form id ="updateForm" class="form-horizontal" style="" method="post">
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌名称</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" id="updateName" />
            </div>
        </div>
        <div class="form-group">
            <input type="text" id="updateLogo" class="form-control"/>
            <label  class="col-sm-2 control-label">图片</label>
            <div class="col-sm-6">
                <input type="file" class="form-control" multiple id="photo" name="cover" />
            </div>

            <input type="text" id="oldUpdateFilePath" class="form-control"/>
        </div>
    </form>
</div>
<!-- 添加 -->
<div id="addDiv" style="display:none">
    <form id ="addForm" class="form-horizontal" style="" method="post">
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌名称</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" id="addName" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">图片</label>
            <div class="col-sm-6">
                <input type="file" class="form-control" multiple id="cover" name="cover" />
            </div>

            <input type="text" id="addFilePath" class="form-control"/>
        </div>
    </form>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">展示页面</h3>
            </div>
            <div class="panel-body">
                <button type="button"  onclick="toAdd()" class=" btn-sm btn btn-success">添加</button>
                <table id="brandTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>品牌id</th>
                        <th>品牌名称</th>
                        <th>图片</th>
                        <th>操做</th>
                    </tr>
                    </thead>

                </table>
            </div>
        </div>
    </div>
</div>

</body>
<jsp:include page="/common/script.jsp"/>
      <script>
           var addHtml;
           var updateHtml;
          $(function(){
              addHtml = $("#addDiv").html();
              updateHtml = $("#updateDiv").html();
              inintTable();
          })
          var brandTable;
          function inintTable(){
              brandTable =  $('#brandTable').DataTable( {
                  "language": {
                      "url": "/js/Chinese.json"
                  },
                  "searching":false,
                  "lengthMenu": [3,10,15,20],
                  "processing": false,
                  "serverSide": false,
                  "ajax":{
                      type:"post",
                      url:"/brand/findBrand.do",

                  },
                  "columns": [
                      { "data": "id" },
                      { "data": "brandName" },
                      {
                          data:"logo",
                          render:function(data){
                              return "<img width='40' height='30' src='"+data+"' />";
                          }
                      },
                      {
                          data: "id",

                          render:function (data, type, row) {
                              var buttonHTML = "";
                              buttonHTML += '<div class="btn-group btn-group-xs">';
                              buttonHTML += '<button type="button" onclick="deleteBrand(' + data + ')" class="btn btn-danger">';
                              buttonHTML += '<span class="glyphicon glyphicon-trash"></span>&nbsp;删除';
                              buttonHTML += '</button>'
                              buttonHTML += '<button type="button" onclick="showUpdate(' + data + ')" class="btn btn-primary">';
                              buttonHTML += '<span class="glyphicon glyphicon-pencil"></span>&nbsp;修改';
                              buttonHTML += '</button>';

                              return buttonHTML;
                          }
                      }
                  ]
              } );
          }
          function search() {
              brandTable.ajax.reload();
          }

          function deleteBrand(id){
              bootbox.confirm({
                  title:"删除提示",
                  message:"是否要删除这些数据",
                  buttons:{
                      //設置確定按鈕的文字和樣式
                      confirm:{
                          label:"确认",
                          className:"btn btn-success"
                      },
                      //設置取消按鈕的文字和樣式
                      cancel:{
                          label:"取消",
                          className:"btn btn-danger"
                      }
                  },
                  callback:function(result){
                      if(result){
                          $.ajax({
                              url:"/brand/deleteBrand.do",
                              dataType:"json",
                              data:{"id":id},
                              success:function(result){
                                  if(result.code==200){
                                      alert("删除成功")
                                      search();
                                  }else{
                                      alert("后台失败")
                                  }
                              },error:function(){
                                  alert("删除失败")
                              }
                          })
                      }
                  }

              })


          }


          function toAdd(){

              //初始化新增用户表单中的用户图片文件域
              $("#cover").fileinput({
                  language:"zh",//设置语言选项
                  maxFileCount:1,//设置最大上传文件个数
                  //设置文件上传的地址
                  uploadUrl:"/brand/uploadFile.do"
              });
              $("#cover").on("fileuploaded",function(a,b,c,d){
                  //其中b就代表服务器返回的数据
                  var result = b.response;
                  if(result.code == 200){
                      //将图片上传后的相    //设置文件上传之后的回调函数
                      //对路径放入新增用户表单中的用于存放图片相对路径的隐藏域中
                      $("#addFilePath").val(result.data);
                  }
              });
              bootbox.confirm({
                  title:"新增品牌",
                  message:$("#addDiv").children(),
                  buttons:{
                      confirm:{
                          label:"确认"
                      },
                      cancel:{
                          label:"取消",
                          className:"btn btn-danger"
                      }
                  },
                  callback:function(result){

                      if(result){
                          var param = {};
                          param.brandName = $("#addName").val();
                          param.logo = $("#addFilePath").val();
                          $.ajax({
                              type:"post",
                              url:"/brand/addBrand.do",
                              dataType:"json",
                              data:param,
                              success:function(result){
                                  if(result.code==200){
                                      alert("添加成功")
                                      search();
                                  }else{
                                      alert("后台失败")
                                  }
                              },
                              error:function(){
                                  alert("添加失败")
                              }
                          })
                      }
                      $("#addDiv").html(addHtml);
                  }
              })
          }
          function initFile(mainFile){
              //初始化新增用户表单中的用户图片文件域
              $("#photo").fileinput({
                  language:"zh",//设置语言选项
                  maxFileCount:1,//设置最大上传文件个数
                  //设置文件上传的地址
                  uploadUrl:"/brand/uploadFile.do",
                  initialPreview:mainFile,
                  initialPreviewAsData:true
              });
              $("#photo").on("fileuploaded",function(a,b,c,d){
                  //其中b就代表服务器返回的数据
                  var result = b.response;
                  if(result.code == 200){
                      //将图片上传后的相    //设置文件上传之后的回调函数
                      //对路径放入新增用户表单中的用于存放图片相对路径的隐藏域中
                      $("#updateLogo").val(result.data);
                  }
              });
          }
           function showUpdate(id){

               $.ajax({
                   type:"post",
                   url:"<%=request.getContextPath()%>/brand/getById.do",
                   dataType:"json",
                   data:{"id":id},
                   success:function(result){
                       if(result.code==200){
                           var brand = result.data;
                           $("#updateName").val(brand.brandName);
                           $("#oldUpdateFilePath").val(brand.logo);
                           var logoArray = [];
                           logoArray.push(brand.logo);
                           initFile(logoArray);
                           bootbox.confirm({
                               title:"修改品牌",
                               message:$("#updateDiv").children(),
                               buttons:{
                                   confirm:{
                                       label:"确认"
                                   },
                                   cancel:{
                                       label:"取消",
                                       className:"btn btn-danger"
                                   }
                               },
                               callback:function(result){

                                   if(result){
                                       var param = {};
                                       param.id  = brand.id;
                                       param.brandName = $("#updateName").val();
                                       param.logo = $("#updateLogo").val();
                                       param.oldLogo = $("#oldUpdateFilePath").val();
                                       $.ajax({
                                           type:"post",
                                           url:"/brand/updateBrand.do",
                                           dataType:"json",
                                           data:param,
                                           success:function(result){
                                               if(result.code==200){
                                                   alert("修改成功")
                                                   search();
                                               }else{
                                                   alert("后台失败")
                                               }
                                           },
                                           error:function(){
                                               alert("修改失败")
                                           }
                                       })
                                   }
                                   $("#updateDiv").html(updateHtml);
                               }
                           })
                       }
                   },
                   error:function(){
                       alert("回显失败")
                   }
               })
           }


      </script>
</html>
