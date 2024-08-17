<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Titolo centrale -->
<header class="bg-dark text-white text-center py-4">
  <h1>I Nostri Eventi</h1>
</header>

<!-- Navbar con le categorie -->
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
  <div class="container-fluid justify-content-center">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link text-white category-link" href="#" data-category-id="1">Concerti</a>
      </li>
      <li class="nav-item">
        <a class="nav-link text-white category-link" href="#" data-category-id="2">Spettacoli Teatrali</a>
      </li>
      <li class="nav-item">
        <a class="nav-link text-white category-link" href="#" data-category-id="3">Eventi Sportivi</a>
      </li>
      <li class="nav-item">
        <a class="nav-link text-white category-link" href="#" data-category-id="4">Visite Guidate a Mostre</a>
      </li>
      <li class="nav-item">
        <a class="nav-link text-white category-link" href="#" data-category-id="5">Visite Guidate a Musei</a>
      </li>
    </ul>
  </div>
</nav>

<!-- Container for event cards -->
<div id="eventContainer" class="row row-cols-1 row-cols-md-3 g-4 mt-3"></div>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.category-link').forEach(function (link) {
      link.addEventListener('click', function (e) {
        e.preventDefault();
        let categoryId = this.getAttribute('data-category-id');

        fetch('<%= request.getContextPath() %>/user/getEvents?categoryId=' + categoryId)
                .then(function (response) {
                  if (!response.ok) {
                    throw new Error('Network response was not ok');
                  }
                  return response.json();
                })
                .then(function (data) {
                  displayEvents(data);
                })
                .catch(function (error) {
                  console.error('Error fetching events:', error);
                });
      });
    });

    function displayEvents(events) {
      let container = document.getElementById('eventContainer');
      container.innerHTML = '';

      events.forEach(function (event) {
        // Determina le classi da applicare in base alla disponibilità
        let cardClass = '';
        if (event.availableSeats === 0 && event.availableStanding === 0) {
          cardClass = 'card-grigia'; // Card grigia se entrambi i tipi di posti sono esauriti
        } else if (event.availableSeats === 0 || event.availableStanding === 0) {
          cardClass = 'card-rosso'; // Card rossa se almeno un tipo di posto è esaurito
        }

        let card =
                '<div class="col-lg-4 col-md-6 mb-4">' +  // Adjust column sizing and margin-bottom
                '<div class="card ' + cardClass + '" style="width: 100%;">' +
                '<div class="card-body">' +
                '<h5 class="card-title">' + event.name + '</h5>' +
                '<p class="card-text">' + event.descrizione + '</p>' +
                '<p><strong>Location:</strong> ' + event.nomeLocation + '</p>' +  // Display location
                '<p><strong>Inizio:</strong> ' + formatDate(event.start) + '</p>' +  // Display start time
                '<p><strong>Fine:</strong> ' + formatDate(event.end) + '</p>' +  // Display end time
                '<p><strong>Prezzo (Poltrone):</strong> €' + event.seatPrice.toFixed(2) + '</p>' +  // Display seat price
                '<p><strong>Prezzo (In Piedi):</strong> €' + event.standingPrice.toFixed(2) + '</p>' +  // Display standing price
                '<p><strong>Disponibilità (Poltrone):</strong> ' + (event.availableSeats === 0 ? '<span class="text-danger">Esaurito</span>' : event.availableSeats) + '</p>' + // Seat availability
                '<p><strong>Disponibilità (In Piedi):</strong> ' + (event.availableStanding === 0 ? '<span class="text-danger">Esaurito</span>' : event.availableStanding) + '</p>' + // Standing availability
                '<a href="#" class="btn btn-primary">Dettagli</a>' +
                '</div>' +
                '</div>' +
                '</div>';

        container.insertAdjacentHTML('beforeend', card);
      });
    }

    function formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', hour12: false };
      const date = new Date(dateString);
      return date.toLocaleDateString('it-IT', options);
    }

  });
</script>