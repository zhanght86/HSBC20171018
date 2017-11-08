 
<script type="text/javascript">
function initForm()
{
    initInpBox();
    document.all("PrintID").value='';
    document.all("PrintName").value = '';
    document.all("PrintObject").value='0';
    document.all("PrintObjectName").value='客户';
    document.all("PrintType").value='0';
    document.all("PrintTypeName").value='通知书';
    document.all("LanguageType").value='0';
    document.all("LanguageTypeName").value='用户语言';
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
