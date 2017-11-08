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

//接收报案页面传递过来的参数
function initParam()
{
    document.all('claimNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('caseNo').value = nullToEmpty("<%= tCaseNo %>");
    document.all('custNo').value = nullToEmpty("<%= tCustNo %>");
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

/**
    Form载入页面时进行初始化
*/
function initForm()
{
    try
    {
        initParam();
        initInpBox();                   //初始化表格
        initMedFeeCalGrid();            //初始化费用计算信息
        showMedFeeCalGrid();            //显示费用计算信息
    }
    catch(re)
    {
        alert("LLMedicalFeeInpInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
        alert("LLClaimRegisterMedFeeCalInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}


/**
    门诊单证录入信息
*/
function initMedFeeCalGrid()
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
        iArray[1][1]="120px";
        iArray[1][2]=120;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="理赔类型";
        iArray[2][1]="60px";
        iArray[2][2]=60;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="险种";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="保项名称";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="费用类型";
        iArray[5][1]="50px";
        iArray[5][2]=50;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="费用名称";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0;                

        iArray[7]=new Array();
        iArray[7][0]="序号";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="医院编号";
        iArray[8][1]="60px";
        iArray[8][2]=60;
        iArray[8][3]=3;
        
        
        iArray[9]=new Array();
        iArray[9][0]="医院名称";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="开始时间";
        iArray[10][1]="80px";
        iArray[10][2]=80;
        iArray[10][3]=0;
                
        iArray[11]=new Array();
        iArray[11][0]="结束时间";
        iArray[11][1]="80px";
        iArray[11][2]=80;
        iArray[11][3]=0;  
        
                        
        iArray[12]=new Array();
        iArray[12][0]="天数";
        iArray[12][1]="30px";
        iArray[12][2]=30;
        iArray[12][3]=0;
        
        iArray[13]=new Array();
      iArray[13][0]="币种";      	   		//列名
      iArray[13][1]="80px";            			//列宽
      iArray[13][2]=20;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[14]=new Array();
        iArray[14][0]="原始金额";
        iArray[14][1]="80px";
        iArray[14][2]=40;
        iArray[14][3]=0;        
                
        iArray[15]=new Array();
        iArray[15][0]="调整金额";
        iArray[15][1]="80px";
        iArray[15][2]=40;
        iArray[15][3]=0;
        
                        
        iArray[16]=new Array();
        iArray[16][0]="免赔金额";
        iArray[16][1]="80px";
        iArray[16][2]=40;
        iArray[16][3]=0;
                
                
        iArray[17]=new Array();
        iArray[17][0]="理算金额";
        iArray[17][1]="80px";
        iArray[17][2]=40;
        iArray[17][3]=0;
                        
                
        MedFeeCalGrid = new MulLineEnter("document","MedFeeCalGrid");
        MedFeeCalGrid.mulLineCount = 5;
        MedFeeCalGrid.displayTitle = 1;
        MedFeeCalGrid.locked = 0;
//      //MedFeeCalGrid.canChk =1;              //多选按钮，1显示，0隐藏
        MedFeeCalGrid.canSel =1;              //单选按钮，1显示，0隐藏
        MedFeeCalGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        MedFeeCalGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        //MedFeeCalGrid.selBoxEventFuncName = "getMedFeeCalGrid"; //函数名称
        //MedFeeCalGrid.selBoxEventFuncParm ="divLLInqCourse";          //参数        
        MedFeeCalGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}




</script>

