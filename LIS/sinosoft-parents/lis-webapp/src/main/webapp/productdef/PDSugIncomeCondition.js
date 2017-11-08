


var conditionValue = "";
function cannelCondition(){
	returnParent();
}
function save(){
	conditionValue = "";
	var objTable = document.getElementById("TableRisk");
	var temp = "";
	for(var i = 0; i<objTable.rows.length; i++){
		var objInput = objTable.rows.item(i).cells.item(1);
		var inputValue = objInput.childNodes[0].value;
		if(inputValue==""||inputValue==null){
			if(conditionValue == ""){
				conditionValue = "*";
			}else{
				conditionValue = conditionValue+"/"+"*";
			}
		}else{
			if(conditionValue == ""){
				conditionValue = inputValue;
			}else{
				conditionValue = conditionValue+"/"+inputValue;
			}

		}
		if(i == objTable.rows.length-1)
			temp = temp +"*";
		else
			temp = temp +"*/"
	}
	if(temp == conditionValue)
		conditionValue = "*";
	top.opener.document.getElementById("TERMS").value = conditionValue;
	returnParent();
}


function returnParent(){
	top.opener.focus();
	top.close();
}

