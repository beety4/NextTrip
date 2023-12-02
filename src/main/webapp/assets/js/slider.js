const slider = document.getElementById("image-slider");
const slides = document.querySelectorAll(".slide");

let counter = 1;


setInterval(() => {
	if(slides.length == 1) {
		return;
	}
	slider.style.transition = "transform 0.5s ease-in-out";
	slider.style.transform = `translateX(${-counter * 100}%)`;

	counter++;

	// 마지막 슬라이드까지 도달하면 첫 번째 슬라이드로 이동
	if(counter === slides.length) {
		setTimeout(() => {
			slider.style.transition = "none";
			slider.style.transform = "translateX(0)";
			counter = 1;
		}, 5000);
	}
}, 5000);