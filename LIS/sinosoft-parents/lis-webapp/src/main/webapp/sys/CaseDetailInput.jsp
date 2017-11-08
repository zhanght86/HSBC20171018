<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>             
<html>
<%
//程序名称：CaseDetailInput.jsp
//程序功能：综合查询之理赔明细信息查询
//创建日期：2004-2-19
//创建人  ：刘岩松
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="CaseDetailInput.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="CaseDetailInit.jsp"%>

	<title>理赔明细信息 </title>
</head>

<body  onload="initForm();" >
  <form action="./ShowCaseDetail.jsp" method=post name=fm id=fm target="fraSubmit">
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				理赔明细信息
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''" class=maxbox>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            报案号
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RptNo id=RptNo >
          </TD>
          <TD  class= title5>
            立案号
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RgtNo id=RgtNo >
          </TD>
		</TR>
				
		<TR  class= common>
          <TD  class= title5>
            审核类型 
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid"  readonly name=CheckType id=CheckType >
          </TD>
          <TD  class= title5>
            理赔状态
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=CaseState id=CaseState >
          </TD>
		</TR>
				
				<TR  class= common>
          <TD  class= title5>
            事故者类型 
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=CustomerType id=CustomerType >
          </TD>
          <TD  class= title5>
            事故者姓名
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=CustomerName id=CustomerName >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            出险日期
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AccidentDate id=AccidentDate >
          </TD>
          <TD  class= title5>
            出险地点
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AccidentSite id=AccidentSite >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            事故类型
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AccidentType id=AccidentType >
          </TD>
          <TD  class= title5>
            核赔意见
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=ClmDecision id=ClmDecision >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            报案日期
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RptDate id=RptDate >
          </TD>
          <TD  class= title5>
            报案人姓名
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RptOperatorName id=RptOperatorName >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            立案日期
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RgtDate id=RgtDate >
          </TD>
          <TD  class= title5>
            申请权利人姓名
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RgtantName id=RgtantName >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            合计给付金额
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=TotalPayMoney id=TotalPayMoney >
          </TD>
          <TD  class= title5>
            合计豁免金额
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=TotalReleaseMoney id=TotalReleaseMoney >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            已发生未领取生存保险金
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=LiveMoney id=LiveMoney>
          </TD>
          <TD  class= title5>
            保单红利
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=BonusMoney id=BonusMoney >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            合计退还保险费
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=TotalPermMoney id=TotalPermMoney >
          </TD>
          <TD  class= title5>
            合计扣款
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=TotalSubMoney id=TotalSubMoney >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            批单号码
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=PDNo id=PDNo>
          </TD>
		  <TD  class= title5>
            
          </TD>
          <TD  class= input5>
            
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title5>
            结案日期
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=EndCaseDate id=EndCaseDate >
          </TD>
          <TD  class= title5>
            通知日期
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=InformDate id=InformDate >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            理算审核人
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=Clmer id=Clmer >
          </TD>
          <TD  class= title5>
            签批人
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=ClmUWer id=ClmUWer >
          </TD>
				</TR>
				
				<tr >
        	<td class=common>
        	  <input class=common type= hidden name= RgtNo_1 id=RgtNo_1 >
        	  <input class=common type= hidden name= ClmUWNo id=ClmUWNo >
        	  <input class=common type= hidden name= PolNo id=PolNo >
        	  <input class=common type= hidden name= InsuredName id=InsuredName >
        		<input class=common type= hidden name= CaseNo id=CaseNo >
        		<input class=common type= hidden name= InsuredNo id=InsuredNo >
        		<input class=common type= hidden name= LPJC id=LPJC >
        		
        	</td>
				</tr>
				<TR  class= common>
          <TD  class= title5>
            给付通知人
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=InformName id=InformName >
          </TD>
          <TD  class= title5>
            保险金领取方式
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=CaseGetMode id=CaseGetMode >
          </TD>
				</TR>
			</table>
			
			<table class=common>
				<TR class= common>
			    <TD class= title>
 				    事故经过描述
  			  </TD>
		    </TR>

		    <TR  class= common>
  			  <TD  class= input>
    				<textarea class= "readonly wid" readonly name="AccidentReason" id=AccidentReason cols="110%" rows="3" witdh=25% class="common wid">
            </textarea>
    			</TD>
  		  </TR>
  		  
  		  <TR class= common>
          <TD  class= title>
            案件撤销原因
          </TD>
        </TR>
        
        <TR>
          <TD  class= input>
            <textarea class= "readonly wid" readonly name="RgtReason" id=RgtReason cols="110%" rows="3" witdh=25% class="common">
            </textarea>
           </TD>
        </TR>
        
        <TR class= common>
          <TD  class= title>
            批单内容
          </TD>
        </TR>
        
        <TR>
          <TD  class= input>
            <textarea class= "readonly wid" readonly name="PDContent" id=PDContent cols="110%" rows="3" witdh=25% class="common">
            </textarea>
           </TD>
        </TR>
        
        <tr>
        	<td>
        		<input class="cssButton" type=button value="二次核保" onclick="SecondUW()">
  					<input class="cssButton" type=button value="解约暂停" onclick="EndAgreement()">
  				</td>
  			</tr>
     </table>
     
  </Div>
</form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
  </body>
<script language="javascript">
try
{
  fm.ClmUWNo.value = '<%= request.getParameter("ClmUWNo") %>';
  fm.RgtNo_1.value = '<%= request.getParameter("RgtNo") %>';
  fm.InsuredName.value = '<%= StrTool.unicodeToGBK(request.getParameter("InsuredName")) %>';
	fm.PolNo.value = '<%= request.getParameter("PolNo")%>';
	fm.ClmUWer.value = '<%= StrTool.unicodeToGBK(request.getParameter("ClmUWer")) %>';
	fm.CheckType.value = '<%= StrTool.unicodeToGBK(request.getParameter("CheckType")) %>';	
	fm.Clmer.value = '<%= StrTool.unicodeToGBK(request.getParameter("Clmer")) %>';
	fm.LPJC.value = '<%= StrTool.unicodeToGBK(request.getParameter("LPJC")) %>'				
}
catch(ex)
{
  alert(ex);
}
</script>
</html>
