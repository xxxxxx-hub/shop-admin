<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/common/link.jsp"/>
    <link rel="shortcut icon" href="#"/>
</head>
<body>

<div class="panel-body">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" id="memberName" placeholder="请输入用户名...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">密码</label>
            <div class="col-sm-6" id="brandDiv">
                <input type="password" class="form-control" id="password" placeholder="请输入密码...">
            </div>
        </div>
        <div style="text-align: center;">
            <button type="button" class="btn btn-primary" onclick="login();"><i class="glyphicon glyphicon-search"></i>登录</button>
            <button type="button" onclick="register()" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>注册</button>
        </div>
    </form>
</div>

<jsp:include page="/common/script.jsp"/>
<script>
    var addHtml;
    $(function(){
        addHtml=$("#addDiv").html();

    })
    function login(){
        var v_memberName = $("#memberName").val();
        var v_password = $("#password").val();
        if(v_memberName==null || v_memberName==''||v_password==null || v_password==''){
            return;
        }

        $.ajax({
            type:"post",
            url:"/members/login.do",
            data:{"memberName":v_memberName,
                   "password":v_password},
            success:function(result){
                if(result.code==200){
                    location.href="/product/toIndex.do"
                }
                else{
                    console.log(result.msg);
                }
            }
        })
    }
    function initDate(elment){
        $("#"+elment).datetimepicker({
            format:"YYYY-MM-DD",
            locale:"zh-CN",
        });
    }
    //递归地区
    function queryArea(a,obj){
        if(obj){
            //当前元素的上级元素之后的同级清除
            $(obj).parent().nextAll().remove();
        }

        $.ajax({
            type:"post",
            url:"/area/findByPid.jhtml",
            data:{"pid":a},
            success:function(result){
                if(result.code==200){
                       var v_area   =   result.data;
                       if(v_area.length>0){
                           var v_areaHtml = "";
                           v_areaHtml += "<div class=\"col-sm-3\"><select class=\"form-control\" name=\"areaSelect\" onchange='queryArea(this.value,this)'>"
                           v_areaHtml+="<option value='-1'>请选择</option>"
                           for (var i = 0; i < v_area.length; i++) {
                               v_areaHtml += "<option value='" + v_area[i].id + "'>" + v_area[i].areaName + "</option>"
                           }
                           v_areaHtml += "</select></div>"
                           $("#selectedId").append(v_areaHtml);
                       }
                }
            }
        })
    }
    //注册
    var registerDiv;
    function register(){
        queryArea(1);
        //初始化新增用户表单中的用户图片文件域
        $("#cover").fileinput({
            language:"zh",//设置语言选项
            maxFileCount:1,//设置最大上传文件个数
            //设置文件上传的地址
            uploadUrl:"/user/uploadFile.jhtml"
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
        initDate("addBirthday");
       registerDiv =  bootbox.confirm({
            title:"注册用户",
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
                    param.userName = $("#addUserName").val();
                    param.realName = $("#addRealName").val();
                    param.password = $("#addPassword").val();
                    param.filePath = $("#addFilePath").val();
                    param.birthday = $("#addBirthday").val();
                    param.sex = $("[name=addSex]:checked").val();
                    param.phone = $("#addPhone").val();
                    param.email = $("#addEmail").val();
                    param.shengId = $("select[name=areaSelect]")[0].value;
                    param.shiId = $("select[name=areaSelect]")[1].value;
                    param.xianId = $("select[name=areaSelect]")[2].value;
                    $.ajax({
                        url:"/user/addUser.jhtml",
                        dataType:"json",
                        data:param,
                        success:function(result){
                            if(result.code==200){
                                alert("注册成功")

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




</script>

</body>
</html>


