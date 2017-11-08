<%
//**************************************************************************************************
//Name：LLClaimPrepayMissInit.jsp
//Function：待审核工作队列工作队列信息（准备用于“预付”处理）
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
    initLLClaimAuditGrid();
    initLLClaimPrepayGrid()
    LLClaimPrepayGridQuery();
    //LLClaimAuditGridQuery();
  }
  catch(re)
  {
    alert("LLClaimAuditMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 报案信息列表的初始化
function initLLClaimAuditGrid()
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
    iArray[1][0]="立案号";             //列名
    iArray[1][1]="150px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="立案状态";             //列名
    iArray[2][1]="100px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="出险人编码";             //列名
    iArray[3][1]="100px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="出险人姓名";             //列名
    iArray[4][1]="120px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="出险人性别";             //列名
    iArray[5][1]="80px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="出险日期";             //列名
    iArray[6][1]="80px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="申请类型";             //列名
    iArray[7][1]="0px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=3; 
    
    iArray[8]=new Array();
    iArray[8][0]="号码类型";             //列名
    iArray[8][1]="0px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3; 
    
    iArray[9]=new Array();
    iArray[9][0]="其他号码";             //列名
    iArray[9][1]="0px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=3; 
    
    iArray[10]=new Array();
    iArray[10][0]="核赔师权限";             //列名
    iArray[10][1]="0px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=3;     

    iArray[11]=new Array();
    iArray[11][0]="预付标志";             //列名
    iArray[11][1]="0px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=3;     
    
    iArray[12]=new Array();
    iArray[12][0]="来自";             //列名
    iArray[12][1]="0px";                //列宽
    iArray[12][2]=200;                  //列最大值
    iArray[12][3]=3;     
    
    iArray[13]=new Array();
    iArray[13][0]="审核操作人";             //列名
    iArray[13][1]="0px";                //列宽
    iArray[13][2]=200;                  //列最大值
    iArray[13][3]=3;     

    iArray[14]=new Array();
    iArray[14][0]="机构";             //列名
    iArray[14][1]="0px";                //列宽
    iArray[14][2]=200;                  //列最大值
    iArray[14][3]=3;   

    iArray[15]=new Array();
    iArray[15][0]="Missionid";             //列名
    iArray[15][1]="0px";                //列宽
    iArray[15][2]=200;                  //列最大值
    iArray[15][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[16]=new Array();
    iArray[16][0]="submissionid";             //列名
    iArray[16][1]="0px";                //列宽
    iArray[16][2]=200;                  //列最大值
    iArray[16][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[17]=new Array();
    iArray[17][0]="activityid";             //列名
    iArray[17][1]="0px";                //列宽
    iArray[17][2]=200;                  //列最大值
    iArray[17][3]=3;                    //是否允许输入,1表示允许，0表示不允许            
  
    iArray[18]=new Array();
    iArray[18][0]="ComFlag";             //列名---------权限跨级标志
    iArray[18][1]="0px";                //列宽
    iArray[18][2]=200;                  //列最大值
    iArray[18][3]=3;                    //是否允许输入,1表示允许，0表示不允许         
        
    LLClaimAuditGrid = new MulLineEnter( "fm" , "LLClaimAuditGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimAuditGrid.mulLineCount = 0;   
    LLClaimAuditGrid.displayTitle = 1;
    LLClaimAuditGrid.locked = 0;
    LLClaimAuditGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimAuditGrid.selBoxEventFuncName ="LLClaimAuditGridClick"; //响应的函数名，不加扩号   
    LLClaimAuditGrid.hiddenPlus=1;
    LLClaimAuditGrid.hiddenSubtraction=1;
    LLClaimAuditGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 报案信息列表的初始化
function initLLClaimPrepayGrid()
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
    iArray[1][0]="立案号";             //列名
    iArray[1][1]="150px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="立案状态";             //列名
    iArray[2][1]="100px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="出险人编码";             //列名
    iArray[3][1]="100px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="出险人姓名";             //列名
    iArray[4][1]="120px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="出险人性别";             //列名
    iArray[5][1]="80px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="出险日期";             //列名
    iArray[6][1]="80px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //列名
    iArray[7][1]="80px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //列名
    iArray[8][1]="100px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //列名
    iArray[9][1]="100px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许                 
    
    LLClaimPrepayGrid = new MulLineEnter( "fm" , "LLClaimPrepayGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimPrepayGrid.mulLineCount =0;   
    LLClaimPrepayGrid.displayTitle = 1;
    LLClaimPrepayGrid.locked = 0;
    LLClaimPrepayGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimPrepayGrid.selBoxEventFuncName ="LLClaimPrepayGridClick"; //响应的函数名，不加扩号
    LLClaimPrepayGrid.hiddenPlus=1;
    LLClaimPrepayGrid.hiddenSubtraction=1;
    LLClaimPrepayGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
