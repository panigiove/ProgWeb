<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">

  </script>
  <title>JSP - Hello World</title>
</head>
<body>

<h1><%= "Hello World!" %></h1>
<br/>
<jsp:include page="WEB-INF/view/mostSoldCard.jsp"/><br>
<jsp:include page="WEB-INF/view/eventsNavbar.jsp"/><br>
<jsp:include page="WEB-INF/view/footer.jsp"/>
</body>
</html>