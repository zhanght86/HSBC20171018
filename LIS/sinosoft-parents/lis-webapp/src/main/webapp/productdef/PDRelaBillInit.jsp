<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDRelaBillInit.jsp
  //程序功能：责任给付账单关联
  //创建日期：2009-3-16
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

var tResourceName = "productdef.PDRelaBillInputSql";
function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		fm.all("GETDUTYCODE").value = "<%=request.getParameter("GetDutyCode")%>";
		fm.all("GETDUTYKIND").value = "<%=request.getParameter("GetDutyKind")%>";
	var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRelaBillInputSql5";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("GETDUTYCODE").value);//指定传入的参数
	  mySql.addSubPara(fm.all("GETDUTYKIND").value);//指定传入的参数
   strSQL = mySql.getString();
    var arrInfo = easyExecSql(strSQL);
   if(arrInfo.length>0)
   {
   		fm.all("GETDUTYNAME").value = arrInfo[0][0];
   }
		
		initMulline10Grid();
		queryMulline10Grid();
		
		//initMulline8Grid();
		//initMulline9Grid();
				
		//queryMulline9Grid();
		
		//queryMulline8Grid();

		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDRelaBillInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}

function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;


		iArray[1]=new Array();
		iArray[1][0]="给付代码";
		iArray[1][1]="90px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="给付名称";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="理赔类型";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="账单项目编码";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="账单项目名称";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="费用计算方式";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="费用明细计算公式";
		iArray[7][1]="90px";
		iArray[7][2]=100;
		iArray[7][3]=0;
		

		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 
           
           
		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
    	Mulline10Grid.selBoxEventFuncName ="getOneData";
           
		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initMulline8Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="账单项目编码";
		iArray[1][1]="20px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="账单项目名称";
		iArray[2][1]="20px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		Mulline8Grid = new MulLineEnter( "fm" , "Mulline8Grid" ); 
           
		Mulline8Grid.mulLineCount=0;
		Mulline8Grid.displayTitle=1;
		Mulline8Grid.canSel=0
		Mulline8Grid.canChk=1;
		Mulline8Grid.hiddenPlus=1;
		Mulline8Grid.hiddenSubtraction=1;
           
		Mulline8Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="账单项目编码";
		iArray[1][1]="20px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="账单项目名称";
		iArray[2][1]="20px";
		iArray[2][2]=100;
		iArray[2][3]=0;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 
           
		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=0;
		Mulline9Grid.canChk=1;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
           
		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function updateDisplayState()
{
	var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRelaBillInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara("0");//指定传入的参数
   strSQL = mySql.getString();
    //tongmeng 2011-07-13 modify
   // 
   var arrInfo = easyExecSql(strSQL);
   if(arrInfo.length>0)
   {
   	for(i=0;i<arrInfo.length;i++)
   	{
   		try{
   		var tFieldName = arrInfo[i][1];
   		var tTitle = arrInfo[i][4];
   		if(tTitle!=null&&tTitle!='')
   		{
   			// tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').setAttribute('title',\"" + newData + "\")";
   			document.getElementById(tFieldName).title = tTitle;
   		}
   		}
   		catch(Ex)
   		{
   			}
   	}
   }

}
</script>
