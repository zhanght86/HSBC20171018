<script type="text/javascript">
function initForm()
{
	initInpBox();
	document.all("TempleteID").value="";
	document.all("TempleteName").value="";
	document.all("PrintID").value = "";
	document.all("TempleteType").value='0';
	document.all("TempleteTypeName").value='xls';
	document.all("Language").value='zh';
	document.all("LanguageType").value='中文';
	document.all("OutputType").value='0';
	document.all("OutputTypeName").value='xls';
	document.all("Output").value='0';
	document.all("OutputName").value='online';
	document.all("DefaultType").value='Y';
	document.all("DefaultFlag").value='是';
	document.all("FilePath").value="";
}
function initInpBox()
{
	try
	{ 
  
	}
	catch(ex)
	{
    	alert("-->InitForm函数中发生异常:初始化界面错误!");
	}
}
</script>
