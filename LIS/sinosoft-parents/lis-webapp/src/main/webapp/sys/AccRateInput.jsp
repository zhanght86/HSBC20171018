<html> 
<%
//�������ƣ�
//�����ܣ����������������
//�������ڣ�2007-11-09 17:55:57
//������  ������ͥ
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="AccRateInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="AccRateInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./AccRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuAccRate);">
      </td>
      <td class= titleImg>
        ���ܽ�������¼��
      </td>
    	</tr>
    </table>
    <Div  id= "divInsuAccRate" style= "display: ''" class="maxbox">
     <input type="radio" name="AccRate" value=1 checked onclick="return displayAccClick();">  ��������
     <input type="radio" name="AccRate" value=0 onclick="return displayAccGClick();">  ��֤����   	
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            ���ֱ���
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=RiskCode id=RiskCode >
          </TD>
          <TD  class= title>
            �ʻ�����
          </TD>
          <TD  class= input>
          	<Input class="wid" class= common  name=InsuAccNo id=InsuAccNo >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#BalaDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=BalaDate id="BalaDate"><span class="icon"><a onClick="laydate({elem: '#BalaDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
          </TR>
                      
  </table>       
      
<Div id= "divAccRate" style= "display: ''">  
	      <table  class= common>
          <TR  class= common>                                          
           <TD  class= title>          	     
          ��������                        
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=Rate id=Rate >
          </TD>
          <TD  class= title>          	     
          Ӧ�ù�������                       
          </TD>
          <TD  class= input>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#SRateDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=SRateDate id="SRateDate"><span class="icon"><a onClick="laydate({elem: '#SRateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>    
          <TD  class= title>          	     
          ʵ�ʹ�������                        
          </TD>
          <TD  class= input>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#ARateDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ARateDate id="ARateDate"><span class="icon"><a onClick="laydate({elem: '#ARateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>    
          </TR>                       
     </table>
</Div>                 
<Div id= "divAccRateG" style= "display:none ">                        
         <table  class= common>
          <TR  class= common>
           <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=GruRate id=GruRate >
          </TD>  	
          <TD  class= title>
            ��ʼ����
          </TD>
          <TD  class= input>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#RateStartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RateStartDate id="RateStartDate"><span class="icon"><a onClick="laydate({elem: '#RateStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD> <!--</TR>
           <TR  class= common>-->
          <TD   class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" onClick="laydate({elem: '#RateEndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RateEndDate id="RateEndDate"><span class="icon"><a onClick="laydate({elem: '#RateEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>             
       </table>
</Div></Div>      

  <Div id= "divOperate" style= "display: ''">
			<!--<INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return addClick();">
			<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
			<INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">-->
            <a href="javascript:void(0);" name="addbutton" class="button" onClick="return addClick();">��    ��</a>
            <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">��    ��</a>
            <a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">ɾ    ��</a>
  </Div>  
           
      <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLMInsuAccRate);">
            </td>
            <td class= titleImg>
                     ������ϸ
            </td>
    	</tr>
     </table>
    <Div  id= "divLMInsuAccRate" style= "display: ''">
    <table  class= common>
            <tr  class= common>
                    <td text-align: left colSpan=1>
                                    <span id="spanLMInsuAccRateGrid" >
                                    </span>
                            </td>
                    </tr>
            </table>
    </div>
      	<Div  id= "divAllGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAllGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
					
  	</div>
    </Div>   
    <input type="hidden" name="transact">     
    <input type="hidden" name="AccType">   
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
