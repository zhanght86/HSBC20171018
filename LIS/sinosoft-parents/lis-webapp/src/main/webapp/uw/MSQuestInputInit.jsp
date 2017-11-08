<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestInputInit.jsp
//程序功能：问题件录入
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
<%
  String tContNo = "";
  String tFlag = "";
  String strsql ="";
  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");
  String tMissionID = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  String tActivityID = request.getParameter("ActivityID");

  loggerDebug("MSQuestInputInit","ContNo:"+tContNo);
  loggerDebug("MSQuestInputInit","Flag:"+tFlag);

%>                            

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {            
    document.all('BackObj').value = '';
    document.all('BackObjName').value='';
    document.all('Content').value = '';
    document.all('Quest').value = '';
    document.all('Questionnaire').value= '';
    document.all('QuestionObj').value = '1';
    document.all('QuestionObjName').value = '第一被保人';
    document.all("hiddenQuestionSeq").value = '';
    document.all("hiddenProposalContNo").value = '';
    
    document.all('MissionID').value='<%=tMissionID%>';
    document.all('SubMissionID').value='<%=tSubMissionID%>';
    document.all('ActivityID').value='<%=tActivityID%>';
    document.all('NeedPrintFlag').value='Y';
    document.all('IFNeedFlagName').value='下发';
    //alert(3);
    strsql = " select insuredno,insuredname from lccont where  ContNo = '<%=tContNo%>' ";
    //alert(<%=tContNo%>);	
    
     //查询SQL，返回结果字符串
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

    //判断是否查询成功
    if (!turnPage.strQueryResult) 
    	 {
      
           return "";
 	 }
  
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
    fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
    fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
  }
  catch(ex)
  {
    alert("在UWManuDateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在UWSubInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tFlag)
{
  try
  {
	initInpBox();


	initQuestGrid();

	initHide(tContNo,tFlag);
 
	QuestQuery(tContNo,tFlag);
  questAllNeedQuestion(tContNo,tFlag);
	//QueryCont(tContNo,tFlag);
	
	initCodeDate(tContNo,tFlag);
 
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
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="问题代码";         			//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="问题内容";         			//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                           
      iArray[4]=new Array();
      iArray[4][0]="回复内容";         			//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="录入人";         			//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="录入日期";         			//列名
      iArray[6][1]="85px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=8;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="操作位置";         			//列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=50;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      //iArray[7][10] = "OperateLocation";
      //iArray[7][11] = "0|^0|新单录入/复核修改/问题修改^1|人工核保^5|新单复核";
      //iArray[7][12] = "7";
      //iArray[7][13] = "1";

      iArray[8]=new Array();
      iArray[8][0]="返回对象";         			//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=50;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][10] = "ReturnToObject";
      iArray[8][11] = "0|^1|操作员^2|业务员^3|保户^4|机构";
      iArray[8][12] = "8";
      iArray[8][13] = "1";
      
      iArray[9]=new Array();
      iArray[9][0]="是否下发";         			//列名
      iArray[9][1]="50px";            		//列宽
      iArray[9][2]=50;            			//列最大值
      iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[9][10] = "NeedPrint";
      iArray[9][11] = "0|^Y|下发^N|不下发";
//      iArray[9][12] = "0|1";
//      iArray[9][13] = "1";   
      
      iArray[10]=new Array();
      iArray[10][0]="流水号";         			//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=50;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许     
      
      iArray[11]=new Array();
      iArray[11][0]="发送对象";         			//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=50;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许     

      iArray[12]=new Array();
      iArray[12][0]="发放状态";         			//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=50;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许     
      
      iArray[13]=new Array();
      iArray[13][0]="接收对象";         			//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=50;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许    

   
      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //这些属性必须在loadMulLine前                            
      QuestGrid.mulLineCount = 1;
      QuestGrid.displayTitle = 1;
      QuestGrid.canSel = 1;
      QuestGrid.hiddenPlus = 1;
      QuestGrid.hiddenSubtraction = 1;
      QuestGrid.selBoxEventFuncName = "queryone";
      QuestGrid.loadMulLine(iArray);      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tFlag)
{
	document.all('ContNo').value=tContNo;
	document.all('Flag').value=tFlag;
	//alert("pol:"+tContNo);
}

function initCodeDate(tContNo,tFlag)
{
	//alert("tFlag:"+tFlag);
	if (tFlag == "0")
	{
		//录入外包异常件处理
		//document.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		document.all('BackObj').CodeData = "0|^1|核保通知书(打印类)^2|核保通知书(非打印类)^3|业务员通知书^4|返回机构^5|问题件修改岗";
		//document.all('BackObj').value = "1";
	}
	if (tFlag == "5")
	{
		//复核
		//document.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		//document.all('BackObj').CodeData = "0|^1|核保通知书(打印类)^2|核保通知书(非打印类)^3|业务员通知书^4|返回机构^5|问题件修改岗";
		document.all('BackObj').CodeData = "0|^1|核保通知书(打印类)^2|核保通知书(非打印类)^3|业务员通知书^4|返回机构";
		//document.all('BackObj').value = "1";
	}
	if (tFlag == "1")
	{
	 //录入
		//document.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		document.all('BackObj').CodeData = "0|^1|核保通知书(打印类)^2|核保通知书(非打印类)^3|业务员通知书^4|返回机构";
	}
	
	if (tFlag == "3")
	{
	 //问题件修改
		//document.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		document.all('BackObj').CodeData = "0|^1|核保通知书(打印类)^2|核保通知书(非打印类)^3|业务员通知书^4|返回机构";
	}
	
	//tongmeng 2007-11-08 add
	//增加人工核保问题件录入
	if (tFlag == "6")
	{
				document.all('BackObj').CodeData = "0|^1|核保通知书(打印类)^2|核保通知书(非打印类)^3|业务员通知书^4|返回机构^5|问题件修改岗";

	}
	//alert(document.all('BackObj').CodeData);
}

</script>


