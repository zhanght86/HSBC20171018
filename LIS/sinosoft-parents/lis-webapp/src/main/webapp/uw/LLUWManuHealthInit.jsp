<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuHealthInit.jsp
//程序功能：保全人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
//              liurx      2005-8-30       添加Flag标志，为"1"时表示保全调用
%>
<!--用户校验类-->
  <%@page import="java.util.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  String tUWIdea = "";
  String tPrtNo = "";
  String tBatchNo = "";
  String tRgtNo = "";
  Date today = new Date();
  today = PubFun.calDate(today,30,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  tContNo = request.getParameter("ContNo2");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  tPrtNo = request.getParameter("PrtNo");
  tFlag = request.getParameter("Flag");
  tBatchNo = request.getParameter("BatchNo");
  tRgtNo = request.getParameter("RgtNo");
 %>

<script language="JavaScript">
var mContNo = '<%=tContNo%>';
var mPrtNo = '<%=tPrtNo%>';
var mFlag = '<%=tFlag%>';
var mBatchNo = '<%=tBatchNo%>';
var mRgtNo = '<%=tRgtNo%>';



function initForm(tContNo,tMissionID,tSubMission,tPrtNo,tFlag,tBatchNo,tRgtNo)
{
  try
  {
    initInpBox();
		initPEGrid();
    initHide(tContNo,tMissionID,tSubMission,tPrtNo,tFlag,tBatchNo,tRgtNo);
    initHospital(tContNo);
    initInsureNo(tPrtNo);
    querySavePEInfo();
    //清除所有选择项目
   	clearAllCheck();
 }
  catch(re) {
    alert("在 UWManuHealthInit.jsp --> InitForm 函数中发生异常:初始化界面错误！ ");
  }
}

// 输入框的初始化（单记录部分）
function initInpBox()
{
try
  {
    document.all('EdorNo').value = NullToEmpty('<%=request.getParameter("EdorNo")%>');
    document.all('ContNo').value = '';
    document.all('MissionID').value = '';
    document.all('SubMissionID').value = '';
    document.all('EDate').value = '<%=tday%>';
    document.all('PrintFlag').value = '';
    document.all('Hospital').value = '';
    document.all('IfEmpty').value = 'Y';
    
    document.all('InsureNo').value = '';
    document.all('Note').value = '';
   // document.all('bodyCheck').value = '';
  }
  catch(ex)
  {
    alert("在 UWManuHealthInit.jsp --> InitInpBox 函数中发生异常:初始化界面错误！ ");
  }
}

function initHide(tContNo,tMissionID,tSubMission,tPrtNo,tFlag,tBatchNo,tRgtNo)
{
    document.all('ContNo').value = tContNo;
    document.all('MissionID').value = tMissionID;
    document.all('SubMissionID').value = tSubMission ;
    document.all('PrtNo').value = tPrtNo ;
    document.all('Flag').value = tFlag ;
    document.all('BatchNo').value = tBatchNo ;
    document.all('RgtNo').value = tRgtNo ;
}

// 体检信息列表的初始化
function initPEGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="体检人";         		//列名
      iArray[1][1]="40px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="体检人类型";         		//列名
      iArray[2][1]="40px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="状态";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="录入人员";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="打印流水号";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="打印标记";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
  
      iArray[7]=new Array();
      iArray[7][0]="客户号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="客户号类型";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="体检项目";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="录入日期";         		//列名
      iArray[10][1]="50px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      PEGrid = new MulLineEnter( "fm" , "PEGrid" ); 
      //这些属性必须在loadMulLine前
      PEGrid.mulLineCount = 0;   
      PEGrid.displayTitle = 1;
      PEGrid.locked = 1;
      PEGrid.canSel = 1;
      PEGrid.canChk = 0;
      PEGrid.hiddenPlus = 1;
      PEGrid.hiddenSubtraction = 1;
      PEGrid.loadMulLine(iArray);  
      
      PEGrid. selBoxEventFuncName = "easyQueryAddClick";
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
