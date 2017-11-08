<%
//Name:LLClaimEndCaseInit.jsp
//function：
//author: zl
//Date: 2005-6-21 9:31
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
    document.all('ClaimNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    document.all('RgtObjNo').value = nullToEmpty("<%= tGrpRptNo %>");
    if (document.all('RgtObjNo').value!=null && document.all('RgtObjNo').value!="")
    {
       spanRgtObjNo1.style.display = "";
       spanRgtObjNo2.style.display = "";
    }
    else
    {
       spanRgtObjNo1.style.display = "none";
       spanRgtObjNo2.style.display = "none";
    }    
    
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}
	
function initForm()
{ 
  try
  {   
    initParam();
    initInpBox();
//    initLLClaimGrid();
	initLLReCustomerGrid(); //与赔案相关的客户信息列表
    initPolDutyCodeGrid(); //保项计算信息
    LLClaimQuery();
    LLReCustomerGridQuery();

    
    //fm.BarCodePrint.disabled = true; //打印条形码
    //fm.ColQueryImage.disabled = true; //影像查询 
    //fm.ReciInfoInp.disabled = true; //收件人信息
    //fm.BatchCreateNote.disabled = true; //单证批量打印控制
  }
  catch(re)
  {
    alert("在LLClaimEndCaseInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

/**
	页面初始化
*/
function initInpBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("在LLClaimEndCaseInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

//保项计算初始化
function initPolDutyCodeGrid()
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
	    iArray[1][0]="保单号";
	    iArray[1][1]="130px";
	    iArray[1][2]=130;
	    iArray[1][3]=0;

	    iArray[2]=new Array();
	    iArray[2][0]="理赔类型";
	    iArray[2][1]="60px";
	    iArray[2][2]=60;
	    iArray[2][3]=3;

	    iArray[3]=new Array();
	    iArray[3][0]="险种编码";
	    iArray[3][1]="60px";
	    iArray[3][2]=60;
	    iArray[3][3]=3;

	    iArray[4]=new Array();
	    iArray[4][0]="保项编码";
	    iArray[4][1]="100px";
	    iArray[4][2]=100;
	    iArray[4][3]=3;

	    iArray[5]=new Array();
	    iArray[5][0]="保险责任";
	    iArray[5][1]="200px";
	    iArray[5][2]=200;
	    iArray[5][3]=0;


	    iArray[6]=new Array();
	    iArray[6][0]="责任起期";
	    iArray[6][1]="80px";
	    iArray[6][2]=80;
	    iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="责任止期";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;
     
        iArray[8]=new Array();
        iArray[8][0]="宽限天数";
        iArray[8][1]="60px";
        iArray[8][2]=60;
        iArray[8][3]=0;        

	    iArray[9]=new Array();
	    iArray[9][0]="保额";
	    iArray[9][1]="60px";
	    iArray[9][2]=60;
	    iArray[9][3]=0;

	    iArray[10]=new Array();
	    iArray[10][0]="年度红利";
	    iArray[10][1]="60px";
	    iArray[10][2]=60;
	    iArray[10][3]=0;

	    iArray[11]=new Array();
	    iArray[11][0]="终了红利";
	    iArray[11][1]="60px";
	    iArray[11][2]=60;
	    iArray[11][3]=0;

	    iArray[12]=new Array();
	    iArray[12][0]="理算金额";
	    iArray[12][1]="60px";
	    iArray[12][2]=60;
	    iArray[12][3]=0;

        iArray[13]=new Array();
        iArray[13][0]="给付代码";
        iArray[13][1]="60px";
        iArray[13][2]=60;
        iArray[13][3]=2;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        iArray[13][4]="llpayregconclusion"; //是否引用代码: null或者" "为不引用
        iArray[13][5]="13|14";              //引用代码信息分别放在第13列和第14列，'|'为分割符
        iArray[13][6]="0|1";                //引用代码数组的第0项（代码）放在第1列,第1项（名称）放在第2列


        iArray[14]=new Array();
        iArray[14][0]="给付名称";
        iArray[14][1]="60px";
        iArray[14][2]=60;
        iArray[14][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

        iArray[15]=new Array();
        iArray[15][0]="出险期间标识";
        iArray[15][1]="80px";
        iArray[15][2]=60;
        iArray[15][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        

        iArray[16]=new Array();
        iArray[16][0]="dutycode";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏


	    PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
	    PolDutyCodeGrid.mulLineCount = 0;        // 显示行数
	    PolDutyCodeGrid.displayTitle = 1;
	    PolDutyCodeGrid.locked = 0;
	//  PolDutyCodeGrid.canChk =1;               // 多选按钮：1 显示 ；0 隐藏（缺省值）
	    PolDutyCodeGrid.canSel =1;                   // 单选按钮：1 显示 ；0 隐藏（缺省值）
	    PolDutyCodeGrid.hiddenPlus=1;        //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
	    PolDutyCodeGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
	    PolDutyCodeGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }  
}

/**
	出险人信息初始化
*/
function initLLClaimGrid()
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
        iArray[1][0]="立案号"; //
        iArray[1][1]="160px";
        iArray[1][2]=160;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="给付责任类型"; //
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="赔案状态";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
    
        iArray[4]=new Array();
        iArray[4][0]="核算赔付金额";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
    
        iArray[5]=new Array()
        iArray[5][0]="先给付金额";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="核赔赔付金额";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;
    
        iArray[7]=new Array();
        iArray[7][0]="赔付结论";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
    
        iArray[8]=new Array();
        iArray[8][0]="赔付结论描述";
        iArray[8][1]="0px";
        iArray[8][2]=100;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="理赔员";
        iArray[9][1]="0px";
        iArray[9][2]=100;
        iArray[9][3]=3;
    
        iArray[10]=new Array();
        iArray[10][0]="拒赔号";
        iArray[10][1]="0px";
        iArray[10][2]=100;
        iArray[10][3]=3;
        
        iArray[11]=new Array();
        iArray[11][0]="结案日期";
        iArray[11][1]="0px";
        iArray[11][2]=100;
        iArray[11][3]=3;
        
        iArray[12]=new Array();
        iArray[12][0]="案件给付类型";
        iArray[12][1]="0px";
        iArray[12][2]=100;
        iArray[12][3]=3;
        
        iArray[13]=new Array();
        iArray[13][0]="审核类型";
        iArray[13][1]="0px";
        iArray[13][2]=100;
        iArray[13][3]=3;                        

        LLClaimGrid = new MulLineEnter("fm","LLClaimGrid");
        LLClaimGrid.mulLineCount = 5;
        LLClaimGrid.displayTitle = 1;
        LLClaimGrid.locked = 0;
//        LLClaimGrid.canChk = 1;
        LLClaimGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLClaimGrid.selBoxEventFuncName ="LLClaimGridClick"; //响应的函数名，不加扩号
//        LLClaimGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项        
        LLClaimGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LLClaimGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        LLClaimGrid.loadMulLine(iArray);

    }
    catch(ex)
    {
        alert(ex);
    }
}

//与赔案相关的出险人信息列表
function initLLReCustomerGrid()
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
        iArray[1][0]="客户编码"; //原事故者客户号
        iArray[1][1]="230px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="客户姓名"; //事故者姓名
        iArray[2][1]="150px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="性别代码";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="150px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array()
        iArray[6][0]="社保标志代码";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array()
        iArray[7][0]="社保标志";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;  
        
        LLReCustomerGrid = new MulLineEnter("fm","LLReCustomerGrid");
        LLReCustomerGrid.mulLineCount = 0;
        LLReCustomerGrid.displayTitle = 1;
        LLReCustomerGrid.locked = 0;
        LLReCustomerGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLReCustomerGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项
        LLReCustomerGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LLReCustomerGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        LLReCustomerGrid.loadMulLine(iArray);

    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
