<%
//程序名称：TempFeeQueryInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人 zy   更新日期 2008-09-22 9:51    更新原因/内容
%>
<%
     //添加页面控件的初始化。
     GlobalInput tG = new GlobalInput();
     tG = (GlobalInput)session.getValue("GI");
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
	// 查询条件
    document.all('TempFeeStatus').value = '';
    document.all('RiskCode').value = '';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('Operator').value = '';
    document.all('TempFeeNo').value = '';
    document.all('PrtNo').value = '';
    document.all('AgentCode').value = '';
    document.all('ManageCom').value = <%=tG.ComCode%>;
      
  }
  catch(ex)
  {
    alert("在TempFeeQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在TempFeeQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();  
    initTempQueryGrid();
  }
  catch(re)
  {
    alert("在TempFeeQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

 var TempQueryGrid ;
 
// 保单信息列表的初始化
function initTempQueryGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            		//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";          		//列名
      iArray[1][1]="80px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[1][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[1][5]="1|2";              	                //引用代码对应第几列，'|'为分割符
      iArray[1][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[1][18]=300;
      
      
      iArray[2]=new Array();
      iArray[2][0]="业务员";   		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            		//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="组别";		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=60;            		//列最大值
      iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="币种";      	   		//列名
      iArray[4][1]="80px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="金额";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=200;            		//列最大值
      iArray[5][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="收据号码";         		//列名
      iArray[6][1]="160px";            		//列宽
      iArray[6][2]=200;            	        //列最大值
      iArray[6][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="交费日期";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            	        //列最大值
      iArray[7][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="到帐日期";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=200;            	        //列最大值
      iArray[8][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="到帐入机日期";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=200;            	        //列最大值
      iArray[9][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="入机日期";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=200;            	        //列最大值
      iArray[10][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="核销日期";         	//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=200;            	        //列最大值
      iArray[11][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="操作员";         	//列名
      iArray[12][1]="80px";              	//列宽
      iArray[12][2]=200;            	        //列最大值
      iArray[12][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="状态";         	//列名
      iArray[13][1]="100px";              	//列宽
      iArray[13][2]=200;            	        //列最大值
      iArray[13][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[14]=new Array();
      iArray[14][0]="对应号码";         	//列名
      iArray[14][1]="160px";              	//列宽
      iArray[14][2]=200;            	        //列最大值
      iArray[14][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
      
      iArray[15]=new Array();
      iArray[15][0]="缴费方式";         	//列名
      iArray[15][1]="60px";              	//列宽
      iArray[15][2]=200;            	        //列最大值
      iArray[15][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
      
      iArray[16]=new Array();
      iArray[16][0]="缴费年期";         	//列名
      iArray[16][1]="60px";              	//列宽
      iArray[16][2]=200;            	        //列最大值
      iArray[16][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      
      TempQueryGrid = new MulLineEnter( "fm" , "TempQueryGrid" ); 
      //这些属性必须在loadMulLine前
      TempQueryGrid.mulLineCount = 5;   
      TempQueryGrid.displayTitle = 1;
      TempQueryGrid.hiddenPlus = 1;
      TempQueryGrid.hiddenSubtraction = 1;
//      TempQueryGrid.locked = 1;
//      TempQueryGrid.canSel = 1;
      TempQueryGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
