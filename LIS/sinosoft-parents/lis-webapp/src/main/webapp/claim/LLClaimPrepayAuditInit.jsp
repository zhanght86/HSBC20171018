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
   document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
   
   document.all('RptNo').value =nullToEmpty("<%=tClmNo%>");
   document.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
   
   document.all('CustomerNo').value =nullToEmpty("<%=tCustomerNo%>");
   document.all('ActivityID').value =nullToEmpty("<%=tActivityID%>");
   document.all('MissionID').value =nullToEmpty("<%=tMissionID%>");
   document.all('SubMissionID').value =nullToEmpty("<%=tSubMissionID%>"); 


   document.all('AuditPopedom').value =nullToEmpty("<%=mAuditPopedom%>");
   document.all('tRgtClass').value =nullToEmpty("<%=mRgtClass%>");  
   document.all('tRgtObj').value =nullToEmpty("<%=mRgtObj%>"); 

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
    initLLPrepayDetailGridQuery();   //初始化查询“ 已保存的预付保项信息列表”
    initLLClaimUWMainQuery(); //初始化“ 审核意见”，根据赔案号查询LLClaimUWMain表
    
    initSubReportGrid(); //出险人信息
    initApplyPrepayNo(); //初始化,申请批次号
    
    initLLClaimPrepayNodeQuery(); //[查询预付节点信息]，为生成 审核节点 准备提供数据

    queryRegister();
  }
  catch(re)
  {
    alert("LLClaimPrepayAuditInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


/**
出险人信息初始化
*/
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
    iArray[1][0]="客户编码"; //原事故者客户号
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

//    iArray[3]=new Array();
//    iArray[3][0]="医院代码";
//    iArray[3][1]="0px";
//    iArray[3][2]=60;
//    iArray[3][3]=3;
//    iArray[3][4]="HospitalCode";
//    iArray[3][5]="3|4";                                //引用代码对应第几列，'|'为分割符
//    iArray[3][6]="0|1";                        //上面的列中放置引用代码中第几位值
//    iArray[3][9]="医院代码|NOTNULL";

//iArray[4]=new Array()
//iArray[4][0]="医院名称";
//iArray[4][1]="0px";
//iArray[4][2]=100;
//iArray[4][3]=3;

//iArray[5]=new Array()
//iArray[5][0]="入院日期";
//iArray[5][1]="0px";
//iArray[5][2]=100;
//iArray[5][3]=3;

    iArray[6]=new Array()
    iArray[6][0]="社保标志代码";
    iArray[6][1]="50px";
    iArray[6][2]=100;
    iArray[6][3]=3;
    
    iArray[7]=new Array()
    iArray[7][0]="社保标志";
    iArray[7][1]="80px";
    iArray[7][2]=100;
    iArray[7][3]=0; 

//    iArray[6]=new Array();
//    iArray[6][0]="出险细节";
//    iArray[6][1]="0px";
//    iArray[6][2]=100;
//    iArray[6][3]=3;
//
//    iArray[7]=new Array();
//    iArray[7][0]="治疗医院";
//    iArray[7][1]="0px";
//    iArray[7][2]=100;
//    iArray[7][3]=3;
//
//    iArray[8]=new Array();
//    iArray[8][0]="治疗情况";
//    iArray[8][1]="0px";
//    iArray[8][2]=100;
//    iArray[8][3]=3;
//
//    iArray[9]=new Array();
//    iArray[9][0]="死亡标识";
//    iArray[9][1]="0px";
//    iArray[9][2]=100;
//    iArray[9][3]=3;
//
//    iArray[10]=new Array();
//    iArray[10][0]="备注信息";
//    iArray[10][1]="0px";
//    iArray[10][2]=100;
//    iArray[10][3]=3;

    SubReportGrid = new MulLineEnter("fm","SubReportGrid");
    SubReportGrid.mulLineCount = 0;
    SubReportGrid.displayTitle = 1;
    SubReportGrid.locked = 0;
//    SubReportGrid.canChk =1;
    SubReportGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    SubReportGrid.selBoxEventFuncName ="SubReportGridClick"; //响应的函数名，不加扩号
    SubReportGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项  
    SubReportGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    SubReportGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    SubReportGrid.loadMulLine(iArray);
}
catch(ex)
{
alter(ex);
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
    iArray[1][1]="150px";                //列宽
    iArray[1][2]=150;                  //列最大值
    iArray[1][3]=0;   

    iArray[2]=new Array();
    iArray[2][0]="分案号";             //列名
    iArray[2][1]="80px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=3;   

    iArray[3]=new Array();
    iArray[3][0]="保单号";             //列名
    iArray[3][1]="120px";                //列宽
    iArray[3][2]=150;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="客户号码";             //列名
    iArray[4][1]="100px";                //列宽
    iArray[4][2]=100;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="责任编码";             //列名
    iArray[5][1]="80px";                //列宽
    iArray[5][2]=80;                  //列最大值
    iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="给付责任类型";             //列名
    iArray[6][1]="150px";                //列宽
    iArray[6][2]=150;                  //列最大值
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
      iArray[9][0]="币种";      	   		//列名
      iArray[9][1]="80px";            			//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[10]=new Array();
    iArray[10][0]="核赔赔付金额";             //列名
    iArray[10][1]="80px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[11]=new Array();
    iArray[11][0]="预付标志";             //列名
    iArray[11][1]="0px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[12]=new Array();
    iArray[12][0]="预付金额";             //列名
    iArray[12][1]="60px";                //列宽
    iArray[12][2]=200;                  //列最大值
    iArray[12][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[13]=new Array();
    iArray[13][0]="给付责任类型代码";             //列名
    iArray[13][1]="80px";                //列宽
    iArray[13][2]=120;                  //列最大值
    iArray[13][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    //iArray[13]=new Array();
    //iArray[13][0]="出险客户姓名";             //列名
    //iArray[13][1]="80px";                //列宽
    //iArray[13][2]=120;                  //列最大值
    //iArray[13][3]=0;                    //是否允许输入,1表示允许，0表示不允许

            
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
    iArray[1][1]="150px";                //列宽
    iArray[1][2]=150;                  //列最大值
    iArray[1][3]=0;   

    iArray[2]=new Array();
    iArray[2][0]="分案号";             //列名
    iArray[2][1]="90px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=3;   

    iArray[3]=new Array();
    iArray[3][0]="保单号";             //列名
    iArray[3][1]="120px";                //列宽
    iArray[3][2]=150;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许
   
    iArray[4]=new Array();
    iArray[4][0]="给付责任类型";             //列名
    iArray[4][1]="150px";                //列宽
    iArray[4][2]=150;                  //列最大值
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
      iArray[8][0]="币种";      	   		//列名
      iArray[8][1]="80px";            			//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="预付金额";             //列名
    iArray[9][1]="60px";                //列宽
    iArray[9][2]=100;                  //列最大值
    iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="预付日期";             //列名
    iArray[10][1]="80px";                //列宽
    iArray[10][2]=100;                  //列最大值
    iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
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
