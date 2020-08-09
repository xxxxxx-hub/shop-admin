
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>会员管理</title>
    <jsp:include page="/common/link.jsp"/>
</head>
<body>

<div class="container">
    <div class="row">
            <div class="col-md-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">会员查询</div>
                    <div class="panel-body">
                        <form class="form-horizontal" method="post" id="returnQuery" nctype="multipart/form-data">
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">会员名</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="memberName" id="memberName" placeholder="请输入会员名...">
                                </div>
                                <label  class="col-sm-2 control-label">真实性名</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="realName" id="realName" placeholder="请输入真实性名...">
                                </div>
                            </div>
                            <div class="form-group">

                                <label  class="col-sm-2 control-label">生日</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="minDate" id="minDate" placeholder="...">
                                        <span class="input-group-addon" id="basic-addon1">@</span>
                                        <input type="text" class="form-control" name="maxDate" id="maxDate" placeholder="...">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group" id="areaDiv">
                                <label class="col-sm-2 control-label">地区</label>
                                <div class="col-sm-10">
                                </div>
                            </div>

                            <div style="text-align: center;">
                                <button type="button" class="btn btn-primary" onclick="search();"><i class="glyphicon glyphicon-search"></i> 查询</button>
                                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
    </div>

    <table id="memberTable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr>
            <th>选择</th>
            <th>会员id</th>
            <th>会员名</th>
            <th>真实姓名</th>
            <th>手机</th>
            <th>邮箱</th>
            <th>生日</th>
            <th>地区</th>

        </tr>
          </thead>
        <tfoot>
        <tr>
            <th>选择</th>
            <th>会员id</th>
            <th>会员名</th>
            <th>真实姓名</th>
            <th>手机</th>
            <th>邮箱</th>
            <th>生日</th>
            <th>地区</th>
        </tr>
        </tfoot>

    </table>


</div>


<jsp:include page="/common/script.jsp"/>

<script>
    $(function () {
        initMemberTable();
        initDate("minDate");
        initDate("maxDate");
        initArea(1);
    })
    function initArea(id, obj) {
        if(obj){
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            type:"get",
            url:"/area/findChilds.do",
            data:{"id":id},
            success:function (result) {
                if (result.code == 200) {
                    var v_areaArr = result.data;
                    if(v_areaArr.length >0){
                        var v_areaHtml = ' <div class="col-sm-3">\n' +
                            '                        <select class="form-control" onchange="initArea(this.value,this)" name="areaSelect"><option value="-1">==请选择==</option></option>'

                        for (let area of v_areaArr) {
                            v_areaHtml += '<option value="' + area.id + '" data-area-name="'+area.name+'">' + area.name + '</option>';
                        }
                        v_areaHtml += '</select></div> ';
                        $("#areaDiv").append(v_areaHtml);
                    }
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

    function search() {
        var v_param = {};
        v_param.memberName = $("#memberName").val();
        v_param.realName = $("#realName").val();
        v_param.minDate = $("#minDate").val();
        v_param.maxDate = $("#maxDate").val();
        var shengId = $($("select[name='areaSelect']")[0]).val();
        var shiId = $($("select[name='areaSelect']")[1]).val();
        var xianId = $($("select[name='areaSelect']")[2]).val();

        v_param.shengId=shengId;
        v_param.shiId=shiId;
        v_param.xianId=xianId;
        memberTable.settings()[0].ajax.data = v_param;
        memberTable.ajax.reload();
    }
    var memberTable;
    function initMemberTable() {
        memberTable =  $('#memberTable').DataTable( {
            "language": {
                "url":"/js/DataTables/Chinese.json",
            },
            "searching":false,
            "lengthMenu": [3,10,15,20],
            "processing": true,
            "serverSide": true,
            "ajax":{
                url:"/members/findList.do",
                type:"get"
            },
            "columns": [
                {"data": "id",
                    render:function(data, type, row, meta) {
                        var html = "<input type='checkbox' name='id' value='"+data+"'>"
                        return html;
                    }},
                { "data": "id" },
                { "data": "memberName" },
                { "data": "realName" },
                { "data": "phone" },
                { "data": "mail" },
                { "data": "birthday"},
                { "data": "areaName"},
            ]

        } );

    }

</script>
</body>
</html>
