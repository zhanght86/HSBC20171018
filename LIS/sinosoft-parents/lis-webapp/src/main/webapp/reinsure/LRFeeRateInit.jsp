<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2006-08-21
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 	GlobalInput tG = new GlobalInput(); 
  	tG=(GlobalInput)session.getAttribute("GI");
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
 		String CurrentDate= PubFun.getCurrentDate();   
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);
    String tCurrentMonth=StrTool.getVisaMonth(CurrentDate);
    String tCurrentDate=StrTool.getVisaDay(CurrentDate);                	               	
 %>
<script type="text/javascript">
function initInpBox()
{
  try
  {
  	fm.FeeTableNo.value 	=	"";
  	fm.FeeTableName.value =	"";
  	fm.FeeTableType.value =	"";
  	fm.ReMark.value =	"";
  	fm.State.value ="02";
  	fm.stateName.value ="未生效";
  	
  }         
  catch(ex) 
  {
    myAlert("进行初始化是出现错误！");
  }
}
;

// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("在LRContInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initTableClumnDefGrid();
    verifyFeeRateTableImp();
  }
  catch(re)
  {
    myAlert("ReContManageInit.jsp-->"+"初始化界面错误!");
  }
}


//再保合同签订人列表初始化
function initTableClumnDefGrid() 
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;
    
    iArray[1]=new Array();    
    iArray[1][0]="费率表字段";
    iArray[1][1]="60px";      
    iArray[1][2]=100;         
    iArray[1][3]=1;  
    iArray[1][9]="费率表字段名|notnull";
    iArray[1][19]=1;
    
    iArray[2]=new Array();    
    iArray[2][0]="接口表字段名";
    iArray[2][1]="60px";      
    iArray[2][2]=100;         
    iArray[2][3]=2;  
    iArray[2][10]="AccType"; 
    iArray[2][11]="0|^GrpContNo|集体合同号码(字符型)^ContNo|合同号码(字符型)^RiskCode|险种编码(字符型)^DutyCode|责任编码(字符型)^Years|保险年期(整数型)^InsuredYear|保单年度(整数型)^CValiDate|保单生效日期(日期型)^EndDate|保险责任终止日期(日期型)^InsuredNo|被保人客户号码(字符型)^InsuredSex|被保人性别(字符型)^InsuredAge|被保人年龄(字符型)^OccupationType|被保人职业类别(字符型)^StandPrem|标准保费(数字型)^Prem|实际保费(数字型)^RiskAmnt|风险保额(数字型)^Amnt|基本保额(数字型)^Mult|份数(整数型)^PayIntv|交费间隔(整数型)^PayYears|交费年期(整数型)^AcciEndDate|意外责任终止日期(日期型)^GetStartDate|起领日期(日期型)^GetStartType|起领日期计算类型(字符)^GetRate |赔付比例(数字型)^GetIntv |领取间隔(整数型)^PayCount|交费期数(整数型)^PayMoney|交费金额(数字型)^LastPayToDate|原交至日期(日期型)^CurPayToDate |现交至日期(日期型)^EdorNo|批单号(字符型)^FeeOperationType|批改业务类型(字符型)^FeeFinaType|批改财务类型(字符型)^ClmNo|赔案号(字符型)^ClmFeeOperationType|赔案业务类型(字符型)^ClmFeeFinaType|赔案财务类型(字符型)^StandGetMoney|标准赔款(数字型)^ClmGetMoney|实际赔款(数字型)^GetCValiDate|生效日期(日期型)^GetDate|业务日期(日期型)^ContPlanCode|保险计划编码^StandbyString2|弱体等级^StandbyString3|计划别";
		iArray[2][12]="2";
		iArray[2][13]="0";  
    iArray[2][9]="接口表字段|notnull";         
    iArray[2][19]=1;
    
    
    
    iArray[3]=new Array();
    iArray[3][0]="费率表字段类型名";
    iArray[3][1]="80px";
    iArray[3][2]=100;
    iArray[3][3]=2;
    iArray[3][10]="AccType0"; 
    iArray[3][11]="0|^A01|字符型固定值^A02|字符型区间值^N01|数值型固定值^N02|数值型区间值";
		iArray[3][12]="3|4";
		iArray[3][13]="1|0";  
    iArray[3][19]=1;
    
    iArray[4]=new Array();
    iArray[4][0]="费率表字段类型";
    iArray[4][1]="80px";
    iArray[4][2]=100;
    iArray[4][3]=0;
    iArray[4][9]="接口表字段|notnull";
    iArray[4][19]=1;
    
    iArray[5]=new Array();
    iArray[5][0]="固定值映射字段名";  //列名
    iArray[5][1]="80px";           	//列宽
    iArray[5][2]=200;            		//列最大值
    iArray[5][3]=2;              	  //是否允许输入,1表示允许，0表示不允许   
    iArray[5][10]="AccType1"; 
    iArray[5][11]="0|^|^NumOne|数字字段一^NumTwo|数字字段二^NumThree|数字字段三^NumFour|数字字段四^NumFive|数字字段五^NumSix|数字字段六^NumSeven|数字字段七^NumEight|数字字段八^NumNine|数字字段九^NumTen|数字字段十^StrOne|字符字段一^StrTwo|字符字段二^StrThree|字符字段三^StrFour|字符字段四^StrFive|字符字段五|";
		iArray[5][12]="5|6";
		iArray[5][13]="0|1";
		iArray[5][19]=1;
		
		iArray[6]=new Array();
    iArray[6][0]="固定值映射字段";
    iArray[6][1]="0px";
    iArray[6][2]=200;
    iArray[6][3]=3;
    iArray[6][19]=1;
	
    iArray[7]=new Array();
    iArray[7][0]="区间下限限映射字段名";         		//列名
    iArray[7][1]="80px";            //列宽
    iArray[7][2]=60;            		//列最大值
    iArray[7][3]=2;              		//2表示是代码选择
    iArray[7][10]="AccType2"; 
    iArray[7][11]="0|^|^NumOne|数字字段一^NumTwo|数字字段二^NumThree|数字字段三^NumFour|数字字段四^NumFive|数字字段五^NumSix|数字字段六^NumSeven|数字字段七^NumEight|数字字段八^NumNine|数字字段九^NumTen|数字字段十^StrOne|字符字段一^StrTwo|字符字段二^StrThree|字符字段三^StrFour|字符字段四^StrFive|字符字段五|"	 ;
		iArray[7][12]="7|8";
		iArray[7][13]="0|1";
		iArray[7][19]=1;
    
    iArray[8]=new Array();
    iArray[8][0]="区间下限限字段映射";
    iArray[8][1]="0px";
    iArray[8][2]=200;
    iArray[8][3]=3;
    iArray[8][19]=1;

    iArray[9]=new Array();
    iArray[9][0]="区间上限字段映射名";        //列名
    iArray[9][1]="90px";           //列宽
    iArray[9][2]=60;            		//列最大值
    iArray[9][3]=2;              		//是否允许输入,1表示允许，0表示不允许
    iArray[9][10]="AccType3"; 
    iArray[9][11]="0|^|^NumOne|数字字段一^NumTwo|数字字段二^NumThree|数字字段三^NumFour|数字字段四^NumFive|数字字段五^NumSix|数字字段六^NumSeven|数字字段七^NumEight|数字字段八^NumNine|数字字段九^NumTen|数字字段十^StrOne|字符字段一^StrTwo|字符字段二^StrThree|字符字段三^StrFour|字符字段四^StrFive|字符字段五|"	 ;
		iArray[9][12]="9|10";
		iArray[9][13]="0|1";
		iArray[9][19]=1;
    
		iArray[10]=new Array();
    iArray[10][0]="区间上限字段映射";
    iArray[10][1]="0px";
    iArray[10][2]=200;
    iArray[10][3]=3;
    iArray[10][19]=1;
    
    TableClumnDefGrid = new MulLineEnter( "fm" , "TableClumnDefGrid" );
    TableClumnDefGrid.mulLineCount = 1;   
	  TableClumnDefGrid.displayTitle = 1;
//  TableClumnDefGrid.locked = 0;
	  TableClumnDefGrid.canSel =0; 
//  TableClumnDefGrid.hiddenPlus=0; 
//  TableClumnDefGrid.hiddenSubtraction=1;
	  TableClumnDefGrid.loadMulLine(iArray); 
  }
  
  catch(ex)
  {
    myAlert("初始化时出错:"+ex);
  }
}

</script> 