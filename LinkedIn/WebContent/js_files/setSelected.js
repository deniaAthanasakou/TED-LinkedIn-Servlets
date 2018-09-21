function setSelectedEducationLevel(str){
	var selectedOptions = str.replace("[", "").replace("]", "").split(",").map(function(item) {
		  return item.trim();
	});
	var selectionEduc = document.getElementById('educationLevel');
	var i,j;
	for(i=0;i<selectionEduc.length;i++){
		for(j=0;j<selectedOptions.length;j++){
			if(selectionEduc.options[i].value == selectedOptions[j]){
				selectionEduc.options[i].selected = true;
			}
		}
	}
}

function setSelectedJobFunctions(str){
	var selectedOptions = str.replace("[", "").replace("]", "").split(",").map(function(item) {
		  return item.trim();
	});
	var selectionJobFunction = document.getElementById('jobFunction');
	var i,j;
	for(i=0;i<selectionJobFunction.length;i++){
		for(j=0;j<selectedOptions.length;j++){
			if(selectionJobFunction.options[i].value == selectedOptions[j]){
				selectionJobFunction.options[i].selected = true;
			}
		}
	}
}

function setSelectedJobCompanyTypes(str){
	var selectedOptions = str.replace("[", "").replace("]", "").split(",").map(function(item) {
		  return item.trim();
	});
	var selectionComp = document.getElementById('companyIndustry');
	var i,j;
	for(i=0;i<selectionComp.length;i++){
		for(j=0;j<selectedOptions.length;j++){
			if(selectionComp.options[i].value == selectedOptions[j]){
				selectionComp.options[i].selected = true;
			}
		}
	}
}

function setSelectedJobType(str){
	var selectionType = document.getElementById('employmentType');
	selectionType.value = str;
}

function setSelectedExperience(str){
	var selectionExp = document.getElementById('seniorityLevel');
	selectionExp.value = str;
}
