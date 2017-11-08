<%
//Creator :张征
//Date :2007-01-15
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.bl.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
%>
<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('checkRate').value = '';
    document.all('checkMax').value = '';
    document.all('RiskCode').value = '';
    document.all('BussNo').value = '';
    document.all('ManageCom').value = '';
    //document.all('Remark').value = '';
  }
  catch(ex)
  {
    alert("ProposalSpotCheckInit.jsp——initInpBox()初始化失败");
  }
}
;

//function RegisterDetailClick(cObj)
//{
  	//var ex,ey;
  	//ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	//ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	//divDetailInfo.style.left=ex;
  	//divDetailInfo.style.top =ey;
    //divDetailInfo.style.display ='';
//}



function initForm()
{
  try
  {
    initInpBox();
  }
  catch(re)
  {
    alert("ProposalSpotCheckInit.jsp——InitForm()初始化失败");
  }
}

</script>


