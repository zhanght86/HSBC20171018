<%
//Name: LLMedicalFeeInpInit.jsp
//function: 
//author: 续涛
//Date: 2005.05.13
%>

<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

var mCurrentDate = "";

//接收报案页面传递过来的参数
function initParam()
{
    mCurrentDate = nullToEmpty("<%= CurrentDate %>");
    
    fm.all('claimNo').value = nullToEmpty("<%= tclaimNo %>");
    fm.all('caseNo').value = nullToEmpty("<%= tcaseNo %>");
    fm.all('custNo').value = nullToEmpty("<%= tcustNo %>");
    fm.all('accDate1').value = nullToEmpty("<%= taccDate1 %>"); //意外事故发生日期
    fm.all('accDate2').value = nullToEmpty("<%= taccDate2 %>"); //出险日期
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
        initInpBox();                   //初始化表格
        initMedFeeClinicInpGrid();      //门诊信息录入
        initMedFeeInHosInpGrid();       //住院信息录入
        initMedFeeCaseInfoGrid();       //伤残
        initMedFeeOperGrid();           //手术
        initMedFeeOtherGrid();          //其他
        initMedFeeThreeGrid();          //社保第三方给付
    }
    catch(re)
    {
        alert("LLMedicalFeeInpInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

/**=========================================================================
    页面初始化
   =========================================================================
*/
function initInpBox()
{
  try
  {
   
    //门诊信息
    fm.ClinicHosID.value = '';
    fm.ClinicHosName.value = '';
    fm.ClinicHosGrade.value = '';
          
    fm.ClinicMedFeeType.value = '';
    fm.ClinicMedFeeTypeName.value = '';
    fm.ClinicMedFeeSum.value = '';
        
    fm.ClinicStartDate.value = '';
    fm.ClinicEndDate.value = '';
    fm.ClinicDayCount1.value = '';  
            
//    MedFeeInHosInp.style.display="none";
  }
  catch(ex)
  {
    alert("LLMedicalFeeInpInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

/**=========================================================================
    门诊单证录入信息
   =========================================================================
*/
function initMedFeeClinicInpGrid()
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
        iArray[1][0]="医院代码";
        iArray[1][1]="0px";
        iArray[1][2]=10;
        iArray[1][3]=3;

        
        iArray[2]=new Array();
        iArray[2][0]="医院名称";
        iArray[2][1]="100px";
        iArray[2][2]=10;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="医院等级";
        iArray[3][1]="50px";
        iArray[3][2]=10;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="开始日期";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="结束日期";
        iArray[5][1]="80px";
        iArray[5][2]=10;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="天数";
        iArray[6][1]="30px";
        iArray[6][2]=10;
        iArray[6][3]=0;                

        iArray[7]=new Array();
        iArray[7][0]="费用代码";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="费用类型";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="费用金额";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
//                
        iArray[11]=new Array();
        iArray[11][0]="赔案号";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="分案号";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;
        
        iArray[13]=new Array();
        iArray[13][0]="出险人客户号";
        iArray[13][1]="0px";
        iArray[13][2]=10;
        iArray[13][3]=3;        

        iArray[14]=new Array();
        iArray[14][0]="账单号";
        iArray[14][1]="0px";
        iArray[14][2]=10;
        iArray[14][3]=3;  

        iArray[15]=new Array();
        iArray[15][0]="账单费用明细号";
        iArray[15][1]="0px";
        iArray[15][2]=10;
        iArray[15][3]=3;  
                                
        iArray[16]=new Array();
        iArray[16][0]="开始日期是否早于出险日期";
        iArray[16][1]="0px";
        iArray[16][2]=10;
        iArray[16][3]=3;  

        iArray[17]=new Array();
        iArray[17][0]="特殊标志";
        iArray[17][1]="60px";
        iArray[17][2]=10;
        iArray[17][3]=0;  
                        
        MedFeeClinicInpGrid = new MulLineEnter("fm","MedFeeClinicInpGrid");
        MedFeeClinicInpGrid.mulLineCount = 0;
        MedFeeClinicInpGrid.displayTitle = 1;
        MedFeeClinicInpGrid.locked = 0;
//      MedFeeClinicInpGrid.canChk =1;              //多选按钮，1显示，0隐藏
        MedFeeClinicInpGrid.canSel =1;              //单选按钮，1显示，0隐藏
        MedFeeClinicInpGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        MedFeeClinicInpGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        MedFeeClinicInpGrid.selBoxEventFuncName = "getMedFeeClinicInpGrid"; //函数名称
//        MedFeeClinicInpGrid.selBoxEventFuncParm ="";          //参数        
        MedFeeClinicInpGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
    住院录入信息
   =========================================================================
*/
function initMedFeeInHosInpGrid()
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
        iArray[1][0]="医院代码";
        iArray[1][1]="0px";
        iArray[1][2]=10;
        iArray[1][3]=3;

        
        iArray[2]=new Array();
        iArray[2][0]="医院名称";
        iArray[2][1]="100px";
        iArray[2][2]=10;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="医院等级";
        iArray[3][1]="50px";
        iArray[3][2]=10;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="开始日期";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="结束日期";
        iArray[5][1]="80px";
        iArray[5][2]=10;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="天数";
        iArray[6][1]="30px";
        iArray[6][2]=10;
        iArray[6][3]=0;                

        iArray[7]=new Array();
        iArray[7][0]="费用代码";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="费用类型";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="费用金额";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
//                
        iArray[11]=new Array();
        iArray[11][0]="赔案号";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="分案号";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;
        
        iArray[13]=new Array();
        iArray[13][0]="出险人客户号";
        iArray[13][1]="0px";
        iArray[13][2]=10;
        iArray[13][3]=3; 
        
        iArray[14]=new Array();
        iArray[14][0]="账单号";
        iArray[14][1]="0px";
        iArray[14][2]=10;
        iArray[14][3]=3;  

        iArray[15]=new Array();
        iArray[15][0]="账单费用明细号";
        iArray[15][1]="0px";
        iArray[15][2]=10;
        iArray[15][3]=3;  
        
        iArray[16]=new Array();
        iArray[16][0]="开始日期是否早于出险日期";
        iArray[16][1]="0px";
        iArray[16][2]=10;
        iArray[16][3]=3;  

        iArray[17]=new Array();
        iArray[17][0]="特殊标志";
        iArray[17][1]="60px";
        iArray[17][2]=10;
        iArray[17][3]=0;  
        
        MedFeeInHosInpGrid = new MulLineEnter("fm","MedFeeInHosInpGrid");
        MedFeeInHosInpGrid.mulLineCount = 0;
        MedFeeInHosInpGrid.displayTitle = 1;
        MedFeeInHosInpGrid.locked = 0;
//      MedFeeInHosInpGrid.canChk =1;              //多选按钮，1显示，0隐藏
        MedFeeInHosInpGrid.canSel =1;              //单选按钮，1显示，0隐藏
        MedFeeInHosInpGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        MedFeeInHosInpGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        MedFeeInHosInpGrid.selBoxEventFuncName = "getMedFeeInHosInpGrid"; //函数名称
//        MedFeeInHosInpGrid.selBoxEventFuncParm ="";          //参数        
        MedFeeInHosInpGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
    伤残录入信息
   =========================================================================
*/
function initMedFeeCaseInfoGrid()
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
        iArray[1][0]="残疾类型";
        iArray[1][1]="80px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="伤残级别";
        iArray[2][1]="50px";
        iArray[2][2]=10;
        iArray[2][3]=3;        
        
        iArray[3]=new Array();
        iArray[3][0]="伤残级别名称";
        iArray[3][1]="100px";
        iArray[3][2]=10;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="伤残代码";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="残疾原因";
        iArray[5][1]="50px";
        iArray[5][2]=10;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="残疾给付比例";
        iArray[6][1]="80px";
        iArray[6][2]=10;
        iArray[6][3]=0;                

        iArray[7]=new Array();
        iArray[7][0]="申请给付比例";
        iArray[7][1]="80px";
        iArray[7][2]=10;
        iArray[7][3]=0;  
        
        iArray[8]=new Array();
        iArray[8][0]="实际给付比例";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="赔案号";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=3;  
        
        iArray[10]=new Array();
        iArray[10][0]="分案号";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
//                
        iArray[11]=new Array();
        iArray[11][0]="序号";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="出险人客户号";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;
        
        iArray[13]=new Array();
        iArray[13][0]="鉴定机构名称";
        iArray[13][1]="100px";
        iArray[13][2]=10;
        iArray[13][3]=0;
        
        iArray[14]=new Array();
        iArray[14][0]="鉴定时间";
        iArray[14][1]="80px";
        iArray[14][2]=10;
        iArray[14][3]=0;  
        
        MedFeeCaseInfoGrid = new MulLineEnter("fm","MedFeeCaseInfoGrid");
        MedFeeCaseInfoGrid.mulLineCount = 0;
        MedFeeCaseInfoGrid.displayTitle = 1;
        MedFeeCaseInfoGrid.locked = 0;
//      MedFeeCaseInfoGrid.canChk =1;              //多选按钮，1显示，0隐藏
        MedFeeCaseInfoGrid.canSel =1;              //单选按钮，1显示，0隐藏
        MedFeeCaseInfoGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        MedFeeCaseInfoGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        MedFeeCaseInfoGrid.selBoxEventFuncName = "getMedFeeCaseInfoGrid"; //函数名称
//        MedFeeCaseInfoGrid.selBoxEventFuncParm ="divLLInqCourse";          //参数        
        MedFeeCaseInfoGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
    手术特种疾病信息
   =========================================================================
*/
function initMedFeeOperGrid()
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
        iArray[1][0]="类型";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="代码";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="名称";
        iArray[3][1]="150px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="档次";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=3;
        
        iArray[5]=new Array();
        iArray[5][0]="等级";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="金额";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;                

        iArray[7]=new Array();
        iArray[7][0]="主切口标志";
        iArray[7][1]="80px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
             
        iArray[8]=new Array();
        iArray[8][0]="赔案号";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="分案号";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=3;  
        
        iArray[10]=new Array();
        iArray[10][0]="序号";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
                
        iArray[11]=new Array();
        iArray[11][0]="出险人客户号";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="医疗机构名称";
        iArray[12][1]="150px";
        iArray[12][2]=100;
        iArray[12][3]=0;
        
        iArray[13]=new Array();
        iArray[13][0]="确诊日期";
        iArray[13][1]="100px";
        iArray[13][2]=100;
        iArray[13][3]=0;  
        
        MedFeeOperGrid = new MulLineEnter("fm","MedFeeOperGrid");
        MedFeeOperGrid.mulLineCount = 0;
        MedFeeOperGrid.displayTitle = 1;
        MedFeeOperGrid.locked = 0;
//      MedFeeOperGrid.canChk =1;              //多选按钮，1显示，0隐藏
        MedFeeOperGrid.canSel =1;              //单选按钮，1显示，0隐藏
        MedFeeOperGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        MedFeeOperGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        MedFeeOperGrid.selBoxEventFuncName = "getMedFeeOperGrid"; //函数名称
//        MedFeeOperGrid.selBoxEventFuncParm ="divLLInqCourse";          //参数        
        MedFeeOperGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
    其他录入信息
   =========================================================================
*/
function initMedFeeOtherGrid()
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
        iArray[1][0]="费用类型";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="特种费用代码";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="特种费用名称";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="特种费用金额";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="赔案号";
        iArray[5][1]="80px";
        iArray[5][2]=10;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="分案号";
        iArray[6][1]="30px";
        iArray[6][2]=10;
        iArray[6][3]=3;                

        iArray[7]=new Array();
        iArray[7][0]="序号";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="出险人客户号";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="服务机构名称";
        iArray[9][1]="120px";
        iArray[9][2]=10;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="起始日期";
        iArray[10][1]="80px";
        iArray[10][2]=100;
        iArray[10][3]=0;
                
        iArray[11]=new Array();
        iArray[11][0]="结束日期";
        iArray[11][1]="80px";
        iArray[11][2]=100;
        iArray[11][3]=0;  
//        
//        iArray[12]=new Array();
//        iArray[12][0]="分案号";
//        iArray[12][1]="0px";
//        iArray[12][2]=10;
//        iArray[12][3]=3;
//        
//        iArray[13]=new Array();
//        iArray[13][0]="出险人客户号";
//        iArray[13][1]="0px";
//        iArray[13][2]=10;
//        iArray[13][3]=3;  
        
        MedFeeOtherGrid = new MulLineEnter("fm","MedFeeOtherGrid");
        MedFeeOtherGrid.mulLineCount = 0;
        MedFeeOtherGrid.displayTitle = 1;
        MedFeeOtherGrid.locked = 0;
//      MedFeeOtherGrid.canChk =1;              //多选按钮，1显示，0隐藏
        MedFeeOtherGrid.canSel =1;              //单选按钮，1显示，0隐藏
        MedFeeOtherGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        MedFeeOtherGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        MedFeeOtherGrid.selBoxEventFuncName = "getMedFeeOtherGrid"; //函数名称
//        MedFeeOtherGrid.selBoxEventFuncParm ="divLLInqCourse";          //参数        
        MedFeeOtherGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
    社保第三方给付
   =========================================================================
*/
function initMedFeeThreeGrid()
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
        iArray[1][0]="费用类型";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="费用代码";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="费用名称";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="费用金额";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="赔案号";
        iArray[5][1]="80px";
        iArray[5][2]=10;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="分案号";
        iArray[6][1]="30px";
        iArray[6][2]=10;
        iArray[6][3]=3;                

        iArray[7]=new Array();
        iArray[7][0]="序号";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="出险人客户号";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="服务机构名称";
        iArray[9][1]="120px";
        iArray[9][2]=10;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="备注";
        iArray[10][1]="80px";
        iArray[10][2]=100;
        iArray[10][3]=3;
//                
//        iArray[11]=new Array();
//        iArray[11][0]="结束日期";
//        iArray[11][1]="80px";
//        iArray[11][2]=10;
//        iArray[11][3]=0;  
////        
//        iArray[12]=new Array();
//        iArray[12][0]="分案号";
//        iArray[12][1]="0px";
//        iArray[12][2]=10;
//        iArray[12][3]=3;
//        
//        iArray[13]=new Array();
//        iArray[13][0]="出险人客户号";
//        iArray[13][1]="0px";
//        iArray[13][2]=10;
//        iArray[13][3]=3;  
        
        MedFeeThreeGrid = new MulLineEnter("fm","MedFeeThreeGrid");
        MedFeeThreeGrid.mulLineCount = 0;
        MedFeeThreeGrid.displayTitle = 1;
        MedFeeThreeGrid.locked = 0;
//      MedFeeThreeGrid.canChk =1;              //多选按钮，1显示，0隐藏
        MedFeeThreeGrid.canSel =1;              //单选按钮，1显示，0隐藏
        MedFeeThreeGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        MedFeeThreeGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        MedFeeThreeGrid.selBoxEventFuncName = "getMedFeeThreeGrid"; //函数名称
//        MedFeeThreeGrid.selBoxEventFuncParm ="divLLInqCourse";          //参数        
        MedFeeThreeGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>

