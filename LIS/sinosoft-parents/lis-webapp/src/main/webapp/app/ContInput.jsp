<%@include file="../common/jsp/UsrCheck.jsp" %>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-08-15 11:48:42
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	String tPNo = "";
	try
	{
		tPNo = request.getParameter("PNo");
 
	}
	catch( Exception e )
	{
		tPNo = "";
	}

//得到界面的调用位置,默认为1,表示个人保单直接录入.
    /**********************************************
     *LoadFlag=1 -- 个人投保单直接录入
     *LoadFlag=2 -- 集体下个人投保单录入
     *LoadFlag=3 -- 个人投保单明细查询
     *LoadFlag=4 -- 集体下个人投保单明细查询
     *LoadFlag=5 -- 复核
     *LoadFlag=6 -- 查询
     *LoadFlag=7 -- 保全新保加人
     *LoadFlag=8 -- 保全新增附加险
     *LoadFlag=9 -- 无名单补名单
     *LoadFlag=10-- 浮动费率
     *LoadFlag=13-- 集体下投保单复核修改
     *LoadFlag=16-- 集体下投保单查询
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//默认情况下为个人保单直接录入
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "1";
	}

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	
  loggerDebug("ContInput","LoadFlag:" + tLoadFlag);
%>
<script language="javascript">
	
	//从服务器端取得数据:
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
	//alert("prtNo:"+prtNo);
	
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	//alert("ActivityID:"+ActivityID);
	var NoType = "<%=request.getParameter("NoType")%>";
	//alert("NoType:"+NoType);
	
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var operator = "<%=tGI.Operator%>";
	//alert("ContNo:"+ContNo);
	var scantype = "<%=request.getParameter("scantype")%>";
	var type = "<%=request.getParameter("type")%>";
	//保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == "null") BQFlag = "0";

	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";

	if (ScanFlag == "null") ScanFlag = "0";
	//保全调用会传险种过来
	var BQRiskCode = "<%=request.getParameter("riskCode")%>";
	//添加其它模块调用处理
	var LoadFlag ="<%=tLoadFlag%>"; //判断从何处进入保单录入界面,该变量需要在界面出始化前设置

	//alert("prtNo==="+prtNo);
	//hanlin 20050416
	var ContType = 1;
	
	//Q：Auditing 是团体人工核保时，查询个单明细时作为标志 Auditing=1
	var Auditing = "<%=request.getParameter("Auditing")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置

	/******************
   *  新契约：Auditing=null	
	 *
	 *****************/
	
	//alert("hl Auditing=="+Auditing);
	
	
	if(Auditing=="null")
	{
	  Auditing=0;
	}   
	    
	var MissionID = "<%=request.getParameter("MissionID")%>";
	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var AppntNo = "<%=request.getParameter("AppntNo")%>";
	var AppntName = "<%=request.getParameter("AppntName")%>";
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var param="";
	var checktype = "1";


	//end hanlin

</script>


<head>

  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
   <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                 在本页中引入其他JS页面              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <script src="../common/laydate/laydate.js"></script>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <SCRIPT src="../common/javascript/VerifyWorkFlow.js"></SCRIPT>
  <script src="ProposalAutoMoveNew.js"></script>

  <script src="../common/javascript/VerifyInput.js"></script>
  <script src="../common/javascript/MultiCom.js"></script>

  <script src="../common/javascript/jquery.js"></script>
  <script src="../common/javascript/jquery.imageView.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script>

	<script src="../common/javascript/Signature.js"></script>
  <%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){
%>


  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="../common/javascript/ScanPicView.js"></script>
  

  <script language="javascript">window.document.onkeydown = document_onkeydown;</script>

	

<%
}
%>

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


  
  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
             引入本页ContInit.jsp页面                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<%-- @include file="..\common\jsp\CodeQueryApplet.jsp" --%>
<%@include file="ContInit.jsp"%>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    引入本页ContInput.js           
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="ContInput.js"></script>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		-->
</head>

  
<body onLoad="initForm();initForm2();initElementtype();" >
  <form action="./ContSave.jsp" method=post name=fm id="fm" target="fraSubmit">

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                      引入合同信息录入控件         
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="ContPage.jsp"/>


<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                   多业务员MultiLine            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <div id="DivMultiAgent" style="display:none">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgent1);">
            </td>
            <td class= titleImg>
                其他业务员信息
            </td>
        </tr>
    </table>
   
    <div id="divMultiAgent1" style="display: ">
        <table class="common">
            <tr class="common">
                <td style="text-align:left" colSpan="1">
                    <span id="spanMultiAgentGrid">
                    </span>
                </td>
            </tr>
        </table>
    </div>
  </div>


<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                     业务员告知            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

  <div id="DivAgentImpart" style="display: ">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divAgentImpart1);">
            </td>
            <td class= titleImg>
                业务员告知
            </td>
        </tr>
    </table>
    <div id="divAgentImpart1" style="display: ">
      <table class="common">
        <tr class="common">
          <td class="common">
            <span id='spanAgentImpartGrid'>
            </span>
          </td>
        </tr>
      </table>
    </div>
  </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               引入投保人录入控件界面
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<jsp:include page="ComplexAppnt.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

    <!-- 告知信息部分（列表） -->
    <div id="DivLCImpart" style="display: ">
      <table>
          <tr>
              <td class=common>
                  <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCImpart1);">
              </td>
              <td class= titleImg>
                  投保人告知信息
                </td>
          </tr>
      </table>
   
   <div id="DivIncome0" style="display:none">
    <div class="maxbox">
     <table  class="common">  
       <tr class="common">
         <td  class="title">您每年固定收入</td>
          <td class=" input ">
            <input class="common wid" name=Income0 id="Income0" > 万元
          </td>
         
  
        <!--td class="title">万元</td-->
    
         <td  class="title">
            主要收入来源
         </td>             
         <td class="input">           
           <input class="common wid" name="IncomeWay0" id="IncomeWay0" onDblClick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" onKeyUp="return showCodeListKey('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');"><Input class="common" name="IncomeWayName0"  readonly="true">
         </td>              
         <td class="title"></td>
         <td class="input"></td>
         
       </tr>
       <tr class = "common">
       	 <td class="title">投保人身高</td>
          <td class=" input ">
            <input class="common wid" name=AppntStature id="AppntStature"  > 厘米
          </td>          
          <td class="title">投保人体重</td>
          <td class=" input " >
            <input class="common wid" name=AppntAvoirdupois id="AppntAvoirdupois"  > 公斤
          </td>
       </tr>	
       <tr>
         <td>
           <input class="cssButton" name=appntfinaimpart id="appntfinaimpart" type="button" value="投保人财务告知" onClick="">

         </td>
         <td>
                    <input class="cssButton" name=appnthealimpart id="appnthealimpart" type="button" value="投保人健康告知" onClick="">
         </td>
       </tr>
    </table> 
   </div> 
   </div> 
      <div id="divLCImpart1" style="display: ">
          <table class="common">
              <tr class="common">
                  <td style="text-align:left" colSpan="1">
                      <span id="spanImpartGrid">
                      </span>
                  </td>
              </tr>
          </table>
      </div>
    </div>

	<table class=common style="display:none">
		<tr  class= common> 
			<td  class= title>其它声明（200个汉字以内）</TD>
		</tr>
		<tr  class= common>
			<td  class= input>
				<textarea name="Remark1" verify="其他声明|len<800" verifyorder="1" cols="88" rows="4" class="common"  >
				</textarea>
			</td>
		</tr>
	</table>
    
    <div  id= "divButton" style= "display:none">
<%--jsp:include page="../common/jsp/InputButton.jsp"/--%>
      <span id="operateButton" >
       	<table  class=common align=center>
       		<tr align=right>
       			<td class=button >
       				&nbsp;&nbsp;
       			</td>
       			<td class=button width="10%" align=right>
       				<!-- <input class=cssButton name="addbutton" id="addbutton" VALUE="保  存"  TYPE=button onClick="return submitForm();"> -->
              <a href="javascript:void(0)" class=button name="addbutton" id="addbutton" onClick="return submitForm();">保  存</a>
       			</td>
       			<td class=button width="10%" align=right>
       				<!-- <input class=cssButton name="updatebutton" id="updatebutton" VALUE="修  改"  TYPE=button onClick="return updateClick();"> -->
              <a href="javascript:void(0)" class=button name="updatebutton" id="updatebutton" onClick="return updateClick();">修  改</a>
       			</td>
       			<td class=button width="10%" align=right>
       				<!-- <input class=cssButton name="deletebutton" id="deletebutton" VALUE="删  除"  TYPE=button onClick="return deleteClick();"> -->
              <a href="javascript:void(0)" class=button name="deletebutton" id="deletebutton" onClick="return deleteClick();">删  除</a>
       			</td>
       		</tr>
       	</table>
      </span>
    </div>


  	<!------############# FamilyType:0-个人，1-家庭单 #######################--------->
	<div  id= "divFamilyType" style="display:none">
		<table class=common>
			<tr class=common>
				<!---TD  class= title>个人合同类型</TD-->
					<!--Input class="code" name=FamilyType ondblclick="showCodeList('FamilyType',[this]);" onkeyup="return showCodeListKey('FamilyType',[this]);" onfocus="choosetype();"-->
					<input type="hidden" name=FamilyType id="FamilyType" value="1">
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               被保人列表MultiLine
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    
	<div  id= "divTempFeeInput" style= "display: ">
		<Table class=common>
			<tr>
				<td style="text-align: left" colSpan=1>
					<span id="spanInsuredGrid" ></span>
				</td>
			</tr>
		</Table>
	</div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               引入被保人录入控件
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="ComplexInsured2.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


    <!--table class=common>
      <TR  class= common>
        <TD  class= title> 特别约定及备注 </TD>
      </TR>
      <TR  class= common>
        <TD  class= title>
        <textarea name="GrpSpec" cols="120" rows="3" class="common" >
        </textarea></TD>
      </TR>
    </table-->
    <div id="DivLCImpartInsured" STYLE="display: ">
    <!-- 告知信息部分（列表） -->
      <table>
        <tr>
          <td class=common>
            <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpartInsured1);">
          </td>
          <td class= titleImg>
            被保险人告知信息
          </td>
        </tr>
      </table>
    </div>

   <div id="DivIncome" style="display:none">
    <div class="maxbox">
     <table  class="common">  
       <tr class="common">
         <td  class="title">您每年固定收入</td>
          <td class=" input">
            <input class="common wid" name=Income id="Income" >&nbsp;&nbsp;万元
          </td>
       
         <td  class="title">
            主要收入来源：
         </td>             
         <td class="input">           
           <input ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" class="codeno" name="IncomeWay" id="IncomeWay" onclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName" id="IncomeWayName" readonly="true">
         </td>   
           
        </tr>
          
         <tr class = "common">
       	 <td class="title">被保人身高</td>
          <td  class=" input">
            <input class="common wid"  name=Stature id="Stature" elementtype=nacessary>&nbsp;&nbsp;厘米
          </td>          
          <td class="title">被保人体重</td>
          <td  class=" input">
            <input class="common wid" name=Avoirdupois id="Avoirdupois"  elementtype=nacessary>&nbsp;&nbsp;公斤
          </td>
       </tr>	       
       <tr>
         <td>
           <input class="cssButton" name=insufinaimpart id="insufinaimpart" type="button" value="被保人财务告知" >

         </td>
         <td>
          <input class="cssButton" name=insuhealimpart id="insuhealimpart" type="button" value="被保人健康告知" >
         </td>
       </tr>
      </table> 
    </div>
    </div>     
      <div id="divLCImpartInsured1" style= "display:  ">
        <table  class= common>
          <tr  class= common>
            <td style="text-align: left" colSpan=1>
            <span id="spanImpartInsuredGrid" >
            </span>
            </td>
          </tr>
        </table>
      </div>
    
    <!-- 告知信息部分（列表） -->
<!--
    <div id="DivLCImpartInsuredDetail" STYLE="display: ">
      <table>
        <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart2);">
          </td>
          <td class="titleImg">
            被保险人告知明细信息
          </td>
        </tr>
      </table>
      
      <div  id="divLCImpart2" style= "display:  ">
        <table  class= common>
          <tr  class=common>
            <td text-align: left colSpan=1>
              <span id="spanImpartDetailGrid" >
              </span>
            </td>
          </tr>
        </table>
      </div>
    </div>
-->
    <div  id= "divAddDelButton" style= "display:none" align=right>
      <input type="button" class="cssButton" value="添加被保险人" onclick="addRecord();"> 
      <input type="button" class="cssButton" value="删除被保险人" onclick="deleteRecord();"> 
      <input type="button" class="cssButton" value="修改被保险人" onclick="modifyRecord();"> 
    </div>
    <!-- 被保人险种信息部分 -->
    <div id="DivLCPol" STYLE="display:block">
      <table>
        <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
          </td>
          <td class= titleImg>
            被保险人险种信息:
          </td>
        </tr>
      </table>
    
      <div  id= "divLCPol1" style= "display:  ">
        <table  class= common>
          <tr  class= common>
            <td style="text-align:left" colSpan=1>
              <span id="spanPolGrid" >
              </span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    
    
    
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!--end hanlin-->
    
    <br>
 <!-- 保单投资计划 -->
	 <Div  id= "divInvestPlanRate" style= "display: none" >
       <table>
        <tr class=common>	
       <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,spanInvestPlanRate);">
          </td>
    	 	<td class= titleImg>
    			 保单投资计划
    		</td>
    	  </tr>
    	    </table> 
        <table class="common" width="100%"> 
    	   <TR  class="common" >
       	<td class="title">保单帐户生效日类型</td> 
       <td class="input"><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=UintLinkValiFlag id="UintLinkValiFlag"  CodeData='0|^2|签单日生效^4|过犹豫期后生效' value=2 onclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" ondblclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);"><input class=codename name=UintLinkValiFlagName id="UintLinkValiFlagName" readonly=true ></td>
       <td class="title"></td>
       <td class="input"></td>
       <td class="title"></td>
       <td class="input"></td>
        </TR> 
        </table>
    		  <table  class= common>
    			
        	 <tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					  <span id="spanInvestPlanRate" >
					  </span>
				    </td>
			     </tr>
			      <tr  class= common>
			      	<td>
			      	</td>
			     </tr>
		    </table>
  	</Div>
<!--
  ###############################################################################
                              录单按钮 
  ###############################################################################
-->
	<div  id= "divInputContButton" style="display:none;float: left">
    
	<input class=cssButton VALUE="影像件查询" TYPE=button onclick="QuestPicQuery();">
	<input id="sqButton" class=cssButton VALUE="签名查询"  TYPE=button onclick="signatureQuery(this);" cancut="1" codetype="PrtNo">
	<input class=cssButton id="riskbutton4" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
	<input class=cssButton VALUE="问题件查询" TYPE=button onclick="QuestQuery();"> 
	<!--INPUT class=cssButton id="riskbutton1" VALUE="上一步" TYPE=button onclick="returnparent();"-->
	<input class=cssButton id="riskbutton3" VALUE="险种信息录入" TYPE=button onclick="intoRiskInfo();">
	<input class=cssButton id="riskbutton33" VALUE="险种删除" TYPE=button onclick="DelRiskInfo();">
	<input class=cssButton name="NotePadButton1" VALUE="记事本查看" TYPE=button onclick="showNotePad();">
	<input class=cssButton id="riskbutton2" VALUE="录入完毕"  TYPE=button onclick="inputConfirm(1);">
</div>
<!--
  ###############################################################################
                              复核按钮 
  ###############################################################################
-->
<div id = "divApproveContButton" style = "display:none;float: left">
	<table>
		<tr>
      <td>
			<input class=cssButton VALUE="影像件查询" TYPE=button onclick="QuestPicQuery();">	
			<input id="sqButton2" class=cssButton VALUE="签名查询"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
			<input class=cssButton id="Donextbutton5" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
			<input id="demo1" name="AppntChkButton" style = "display: " class=cssButton VALUE=投保人校验 TYPE=button onclick='AppntChk();'>
			<input id="demo2" name="InsuredChkButton" style = "display: " class=cssButton VALUE=被保人校验 TYPE=button onclick='InsuredChk();' disabled="disabled">
			<input class=cssButton id="intoriskbutton" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
		</td>
  </tr>
		<tr>
            <td>
			    <input class=cssButton VALUE="问题件查询" name="ApproveQuestQuery" id="ApproveQuestQuery" TYPE=button onclick="QuestQuery();">
				<!--INPUT class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();"-->
				<!--INPUT class=cssButton id="Donextbutton6" VALUE="下一步" TYPE=button onclick="intoInsured();"-->
				<input class=cssButton name="NotePadButton5" id="NotePadButton5" VALUE="记事本查看" TYPE=button onclick="showNotePad();">
				<input class=cssButton id="riskbutton5" VALUE="强制进入人工核保" TYPE=button onclick="forceUW();">
				<input class=cssButton id="Donextbutton4" VALUE=" 复核完毕 " TYPE=button onclick="inputConfirm(2);">
				<!--<input class="cssButton" id="exitButton" value="  返回  " type="button" onclick="exitAuditing();">-->
		</td></tr>
	</table>
</div>
<!--
  ###############################################################################
                              核保修改投保单按钮 
  ###############################################################################
-->

    <div id = "divchangplan" style="display:none;float:left">
    		<input id="sqButton3" class=cssButton VALUE="签名查询"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
      <!--input class=cssButton id="Donextbutton5" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
      <input class=cssButton VALUE=投保人校验 TYPE=button onclick='AppntChk();'>
      <input class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();"-->
      <input class=cssButton id="intoriskbutton3" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
      <!--input class=cssButton id="riskbutton34" VALUE="险种删除" TYPE=button onclick="DelRiskInfo();"-->
    </div>
    
<!--
  ###############################################################################
                              团单复核修改按钮 
  ###############################################################################
-->

    <div id = "divApproveModifyContButton" style = "display:none;float:right">
        <input class=cssButton id="intoriskbutton2" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
        	<input id="sqButton4" class=cssButton VALUE="签名查询"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
        <input class=cssButton id="riskbutton24" VALUE="险种删除" TYPE=button onclick="DelRiskInfo();">
    	<input class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();">
    	<input class=cssButton id="Donextbutton5" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
        <input class=cssButton VALUE=问题件回复 TYPE=button onclick="QuestQuery();">
        <input class=cssButton VALUE=问题件影像查询 TYPE=button onclick="QuestPicQuery();">
        <input id="demo5" name="AppntChkButton3" style = "display: " class=cssButton VALUE=投保人校验 TYPE=button onclick='AppntChk();'>
        <input id="demo6" name="InsuredChkButton3" style = "display: " class=cssButton VALUE=被保人校验 TYPE=button onclick='InsuredChk();' disabled="disabled">
    	  <input class=cssButton id="Donextbutton7" VALUE="问题件修改完毕" TYPE=button onclick="inputConfirm(3);">
        <input class="cssButton" id="exitButton2" value="返回" type="button" onclick="exitAuditing2();">
    	  <!--input class=cssButton id="Donextbutton8" VALUE="保  存"  TYPE=button onclick="submitForm();"-->
    	  <!--INPUT class=cssButton id="Donextbutton9" VALUE="下一步" TYPE=button onclick="intoInsured();"-->

    </div>
<!--
  ###############################################################################
                              个单问题件修改按钮 
  ###############################################################################
-->
<div id = "divProblemModifyContButton" style = "display:none;float:left">
	<table>
		<tr>
			<td>
			<input class=cssButton id="intoriskbutton2" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
			<input id="sqButton5" class=cssButton VALUE="签名查询"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
			<input class=cssButton id="riskbutton24" VALUE="险种删除" TYPE=button onclick="DelRiskInfo();"> 
			<input class=cssButton id="demo3" name="AppntChkButton2" VALUE=投保人校验 TYPE=hidden onclick='AppntChk();'>
			<input class=cssButton id="demo4" name="InsuredChkButton2" VALUE=被保人校验 TYPE=hidden onclick='InsuredChk();'>
			<input class=cssButton name="NotePadButton3" id="NotePadButton3" VALUE="记事本查看" TYPE=button onclick="showNotePad();"> 
			</td>
		</tr>
		<tr>
			<td>
            <input class=cssButton id="Donextbutton5" VALUE="问题件录入" TYPE=button onClick="QuestInput();"> 
			<input class=cssButton VALUE="   问题件回复   " TYPE=button onclick="QuestQuery();"-->
			<input class=cssButton VALUE="   问题件查询   " TYPE=button onclick="QuestQuery();">
			<input class=cssButton VALUE=" 问题件影像查询 " TYPE=button onclick="QuestPicQuery();">
			<input class="cssButton" id="exitButton10" value="投保人强制关联" type="button" onclick="customerForceUnion(0);">
			<input class="cssButton" id="exitButton11" value="被保人强制关联" type="button" onclick="customerForceUnion(1);">
			<input class=cssButton id="Donextbutton7" VALUE=" 问题件修改完毕 " TYPE=button onclick="inputConfirm(3);"> 
			<input class="cssButton" id="exitButton2" value="     返回     " type="hidden" onclick="exitAuditing2();">
			
			</td>
		</tr>
	</table>

</div>
<!--
  ###############################################################################
                              随动定制按钮 
  ###############################################################################
-->
    
    <div id="autoMoveButton" style="display:none">
	    <input type="button" name="autoMoveInput" id="autoMoveInput" value="随动定制确定" onclick="submitAutoMove('11');" class="cssButton">
	    <input type="button" name="Next" id="Next" value="下一步" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class="cssButton">
      <input class=cssButton id="intoriskbutton5" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
      <input value="返  回" class="cssButton" type="button" onclick="top.close();">
      <input type=hidden name="autoMoveFlag" id="autoMoveFlag">
      <input type=hidden name="autoMoveValue" id="autoMoveValue">
      <input type=hidden name="pagename" id="pagename" value="11">
    </div>
<!--
  ###############################################################################
                              查询投保单按钮 
  ###############################################################################
-->

    <div id = "divInputQuery" style="display:none;float:left">
    	<input id="sqButton6" class=cssButton VALUE="签名查询"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
      <input class=cssButton id="queryintoriskbutton" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
    </div>

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               隐藏控件位置                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <div  id= "HiddenValue" style="display:none;float: right">
    	<input type="hidden" id="fmAction" name="fmAction">
    	<!-- 工作流任务编码 -->
			<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
			<input type="hidden" name="MissionID" id="MissionID" value= "">
      <input type="hidden" name="SubMissionID" id="SubMissionID" value= "">
      
      <input type="hidden" name="ActivityID" id="ActivityID" value= "">
      <input type="hidden" name="NoType" id="NoType" value= "">
      <input type="hidden" name="PrtNo2" id="PrtNo2" value= "">
      
      <input type="hidden" name="ContNo" id="ContNo" value="" >
      <input type="hidden" name="ProposalGrpContNo" id="ProposalGrpContNo" value="">
      <input type="hidden" name="SelPolNo" id="SelPolNo" value="">
      <input type="hidden" name="BQFlag" id="BQFlag">  
      <input type='hidden' name="MakeDate" id="MakeDate">
      <input type='hidden' name="MakeTime" id="MakeTime">
      <input type='hidden' id="ContType" name="ContType">
      <input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
    </div> 
    
       
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


  
  
</form>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>


</body>

</html>
