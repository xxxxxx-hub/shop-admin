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
        <h3 class="panel-title">商品分类
            <button class="btn btn-primary btn-sm" onclick="showAdd()">
                <span class="glyphicon glyphicon-plus"></span> 新增
            </button>
            <button class="btn btn-primary btn-sm" onclick="showUpdate()">
                <span class="glyphicon glyphicon-pencil"></span> 修改
            </button>
            <button class="btn btn-primary btn-sm" onclick="deleteType()">
                <span class="glyphicon glyphicon-pencil"></span> 删除
            </button>
        </h3>
    </div> <div class="panel-body">

    <ul id="typeTree" class="ztree"></ul>
</div>
</div>

<!-- 新增DIV -->
<div id="addDiv" style="display:none">
    <!-- 新增权限form -->
    <form id="addForm" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">类别名称:</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="addName" />
            </div>
        </div>
    </form>
</div>

<!-- 修改DIV -->
<div id="updateDiv" style="display:none">
    <!-- 新增权限form -->
    <form id="updateForm" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">类别名称:</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="updateName" />
            </div>
        </div>
    </form>
</div>
<jsp:include page="/common/script.jsp"/>
<script>

     var addHTML;
     var updateHTML;
    $(function(){
        jump();
        updateHTML = $("#updateDiv").html()
        addHTML =  $("#addDiv").html()
    })
    var zTreeObj;

    var setting={
        view: {
            selectedMulti: false
        },
        data:{
            simpleData:{
                enable:true,
                pIdKey:"pid",
            },
            key:{
                name:"typeName"
            }
        }
    };
    //展示
    function jump(){
        $.ajax({
            type:"post",
            url:"/type/findType.do",
            dataType:"json",
            success:function(result){
                if(result.code==200){
                    zTreeObj = $.fn.zTree.init($("#typeTree"),setting,result.data)
                }else{
                    alert("查询失败")
                }
            },
            error:function(){
                alert("查询失败")
            }
        })
    }
    //添加
    function showAdd(){
        var selectedNodes = zTreeObj.getSelectedNodes();
        if(selectedNodes.length==1){
            var selectedNode = selectedNodes[0];
            var addTypeHtml =  bootbox.confirm({
                title:"添加分类",
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
                        var v_id = selectedNode.id;
                        var v_name = $("#addName").val();
                        $.ajax({
                            type:"post",
                            url:"/type/addType.do",
                            data:{"typeName":v_name,"pid":v_id},
                            success:function(result){
                                var newNode = {id:result.data,typeName:$("#addName",addTypeHtml).val(),pid:v_id};
                                zTreeObj.addNodes(selectedNode, newNode);
                            },
                            error:function(){
                                alert("添加失败")
                            }
                        })
                    }
                    $("#addDiv").html(addHTML);
                }
            })
        }else if(selectedNodes.length==0){
            alert("请选择要添加的分类")
        }else{
            alert("不能多选")
        }
     }
     //删除
    function deleteType(){
        var selectedNodes = zTreeObj.getSelectedNodes();
        if(selectedNodes.length>0){
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
                        var node = selectedNodes[0];
                        //根据树本身的查询子孙的方法放入选中的节点
                        var nodeArr = zTreeObj.transformToArray(node);
                        //定义一个空的数组
                        var ids=[];
                        for(var i=0;i<nodeArr.length;i++){
                            //将要删除的数据以及数据下的数据存入空的数组中
                            ids.push(nodeArr[i].id);
                        }
                        $.ajax({
                            url:"/type/deleteType.do",
                            dataType:"json",
                            data:{"ids":ids},
                            success:function(result){
                                if(result.code==200){
                                    var nodes = zTreeObj.getSelectedNodes();
                                    for (var i=0; i < nodes.length; i++) {
                                        zTreeObj.removeNode(nodes[i]);
                                    }
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
        }else{
            alert("请选择要删除的分类")
        }
    }

    //修改
    function showUpdate(){
        var selectedNodes = zTreeObj.getSelectedNodes();
        if(selectedNodes.length==1){
            var selectedNode = selectedNodes[0];
            $.ajax({
                type:"post",
                url:"/type/findTypeById.do",
                data:{"id":selectedNode.id},
                success:function(result){
                  if(result.code==200){
                      var type =  result.data;
                      $("#updateName").val(type.typeName);
                  }
                },
                error:function(){
                    alert("失败")
                }
            })
            bootbox.confirm({
                title:"修改",
                message:$("#updateDiv").children(),
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
                        var name = $("#updateName").val();
                        var v_id  = selectedNode.id;
                        $.ajax({
                            type:"post",
                            url:"/type/editType.do",
                            data:{"id":v_id,
                                "typeName":name},
                            success:function(result){
                                if(result.code==200){
                                    selectedNode.typeName=name;
                                    zTreeObj.updateNode(selectedNode);
                                }
                            },
                            error:function(){
                                alert("修改失败")
                            }
                        })
                    }
                    $("#updateDiv").html(updateHTML);
                }
            })
        }else if(selectedNodes.length==0){
            alert("选择修改")
        }else{
            alert("只能选择一个")
        }

    }

</script>
</body>
</html>
