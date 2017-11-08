<%@include file="/i18n/language.jsp"%>
 <%
//创建日期：2008-08-18
//创建人  ：范昕  
%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>


<%
	GlobalInput globalInput = (GlobalInput)session.getAttribute("GI");
	
	if(globalInput == null) 
	{
		out.println(""+"网页超时，请重新登录"+"");
		return;
	}
	String strOperator = globalInput.Operator;
	String tArithmeticDefID = request.getParameter("ArithmeticID");
%>  
                       
<script type="text/javascript">


function initForm()
{
	try
	{
		fm.ArithmeticDefID.value = <%=tArithmeticDefID%>
		fm.NEWArithmeticDefID.value = <%=tArithmeticDefID%>
		//initCalItemTypeGrid();
		initKResultGrid();
		//var strSQL="select distinct(a.arithmeticid),a.arithmeticdefid,(select l.codename from ldcode l where l.code=a.ArithmeticType and l.codetype='Arithmetic'),a.ArithmeticType from riitemcal a  where 1=1 "
		//	+getWherePart('arithmeticdefid','ArithmeticDefID');
		//turnPage1.queryModal(strSQL,CalItemTypeGrid);
	}
	catch(re)
	{
	  myAlert("在RIItemCalInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}
/*
function initCalItemTypeGrid()
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
      iArray[1][0]="明细算法编码";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[2]=new Array();
      iArray[2][0]="算法编码";      	   		//列名
      iArray[2][1]="50px";            			//列宽
      iArray[2][2]=10;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[3]=new Array();
      iArray[3][0]="算法类型名称";      	   		//列名
      iArray[3][1]="50px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="算法类型编码";      	   		//列名
      iArray[4][1]="50px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
                 
      iArray[5]=new Array();
      iArray[5][0]="业务类型编码";      	   		//列名
      iArray[5][1]="50px";            			//列宽
      iArray[5][2]=10;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      
      CalItemTypeGrid = new MulLineEnter( "fm" , "CalItemTypeGrid" ); 
      //这些属性必须在loadMulLine前
      //ResultGrid.mulLineCount = 1;   
      CalItemTypeGrid.displayTitle = 1;
      CalItemTypeGrid.locked = 1;
      CalItemTypeGrid.canSel = 1;
      CalItemTypeGrid.canChk = 0;
      CalItemTypeGrid.hiddenSubtraction = 1;
      CalItemTypeGrid.hiddenPlus = 1;
      CalItemTypeGrid.loadMulLine(iArray);  
      CalItemTypeGrid.selBoxEventFuncName ="getDetail"; //响应的函数名，不加扩号        
      

	}
	catch(ex)
	{
		alert(ex);
	}
}
*/
function initKResultGrid()
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
      iArray[1][0]="算法编码";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="明细算法编码";      	   		//列名
      iArray[2][1]="60px";            			//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[3]=new Array();
      iArray[3][0]="明细算法名称";      	   		//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[4]=new Array();
      iArray[4][0]="明细算法类型";      	   		//列名
      iArray[4][1]="50px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[5]=new Array();
      iArray[5][0]="业务类型";      	   		//列名
      iArray[5][1]="50px";            			//列宽
      iArray[5][2]=10;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="计算项名称";      	   		//列名
      iArray[6][1]="50px";            			//列宽
      iArray[6][2]=10;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="算法配置";      	   		//列名
      iArray[7][1]="100px";            			//列宽
      iArray[7][2]=10;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[8]=new Array();
      iArray[8][0]="算法描述";      	   		//列名
      iArray[8][1]="100px";            			//列宽
      iArray[8][2]=10;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="算法类型";      	   		//列名
      iArray[9][1]="100px";            			//列宽
      iArray[9][2]=10;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="计算项次序";      	   		//列名
      iArray[10][1]="100px";            			//列宽
      iArray[10][2]=10;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="计算项编码";      	   		//列名
      iArray[11][1]="50px";            			//列宽
      iArray[11][2]=10;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      KResultGrid = new MulLineEnter( "fm" , "KResultGrid" ); 
      //这些属性必须在loadMulLine前
      //ResultGrid.mulLineCount = 1;   
      KResultGrid.displayTitle = 1;
      KResultGrid.locked = 1;
      KResultGrid.canSel = 1;
      KResultGrid.canChk = 0;
      KResultGrid.hiddenSubtraction = 1;
      KResultGrid.hiddenPlus = 1;
      KResultGrid.selBoxEventFuncName ="queryClick";
      KResultGrid.loadMulLine(iArray);    

	}
	catch(ex)
	{
		myAlert(ex);
	}
}
</script>
