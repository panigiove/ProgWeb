<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .back-to-top {
        position: fixed;
        bottom: 40px;
        right: 20px;
        color: white;
        border: none;
        padding: 10px 20px;
        border-radius: 50%;
        box-shadow: 0 4px 8px rgba(0,0,0,0.3);
        font-size: 16px;
        cursor: pointer;
        display: none;
        z-index: 1050;
        transition: opacity 0.3s ease;
    }

    .back-to-top.show {
        display: block;
    }
</style>

<button id="backToTop" class="btn bg-info back-to-top">
    ritorna su
</button>

<script>
    document.addEventListener('scroll', function() {
        var backToTopButton = document.getElementById('backToTop');
        if (window.scrollY > 170) {
            backToTopButton.classList.add('show');
        } else {
            backToTopButton.classList.remove('show');
        }
    });

    document.getElementById('backToTop').addEventListener('click', function() {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    });
</script>
