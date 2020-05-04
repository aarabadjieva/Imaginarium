const URL = {
    about: '/api/about'
}

const partnerToString = (partner) => `
<div class="col-lg-3 col-md-6 mb-lg-0 mb-5">
 <div class="avatar mx-auto">
 <img src="${partner.logo}" class="rounded-circle z-depth-1"
  alt="Partner picture">
 </div>
 <h5 class="font-weight-bold mt-4 mb-3"><h3 class="font-weight-bold">${partner.name}</h3></h5>
 <p class="blue-text">Official page: <a href="${partner.website}"><strong>${partner.website}</strong></a></p>
 <p class="grey-text">${partner.description}</p>
 </div>
`

const guideToString = (guide) => `
<div class="col-lg-3 col-md-6 mb-lg-0 mb-5">
<div class="avatar mx-auto">
<img src="${guide.logo}" class="rounded-circle z-depth-1"
 alt="partner pictire">
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
        for (var key in result) {
            if (result.hasOwnProperty(key)) {
                Array.from(result[key]).forEach(partner => {
                    if (partner.sector !== 'GUIDES') {
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