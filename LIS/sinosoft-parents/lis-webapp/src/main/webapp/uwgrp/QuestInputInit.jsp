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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tContNo = "";
  String tFlag = "";
  String strsql ="";
  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");

  loggerDebug("QuestInputInit","ContNo:"+tContNo);
  loggerDebug("QuestInputInit","Flag:"+tFlag);

%>                            

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
    fm.all('BackObj').value = '';
    fm.all('Content').value = '';
    fm.all('Quest').value = '';
    fm.all('QuestionObj').value = '0';
    fm.all('QuestionObjName').value = '投保人';
    //alert(3);
    strsql = " select appntno,appntname from lccont where  ContNo = '<%=tContNo%>' ";
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


	//initQuestGrid();

	initHide(tContNo,tFlag);
 
	QuestQuery(tContNo,tFlag);
 
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
      iArray[1][0]="问题代码";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="问题名称";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="问题内容";         			//列名
      iArray[3][1]="300px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                           

      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //这些属性必须在loadMulLine前                            
      QuestGrid.mulLineCount = 1;
      QuestGrid.displayTitle = 1;
      QuestGrid.canChk = 1;
      QuestGrid.loadMulLine(iArray);  
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tFlag)
{
	fm.all('ContNo').value=tContNo;
	fm.all('Flag').value=tFlag;
	//alert("pol:"+tContNo);
}

function initCodeDate(tContNo,tFlag)
{
	if (tFlag == "0")
	{
		fm.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		//fm.all('BackObj').value = "1";
	}
	if (tFlag == "5")
	{
		fm.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		//fm.all('BackObj').value = "1";
	}
	if (tFlag == "1")
	{
		fm.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
	}
}

</script>


