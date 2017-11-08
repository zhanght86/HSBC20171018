<%
//程序名称：LLSubmitApplyDealInit.jsp
//程序功能：呈报信息处理页面控件的初始化
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

//接收报案页面传递过来的参数(//此处有错，重写)


function initForm()
{
	try
    {   
        initInpBox();
        initDivLLsubmit();
        initParam();
        easyQueryClick();
        if(fm.SubCount.value =="1") //呈报次数)
        {
       	 	DivHeadIdea.style.display="";    
        }
     }
    catch(re)
    {
    	alert("LLSubmitApplyDealInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
function initParam()
{	
	fm.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	      
    fm.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
    fm.all('SubNO').value =nullToEmpty("<%=tSubNo%>");
    fm.all('SubCount').value =nullToEmpty("<%=tSubCount%>");
    fm.all('MissionID').value =nullToEmpty("<%=tMissionID%>");
    fm.all('SubMissionID').value =nullToEmpty("<%=tSubMissionID%>");
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
function initDivLLsubmit()  //初始化DivLLSubmit框架
{
	try
	{
    	fm.ClmNo.value=""; //赔案号
        fm.SubNO.value="";  //呈报序号
        fm.SubCount.value=""; //呈报次数
        fm.CustomerNo.value=""; //出险人客户号
        fm.CustomerName.value=""; //出险人客户姓名
        fm.VIPFlag.value="";   //VIP客户
        fm.InitPhase.value=""; //提起阶段
        fm.SubType.value="";    //呈报类型
        fm.SubRCode.value="";  //呈报原因
        fm.SubDesc.value="";  //呈报描述
        fm.SubPer.value="";   //呈报人
        fm.SubDate.value="";  //呈报日期
        fm.SubDept.value="";  //呈报机构
        fm.SubState.value=""; //呈报状态
        fm.DispDept.value=""; //承接机构代码
        fm.DispPer.value="";  //承接人员编号       
        
     }
     catch(ex)
     {
    	alert("在LLSubmitApplyDealInit.jsp-->initDivLLsubmit()()函数中发生异常:初始化界面错误!");
     }
}

function initInpBox()  //
{
	try
	{
 		DivDispType.style.display="none";     
 		DivReportheadSubDesc.style.display="none";   
 		DivHeadIdea.style.display="none";        	     	
    }
     catch(ex)
     {
    	alert("在LLSubmitApplyDealInit.jsp-->initInpBox()函数中发生异常:初始化界面错误!");
     }
}

</script>
