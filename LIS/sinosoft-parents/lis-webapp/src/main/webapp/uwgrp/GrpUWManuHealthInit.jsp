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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
//  String tMissionID = "";
//  String tSubMissionID = "";
  String tFlag = "";
  String tUWIdea = "";  
  String tPrtNo = "";
  String tGrpContNo = "";
  Date today = new Date();
  today = PubFun.calDate(today,15,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  tContNo = request.getParameter("ContNo2");  
//    tMissionID = request.getParameter("MissionID");
//  tSubMissionID = request.getParameter("SubMissionID");
  tPrtNo = request.getParameter("PrtNo");
  tGrpContNo = request.getParameter("GrpContNo");
 %>                            

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {   
                               
    fm.all('ContNo').value = '';
/*    fm.all('MissionID').value = '';
    fm.all('SubMissionID').value = '';*/
    fm.all('EDate').value = '<%=tday%>';
    fm.all('PrintFlag').value = '';
    fm.all('Hospital').value = '';
    fm.all('IfEmpty').value = '';
    fm.all('InsureNo').value = '';
    fm.all('Note').value = '';
	fm.all('bodyCheck').value = '';
  }
  catch(ex)
  {
    alert("在GrpUWManuHealthInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在GrpUWSubInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tPrtNo,tGrpContNo)
{
  try
  {
	initInpBox();
	//initUWHealthGrid();
	initHide(tContNo,tPrtNo,tGrpContNo);
	initHospital(tContNo);
	initInsureNo(tPrtNo);
	easyQueryClickSingle();	
	
	//easyQueryClick();
 }
  catch(re) {
    alert("GrpUWManuHealthInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[2][1]="140px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许



      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //这些属性必须在loadMulLine前                            
      HealthGrid.mulLineCount = 0;
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

function initHide(tContNo,tPrtNo,tGrpContNo)
{
	fm.all('ContNo').value = tContNo;
//    fm.all('MissionID').value = tMissionID;
//	fm.all('SubMissionID').value = tSubMission ;
   	fm.all('PrtNo').value = tPrtNo ;
   	fm.all('GrpContNo').value = tGrpContNo;
	
}

</script>


