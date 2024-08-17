<%@ page import="web.example.progweb.model.entity.Discount" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
  async function fetchDiscounts() {
    try {
      const response = await fetch('<%= request.getContextPath() %>/user/getDiscounts');
      if (!response.ok) {
        throw new Error('Network response was not ok.');
      }
      return response.json();
    } catch (error) {
      console.error('Fetch error:', error);
      return [];
    }
  }

  async function update() {
    const discounts = await fetchDiscounts();

    if (discounts.length > 0) {
      const randomIndex = Math.floor(Math.random() * discounts.length);
      const discount = discounts[randomIndex];
      const titleElement = document.getElementById("discountTitle");
      const descriptionElement = document.getElementById("discountDescription");
      const eventNameElement = document.getElementById("eventName");

      // Rimuove le classi di animazione per il re-trigger
      titleElement.classList.remove("slide-in");
      descriptionElement.classList.remove("slide-in");
      eventNameElement.classList.remove("slide-in");

      // Trigger del reflow per riavviare l'animazione
      void titleElement.offsetWidth;
      void descriptionElement.offsetWidth;
      void eventNameElement.offsetWidth;

      // Aggiorna il contenuto con il nuovo sconto
      titleElement.innerText = 'Sconto: ' + discount.discount + ' %';
      descriptionElement.innerText = 'Scadenza: ' + new Date(discount.expiration_date).toLocaleDateString('it-IT', { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', hour12: false });
      eventNameElement.innerText = 'Evento: ' + discount.nomeEvent;

      // Riaggiunge la classe di animazione
      titleElement.classList.add("slide-in");
      descriptionElement.classList.add("slide-in");
      eventNameElement.classList.add("slide-in");
    }
  }

  // Esegue la funzione update solo all'avvio della pagina
  window.onload = update;
</script>

<div class="container text-center mt-5">
  <div class="card mx-auto" style="width: 50%;">
    <div class="card-body">
      <h5 id="discountTitle" class="card-title"></h5>
      <p id="eventName" class="card-text"></p>
      <p id="discountDescription" class="card-text"></p>
    </div>
  </div>
</div>
