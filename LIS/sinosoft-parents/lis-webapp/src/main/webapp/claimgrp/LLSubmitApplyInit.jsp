<%
//程序名称：LLSubmitApplyInit.jsp
//程序功能：发起呈报页面初始化
//创建日期：2005-05-30
//创建人  ：zhoulei
//更新记录：
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
    String tCurrentDate = PubFun.getCurrentDate();
%>                            
<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
	document.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	  
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('CustomerNo').value	= nullToEmpty("<%= tCustomerNo %>");
    document.all('CustomerName').value = nullToEmpty("<%= tCustomerName %>");
    //document.all('VIPFlag').value = nullToEmpty("<%= tVIPFlag %>");
    
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initInpBox()
{ 
    try
    {                      
        fm.SubPer.value = nullToEmpty("<%= tG.Operator %>");
        fm.SubDate.value = nullToEmpty("<%= tCurrentDate %>");   
        fm.SubDept.value = nullToEmpty("<%= tG.ManageCom %>");
        showOneCodeName('station','SubDept','SubDeptName');
              
        fm.SubType.value = "0"; //呈报类型
        showOneCodeName('llSubType','SubType','SubTypeName');
        fm.SubRCode.value = ""; //呈报原因
    }
    catch(ex)
    {
        alert("在LLSubmitApplyInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
        alert("在LLSubmitApplyInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}                                        

function initForm()
{
    try
    {
    	initParam();
        initInpBox();
        initSelBox();
        initQuery(); //查询出险人姓名--解决乱码问题  
    }
    catch(re)
    {
        alert("LLSubmitApplyInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

</script>