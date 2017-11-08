<% 
//程序名称：PEdorFormPrint.jsp
//程序功能：保全报表批量打印控制台
//创建日期：2006-09-25 09:10:00
//创建人  ：wangyan
//更新记录：  更新人    更新日期      更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
	String comcode = globalInput.ComCode;
  String[] dateArr = BqNameFun.getNomalMonth(PubFun.getCurrentDate());
%>                         

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
  	document.all('StartDate').value = '<%=dateArr[0]%>';
		document.all('EndDate').value = '<%=dateArr[1]%>';
		//document.all('SaleChnl').value = '';
		document.all('BillType').value = '';
		document.all('BillTypeName').value = '';
		document.all('ManageCom').value = "<%=comcode%>";
		document.all('ChnlType').value = '';
		document.all('ChnlSel').value = '';
		document.all('RiskFlag').value = "NO";
  }
  catch(ex)
  {
    alert("在PEdorFormPrintInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
function initSelBox()
{  
  try{}
  catch(ex)
  {
    alert("在PEdorFormPrintInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    var NoticeType = fm.BillType.value;
    initNoticeGrid(NoticeType);   
    initManageCom(); //初始化管理机构，最长截取6位   
  }
  catch(re)
  {
    alert("PEdorFormPrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var NoticeGrid;          //定义为全局变量，提供给displayMultiline使用
// 投保单信息列表的初始化
function initNoticeGrid(tNoticeType)
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
		  iArray[1][0]="报表名称";         	//列名
		  iArray[1][1]="200px";            	//列宽
		  iArray[1][2]=100;            			//列最大值
		  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
  
		  switch(tNoticeType)
		  {
		    case "":
		    default:
 		        iArray[2]=new Array();
	  	   	  iArray[2][0]="管理机构";       		  //列名
	          iArray[2][1]="120px";            	//列宽
	          iArray[2][2]=100;            			//列最大值
	          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
            iArray[3]=new Array();
	          iArray[3][0]="销售渠道";          //列名
	          iArray[3][1]="60px";            	//列宽
	          iArray[3][2]=100;            			//列最大值
	          iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	    
						iArray[4]=new Array();
	          iArray[4][0]="起始日期";          //列名
	          iArray[4][1]="80px";            	//列宽
            iArray[4][2]=100;            			//列最大值
            iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许

            iArray[5]=new Array();
	          iArray[5][0]="终止日期";          //列名
	          iArray[5][1]="80px";            	//列宽
	          iArray[5][2]=100;             		//列最大值
	          iArray[5][3]=0; 					        //是否允许输入,1表示允许，0表示不允许
	    
	          iArray[6]=new Array();
	          iArray[6][0]="日期类型";          //列名
	          iArray[6][1]="60px";            	//列宽
            iArray[6][2]=100;            		  //列最大值
            iArray[6][3]=0; 					        //是否允许输入,1表示允许，0表示不允许

            iArray[7]=new Array();
	          iArray[7][0]="当前状态";          //列名
	          iArray[7][1]="60px";            	//列宽
            iArray[7][2]=100;            		  //列最大值
            iArray[7][3]=0; 					        //是否允许输入,1表示允许，0表示不允许
            
            iArray[8]=new Array();
	          iArray[8][0]="序列号";          //列名
	          iArray[8][1]="0px";            	//列宽
            iArray[8][2]=0;            		  //列最大值
            iArray[8][3]=0; 					        //是否允许输入,1表示允许，0表示不允许            

	        break;           
	  }
		
      NoticeGrid = new MulLineEnter( "fm" , "NoticeGrid" ); 
      //这些属性必须在loadMulLine前
      NoticeGrid.mulLineCount = 10;   
      NoticeGrid.displayTitle = 1;
      NoticeGrid.canSel = 1;
      NoticeGrid.hiddenPlus=1;
      NoticeGrid.hiddenSubtraction=1;
      NoticeGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>