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
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tGrpContNo = "";
  String tFlag = "";
  tGrpContNo = request.getParameter("GrpContNo");
  tFlag = request.getParameter("Flag");


  loggerDebug("GrpQuestQueryInit","GrpContNo:"+tGrpContNo);
  loggerDebug("GrpQuestQueryInit","Flag:"+tFlag);


%>            
<script language="JavaScript">

//得到界面的调用位置,默认为1,表示个人保单直接录入.
// 1 -- 个人投保单直接录入
// 2 -- 集体下个人投保单录入
// 3 -- 个人投保单复核修改
// 4 -- 集体下个人投保单复核
// 5 -- 个人投保单复核
// 6 -- 个人投保单查询
// 7 -- 保全新保加人
// 8 -- 保全新增附加险
// 9 -- 无名单补名单
// 10-- 浮动费率
// 13 -- 集体下投保单复核修改
// 16 -- 集体下投保单查询

// 99-- 随动定制

// 输入框的初始化（单记录部分）
function initInpBox(tGrpContNo,tFlag)
{ 
try
  {                                   
    fm.all('GrpContNo').value = tGrpContNo;
    fm.all('IfReply').value = '';
    
    fm.all('ManageCom').value = '';
    fm.all('OManageCom').value = '';
        if(tFlag=='13')
        {
   	//fm.all('BackObj').value = '1';
   	}
   	else
   	fm.all('BackObj').value = '';
   	fm.all('OperatePos').value = '';
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

function initForm(tGrpContNo,tFlag)
{
  try
  {
	initInpBox(tGrpContNo,tFlag);
	initQuestGrid();
	initHide(tGrpContNo,tFlag);	
	//fm.all('BackObj').value='1';
	QuestQuery(tGrpContNo,tFlag);


	if (tFlag == "3" || tFlag == "13")
	{
		showDiv(divButton,"true");
	}
	else
	{
		showDiv(divButton,"false");
	}
	
	if (tFlag == "3" || tFlag == "13")
	{
		showDiv(divModiButton,"false");
	}
	else
	{
		showDiv(divModiButton,"false");
	}

	initCodeData(tGrpContNo,tFlag);
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
      iArray[1][0]="投保单号码";    	//列名
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
      //iArray[7][11] = "0|^1|个单录入^2|团单录入^3|个单复核修改^4|团单复核^5|个单复核修改";
      //iArray[7][12] = "7";
      //iArray[7][13] = "0";

      iArray[8]=new Array();
      iArray[8][0]="返回对象";         			//列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=50;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      //iArray[8][10] = "ReturnToObject";
      //iArray[8][11] = "0|^1|操作员^2|业务员^3|保户^4|机构";
      //iArray[8][12] = "8";
      //iArray[8][13] = "0";
      
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

function initHide(tGrpContNo,tFlag)
{
	fm.all('GrpContNo').value=tGrpContNo;
	fm.all('GrpContNoHide').value=tGrpContNo;
	fm.all('Flag').value=tFlag;
	fm.all('HideOperatePos').value="";
	fm.all('HideQuest').value="";
	fm.all('HideSerialNo').value = "";
}

function initCodeData(tGrpContNo,tFlag)
{	
	 
	
	//fm.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
	fm.all('BackObj').CodeData = "0|^1|操作员";
	
	fm.all('OperatePos').CodeData = "0|^2|承保^4|复核^13|复核修改";
	
}

</script>


