<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpec.jsp
//�����ܣ���ȫ�˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
   GlobalInput tGlobalInput = new GlobalInput();
   tGlobalInput=(GlobalInput)session.getValue("GI");
   String tOperator = tGlobalInput.Operator;
   loggerDebug("UWManuAdd"," tOperator:"+ tOperator);
   String tQueryFlag = request.getParameter("QueryFlag");
   if(tQueryFlag==null||tQueryFlag.equals("")){
     tQueryFlag="0";
   }

 %>
 <script language="JavaScript" type="">
	var tOperator = "<%=tOperator%>";
	var tQueryFlag = "<%=tQueryFlag%>";
 </script>
<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" type="" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js" type=""></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js" type=""></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js" type=""></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js" type=""></SCRIPT>
  <SCRIPT src="UWManuAdd.js" type=""></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> �ӷѳб� </title>
  <%@include file="UWManuAddInit.jsp"%>

</head>
<body  onload="initForm('', '<%=tContNo%>', '<%=tMissionID%>', '<%=tSubMissionID%>','<%=tInsuredNo%>');" >


  <form method=post id="fm" name=fm target="fraSubmit" action="" >
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);" alt="">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
      </table>
        <Div  id= "divLCPol2" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid" >
  					</span>
  			  	</td>
  			  </tr>
    	  </table>
    	<tr class=common align=center>
        	
    	</tr>
    	<center>
    	<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();">
        	<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();">
        	<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();">
        	<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">
        	</center>
        </Div>

        <table>
    	<tr>
          <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);" alt="">
          </td>
    	  <td class= titleImg>
    	       �ӷ���Ϣ
    	  </td>
    	</tr>
        </table>
      	<Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanSpecGrid">
  					</span>
  			    	</td>
  			  </tr>
      	</table>
      	</div>


     <table class="common" style= "display: none" >
    	<TR  class= common>
          <TD  class= title>
            �ӷ�ԭ��
          </TD>
          <tr></tr>

      <TD  class= input> <textarea name="AddReason" id=AddReason cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      </TR>
     </table>
      <INPUT type="hidden" id="PolNo2" name="PolNo2" value= ""> <!--��ȫ�ӷ�����Եı���-->
      <INPUT type= "hidden" id="PolNo" name="PolNo" value= ""> <!--��ȫ��Ŀ����Եı���,��ǰһҳ�洫��-->
      <INPUT type= "hidden" id="ContNo" name="ContNo" value= "">
      <INPUT type= "hidden" id="MissionID" name="MissionID" value= "">
      <INPUT type= "hidden" id="SubMissionID" name="SubMissionID" value= "">
      <INPUT type= "hidden" id="InsuredNo" name="InsuredNo" value="">

      <INPUT type= "hidden" id="RiskCode" name="RiskCode" value= "">
      <INPUT type = "hidden" id="DutyCode" name = "DutyCode" value = ""><!---->
      <INPUT type= "hidden" id="AddFeeObject" name="AddFeeObject" value= ""><!--�ӷѶ���-->
      <INPUT type= "hidden" id="AddFeeType" name="AddFeeType" value=""><!--�ӷ�����-->
      <INPUT type= "hidden" id="SuppRiskScore" name="SuppRiskScore" value= "">
      <INPUT type= "hidden" id="SecondScore" name="SecondScore" value= "">



      <INPUT type= "button" id="button1"  name="sure" value="ȷ  ��" class= cssButton onclick="submitForm()">
      <INPUT type= "button" id="button2"  name="back" value="��  ��" class= cssButton onclick="top.close();">
      <INPUT type= "button" id="button3"  name="delete" value="ɾ  ��" class= cssButton onclick="deleteData()">
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
