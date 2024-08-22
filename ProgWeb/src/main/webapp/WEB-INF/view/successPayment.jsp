<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>EntriEasy</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icon.svg">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="topNavUser.jsp"/>
<div class="container mt-5">
  <div class="alert alert-dismissible fade show" >
    <%
      String status = (String) request.getAttribute("status");
      if ("success".equals(status)) {
    %>
    <strong>Successo!</strong> L'acquisto del biglietto è andato a buon fine.
    <%
    } else {
    %>
    <strong>Errore!</strong> L'acquisto del biglietto è fallito. Per favore, riprova.
    <%
      }
    %>
  </div>

  <div class="d-flex justify-content-center mt-4">
    <a href="<%= request.getContextPath() %>" class="btn btn-primary">Torna alla Home</a>
  </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
