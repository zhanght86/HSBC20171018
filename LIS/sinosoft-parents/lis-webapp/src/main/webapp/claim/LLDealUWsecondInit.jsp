
<%
	//文件名：LLDealUWsecondInit.jsp
	//功能：二核结论
	//日期：2004-12-23 16:49:22
	//建立人：zhangxing  更改：yuejw
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//添加页面控件的初始化。
%>

<script language="JavaScript">

function initForm()
{
  try
  { 
 	initParam();
 	
  	initLLUnfinishedContGrid();	// 未二次核保完成的合同单列表的初始化   	
    initLLContGrid();	// 已经二次核保完成的合同单列表的初始化  
    initLLPolGrid();
   
    initQueryLLContGrid();  
	initQueryLLUnfinishedContGrid(); 
	initLWMission();  
    
    if(fm.Flag.value == "N")	//“生效”和“不生效”和“发起二核”按钮只可“二核结论处理”中可用，其他的查询情况不显示
    {
    	fm.CvalidateButton.disabled = true;
    	fm.NotCvalidateButton.disabled = true;
    	fm.UWConclusionCancelButton.disabled = true;
   		//fm.cancelbutton1.disabled = true;
   		fm.cancelbutton2.disabled = true;
   		fm.cancelbutton3.disabled = true; 
    }
  }
  catch(ex)
  {
    alter("在LLDealUWsecondInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//接收审核页面传递过来的参数
function initParam()
{
    document.all('CaseNo').value    = nullToEmpty("<%= tCaseNo %>");
    document.all('InsuredNo').value = nullToEmpty("<%= tInsuredNo %>");
    document.all('Flag').value      = nullToEmpty("<%=tFlag%>");
    document.all('Operator').value = nullToEmpty("<%= tG.Operator %>");       
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

// 被保险人下未二次核保完成的合同单列表的初始化 
function initLLUnfinishedContGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";              //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";              //列宽
      iArray[0][2]=10;                  //列最大值
      iArray[0][3]=0;                   //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="赔案号";             //列名
      iArray[1][1]="130px";              //列宽
      iArray[1][2]=130;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批次号";             //列名
      iArray[2][1]="80px";              //列宽
      iArray[2][2]=80;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="合同号码";           //列名
      iArray[3][1]="120px";              //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许  

      iArray[4]=new Array();
      iArray[4][0]="投保人号码";         //列名
      iArray[4][1]="80px";               //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="投保人姓名";         //列名
      iArray[5][1]="80px";               //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="核保状态";           //列名
      iArray[6][1]="60px";               //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许
     
      iArray[7]=new Array();
      iArray[7][0]="相关标志";           //列名
      iArray[7][1]="60px";               //列宽
      iArray[7][2]=200;                  //列最大值
      iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
     
      iArray[8]=new Array();
      iArray[8][0]="投保书健康告知栏询问号"; //列名
      iArray[8][1]="0px";                //列宽
      iArray[8][2]=200;                  //列最大值
      iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
      iArray[9]=new Array();
      iArray[9][0]="体检健康告知栏询问号"; //列名
      iArray[9][1]="0px";                //列宽
      iArray[9][2]=200;                  //列最大值
      iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="对应未告知情况";     //列名
      iArray[10][1]="0px";                //列宽
      iArray[10][2]=200;                  //列最大值
      iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许
     
      iArray[11]=new Array();
      iArray[11][0]="出险人目前健康状况介绍";  //列名
      iArray[11][1]="0px";                //列宽
      iArray[11][2]=200;                  //列最大值
      iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许
     
      iArray[12]=new Array();
      iArray[12][0]="核保结论";           //列名
      iArray[12][1]="0px";                //列宽
      iArray[12][2]=200;                  //列最大值
      iArray[12][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
      iArray[13]=new Array();
      iArray[13][0]="核保意见";           //列名
      iArray[13][1]="0px";                //列宽
      iArray[13][2]=200;                  //列最大值
      iArray[13][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[14]=new Array();
      iArray[14][0]="核保人";             //列名
      iArray[14][1]="80px";                //列宽
      iArray[14][2]=200;                  //列最大值
      iArray[14][3]=0;                    //是否允许输入,1表示允许，0表示不允许
     
      iArray[15]=new Array();
      iArray[15][0]="提起二核日期";       //列名
      iArray[15][1]="80px";               //列宽
      iArray[15][2]=200;                  //列最大值
      iArray[15][3]=0;                    //是否允许输入,1表示允许，0表示不允许    

      LLUnfinishedContGrid = new MulLineEnter( "document" , "LLUnfinishedContGrid" ); 
      //这些属性必须在loadMulLine前
      LLUnfinishedContGrid.mulLineCount      = 5;   
      LLUnfinishedContGrid.displayTitle      = 1;
      LLUnfinishedContGrid.locked            = 1;
      LLUnfinishedContGrid.canSel            = 1; // 1 显示 ；0 隐藏（缺省值）
      LLUnfinishedContGrid.hiddenPlus        = 1;
      LLUnfinishedContGrid.hiddenSubtraction = 1;
      LLUnfinishedContGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}


// 被保险人已经二次核保完成的合同单列表的初始化 
function initLLContGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";               //列宽
      iArray[0][2]=10;                   //列最大值
      iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="赔案号";             //列名
      iArray[1][1]="130px";              //列宽
      iArray[1][2]=130;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批次号";             //列名
      iArray[2][1]="80px";              //列宽
      iArray[2][2]=80;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="合同号码";           //列名
      iArray[3][1]="120px";              //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许  

      iArray[4]=new Array();
      iArray[4][0]="投保人号码";         //列名
      iArray[4][1]="80px";               //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="投保人姓名";         //列名
      iArray[5][1]="80px";               //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="核保状态";           //列名
      iArray[6][1]="60px";               //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="相关标志";           //列名
      iArray[7][1]="60px";               //列宽
      iArray[7][2]=200;                  //列最大值
      iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="投保书健康告知栏询问号";             //列名
      iArray[8][1]="0px";                //列宽
      iArray[8][2]=200;                  //列最大值
      iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="体检健康告知栏询问号"; //列名
      iArray[9][1]="0px";                //列宽
      iArray[9][2]=200;                  //列最大值
      iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="对应未告知情况";     //列名
      iArray[10][1]="0px";                //列宽
      iArray[10][2]=200;                  //列最大值
      iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="出险人目前健康状况介绍";             //列名
      iArray[11][1]="0px";                //列宽
      iArray[11][2]=200;                  //列最大值
      iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="核保结论";           //列名
      iArray[12][1]="0px";                //列宽
      iArray[12][2]=200;                  //列最大值
      iArray[12][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[13]=new Array();
      iArray[13][0]="核保意见";           //列名
      iArray[13][1]="0px";                //列宽
      iArray[13][2]=200;                  //列最大值
      iArray[13][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[14]=new Array();
      iArray[14][0]="核保人";             //列名
      iArray[14][1]="80px";               //列宽
      iArray[14][2]=200;                  //列最大值
      iArray[14][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[15]=new Array();
      iArray[15][0]="提起二核日期";       //列名
      iArray[15][1]="80px";               //列宽
      iArray[15][2]=200;                  //列最大值
      iArray[15][3]=0;                    //是否允许输入,1表示允许，0表示不允许    
      
      iArray[16]=new Array();
      iArray[16][0]="有效标志";           //列名
      iArray[16][1]="80px";               //列宽
      iArray[16][2]=200;                  //列最大值
      iArray[16][3]=0;                    //是否允许输入,1表示允许，0表示不允许    

      iArray[17]=new Array();
      iArray[17][0]="被保人客户号码";           //列名
      iArray[17][1]="80px";               //列宽
      iArray[17][2]=200;                  //列最大值
      iArray[17][3]=0;                    //是否允许输入,1表示允许，0表示不允许    
      
      iArray[18]=new Array();
      iArray[18][0]="被保人名称";           //列名
      iArray[18][1]="80px";               //列宽
      iArray[18][2]=200;                  //列最大值
      iArray[18][3]=0;                    //是否允许输入,1表示允许，0表示不允许    
      
      LLContGrid = new MulLineEnter( "document" , "LLContGrid" ); 
      //这些属性必须在loadMulLine前
      LLContGrid.mulLineCount = 5;   
      LLContGrid.displayTitle = 1;
      LLContGrid.locked = 1;
      LLContGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLContGrid.selBoxEventFuncName = "LLContGridClick"; //点击RadioBox时响应的函数名
      LLContGrid.hiddenPlus=1;
      LLContGrid.hiddenSubtraction=1;
      LLContGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}


//调查申请表初始化
function initLLPolGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";               //列宽
      iArray[0][2]=10;                   //列最大值
      iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="赔案号";             //列名
      iArray[1][1]="100px";              //列宽
      iArray[1][2]=100;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="合同号";             //列名
      iArray[2][1]="100px";              //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="总单投保单号码";     //列名
      iArray[3][1]="0px";                //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保单险种号码";       //列名
      iArray[4][1]="120px";              //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="险种编码";           //列名
      iArray[5][1]="80px";               //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="险种名称";           //列名
      iArray[6][1]="150px";              //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="核保顺序号";         //列名
      iArray[7][1]="0px";                //列宽
      iArray[7][2]=100;                  //列最大值
      iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="核保结论";           //列名
      iArray[8][1]="0px";                //列宽
      iArray[8][2]=100;                  //列最大值
      iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
       
      iArray[9]=new Array();
      iArray[9][0]="核保结论";           //列名
      iArray[9][1]="80px";               //列宽
      iArray[9][2]=100;                  //列最大值
      iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许
     
      iArray[10]=new Array();
      iArray[10][0]="核保意见";           //列名
      iArray[10][1]="0px";                //列宽
      iArray[10][2]=100;                  //列最大值
      iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      LLPolGrid = new MulLineEnter( "document" , "LLPolGrid" ); 
      //这些属性必须在loadMulLine前
      LLPolGrid.mulLineCount = 5;   
      LLPolGrid.displayTitle = 1;
      LLPolGrid.locked = 1;
      LLPolGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLPolGrid.selBoxEventFuncName = "LLPolGridClick"; //点击RadioBox时响应的函数名
      LLPolGrid.hiddenPlus=1;
      LLPolGrid.hiddenSubtraction=1;
      LLPolGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
