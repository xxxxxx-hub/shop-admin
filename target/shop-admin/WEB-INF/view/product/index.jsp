
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/common/link.jsp"/>
</head>
<body>
<jsp:include page="../common/nav.jsp"></jsp:include>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">条件查询</h3>
    </div>
    <div class="panel-body">
        <form class="form-horizontal" method="post" id="returnQuery" nctype="multipart/form-data">
            <div class="form-group">
                <label  class="col-sm-2 control-label">商品名</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" name="productName" id="productName" placeholder="请输入商品名...">
                </div>
                <label  class="col-sm-2 control-label">价格范围</label>
                <div class="col-sm-4">
                    <div class="input-group">
                        <input type="text" class="form-control" name="minPrice" id="minPrice" placeholder="请输入最小价格...">
                        <span class="input-group-addon" id="basic-addon1"><i class="glyphicon glyphicon-yen"></i> </span>
                        <input type="text" class="form-control" name="maxPrice" id="maxPrice" placeholder="请输入最大价格...">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">商品品牌</label>
                <div class="col-sm-4" id="brandDiv">

                </div>
                <label  class="col-sm-2 control-label">生产日期</label>
                <div class="col-sm-4">
                    <div class="input-group">
                        <input type="text" class="form-control" name="minCreateDate" id="minCreateDate" placeholder="...">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-arrow-right"></i> </span>
                        <input type="text" class="form-control" name="maxCreateDate" id="maxCreateDate" placeholder="...">
                    </div>
                </div>
            </div>
            <div style="text-align: center;">
                <button type="button" class="btn btn-primary" onclick="search();"><i class="glyphicon glyphicon-search"></i> 查询</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button>
            </div>
        </form>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">展示页面</h3>
            </div>
            <div class="panel-body">
                <button type="button"  onclick="toAdd()" class="btn-sm btn btn-success"><i class="glyphicon glyphicon-plus"></i>添加商品</button>
                <button type="button" class="btn btn-info"  onclick="batchDelete()"><i class="glyphicon glyphicon-trash"></i>批量删除</button>
                <button type="button" class="btn btn-info"  onclick="clearCache()"><i class="glyphicon glyphicon-trash"></i>清空缓存</button>
                <table id="productTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>
                            <input type="checkbox">
                        </th>
                        <th>id</th>
                        <th>商品名</th>
                        <th>价格</th>
                        <th>是否热销</th>
                        <th>品牌名称</th>
                        <th>生产日期</th>
                        <th>录入日期</th>
                        <th>修改日期</th>
                        <th>分类</th>
                        <th>图片</th>
                        <th>是否下架</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                </table>
            </div>
        </div>
    </div>
</div>

<div id="importExcelDiv" style="display: none">
    <form id="importExcelForm" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label"></label>
            <div class="col-sm-8">
                <input type="file" name="file" accept=".xlsx" id="file" />
            </div>
            <input type="text"  class="form-control" id="filePath">
        </div>
    </form>
</div>
<script src="/js/jquery.min.js"></script>
<script src="/js/dataTable/DataTables-1.10.20/js/jquery.dataTables.js"></script>
<script src="/js/bootstrap3/js/bootstrap.min.js"></script>
<script src="/js/bootstrap3/js/bootbox.min.js"></script>
<script src="/js/bootstrap3/js/bootbox.locales.min.js"></script>
<script src="/js/bootstrap3/bootstrap-datetimepicker/js/moment-with-locales.js"></script>
<script src="/js/bootstrap3/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/js/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/js/bootstrap-fileinput/js/locales/zh.js"></script>

</body>
<jsp:include page="/common/script.jsp"/>
      <script>
          var importExcelDivHTML;
          $(function(){
              inintTable();
              importExcelDivHTML = $("#importExcelDiv").html();
              initDate("minCreateDate");
              initDate("maxCreateDate");
              initBindEvent();
              initBrand();
          })


          var idList=[];
          function initBindEvent(){
              //在表单的tr中设置鼠标单击事件
              $('#productTable').on('click', 'tr',function() {
                  var data = productTable.row(this).data(); //获取单击那一行的数据
                  console.log(data);
                  //获取复选框
                  var checkBox = $(this).find("[name='id']")[0];
                  if(checkBox.checked){
                      checkBox.checked = false;
                      $(this).css("background-color","");
                      idList.splice(idList.indexOf(checkBox.value),1);
                  }else{
                      checkBox.checked = true;
                      $(this).css("background-color","#66afe9");
                      idList.push(checkBox.value);
                  }
              } );
          }
          function initDate(elment){
              $("#"+elment).datetimepicker({
                  format:"YYYY-MM-DD",
                  locale:"zh-CN",
              });
          }
          var productTable;
          function inintTable(){
              productTable =  $('#productTable').DataTable( {
                  "language": {
                      "url": "/js/Chinese.json"
                  },
                  "searching":false,
                  "lengthMenu": [3,10,15,20],
                  "processing": true,
                  "serverSide": true,
                  "ajax":{
                      type:"post",
                      url:"/product/findPageList.do",

                  },
                  "columns": [
                      {"data": "id",
                          render:function(data, type, row, meta) {
                              var html = "<input type='checkbox' name='id' value='"+data+"'>"
                              return html;
                          }},
                      { "data": "id" },
                      { "data": "name" },
                      { "data": "price" },
                      { "data": "isHot",
                          "render":function(data){
                             return data==0?"非热销":"热销"
                          }
                      },
                      { "data": "brandName" },
                      { "data": "createDate" },
                      { "data": "showTime" },
                      { "data": "updateTime" },
                      { "data": "typeName" },
                      {
                          data:"filePath",
                          render:function(data){
                              return "<img width='40' height='30' src='"+data+"' />";
                          }
                      },
                      { "data": "isup",
                          "render":function(data){
                              return data==0?"下架":"上架"
                          }
                      },
                      {
                          data: "id",

                          render:function (data, type, row) {

                              var isHot = row.isHot;
                              var isup = row.isup;
                              var v_text="";
                              var v_icon = "";
                              var v_color="";
                              var status ="";
                              var isupStatus ="";
                              var text="";
                              var icon = "";
                              var color="";
                              if(isHot==1){
                                  v_text="非热销";
                                  v_color="btn btn-warning";
                                  v_icon="glyphicon glyphicon-map-marker";
                                  status="0";
                              }else{
                                  v_text="热销";
                                  v_color="btn btn-success";
                                  v_icon="glyphicon glyphicon-fire";
                                  status="1";
                              };
                              if(isup==1){
                                  text="下架";
                                  color="btn btn-warning";
                                  icon="glyphicon glyphicon-cloud-download";
                                  isupStatus="0";
                              }else{
                                  text="上架";
                                  color="btn btn-danger";
                                  icon="glyphicon glyphicon-cloud-upload";
                                  isupStatus="1";
                              };
                              var buttonHTML = "";
                              buttonHTML += '<div class="btn-group btn-group-xs">';
                              buttonHTML += '<button type="button" onclick="deleteProduct(' + data + ')" class="btn btn-danger">';
                              buttonHTML += '<span class="glyphicon glyphicon-trash"></span>&nbsp;删除';
                              buttonHTML += '</button>'
                              buttonHTML += '<button type="button" onclick="showUpdate(' + data + ')" class="btn btn-primary">';
                              buttonHTML += '<span class="glyphicon glyphicon-pencil"></span>&nbsp;修改';
                              buttonHTML += '</button>';
                              buttonHTML += '<button type="button" onclick="updateIsHotStatus(' + data + ','+status+')" class="'+v_color+'">';
                              buttonHTML += '<span class="'+v_icon+'"></span>&nbsp;'+v_text+'';
                              buttonHTML += '</button>'; buttonHTML += '<button type="button" onclick="updateIsupStatus(' + data + ','+isupStatus+')" class="'+color+'">';
                              buttonHTML += '<span class="'+icon+'"></span>&nbsp;'+text+'';
                              buttonHTML += '</button>';

                              return buttonHTML;
                          }
                      }
                  ],
                  "drawCallback": function( settings ) {
                      console.log(idList);
                      if(idList.length>0){
                          $("[name='id']").each(function () {
                              if(idList.indexOf(this.value)!=-1){
                                  this.checked=true;
                                  $(this).parent().parent().css("background-color","#66afe9");
                              }
                          })
                      }

                  },
              } );
          }
          function search() {
              var v_param = {};
              v_param.productName = $("#productName").val();
              v_param.minPrice = $("#minPrice").val();
              v_param.maxPrice = $("#maxPrice").val();
              v_param.selectId = $("#selectId").val();
              v_param.minCreateDate = $("#minCreateDate").val();
              v_param.maxCreateDate = $("#maxCreateDate").val();
              productTable.settings()[0].ajax.data = v_param;
              productTable.ajax.reload();
          }

             function updateIsHotStatus(id,status) {
                 event.stopPropagation();
                 $.ajax({
                     type: "post",
                     url: "/product/updateIsHotStatus.do?id=" + id + "&status=" + status,
                     success: function (result) {
                         if (result.code = 200) {
                             productTable.ajax.reload();
                         }
                     },
                     error: function () {
                         alert("失败")
                     }
                 })
             }
                 function updateIsupStatus(id,status){
                     event.stopPropagation();
                     $.ajax({
                         type:"post",
                         url:"/product/updateIsupStatus.do?id="+id+"&status="+status,
                         success:function(result){
                             if(result.code=200){
                                 productTable.ajax.reload();
                             }
                         },
                         error:function(){
                             alert("失败")
                         }
                     })
             }
          function deleteProduct(id){
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
                              url:"/product/deleteProduct.do",
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
              location.href="/product/toAdd.do"
          }
          function showUpdate(id){
              location.href="/product/toUpdate.do?id="+id;
          }


          function initBrand(){
              $.ajax({
                  url:"/brand/findBrand.do",
                  data:{},
                  success:function(result){
                      var b_html="<select id='selectId' name='selectId' class=\"form-control\"><option value='-1'>-----请选择-----</option>"
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

          function batchDelete(){
              bootbox.dialog({
                  message: "确认删除",
                  title: "提示信息",
                  buttons: {
                      Cancel: {
                          label: "取消",
                          className: "btn-default",
                          callback: function () {

                          }
                      }
                      , OK: {
                          label: "确认",
                          className: "btn-danger",
                          callback: function () {
                              if(idList.length>0){
                                  $.post(
                                      "/product/batchDelete.do",
                                      {"ids":idList},
                                      function (data) {
                                          search();
                                      }
                                  )
                              }else{
                                  bootbox.alert("请选择要删除的数据！",function(){
                                  })
                              }
                          }
                      }
                  }
              });
          }

          function clearCache() {
              $.ajax({
                type:"post",
                url:"/product/clearCache.do",
                  success:function (res) {
                      if(res.code=200){
                          alert("刷新成功");
                      }
                  }

              })
          }


      </script>
</html>
