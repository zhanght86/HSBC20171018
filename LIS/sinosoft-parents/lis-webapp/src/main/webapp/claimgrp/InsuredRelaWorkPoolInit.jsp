<%
//程序名称：GrpFillListInput.jsp
//程序功能：团体无名单补名单查询初始化
//创建日期：2006-04-08 16:30:57
//创建人  ：Chenrong
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// 输入框的初始化（查询条件）
function initInpBox()
{ 
    fm.PrtNo.value = "";
    fm.GrpContNo.value = "";
    //fm.ContNo.value = "";
}

//隐藏控件的初始化
function initHiddenBox()
{
    fm.ManageCom.value = nullToEmpty("<%=tGI.ManageCom%>"); 
    fm.Operator.value = nullToEmpty("<%=tGI.Operator%>"); 
}

function nullToEmpty(string)
{
    if ((string == null) || (string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

function initForm()
{
    try 
    {
        initInpBox();
        initHiddenBox();
        initPolGrid();
    }
    catch(re) 
    {
        alert("ProposalApproveModifyInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}


// 保单信息列表的初始化
function initPolGrid() 
{                               
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="投保单号";         		//列名
        iArray[1][1]="100px";            		//列宽
        iArray[1][2]=100;            			//列最大值
        iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[2]=new Array();
        iArray[2][0]="团体合同号";         		//列名
        iArray[2][1]="100px";            		//列宽
        iArray[2][2]=100;            			//列最大值
        iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[3]=new Array();
        iArray[3][0]="团体客户号";         		//列名
        iArray[3][1]="100px";            		//列宽
        iArray[3][2]=100;            			//列最大值
        iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[4]=new Array();
        iArray[4][0]="投保单位名称";         		//列名
        iArray[4][1]="100px";            		//列宽
        iArray[4][2]=200;            			//列最大值
        iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[5]=new Array();
        iArray[5][0]="保费";         		//列名
        iArray[5][1]="70px";            		//列宽
        iArray[5][2]=100;            			//列最大值
        iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[6]=new Array();
        iArray[6][0]="保额";         		//列名
        iArray[6][1]="70px";            		//列宽
        iArray[6][2]=100;            			//列最大值
        iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
        
        PolGrid = new MulLineEnter("document","PolGrid"); 
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 0;   
        PolGrid.displayTitle = 1;
        PolGrid.locked = 1;
        PolGrid.canSel = 1;
        PolGrid.hiddenPlus = 1;
        PolGrid.hiddenSubtraction = 1;
        PolGrid.selBoxEventFuncName ="addNameClick" ;     //点击RadioBox时响应的JS函数
        PolGrid.loadMulLine(iArray);  
        
        //这些操作必须在loadMulLine后面
        //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert("ProposalApproveModifyInit.jsp-->initPolGrid函数中发生异常:初始化界面错误!");
    }
}

</script>
