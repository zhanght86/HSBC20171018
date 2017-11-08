<%
//程序名称：FIDataBaseLinkInit.jsp
//程序功能：数据接口配置管理
//创建日期：2008-08-04
//创建人  ：范昕  
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("网页超时，请重新登录");
		return;
	}
	String strOperator = globalInput.Operator;
%>                          
<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
  	fm.reset();
  	document.all('InterfaceCode').value = '';
		fm.Operator.value = '<%= strOperator %>';
  	document.all('InterfaceName').value = '';
  	document.all('DBType').value = '';
  	document.all('IP').value = '';
  	document.all('Port').value = '';
  	document.all('DBName').value = '';
  	document.all('ServerName').value = '';
  	document.all('UserName').value = '';
    document.all('PassWord').value = '';
    document.all('ManageCom').value = '86';
  }
  catch(ex)
  {
    alert("在FIDataBaseLinkInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在FIDataBaseLinkInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFIDataBaseLinkGrid();
  }
  catch(re)
  {
    alert("在FIDataBaseLinkInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initFIDataBaseLinkGrid()
{
	var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="接口编码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="60px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="接口名称";    	//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="数据库类型";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="IP";         			//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="端口号";         			//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[6]=new Array();
      iArray[6][0]="数据库名称";         			//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="服务名称";         			//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="用户名";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      
      iArray[9]=new Array();
      iArray[9][0]="密码";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      


      FIDataBaseLinkGrid = new MulLineEnter( "document" , "FIDataBaseLinkGrid" ); 
      FIDataBaseLinkGrid.mulLineCount = 5;   
      FIDataBaseLinkGrid.displayTitle = 1;
      FIDataBaseLinkGrid.canSel=1;
      FIDataBaseLinkGrid.locked = 1;	
	  	FIDataBaseLinkGrid.hiddenPlus = 1;
	  	FIDataBaseLinkGrid.hiddenSubtraction = 1;
      FIDataBaseLinkGrid.loadMulLine(iArray);  
      FIDataBaseLinkGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
