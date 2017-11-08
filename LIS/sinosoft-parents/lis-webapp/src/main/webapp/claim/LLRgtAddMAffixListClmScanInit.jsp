<%
//Name:LLRgtAddMAffixListInit.jsp
//Function：单证补充界面初始化程序
//Date：2005-07-1 17:44:28
//Author ：yuejw
%>
<!--用户校验类-->
<%
//
//  String CaseNo= request.getParameter("tClmNo");
//  String whoNo= request.getParameter("tCustomerNo");
//  String Proc=request.getParameter("tProc");
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
var turnPage = new turnPageClass();
//接受上页传入的数据 
function initParam()
{
	fm.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
//	alert("fm.all('ClmNo').value"+fm.all('ClmNo').value);
    fm.all('Proc').value =nullToEmpty("<%=tProc%>");
//    alert("fm.all('Proc').value"+fm.all('Proc').value);
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
    	initParam()
    	initInpBox();
    	initAffixGrid();
	    initQueryAffixGrid();    	
    }
    catch(re)
    {
        alert("LLRgtAddMAffixListInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}


function initInpBox()
{
    try
    {    
    	//判断是否需要生成单证----查询如果有单证，则无需生成，否则则需生成单证
         var strSql="select * from llaffix where rgtno='"+fm.ClmNo.value+"' ";
//         alert(strSql);
         var arr=easyExecSql(strSql);
        if(arr==null)
        {
            fm.build.disabled=false;
        }
	    else
    	{
    		fm.build.disabled=true;
    	}	
    }
    catch(re)
    {
        alert("LLMAffixListInit.jsp-->initInpBox函数中发生异常:初始化界面错误!");
    }
}

//所需单证信息
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
        iArray[1][1]="30px";
        iArray[1][2]=10;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="单证名称";
        iArray[2][1]="200px";
        iArray[2][2]=300;
        iArray[2][3]=0;
      
        iArray[3]=new Array();
        iArray[3][0]="是否已提交";
        iArray[3][1]="0px";
        iArray[3][2]=10;
        iArray[3][3]=3;        
//        iArray[3][10]="SubFlag";
//        iArray[3][11]="0|^0|是^1|否"; 
                
        iArray[4]=new Array();
        iArray[4][0]="是否必需";
        iArray[4][1]="30px";
        iArray[4][2]=10;
        iArray[4][3]=2;   
        iArray[4][10]="NeedFlag";
        iArray[4][11]="0|^0|是^1|否"; 
        
        iArray[5]=new Array();
        iArray[5][0]="单证件数";
        iArray[5][1]="30px";
        iArray[5][2]=10;
        iArray[5][3]=1;    
                
        iArray[6]=new Array();
        iArray[6][0]="提交形式";
        iArray[6][1]="30px";
        iArray[6][2]=10;
        iArray[6][3]=2;  
        iArray[6][10]="Property";        
        iArray[6][11]="0|^0|原件^1|复印件"; 
        
        iArray[7]=new Array();
        iArray[7][0]="缺少件数";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
                
        iArray[8]=new Array();
        iArray[8][0]="是否退还原件";
        iArray[8][1]="0px";
        iArray[8][2]=50;
        iArray[8][3]=3; 
//        iArray[8][10]="ReturnFlag";
//        iArray[8][11]="0|^0|是^1|否"; 

        iArray[9]=new Array();
        iArray[9][0]="附件号码";
        iArray[9][1]="0px";
        iArray[9][2]=10;
        iArray[9][3]=3; 

        AffixGrid = new MulLineEnter("fm","AffixGrid");
        AffixGrid.mulLineCount = 0;
        AffixGrid.displayTitle = 1;
        AffixGrid.locked = 0;
        AffixGrid.canChk =1;              //多选按钮，1显示，0隐藏
        AffixGrid.canSel =0;              //单选按钮，1显示，0隐藏
        AffixGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        AffixGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        AffixGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
