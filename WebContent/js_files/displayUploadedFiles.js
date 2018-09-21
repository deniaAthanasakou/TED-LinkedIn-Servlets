$(document).ready(function(){
    $('#inputImages').change(function(e){
    	removeUploadList("images");
    	var j;
    	for(j=0;j< e.target.files.length; j++){
    		var fileName = e.target.files[j].name;
        	$("#uploadedFiles").append('<li class=\"list-group-item images\">' + fileName + '</li>');
    	}
    });
    
    $('#inputVideo').change(function(e){
    	removeUploadList("videos");
    	var j;
    	for(j=0;j< e.target.files.length; j++){
    		var fileName = e.target.files[j].name;
        	$("#uploadedFiles").append('<li class=\"list-group-item videos\">' + fileName + '</li>');
    	}
    });
    
    $('#inputAudio').change(function(e){
    	removeUploadList("audios");
    	var j;
    	for(j=0;j< e.target.files.length; j++){
    		var fileName = e.target.files[j].name;
        	$("#uploadedFiles").append('<li class=\"list-group-item audios\">' + fileName + '</li>');
    	}
    });
});

function removeUploadList(string){
	if(string=='images'){
		var x = document.getElementsByClassName("images");
		var i;
		for (i = 0; i < x.length; i++) {
		    x[i].innerHTML = "";
		    x[i].style.display = "none";
		}
	}else if(string=='videos'){
		var x = document.getElementsByClassName("videos");
		var i;
		for (i = 0; i < x.length; i++) {
		    x[i].innerHTML = "";
		    x[i].style.display = "none";
		}
	}else if(string=='audios'){
		var x = document.getElementsByClassName("audios");
		var i;
		for (i = 0; i < x.length; i++) {
		    x[i].innerHTML = "";
		    x[i].style.display = "none";
		}
	}else{
		document.getElementById("uploadedFiles").innerHTML = "";
	}
}