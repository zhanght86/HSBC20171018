<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html>

<%
 //�������ƣ�PDAlgoDefiInput.jsp
 //�����ܣ��㷨�������
 //�������ڣ�2009-3-17
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
 
 String mRiskCode = request.getParameter("riskcode");
 String mRuleName = request.getParameter("RuleName");
 String mRuleDes = request.getParameter("RuleDes");
 String mCreator = request.getParameter("Creator");
 String mRuleStartDate = request.getParameter("RuleStartDate");
if(mRuleStartDate==null||mRuleStartDate.equals("")||mRuleStartDate.equals("null"))
{
	mRuleStartDate = PubFun.getCurrentDate();
}
 String mRuleEndDate = request.getParameter("RuleEndDate");
 String mTempalteLevel = request.getParameter("TempalteLevel");
	
 String mBusiness = request.getParameter("Business");
 String mState = request.getParameter("State");
 String mValid = request.getParameter("Valid");
 //��ȡ�������ͱ�־
 String mflag = (String) request.getParameter("flag");
// mflag = 1;
 //��ȡ�����������־����Ҫ���ڹ�����޸ġ����ƺͲ鿴ʱ���ڲ��ҹ��������
 String mLRTemplate_ID = (String) request.getParameter("LRTemplate_ID");
 //��ȡ������Դ�ı�������Ҫ���ڹ�����޸ġ����ƺͲ鿴ʱ���ڲ��ҹ���ı�
 String mLRTemplateName = (String) request.getParameter("LRTemplateName");
 
 
 //tongmeng 2011-05-17 add
 //���ӹ�����������������
 String mRuleModul = (String) request.getParameter("RuleModul");
 String mRuleInputName = request.getParameter("RuleInputName");
 
 String mRuleType = request.getParameter("RuleType");
%>
 <SCRIPT language="JavaScript">
 		//window.onunload = endProcess;
 	<!-- ȫ�ֱ���-->
 	var jRiskCode = '<%=mRiskCode%>';
 	var jRuleName = '<%=mRuleName%>';
 	var jRuleDes = '<%=mRuleDes%>';
 	var jCreator = '<%=mCreator%>';
 	var jRuleStartDate = '<%=mRuleStartDate%>';
 	var jRuleEndDate = '<%=mRuleEndDate%>';
 	var jTempalteLevel = '<%=mTempalteLevel%>';
 	jTempalteLevel = '01';
 	var jBusiness = '<%=mBusiness%>';
 	var jState = '<%=mState%>';
 	var jValid = '<%=mValid%>';
 	jValid = 1;
 	var jflag = '<%=mflag%>';
 	jflag='1';
 	var jLRTemplate_ID = '<%=mLRTemplate_ID%>';
 	var jLRTemplateName = '<%=mLRTemplateName%>';
 	
 	var tRuleModul = '<%=mRuleModul%>';
 	var jRuleInputName = '<%=mRuleInputName%>';
 	
 	var tCurrentProcess = 0;
 	var tMaxProcess = 4;
 	
 	var  jRuleType = '<%=mRuleType%>';
 	if(jRuleType==null||jRuleType==''||jRuleType=='null')
 	{
 		jRuleType = "1";
 	}
 	
 	
 </SCRIPT>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="IbrmsPDAlgoDefi.js"></SCRIPT>
 
 <%@include file="IbrmsPDAlgoDefiInit.jsp"%>
</head>
<body onload="BrowserCompatible();initRuleCode();onRiskTabChange(1)"  >
<STYLE>
	 #changeRiskPlan a {
      float:left;
      background:url("../common/images/tableft1.gif") no-repeat left top;
      margin:0;
      padding:0 0 0 4px;
      text-decoration:none;
      }
      #changeRiskPlan a span {
      float:left;
      display:block;
      background:url("../common/images/tabright1.gif") no-repeat right top;
      padding:5px 15px 4px 6px;
      color:#627EB7;
      }
    /* Commented Backslash Hack hides rule from IE5-Mac \*/
    #changeRiskPlan a span {float:none;}
    /* End IE5-Mac hack */
    #changeRiskPlan a:hover span {
      color:#627EB7;
      }
    #changeRiskPlan a:hover {
      background-position:0% -42px;
      }
    #changeRiskPlan a:hover span {
      background-position:100% -42px;
      }

      #changeRiskPlan #current a {
              background-position:0% -42px;
      }
      #changeRiskPlan #current a span {
              background-position:100% -42px;
      }
</STYLE>






<Div  id="changeRiskMain"  style= "display: ''">
    	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,changeRiskPlan);">
    		</td>
    		<td class= titleImg>
    			�㷨���嵼��
    		</td>
    	 </tr>
       </table>	
</Div>
<div class=maxbox1>
<table class=common >
	<tr class= common>
        
        <TD  class= title>����״̬:</TD>
        <TD  class= input>
            <Input class="readonly wid" readonly name=RuleStateCN id=RuleStateCN value='δ����'>
        </TD>
        
        <TD  class= title>��ǰ����:</TD>
        <TD  class= input>
            <Input class="readonly wid" readonly name=CurProcess id=CurProcess value=''>
        </TD> 
        <TD  class= input>
        	<input type="button" class=cssButton name="preProcess1" value="��һ��" onclick="preProcess();" /> 
          <input type="button" class=cssButton name="nextProcess1" value="��һ��" onclick="nextProcess();" /> 
          <input type="button" class=cssButton name="endProcess1" value="���" onclick="endProcess();" /> 
        </TD>
        
    	</tr>
</table>
</div>
<hr class=line>
<Div  id="changeRiskPlan" style= "display: ''">
 <Div  id="changeRiskPlanTitle" style= "display: ''">
<table class=common >
  <tr>
    <td class="common">
    	<a href="#" onclick="onRiskTabChange(1);">
    		<span>������Ϣ</span>
    	</a>
    	<a href="#" onclick="onRiskTabChange(2);">
    	  <span>������/�޸�</span>
    	</a>
    	<a href="#" onclick="onRiskTabChange(3)">
    	  <span>�������</span>
    	</a>
      <a href="#" onclick="onRiskTabChange(4)">
    	  <span>������/����/�޶�</span>
    	</a>
    	
    </td>
  </tr>
 </table>
 </Div>
 <table class=common >
  <tr>
    <td class="common">
 <div id="tab1c">
        	<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    <td class=titleImg>
      �㷨��ϸ
    </td>
    </td>
    </tr>
    </table>
<br>
<div class=maxbox1>  
<table class=common>
	<tr class=common>
		<td class=title>
			�㷨����
		</td>
		<td class=input>
			<input class="readonly wid" readonly name="RuleName" id=RuleName value='<%=mRuleName%>' maxlength="6" elementtype=nacessary>
		</td>
		<td class=title>
			���ִ���
		</td>
		<td class=input>
			<input class="readonly wid" readonly  name="RiskCode" id=RiskCode value='<%=mRiskCode%>' maxlength="8"  elementtype=nacessary>
		</td>
	</tr>

</table>
</div>
<Div  id= "divRiskCalDetail" style= "display: 'none'">   
<table width=100% >
  <tr>
    <td class="titleImg" >�㷨����:</td>
  </tr>

<table  class=common >
   <iframe id= "IbrmsDetail" src="" width=100% height=500 SCROLLING='Auto' >
 </iframe> 
</table>
</table>
</div>
<br><br>
        </div>
        <div id="tab2c" style="display:none">
         <table  class= common>
  					<iframe id= "IbrmsMake" src="" width=100% height=500 SCROLLING='Auto'>
  				  </iframe> 
	      </table>
        </div>
        <div id="tab3c" style="display:none">
        	<iframe id= "IbrmsTest" src="" width=100% height=800 SCROLLING='Auto'>
				  </iframe>
        </div>
        <div id="tab4c" style="display:none">
        
        <table>
    			<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,resultImport);">
    		</td>
    		<td class= titleImg>
    			���ݵ������:
    		</td>
    	 </tr>
       </table>	
       <br>
			 <div id='resultImport'>
			 <table class=common>
			 		<tr class= common>
			 			1:���ڹ��򷢲�ǰ,�������ݵĵ���
			 		</tr>
			 		<tr class= common>
			 			2:����ÿ���޸Ĺ����,���������µ�����ģ��
			 		</tr>
			 		<tr class= common>
			 			3:�벻Ҫ����ģ�����͹�����������
			 		</tr>
			 </table>
			 <hr>
			 <form action="./RuleResultSave.jsp" enctype="multipart/form-data" method=post  name=fmResult target="fraSubmit">
        <table class=common>
        	<tr class= common>
						<TD  class= common>
        			<input type="button" class=cssButton name="downloadTemplate1" value="����ģ������" onclick="downloadTemplate();" /> 
        			<input type="button" class=cssButton name="downloadTemplate2" value="�����ļ�����" onclick="downloadData();" /> 
						</TD>
					</tr>
					</table>
					<br>
				<table class=common>
					<tr class= common>
						
    				<td class="titleImg" >���ݵ���
    					<Input type="file" style="background-color: #F7F7F7;border: 1px #799AE1 solid;height: 20px;width: 280px;"  width="100%" name=FileName value="sdf">
							<input value="ȫ������"  name="ImportExcel1" onclick="ImportExcel()" class="cssButton" type="button" >
							<input value="��������"  name="ImportExcel2" onclick="ImportExcelAdd()" class="cssButton" type="button" >
						</td>
  				</tr>
      	 <input  type=hidden class=common id="UploadTempalteID" /> 
     		 </table>
     		</form>
   			 </div>
       <hr> 
    			<table>
    			<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ruleDeployDiv);">
    		</td>
    		<td class= titleImg>
    			���򷢲�����:
    		</td>
    	 </tr>
       </table>	
       <br>
       <div id='ruleDeployDiv'>
			 <table class=common>
			 		<tr class= common>
			 			1:������Ҫ�����������Ч
			 		</tr>
			 		<tr class= common>
			 			2:�ѷ����Ĺ�����ͨ��"�����޶�"�����,���ܽ����޸�
			 		</tr>
			 </table>
			 <hr>
        <table class=common>
        	<tr class= common>
						<TD  class= common>
        			<input type="button" class=cssButton name="ruleDeploy" value="���򷢲�" onclick="deploy();" /> 
						</TD>
					</tr>
					<tr class= common>
					</tr>	
					<tr class= common>
        		<TD  class= common>
          		<input type="button" class=cssButton name="ruleUnDeploy" value="�����޶�" onclick="unDeploy();" /> 
        		</TD>
        	</tr>
        </div>
     		 </table>
    </td>
  </tr>
</table>
</div>
<form method="post" name="fm1" action="./IbrmsPDAlgoDefiSave.jsp"  target="fraSubmit">
<input  type=hidden class=common name="BtnFlag" /> 
<input  type=hidden class=common name="TempalteID" /> 
<input  type=hidden class=common name="RuleState" /> 
<input type="hidden"   name="riskcode" value='<%=mRiskCode%>' />
 <span id="spanCode"  style="display: none; position:absolute; slategray; left: 736px; top: 264px; width: 229px; height: 44px;"> </span>

</form>
<form method="post" name="fm2" action="./IbrmsGetRuleCode.jsp"  target="fraSubmit">
	<input  type=hidden class=common name="ModulName" value='<%=mRuleModul%>'/> 
</form>	

<input  type=hidden class=common name="RuleInputName" value='<%=mRuleInputName%>'/> 
</body>
</html>
