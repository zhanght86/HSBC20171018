<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：RReportQueryInit.jsp
//程序功能：生存调查报告查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox(tContNo,tFlag)
{ 
try
  {                                   
    
  }
  catch(ex)
  {
    alert("在GrpRReportResultInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

function initForm(tGrpContNo,tPrtSeq,tMissionID,tSubMissionID)
{
  try
  {	
	//initQuestGrid();		
	initContGrid();
	initRReportGrid();
	initRReportResultGrid();
	QueryCont(tGrpContNo);
	

	
	
	//initHide(tContNo,tPrtSeq,tMissionID,tSubMissionID);
	


  }
  catch(re)
  {
    alert("GrpRReportResultInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 责任信息列表的初始化
function initContGrid()
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
		iArray[1][0]="个单合同号";
		iArray[1][1]="120px";
		iArray[1][2]=170;
		iArray[1][3]=0;
		

		iArray[2]=new Array();
		iArray[2][0]="打印流水号";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="代理人编码";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="录入时间";         	
		iArray[4][1]="100px";           
		iArray[4][2]=100;            	
		iArray[4][3]=0; 
      
      ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 
      //这些属性必须在loadMulLine前                            
      ContGrid.mulLineCount = 4;
      ContGrid.locked = 1;
      ContGrid.displayTitle = 1;
      ContGrid.canChk = 0;
      ContGrid.hiddenPlus = 1;
      ContGrid.hiddenSubtraction = 1;
      ContGrid.canSel =1
      ContGrid.loadMulLine(iArray);
      
      
      ContGrid. selBoxEventFuncName = "ReportInfoClick";
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 责任信息列表的初始化
function initRReportGrid()
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
      iArray[1][0]="生调项目编号";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4] = "RReportCode1";             			//是否允许输入,1表示允许，0表示不允许
      iArray[1][5]="1|2";     //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1";    //上面的列中放置引用代码中第几位值
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="生调项目名称";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="备注";         			//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      
      RReportGrid = new MulLineEnter( "fm" , "RReportGrid" ); 
      //这些属性必须在loadMulLine前                            
      RReportGrid.mulLineCount = 3;
      RReportGrid.hiddenPlus = 1;
      RReportGrid.hiddenSubtraction = 1;
      RReportGrid.displayTitle = 1;
      RReportGrid.canChk = 0;
      RReportGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 责任信息列表的初始化
function initRReportResultGrid()
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
      iArray[1][0]="生调结果";         		//列名
      iArray[1][1]="260px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="ICDName";
      iArray[1][9]="生调结论|len<=120";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="ICD编码";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ICDCode";
      iArray[2][9]="ICD编码|len<=20";
      iArray[2][15]="ICDName";
      iArray[2][17]="2";
      iArray[2][18]=700;
     

      RReportResultGrid = new MulLineEnter( "fm" , "RReportResultGrid" ); 
      //这些属性必须在loadMulLine前                            
      RReportResultGrid.mulLineCount = 0;
      RReportResultGrid.displayTitle = 1;
      RReportResultGrid.canChk = 0;
      RReportResultGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DisDesbGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tPrtSeq,tMissionID,tSubMissionID)
{
	document.all('ContNo').value=tContNo;		
	document.all('PrtSeq').value =tPrtSeq;
	document.all('MissionID').value = tMissionID;
	document.all('SubMissionID').value = tSubMissionID;
	
}



</script>


