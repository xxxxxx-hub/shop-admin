
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<nav class="navbar navbar-inverse" role="navigation"> <div class="container-fluid">
    <div class="navbar-header"><a class="navbar-brand" href="/product/toIndex.jhtml">商品展示</a></div>
    <div>
        <ul class="nav navbar-nav">
            <li><a href="/members/toIndex.do">会员展示</a></li>
            <li><a href="/brand/toIndex.do">品牌展示</a></li>
            <li><a href="/area/toIndex.do">地区展示</a></li>
            <li><a href="/type/toIndex.do">分类展示</a></li>
            <li><a href=">分类展示</a></li>
            <li><a href="></a></li>
            <li><a href=""></a></li>
            <li><a href="/user/outLogin.jhtml">退出</a></li>

        </ul>
    </div>
</div>
<%--
    <div class="col-sm-4" style="color:#fff"  >
        <div class="row">

            <div class="col-sm-2">
                <!-- 展示用户头像 -->
                <img src="${members.filePath}" height="30" width="30">
                &lt;%&ndash; <a href="<%=request.getContextPath()%>/UserController/logout.do">注销</a>&ndash;%&gt;
            </div>
            <div class="col-sm-10">
                欢迎您,${member.realName} !
                您今天是第${member.logCount}次登录!<br/>
                上次登录时间为 : <fmt:formatDate value="${user.upDate}" pattern="yyyy-MM-dd HH:mm:ss"/>  !
            </div>

        </div>

    </div>--%>
</nav>
</body>
</html>
