<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：UWAutoInit.jsp
	//程序功能：个人自动核保
	//创建日期：2002-06-19 11:10:36
	//创建人  ：WHN
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//添加页面控件的初始化。
%>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                                   
	// 保单查询条件
    document.all('ManageCom').value = '';
    document.all('ManageComName').value = '';
    document.all('ContNo').value = '';    
    document.all('PrtNo').value = '';    
    
    document.all('RiskCode').value = '';
    document.all('RiskCodeName').value = '';
    document.all('AgentCode').value = '';
    
    document.all('SecPayMode').value = '';
    document.all('PayModeName').value = '';
        
    document.all('ContType').value = '';
    document.all('ContTypeName').value = '';
  }
  catch(ex)
  {
    alert("在ProposalQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在ProposalQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();  
    initContGrid();  
	  initPolGrid();
	  if(DealType=='ZC')
	  {
	  	divZC.style.display = '';
      divGQ.style.display = 'none';
	  }
	  else
	  {
	  	divZC.style.display = 'none';
      divGQ.style.display = '';
	  }
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initContGrid()
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
      iArray[1][0]="保单号码";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[2]=new Array();
      iArray[2][0]="印刷号码";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[3]=new Array();
	  iArray[3][0]="投保人";         		//列名
	  iArray[3][1]="160px";            		//列宽
	  iArray[3][2]=100;            			//列最大值
	  iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 
      //这些属性必须在loadMulLine前
      ContGrid.mulLineCount = 5;   
      ContGrid.displayTitle = 1;
      ContGrid.locked = 1;
      ContGrid.canSel = 1;
      ContGrid.canChk = 0;
      ContGrid.loadMulLine(iArray);  
      ContGrid. selBoxEventFuncName = "easyQueryAddClick";
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initPolGrid()
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
      iArray[1][0]="险种保单号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2; 
      iArray[3][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[3][5]="6";              	                //引用代码对应第几列，'|'为分割符
      iArray[3][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[3][18]=250;
      iArray[3][19]= 0 ;

      iArray[4]=new Array();
      iArray[4][0]="险种版本";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="币种";      	   		//列名
      iArray[5][1]="80px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="保费";         		    //列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=7;              			//是否允许输入,1表示允许，0表示不允许
	  iArray[6][22]="col5";
	  iArray[6][23]=1;
      
      iArray[7]=new Array();
      iArray[7][0]="投保人";         		//列名
      iArray[7][1]="120px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="被保人";         		//列名
      iArray[8][1]="120px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="交至日期";         		//列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 0;
      PolGrid.canChk = 0;
      PolGrid.loadMulLine(iArray);  
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
