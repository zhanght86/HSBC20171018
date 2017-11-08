<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuHealthInit.jsp
//程序功能：保全人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="java.util.*"%>
  <%@page import="java.lang.Math.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  
  String tFlag = "";
  String tUWIdea = "";  
  String tPrtNo = "";
  Date today = new Date();
  today = PubFun.calDate(today,15,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  tContNo = request.getParameter("ContNo");
  
  tPrtNo = request.getParameter("PrtNo");
 %>                            

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
	
    document.all('ContNo').value = '';
   
  }
  catch(ex)
  {
    alert("在UWManuHealthInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在UWSubInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tPrtNo)
{
  try
  {
	initInpBox();
	
	initUWHealthGrid();
	
	initMainUWHealthGrid();
	
	initDisDesbGrid();
	
	initHide(tContNo,tPrtNo);
	
	initInsureNo(tPrtNo);
	
 }
  catch(re) {
    alert("UWManuHealthInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


// 责任信息列表的初始化
function initMainUWHealthGrid()
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
      iArray[1][0]="合同号";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[2]=new Array();
      iArray[2][0]="流水号";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

              

      iArray[3]=new Array();
      iArray[3][0]="体检客户号";         			//列名
      iArray[3][1]="120px";            			//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="体检人姓名";         			//列名
      iArray[4][1]="120px";            			//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="体检时间";         			//列名
      iArray[5][1]="120px";            			//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="入机时间";         			//列名
      iArray[6][1]="120px";            			//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="打印状态";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[7][1]="60px";         			//列宽
      iArray[7][2]=10;          			//列最大值
      iArray[7][3]=2; 
      iArray[7][10] = "ifPrint";
      iArray[7][11] = "0|3^0|未打印|^1|已打印|^2|已回复|";  
      iArray[7][12] = "7";
      iArray[7][13] = "0";
      iArray[7][14] = "N";
     
      
      
      MainHealthGrid = new MulLineEnter( "fm" , "MainHealthGrid" ); 
      //这些属性必须在loadMulLine前                            
      MainHealthGrid.mulLineCount = 0;
      MainHealthGrid.displayTitle = 1;
      MainHealthGrid.canChk = 0;
      MainHealthGrid.canSel =1
      MainHealthGrid.loadMulLine(iArray); 
      MainHealthGrid.hiddenPlus = 1;
      MainHealthGrid.hiddenSubtraction = 1;
      MainHealthGrid. selBoxEventFuncName = "easyQueryClick";
      //这些操作必须在loadMulLine后面
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


// 责任信息列表的初始化
function initUWHealthGrid()
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
      iArray[1][0]="体检项目编号";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4] = "HealthCode";             			//是否允许输入,1表示允许，0表示不允许
      iArray[1][5]="1|2";     //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1";    //上面的列中放置引用代码中第几位值
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="体检项目名称";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="是否免检";         			//列名
      iArray[3][1]="120px";            			//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][10] = "IfCheck";
      iArray[3][11] = "0|2^Y|免检|^N|不免检|";
      iArray[3][12] = "3";
      iArray[3][13] = "0";
      iArray[3][14] = "N";

      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //这些属性必须在loadMulLine前                            
      HealthGrid.mulLineCount = 0;
      HealthGrid.displayTitle = 1;
      HealthGrid.canChk = 0;
      HealthGrid.hiddenPlus = 1;
      HealthGrid.hiddenSubtraction = 1;
      HealthGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


function initDisDesbGrid()
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
      iArray[1][0]="疾病症状";    	//列名
      iArray[1][1]="260px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="疾病结论";         		//列名
      iArray[2][1]="260px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ICDName";
      iArray[2][9]="疾病结论|len<=120";
      iArray[2][18]=300;

      iArray[3]=new Array();
      iArray[3][0]="ICD编码";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="ICDCode";
      iArray[3][9]="ICD编码|len<=20";
      iArray[3][15]="ICDName";
      iArray[3][17]="2";
      iArray[3][18]=700;
     

      DisDesbGrid = new MulLineEnter( "fm" , "DisDesbGrid" ); 
      //这些属性必须在loadMulLine前                            
      DisDesbGrid.mulLineCount = 0;
      DisDesbGrid.displayTitle = 1;
      DisDesbGrid.canChk = 0;
      DisDesbGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DisDesbGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tPrtNo)
{
	document.all('ContNO').value = tContNo;
    
	document.all('PrtNo').value = tPrtNo ;
	
}


</script>


