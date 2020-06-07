const URL = {
    about: '/api/about'
}

const partnerToString = (partner) => `
<div class="col-lg-3 col-md-6 mb-lg-0 mb-5">
 <div class="avatar mx-auto">
 <a href="/profile/partner/${partner.username}">
 <img src="${partner.logo}" onerror="this.src = '/images/partner_no_pic.jpg';" class="rounded-circle z-depth-1"
  alt="Partner picture">
  </a>
 </div>
 <h5 class="font-weight-bold mt-4 mb-3"><h3 class="font-weight-bold">${partner.name}</h3></h5>
 <p class="blue-text">Official page: <a href="/dummy"><strong>${partner.website}</strong></a></p>
 <p class="grey-text">${partner.description}</p>
 </div>
`

const guideToString = (guide) => `
<div class="col-lg-3 col-md-6 mb-lg-0 mb-5">
<div class="avatar mx-auto">
 <a href="/profile/guide/${guide.username}">
<img src="${guide.logo}" class="rounded-circle z-depth-1"
 alt="partner picture">
 </a>
</div>
<h5 class="font-weight-bold mt-4 mb-3"><h3 class="font-weight-bold">${guide.name}</h3></h5>
<p class="blue-text"><strong>Planet: </strong>${guide.planet}</p>
<p class="grey-text">${guide.description}</p>
</div>
`

fetch(URL.about)
    .then(resp => resp.json())
    .then(result => {
        let partnersResult = '';
        let guidesResult = '';
        for (var partners in result) {
            if (result.hasOwnProperty(partners)) {
                Array.from(result[partners]).forEach(partner => {
                    if (partner.sector !== 'GUIDE') {
                        const partnerString = partnerToString(partner);
                        partnersResult += partnerString;
                    } else {
                        const guideString = guideToString(partner);
                        guidesResult += guideString;
                    }
                })
            }
        }
        $('#about_all_partners').html(partnersResult)
        $('#about_all_guides').html(guidesResult)
    })

function readLess() {
    const dots = document.getElementById("dots");
    const moreText = document.getElementById("collapse");
    const btnText = document.getElementById("readMoreBtn");

    if (dots.style.display === "none") {
        dots.style.display = "inline";
        btnText.innerHTML = "Read More";
        moreText.style.display = "none";
    } else {
        dots.style.display = "none";
        btnText.innerHTML = "Read Less";
        moreText.style.display = "inline";
    }
}