<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpec.jsp
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
   String tQueryFlag = request.getParameter("QueryFlag");
%>
 <script language="JavaScript">
	var tQueryFlag = "<%=tQueryFlag%>";
 </script>                          

<html> 
<head >

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWManuSpec.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> �����б� </title>
  <%@include file="UWManuSpecInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tInsuredNo%>');" >

  <form method=post name=fm id="fm" target="fraSubmit" action= "./UWManuSpecChk.jsp">
   <br>
     <!-- ��������Ϣ -->
     <Div   style= "display: ''">
    	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsuredBasic);">
    		</td>
    		<td class= titleImg>
    			�����˻�����Ϣ
    		</td>
    	 </tr>
       </table>	
    </Div>
    
    <DIV id=DivLCInsuredBasic class="maxbox1" STYLE="display:''">
     <table class= common>
    	<tr  class= common>
	          <td  class= title>
	            ������������
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredName" id="InsuredName" value="">
	          </TD>
	          <td  class= title>
	            ������������
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredAge" id="InsuredAge" >
	          </TD>
	          <td  class= title>
	            ��������
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="ManageCom" id="ManageCom" >
	          </TD>
	      </tr>
     </table> 
    </DIV>
    <!-- �����˱���¼���֣��б� -->
     <Div  id= "divLCPol" style= "display: ''">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			�ѱ�����Լ��Ϣ
    		</td>
    	    </tr>
        </table>	
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanUWSpecGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    </Div>
    
    
   
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanUWResultSpecGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
    	 <table  style= "display: none">
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			��Լ��Ϣ
    		</td>
    	    </tr>
        </table>	
      	<table  class= common  style= "display: none">
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanUWSpecContGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    <Div  id= "divQuery" style= "display: ''">	
		<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
    		</td>
    		<td class= titleImg>
    			��Լģ��
    		</td>
    	    </tr>
        </table>	
     <Div  id= "divTemp" class="maxbox" style= "display: ''">
    	<table class=common>
    	  <TR  class= common>
    		<td class=title5>
         	��Լģ��
      	</td>
      	<td class=input5>
            <Input class="codeno" name=SpecTemp id="SpecTemp" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('SpecTemp',[this,SpecTempname,SpecReason,Remark],[0,1,2,3],null,'ch','temptype');" onDblClick="return showCodeList('SpecTemp',[this,SpecTempname,SpecReason,Remark],[0,1,2,3],null,'ch','temptype');" onKeyUp="return showCodeListKey('SpecTemp',[this,SpecTempname,SpecReason,Remark],[0,1,2,3],null,'ch','temptype');"><Input class="codename" name=SpecTempname id="SpecTempname" readonly elementtype=nacessary>
        </td>
        <TD  class= title5>
    	  		�Ƿ��·�
    			</TD>
        	<TD class=input5>
            <Input class="codeno" name=NeedPrintFlag id="NeedPrintFlag" CodeData = "0|^Y|�·�^N|���·�" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ondblclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onKeyUp="showCodeListKeyEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ><Input class=codename  name=IFNeedFlagName id="IFNeedFlagName" readonly = true>
        	</TD>
        	</TR>
        </table>
    	</Div>
    <table style= "display: none">
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
    		</td>
    		<td class= titleImg>
    			��Լ����
    		</td>
    	    </tr>
    </table>	
        
        <table class = common  style= "display: none">
    	<TR  class= common>
          <TD  class= title>
            ��Լԭ��
          </TD>
          </tr><tr>          
      <TD  class= input> <textarea name="SpecReason" id=SpecReason  cols="100%" rows="5" witdh=100% ></textarea></TD>
        </TR>
    </table>


    	
    	<table class = common>
    	<TR  class= common>
          <TD  class= title>
            �ر�Լ��
          </TD>
          </tr><tr>
          
      <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp; <textarea name="Remark" id=Remark class="common" cols="100%" rows="5" witdh=100% ></textarea></TD>
        </TR>
    	
     </table>
      
      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
       <INPUT type= "hidden" id="PolNo" name= "PolNo" value= "">
       <INPUT type= "hidden" id="InsuredNo" name= "InsuredNo" value= "">
      <INPUT type= "hidden" id="Flag" name= "Flag" value="">
      <input type= "hidden" id="UWIdea" name= "UWIdea" value="">
      <input type= "hidden" id="PrtNo" name= "PrtNo" value="">
      <INPUT type= "hidden" id="MissionID" name= "MissionID" value= "">
      <INPUT type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
      <INPUT type= "hidden" id="operate" name= "operate" value="">
      <INPUT type= "hidden" id="proposalcontno" name= "proposalcontno" value="">
      <INPUT type= "hidden" id="serialno" name= "serialno" value="">
      
      <INPUT type= "button" id="button1" name= "sure" value="��  ��" class= cssButton  onclick="submitForm(1)">			
      <INPUT type= "button" id="button3" name= "sure" value="��  ��" class= cssButton  onclick="submitForm(2)">			
      <INPUT type= "button" id="button4" name= "sure" value="ɾ  ��" class= cssButton  onclick="submitForm(3)">	
      <!--INPUT type= "button" id="button5" name= "sure" value="�޸��·����" class= cssButton  onclick="submitForm(4)"-->
    </Div>		
      <INPUT type= "button" id="button2" name= "back" value="��  ��" class= cssButton  onclick="top.close();">					
	
    <!--��ȡ��Ϣ-->
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
