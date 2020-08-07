/* JS Document */

/******************************
 [Table of Contents]

 1. Vars and Inits
 2. Init Special Slider
 3. Init Video
 ******************************/


$(document).ready(function () {
    "use strict";

    /*======================
    1. Vars & Inits
    ======================*/


    const specialSlider = $('.special_slider');
    const next = $('.special_slider_nav');
    initSpecialSlider();
    initVideo();


    /*======================
    2. Initialize Special slider
    ======================*/

    function initSpecialSlider() {
        if (specialSlider.length) {
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

        if (next.length) {
            next.on('click', function () {
                specialSlider.trigger('next.owl.carousel');
            });
        }
    }

    /*=================
    3. Init Video
    =================*/

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


})



