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

  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");

  loggerDebug("LLUWPSendInit","ContNo:"+tContNo);
  loggerDebug("LLUWPSendInit","Flag:"+tFlag);

%>                            

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
      fm.all('NoticeType').value = 'LP81';
      fm.all('Content').value = '';
  }
  catch(ex)
  {
      alert("在LLUWPSendInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}


function initForm(tContNo,tMissionID,tSubMissionID,tEdorNo,tEdorType)
{
  try
  {

	initInpBox();

	initQuestionTypeGrid();

	initHide(tContNo,tMissionID,tSubMissionID,tEdorNo,tEdorType);
	initLWMission();
	
	divUWSpec1.style.display = 'none';
    divUWSpec.style.display = 'none';
	divnoticecontent.style.display = '';
	fm.Content.value = "";
	initLoprtManager();


	
	

  }
  catch(re)
  {
    alert("在LLUWPSendInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initQuestionTypeGrid()
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
      iArray[1][0]="问卷类型编号";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4] = "rreporttype";             			//是否允许输入,1表示允许，0表示不允许
      iArray[1][5]="1|2";     //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1";    //上面的列中放置引用代码中第几位值
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="问卷类型名称";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      
      QuestionTypeGrid = new MulLineEnter( "fm" , "QuestionTypeGrid" ); 
      //这些属性必须在loadMulLine前                            
      QuestionTypeGrid.mulLineCount = 0;
      QuestionTypeGrid.displayTitle = 1;
      QuestionTypeGrid.canChk = 0;
      QuestionTypeGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      
      }
      catch(ex)
      {
        alert(ex);
      }
}



// 责任信息列表的初始化
function initPolGrid()
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
      iArray[1][0]="打印流水号";         			//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="号码";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      
      iArray[3]=new Array();
      iArray[3][0]="号码类型";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][10] = "NoType";
      iArray[3][11] = "0|^00|个单投保单号^01|集体投保单号^02|合同号^03|批单号^04|实付收据号^05|保单印刷号";
      iArray[3][12] = "3";
      iArray[3][19] = "0";
      
  
      iArray[4]=new Array();
	  iArray[4][0]="业务员编码";
	  iArray[4][1]="100px";
	  iArray[4][2]=100;
	  iArray[4][3]=2;
	  iArray[4][4]="AgentCode";
	  iArray[4][5]="3";
	  iArray[4][9]="代理人编码|code:AgentCode&NOTNULL";
	  iArray[4][18]=250;
	  iArray[4][19]= 0 ;

		
      iArray[5]=new Array();
      iArray[5][0]="通知书类型";    	//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][4]="uwnoticetype";
	  iArray[5][5]="3";
	  iArray[5][9]="代理人编码|code:uwnoticetype&NOTNULL";
	  iArray[5][18]=250;
	  iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="打印状态";    	//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
                               

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前                            
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
    
      PolGrid.loadMulLine(iArray);  
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      
     
      }
      catch(ex)
      {
    
        alert(ex);
      }
}

function initHide(tContNo,tMissionID,tSubMissionID,tEdorNo,tEdorType)
{
	fm.all('ContNo').value=tContNo;
  	fm.all('MissionID').value = tMissionID;
  	fm.all('SubMissionID').value = tSubMissionID;	
  	fm.all('EdorNo').value = tEdorNo;
  	fm.all('EdorType').value = tEdorType;   
}

</script>


