<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2006-08-20
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 String pageFlag = request.getParameter("PageFlag"); 
 String reContCode = request.getParameter("ReContCode"); 
 System.out.println("pageFlag: "+pageFlag);
 System.out.println("ql_ReContCode: "+reContCode);
%>
<script type="text/javascript">
function initInpBox()
{
  try
  { 
  	fm.ReContCode1.value = "<%=reContCode%>";
  	fm.PageFlag.value = "<%=pageFlag%>";
  }
  catch(ex)
  {
    myAlert("初始化界面错误!");
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
    myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initReContGrid();
    
    submitForm();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"初始化界面错误!");
  }
}

function initReContGrid()
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
	      iArray[1][0]="合同编号";    	
	      iArray[1][1]="120px";         
	      iArray[1][2]=100;            	
	      iArray[1][3]=0;              	
				
				iArray[2]=new Array();
	      iArray[2][0]="合同名称";
	      iArray[2][1]="120px";         
	      iArray[2][2]=100;            	
	      iArray[2][3]=0;      
				
	      iArray[3]=new Array();
	      iArray[3][0]="保单类型";      
	      iArray[3][1]="100px";         
	      iArray[3][2]=100;            	
	      iArray[3][3]=3;    
	      
	      iArray[4]=new Array();
	      iArray[4][0]="合同类型名称";      
	      iArray[4][1]="100px";         
	      iArray[4][2]=100;            	
	      iArray[4][3]=3;              	
	 
	      iArray[5]=new Array();
	      iArray[5][0]="合同险类类型";  
	      iArray[5][1]="100px";          
	      iArray[5][2]=60;            	
	      iArray[5][3]=3;              	
	      
	      iArray[6]=new Array();
	      iArray[6][0]="合同险类类型名称";  
	      iArray[6][1]="60px";          
	      iArray[6][2]=60;            	
	      iArray[6][3]=3;       
	      
	      iArray[7]=new Array();
	      iArray[7][0]="分保类型";      
	      iArray[7][1]="100px";          
	      iArray[7][2]=60;            	
	      iArray[7][3]=3;              	
	      
	      iArray[8]=new Array();
	      iArray[8][0]="分保类型名称";      
	      iArray[8][1]="100px";          
	      iArray[8][2]=60;            	
	      iArray[8][3]=1;   
	      
	      iArray[9]=new Array();
	      iArray[9][0]="合同生效日期"; 	
	      iArray[9][1]="100px";         
	      iArray[9][2]=1000;            
	      iArray[9][3]=0;              	
	      
	      iArray[10]=new Array();
	      iArray[10][0]="合同终止日期";  
	      iArray[10][1]="100px";         
	      iArray[10][2]=1000;            
	      iArray[10][3]=0;              	
				
	      ReContGrid = new MulLineEnter( "fm" , "ReContGrid" ); 
	      ReContGrid.mulLineCount = 0;   
	      ReContGrid.displayTitle = 1;
	      ReContGrid.canSel=1;
	      ReContGrid.locked = 1;	
				ReContGrid.hiddenPlus = 1;
				ReContGrid.hiddenSubtraction = 1;
	      ReContGrid.loadMulLine(iArray);  
	      ReContGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>