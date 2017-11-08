<%
//程序名称：UWNoticeInit.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
	String strManageCom = globalInput.ComCode;
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('PolNo').value = '';
	document.all('ManageCom').value = <%=strManageCom%>;
  }
  catch(ex)
  {
    alert("在UWNoticeInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在UWNoticeInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("PEdorUWNoticeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
 var PolGrid;          //定义为全局变量，提供给displayMultiline使用
// 投保单信息列表的初始化
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="流水号";         		//列名
	  iArray[1][1]="140px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[2]=new Array();
	  iArray[2][0]="保单号码";       		//列名
	  iArray[2][1]="140px";            	//列宽
	  iArray[2][2]=100;            			//列最大值
	  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[3]=new Array();
	  iArray[3][0]="代理人编码";         	//列名
	  iArray[3][1]="100px";            	//列宽
	  iArray[3][2]=100;            			//列最大值
	  iArray[3][3]=2; 
      iArray[3][4]="AgentCode";              	        //是否引用代码:null||""为不引用
      iArray[3][5]="3";              	                //引用代码对应第几列，'|'为分割符
      iArray[3][9]="代理人编码|code:AgentCode&NOTNULL";
      iArray[3][18]=250;
      iArray[3][19]= 0 ;
	
	  iArray[4]=new Array();
	  iArray[4][0]="代理人组别";        //列名
	  iArray[4][1]="100px";            	//列宽
	  iArray[4][2]=200;            			//列最大值
	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[5]=new Array();
	  iArray[5][0]="展业机构";        //列名
	  iArray[5][1]="160px";            	//列宽
	  iArray[5][2]=200;            			//列最大值
	  iArray[5][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	  
      iArray[6]=new Array();
	  iArray[6][0]="管理机构";         	//列名
	  iArray[6][1]="100px";            	//列宽
	  iArray[6][2]=100;            			//列最大值
	  iArray[6][3]=2; 
      iArray[6][4]="station";              	        //是否引用代码:null||""为不引用
      iArray[6][5]="3";              	                //引用代码对应第几列，'|'为分割符
      iArray[6][9]="管理机构|code:station&NOTNULL";
      iArray[6][18]=250;
      iArray[6][19]= 0 ;
      
      iArray[7]=new Array();
	  iArray[7][0]="保全申请号";         	//列名
	  iArray[7][1]="100px";            	//列宽
	  iArray[7][2]=100;            			//列最大值
	  iArray[7][3]=0; 
	  
      iArray[8]=new Array();
	  iArray[8][0]="工作流任务编码";         	//列名
	  iArray[8][1]="0px";            	//列宽
	  iArray[8][2]=100;            			//列最大值
	  iArray[8][3]=0; 
	  
	  iArray[9]=new Array();
	  iArray[9][0]="工作流子任务编码";         	//列名
	  iArray[9][1]="0px";            	//列宽
	  iArray[9][2]=100;            			//列最大值
	  iArray[9][3]=0; 
      
      
	
			
      PolGrid = new MulLineEnter( "fmSave" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 10;   
      PolGrid.displayTitle = 1;
      //PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus=1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>