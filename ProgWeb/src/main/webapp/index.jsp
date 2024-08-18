<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="web.example.progweb.model.entity.Discount" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EntriEasy - HOME</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
        #cookieBanner {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            background-color: #f8f9fa;
            box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
            border-bottom: 1px solid #dee2e6;
            display: none;
            z-index: 1050;
        }.cookie-banner-content {
             padding: 15px;
             display: flex;
             align-items: center;
             justify-content: space-between;
             max-width: 1200px;
             margin: 0 auto;
         }
        .cookie-banner-content .alert-title {
            flex: 1;
            margin-right: 10px;
        }
        .cookie-banner-buttons {
            display: flex;
            gap: 10px;
        }
        .btn-success {
            margin-right: 10px;
        }
        #overlay {
            background-color: rgba(128, 128, 128, 0.5);
            position: fixed;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            z-index: 1040;
            display: none;
        }
        .no-scroll {
            overflow: hidden;
        }

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

        #discountMessage {
            font-size: 16px;
            color: #d9534f;
            text-align: center;
            margin-top: 10px;
            display: none;
        }

        .cookie-banner-buttons {
            margin-bottom: 10px;
        }

        .cookie-banner-content .alert-link {
            display: block;
            margin-top: 10px;
            text-align: center;
        }
    </style>
</head>
<body>

<div id="cookieBanner" class="alert alert-dark alert-dismissible fade show" role="alert">
    <div class="cookie-banner-content">
        <div class="alert-title">
            <strong>Cookie Consent</strong> Utilizziamo i cookie per migliorare la tua esperienza. Accetti l'uso dei cookie su questo sito?
        </div>
        <div class="cookie-banner-buttons">
            <button type="button" class="btn btn-success" onclick="setPermissionCookies(true)">Accetto</button>
            <button type="button" class="btn btn-danger" onclick="setPermissionCookies(false)">Rifiuto</button>
        </div>
        <div class="mt-2 text-center">
            <a href="${pageContext.request.contextPath}/index/cookiesPolicy" class="alert-link">Ulteriori informazioni sui cookie</a>
        </div>
    </div>
</div>

<div id="overlay"></div>

<div id="content">
    <jsp:include page="/WEB-INF/view/topNavUser.jsp" />

    <div class="container text-center mt-5 mb-4">
        <div class="card mx-auto" style="width: 50%;">
            <div class="card-body">
                <div id="discountMessage" class="card-title"></div>
                <h5 id="discountTitle" class="card-title"></h5>
                <p id="eventName" class="card-text"></p>
                <p id="discountDescription" class="card-text"></p>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/view/display3MostClickedEvent.jsp"/><br>
    <jsp:include page="/WEB-INF/view/displayEventsNavbar.jsp"/><br>
    <jsp:include page="/WEB-INF/view/footer.jsp"/>
    <jsp:include page="/WEB-INF/view/returnTopPage.jsp"/>
</div>


<script>
    const discounts = <%= new Gson().toJson((List<Discount>) request.getAttribute("discounts")) %>;
    function update() {
        const titleElement = document.getElementById("discountTitle");
        const descriptionElement = document.getElementById("discountDescription");
        const eventNameElement = document.getElementById("eventName");
        const messageElement = document.getElementById("discountMessage")
        if (discounts.length > 0) {
            const randomIndex = Math.floor(Math.random() * discounts.length);
            const discount = discounts[randomIndex];

            titleElement.classList.remove("slide-in");
            descriptionElement.classList.remove("slide-in");
            eventNameElement.classList.remove("slide-in");

            //obbligo a ricaricare
            void titleElement.offsetWidth;
            void descriptionElement.offsetWidth;
            void eventNameElement.offsetWidth;

            titleElement.innerText = 'Sconto: ' + discount.discount + ' %';
            descriptionElement.innerText = 'Scadenza: ' + new Date(discount.expiration_date).toLocaleDateString('it-IT', { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', hour12: false });
            eventNameElement.innerText = 'Evento: ' + discount.nomeEvent;

            titleElement.classList.add("slide-in");
            descriptionElement.classList.add("slide-in");
            eventNameElement.classList.add("slide-in");
        }else{
            titleElement.style.display = 'none';
            descriptionElement.style.display = 'none';
            eventNameElement.style.display = 'none';
            messageElement.innerText = 'Al momento non ci sono sconti disponibili. Arriveranno prossimamente.';
            messageElement.style.display = 'block';
        }
    }

    function checkCookieConsent() {
        if (!<%= session.getAttribute("cookiesAccepted") != null %>)
        {
            const cookiesAccepted = localStorage.getItem('cookiesAccepted');
            const banner = document.getElementById('cookieBanner');
            const overlay = document.getElementById('overlay');
            const body = document.body;
            banner.style.display = 'block';
            overlay.style.display = 'block';
            body.classList.add('no-scroll');
        }
    }

    function setPermissionCookies(isPermitted) {
        const params = new URLSearchParams();
        params.append('cookiesAccepted', isPermitted);
        fetch('${pageContext.request.contextPath}/index/setPermission',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        })
            .then(response => response.json())
            .then(data => {
                const banner = document.getElementById('cookieBanner');
                const overlay = document.getElementById('overlay');
                const body = document.body;
                banner.style.display = 'none';
                overlay.style.display = 'none';
                body.classList.remove('no-scroll');
            })
            .catch(errror => {
                console.error('Error:', error);
            });

    }

    function onLoad (){
        checkCookieConsent();
        update();
    }

    window.onload = onLoad();
    setInterval(update, 15000);
</script>

</body>
</html>
