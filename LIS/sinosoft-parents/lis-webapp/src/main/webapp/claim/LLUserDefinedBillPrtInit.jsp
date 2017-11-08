<%
//文件名称: LLUserDefinedBillPrtInit.jsp
//文件功能：用户自定义打印
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

//接收页面传递过来的参数
function initParam()
{
	document.all('MngCom').value = nullToEmpty("<%= tG.ManageCom %>");	 
	document.all('ComCode').value = nullToEmpty("<%= tG.ComCode %>");	  
//	document.all('Operator').value = nullToEmpty("<%= tG.Operator %>");	 
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('CustNo').value	= nullToEmpty("<%= tCustNo %>");
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
		initAffixGrid();
    }
    catch(re)
    {
        alert("LLUserDefinedBillPrtInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//文件数据表格
function initAffixGrid()
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
        iArray[1][0]="单证代码";
        iArray[1][1]="50px";
        iArray[1][2]=10;
        iArray[1][3]=0;
     
        iArray[2]=new Array();
        iArray[2][0]="单证名称";
        iArray[2][1]="200px";
        iArray[2][2]=10;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="是否提交";
        iArray[3][1]="40px";
        iArray[3][2]=10;
        iArray[3][3]=2;   
        iArray[3][10]="SubFlag";
        iArray[3][11]="0|^0|是^1|否";      
        iArray[3][14]="0";
                
        iArray[4]=new Array();
        iArray[4][0]="页数";
        iArray[4][1]="40px";
        iArray[4][2]=10;
        iArray[4][3]=1;  
        iArray[4][14]="1";  
                        
        iArray[5]=new Array();
        iArray[5][0]="提交形式";
        iArray[5][1]="40px";
        iArray[5][2]=10;
        iArray[5][3]=2;   
        iArray[5][10]="Property";
        iArray[5][11]="0|^0|原件^1|复印件";      
        iArray[5][14]="0";
        
        iArray[6]=new Array();
        iArray[6][0]="是否退还原件";
        iArray[6][1]="60px";
        iArray[6][2]=10;
        iArray[6][3]=2;   
        iArray[6][10]="ReturnFlag";
        iArray[6][11]="0|^0|是^1|否";      
        iArray[6][14]="0";
        
        AffixGrid = new MulLineEnter("fm","AffixGrid");
        AffixGrid.mulLineCount = 0;
        AffixGrid.displayTitle = 1;
        AffixGrid.locked = 0;
        AffixGrid.canChk =0;              //多选按钮，1显示，0隐藏
        AffixGrid.canSel =0;              //单选按钮，1显示，0隐藏
        AffixGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        AffixGrid.hiddenSubtraction=0;    //－号：1隐藏，0显示
        AffixGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
