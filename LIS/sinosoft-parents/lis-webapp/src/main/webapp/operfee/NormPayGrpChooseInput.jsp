<html> 
<%
//程序名称：NormPayGrpChooseInput.jsp
//程序功能：集体选名单交费

//创建日期：2002-10-08    
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
    GlobalInput tG = (GlobalInput)session.getValue("GI");
    String Branch =tG.ComCode;
    String strCurTime = PubFun.getCurrentDate();   
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  

  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="./NormPayGrpChooseInput.js"></SCRIPT>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="NormPayGrpChooseInit.jsp"%>
</head>
<body  onload="initForm();" >					                                                                                  
<form action="./NormPayGrpChooseSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    
    <div class="maxbox">
    <Div  id= "divNormPayGrpChoose" style= "display: ''">
      <table  class= common >                   
        <TR class= common>                      
          <TD  class= title5>
            总应交金额
          </TD>                   
          <TD  class= input5>
            <Input class="common wid" name=SumDuePayMoney id="SumDuePayMoney" readonly tabindex=-1 >
          </TD>           
          <TD  class= title5>
            交至日期
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=PaytoDate id="PaytoDate" readonly tabindex=-1 >
          </TD>                                                                
        </TR>
        <TR class= common>                            
          <TD class=title5>
	     集体保单号码
	  </TD>
	  <TD class= input5>
	      <input class="common wid" name=GrpPolNo id="GrpPolNo" verify="集体保单号码|NOTNULL">
	  </TD>   
          <TD  class= title5>
            交费日期
          </TD>
         <TD  class= input5>
            <input class="common wid" name="PayDate" id="PayDate" verify="交费日期|DATE&NOTNULL">                       
          </TD>                                                                             
        </TR>   
        <TR class= common>                            
          <TD class=title5>
	     管理费比例
	  </TD>
	  <TD class= input5>
	      <input class="common wid" name=ManageFeeRate id="ManageFeeRate" verify="管理费比例|NUM">
	  </TD>                                                                               
      <TD class=title5>
	     投保单位
	  </TD>
	  <TD class= input5>
	      <input class="common wid" name=GrpName id="GrpName">
	  </TD> 
        </TR>
       <TR class= common> 
       	<TD class=title5>
	     个单号码
	  </TD>
	  <TD class= input5>
	      <input class="common wid" name=PolNo1 id="PolNo1" verify="个单号码">
	  </TD>                                    
       </TR>
      </table>
    </Div>
    </div>  
    <a href="javascript:void(0)" class=button onclick="queryRecord();">查  询</a>
    <a href="javascript:void(0)" class=button onclick=onclick="window.open('./GrpChooseFeeList.html');">查询已交费团体不定期保单</a>
        <!-- <INPUT class= cssButton VALUE="查    询"  TYPE=button onclick="queryRecord();">        
          
        <INPUT VALUE="查询已交费团体不定期保单" TYPE=button class=cssButton onclick="window.open('./GrpChooseFeeList.html');">    -->      
         
      <!--<INPUT VALUE="返回" TYPE=button onclick="returnParent();">--> 					
            
    <table>
      <tr class="common">
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNormGrid1);">
        </td>
    	  <td class= titleImg>
    	    集体中的个人信息
          </td>
    	</tr>
    </table>
  <Div  id= "divNormGrid1" style= "display: ''" align=center>
     <table  class= common >
        <tr>
    	  	<td text-align: left colSpan=1 >
	         <span id="spanNormPayGrpChooseGrid" ></span> 
		      </td>
	      </tr>
      </table>
     <INPUT VALUE="首  页"   class= cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
     <INPUT VALUE="上一页"   class= cssButton91 TYPE=button onclick="turnPage.previousPage();"> 			
     <INPUT VALUE="下一页"   class= cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
     <INPUT VALUE="尾  页"   class= cssButton93 TYPE=button onclick="turnPage.lastPage();">	
  </Div>
  <div>
    <a href="javascript:void(0)" class=button onclick="submitCurData();">保存页面上选中个人数据</a>
    <a href="javascript:void(0)" class=button onclick="verifyChooseRecord();">选名单核销</a>
  </div>
  <!-- <INPUT class= cssButton VALUE="保存页面上选中个人数据" TYPE=button onclick="submitCurData();">  
  <INPUT class= cssButton VALUE="选名单核销" TYPE=button onclick="verifyChooseRecord();">   --> 
         <!--隐藏的号码，用于保存集体保单号用于提交-->      
         <Input type=hidden name=PolNo id="PolNo"> 
         <input type=hidden name=GrpContNo id="GrpContNo">
  </form>
  
    <Form name=fmFileImport id="fmFileImport" action="./FileImportNormPayCollSaveAll.jsp" method=post target="fraSubmit" ENCTYPE="multipart/form-data">         
      <table>
      <tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay1);">
        </td>
    	  <td class= titleImg>
    	   磁盘文件处理
          </td>
    	</tr>
    </table> 
    <div class="maxbox1">
    <div  id= "divFCDay1" style= "display: ''">
     <table class= common>
	  <TR  class= common>
	    	<TD class=title5>
	     集体保单号码
	  	</TD>
	  	<TD class= input5>
	     		 <input class="common wid" name=GrpPolNo1 id="GrpPolNo1">
	 	 </TD>
     <td class=title5></td>       
     <td class= input5></td>    	 
	   </TR>     
	</table>
</div>
</div> 
<div>
  <a href="javascript:void(0)" class=button onclick="submitCurDataAll();">保存团体下所有个人数据</a>
  <a href="javascript:void(0)" class=button onclick="ToExcel();">生成模板</a>
</div>
<br>
	<!-- <INPUT class= cssButton VALUE="保存团体下所有个人数据" type=button onclick ="submitCurDataAll();">
	<Input type=button class=cssButton value="生成模板" onclick = "ToExcel();"> -->
<!--	<a href="./download.jsp">点击下载</a>-->

	<div class="maxbox1">
	<table class=common>
	<tr class="common">
		<TD class = title width=20%>
	        	文件地址
	     	 </TD>
	     	 <TD class = common width=80%>
	        	<Input type="file" name=FileName id="FileName">
	     	 </TD>
	</tr>
	</table>
  </div>
  <a href="javascript:void(0)" class=button onclick="fileImport();">磁盘文件导入</a>
	<!-- <INPUT VALUE="磁盘文件导入" class= cssButton TYPE=button onclick = "fileImport();">  -->
	<input type=hidden name=ImportFile id="ImportFile">
	<input type=hidden name=ImportConfigFile id="ImportConfigFile">
</Form>	
  <Form name=fmToExcel id="fmToExcel" action="./NormPayToExcel.jsp" method=post target="fraSubmit">   
 <!--        <Input type=button class=common value="生成模板" onclick = "ToExcel();">      -->
  </Form> 

  <Form name=fmSaveAll id="fmSaveAll" action="./NormPayCollSaveAll.jsp" method=post target="fraSubmit">   
         <Input type=hidden name=SubmitGrpPolNo id="SubmitGrpPolNo">      
       <!--<Input type=button class=common value="保存团体下所有个人数据" onclick = "submitCurDataAll();"> -->    
  </Form> 
  <Form name=fmSubmitAll id="fmSubmitAll" action="./NormPayGrpChooseSubmitAll.jsp" method=post target="fraSubmit">   
         <Input type=hidden name=SubmitGrpPolNo id="SubmitGrpPolNo"> 
         <Input type=hidden name=SubmitPayDate id="SubmitPayDate"> 
         <Input type=hidden name=SubmitManageFeeRate id="SubmitManageFeeRate"> 
         <Input type=hidden name=SubmitGrpContNo id="SubmitGrpContNo"> 
  </Form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanNormPayGrp"  style="display:''; position:absolute; slategray"></span>  
</body>
</html>
