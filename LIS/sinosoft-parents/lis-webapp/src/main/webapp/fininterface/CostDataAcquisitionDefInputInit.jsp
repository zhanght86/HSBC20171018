<%
//程序名称：CostDataAcquisitionDefInputInit.jsp
//程序功能：凭证费用数据采集定义
//创建日期：2008-08-18
//创建人  ：范昕  
%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>


<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) 
	{
		out.println("网页超时，请重新登录");
		return;
	}
	String strOperator = globalInput.Operator;
%>  
                       
<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    fm.reset();
    document.all('VersionNo').value = '';        
    document.all('VersionState').value = '';
    document.all('VersionState2').value = '';
    document.all('AcquisitionID').value = '';
    document.all('AcquisitionType').value = '';
    document.all('DataSourceType').value = '';
    document.all('CostOrDataID').value = '';
    document.all('DistillMode').value = '';
    document.all('DistillSQL').value = '';
    document.all('DistillSQLForOne').value = '';
    document.all('DistillClass').value = '';
    document.all('Remark').value = '';
    document.all('AcquisitionTypeName').value = '';
    document.all('CostOrDataName').value = '';
    document.all('DistillModeName').value = '';
    document.all('DataSourceTypeName').value = '';
    document.all('tempAcquisitionID').value = '';
    document.all('tempAcquisitionType').value = ''; 
    document.all('tempCostOrDataID').value = '';

    
    document.all('updatebutton').disabled = true;	
    document.all('deletebutton').disabled = true;	
  }
  catch(ex)
  {
    alert("在CostDataAcquisitionDefInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
function initAcquisition()
{
	document.all('tempAcquisitionID').value = '';
	<%
  	  String tAcquisitionID = "";  	  
  	  tAcquisitionID = PubFun1.CreateMaxNo("AcquisitionID",20);
        %>
    var ttAcquisitionID = "<%=tAcquisitionID%>";
    document.all('tempAcquisitionID').value = ttAcquisitionID;    
}

function initForm()
{
  try
  {
    initInpBox();
  }
  catch(re)
  {
    alert("在CostDataAcquisitionDefInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>
