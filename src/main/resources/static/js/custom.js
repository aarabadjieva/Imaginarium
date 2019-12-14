/* JS Document */

/******************************

 [Table of Contents]

 1. Vars and Inits
 2. Set Header
 3. Initialize Hamburger
 4. Init Special Slider
 5. Init Video
 6. Init Search


 ******************************/

$(document).ready(function () {
    "use strict";

    /*

    1. Vars and Inits

    */

    var header = $('.header');
    var hamb = $('.hamburger');
    var hambActive = false;
    var menuActive = false;

    setHeader();

    $(window).on('resize', function () {
        setHeader();
    });

    $(document).on('scroll', function () {
        setHeader();
    });

    initHamburger();
    initSpecialSlider();
    initVideo();
    initSearch();

    /*

    2. Set Header

    */

    function setHeader() {
        if (window.innerWidth < 992) {
            if ($(window).scrollTop() > 100) {
                header.addClass('scrolled');
            } else {
                header.removeClass('scrolled');
            }
        } else {
            if ($(window).scrollTop() > 100) {
                header.addClass('scrolled');
            } else {
                header.removeClass('scrolled');
            }
        }
        // if(window.innerWidth > 991 && menuActive)
        // {
        // 	closeMenu();
        // }
        var fileName = location.href.split("/").slice(-1);
        if (fileName[0] === "") {
            document.getElementById("home").classList.add("active");
        } else if (fileName[0] === "about") {
            document.getElementById("about").classList.add("active");
        } else if (fileName[0] === "register") {
            document.getElementById("register").classList.add("active");
        } else if (fileName[0] === "login") {
            document.getElementById("login").classList.add("active");
        } else if (fileName[0] === "partner") {
            document.getElementById("partner").classList.add("active")
        } else if (fileName[0] === "blog") {
            document.getElementById("blog").classList.add("active");
        } else if (fileName[0] === "contacts") {
            document.getElementById("contacts").classList.add("active");
        }
    }

    /*

    3. Initialize Hamburger

    */

    function initHamburger() {
        if ($('.hamburger').length) {
            hamb.on('click', function (event) {
                event.stopPropagation();

                if (!menuActive) {
                    openMenu();

                    $(document).one('click', function cls(e) {
                        if ($(e.target).hasClass('menu_mm')) {
                            $(document).one('click', cls);
                        } else {
                            closeMenu();
                        }
                    });
                } else {
                    $('.menu_container').removeClass('active');
                    menuActive = false;
                }
            });
        }
    }

    function openMenu() {
        var fs = $('.menu_container');
        fs.addClass('active');
        hambActive = true;
        menuActive = true;
    }

    function closeMenu() {
        var fs = $('.menu_container');
        fs.removeClass('active');
        hambActive = false;
        menuActive = false;
    }

    /*

    4. Set Header

    */

    function initSpecialSlider() {
        if ($('.special_slider').length) {
            var specialSlider = $('.special_slider');
            specialSlider.owlCarousel(
                {
                    loop: true,
                    autoplay: false,
                    center: true,
                    stagePadding: 190,
                    margin: 5,
                    nav: false,
                    dots: false,
                    smartSpeed: 700,
                    responsive:
                        {
                            0: {items: 1, margin: 5, stagePadding: 0},
                            992: {items: 2, margin: 5, stagePadding: 130},
                            1280: {items: 3, margin: 5, stagePadding: 190}
                        }
                });
        }

        if ($('.special_slider_nav').length) {
            var next = $('.special_slider_nav');
            next.on('click', function () {
                specialSlider.trigger('next.owl.carousel');
            });
        }
    }

    /*

    5. Init Video

    */

    function initVideo() {
        $('.video').magnificPopup({
            disableOn: 700,
            type: 'iframe',
            mainClass: 'mfp-fade',
            removalDelay: 160,
            preloader: false,
            fixedContentPos: false
        });
    }

    /*

    6. Init Search

    */

    function initSearch() {
        if ($('.search').length) {
            var search = $('.search');
            search.on('click', function (e) {
                var target = $(e.target);
                if (!target.hasClass('ctrl_class')) {
                    $(this).toggleClass('active');
                }
            });
        }
    }
});

var modal = document.getElementById('id01');
var loginBut = document.getElementById('loginEvent');
var inputUser = document.getElementById("username");
var inputPass = document.getElementById("password");

window.onclick = function (event) {
    if (event.target !== modal && event.target !== inputPass && event.target !== inputUser && event.target !== loginBut) {
        modal.style.display = "none";
    }
};

var address = {lat: 42.6977, lng: 23.3219};

function initMap() {
    var map = new google.maps.Map(
        document.getElementById('map'), {zoom: 16, center: address});
    var marker = new google.maps.Marker({
        position: address, map: map,
    });

    var infoWindow = new google.maps.InfoWindow({
        content: 'Imaginarium'
    })

    marker.addListener('click', function () {
        infoWindow.open(map, marker)
    })
};

$(function () {
    $(".multiple").select2();
});





