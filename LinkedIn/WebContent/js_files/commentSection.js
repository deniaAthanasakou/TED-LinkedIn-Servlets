function submitForm(){
	// Get the input field
	var input = document.getElementById("comment");
	
	// Execute a function when the user releases a key on the keyboard
	input.addEventListener("keyup", function(event) {
	  // Cancel the default action, if needed
	  event.preventDefault();
	  // Number 13 is the "Enter" key on the keyboard
	  if (event.keyCode === 13) {
	    // Trigger the button element with a click
	    document.getElementById("commentForm").submit();
	  }
	});
}

function enableCommentsSection(child){
	var length = document.getElementsByClassName("commentsBox").length;
	var commentsSection = document.getElementsByClassName("commentsBox")[length-child];
	commentsSection.style.display = "block";
}