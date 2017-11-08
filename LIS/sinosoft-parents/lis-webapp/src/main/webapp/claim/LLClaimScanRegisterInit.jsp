<%
//**************************************************************************************************
//Name: LLClaimRegisterInit.jsp
//function：立案界面初始化
//author: zl
//Date: 2005-6-14 20:03
//**************************************************************************************************
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
    
var mCurrentDate = "";
var mNowYear = "";


//接收报案页面传递过来的参数
function initParam()
{

	//var sysdatearr=easyExecSql("select to_date(sysdate) from dual"); //取服务器的时间作为系统当前日期
	var tResourceName="claim.LLClaimScanRegisterInputSql";
	var tSQL = wrapSql(tResourceName,"querysqldes1",["1"]);
	var sysdatearr=easyExecSql(tSQL);
	mCurrentDate=sysdatearr[0][0];

    document.all('RptNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");           
    document.all('isNew').value = nullToEmpty("<%= tIsNew %>");
    document.all('clmState').value = nullToEmpty("<%= tClmState %>");
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    document.all('ActivityID').value = nullToEmpty("<%= tActivityID %>");


	
    mNowYear = mCurrentDate.substring(0,4);

    spanConclusion1.style.display="none";


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

//初始化表单
function initForm()
{
    try
    {
        initParam();
        initInpBox();
        initSubReportGrid();
        initClaimGrid();        
        initDutyKindGrid();
        initPolDutyKindGrid()
        initPolDutyCodeGrid();
        //******************************************************************************************Beg
        //通过传入参数isNew和clmState判断显示[修改]还是[保存]按钮
        //--新增：isNew=0 ，直接显示[保存]按钮
        //--共享池或工作队列：isNew=1&&clmState=10 ，立案表信息不存在，查询报案表，显示[保存]按钮
        //                    isNew=1&&clmState=20||30 ，立案表信息存在，查询立案表，显示[修改]按钮
        //******************************************************************************************
        if(fm.isNew.value == '0')
        {
            fm.isReportExist.value = "false";
            fm.isRegisterExist.value = "false";
            //显示[保存]按钮栏
            operateButton21.style.display="";
            //设置可操作按钮
            disabledButton();
            fm.QueryPerson.disabled = false;
			fm.QueryReportClm.disabled=false;
            fm.QueryReport.disabled = false;
            fm.QueryIssue.disabled = false;
            fm.addbutton.disabled = false;
            fm.goBack.disabled = false;
            fm.ColQueryImage.disabled = false;
        //  fm.printDelayRgt.disabled = false; //可打印补充单证
            document.all('MedicalAccidentDate').disabled=true;
            document.all('OtherAccidentDate').disabled=true;
        }
        else
        {
    			fm.QueryReportClm.disabled=false;
    			 fm.QueryIssue.disabled = false;
                fm.isReportExist.value = "true";
                fm.isRegisterExist.value = "true";             
                //显示[修改]按钮栏
                operateButton22.style.display="";
                //查询立案
                queryRegister();
                //设置可操作按钮
                fm.QueryPerson.disabled = true;
                fm.QueryReport.disabled = true;

                document.all('MedicalAccidentDate').disabled=false;
                document.all('OtherAccidentDate').disabled=false;   

        }
        
        //******************************************************************************************End
    }
    catch(ex)
    {
        alert("在LLRegisterInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//页面输入域初始化
function initInpBox()
{
    try
    {
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = true;
    }
    catch(ex)
    {
        alert("在LLReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

//出险人信息列表初始化
function initSubReportGrid()
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
        iArray[1][0]="客户号码"; //原事故者客户号
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

//        iArray[3]=new Array();
//        iArray[3][0]="医院代码";
//        iArray[3][1]="0px";
//        iArray[3][2]=60;
//        iArray[3][3]=3;
//        iArray[3][4]="HospitalCode";
//        iArray[3][5]="3|4";                                //引用代码对应第几列，'|'为分割符
//        iArray[3][6]="0|1";                        //上面的列中放置引用代码中第几位值
//        iArray[3][9]="医院代码|NOTNULL";

//    iArray[4]=new Array()
//    iArray[4][0]="医院名称";
//    iArray[4][1]="0px";
//    iArray[4][2]=100;
//    iArray[4][3]=3;

//    iArray[5]=new Array()
//    iArray[5][0]="入院日期";
//    iArray[5][1]="0px";
//    iArray[5][2]=100;
//    iArray[5][3]=3;

        iArray[6]=new Array()
        iArray[6][0]="社保标志代码";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array()
        iArray[7][0]="社保标志";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;  

//        iArray[6]=new Array();
//        iArray[6][0]="出险细节";
//        iArray[6][1]="0px";
//        iArray[6][2]=100;
//        iArray[6][3]=3;
//
//        iArray[7]=new Array();
//        iArray[7][0]="治疗医院";
//        iArray[7][1]="0px";
//        iArray[7][2]=100;
//        iArray[7][3]=3;
//
//        iArray[8]=new Array();
//        iArray[8][0]="治疗情况";
//        iArray[8][1]="0px";
//        iArray[8][2]=100;
//        iArray[8][3]=3;
//
//        iArray[9]=new Array();
//        iArray[9][0]="死亡标识";
//        iArray[9][1]="0px";
//        iArray[9][2]=100;
//        iArray[9][3]=3;
//
//        iArray[10]=new Array();
//        iArray[10][0]="备注信息";
//        iArray[10][1]="0px";
//        iArray[10][2]=100;
//        iArray[10][3]=3;

        SubReportGrid = new MulLineEnter("document","SubReportGrid");
        SubReportGrid.mulLineCount = 5;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
//        SubReportGrid.canChk =1;
        SubReportGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        SubReportGrid.selBoxEventFuncName ="allSubReportGridClick"; //响应的函数名，不加扩号
        SubReportGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项
        SubReportGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        SubReportGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        SubReportGrid.loadMulLine(iArray);

    }
    catch(ex)
    {
        alert(ex);
    }
}


/**=========================================================================
    修改状态：开始
    修改原因：以下为理赔计算的结果区，分别显示为赔案的理赔类型，保单的理赔类型，保单理赔类型的保项
    修 改 人：续涛
    修改日期：2005.06.25
   =========================================================================
**/

//赔案总金额
function initClaimGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;
        
        iArray[1]=new Array();
	      iArray[1][0]="币种";      	   		//列名
	      iArray[1][1]="80px";            			//列宽
	      iArray[1][2]=20;            			//列最大值
	      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="赔付金额";
        iArray[2][1]="80px";
        iArray[2][2]=80;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="预付金额";
        iArray[3][1]="80px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="结算金额";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="最终赔付金额";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="拒赔金额";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=3;
        

        ClaimGrid = new MulLineEnter("document","ClaimGrid");
        ClaimGrid.mulLineCount = 5;       // 显示行数
        ClaimGrid.displayTitle = 1;
        ClaimGrid.locked = 0;
    //  ClaimGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
        ClaimGrid.canSel =1;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
        ClaimGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        ClaimGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        ClaimGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}



//赔案的理赔类型
function initDutyKindGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="理赔类型代码";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="理赔类型名称";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
	      iArray[3][0]="币种";      	   		//列名
	      iArray[3][1]="80px";            			//列宽
	      iArray[3][2]=20;            			//列最大值
	      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="账单金额";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="自费/自付金额";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="参与计算金额";      //也就是理算金额
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="保单合计理算金额";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="社保给付";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="第三方给付";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;

        iArray[10]=new Array();
        iArray[10][0]="核赔赔付金额";
        iArray[10][1]="80px";
        iArray[10][2]=80;
        iArray[10][3]=0;
        

        DutyKindGrid = new MulLineEnter("document","DutyKindGrid");
        DutyKindGrid.mulLineCount = 5;       // 显示行数
        DutyKindGrid.displayTitle = 1;
        DutyKindGrid.locked = 0;
    //  DutyKindGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
        DutyKindGrid.canSel =1;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
        DutyKindGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        DutyKindGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        DutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


//保单、理赔类型计算初始化
function initPolDutyKindGrid()
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
	    iArray[1][0]="合同号";
	    iArray[1][1]="105px";
	    iArray[1][2]=105;
	    iArray[1][3]=0;

	    iArray[2]=new Array();
	    iArray[2][0]="保单号";
	    iArray[2][1]="105px";
	    iArray[2][2]=105;
	    iArray[2][3]=0;

	    iArray[3]=new Array();
	    iArray[3][0]="理赔类型";
	    iArray[3][1]="60px";
	    iArray[3][2]=60;
	    iArray[3][3]=0;

	    iArray[4]=new Array();
	    iArray[4][0]="生效日期";
	    iArray[4][1]="70px";
	    iArray[4][2]=60;
	    iArray[4][3]=0;

	    iArray[5]=new Array();
	    iArray[5][0]="交至日期";
	    iArray[5][1]="70px";
	    iArray[5][2]=60;
	    iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="险种代码";
        iArray[6][1]="60px";
        iArray[6][2]=60;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="险种名称";
        iArray[7][1]="140px";
        iArray[7][2]=140;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
	      iArray[8][0]="币种";      	   		//列名
	      iArray[8][1]="80px";            			//列宽
	      iArray[8][2]=20;            			//列最大值
	      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[9]=new Array();
        iArray[9][0]="理算金额";
        iArray[9][1]="60px";
        iArray[9][2]=60;
        iArray[9][3]=0;
        
        
	    PolDutyKindGrid = new MulLineEnter("document","PolDutyKindGrid");
	    PolDutyKindGrid.mulLineCount = 5;       // 显示行数
	    PolDutyKindGrid.displayTitle = 1;
	    PolDutyKindGrid.locked = 0;
	//  PolDutyKindGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
	    PolDutyKindGrid.canSel =1;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
	    PolDutyKindGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
	    PolDutyKindGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
	    PolDutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
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
	      iArray[9][0]="币种";      	   		//列名
	      iArray[9][1]="80px";            			//列宽
	      iArray[9][2]=20;            			//列最大值
	      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许       

	    iArray[10]=new Array();
	    iArray[10][0]="保额";
	    iArray[10][1]="60px";
	    iArray[10][2]=60;
	    iArray[10][3]=0;

	    iArray[11]=new Array();
	    iArray[11][0]="年度红利";
	    iArray[11][1]="60px";
	    iArray[11][2]=60;
	    iArray[11][3]=0;

	    iArray[12]=new Array();
	    iArray[12][0]="终了红利";
	    iArray[12][1]="60px";
	    iArray[12][2]=60;
	    iArray[12][3]=0;

	    iArray[13]=new Array();
	    iArray[13][0]="理算金额";
	    iArray[13][1]="60px";
	    iArray[13][2]=60;
	    iArray[13][3]=0;

        iArray[14]=new Array();
        iArray[14][0]="给付代码";
        iArray[14][1]="60px";
        iArray[14][2]=60;
        iArray[14][3]=2;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        iArray[14][4]="llpayregconclusion"; //是否引用代码: null或者" "为不引用
        iArray[14][5]="13|14";              //引用代码信息分别放在第13列和第14列，'|'为分割符
        iArray[14][6]="0|1";                //引用代码数组的第0项（代码）放在第1列,第1项（名称）放在第2列


        iArray[15]=new Array();
        iArray[15][0]="给付名称";
        iArray[15][1]="60px";
        iArray[15][2]=60;
        iArray[15][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

        iArray[16]=new Array();
        iArray[16][0]="出险期间标识";
        iArray[16][1]="80px";
        iArray[16][2]=60;
        iArray[16][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        

        iArray[17]=new Array();
        iArray[17][0]="dutycode";
        iArray[17][1]="60px";
        iArray[17][2]=60;
        iArray[17][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏


	    PolDutyCodeGrid = new MulLineEnter("document","PolDutyCodeGrid");
	    PolDutyCodeGrid.mulLineCount = 5;        // 显示行数
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
/**=========================================================================
    修改状态：结束
    修改原因：以下为理赔计算的结果区，分别显示为赔案的理赔，保单的理赔类型，保单理赔类型的保项
    修 改 人：续涛
    修改日期：2005.06.25
   =========================================================================
**/

</script>
