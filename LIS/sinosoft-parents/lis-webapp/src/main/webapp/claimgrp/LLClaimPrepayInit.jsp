<%
//**************************************************************************************************
//Name：LLClaimPrepayInit.jsp
//Function：“预付管理”主界面初始化文件
//Author：yuejw
//Date: 2005-7-5 16:00
//更新记录：
//**************************************************************************************************
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

//接收参数
function initParam()
{
   fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
   
   fm.all('tRptNo').value =nullToEmpty("<%=tClmNo%>");
   fm.all('tCustomerNo').value =nullToEmpty("<%=tCustomerNo%>");
   fm.all('tActivityID').value =nullToEmpty("<%=tActivityID%>");
   fm.all('tMissionID').value =nullToEmpty("<%=tMissionID%>");
   fm.all('tSubMissionID').value =nullToEmpty("<%=tSubMissionID%>");  

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
    initLLClaimDetailGrid();
    initLLPrepayDetailGrid();   
    initLLClaimDetailGridQuery();   //初始化查询“ 给付保项信息列表”
    initApplyPrepayNo(); //初始化,申请批次号
    initLLClaimPrepayNodeQuery(); //[查询预付节点信息]，为生成 审核节点 准备提供数据
  }
  catch(re)
  {
    alert("LLClaimPrepayInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 赔付明细表信息列表的初始化
function initLLClaimDetailGrid()
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
    iArray[1][3]=0;   

    iArray[2]=new Array();
    iArray[2][0]="分案号";             //列名
    iArray[2][1]="80px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;   

    iArray[3]=new Array();
    iArray[3][0]="保单号";             //列名
    iArray[3][1]="120px";                //列宽
    iArray[3][2]=150;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="出险人客户号";             //列名
    iArray[4][1]="100px";                //列宽
    iArray[4][2]=100;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="责任编码";             //列名
    iArray[5][1]="60px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="给付责任类型";             //列名
    iArray[6][1]="80px";                //列宽
    iArray[6][2]=120;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="给付责任编码";             //列名
    iArray[7][1]="80px";                //列宽
    iArray[7][2]=120;                  //列最大值
    iArray[7][3]=0; 
    
    iArray[8]=new Array();
    iArray[8][0]="受理事故号";             //列名
    iArray[8][1]="0px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3;    //是否允许输入,1表示允许，0表示不允许    
    
    iArray[9]=new Array();
    iArray[9][0]="核赔赔付金额";             //列名
    iArray[9][1]="80px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="预付标志";             //列名
    iArray[10][1]="0px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[11]=new Array();
    iArray[11][0]="预付金额";             //列名
    iArray[11][1]="60px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许

            
    LLClaimDetailGrid = new MulLineEnter( "fm" , "LLClaimDetailGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimDetailGrid.mulLineCount = 2;   
    LLClaimDetailGrid.displayTitle = 1;
    LLClaimDetailGrid.locked = 0;
    LLClaimDetailGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimDetailGrid.selBoxEventFuncName ="LLClaimDetailGridClick"; //响应的函数名，不加扩号   
    LLClaimDetailGrid.hiddenPlus=1;
    LLClaimDetailGrid.hiddenSubtraction=1;
    LLClaimDetailGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 预付明细记录列表的初始化
function initLLPrepayDetailGrid()
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
    iArray[1][1]="90px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;   

    iArray[2]=new Array();
    iArray[2][0]="分案号";             //列名
    iArray[2][1]="90px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;   

    iArray[3]=new Array();
    iArray[3][0]="保单号";             //列名
    iArray[3][1]="120px";                //列宽
    iArray[3][2]=150;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许
   
    iArray[4]=new Array();
    iArray[4][0]="给付责任类型";             //列名
    iArray[4][1]="80px";                //列宽
    iArray[4][2]=120;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="给付责任编码";             //列名
    iArray[5][1]="80px";                //列宽
    iArray[5][2]=120;                  //列最大值
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="预付批次号";             //列名
    iArray[6][1]="0px";                //列宽
    iArray[6][2]=100;                  //列最大值
    iArray[6][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="预付序号";             //列名
    iArray[7][1]="80px";                //列宽
    iArray[7][2]=100;                  //列最大值
    iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="预付金额";             //列名
    iArray[8][1]="60px";                //列宽
    iArray[8][2]=100;                  //列最大值
    iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="预付日期";             //列名
    iArray[9][1]="80px";                //列宽
    iArray[9][2]=100;                  //列最大值
    iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    LLPrepayDetailGrid = new MulLineEnter( "fm" , "LLPrepayDetailGrid" ); 
    //这些属性必须在loadMulLine前
    LLPrepayDetailGrid.mulLineCount = 0;   
    LLPrepayDetailGrid.displayTitle = 1;
    LLPrepayDetailGrid.locked = 0;
    LLPrepayDetailGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
    LLPrepayDetailGrid.hiddenPlus=1;
    LLPrepayDetailGrid.hiddenSubtraction=1;
    LLPrepayDetailGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
