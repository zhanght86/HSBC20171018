<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//�������ƣ�ContGrpInsuredInput.jsp
//�����ܣ�
//�������ڣ�2004-11-26 11:10:36
//������  ��yuanaq
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<script>
	var prtNo ="<%=request.getParameter("prtNo")%>";
  var polNo ="<%=request.getParameter("polNo")%>";
  var scantype ="<%=request.getParameter("scantype")%>";
  var MissionID ="<%=request.getParameter("MissionID")%>";
  var ManageCom ="<%=request.getParameter("ManageCom")%>";
  var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
  var ActivityID = "<%=request.getParameter("ActivityID")%>";
  var NoType = "<%=request.getParameter("NoType")%>";
  var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == null||BQFlag == "null") BQFlag = "0";
	var LoadFlag = "<%=request.getParameter("LoadFlag")%>";
	if (LoadFlag == null||LoadFlag == "null") BQFlag = "0";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="ContGrpInsuredInput.js"></SCRIPT>
  <%@include file="ContGrpInsuredInit.jsp"%>
  <script>
	var turnPage = new turnPageClass(); 
</script>
  <title>�����˲�ѯ��Ϣ </title>
</head>

<body  onload="initForm();" >
  <form action="" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    
      <table>
    	<tr>
        <td class=common>
			  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
    		<td class= titleImg>
    			�������ѯ����������
    		</td>
    	</tr>
    </table>
    <div class="maxbox1">
      <Div  id= "divGroupPol1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
	 
          <TD  class= title>
            ����Ͷ��������
          </TD>
          <TD  class= input>
            <Input class="common wid" name=ProposalGrpContNo id=ProposalGrpContNo readonly>
          </TD>
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id=ManageCom >
          </TD>
          <TD class= title>
          �������˿ͻ���
        </TD>
        <TD class= input>
          <Input class="common wid" name=InsuredNo id=InsuredNo>
        </TD>
      </TR>
      <TR class= common>      
        
        <TD class= title>
          ����
        </TD>
        <TD class= input>
          <Input class="common wid" name=Name id=Name>
        </TD>
        <TD class= title>
          ֤������
        </TD>
        <TD class= input>
          <Input class="common wid" name=IDNo id=IDNo>
        </TD>
        <TD class= title>
          ���ϼ���
        </TD>
        <TD class= input>
          <Input class="common wid"  name=ContPlanCode id=ContPlanCode>
        </TD>
      </TR>
      </table>
      </Div>
      <a href="javascript:void(0)" class=button onclick="queryperinsure();">��  ѯ</a>
          <!-- <INPUT class=cssButton  value="��  ѯ" onclick="queryperinsure();" type=button> -->
    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPersonInsured);">
    		</td>
    		<td class="titleImg">��������Ϣ
  			</td>
    	</tr>
    </table>
    	<Div  id= "divPersonInsured" style= "display: ''">
        <table >
          <tr  class= common>
        	  <td style="text-align: left" colSpan=1>
    					<span id="spanPersonInsuredGrid" >
    					</span> 
    				</td>
    			</tr>
        </table>
    	</Div>
	
      <Div  align=center style= "display: none">
        
        <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div>
      <Div  id= "divSaveButton" style= "display: none" > 
         <a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>          
         <!-- <INPUT type =button class=cssButton value="��һ��" onclick="returnparent();">       -->
      </Div>
       <Div  id= "divSaveInsuredButton" style= "display: ''" >
           <hr>
           <a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>
           <a href="javascript:void(0)" class=button id="pisdbutton3" onclick="getintopersoninsured();">��ӱ�����</a>
           <a href="javascript:void(0)" class=button id="pisdbutton1" onclick="getin();">���뱻�����嵥</a>
           <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="delAllInsured();">ɾ��ȫ��������</a>
           <!-- <INPUT type =button class=cssButton value="��һ��" onclick="returnparent();">      
           <INPUT class=cssButton id="pisdbutton3"  VALUE="��ӱ�����"  TYPE=button onclick="getintopersoninsured();">    
           <INPUT class=cssButton id="pisdbutton1" VALUE="���뱻�����嵥" TYPE=button onclick="getin();"> 
           <INPUT class=cssButton id="pisdbutton2" VALUE="ɾ��ȫ��������" TYPE=button onclick="delAllInsured();"> --> 
           <!--INPUT class=cssButton id="pisdbutton2" VALUE="�����������嵥" TYPE=button onclick="getout();"--> 
           <hr>            
       </Div>  										
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
     
</body>
</html>
