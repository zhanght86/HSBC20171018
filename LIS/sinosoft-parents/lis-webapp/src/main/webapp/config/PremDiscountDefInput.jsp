<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�PremDiscountDefInput.jsp
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var tContNo = "<%=request.getParameter("ContNo")%>";
	//alert("ContNo="+ContNo);
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
//	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>�ۿ۶������</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/laydate/laydate.js"></script>
  <script src="../menumang/treeMenu.js"></script>
   <SCRIPT src="../userMan/UserAdd.js"></SCRIPT>
  
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="PremDiscountDef.js"></SCRIPT>
  <%@include file="PremDiscountDefInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm  id="fm" target="fraSubmit" action="./PremDiscountDefSave.jsp">
   <table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
    <td class=titleImg>�ѱ����ۿ��б�</td>
    </td>
    </table>   
    <!--<table  class= common>
    	<tr class= common>
        <TD  class= title>�ۿ�����</TD>
        <TD  class= input>
            <Input style="width:60px" class="codeno" name=DiscountTypeQ verify ="�ۿ�����|notnull" ondblclick="return showCodeList('discounttype',[this,DiscounTypeQName],[0,1]);" onkeyup="return showCodeListKey('discounttype',[this,DiscounTypeQName],[0,1]);"><input style="width:100px" class=codename name=DiscounTypeQName readonly=true>
        </TD> 
        <TD  class= title>�ۿۺ���</TD>
        <TD  class= input>
          <Input style="100px" class=common  name=DiscountCodeQ  >
        </TD>
        
        <TD  class= title>���������</TD>
        <TD  class= input>
          <Input class=common  name=CampaignCodeQ  >
        </TD>
        
        <TD  class= title>�ӷ��ۿ۱��</TD>
       <TD  class= input>
          <Input style="width:60px" class="codeno" name=AddFeeDiscFlagQ verify ="�ӷ��ۿ۱��|notnull" ondblclick="return showCodeList('yesno',[this,AddFeeDiscFlagQName],[0,1]);" onkeyup="return showCodeListKey('yesno',[this,AddFeeDiscFlagQName],[0,1]);"><input style="width:100px" class=codename name=AddFeeDiscFlagQName readonly=true>
        </TD>
    	</tr>
   
    <tr>
    	 <TD  class= title>���ֱ���</TD>
        <TD  class= input>
            <Input class="codeno" name=RiskCodeQ  ondblclick="return showCodeList('RiskCode',[this,RiskCodeQName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeQName],[0,1]);"><input class=codename name=RiskCodeQName readonly=true>
        </TD> 
        <TD  class= title>���α���</TD>
        <TD  class= input>
 						<Input style="width:60px" class=codeno name=DutyCodeQ ondblclick="return showCodeList('griskduty',[this,DutyCodeQName],[0,1],null,fm.RiskCodeQ.value,'a.RiskCode',1);" onkeyup="return showCodeListKey('griskduty',[this,DutyCodeQName],[0,1],null,fm.RiskCodeQ.value,'a.RiskCode',1);"><input style="width:100px" class=codename name="DutyCodeQName" readonly=true>
        </TD> 
        <TD  class= title>
          ��ʼ����
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="StartDateQ" >
        </TD>
        <TD  class= title>
          ��ֹ����
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="EndDateQ" >
        </TD>       
    </tr>
    </table>-->
    
    <!--2015/7/27-->
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
    <table  class= common>
    	<tr class= common>
        <TD  class= title5>�ۿ�����</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center";*+style="background:url(../common/images/select--bg_03.png) no-repeat right center"; "  class="codeno" name=DiscountTypeQ verify ="�ۿ�����|notnull" onDblClick="return showCodeList('discounttype',[this,DiscounTypeQName],[0,1]);" onMouseDown="return showCodeList('discounttype',[this,DiscounTypeQName],[0,1]);" onKeyUp="return showCodeListKey('discounttype',[this,DiscounTypeQName],[0,1]);"><input class=codename name=DiscounTypeQName readonly=true>
        </TD> 
        <TD  class= title5>�ۿۺ���</TD>
        <TD  class= input5>
          <Input class="wid" class=common name=DiscountCodeQ  >
        </TD></tr>
        <tr class= common>
        <TD  class= title5>���������</TD>
        <TD  class= input5>
          <Input class="wid" class=common  name=CampaignCodeQ  >
        </TD>
        <TD  class= title5>�ӷ��ۿ۱��</TD>
       <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center; " class="codeno" name=AddFeeDiscFlagQ verify ="�ӷ��ۿ۱��|notnull" onDblClick="return showCodeList('yesno',[this,AddFeeDiscFlagQName],[0,1]);" onMouseDown="return showCodeList('yesno',[this,AddFeeDiscFlagQName],[0,1]);" onKeyUp="return showCodeListKey('yesno',[this,AddFeeDiscFlagQName],[0,1]);" ><input class=codename name=AddFeeDiscFlagQName readonly=true>
					</TD>
        </tr>
        <tr class= common>
        
    	
   
    
    	 <TD  class= title5>���ֱ���</TD>
        <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center;" class="codeno" name=RiskCodeQ  ondblclick="return showCodeList('RiskCode',[this,RiskCodeQName],[0,1]);" onMouseDown="return showCodeList('RiskCode',[this,RiskCodeQName],[0,1]);" onKeyUp="return showCodeListKey('RiskCode',[this,RiskCodeQName],[0,1]);" ><input class=codename name=RiskCodeQName readonly=true>
					</TD>
        <TD  class= title5>���α���</TD>
        <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center;" class=codeno name=DutyCodeQ onDblClick="return showCodeList('griskduty',[this,DutyCodeQName],[0,1],null,document.RiskCodeQ.value,'a.RiskCode',1);" onMouseDown="return showCodeList('griskduty',[this,DutyCodeQName],[0,1],null,document.RiskCodeQ.value,'a.RiskCode',1);" onKeyUp="return showCodeListKey('griskduty',[this,DutyCodeQName],[0,1],null,document.RiskCodeQ.value,'a.RiskCode',1);" ><input class=codename name="DutyCodeQName" readonly=true>
					</TD>
        </tr>
        <tr class= common>
        <TD  class= title5>
          ��ʼ����
        </TD>
        <!--<TD  class= input5>
          <input class="coolDatePicker" dateFormat="short" name="StartDateQ" >
        </TD>-->
        <TD class=input5>
			<!--<Input class="coolDatePicker" id="StartDateQ" onClick="laydate({elem: '#StartDateQ'})" dateFormat="short" style="width:190px;" name="StartDateQ" ><span class="icon"><a onClick="laydate({elem: '#StartDateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDateQ'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDateQ id="StartDateQ"><span class="icon"><a onClick="laydate({elem: '#StartDateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
        <TD  class= title5>
          ��ֹ����
        </TD>
        <TD class=input5>
					<!--<Input class="coolDatePicker"  id="EndDateQ" onClick="laydate({elem: '#EndDateQ'})" dateFormat="short" style="width:190px;" name="EndDateQ" ><span class="icon"><a onClick="laydate({elem: '#EndDateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDateQ'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDateQ id="EndDateQ"><span class="icon"><a onClick="laydate({elem: '#EndDateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>      
    </tr>
    </table></Div>
    
    
    	
    <!--<INPUT CLASS=cssButton VALUE="��   ѯ" TYPE=button onClick="easyQueryClick()">-->
    <a style="color:#fff" href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a><br><br>
      
       <Div  id= "divAgentQuality" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanPremDiscountGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
            
    </Div>
    
   <table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPay);"></td>
    <td class=titleImg>�ۿ���ϸ</td>
    </td>
    </tr>
    </table> 
    
<!--       <table class= common >
    	<tr class= common>
        <TD  class= title>�ۿ�����</TD>
        <TD  class= input>
            <Input style="width:60px" class="codeno" name=DiscountType verify ="�ۿ�����|notnull" ondblclick="return showCodeList('discounttype',[this,DiscounTypeName],[0,1]);" onkeyup="return showCodeListKey('discounttype',[this,DiscounTypeName],[0,1]);"><input style="width:100px" class=codename name=DiscounTypeName readonly=true>
        </TD> 
        <TD  class= title>�ۿۺ���</TD>
        <TD  class= input>
          <Input class=readonly  readonly  name=DiscountCode  >
        </TD>
        
        <TD  class= title>���������</TD>
        <TD  class= input>
          <Input class=common  name=CampaignCode  >
        </TD>
        
        <TD  class= title>�ӷ��ۿ۱��</TD>
       <TD  class= input>
          <Input style="width:60px" class="codeno" name=AddFeeDiscFlag verify ="�ӷ��ۿ۱��|notnull" ondblclick="return showCodeList('yesno',[this,AddFeeDiscFlagName],[0,1]);" onkeyup="return showCodeListKey('yesno',[this,AddFeeDiscFlagName],[0,1]);"><input style="width:100px" class=codename name=AddFeeDiscFlagName readonly=true>
        </TD>
    	</tr>
   
    <tr>
    	 <TD  class= title>���ֱ���</TD>
        <TD  class= input>
            <Input style="width:60px" class="codeno" name=RiskCode  ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input style="width:100px" class=codename name=RiskCodeName readonly=true>
        </TD> 
        <TD  class= title>���α���</TD>
        <TD  class= input>
						<Input style="width:60px" class=codeno name=DutyCode ondblclick="return showCodeList('griskduty',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'a.RiskCode',1);" onkeyup="return showCodeListKey('griskduty',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'a.RiskCode',1);"><input style="width:100px" class=codename name="DutyCodeName" readonly=true>
        </TD> 
        <TD  class= title>
          ��ʼ����
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="StartDate" >
        </TD>
        <TD  class= title>
          ��ֹ����
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="EndDate" >
        </TD>
      </tr>
   
    <tr>  
        <TD  class= title>�㷨����</TD>
        <TD  class= input>
          <Input class=readonly readonly  name=CalCode  >
        </TD>
        <TD  class= title>
        <INPUT CLASS=cssButton VALUE="�����㷨" TYPE=button onclick="defCalRule()">
      </TD>
    </tr>
    </table>--> <!--//2015/7/27-->
    <Div  id= "divPay" style= "display: ''" class="maxbox">
       <table class= common >
    	<tr class= common>
        <TD  class= title5>�ۿ�����</TD>
        <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=DiscountType verify ="�ۿ�����|notnull" onDblClick="return showCodeList('discounttype',[this,DiscounTypeName],[0,1]);" onMouseDown="return showCodeList('discounttype',[this,DiscounTypeName],[0,1]);" onKeyUp="return showCodeListKey('discounttype',[this,DiscounTypeName],[0,1]);" ><input class=codename name=DiscounTypeName readonly=true>
					</TD>
        <TD  class= title5>�ۿۺ���</TD>
        <TD  class= input5>
          <Input class="wid" class=readonly  readonly name=DiscountCode  >
        </TD></tr>
        <tr class= common>
        <TD  class= title5>���������</TD>
        <TD  class= input5>
          <Input class="wid" class=common name=CampaignCode  >
        </TD>
         <TD  class= title5>�ӷ��ۿ۱��</TD>
       <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center;*+background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AddFeeDiscFlag verify ="�ӷ��ۿ۱��|notnull" onDblClick="return showCodeList('yesno',[this,AddFeeDiscFlagName],[0,1]);" onMouseDown="return showCodeList('yesno',[this,AddFeeDiscFlagName],[0,1]);" onKeyUp="return showCodeListKey('yesno',[this,AddFeeDiscFlagName],[0,1]);"><input class=codename name=AddFeeDiscFlagName readonly=true>
					</TD>
        </tr>
        <tr class= common>
       
    	
   
    
    	 <TD  class= title5>���ֱ���</TD>
        <TD class="input5">
               <input style="background:url(../common/images/select--bg_03.png) no-repeat right center";*+style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=RiskCode  ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onMouseDown="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onKeyUp="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);" ><input class=codename name=RiskCodeName readonly=true>
					</TD>
        <TD  class= title5>���α���</TD>
        <TD class="input5">
                        <input style="background:url(../common/images/select--bg_03.png) no-repeat right center";*+style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DutyCode onDblClick="return showCodeList('griskduty',[this,DutyCodeName],[0,1],null,document.RiskCode.value,'a.RiskCode',1);" onMouseDown="return showCodeList('griskduty',[this,DutyCodeName],[0,1],null,document.RiskCode.value,'a.RiskCode',1);" onKeyUp="return showCodeListKey('griskduty',[this,DutyCodeName],[0,1],null,document.RiskCode.value,'a.RiskCode',1);" ><input class=codename name="DutyCodeName" readonly=true>
					</TD>
        </tr> 
        <tr class= common>
        <TD  class= title5>
          ��ʼ����
        </TD>
        <TD class=input5>
					<!--<Input class="laydate-icon" id="demo2" onClick="laydate({elem: '#demo2'});" dateFormat="short" style="width:190px;" name="StartDate"><span class="icon"><a onClick="laydate({elem: '#demo2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
                     <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
        <TD  class= title5>
          ��ֹ����
        </TD>
        <TD class=input5>
					<!--<Input class="laydate-icon"  id="demo3" onClick="laydate({elem: '#demo3'});" dateFormat="short" style="width:190px;" name="EndDate" ><span class="icon"><a onClick="laydate({elem: '#demo3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>  </tr>
      <tr class= common>
   
     
        <TD  class= title5>�㷨����</TD>
        <TD  class= input5>
          <Input class="wid" class=readonly readonly  name=CalCode  id=CalCode  >
        
        
      </TD>
    </tr>
    </table>
    </Div>
<!--    <INPUT CLASS=cssButton VALUE="�����㷨" TYPE=button onClick="defCalRule()">

  <INPUT CLASS=cssButton VALUE="��    ��" TYPE=button onClick="initAll()">
  <INPUT CLASS=cssButton VALUE="��    ��" TYPE=button onClick="InsertClick()">-->
  
  <a style="color:#fff" href="javascript:void(0);" class="button" onClick="defCalRule();">�����㷨</a>
  <a style="color:#fff" href="javascript:void(0);" class="button" onClick="initAll();">��    ��</a>
  <a style="color:#fff" href="javascript:void(0);" class="button" onClick="InsertClick();">��    ��</a>
  <!--INPUT CLASS=cssButton VALUE="�����㷨" TYPE=button onclick="defCalCode()"-->
  <!--INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="UpdateClick()"-->
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> <br><br><br><br>

</body>
</html>

