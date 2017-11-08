<%
/***************************************************************
 * <p>ProName：LLClaimSurveyInput.jsp</p>
 * <p>Title：调查录入</p>
 * <p>Description：调查录入申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tCurrentDate = PubFun.getCurrentDate();  //提取服务器当前日期
	String tManageCom =tGI.ManageCom;
	String tOperator = tGI.Operator;
	String tState =request.getParameter("State");
	String tRgtNo =request.getParameter("RgtNo");
	String tInqNo =request.getParameter("InqNo");	
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //记录登陆机构
	var tOperator = "<%=tOperator%>";  //记录操作人
	var tRgtNo = "<%=tRgtNo%>";
	var tInqNo = "<%=tInqNo%>";
</script>
<html>
<head>
	<title>调查录入</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="../common/javascript/jquery-1.7.2.js"></script>
	<script src="./LLClaimSurveyInput.js"></script>
	<%@include file="./LLClaimSurveyInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post  target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLLInqApplyInfo);">
				</td>
				<td class=titleImg>调查申请信息</td>
			</tr>
		</table>    
	<div id="divLLInqApplyInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title> 案件号 </td>
				<td class=Input><Input class="wid readonly" readonly name=RgtNo></td>
				<td class=title> 投保人名称 </td>
				<td class=Input><Input class="wid readonly" readonly name=grpName></td>   
				<td class=title></td>
				<td class=Input></td>                  
			</tr>
			<tr class=common>
				<td class=title> 被保险人名称 </td>
				<td class=Input><Input class="wid readonly" readonly name=InsuredName></td>
				<td class=title> 证件号码 </td>
				<td class=Input><Input class="wid readonly" readonly name=IdNo></td>  
				<td class=title> 调查类型 </td>
				<td class=Input><Input class="wid readonly" readonly name=InqType></td>
			</tr>
			<tr class=common>
				<td class=title> 调查发起日期 </td>
				<td class=Input><Input class="wid readonly" readonly name=ApplyDate></td>
				<td class=title> 调查发起人 </td>
				<td class=Input><Input class=codeno readonly name=ApplyPer><Input class=codename name=ApplyPerName readonly=true></td>
				<td class=title> 调查发起机构 </td>
				<td class=Input><Input class=codeno readonly name=InitDept><Input class=codename name=InitDeptName readonly=true></td>  
			</tr>
			<tr class=common>
				<td class=title> 调查原因 </td>
				<td class=Input><Input class=codeno readonly name=InqRCode><Input class=codename name=InqRCodeName readonly=true></td>               
				<td class=title> 调查流程状态 </td>
				<td class=Input><Input class=codeno readonly name=SurveyFlowState><Input class=codename name=SurveyFlowStateName readonly=true></td> 
				<td class=title>调查机构</td>
				<td class=Input><Input class=codeno readonly name=InqDept><Input class=codename name=InqDeptName readonly=true></td>            
			</tr>
			<tr class=common>
				<td class=title>是否异地调查</TD>
				<td class=input><Input class=codeno name=IsLoc id=IsLoc style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,RptFlagName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,RptFlagName],[0,1],null,null,null,1);"><input class=codename name=IsLocName id=RptFlagName readonly></td> 
				<!-- <td class=input><Input class=codeno name=IsLoc style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);"><input class=codename name="IsLocName" readonly=true></TD> -->
				<td class=title></td>
				<td class=Input></td> 
				<td class=title></td>
				<td class=Input></td>                            
			</tr>
			<tr class=common>
				<td class=title> 调查目的 </td>
				<td class=input colspan="5"><textarea name=InqItem id=InqItem cols=60 rows=5  verify="调查目的|NOTNULL&LEN<=500"  maxlength="1000" class=common></textarea></TD>       
	  		</tr>
			<tr class=common>
				<td class=title> 调查计划 </td>
				<td class=input colspan="5"><textarea name=InqPlan id=InqPlan cols=60 rows=5  verify="调查计划|NOTNULL&LEN<=500"  maxlength="1000" class=common></textarea></TD>       
	  		</tr>
			<tr class=common>
				<td class=title> 备注信息 </td>
				<td class=input colspan="5"><textarea name=Remark id=Remark cols=60 rows=5  verify="备注信息|LEN<=500"  maxlength="1000" class=common></textarea></TD>       
	  		</tr>
	  	</table>    
	</div> 
		<Input class=cssButton name=LCQueryBut value="保单查询" type=button onclick="policyQuery()">
		<Input class=cssButton name=LPQueryBut value="保全查询" type=button onclick="edorQuery()">
		<Input class=cssButton name=PastReport value="既往赔案查询" type=button onclick="clmPastCaseQuery()">
		
  <!--调查申请信息结束-->  
  <!--调查过程录入开始-->    
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLLSurveyCourseGrid);">
				</td>
				<td class=titleImg>调查过程录入信息列表</td>
			</tr>
		</table>     
    <div id="divLLSurveyCourseGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanLLSurveyCourseGrid" ></span> </td>
			</tr>
		</table>
		</div>
		<div id="divLLSurveyCoursePage" style="display:none">
		<center>
			<Input class=cssButton90 value="首  页" type=button onclick="turnPage5.firstPage();">
			<Input class=cssButton91 value="上一页" type=button onclick="turnPage5.previousPage();">
			<Input class=cssButton92 value="下一页" type=button onclick="turnPage5.nextPage();">
			<Input class=cssButton93 value="尾  页" type=button onclick="turnPage5.lastPage();">
		</center>
	</div>  
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInqCourseInfo);">
			</td>
			<td class=titleImg>调查过程录入信息</td>
		</tr>
	</table> 
	
	<div id="divInqCourseInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>调查方式</td>
				<td class=Input><Input class=codeno name=InqMode verify="调查方式|code:llinqmode" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('llinqmode',[this,InqModeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('llinqmode',[this,InqModeName],[0,1],null,null,null,1);"><Input class=codename name="InqModeName" elementtype=nacessary readonly=true></td>        
				<td class=title>调查地点</td>
				<td class=Input><Input class="wid common" elementtype=nacessary name=InqSite maxlength="25"></td>
				<td class=title>调查日期</td>
				<td class=Input><Input class='coolDatePicker' elementtype=nacessary dateFormat="Short" name=InqDate onClick="laydate({elem: '#InqDate'});" id="InqDate"><span class="icon"><a onClick="laydate({elem: '#InqDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
			</tr>
			<tr class=common> 
				<td class=title>第一调查人</td>
				<td class=Input><Input class="wid common" name=InqPer1 maxlength="10" elementtype=nacessary ></td>
				<td class=title>第二调查人</td>
				<td class=Input><Input class="wid common" name=InqPer2 maxlength="10"></td>
				<td class=title>被调查人</td>
				<td class=Input><Input class="wid common" name=InqByPer maxlength="200" elementtype=nacessary ></td>
			</tr>
			<!-- <tr class=common> 
				<td class=title>被调查人与出险人关系</td>
				<td class=input><input class=codeno name=Relation maxlength=6 ondblclick="return showCodeList('relation',[this,RelationName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1],null,null,null,'1',null);" maxlength=50><input class=codename name=RelationName elementtype=nacessary></td>
				<td class=title></td>
				<td class=Input></td>
				<td class=title></td>
				<td class=Input></td>
			</tr>			 -->
		</table>
		<table class=common>
			<tr class=common> 
				<td class=common> 调查过程录入 <font color=red>*</font> </td>
			</tr>
			<tr class=common>
				<td class=common>
					<textarea name=InqCourse id=InqCourse cols="130" rows="18"  class=common></textarea>
				</td>
			</tr>
		</table> 
		<div id =Save1>
			<Input class=cssButton name=SaveCourseButton  id=SaveCourseButton value="新  增" type=button onclick="AddInqCourseClick();">
			<Input class=cssButton name=UpdateCourseButton value="修  改" type=button onclick="UpdateInqCourseClick();">
			<Input class=cssButton name=DeleteCourseButton value="删  除" type=button onclick="DeleteInqCourseClick();">
		</div>
   </div>  
   <!--调查过程录入结束-->
		<!-- 隐藏区域-->
		<Input type=hidden class="wid readonly" readonly name=InitPhase>
		<Input type=hidden name=fmtransact><!--操作代码‘insert,delete’等-->       
		<Input type=hidden name=ManageCom><!--机构-->
		<Input type=hidden name=CustomerNo><!--客户编号-->
		<Input type=hidden name=CourseCouNo><!--调查过程序号-->
		<Input type=hidden name=ClmNo><!--业务号-->
		<Input type=hidden name=InqNo><!--业务号-->
		<Input type=hidden name=InqTypeCode><!--业务号-->
		
  </form>
  
  <!-- 调查附件form-->
	<form action="LLSurveyAttachmentSave.jsp" style="margin-bottom:0px;margin-top: 0px" method=post name=fm2 id=fm2 target="fraSubmit" ENCtype="multipart/form-data">
	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSurveyAttachmentGrid);">
				</td>
				<td class=titleImg>调查附件信息列表</td>
			</tr>
		</table>
		<div id="divSurveyAttachmentGrid" style="display: ''" align=left>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanSurveyAttachmentGrid" ></span> 
					</td>
				</tr>
			</table>
		</div>
		<div  id="divSurveyAttachmentPage" style="display: none">
			<center>
				<Input class=cssButton90 value="首  页" type=button onclick="turnPage4.firstPage();">
				<Input class=cssButton91 value="上一页" type=button onclick="turnPage4.previousPage();">
				<Input class=cssButton92 value="下一页" type=button onclick="turnPage4.nextPage();">
				<Input class=cssButton93 value="尾  页" type=button onclick="turnPage4.lastPage();">
			</center>	
		</div>
  		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFJ);">
				</td>
				<td class=titleImg>调查附件信息</td>
			</tr>
		</table>
		<div class=maxbox1 id=divFJ>
			<Input value="新  增" class="cssButton" type=button name=saveAttache onclick="saveAttachmentClick();">
			<Input value="修  改" class="cssButton" type=button name=updateAttache onclick="updateLLinqupload();">
			<Input value="删  除" class="cssButton" type=button name=deleteAttache onclick="deleteLLinqupload();">
			<td style="font:10pt"><font color=red>&nbsp&nbsp&nbsp&nbsp* 请先"新增"附件，然后进行"调查附件上载"</font></td>
		</div>
		<div>
			<table class=common>
				<tr class=common>
					<td class=title> 调查附件名称 </td>
					<td class=Input><Input class="wid common" name=AffixName maxlength="10" elementtype=nacessary></td>
					<td class=title> 原件标识 </td>
					<td class=Input><Input class=codeno verify="原件标识|code:originallogo" name=OriginalLogo style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('originallogo',[this,OriginalLogoName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('originallogo',[this,OriginalLogoName],[0,1],null,null,null,1);"><Input class=codename name=OriginalLogoName elementtype=nacessary readonly=true></td>        
					<td class=title> 上传张数 </td>
					<td class=Input><Input class="wid common" name=AffixNumber maxlength="10" elementtype=nacessary value=1 readonly="readonly"></td>
				</tr>
			</table>
		</div>
		<div>
			<td class=title> 调查附件上载 &nbsp&nbsp&nbsp</td><td class=Input><Input type=hidden id="ImportPath" name=ImportPath> </td>
			<Input type="file" name=FileName style= "height:20px ;width:260px " onkeydown="this.blur()" >
			<Input value="上传文件" class="cssButton" type=button id=uploadfile name=uploadfile onclick="uploadFileClick('0');">
			<Input value="替换文件" class="cssButton" type=button id=repalecefile  name=repalecefile onclick="replaceFileClick();">
			<Input value="查看文件" class="cssButton" type=button name=viewfile onclick="viewAttachmentClick();">
			<Input value="删除文件" class="cssButton" type=button id=deletefile name=deletefile onclick="deleteAttachmentClick();">
		</div>
		<Input type=hidden name=AffixPatch>
		<Input type=hidden name=AttachNo><!--附件序号-->
		<Input type=hidden name=InitDept2><!--调查申请的业务机构-->
		<Input type=hidden name=insuredimport value="1">
		<Input type=hidden name=ImportFile>
		<Input type=hidden name=OldFileName>
		<Input type=hidden name=fmtransact><!--操作代码‘insert,delete’等-->   
		<Input type=hidden name=ClmNo><!--业务号-->
		<Input type=hidden name=InqNo><!--业务号-->
	</form>
	<!--调查附件结束-->
	<!--调查费用-->     
	<form method=post name=fm5 target="fraSubmit" style="margin-bottom:0px;margin-top: 0px"> 
    <!--调查费用录入开始-->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLLSurveyFeeGrid);">
				</td>
				<td class=titleImg>调查费用录入列表</td>
			</tr>
		</table>   
    	<div id="divLLSurveyFeeGrid" style="display: ''">
			<table class=common>
   			<tr class=common>
					<td text-align: left colSpan=1><span id="spanLLSurveyFeeGrid" ></span> </td>
			</tr>
			</table>
		</div>
		<div id='divLLSurveyFeePage' style="display: none">
		<center>
			<Input class=cssButton90 value="首  页" type=button onclick="turnPage2.firstPage();">
			<Input class=cssButton91 value="上一页" type=button onclick="turnPage2.previousPage();">
			<Input class=cssButton92 value="下一页" type=button onclick="turnPage2.nextPage();">
			<Input class=cssButton93 value="尾  页" type=button onclick="turnPage2.lastPage();">
		</center>  
    	</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInqFeeInfo);">
				</td>
				<td class=titleImg>调查费用录入信息</td>
			</tr>
		</table>   
		<div id=Save2 class=maxbox1>
			<Input class=cssButton name=AssFeeButton value="新  增" type=button onclick="AddInqFeeClick();">
			<Input class=cssButton name=UpdateFeeButton value="修  改" type=button onclick="UpdateFeeClick();">
			<Input class=cssButton name=DeleteFeeButton value="删  除" type=button onclick="DeleteFeeClick();">
		</div>
		<table class=common> 
			<tr class=common>
				<td class=title> 费用类型 </td>
				<td class=Input><Input class=codeno name=FeeType verify="费用类型|code:llinqfeetype2" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('llinqfeetype2',[this,FeeTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('llinqfeetype2',[this,FeeTypeName],[0,1],null,null,null,1);"><Input class=codename name="FeeTypeName" readonly=true elementtype=nacessary></td>
				<td class=title>费用金额</td>
				<td class=Input><Input class="wid common" name=FeeSum elementtype=nacessary maxlength='10'></td>
				<td class=title>发生时间</td>
				<td class=Input><Input class='coolDatePicker' elementtype=nacessary dateFormat="Short" name=FeeDate onClick="laydate({elem: '#FeeDate'});" id="FeeDate"><span class="icon"><a onClick="laydate({elem: '#FeeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>领款人</td>
				<td class=Input><Input class="wid common" name=Payee maxlength='10'></td>
				<td class=title>领款方式</td>
				<td class=Input><Input class=codeno name=PayeeType verify="领款方式|code:llinqpaymode" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('llinqpaymode',[this,PayeeTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('llinqpaymode',[this,PayeeTypeName],[0,1],null,null,null,1);"><Input class=codename name="PayeeTypeName" readonly=true></td>
				<td class=title></td>
				<td class=Input></td>
			</tr>
		 	<tr class=common>
				<td class=title> 备注 <font color=red>*</font></td>
				<td class=input colspan="5"><textarea name=Remark id=Remark cols=60 rows=5  verify="调查目的|LEN<=500"  maxlength="1000" class=common></textarea></TD>       
			</tr>
		</table>
    <!--调查费用结束-->
		<Input type=hidden name=fmtransact><!--操作代码‘insert,delete’等-->   
		<Input type=hidden name=FeeTypeOld><!--调查费用类型，用于修改时的查询-->
  </form>
 <!--调查结论form-->
  <form method=post name=fm3 id=fm3 target="fraSubmit" style="margin-bottom:0px;margin-top: 0px"> 
    <!--调查结论录入开始-->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInqConclusionInfo);">
				</td>
				<td class=titleImg>调查结论录入</td>
			</tr>
		</table>
		<div id="divInqConclusionInfo" class=maxbox1 align=left style="display: ''">
			<table class=common>    
				<tr class=common>
					<td class=title></td>
					<td class=Input></td>
					<td class=title></td>
					<td class=Input></td>
					<td class=title></td>
					<td class=Input></td>
      	</tr>
				<tr class=common>
					<td class=title id=luruper style="display:none"> 录入人 </td>
					<td class=Input id=luruper1 style="display:none"><Input class="wid readonly" readonly name=ConPer></td>
					<td class=title id=luruper2 style="display:none"> 录入日期 </td>
					<td class=Input id=luruper3 style="display:none"><Input class="wid readonly" readonly name=ConDate></td>
					<td class=title></td>
					<td class=Input></td>
				</tr>
				<tr class=common>
					<td class=title>调查结论<font color=red>*</font></td>
			  		<td class=input colspan="5"><textarea name=InqConclusion id=InqConclusion cols=60 rows=5  verify="调查结论|LEN<=500"  maxlength="1000" class=common></textarea></TD>       
		  		</tr>
	    	</table>
			<div id=Save3>
	    	<table class=common> 
				<tr>
					<td>
						<Input class=cssButton name=InqConfirm value="调查录入完毕" type=button onclick="InqConfirmClick();">
						<Input class=cssButton name="ReturnQuerycheck"   value="调查需求单打印" type=button onclick="InqPrint();">
						<Input class=cssButton name=ReturnSurveyProcess value="返  回" type=button onclick="turnback();">
					</td>
				</tr>
			</table>      
		</div>
    </div>
     <!--调查结论录入结束-->  
     <Input type=hidden name=fmtransact> 
     <Input type=hidden name=ClmNo> 
     <Input type=hidden name=InqNo> 
     <Input type=hidden name=SurveyType> 
	</form>   
<!------------------------------------------调查复核------------------------------------------------------------------>
	<form method=post name=fm4 id=fm4 target="fraSubmit" style="margin-bottom:0px;margin-top: 0px"> 
	<div id="divSurveyReviewEntry" align=left style="display: none">	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLLGrpClaimAudit1);">
				</td>
				<td class=titleImg>调查信息复核</td>
			</tr>
		</table>
		<div id="divSurveyReviewInfo" class=maxbox1 align=left style="display: ''">
			<table class=common> 
				<tr class=common>
					<td class=title></td>
					<td class=Input></td>
					<td class=title></td>
					<td class=Input></td>
					<td class=title></td>
					<td class=Input></td>
				</tr>
				<tr class=common>
					<td class=title> 复核结论 </td>
					<td class=input><input class=codeno id=Casetype name=CasetypeCode  ondblclick="return showCodeList('inquwtype',[this,CasetypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('inquwtype',[this,CasetypeName],[0,1],null,null,null,1);"><input class=codename name=CasetypeName  elementtype=nacessary readonly></td>
					<td class=title id=per style="display:none"> 复核人 </td>
					<td class=Input id=per1 style="display:none"><Input class="wid readonly" readonly name=uwConPer></td>
					<td class=title id=per2 style="display:none"> 复核日期 </td>
					<td class=Input id=per3 style="display:none"><Input class="wid readonly" readonly name=uwConDate></td>
				</tr>
				<tr class=common>
					<td class=title>调查结论<font color=red>*</font></td>
		 			<td class=input colspan="5"><textarea name=InqConclusion id=InqConclusion cols=60 rows=5  verify="调查结论|LEN<=500"  maxlength="1000" class=common></textarea></TD>       
				</tr>
			</table>				
			<div  id="divSurveyShowReturnView" align=left style="display: ''"> 
				<table > 
					<Input class=cssButton name="ReviewCheck" value="复核确认" type=button onclick="ReviewCheckInput();">
					<Input class=cssButton name="ReturnReviewcheck" value="返  回" type=button onclick="turnbackforReviewcheck();">
				</table>     
			</div>   
			<Input type=hidden name=ConNo> 
			<Input type=hidden name=fmtransact> 
		</div> 
	</div> 
	<div id=Save4 style="display: none">
		<table class=common> 
			<tr>
				<td>
					<Input class=cssButton name=ReturnSurveyProcess value="关  闭" type=button onclick="top.close();">
				</td>
			</tr>
		</table>      
	</div>
	<!--隐藏区-->
	
	<Input type=hidden  name=Operate> 	 	 <!--操作类型-->
	<Input type=hidden  name=InqNo> 	 	 
	<Input type=hidden  name=RgtNo> 
	<br /><br /><br /><br />
	</form>	
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>   
</body>
</html>