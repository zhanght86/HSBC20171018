<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestQueryInit.jsp
//程序功能：问题件查询
//创建日期：2002-09-24 11:10:36
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
  String tMissionID = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");


  loggerDebug("QuestQueryInit","ContNo:"+tContNo);
  loggerDebug("QuestQueryInit","Flag:"+tFlag);


%>            
<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox(tContNo,tFlag,tMissionID,tSubMissionID)
{ 
try
  {                 
    fm.all('MissionID').value = tMissionID;
    fm.all('SubMissionID').value = tSubMissionID;                  
    fm.all('ContNo').value = tContNo;
    fm.all('IfReply').value = '';
    if (tFlag == "5")
    {
    	fm.all('BackObj').value = '';
    }
    else
    {
    	fm.all('BackObj').value = '';
    }
    
    fm.all('ManageCom').value = '';
    fm.all('OManageCom').value = '';
    if(tFlag == "1")
    {
    	divOperation.style.display = "";
    }
    if (tFlag == "5")
    {
    	fm.all('OperatePos').value = '';
    }
    else if(tFlag == "3"||tFlag == "1")
    {
    	fm.all('OperatePos').value = '';	    
    }
    else if(tFlag == "4")
    {
    	fm.all('BackObj').value = '4';
    	fm.all('OperatePos').value = '';
    }
    else
    {
    	fm.all('OperatePos').value = '';
    }
    fm.all('Content').value = '';
    fm.all('ReplyResult').value = '';
  }
  catch(ex)
  {
    alert("在UWManuDateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

function initContent()
{
    fm.all('Content').value = '';
    fm.all('ReplyResult').value = '';
}

function initForm(tContNo,tFlag,tMissionID,tSubMissionID)
{
  try
  {
	initInpBox(tContNo,tFlag,tMissionID,tSubMissionID);
	initQuestGrid();
	initHide(tContNo,tFlag);	
	QuestQuery(tContNo,tFlag);

	
	if (tFlag == "5"||tFlag == "1")
	{
		showDiv(divButton,false);
	}
	else
	{
		showDiv(divButton,"true");
	}
	
	if (tFlag == "1")
	{
		showDiv(divModiButton,"true");
	}
	else
	{
		showDiv(divModiButton,"false");
	}

	initCodeData(tContNo,tFlag);
	query();

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
      iArray[1][0]="投保单号";    	//列名
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
      iArray[4][1]="70px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="录入人";         			//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="录入日期";         			//列名
      iArray[6][1]="70px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
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
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=50;            			//列最大值
      iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][10] = "ReturnToObject";
      iArray[8][11] = "0|^1|操作员^2|业务员^3|保户^4|机构";
      iArray[8][12] = "8";
      iArray[8][13] = "1";
      
      iArray[9]=new Array();
      iArray[9][0]="是否需要打印";         			//列名
      iArray[9][1]="50px";            		//列宽
      iArray[9][2]=50;            			//列最大值
      iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[9][10] = "NeedPrint";
      iArray[9][11] = "0|^Y|需要打印^N|不需要打印";
      iArray[9][12] = "9";
      iArray[9][13] = "0";   
      
      iArray[10]=new Array();
      iArray[10][0]="流水号";         			//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=50;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许         
      
      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //这些属性必须在loadMulLine前                            
      QuestGrid.mulLineCount = 1;
      QuestGrid.displayTitle = 1;
      QuestGrid.canSel = 1;
      QuestGrid.hiddenPlus = 1;
      QuestGrid.hiddenSubtraction = 1;
      QuestGrid.loadMulLine(iArray); 
      
      QuestGrid. selBoxEventFuncName = "queryone";
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tFlag)
{
	fm.all('ContNoHide').value=tContNo;
	fm.all('Flag').value=tFlag;
	fm.all('HideOperatePos').value="";
	fm.all('HideQuest').value="";
	fm.all('HideSerialNo').value = "";
	//alert("pol:"+tContNo);
}

function initCodeData(tContNo,tFlag)
{
	if (tFlag == "5")
	{
		fm.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		fm.all('OperatePos').CodeData = "0|^0|承保^5|复核";
	}
	if (tFlag == "1")
	{
		fm.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		fm.all('OperatePos').CodeData = "0|^0|承保^1|核保^5|复核";
	}
	if (tFlag == "2")
	{
		fm.all('BackObj').CodeData = "0|^1|操作员";
		fm.all('OperatePos').CodeData = "0|^1|核保^5|复核";
	}
	if (tFlag == "3")
	{
		fm.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		fm.all('OperatePos').CodeData = "0|^0|承保^1|核保^5|复核";
	}
	if (tFlag == "4")
	{
		fm.all('BackObj').CodeData = "0|^4|机构";
		fm.all('OperatePos').CodeData = "0|^0|承保^1|核保^5|复核";
	}	
}

</script>


