<%
//程序名称：ProposalApproveInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    //document.all('ProposalNo').value = '';
    //document.all('ManageCom').value = '';
    //document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
  }
  catch(ex)
  {
    alert("在ProposalApproveInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在ProposalApproveInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
	initInpBox();  
	initSelBox();       
	initPolGrid();
	initSelfPolGrid();	
	easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ProposalApproveInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0; 
      iArray[2][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[2][5]="3";              	                //引用代码对应第几列，'|'为分割符
      iArray[2][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[2][18]=250;
      iArray[2][19]= 0 ;

      iArray[3]=new Array();
      iArray[3][0]="管理机构";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="产生原因";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=80;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][4]="BPODealType";              	        //是否引用代码:null||""为不引用

      iArray[5]=new Array();
      iArray[5][0]="扫描日期";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[9]=new Array();
      iArray[9][0]="扫描时间";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="业务号码类型";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 个人保单信息列表的初始化
function initSelfPolGrid()
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
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0; 
      iArray[2][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[2][5]="3";              	                //引用代码对应第几列，'|'为分割符
      iArray[2][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[2][18]=250;
      iArray[2][19]= 0 ;

      iArray[3]=new Array();
      iArray[3][0]="管理机构";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="产生原因";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=80;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][4]="BPODealType";              	        //是否引用代码:null||""为不引用
      
      iArray[5]=new Array();
      iArray[5][0]="扫描日期";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[9]=new Array();
      iArray[9][0]="扫描时间";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="业务号码类型";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      SelfPolGrid = new MulLineEnter( "fm" , "SelfPolGrid" ); 
      //这些属性必须在loadMulLine前
      SelfPolGrid.mulLineCount = 5;   
      SelfPolGrid.displayTitle = 1;
      SelfPolGrid.locked = 1;
      SelfPolGrid.canSel = 1;
      SelfPolGrid.hiddenPlus = 1;
      SelfPolGrid.hiddenSubtraction = 1;
      SelfPolGrid.selBoxEventFuncName = "InitshowApproveDetail";    
      SelfPolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
