<%
//Creator :����
//Date :2007-01-15
%>
<!--�û�У����-->

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
    alert("ProposalSpotCheckInit.jsp����initInpBox()��ʼ��ʧ��");
  }
}
;

//function RegisterDetailClick(cObj)
//{
  	//var ex,ey;
  	//ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  	//ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
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
    alert("ProposalSpotCheckInit.jsp����InitForm()��ʼ��ʧ��");
  }
}

</script>


