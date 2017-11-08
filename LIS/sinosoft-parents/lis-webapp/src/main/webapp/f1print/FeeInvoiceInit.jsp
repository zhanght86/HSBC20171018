<%
//程序名称：FeeInvoiceInit.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('PayNo').value = '';
    document.all('IncomeNo').value = '';
    document.all('IncomeType').value = '';
    document.all('PayDate').value = '';
    document.all('MngCom').value = '<%=tG.ManageCom%>';
    document.all('AgentCode').value = '';
  }
  catch(ex)
  {
    alert("在FeeInvoiceInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

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
    alert("在FeeInvoiceInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();   
    initPolGrid(); 
  }
  catch(re)
  {
    alert("FeeInvoiceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
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
      iArray[1][0]="交费收据号码";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="实收编号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="合同号";         		//列名
      iArray[3][1]="160px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="编号类型";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][10] = "NoType1";
   //   iArray[3][11] = "0|^1|集体保单号^2|个人保单号^3|批改号^4|合同投保单号^5|集体投保单号^6|个人投保单号^7|合同印刷号^8|集体印刷号^9|个人印刷号";
      iArray[4][11] = "0|^1|集体合同号^2|个人合同号^3|家庭单大合同号^10|保全收费对应的批改号";
      iArray[4][12] = "3";
      iArray[4][18]=300;
      iArray[4][19] = "0";

      iArray[5]=new Array();
      iArray[5][0]="总实交金额";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="交费日期";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="确认日期";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  	iArray[8]=new Array();
      iArray[8][0]="操作员";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
	  iArray[9]=new Array();
      iArray[9][0]="管理机构";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=2; 
      iArray[9][4]="station";              	        //是否引用代码:null||""为不引用
      iArray[9][5]="8";              	                //引用代码对应第几列，'|'为分割符
     // iArray[9][9]="出单机构|code:station&NOTNULL";
      iArray[9][18]=250;
      iArray[9][19]= 0 ;      

	  	iArray[10]=new Array();
      iArray[10][0]="代理人编码";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=2; 									//是否允许输入,1表示允许，0表示不允许
      iArray[10][4]="AgentCode";              	        //是否引用代码:null||""为不引用
      iArray[10][5]="9";              	                //引用代码对应第几列，'|'为分割符
     // iArray[10][9]="代理人编码|code:AgentCode&NOTNULL";
      iArray[10][18]=250;
      iArray[10][19]= 0 ;      

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 10;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
