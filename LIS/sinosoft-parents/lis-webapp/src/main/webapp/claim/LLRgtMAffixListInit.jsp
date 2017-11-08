<%
//Name:LLRgtMAffixListInit.jsp
//Function：单证回销界面初始化程序
//Date：2005-07-1 17:44:28
//Author ：yuejw
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
    document.all('CustomerNo').value =nullToEmpty("<%=tCustomerNo%>");
    document.all('Proc').value =nullToEmpty("<%=tProc%>");
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
    	initAffixGrid();
    	initRgtAffixGrid();
	    initAffixGridQuery();   
	    initRgtAffixGridQuery();	
    }
    catch(re)
    {
        alert("LLRgtMAffixListInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}


function initInpBox()
{
    try
    {
    }
    catch(re)
    {
        alert("LLRgtMAffixListInit.jsp-->initInpBox函数中发生异常:初始化界面错误!");
    }
}

//[回销单证]所需单证信息
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
        iArray[1][0]="附件号码";
        iArray[1][1]="0px";
        iArray[1][2]=10;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="单证代码";
        iArray[2][1]="40px";
        iArray[2][2]=10;
        iArray[2][3]=0;
      
        iArray[3]=new Array();
        iArray[3][0]="单证名称";
        iArray[3][1]="100px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="是否已提交";
        iArray[4][1]="0px";
        iArray[4][2]=10;
        iArray[4][3]=3;  

        iArray[5]=new Array();
        iArray[5][0]="是否必需";
        iArray[5][1]="40px";
        iArray[5][2]=10;
        iArray[5][3]=0;    
                
        iArray[6]=new Array();
        iArray[6][0]="单证件数";
        iArray[6][1]="40px";
        iArray[6][2]=10;
        iArray[6][3]=1;  

        iArray[7]=new Array();
        iArray[7][0]="缺少件数";
        iArray[7][1]="40px";
        iArray[7][2]=10;
        iArray[7][3]=1;  
                
        iArray[8]=new Array();
        iArray[8][0]="提交形式";
        iArray[8][1]="40px";
        iArray[8][2]=10;
        iArray[8][3]=2; 
        iArray[8][10]="Property";
        iArray[8][11]="0|^0|原件^1|复印件";      

        iArray[9]=new Array();
        iArray[9][0]="是否退还原件";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=2; 
        iArray[9][10]="returnflag";
        iArray[9][11]="0|^0|是^1|否";     
        iArray[9][14]="0";     
       
        iArray[10]=new Array();
        iArray[10][0]="单证检查结论";
        iArray[10][1]="50px";
        iArray[10][2]=10;
        iArray[10][3]=2;         
        iArray[10][10]="affixconclusion";        
        iArray[10][11]="0|^0|齐全^1|不齐全";  
        iArray[10][14]="0"; 
        
        iArray[11]=new Array();
        iArray[11][0]="不齐全原因";
        iArray[11][1]="100px";
        iArray[11][2]=10;
        iArray[11][3]=1;      

        iArray[12]=new Array();
        iArray[12][0]="发起阶段";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;                           
                                  
        AffixGrid = new MulLineEnter("document","AffixGrid");
        AffixGrid.mulLineCount = 5;
        AffixGrid.displayTitle = 1;
        AffixGrid.locked = 0;
        AffixGrid.canChk =1;              //多选按钮，1显示，0隐藏
        AffixGrid.canSel =0;              //单选按钮，1显示，0隐藏
        AffixGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        AffixGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        //AffixGrid.chkBoxEventFuncName ="ChkBoxClick"; //多选按钮响应的JS函数名，不加扩号
        //AffixGrid.chkBoxAllEventFuncName="ChkBoxAllClick"; //针对标题栏具有全选功能的checkBox的JS函数名,不加扩号
        AffixGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter(ex);
    }
}


//[已经回销单证]所需单证信息
function initRgtAffixGrid()
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
        iArray[1][0]="附件号码";
        iArray[1][1]="0px";
        iArray[1][2]=10;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="单证代码";
        iArray[2][1]="40px";
        iArray[2][2]=10;
        iArray[2][3]=0;
      
        iArray[3]=new Array();
        iArray[3][0]="单证名称";
        iArray[3][1]="100px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="是否已提交";
        iArray[4][1]="0px";
        iArray[4][2]=10;
        iArray[4][3]=3;  

        iArray[5]=new Array();
        iArray[5][0]="是否必需";
        iArray[5][1]="40px";
        iArray[5][2]=10;
        iArray[5][3]=0;    
                
        iArray[6]=new Array();
        iArray[6][0]="单证件数";
        iArray[6][1]="40px";
        iArray[6][2]=10;
        iArray[6][3]=0;  

        iArray[7]=new Array();
        iArray[7][0]="缺少件数";
        iArray[7][1]="40px";
        iArray[7][2]=10;
        iArray[7][3]=0;  
                
        iArray[8]=new Array();
        iArray[8][0]="提交形式";
        iArray[8][1]="40px";
        iArray[8][2]=10;
        iArray[8][3]=0;   

        iArray[9]=new Array();
        iArray[9][0]="是否退还原件";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=0; 
       
        iArray[10]=new Array();
        iArray[10][0]="单证检查结论";
        iArray[10][1]="50px";
        iArray[10][2]=10;
        iArray[10][3]=0;         
        
        iArray[11]=new Array();
        iArray[11][0]="不齐全原因";
        iArray[11][1]="100px";
        iArray[11][2]=10;
        iArray[11][3]=0;                             
                                  
        RgtAffixGrid = new MulLineEnter("document","RgtAffixGrid");
        RgtAffixGrid.mulLineCount = 5;
        RgtAffixGrid.displayTitle = 1;
        RgtAffixGrid.locked = 0;
        RgtAffixGrid.canChk =0;              //多选按钮，1显示，0隐藏
        RgtAffixGrid.canSel =0;              //单选按钮，1显示，0隐藏
        RgtAffixGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        RgtAffixGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        RgtAffixGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter(ex);
    }
}
</script>
