<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�UWErr.jsp
//�����ܣ��˹��˱�δ��ԭ���ѯ
//�������ڣ�2002-06-19 11:10:36
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWReason.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWReasonInit.jsp"%>
  <title>�����Ժ�δͨ��ԭ���嵥</title>
</head>
<body  onload="initForm(<%=tGrpContNo%>);" >
  <%@include file="../common/jsp/InputButton.jsp"%>
  <form name=fm id="fm">
    <table>
    	<tr>
    		<Input type=hidden name=GrpContNo id="GrpContNo">
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
    		<td class= titleImg>
    			 �Ժ�δͨ��ԭ���嵥
    		</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <div  id= "divFCDay" style= "display: ''">
    <table>
      <tr>
    		<td  class= common>
					 ���˿ͻ���
				</td>
				<td  class= input>
				   <Input class="common wid" name=InsuredNo id="InsuredNo" value="">
				</td>
				<td  class= common>
					 �ͻ�����
				</td>
				<td  class= input>
				   <Input class="common wid" name=InsuredName id="InsuredName" value="">
				</td>
				<TD  class= common>
						���ֱ���
				</TD>
        <TD class=input>
         	  <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=RiskCode id="RiskCode" onclick="return showCodeList('GrpRisk', [this, RiskName], [0, 1],null,<%=tGrpContNo%>,'b.GrpContNo',1);" ondblclick="return showCodeList('GrpRisk', [this, RiskName], [0, 1],null,<%=tGrpContNo%>,'b.GrpContNo',1);" onkeyup="return showCodeListKey('GrpRisk', [this, RiskName], [0, 1],null,<%=tGrpContNo%>,'b.GrpContNo',1);" ><input class=codename name=RiskName id="RiskName" readonly=true> 
        </TD>
     </tr>			
   </table>
	</div> 
  </div>
  <Div  id= "divShowuw" style= "display: ''" align= left> 
    <a href="javascript:void(0)" class=button onclick="QueryForm();">��  ѯ</a>
   <!--  <a href="javascript:void(0)" class=button style="display:none" onclick="excel();">����Excel�嵥</a>
     <INPUT VALUE="��  ѯ" class="cssButton"  TYPE=button onclick="QueryForm();"> 
	 <INPUT VALUE="����Excel�嵥" class="cssButton" style="display:none" TYPE=button  onclick="excel();" > --> 
	</Div>  
  <br>
	<Div  id= "divLCUWSub1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWReasonGrid" >
					</span> 
				</td>
			</tr>
		</table>
    <div align=center>
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onclick="getLastPage();"> 
    </div>
	</div>
  <a href="javascript:void(0)" class=button onclick="parent.close();">��  ��</a>
        <!-- <INPUT VALUE="��  ��" class= cssButton TYPE=button onclick="parent.close();"> 					 -->
    <!--��ȡ��Ϣ-->
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>
</body>
</html>
