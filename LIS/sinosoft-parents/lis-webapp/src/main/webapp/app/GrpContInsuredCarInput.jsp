<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//�������ƣ�GrpContInsuredCarInput.jsp
//�����ܣ�
//�������ڣ�2006-10-23 11:10:36
//������  ��chenrong
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>  
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="GrpContInsuredCar.js"></SCRIPT>
    <%@include file="GrpContInsuredCarInit.jsp"%>
    <title>������Ϣ </title>
</head>

<body  onload="initForm();" >
<form action="" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>    
    <table>
        <tr>
            <td class=common>
        	  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
        	</td>
        	<td class= titleImg>�������ѯ������Ϣ����</td>
        </tr>
    </table>
    <Div  id= "divGroupPol1" style= "display: ''">
        <div class="maxbox1">
        <table  class= common>
            <TR  class= common>            
                <TD  class= title5>����Ͷ��������</TD>
                <TD  class= input5><Input class="readonly wid" name=ProposalGrpContNo id="ProposalGrpContNo"></TD>
                <TD class= title5>���ƺ�</TD>
                <TD class= input5><Input class="common wid"  name=CarNo id="CarNo"></TD>
            </TR>
        </table>
        </div>
        <a href="javascript:void(0)" class=button onclick="queryCarInfo();">��  ѯ</a>   
        <!-- <INPUT class=cssButton  value="��  ѯ" onclick="queryCarInfo();" type=button> -->

        <table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuredCar);"></td>
        		<td class="titleImg">������Ϣ</td>
        	</tr>
        </table>
        <Div  id= "divInsuredCar" style= "display: ''">
        	<table >
            	<tr  class= common>
        	  		<td text-align:left colSpan=1><span id="spanInsuredCarGrid" ></span></td>        	  		
        		</tr>
            </table>
            <Div id= "divPage" align=center style= "display: '' ">    
                <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
                <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 
                <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
                <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
            </Div> 
        </Div>
        <table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuredCar);"></td>
        		<td class="titleImg">������Ϣ</td>
        	</tr>
        </table>
        <Div  id= "divInsuredCar" style= "display: ''">
        	<table >
            	<tr  class= common>
        	  		<td text-align:left colSpan=1><span id="spanCarPolGrid" ></span></td>        	  		
        		</tr>
            </table>
            <Div id= "divPage" align=center style= "display: 'none' ">    
                <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage1.firstPage();"> 
                <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();"> 
                <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();"> 
                <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage1.lastPage();">
            </Div> 
        </Div>
               
    </Div>
    <Div  id= "divNotSaveCarButton" style= "display: 'none'" >       
        <a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a> 
        <!-- <INPUT type=button class=cssButton value="��һ��" onclick="returnparent();"> -->
    </Div>
    <Div  id= "divSaveCarButton" style= "display: ''" >        
            <a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>
            <a href="javascript:void(0)" class=button id="pisdbutton1" onclick="getin();">���복���嵥</a>
            <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="delAllInsuredCar();">ɾ��ȫ��������Ϣ</a>
            <!-- <INPUT type=button class=cssButton value="��һ��" onclick="returnparent();">      
            <INPUT class=cssButton id="pisdbutton1" VALUE="���복���嵥" TYPE=button onclick="getin();"> 
            <INPUT class=cssButton id="pisdbutton2" VALUE="ɾ��ȫ��������Ϣ" TYPE=button onclick="delAllInsuredCar();">  -->        
    </Div>
    <INPUT type=hidden name="ManageCom" id="ManageCom" value="">
    <INPUT type=hidden name="PrtNo" id="PrtNo" value="">
    <INPUT type=hidden name="PolNo" id="PolNo" value="">
    <INPUT type=hidden name="ScanType" id="ScanType" value="">
    <INPUT type=hidden name="MissionID" id="MissionID" value="">
    <INPUT type=hidden name="SubMissionID" id="SubMissionID" value="">
    <INPUT type=hidden name="ActivityID" id="ActivityID" value="">
    <INPUT type=hidden name="NoType" id="NoType" value="">
    <INPUT type=hidden name="GrpContNo" id="GrpContNo" value="">
    <INPUT type=hidden name="ScanType" id="ScanType" value="">
    <INPUT type=hidden name="LoadFlag" id="LoadFlag" value="">
</form>
<br>
<br>
<br>
<br>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
