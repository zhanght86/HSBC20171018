<%
//程序名称：GetCredenceInit.jsp
//程序功能：
//创建日期：2002-12-20 
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput tG = (GlobalInput)session.getValue("GI");
	
	if(tG == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = tG.Operator;
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
	  var managecomvalue = '<%=tG.ManageCom%>';
function initInpBox()
{ 
  try
  {     
    document.all('ActuGetNo').value = '';
    document.all('OtherNo').value = '';
    document.all('OtherNoType').value = '';
    document.all('ShouldDate').value = '';
    document.all('MngCom').value = '<%=tG.ManageCom%>';
    document.all('AgentCode').value = '';
  }
  catch(ex)
  {
    alert("在GetCredenceInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在GetCredenceInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initPolGrid(); 
    
    //暂交费退费打印给付凭证时使用
    if (typeof(top.prtData) != "undefined") {
      initWithdraw();    
    }  
  }
  catch(re)
  {
    alert("GetCredenceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[1][0]="实付号码";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="其它号码";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="其它号码类型";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][10] = "NoType";
      iArray[3][11] = "0|^1|客户号^2|生存领取对应的合同号^4|暂收退费的暂收据号^5|理赔对应的赔案号^6|其他退费的给付通知书号码^7|红利给付对应的合同号^9|续期回退对应的合同号^10|保全对应的批改号";
      iArray[3][12] = "3";
      iArray[3][18]=300;
      iArray[3][19] = "0";

      iArray[4]=new Array();
      iArray[4][0]="总给付金额";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="应付日期";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="财务确认日期";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[7]=new Array();
      iArray[7][0]="操作员";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
	  iArray[8]=new Array();
      iArray[8][0]="管理机构";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=2; 
      iArray[8][4]="station";              	        //是否引用代码:null||""为不引用
      iArray[8][5]="8";              	                //引用代码对应第几列，'|'为分割符
      iArray[8][9]="出单机构|code:station&NOTNULL";
      iArray[8][18]=250;
      iArray[8][19]= 0 ;        

	  iArray[9]=new Array();
      iArray[9][0]="代理人编码";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=2; 									//是否允许输入,1表示允许，0表示不允许
      iArray[9][4]="AgentCode";              	        //是否引用代码:null||""为不引用
      iArray[9][5]="9";              	                //引用代码对应第几列，'|'为分割符
      iArray[9][9]="代理人编码|code:AgentCode&NOTNULL";
      iArray[9][18]=250;
      iArray[9][19]= 0 ;       

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
