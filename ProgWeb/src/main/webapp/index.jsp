<%@ page import="web.example.progweb.model.entity.Discount" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="web.example.progweb.model.entity.Category" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <title>EntriEasy - HOME</title>
</head>
<body>

<jsp:include page="/WEB-INF/view/topNavUser.jsp" />

<jsp:include page="WEB-INF/view/discountsDisplay.jsp" />


<br/>
<jsp:include page="/WEB-INF/view/mostClickedCard.jsp"/><br>
<jsp:include page="/WEB-INF/view/eventsNavbar.jsp"/><br>
<jsp:include page="/WEB-INF/view/footer.jsp"/>

<div style="height: 150px"></div>
</body>
</html>
