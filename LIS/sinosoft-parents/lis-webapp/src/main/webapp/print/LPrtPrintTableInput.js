function clickx(cSpanName,cTableName)
{
	try
	{
		var tSpanHtml = document.all(cSpanName).innerHTML;

		tSpanHtml = tSpanHtml.substring(tSpanHtml.indexOf("tbody")+6,tSpanHtml.length-16);
		
		var tTableHtml = GetVar(cSpanName+","+cTableName);
		//alert("添加" + cSpanName+"标签！");
		if(tTableHtml == "")
		{
			//tTableHtml = document.all(cSpanName).all(cTableName).innerHTML;
			tTableHtml = document.getElementById(cTableName).innerHTML;
			tTableHtml = tTableHtml.substr(7,tTableHtml.length-16);
			AddVar(cSpanName+","+cTableName,tTableHtml);
		}
		tSpanHtml += tTableHtml;
		tSpanHtml = "<TABLE class = common>" + tSpanHtml + "</TABLE>";
		document.all(cSpanName).innerHTML = tSpanHtml;
	}
	catch(ex)
	{
		alert(ex.message);
	}
}

var mArrayVars = new Array();

function GetVar(cVarName)
{
	var i;
	var j;
	try
	{
		i=mArrayVars.length;
		for (j=0;j<=i;j++)
		{
			if(mArrayVars[j][0]==cVarName)
			{
				return mArrayVars[j][1];
			}
		}
		return "";
	}
	catch(ex)
	{
		return "";
	}
}

function AddVar(cVarName,cVarValue)
{
	var i;
	try
	{
		i = mArrayVars.length;
		mArrayVars[i]=new Array();
		mArrayVars[i][0]=cVarName;
		mArrayVars[i][1]=cVarValue;
	}
	catch(ex)
	{
		return false;
	}
	return true;
}

function testPrint()
{
	fm.target = "../f1print";
	fm.action="./LPrtPrintTestSave.jsp";
	document.getElementById("fm").submit();
}

function autoFill(){
	$(fm).find("input").each(function(n){
		if(this.value==null||this.value==""){
			this.value = this.name;
		}
	});
}