<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FBochkarev</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        html,
        body {
            height: 100%;
        }

        .wrapper {
            position: relative;
            min-height: 100%;
        }

        .content {
            padding-bottom: 90px;
        }

        .footer {
            position: absolute;
            left: 0;
            bottom: 0;
            width: 100%;
            height: 80px;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="wrapper">
        <div class="content">
            <h3>FBochkarev presents</h3>
            <br/>
            <a href="<c:url value="/persons"/>" target="_blank">Register of person</a>
            <br/>
        </div>

        <div class="footer">
            © 2018 Copyright «FBochkarev» / Project: maven - spring - JPA(hibernate) - postgresSQL application v1.2
        </div>
    </div>
</div>
</body>
</html>
