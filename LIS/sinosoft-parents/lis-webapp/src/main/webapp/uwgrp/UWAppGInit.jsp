<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWAppGInit.jsp
//程序功能：既往投保信息查询
//创建日期：2002-06-19 11:10:36
//创建人  ： WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tProposalNo = "";
  String tInsureNo = "";
  String tContNo = "";
  tProposalNo = request.getParameter("ProposalNo2");
  tInsureNo = request.getParameter("InsureNo2");
  tContNo  = request.getParameter("ContNo");
  loggerDebug("UWAppGInit","ContNo:"+tContNo);
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

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
    alert("在UWAppGInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tProposalNo,tInsureNo,tContNo)
{
  try
  {
  	
	initPolGrid();
	initHide(tProposalNo,tInsureNo,tContNo);
	easyQueryClick();
  }
  catch(re)
  {
    alert("UWAppGInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re);
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
      iArray[1][0]="保单号";    	//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[2]=new Array();
      iArray[2][0]="投保单号";         			//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="印刷号";         			//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保单生效日期";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="险种名称";         		//列名
      iArray[5][1]="260px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      //iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      //iArray[5][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      //iArray[5][5]="4";              	                //引用代码对应第几列，'|'为分割符
      //iArray[5][9]="险种编码|code:RiskCode&NOTNULL";
      //iArray[5][18]=250;
      //iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="保费";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="保额";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="保单状态";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][4]="PolState";              	        //是否引用代码:null||""为不引用
      iArray[8][5]="11";              	                //引用代码对应第几列，'|'为分割符
      iArray[8][9]="保单状态|code:PolState&NOTNULL";
      iArray[8][18]=150;
      iArray[8][19]= 0 ;  

      iArray[9]=new Array();
      iArray[9][0]="职业类别";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[9][4]="OccupationType";              	        //是否引用代码:null||""为不引用
      iArray[9][5]="9";              	                //引用代码对应第几列，'|'为分割符
      iArray[9][9]="职业类别|code:OccupationType&NOTNULL";
      iArray[9][18]=100;
      iArray[9][19]= 0 ;

      iArray[10]=new Array();
      iArray[10][0]="是否体检件";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[10][10] = "Health";
      iArray[10][11] = "0|^0|没有体检件^1|有体检件,未打印^2|有体检件,已打印,但未回复^3|有体检件,已打印,已未回复";
      iArray[10][12] = "10";
      iArray[10][19] = "0";

      iArray[11]=new Array();
      iArray[11][0]="是否条件承保";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[11][10] = "Condition";
      iArray[11][11] = "0|^0|没有条件承保^1|有条件承保,未回复^2|有条件承保,已回复";
      iArray[11][12] = "11";
      iArray[11][19] = "0";
      

      iArray[12]=new Array();
      iArray[12][0]="核保结论";         		//列名
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[12][10] = "End";
      iArray[12][11] = "0|^0|未核保^1|拒保^2|延期^3|条件承保^4|通融承保^5|自动核保^6|待上级核保^8|发核保通知书^9|正常承保^a|撤销投保单^b|保险计划变更^z|核保订正";
      iArray[12][12] = "12";
      iArray[12][19] = "0";

      iArray[13]=new Array();
      iArray[13][0]="理赔次数";         		//列名
      iArray[13][1]="60px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 10;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.loadMulLine(iArray);
      
      PolGrid. selBoxEventFuncName = "ChoClick";
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tProposalNo,tInsureNo,tContNo)
{
	fm.all('ContNo').value= tContNo;
	fm.all('ProposalNoHide').value= tProposalNo;
	fm.all('InsureNoHide').value= tInsureNo;
	//alert("pol:"+tProposalNo);
}

</script>
