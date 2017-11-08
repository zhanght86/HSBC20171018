<%
//程序名称：LLInqFeeQueryInit.jsp
//程序功能：调查费用信息查询页面初始化
//创建日期：2005-06-23
//创建人  ：yuejw
//更新记录：
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            
<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
    fm.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    fm.all('InqNo').value = nullToEmpty("<%= tInqNo %>");
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

function initInpBox()
{ 
    try
    {         
	   fm.ClmNo1.value = "";            
	   fm.InqNo1.value = "";   
	   fm.InqDept.value = "";   
	   fm.FeeType.value = "";   
	   fm.FeeTypeName.value = "";   
	   fm.FeeDate.value = "";   
	   fm.FeeSum.value = "";   
	   fm.Payee.value = "";   
	   fm.PayeeType.value = "";   
	   fm.PayeeTypeName.value = "";   
	   fm.Remark.value =""; 	                          
    }
    catch(ex)
    {
        alert("在LLInqFeeQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }      
}

                             
function initForm()
{
    try
    {
        initInpBox(); 
        initLLInqFeeGrid();
        initParam();
        LLInqFeeQuery();
     }
    catch(re)
    {
        alert("LLInqFeeQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
// 调查结论表的初始化
function initLLInqFeeGrid()
{                             
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                //列宽
        iArray[0][2]=10;                  //列最大值
        iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="赔案号";             //列名
        iArray[1][1]="80px";                //列宽
        iArray[1][2]=100;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="调查序号";             //列名
        iArray[2][1]="80px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[3]=new Array();
        iArray[3][0]="调查机构";             //列名
        iArray[3][1]="50px";                //列宽
        iArray[3][2]=100;                  //列最大值
        iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="费用类型";             //列名
        iArray[4][1]="80px";                //列宽
        iArray[4][2]=100;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="发生时间";             //列名
        iArray[5][1]="80px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=0; 

        iArray[6]=new Array();
        iArray[6][0]="费用金额";             //列名
        iArray[6][1]="80px";                //列宽
        iArray[6][2]=200;                  //列最大值
        iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="领款人";             //列名
        iArray[7][1]="100px";                //列宽
        iArray[7][2]=100;                  //列最大值
        iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[8]=new Array();
        iArray[8][0]="领款方式";             //列名
        iArray[8][1]="0px";                //列宽
        iArray[8][2]=100;                  //列最大值
        iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[9]=new Array();
        iArray[9][0]="备注";             //列名
        iArray[9][1]="0px";                //列宽
        iArray[9][2]=100;                  //列最大值
        iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许
                            
        LLInqFeeGrid = new MulLineEnter( "fm" , "LLInqFeeGrid" ); 
        //这些属性必须在loadMulLine前
        LLInqFeeGrid.mulLineCount = 0;   
        LLInqFeeGrid.displayTitle = 1;
        LLInqFeeGrid.locked = 1;
        LLInqFeeGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLInqFeeGrid.selBoxEventFuncName = "LLInqFeeGridClick"; //点击RadioBox时响应的函数名
        LLInqFeeGrid.hiddenPlus=1;
        LLInqFeeGrid.hiddenSubtraction=1;
        LLInqFeeGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
