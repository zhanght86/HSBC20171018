<%
//程序名称：PayPremQueryInit.jsp
//程序功能：
//创建日期：
//创建人  ：Howie 
//更新记录：  更新人   更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
	document.all('ContNo').value = tContNo;
    document.all('PolNo').value = tPolNo;
  }
  catch(ex)
  {
    alert("在PayPremQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在PayPremQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initPolGrid();
	  easyQueryClick();
  }
  catch(re)
  {
    alert("PayPremQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var PolGrid; 
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
      iArray[1][0]="集体合同号码";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
       
      iArray[2]=new Array();
      iArray[2][0]="集体保单号码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=2; 
      iArray[3][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[3][5]="3";              	                //引用代码对应第几列，'|'为分割符
      iArray[3][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[3][18]=250;
      iArray[3][19]= 0 ;
     
      //iArray[4]=new Array();
      //iArray[4][0]="管理机构";         		//列名
      //iArray[4][1]="80px";            		//列宽
      //iArray[4][2]=100;            			//列最大值
      //iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="交费次数";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      //iArray[6]=new Array();
      //iArray[6][0]="通知书号码";         		//列名
      //iArray[6][1]="40px";            		//列宽
      //iArray[6][2]=100;            			//列最大值
      //iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
       
      //iArray[7]=new Array();
      //iArray[7][0]="交费目的";         		//列名
      //iArray[7][1]="80px";            		//列宽
      //iArray[7][2]=100;            			//列最大值
      //iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="责任编码";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="交费计划编码";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="总应交金额";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    

	    iArray[8]=new Array();
      iArray[8][0]="总实交金额";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="交费间隔";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="交费日期";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

 
      iArray[11]=new Array();
      iArray[11][0]="交费类型";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0; 									//是否允许输入,1表示允许，0表示不允许
   

	  //iArray[15]=new Array();
    //  iArray[15][0]="原交至日期";         		//列名
    //  iArray[15][1]="80px";            		//列宽
    //  iArray[15][2]=100;            			//列最大值
    //  iArray[15][3]=0; 									//是否允许输入,1表示允许，0表示不允许
       
      iArray[12]=new Array();
      iArray[12][0]="现交至日期";         		//列名
      iArray[12][1]="100px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0; 									//是否允许输入,1表示允许，0表示不允许
   
      iArray[13]=new Array();
      iArray[13][0]="银行在途标志";         		//列名
      iArray[13][1]="80px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0; 									//是否允许输入,1表示允许，0表示不允许
   
      iArray[14]=new Array();
      iArray[14][0]="银行转帐成功标记";         		//列名
      iArray[14][1]="100px";            		//列宽
      iArray[14][2]=100;            			//列最大值
      iArray[14][3]=0; 									//是否允许输入,1表示允许，0表示不允许不允许
      
      iArray[15]=new Array();
      iArray[15][0]="保单失效日期";         		//列名
      iArray[15][1]="100px";            		//列宽
      iArray[15][2]=100;            			//列最大值
      iArray[15][3]=0; 									//是否允许输入,1表示允许，0表示不允许不允许
      
      iArray[16]=new Array();
      iArray[16][0]="保单复效日期";         		//列名
      iArray[16][1]="100px";            		//列宽
      iArray[16][2]=100;            			//列最大值
      iArray[16][3]=0; 									//是否允许输入,1表示允许，0表示不允许不允许
      
      iArray[17]=new Array();
      iArray[17][0]="保单终止日期";         		//列名
      iArray[17][1]="100px";            		//列宽
      iArray[17][2]=100;            			//列最大值
      iArray[17][3]=0; 									//是否允许输入,1表示允许，0表示不允许不允许
  
    
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 0;
      PolGrid.hiddenSubtraction=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.selBoxEventFuncName = "";
      PolGrid.loadMulLine(iArray);  
      
      
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
