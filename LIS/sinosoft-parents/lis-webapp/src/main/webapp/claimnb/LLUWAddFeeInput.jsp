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
   loggerDebug("LLUWAddFeeInput"," tOperator:"+ tOperator);
   String tClmNo = request.getParameter("ClmNo");           //�ⰸ��
   String tBatNo = request.getParameter("BatNo");           //���κ�   
   loggerDebug("LLUWAddFeeInput"," ClmNo:"+ tClmNo);
   String tQueryFlag = request.getParameter("QueryFlag");
   if(tQueryFlag==null||tQueryFlag.equals("")){
     tQueryFlag="0";
   }
   
 %>  
 <script language="JavaScript">
	var tOperator = "<%=tOperator%>";
	var tQueryFlag = "<%=tQueryFlag%>";
 </script>                          
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="LLUWAddFee.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> �ӷѳб� </title>
  <%@include file="LLUWAddFeeInit.jsp"%>
 
</head>
<body  onload="initForm('', '<%=tContNo%>', '<%=tMissionID%>', '<%=tSubMissionID%>','<%=tInsuredNo%>');" >
	
	
  <form method=post name=fm id=fm target="fraSubmit" >
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
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
    </Div>
   
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec2);">
    		</td>
    		<td class= titleImg>
    			 �ɷ���Ϣ
    		</td>
    	</tr>
       </table>
       <Div  id= "divUWSpec2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanLCPremGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	</div>
    
   
        <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
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
    
    	
     <table class="common">
    	<TR  class= common>
          <TD  class= title>
            �ӷ�ԭ��
           </TD>
          <tr></tr>
          
      <TD colspan="6" style="padding-left:16px"> <textarea name="AddReason" id="AddReason" cols="224" rows="4" witdh=100% class="common"></textarea></TD>
        </TR>
     </table>
     

<!--�ӷѹ켣��Ϣ 2006-02-09 add by zhaorx-->
    <Table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLUWPSubGrid);"></TD>
            <TD class= titleImg>�ӷѹ켣��Ϣ</TD>
        </TR>
    </Table>  
	<Div  id= "divLLUWPSubGrid" align=center style= "display: ''">  	
	    <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanLLUWPremSubGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>   
        <table>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPageF.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPageF.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPageF.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPageF.lastPage();"></td>
            </tr>
        </table>    	 	
	</DIV>  
	  
     
     <table class=common style="display:none"> 
     	<tr  class=common>
     		<td  class=common>
		      <INPUT type= "button" id="button1"  name="sure"   value="ȷ  ��" class= cssButton onclick="saveClick()">			
		      <INPUT type= "button" id="button3"  name="delete" value="ɾ  ��" class= cssButton onclick="deleteClick();">      
		      <INPUT type= "button" id="button2"  name="back"   value="��  ��" class= cssButton onclick="top.close();">			  
		    </td>
		 </tr>
	 </table>
     <br>
     <a href="javascript:void(0);" id="button1"  name="sure" class="button" onClick="saveClick();">ȷ    ��</a>
     <a href="javascript:void(0);" id="button3"  name="delete" class="button" onClick="deleteClick();">ɾ    ��</a>
     <a href="javascript:void(0);" id="button2"  name="back" class="button" onClick="top.close();">��    ��</a>
      <INPUT type= "hidden" name="PolNo2" value= ""> <!--��ȫ�ӷ�����Եı���-->
      <INPUT type= "hidden" name="PolNo" value= ""> <!--��ȫ��Ŀ����Եı���,��ǰһҳ�洫��-->
      <INPUT type= "hidden" name="ContNo" value= "">
      <INPUT type= "hidden" name="MissionID" value= "">
      <INPUT type= "hidden" name="SubMissionID" value= "">
      <INPUT type= "hidden" name="InsuredNo" value="">
      
      <INPUT type= "hidden" name="RiskCode" value= "">
      <INPUT type ="hidden" name="DutyCode" value = "">
      <INPUT type= "hidden" name="AddFeeObject" value= "">
      <INPUT type= "hidden" name="AddFeeType" value="">
      <INPUT type= "hidden" name="SuppRiskScore" value= "">
      <INPUT type= "hidden" name="SecondScore" value= "">
      <INPUT type= "hidden" name="ClmNo" value=<%=tClmNo%>>
      <INPUT type= "hidden" name="BatNo" value=<%=tBatNo%>>      
      <input type=hidden name=hideOperate value=''> 
      
     <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
