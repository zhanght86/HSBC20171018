<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>�ݽ�����Ϣ�޸�</title>
  
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT> 

  <script src="./TempFeeUpdate.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="TempFeeUpdateInit.jsp"%>

</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm id="fm" action="./TempFeeUpdateResult.jsp" target=fraSubmit method=post>
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    		</td>
    		 <td class= titleImg>
        		 �������ѯ������
       		 </td> 
    	</tr>
    </table>
    <div class="maxbox" id="maxbox">
    <table  class= common align=center>
      	
      	<TR  class= common>
          <TD  class= title>
          ���վݺ�
          </TD>
          <TD  class= input>
            <Input class="common wid" name=TempFeeNo id="TempFeeNo" >
          </TD>
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td>
        </TR>  
        <TR  class= common>
          <TD  class= title>
          Ͷ����ӡˢ��
          </TD>
          <TD  class= input>
            <Input class="common wid" name=OtherNo id="OtherNo" >
          </TD>
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td>
        </TR>  
    </table>
    <br>
    <br>
  <Div  id= "Frame1" style= "display: none">    
      <table  class= common align=center>    	
      <TR  class= common>
          <TD  class= title>
            �ݽ�������
          </TD>          
          <TD  class= input>
            <Input class="code" name=TempFeeType id="TempFeeType" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"
 verify="�ݽ�������|code:TempFeeType"  onMouseDown="return showCodeList('TempFeeType',[this]);" onDblClick="return showCodeList('TempFeeType',[this]);" onKeyUp="return showCodeListKey('TempFeeType',[this]);" readonly>
          </TD>  
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td>      
      </TR>	 
      
      <TR  class= common> 
          <TD  class= title>
            �������
          </TD>          
          <TD  class= input>
	     <Input class="readonly wid" name=ManageCom id="ManageCom" readonly=true > 
	  </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="common wid"  verify="��������|DATE&NOTNULL" name=PayDate id="PayDate">
          </TD>                                         
       </TR>         
       <TR  class= common>
          <TD  class= title>
            �����˱���
          </TD>
          <TD  class= input>
             <Input class="common wid" name=AgentCode id="AgentCode"  >          
          </TD>           
          <TD  class= title>
            ���������
          </TD>
          <TD  class= input>
            <Input class="common wid"  tabindex=-1 name=AgentGroup id="AgentGroup"  >
          </TD>
          </TR>
          <TR>
          <input type=hidden name="Opt" id="Opt">   
          </TR>	   
   </Table> 

   </Div> 
      	<Input class=cssButton type=Button  value="��  ѯ" onClick="submitForm()" name="Query" id="Query">

 </div>
  <Div  id= "Frame2" style= "display: none">     
  <!--�ݽ��ѱ��� -->  
    <Table  class= common>
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanTempGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    <Table>
    <tr>  </tr>
    </Table>




  <!--�ݽ��ѷ������ -->
    <Table  class= common>
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanTempClassToGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    <br>
    <table>
    <TR class=input>     
     <TD class=common>
     
      <input type =button class=cssButton value="�ύ�޸�" onClick="submitForm1();" name="Update" id="Update">        
      <input type =button class=cssButton value="��  ��" onClick="clearFormData();">   
     </TD>
    </TR>
    </table>
</Div>
<br><br><br><br><br>
<input type=hidden name=bComCode id="bComCode">
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form>
</body>
</html>










