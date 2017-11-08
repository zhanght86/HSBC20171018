<%
//程序名称：ComInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:40
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('ComCode').value = '';
    document.all('OutComCode').value = '';
    document.all('Name').value = '';
    document.all('ShortName').value = '';
    document.all('ComGrade').value = '';
    document.all('ComGradeName').value = '';
    document.all('Address').value = '';
    document.all('ZipCode').value = '';
    document.all('Phone').value = '';
    document.all('Fax').value = '';
    document.all('EMail').value = '';
    document.all('WebAddress').value = '';
    document.all('SatrapName').value = '';
    document.all('Sign').value = '';
    document.all('UpComCode').value = '';
    //document.all('ComAreaType').value = '';
    //document.all('ComAreaTypeName').value = '';
    document.all('ComCitySize').value = '';
    document.all('ComCitySizeName').value = '';
    document.all('IsDirUnder').value = '';
    document.all('IsDirUnderName').value = '';
	}
  catch(ex)
  {
    alert("在ComInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
//    setOption("t_sex","0=男&1=女&2=不详");
//    setOption("sex","0=男&1=女&2=不详");
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");
  }
  catch(ex)
  {
    alert("在ComInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
  }
  catch(re)
  {
    alert("ComInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>
