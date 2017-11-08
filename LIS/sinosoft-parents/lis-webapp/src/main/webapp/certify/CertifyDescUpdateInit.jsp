<%
//Creator :刘岩松
//Date :2003-04-18
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initCardRiskGrid();
    initCertifyDescGrid();
    initCertifyDescTraceGrid();
  }
  catch(re)
  {
    alert("CertifyDescInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{
  try
  {       
    document.all('CertifyCode').value = '';
    document.all('CertifyName').value = '';
    document.all('State').value = '';
    
    document.all('HaveLimit').value = '';
    document.all('MaxUnit1').value = '';
    document.all('MaxUnit2').value = '';
    
    document.all('HaveValidate').value = '';
    document.all('Validate1').value = '';
    document.all('Validate2').value = '';
    document.all('Note').value = '';
  }
  catch(ex)
  {
    alert("进行初始化是出现错误！！！！");
  }
}

function RegisterDetailClick(cObj)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	divDetailInfo.style.left=ex;
  	divDetailInfo.style.top =ey;
    divDetailInfo.style.display ='';
}

// 下拉框的初始化
function initSelBox()
{
  try {}
  catch(ex)
  {
    alert("2在CertifyDescInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initCardRiskGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="险种代码";
    iArray[1][1]="100px";
    iArray[1][2]=100;
    iArray[1][3]=2;
    iArray[1][4]="RiskCode";

    iArray[2]=new Array();
    iArray[2][0]="保费";         			//列名
    iArray[2][1]="100px";            		//列宽
    iArray[2][2]=200;            			//列最大值
    iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="保费特性";         	//列名
    iArray[3][1]="100px";            	//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=2;              			//2表示是代码选择
    iArray[3][10]="PremProp";					//保费特性
    iArray[3][11]="0|^1|固定保费|^2|保费倍数|^3|任意保费";

    iArray[4]=new Array();
    iArray[4][0]="保费份额";         			//列名
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

    CardRiskGrid = new MulLineEnter( "document" , "CardRiskGrid" );
    CardRiskGrid.mulLineCount = 5;
    CardRiskGrid.displayTitle = 1;
	CardRiskGrid.canSel = 1;
    CardRiskGrid.loadMulLine(iArray);
    CardRiskGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    alert("初始化时出错2003-05-21"+ex);
  }
}

function initCertifyDescGrid()
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
      iArray[1][0]="单证编码";    	//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="单证名称";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[3]=new Array();
      iArray[3][0]="单证类型";         			//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="单证业务类型";         			//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="是否有价";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[6]=new Array();
      iArray[6][0]="是否有号";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="状态";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="定义日期";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      CertifyDescGrid = new MulLineEnter( "document" , "CertifyDescGrid" ); 
      CertifyDescGrid.mulLineCount = 5;   
      CertifyDescGrid.displayTitle = 1;
	  CertifyDescGrid.hiddenSubtraction=1;
	  CertifyDescGrid.hiddenPlus=1;
      CertifyDescGrid.canSel=1;
      CertifyDescGrid.locked = 1;	
      CertifyDescGrid.selBoxEventFuncName = "showDetail";
      CertifyDescGrid.loadMulLine(iArray);  
      CertifyDescGrid.detailInfo="单击显示详细信息";
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initCertifyDescTraceGrid()
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
      iArray[1][0]="单证编码";    	//列名
      iArray[1][1]="50px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="单证名称";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[3]=new Array();
      iArray[3][0]="状态";         			//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="是否限量领用";         			//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="个人代理人最大领用数";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[6]=new Array();
      iArray[6][0]="非个人代理人最大领用数";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="是否有使用期";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="个人代理人使用期";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="非个人代理人使用期";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[10]=new Array();
      iArray[10][0]="注释";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
           
      iArray[11]=new Array();
      iArray[11][0]="定义日期";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
            
      CertifyDescTraceGrid = new MulLineEnter( "document" , "CertifyDescTraceGrid" ); 
      CertifyDescTraceGrid.mulLineCount = 5;   
      CertifyDescTraceGrid.displayTitle = 1;
	  CertifyDescTraceGrid.hiddenSubtraction=1;
	  CertifyDescTraceGrid.hiddenPlus=1;
      CertifyDescTraceGrid.canSel=1;
      CertifyDescTraceGrid.locked = 1;
      CertifyDescTraceGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}
</script>


