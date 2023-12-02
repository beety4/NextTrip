// Menu display JS
// Plan 위한거였으나 폐기처리
function showdown() {
	var summaryC = document.getElementById('summary');
	var detailsC = document.getElementById('details');
	var mapC = document.getElementById('map');
	
	document.getElementById('Asummary').classList.remove('active');
	summaryC.style.display = 'none';
	
	document.getElementById('Adetails').classList.remove('active');
	detailsC.style.display = 'none';
	
	document.getElementById('Amap').classList.remove('active');
	mapC.style.display = 'none';
}

function showup(choice, ele) {
	showdown();
	var what = document.getElementById(ele);
	choice.classList.add('active');
	what.style.display = 'block';
}

