function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#uploadedImage')
                .attr('src', e.target.result)
                .width(150)
                .height(200);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function removeImage() { 
	document.getElementById("imgInp").value = "";
	document.getElementById("uploadedImage").src = "";
	document.getElementById("uploadedImage").style.width = 0;
	document.getElementById("uploadedImage").style.height = 0;
}