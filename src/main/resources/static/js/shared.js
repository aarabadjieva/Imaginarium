/* JS Document */

/******************************

 [Table of Contents]

 1. Vars and Inits
 2. Set Header
 3. Initialize Hamburger
 4. Init Parallax
 5. Init Accordions
 6. Init Tabs
 9. Init SVG
 10. Init Search
 11. Init modal

 ******************************/

$(document).ready(function () {
    "use strict";

    /*========================
    1. Vars and Inits
    =========================*/

    const header = $('.header');
    const hamb = $('.hamburger');
    let hambActive = false;
    let menuActive = false;

    setHeader();

    $(window).on('resize', function () {
        setHeader();
    });

    $(document).on('scroll', function () {
        setHeader();
    });

    initHamburger();
    initParallax();
    initAccordions();
    initTabs();
    initSvg();
    initSearch();

    /*==========================
    2. Set Header
    ===========================*/

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
        if (window.innerWidth > 991 && menuActive) {
            closeMenu();
        }

        let fileName = location.href.split("/").slice(-1);
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

    /*=============================
    3. Initialize Hamburger
    =============================*/

    function initHamburger() {
        if (hamb.length) {
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
        const fs = $('.menu_container');
        fs.addClass('active');
        hambActive = true;
        menuActive = true;
    }

    function closeMenu() {
        const fs = $('.menu_container');
        fs.removeClass('active');
        hambActive = false;
        menuActive = false;
    }

    /*========================
    4. Init Parallax
    =========================*/

    function initParallax() {
        const elements = $('.prlx_parent');
        // Add parallax effect to every element with class prlx
        // Add class prlx_parent to the parent of the element
        if (elements.length && $('.prlx').length) {

        }
    }

    /*=========================
    5. Init Accordions
    ==========================*/

    function initAccordions() {
        const accs = $('.accordion');

        if (accs.length) {
            accs.each(function () {
                let acc = $(this);

                if (acc.hasClass('active')) {
                    let panel = $(acc.next());

                    if (panel.css('max-height') === "0px") {
                        panel.css('max-height', panel.prop('scrollHeight') + "px");
                    } else {
                        panel.css('max-height', "0px");
                    }
                }

                acc.on('click', function () {
                    if (acc.hasClass('active')) {
                        acc.removeClass('active');
                        let panel = $(acc.next());

                        if (panel.css('max-height') === "0px") {
                            panel.css('max-height', panel.prop('scrollHeight') + "px");
                        } else {
                            panel.css('max-height', "0px");
                        }
                    } else {
                        acc.addClass('active');
                        let panel = $(acc.next());

                        if (panel.css('max-height') === "0px") {
                            panel.css('max-height', panel.prop('scrollHeight') + "px");
                        } else {
                            panel.css('max-height', "0px");
                        }
                    }
                });
            });
        }
    }

    /*==========================
    6. Init Tabs
    ===========================*/

    function initTabs() {
        const tab = $('.tab');
        if (tab.length) {
            tab.on('click', function () {
                tab.removeClass('active');
                $(this).addClass('active');
                let clickedIndex = tab.index(this);

                let panels = $('.tab_panel');
                panels.removeClass('active');
                $(panels[clickedIndex]).addClass('active');
            });
        }
    }


    /*==========================
    7. Init SVG
    ==========================*/

    function initSvg() {
        jQuery('img.svg').each(function () {
            let $img = jQuery(this);
            let imgID = $img.attr('id');
            let imgClass = $img.attr('class');
            let imgURL = $img.attr('src');

            jQuery.get(imgURL, function (data) {
                // Get the SVG tag, ignore the rest
                let $svg = jQuery(data).find('svg');

                // Add replaced image's ID to the new SVG
                if (typeof imgID !== 'undefined') {
                    $svg = $svg.attr('id', imgID);
                }
                // Add replaced image's classes to the new SVG
                if (typeof imgClass !== 'undefined') {
                    $svg = $svg.attr('class', imgClass + ' replaced-svg');
                }

                // Remove any invalid XML tags as per http://validator.w3.org
                $svg = $svg.removeAttr('xmlns:a');

                // Replace image with new SVG
                $img.replaceWith($svg);
            }, 'xml');
        });
    }

    /*========================
    8. Init Search
    =========================*/

    function initSearch() {
        const search = $('.search');

        if (search.length) {
            search.on('click', function (e) {
                let target = $(e.target);
                if (!target.hasClass('ctrl_class')) {
                    $(this).toggleClass('active');
                }
            });
        }
    }

    /*=============================
    9. Init modal
     =============================*/
    const modal = document.getElementById('id01');
    const loginBut = document.getElementById('loginEvent');
    const inputUser = document.getElementById("username");
    const inputPass = document.getElementById("password");

    window.onclick = function (event) {
        if (event.target !== modal && event.target !== inputPass && event.target !== inputUser && event.target !== loginBut) {
            modal.style.display = "none";
        }
    };

    $(function () {
        $(".multiple").select2();
    });

});

