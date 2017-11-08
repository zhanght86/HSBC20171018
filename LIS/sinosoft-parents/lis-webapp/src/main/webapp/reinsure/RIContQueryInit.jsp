<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2006-08-20
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

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
    myAlert("进行初始化是出现错误！！！！");
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
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
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
	  iArray[2][1]="200px";         
	  iArray[2][2]=100;            	
	  iArray[2][3]=0;              	

	  iArray[3]=new Array();
	  iArray[3][0]="合同类型编码";      
	  iArray[3][1]="0px";         
	  iArray[3][2]=100;            	
	  iArray[3][3]=3;   

	  iArray[4]=new Array();
	  iArray[4][0]="合同类型";      
	  iArray[4][1]="100px";         
	  iArray[4][2]=100;            	
	  iArray[4][3]=0;   
	  	            	
	  
	  iArray[5]=new Array();
	  iArray[5][0]="签订日期";      
	  iArray[5][1]="100px";          
	  iArray[5][2]=60;            	
	  iArray[5][3]=0;   
	  
	  iArray[6]=new Array();
	  iArray[6][0]="合同生效日期"; 	
	  iArray[6][1]="100px";         
	  iArray[6][2]=1000;            
	  iArray[6][3]=0;              	
	  
	  iArray[7]=new Array();
	  iArray[7][0]="合同终止日期";  
	  iArray[7][1]="100px";         
	  iArray[7][2]=1000;            
	  iArray[7][3]=0;              	
		
	  iArray[8]=new Array();
	  iArray[8][0]="合同状态编码"; 
	  iArray[8][1]="0px";         
	  iArray[8][2]=1000;            
	  iArray[8][3]=3;              	
	  
	  iArray[9]=new Array();
	  iArray[9][0]="合同状态";
	  iArray[9][1]="100px";         
	  iArray[9][2]=1000;            
	  iArray[9][3]=0;    

	  iArray[10]=new Array();
	  iArray[10][0]="账单周期编码";
	  iArray[10][1]="20px";         
	  iArray[10][2]=30;            
	  iArray[10][3]=3; 

	  iArray[11]=new Array();
	  iArray[11][0]="账单周期";
	  iArray[11][1]="80px";         
	  iArray[11][2]=50;            
	  iArray[11][3]=0; 
	  	  		
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