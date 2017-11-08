<html>                                                     
<%
/*******************************************************************************
 * Name     :CaseInfoInput.jsp
 * Function :综合查询之理赔信息页面初始化程序
 * Date     :2004-2-18
 * Author   :LiuYansong
 */
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="CaseInfoInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CaseInfoInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form action="./CaseInfoQuery.jsp" method=post name=fm id=fm target="fraSubmit">
        <!-- 显示或隐藏LLCase1的信息 -->
     <table>
      <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLCase1);">
      </td>
      <td class= titleImg>
        理赔信息
      </td>
    	</tr>
    </table>

    <Div  id= "divLLCase1" style= "display: ''" class=maxbox1>
      <table  class= common>
        <TR  class= common>
          <TD class=title5>
            报案号
          </TD>
          <TD class =input5>
            <Input class="readonly wid" readonly name = RptNo >
          </Td>
          
          <TD class=title5>
            立案号
          </TD>
          <TD class=input5>
            <Input class="readonly wid" readonly name = RgtNo id=RgtNo>
          </TD>
        </TR>

        <TR class=common>
          <TD  class=title5>
            事故者类型
          </TD>
          <TD  class=input5>
            <Input class= "readonly wid" readonly name=Type id=Type >
          </TD>
          <TD  class=title5>
            事故者姓名
          </TD>
          <TD  class=input5>
            <Input class= "readonly wid" readonly name=InsuredName id=InsuredName >
          </TD>
          </TR> 
        
      </table>
	  <TD class=input>
            <input class="cssButton" type=button value="查询"  onclick="ShowCaseDetail()">
          </TD>
		  <TD class=input>
			<input class="common wid" type=hidden name =PolNo id=PolNo > 
		  </TD> 
    </Div>
    <!--立案保单信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCasePolicy);">
    		</td>
    		<td class= titleImg>
    			 审核信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divCasePolicy" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanCheckGrid" >
					</span>
				</td>
			</tr>
		</table>
  </div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
<br/><br/><br/><br/>
</body>
<script language="javascript">
try
{
  fm.RptNo.value = '<%= request.getParameter("RptNo") %>';
  fm.RgtNo.value = '<%= request.getParameter("RgtNo") %>';
  fm.InsuredName.value = '<%= StrTool.unicodeToGBK(request.getParameter("InsuredName")) %>';
  fm.Type.value = '<%= StrTool.unicodeToGBK(request.getParameter("Type")) %>';
	fm.PolNo.value = '<%= request.getParameter("PolNo")%>';
}
catch(ex)
{
  alert(ex);
}
</script>
</html>
