<%
//程序名称：SubContPolInput.jsp
//程序功能：
//创建日期：2002-08-15 11:48:43
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	String tGrpContNo = "";
	String tPrtNo = "";
	tGrpContNo = request.getParameter("GrpContNo");
	tPrtNo = request.getParameter("PrtNo");
	loggerDebug("SubContPolInit","grp:" + tGrpContNo);
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
}

function initSelBox()
{  
}                                        

function initForm(tGrpContNo, tPrtNo)
{
  try
  {
    initInpBox();
    initSelBox();    
	getGrpCont(tGrpContNo, tPrtNo);
	initGeneralGrid();
	queryGeneralInfo();
	fm.ExecuteCom.value = tManageCom;
	//fm.all("addbutton").style.display = "none";
	//fm.all("modibutton").style.display = "none";
	//fm.all("delbutton").style.display = "none";
  if(LoadFlag=="4" || LoadFlag=="16")
	  {
	    fm.GrpContNo.value=GrpContNo;
	    //隐藏操作按钮
	    divSeperateSave.style.display="none";
	  }
	  if(LoadFlag=="13"){
	    fm.GrpContNo.value=GrpContNo;
	  }
  }
  catch(re)
  {
    alert("SubContPolInputInit.jsp-->InitForm1函数中发生异常:初始化界面错误!");
  }
}

function initGeneralGrid()
{                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="处理机构";          		//列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=2;                      //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[1][4]="comcode";             //是否引用代码:null||""为不引用
      iArray[1][5]="1";              	   //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0";              	   //上面的列中放置引用代码中第几位值

      iArray[2]=new Array();
      iArray[2][0]="管理机构";         			//列名
      iArray[2][1]="60px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="comcode";             //是否引用代码:null||""为不引用
      iArray[2][5]="1";              	   //引用代码对应第几列，'|'为分割符
      iArray[2][6]="0";              	   //上面的列中放置引用代码中第几位值

      iArray[3]=new Array();
      iArray[3][0]="单位名称";      	   		//列名
      iArray[3][1]="100px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=1; 

      iArray[4]=new Array();
      iArray[4][0]="地址";      	   		//列名
      iArray[4][1]="160px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="操作员";      	   		//列名
      iArray[5][1]="40px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="客户号码";      	   		//列名
      iArray[6][1]="0px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0; 

      GeneralGrid = new MulLineEnter( "fm" , "GeneralGrid" ); 
      //这些属性必须在loadMulLine前
      GeneralGrid.mulLineCount = 0;   
      GeneralGrid.displayTitle = 1;
      GeneralGrid.canSel =1;
      GeneralGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      GeneralGrid. hiddenSubtraction=1;
	  GeneralGrid. selBoxEventFuncName = "onGeneralSelected";

      GeneralGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}


function getGrpCont(tGrpContNo, tPrtNo){

//	fm.all( 'PrtNo' ).value = "200411240001";
//	fm.all( 'GrpContNo' ).value = "86110020040990000041";
//	fm.all( 'ProposalGrpContNo' ).value = "86110020040990000041";
//    try { fm.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
//    try { fm.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
//    try { fm.all( 'ProposalGrpContNo' ).value = mSwitch.getVar( "ProposalGrpContNo" ); } catch(ex) { };
    try { fm.all( 'GrpContNo' ).value = tGrpContNo; } catch(ex) { };
    try { fm.all( 'PrtNo' ).value = tPrtNo; } catch(ex) { };

}
</script>
