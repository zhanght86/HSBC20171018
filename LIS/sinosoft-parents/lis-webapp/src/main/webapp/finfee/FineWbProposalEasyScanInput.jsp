<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2008-06-26 
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPNo = "";
	try
	{
		tPNo = request.getParameter("PNo");
	}
	catch( Exception e )
	{
		tPNo = "";
	}

//LoadFlag   3-- 异常件
//           4-- 抽查件
 	
 		String tLoadFlag = ""; 
		tLoadFlag = request.getParameter( "LoadFlag" );
		loggerDebug("FineWbProposalEasyScanInput","tLoadFlag"+tLoadFlag);
	 

%>
<head >
<script>
     var loadFlag = "<%=tLoadFlag%>";  //判断从何处进入财务单处理界面,该变量需要在界面出始化前设置.
     var TempFeeNo = "<%=request.getParameter("TempFeeNo")%>";　
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	
	<%@include file="./FineWbProposalInit.jsp"%>
	<SCRIPT src="FineWbProposalInput.js"></SCRIPT>
	<SCRIPT src="ProposalAutoMove.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		

  <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
  	
</head>

<body  onload="initForm(); " >
  <form action="./FineWbProposalSave.jsp" method=post name=fm id=fm target="fraSubmit">
 
    <Div  id= "divALL0" style= "display: ''">
    　
    <!-- 财务单信息部分 -->
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
        </td>
        <td class= titleImg>
          财务收费修改          
        </td>
      </tr>
    </table>
    <Div  id= "divLCPol" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR  class= common>
         <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id=ManageCom verify="管理机构|code:station" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>    
           <TD CLASS=title>
		      交费日期
		  </TD>
		  <TD CLASS=input COLSPAN=1>
		      <Input NAME=PayDate id=PayDate class= "common wid" verify="交费日期|notnull&date"  onchange="changeEnterDate();">
          </TD>
          <TD  class= title>
            代理人编码
          </TD>
          <TD  class= input>
                  <Input NAME=AgentCode id=AgentCode VALUE="" MAXLENGTH=10 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return queryAgent();"onkeyup="return queryAgent2();" >
          </TD>
        </TR>
        <TR  class= common> 
          <TD  class= title>
            暂交费类型
          </TD>          
          <TD  class= input>
            <Input class="code" name=TempFeeType id=TempFeeType verify="暂交费类型|code:TempFeeType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('TempFeeType',[this]);" onkeyup="return showCodeListKey('TempFeeType',[this]);" readonly=readonly>
          </TD>          
          <TD  class= title>
            投保单印刷号
          </TD>
          <TD  class= input>
            <Input  class="common wid" name=OtherNo id=OtherNo verify="印刷号|len<=20" onchange="changeOtherNo();">
          </TD>
          <TD  class= title>
            单证印刷号
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=TempFeeNo id=TempFeeNo >
          </TD>
        </TR>                    
      </table>
    </Div>           
    
     <Div  id= "divTempFeeInput" style= "display: ''">
  <!--录入的暂交费表部分 -->
    <Table  >
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanTempGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
  <!--录入的暂交费分类表部分 -->
    <Table  class= common>
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanTempClassGrid" >
	 </span> 
	</td>
       </tr>
    </Table>         
    </Div>
        <input type=hidden id="fmAction" name="fmAction">
		<input  type=hidden id="fmLoadFlag" name="fmLoadFlag">
		<input type=hidden name="DealType" id=DealType value="<%=request.getParameter("DealType")%>" >
		<input type=hidden name="aftersave" id=aftersave value="0"   >　
		<input type=hidden name="TempFeeNoHide" id=TempFeeNoHide value=""   >　
  
  <Div  id= "divButton" style= "display: ''">
  <br>
  <%@include file="../app/ProposalOperateButtonSpec.jsp"%>
  </Div> 
  </Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>


