<html> 
<%
//�������ƣ�
//�����ܣ������ս���
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
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT> 
  <SCRIPT src="InsuAccBalaInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="InsuAccBalaInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./InsuAccBalaSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuAccRate);">
      </td>
      <td class= titleImg>
        ���ܽ���
      </td>
    	</tr>
    </table>
    <Div  id= "divInsuAccRate" style= "display: ''" class="maxbox1">	
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            ���ֱ���
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=RiskCode id=RiskCode  >
          </TD>
<!--          <TD  class= title>
            �ʻ�����
          </TD>
          <TD  class= input>
          	<Input class= common  name=InsuAccNo  >
          </TD>  -->
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#BalaDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=BalaDate id="BalaDate"><span class="icon"><a onClick="laydate({elem: '#BalaDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>                       
        </TR>               
   </table>       
    </Div>     

  <Div id= "divOperate" style= "display: ''">
  	<!--<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">-->
    <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">��    ��</a>
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
    
    <!--<Div  id= "divAllGrid" style= "display: ''">-->
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAllGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <center>
		    <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      	<INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      	<INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      	<INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">		</center>				
  	</div>
    <!--</Div>-->   
    <input type="hidden" name="transact">     
    <input type="hidden" name="AccType">   
 <!--   <Div  id= "divWordsRemakeByInfo" style= "display: ''">
			  <table class= common>
			    <TR  class= common> 
			      <TD width="100%" height="15%"  class= title> ��ע:</TD>
			    </TR>
			    <TR  class= common>
			      <TD height="85%"  class= title><textarea name="Remark" verify="��ע����|len<1000" verifyorder="1" cols="80" rows="3" class="common" ></textarea></TD>
			    </TR>
			  </table>
    </Div>
</Div>   --> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
