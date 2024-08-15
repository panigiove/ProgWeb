<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
  </script>
  <title>JSP - Hello World</title>

  <style>
    /* Classe per l'animazione di scorrimento */
    .slide-in {
      position: relative;
      animation: slideIn 0.5s ease-out forwards;
    }

    @keyframes slideIn {
      from {
        opacity: 0;
        transform: translateX(100%);
      }
      to {
        opacity: 1;
        transform: translateX(0);
      }
    }
  </style>

  <script>
    var discounts = [
      {
        title: "Sconto 10 euro per il concerto X",
        description: "Se acquisti almeno due loro magliette avrai sto sconto di merda"
      },
      {
        title: "Sconto del 20% sulla rappresentazione teatrale Y",
        description: "Entro la data 00/00/0000 se acquisterai il posto in platea, avrai diritto allo sconto"
      },
      {
        title: "Sconto del 40% alla degustazione di vini Z",
        description: "Bisogna prendere l'opzione più costosa però eh"
      },
      {
        title: "Sconto di 5 euro al museo W",
        description: "devi essere minore di 15 anni o residente nella provincia di Trento"
      }
    ];

    function update() {
      var randomIndex = Math.floor(Math.random() * discounts.length);
      var discount = discounts[randomIndex];
      var titleElement = document.getElementById("discountTitle");
      var descriptionElement = document.getElementById("discountDescription");

      titleElement.classList.remove("slide-in");
      descriptionElement.classList.remove("slide-in");

      void titleElement.offsetWidth;
      void descriptionElement.offsetWidth;

      titleElement.innerText = discount.title;
      descriptionElement.innerText = discount.description;

      titleElement.classList.add("slide-in");
      descriptionElement.classList.add("slide-in");
    }

    setInterval(update, 15000);

    window.onload = update;
  </script>

</head>
<body>

<h1><%= "Hello World!" %>
</h1>
<%
  String username = (String) session.getAttribute("user");
  if(username==null){
%>
    <a href="signUp.jsp">Sign Up</a>
    <a href="logIn.jsp">Login</a>
<%
  }
  else if(!username.equals("admin")){
%>
    <a href="WEB-INF/view/personal_page.jsp">Area Personale</a>
    <div id="button_logout">
      <button type="submit" name="logout">Logout</button>
    </div>
<%
  }
%>
<div style="height: 150px">
    <div class="container text-center mt-5">
        <div class="card mx-auto" style="width: 50%;">
            <div class="card-body">
                <h5 id="discountTitle" class="card-title"></h5>
                <p id="discountDescription" class="card-text"> </p>
            </div>
        </div>
    </div>
</div>

<br/>
<jsp:include page="WEB-INF/view/mostClickedCard.jsp"/><br>
<jsp:include page="WEB-INF/view/eventsNavbar.jsp"/><br>
<jsp:include page="WEB-INF/view/footer.jsp"/>

<script>
  document.getElementById("button_logout").addEventListener("submit", function (event) {
      fetch("${pageContext.request.contextPath}/LogoutServlet', { method: 'GET' }")
          .then(response => {
              if (response.redirected) {
                  window.location.href = "/index.jsp";
              }
          })
          .catch(error => console.error('Errore durante il logout:', error));

  })
</script>
</body>
</html>