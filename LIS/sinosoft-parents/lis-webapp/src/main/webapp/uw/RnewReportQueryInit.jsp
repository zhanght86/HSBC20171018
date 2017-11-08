<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：RReportQueryInit.jsp
//程序功能：生存调查报告查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人：ln    更新日期：2008-10-24   更新原因/内容：根据新核保要求进行修改
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
    alert("在RReportQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

function initForm(tContNo,tPrtSeq,tMissionID,tSubMissionID)
{
  try
  {	
	initQuestGrid();		
	
	initRReportGrid();

	initRReportResultGrid();
	
	initHide(tContNo,tPrtSeq,tMissionID,tSubMissionID);
	
	
	
	if(Flag == "1")
	{
	divOperation.style.display = "";
	//divRReportButton.style.display = "none";

	divMainHealth.style.display = "";
	fm.Assess.disabled = true;
		easyQueryClick(tContNo);
		
	}
	
if(Flag == "2")
{	

easyQueryClickItem();
}

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 责任信息列表的初始化
function initQuestGrid()
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
      iArray[1][0]="印刷号";    	//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="生存调查人";         			//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="录入人";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                           
      iArray[4]=new Array();
      iArray[4][0]="录入日期";         			//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="回复人";         			//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="回复人姓名";         			//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="回复日期";         			//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="是否回复";         			//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[9]=new Array();
      iArray[9][0]="流水号";         			//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[10]=new Array();
      iArray[10][0]="生调原因";         			//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      iArray[10][4] = "rreportreason";             			//是否允许输入,1表示允许，0表示不允许
      iArray[10][5]="10";     //引用代码对应第几列，'|'为分割符
      iArray[10][6]="0";    //上面的列中放置引用代码中第几位值
      iArray[10][18] = 500;         
      
      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //这些属性必须在loadMulLine前                            
      QuestGrid.mulLineCount = 1;
      QuestGrid.displayTitle = 1;
      QuestGrid.canChk = 0;
      QuestGrid.hiddenPlus = 1;
      QuestGrid.hiddenSubtraction = 1;
      QuestGrid.canSel =1
      QuestGrid.loadMulLine(iArray);
      
      
      QuestGrid. selBoxEventFuncName = "easyQueryChoClick";
      
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
      RReportGrid.mulLineCount = 0;
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


