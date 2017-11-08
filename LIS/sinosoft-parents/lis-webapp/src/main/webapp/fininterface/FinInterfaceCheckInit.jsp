<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<% 
//程序名称：FinInterfaceCheckInit.jsp
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

function initInpBox()
{
  try
  {
	//查询条件置空
	document.all('checkType').value = '';
	document.all('checkTypeName').value = '';
	document.all('StartDay').value = '';
	document.all('EndDay').value = '';
  }
  catch(ex)
  {
    alert("TestInterfaceNotRunInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                            

function initForm()
{
  try
  {
    initInpBox();
    initCodeQuery();
    initCheckQueryDataGrid();  //初始化共享工作池
  }
  catch(re)
  {
    alert("TestInterfaceNotRunInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCodeQuery(){
	
		var strQueryResultL = easyQueryVer3("select distinct codealias,Codename from FICodeTrans where codetype = 'BigCertificateID' order by to_number(substr(codealias,2))",1,1,1);
		var	tArrayL = decodeEasyQueryResult(strQueryResultL);
		var tDS = "0|^";
		for(i=0;i<tArrayL.length;i++){
			tDS =tDS + tArrayL[i][0] + "|" + tArrayL[i][1] + "|M";
			if((i+1)!=tArrayL.length){
				tDS = tDS + "^";
			}
		}
		document.all("SClassType").CodeData = tDS;
}

function initCheckQueryDataGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="38px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="批次号";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="机构";         		//列名
      iArray[2][1]="65px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="借贷标志";         		//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="科目信息";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="业务信息";         		//列名
      iArray[5][1]="120px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[6]=new Array();
      iArray[6][0]="险种";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="渠道";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="业务日期";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="保单号码";         		//列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="明细";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[11]=new Array();
      iArray[11][0]="预算项";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许        
      
      
      iArray[12]=new Array();
      iArray[12][0]="成本中心";         		//列名
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许         
      
      iArray[13]=new Array();
      iArray[13][0]="金额";         		//列名
      iArray[13][1]="80px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[14]=new Array();
      iArray[14][0]="凭证类型";         		//列名
      iArray[14][1]="60px";            		//列宽
      iArray[14][2]=100;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[15]=new Array();
      iArray[15][0]="号码类型";         		//列名
      iArray[15][1]="60px";            		//列宽
      iArray[15][2]=100;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[16]=new Array();
      iArray[16][0]="业务号码";         		//列名
      iArray[16][1]="120px";            		//列宽
      iArray[16][2]=100;            			//列最大值
      iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
      CheckQueryDataGrid = new MulLineEnter( "document" , "CheckQueryDataGrid" ); 
      //这些属性必须在loadMulLine前
      CheckQueryDataGrid.mulLineCount = 10;   
      CheckQueryDataGrid.displayTitle = 1;
      CheckQueryDataGrid.locked = 1;
      CheckQueryDataGrid.canSel = 1;
      CheckQueryDataGrid.canChk = 1;
      //FinDayTestGrid.hiddenPlus = 1;
      //FinDayTestGrid.hiddenSubtraction = 1;        
      CheckQueryDataGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
