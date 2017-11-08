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
function initInpBox()
{ 
try
  {                                   
    document.all('PEAddress').value = '';
    document.all('PEDoctor').value = '';
    document.all('PEDate').value = '';
    document.all('REPEDate').value = '';
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
	initInpBox();		
	initContGrid();
	initHealthGrid();
	initDisDesbGrid();
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
function initHealthGrid()
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
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="ITEM";

      iArray[2]=new Array();
      iArray[2][0]="体检项目名称";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      

      iArray[3]=new Array();
      iArray[3][0]="体检项目结论";         			//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="ITEMRESULT";
      iArray[3][17]="1";
      iArray[3][15]="ITEM";
      
      
      iArray[4]=new Array();
      iArray[4][0]="备注";    	//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //这些属性必须在loadMulLine前                            
      HealthGrid.mulLineCount = 3;
      HealthGrid.hiddenPlus = 1;
      HealthGrid.hiddenSubtraction = 1;
      HealthGrid.displayTitle = 1;
      HealthGrid.canChk = 0;
      HealthGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 责任信息列表的初始化
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
      iArray[3][3]=1;  
     

      DisDesbGrid = new MulLineEnter( "fm" , "DisDesbGrid" ); 
      //这些属性必须在loadMulLine前                            
      DisDesbGrid.mulLineCount = 0;
      DisDesbGrid.displayTitle = 1;
      DisDesbGrid.canChk = 0;
      DisDesbGrid.hiddenPlus = 1;
      DisDesbGrid.hiddenSubtraction = 1;
      DisDesbGrid.loadMulLine(iArray);  
      
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


