<%
//文件名称: LLClaimCertiPrtControlInit.jsp
//文件功能：理赔单证打印控制
//创建日期：2005-10-15  创建人 ：                   
//更新记录：
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
var turnPage = new turnPageClass();
//接受上页传入的数据 
function initParam()
{
	document.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
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
 	initClaimCertiGrid();
 	initParam();
 	showCertiPrtControl();
    }
    catch(re)
    {
        alert("LLClaimCertiPrtControlInit.jsp->InitForm函数中发生异常:初始化界面错误!");
    }
}


//打印管理表中该赔案下可以打印的单证信息信息
function initClaimCertiGrid()
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
        iArray[1][0]="印刷流水号";
        iArray[1][1]="100px";
        iArray[1][2]=10;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="类型代码";
        iArray[2][1]="60px";
        iArray[2][2]=300;
        iArray[2][3]=0;
      
        iArray[3]=new Array();
        iArray[3][0]="单据类型名称";
        iArray[3][1]="250px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
                
        iArray[4]=new Array();
        iArray[4][0]="管理机构";
        iArray[4][1]="0px";
        iArray[4][2]=10;
        iArray[4][3]=3;   
        
        iArray[5]=new Array();
        iArray[5][0]="发起机构";
        iArray[5][1]="0px";
        iArray[5][2]=10;
        iArray[5][3]=3;    
                
        iArray[6]=new Array();
        iArray[6][0]="发起人";
        iArray[6][1]="0px";
        iArray[6][2]=10;
        iArray[6][3]=3;  
        
        iArray[7]=new Array();
        iArray[7][0]="打印方式";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
                
        iArray[8]=new Array();
        iArray[8][0]="打印状态";
        iArray[8][1]="60px";
        iArray[8][2]=50;
        iArray[8][3]=0; 

        ClaimCertiGrid = new MulLineEnter("fm","ClaimCertiGrid");
        ClaimCertiGrid.mulLineCount = 0;
        ClaimCertiGrid.displayTitle = 1;
        ClaimCertiGrid.locked = 0;
        ClaimCertiGrid.canChk =1;              //多选按钮，1显示，0隐藏
        ClaimCertiGrid.canSel =0;              //单选按钮，1显示，0隐藏
        ClaimCertiGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        ClaimCertiGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        ClaimCertiGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script> 
