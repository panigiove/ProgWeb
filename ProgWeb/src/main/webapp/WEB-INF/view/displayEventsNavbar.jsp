<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  .navbar {
    background-color: #A1C7A6;
  }

  .category-link {
    color: #fff;
    transition: background-color 0.3s, color 0.3s;
  }

  .category-link:hover,
  .category-link.active {
    background-color: #8ABF8C;
    color: #fff;
  }

  .category-link.active {
    background-color: #8ABF8C;
  }

  .header {
    color: #000;
    text-align: center;
    padding: 20px 0;
    margin-bottom: 20px;
    border-bottom: 1px solid #ddd;
  }
</style>

<header class="header">
  <h1>I Nostri Eventi</h1>
</header>

<nav class="navbar navbar-expand-lg navbar-light">
  <div class="container-fluid justify-content-center">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link category-link" href="#" data-category-id="1">Concerti</a>
      </li>
      <li class="nav-item">
        <a class="nav-link category-link" href="#" data-category-id="2">Spettacoli Teatrali</a>
      </li>
      <li class="nav-item">
        <a class="nav-link category-link" href="#" data-category-id="3">Eventi Sportivi</a>
      </li>
      <li class="nav-item">
        <a class="nav-link category-link" href="#" data-category-id="4">Visite Guidate a Mostre</a>
      </li>
      <li class="nav-item">
        <a class="nav-link category-link" href="#" data-category-id="5">Visite Guidate a Musei</a>
      </li>
    </ul>
  </div>
</nav>

<div id="eventContainer" class="row row-cols-1 row-cols-md-3 g-4 mt-3"></div>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.category-link').forEach(function (link) {
      link.addEventListener('click', function (e) {
        e.preventDefault();

        document.querySelectorAll('.category-link').forEach(function (link) {
          link.classList.remove('active');
        });

        this.classList.add('active');

        let categoryId = this.getAttribute('data-category-id');

        fetch('<%= request.getContextPath() %>/index/getEvents?categoryId=' + categoryId)
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
        let cardClass = '';
        if (event.availableSeats === 0 && event.availableStanding === 0) {
          cardClass = 'card-grigia';
        } else if (event.availableSeats === 0 || event.availableStanding === 0) {
          cardClass = 'card-rosso';
        }

        let card =
                '<div class="col-lg-4 col-md-6 mb-4 ml-2">' +
                '<div class="card ' + cardClass + '" style="width: 100%;">' +
                '<div class="card-body">' +
                '<h5 class="card-title">' + event.name + '</h5>' +
                '<p class="card-text">' + event.descrizione + '</p>' +
                '<p><strong>Luogo:</strong> ' + event.nomeLocation + '</p>' +
                '<p><strong>Inizio:</strong> ' + formatDate(event.start) + '</p>' +
                '<p><strong>Fine:</strong> ' + formatDate(event.end) + '</p>' +
                '<p><strong>Prezzo (Poltrone):</strong> €' + event.seatPrice.toFixed(2) + '</p>' +
                '<p><strong>Prezzo (In Piedi):</strong> €' + event.standingPrice.toFixed(2) + '</p>' +
                '<p><strong>Disponibilità (Poltrone):</strong> ' + (event.availableSeats === 0 ? '<span class="text-danger">Esaurito</span>' : event.availableSeats) + '</p>' +
                '<p><strong>Disponibilità (In Piedi):</strong> ' + (event.availableStanding === 0 ? '<span class="text-danger">Esaurito</span>' : event.availableStanding) + '</p>' +
                '<a href="<%= request.getContextPath() %>/event?eventId=' + event.id + '" class="btn btn-primary">Dettagli</a>' +
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
