$(document).ready(function(){
	var audio = document.getElementById("audio");
	var tracks = document.getElementsByClassName("track");
	audio.volume = .50;
	for (let i = 0; i < tracks.length ; i++) {
	    tracks[i].addEventListener("click", 
	        function (event) {
	            event.preventDefault();
	            link = $(this);
	            run(link, audio);
	        }, 
	        false);
	}
});

function run(link, player){
	player.src = link.attr('href');
	par = link.parent();
	par.addClass('active').siblings().removeClass('active');
    player.load();
    player.play();
}
