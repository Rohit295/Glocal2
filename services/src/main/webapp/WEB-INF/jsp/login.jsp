<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html lang="en">
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>Admin - Login</title>

<link rel="stylesheet" href="/bootstrap-3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/admin.css">

<script type="text/javascript" src="/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/bootstrap-3.3.0/js/bootstrap.min.js"></script>

<style type="text/css">
.container {
    width: 600px;
}
</style>
  
</head>
<body style="text-align: center;" marginheight="50" marginwidth="50">
  
    <div class="container" style="margin-top: 50px;">

        <h1 align="center">Admin Login</h1>
  
        <br>
  
        <form autocomplete="off" class="well form-inline" action="/login" method="POST">
  
            <input type="text" class="input-large" name="username" placeholder="Username">
            <input type="password" class="input-large" name="password" placeholder="Password">
            <button type="submit" class="btn" name="submit">Log in</button>

        </form>
  
    </div>
  
</body>
  
</html>