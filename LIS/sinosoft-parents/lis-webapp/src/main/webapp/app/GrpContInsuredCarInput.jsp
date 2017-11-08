<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//程序名称：GrpContInsuredCarInput.jsp
//程序功能：
//创建日期：2006-10-23 11:10:36
//创建人  ：chenrong
//更新记录：  更新人    更新日期     更新原因/内容
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
    <title>车辆信息 </title>
</head>

<body  onload="initForm();" >
<form action="" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>    
    <table>
        <tr>
            <td class=common>
        	  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
        	</td>
        	<td class= titleImg>请输入查询车辆信息条件</td>
        </tr>
    </table>
    <Div  id= "divGroupPol1" style= "display: ''">
        <div class="maxbox1">
        <table  class= common>
            <TR  class= common>            
                <TD  class= title5>集体投保单号码</TD>
                <TD  class= input5><Input class="readonly wid" name=ProposalGrpContNo id="ProposalGrpContNo"></TD>
                <TD class= title5>车牌号</TD>
                <TD class= input5><Input class="common wid"  name=CarNo id="CarNo"></TD>
            </TR>
        </table>
        </div>
        <a href="javascript:void(0)" class=button onclick="queryCarInfo();">查  询</a>   
        <!-- <INPUT class=cssButton  value="查  询" onclick="queryCarInfo();" type=button> -->

        <table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuredCar);"></td>
        		<td class="titleImg">车辆信息</td>
        	</tr>
        </table>
        <Div  id= "divInsuredCar" style= "display: ''">
        	<table >
            	<tr  class= common>
        	  		<td text-align:left colSpan=1><span id="spanInsuredCarGrid" ></span></td>        	  		
        		</tr>
            </table>
            <Div id= "divPage" align=center style= "display: '' ">    
                <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
                <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 
                <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
                <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
            </Div> 
        </Div>
        <table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuredCar);"></td>
        		<td class="titleImg">险种信息</td>
        	</tr>
        </table>
        <Div  id= "divInsuredCar" style= "display: ''">
        	<table >
            	<tr  class= common>
        	  		<td text-align:left colSpan=1><span id="spanCarPolGrid" ></span></td>        	  		
        		</tr>
            </table>
            <Div id= "divPage" align=center style= "display: 'none' ">    
                <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage1.firstPage();"> 
                <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage1.previousPage();"> 
                <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage1.nextPage();"> 
                <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage1.lastPage();">
            </Div> 
        </Div>
               
    </Div>
    <Div  id= "divNotSaveCarButton" style= "display: 'none'" >       
        <a href="javascript:void(0)" class=button onclick="returnparent();">上一步</a> 
        <!-- <INPUT type=button class=cssButton value="上一步" onclick="returnparent();"> -->
    </Div>
    <Div  id= "divSaveCarButton" style= "display: ''" >        
            <a href="javascript:void(0)" class=button onclick="returnparent();">上一步</a>
            <a href="javascript:void(0)" class=button id="pisdbutton1" onclick="getin();">导入车辆清单</a>
            <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="delAllInsuredCar();">删除全部车辆信息</a>
            <!-- <INPUT type=button class=cssButton value="上一步" onclick="returnparent();">      
            <INPUT class=cssButton id="pisdbutton1" VALUE="导入车辆清单" TYPE=button onclick="getin();"> 
            <INPUT class=cssButton id="pisdbutton2" VALUE="删除全部车辆信息" TYPE=button onclick="delAllInsuredCar();">  -->        
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
