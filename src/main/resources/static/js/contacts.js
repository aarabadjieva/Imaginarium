/******************************
 [Table of Contents]

 1. Init Map

 ******************************/


	/*=====================
	1. Init map
	=====================*/

	let map;
	const address = {lat: 42.6977, lng: 23.3219};

	initMap();

	function initMap() {
		 map = new google.maps.Map(
			document.getElementById('map'), {zoom: 16, center: address});
		const marker = new google.maps.Marker({
			position: address, map: map,
		});

		const infoWindow = new google.maps.InfoWindow({
			content: 'Imaginarium'
		});

		marker.addListener('click', function () {
			infoWindow.open(map, marker)
		})
	}



