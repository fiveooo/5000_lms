<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>LMS 5000 | login</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome-free/css/all.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <!-- icheck bootstrap -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/adminlte.min.css">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
    <!-- title logo -->
  <link href="${pageContext.request.contextPath}/img/logo_title.ico" rel="shortcut icon" type="image/x-icon">
</head>
<body class="hold-transition login-page" style="background-image:url('${pageContext.request.contextPath}/img/back_login.jpg');background-size:100%;background-repeat: no-repeat;background-position:center;background-color:aliceblue">
<div>
		<c:if test="${errMsg != null }">
			<p>${errMsg }</p>
		</c:if>
</div>
<div class="login-box" Style="background-color:#ffffffb3;padding:1em;border-radius: 20px;">  
<p style="text-align:center">
<img src="${pageContext.request.contextPath}/img/logo_main.png" alt="dd" style="width:40%;">
</p>
  <div class="login-logo">
    <a href="${pageContext.request.contextPath}/index">LMS<b>5000</b></a>
  </div>
  <!-- /.login-logo -->
  <div class="card">
    <div class="card-body login-card-body" Style="border-radius: 20px">
      <p class="login-box-msg">Sign in to start your session</p>

      <form action="${pageContext.request.contextPath}/index/login" method="post">
      
        <div class="input-group mb-3">
          <input type="text" name="userId" class="form-control" placeholder="Id">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-envelope"></span>
            </div>
          </div>
        </div>
        
        <div class="input-group mb-3">
          <input type="password" name="userPw" class="form-control" placeholder="Password">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-lock"></span>
            </div>
          </div>
        </div>
        
        <div class="row">
          <div class="col-8">
            <p class="mb-1">
        		<a href="${pageContext.request.contextPath}/user/findUserId">아이디찾기</a>
      		</p>
		     <p class="mb-0">
		       <a href="${pageContext.request.contextPath}/user/addUser" class="text-center">회원가입</a>
		     </p>
          </div>
          <!-- /.col -->
          <div class="col-4">
            <button type="submit" class="btn btn-primary btn-block">Sign In</button>
          </div>
          <!-- /.col -->
        </div>
        
      </form>

      
    </div>
    <!-- /.login-card-body -->
  </div>
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/dist/js/adminlte.min.js"></script>

</body>
</html>
