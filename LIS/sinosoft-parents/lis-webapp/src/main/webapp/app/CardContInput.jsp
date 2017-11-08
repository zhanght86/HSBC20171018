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
	
	
  loggerDebug("CardContInput","LoadFlag:" + tLoadFlag);
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
    var ComCode = "<%=tGI.ComCode%>";
    var ContNo = "<%=request.getParameter("ContNo")%>";
	//alert("ComCode:"+ComCode);
	var scantype = "<%=request.getParameter("scantype")%>";
	var type = "<%=request.getParameter("type")%>";
	//保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == "null") BQFlag = "0";

	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
	var BankFlag = "<%=request.getParameter("BankFlag")%>";

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
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  
  

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                 在本页中引入其他JS页面              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script> 
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="ProposalAutoMove2.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
  <%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){
%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
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

<%@include file="CardContInit.jsp"%>
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
  <script src="CardContInput.js"></script>
 
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
</head>


<body  onload="initForm();initForm2();initElementtype();queryinfo();" >
  <form action="./CardContSave.jsp" method=post name=fm id="fm" target="fraSubmit">

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                      引入合同信息录入控件         
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="CardContPage.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

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
   
    <div id="divMultiAgent1" style="display:none">
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
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                     业务员告知            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

  <div id="DivAgentImpart" style="display:none">
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
    <div id="divAgentImpart1" style="display:none">
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
<jsp:include page="CardComplexAppnt.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


    <!-- 告知信息部分（列表） -->
    <div id="DivLCImpart" style="display:none">
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
   <div id=DivIncome0 style="display:none">
   	<table class=common>
    <tr  class= common> 
      <td  class= common>其它声明（200个汉字以内）</TD>
    </tr>
    <tr  class= common>
      <td  class= common>
        <textarea name="Remark" id=Remark verify="其他声明|len<800" verifyorder="1" cols="110" rows="2" class="common" >
        </textarea>
      </td>
    </tr>
  </table>
     <table  class="common">  
       <tr class="common">
         <td class="title">您每年固定收入</td>
          <td>
            <input class=common name=Income0 id=Income0 verify="投保人收入|NUM&LEN<=8" verifyorder='1' >万元
          </td>
         
  
        
         <td  class="title">
            主要收入来源：
         </td>             
         <td class="input">
           <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
           <input class="codeno" name="IncomeWay0" id="IncomeWay0" verify="主要收入来源|LEN<4"  verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName0"  readonly="true">
         </td>              
         
         
       </tr> 
    </table> 
   </div>  
      <div id="divLCImpart1" style="display:none">
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


	

    
    <div  id= "divButton" style= "display:none">
<%--jsp:include page="../common/jsp/InputButton.jsp"/--%>
      <span id="operateButton" >
       	<table  class=common align=center>
       		<tr align=right>
       			<td class=button >
       				&nbsp;&nbsp;
       			</td>
       			
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">
       			</td>
       		</tr>
       	</table>
      </span>
    </div>

  </div>
  

    <!--将被保人信息与投保人界面合并 hanlin 20050416-->
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>

    <!--input type=hidden id="fmAction" name="fmAction"-->

    <div  id= "divFamilyType" style="display:none">
      <table class=common>
        <tr class=common>
          
          <TD  class= input>
            <!--Input class="code" name=FamilyType ondblclick="showCodeList('FamilyType',[this]);" onkeyup="return showCodeListKey('FamilyType',[this]);" onfocus="choosetype();"-->
            <input type="hidden" name=FamilyType value="1">
          </TD>
        </tr>
        
      </table>
    </div>
    
    
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               被保人列表MultiLine
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    
    
    
	    
	    
    <div  id= "divTempFeeInput" style= "display:none">
    	<table class=common>
    	  <tr>
    		  <td style="text-align: left" colSpan=1>
	  				<span id="spanInsuredGrid" >
	  				</span>
	  			</td>
    		</tr>
    		<tr CLASS=common>
    <TD CLASS=title>
     缴费期限
    </TD>
    <TD class=input>
        <Input name="PayEndYear" id="PayEndYear">
		<input class=codename name=PayEndYearName id="PayEndYearName" readonly=true elementtype=nacessary>
    </TD>
  </TR>
  <TR CLASS=common>

	  <TD CLASS=title>
      保险期间
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYear id="InsuYear" VALUE="" class="common wid" >
    </TD>


    <TD CLASS=title>
      交费方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayIntv id="PayIntv" VALUE="" class="common wid" >
    </TD>

 </TR>
  <TR CLASS=common>
    

    <TD CLASS=title>
      保险期间单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYearFlag id="InsuYearFlag" value="A" class="common wid">
    </TD>

   <TR CLASS=common>
    <TD CLASS=title>
      交费期间单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayEndYearFlag id="PayEndYearFlag" value="Y" class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      给付方法
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=GetDutyKind id="GetDutyKind">
    </TD>
    <TD CLASS=title>
      领取方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=getIntv id="getIntv">
    </TD>
    <TD CLASS=title>
      起领期间
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=GetYear id="GetYear">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      起领期间单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=GetYearFlag id="GetYearFlag">
    </TD>
    <TD CLASS=title>
      起领日期计算类型
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=GetStartType id="GetStartType">
    </TD>
    <TD CLASS=title>
      自动垫交标志
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=AutoPayFlag id="AutoPayFlag">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      利差返还方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=InterestDifFlag id="InterestDifFlag">
    </TD>
    <TD CLASS=title>
      减额交清标志
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=SubFlag id="SubFlag">
    </TD>
    <TD CLASS=title>
      红利领取方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=BonusGetMode id="BonusGetMode">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      生存金领取方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=LiveGetMode id="LiveGetMode">
    </TD>
    <TD CLASS=title>
      是否自动续保
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=RnewFlag id="RnewFlag">
    </TD>
	<td class="title"></td>
	<td class="input"></td>
  </TR>
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
<jsp:include page="CardComplexInsured.jsp"/>
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

    <div id="DivLCImpartInsured" STYLE="display:none">
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
   <div id=DivIncome style="display:none">
     <table  class="common">  
       <tr class="common">
         <td class="title">您每年固定收入</td>
          <td>
            <input class=common name=Income id="Income" verify="被保险人收入|NUM&LEN<=8" verifyorder='2' >万元
          </td>
         
  
        <!--td class="title">万元</td-->
    
         <td  class="title">
            主要收入来源：
         </td>             
         <td class="input">
           <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
           <input class="codeno" name="IncomeWay" id="IncomeWay" verify="被保人主要收入来源|LEN<4"  verifyorder="2" ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName" readonly="true">
         </td>              
         
         
       </tr> 
   </table> 
</div>     
      <div id="divLCImpartInsured1" style= "display: ''">
        <table  class= common>
          <tr  class= common>
            <td style="text-align: left" colSpan=1>
            <span id="spanImpartInsuredGrid" >
            </span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <!-- 告知信息部分（列表） -->
<!--
    <div id="DivLCImpartInsuredDetail" STYLE="display:''">
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
      
      <div  id="divLCImpart2" style= "display: ''">
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
    <div id=DivLCPol STYLE="display:none">
      <table>
        <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
          </td>
          <td class= titleImg>
            被保险人险种信息
          </td>
        </tr>
      </table>
    
      <div  id= "divLCPol1" style= "display: none">
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
    
    </div>
    <br>

    <div  id= "HiddenValue" style="display:none;float: right">
    	<input type="hidden" id="fmAction" name="fmAction">
			<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
			<input type="hidden" name="MissionID" id="MissionID" value= "">
      <input type="hidden" name="SubMissionID" id="SubMissionID" value= "">
      
      <input type="hidden" name="ActivityID" id="ActivityID" value= "">
      <input type="hidden" name="NoType" id="NoType" value= "">
      <input type="hidden" name="PrtNo2" id="PrtNo2" value= "">
      
      <input type="hidden" name="ContNo" id="ContNo" value="" >
      <input type="hidden" name="ProposalGrpContNo" id="ProposalGrpContNo" value="">
      <input type="hidden" name="SelPolNo" id="SelPolNo" value="">
      <input type="hidden" name="BQFlag" id="MakeDate">  
      <input type='hidden' name="MakeDate" id="MakeDate">
      <input type='hidden' name="MakeTime" id="MakeTime">
      <input type='hidden' id="ContType" name="ContType">
      <input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
      <input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
    </div>          

        <!-- 受益人信息部分（列表） -->
      <table>
      	<tr>
          <td class=common>
		  	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
      		</td>
          <td class= titleImg>
      		  受益人信息
      		</td>
      	</tr>
      </table>
	    <div id= "divLCBnf1" style= "display: ''" >
      	<table class= common>
          <tr class= common>
      	    <td style="text-align: left" colSpan="1">
		  			  <span id="spanBnfGrid" >
		  			  </span>
		  		  </td>
		  	  </tr>
		    </table>
	    </div>
   
   
			<div  id= "divLCPol1" style= "display: ''">
        <table  class=common>
          <tr  class=common>
          	<TD  class= title>险种编码</TD>
            <TD  class= input>
              <Input class="codeno" name=RiskCode id="RiskCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('CardRiskCode',[this,RiskCodeName],[0,1],null,null,null,null,'400');" onkeyup="showCodeListKey('CardRiskCode',[this,RiskCodeName],[0,1],null,null,null,null,'400');">
			  <input class="codename" name="RiskCodeName" id="RiskCodeName" elementtype=nacessary readonly="readonly">
            </TD>
            <TD  class= title>份数</TD>
            <TD  class= input colspan=3 >
              <Input class='common wid'  name="Mult" id="Mult" verify="份数|len<=5"  elementtype=nacessary verifyorder="1" >
            </TD> 		
          </tr>
           <TR CLASS=common>
                <TD CLASS=title>保额</TD>
			    <TD CLASS=input COLSPAN=1>
			      <Input NAME=Amnt id="Amnt" VALUE="" MAXLENGTH=12 CLASS="common wid" readonly>
			    </TD>	
				<TD CLASS=title>保费</TD>
			    <TD CLASS=input COLSPAN=1>
			      <Input NAME=Prem id="Prem" VALUE="" MAXLENGTH=12 CLASS="common wid" readonly>
			    </TD> 
				<td class=title></td>
				<td class=input></td>
  			</TR>
        </table>
      </div>
      
			<div id= "DivPolOtherGrid" style= "display: none" >
				<table class= common>
				<tr class= common>
				<td style="text-align: left" colSpan="1"><span id="spanPolOtherGrid" ></span>
				</td>
				</tr>
				</table>
			</div>
			<br><hr class=line>
      
      <Div  id= "HiddenValue" style= "float: right" style="float: right">
    	
    	
    	<!--INPUT class=cssButton id="Donextbutton8" VALUE="保  存 "  TYPE=button onclick="submitForm();"-->
      <input class=cssButton name="addbutton" VALUE="保  存"  TYPE=button onclick="return submitForm();">      			
    	<!--INPUT class=cssButton id="Donextbutton8" VALUE="修  改 "  TYPE=button onclick="submitForm1();" disabled=true-->
      <INPUT class=cssButton id="Donextbutton9" VALUE="签  单"  TYPE=button onclick="submitForm2();">  
      
      <INPUT class=cssButton id="Donextbutton10" VALUE="删  除"  TYPE=button onclick="deleteClick2();">  	 	
    
    </div>    
<!--
  ###############################################################################
                              随动定制按钮 
  ###############################################################################
-->
    
    <div id="autoMoveButton" style="display:none">
	    <input type="button" name="autoMoveInput" value="随动定制确定" onclick="submitAutoMove('11');" class="cssButton">
	    <input type="button" name="Next" value="下一步" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class="cssButton">
      <input value="返  回" class="cssButton" type="button" onclick="top.close();">
      <input type=hidden name="autoMoveFlag" id="autoMoveFlag">
      <input type=hidden name="autoMoveValue" id="autoMoveValue">
      <input type=hidden name="pagename" id="pagename" value="11">
    </div>

  
  
</form>


  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

<br /><br /><br /><br />
</body>

<script language="javascript">
	if(PrtNo!="null")
	{
	  fm.ProposalContNo.value=PrtNo;
	}else{
	fm.ProposalContNo.value="";
	}
</script>
</html>
