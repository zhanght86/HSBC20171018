<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDAlgoDefiInit.jsp
  //程序功能：算法定义界面
  //创建日期：2009-3-17
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = '<%=request.getParameter("riskcode")%>';
		fm.all("mRiskCode").value = '<%=request.getParameter("riskcode")%>';
		
		if(fm.all("RiskCode").value == null || fm.all("RiskCode").value == "")
		{
			fm.all("RiskCode").value = '';
		}
		
		fm.all("IsReadOnly").value = '<%=session.getAttribute("IsReadOnly")%>';	
		
		fm.all("AlgoCode").value = '<%=request.getParameter("AlgoCode")%>';	
		
		if(fm.all("AlgoCode").value != "null" && fm.all("AlgoCode").value != "")
		{
			var sql = "select calcode,type,Remark,Calsql from Pd_Lmcalmode where calcode = '" + fm.all("AlgoCode").value +"'";
			var arr = easyExecSql(sql);
			
			if(arr != null)
			{
				fm.all("AlgoCode").value = arr[0][0];
				fm.all("AlgoType").value = arr[0][1];
				fm.all("AlgoDesc").value = arr[0][2];
				fm.all("AlgoContent").value = arr[0][3]; 
			}
		}
		else
		{
			fm.all("AlgoCode").value = ""; 
		}
		initMulline9Grid();
		queryMulline9Grid();
		initMulline10Grid();
		initMulline11Grid();
		queryMulline11Grid();
		var tpdflag = <%=request.getParameter("pdflag")%>;
		if(tpdflag==0){
			document.getElementById("modifyButton1").style.display = 'none';
			document.getElementById("modifyButton2").style.display = 'none';
			document.getElementById("modifyButton3").style.display = 'none';
		}
		
	}
	catch(re){
		myAlert("PDAlgoDefiInit.jsp-->"+"初始化界面错误!");
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
		iArray[1][0]="算法编码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种编码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="算法模板类型";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="算法内容";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="算法描述";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="算法类型名称";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=3;
		
		iArray[7]=new Array();
		iArray[7][0]="父算法编码";
		iArray[7][1]="60px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		Mulline9Grid.selBoxEventFuncName = "afterSel";

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function afterSel()
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo > 0){
		fm.all("AlgoCode").value = Mulline9Grid.getRowColData(selNo-1,1);
		fm.all("AlgoType").value = Mulline9Grid.getRowColData(selNo-1,3);
		fm.all("AlgoDesc").value = Mulline9Grid.getRowColData(selNo-1,5);
		fm.all("AlgoContent").value = Mulline9Grid.getRowColData(selNo-1,4).replace(new RegExp('\"','gm'),'\'');
		fm.all("AlgoTypeName").value = Mulline9Grid.getRowColData(selNo-1,6);
		var AclgCalCode = Mulline9Grid.getRowColData(selNo-1,7);
		if(fm.all("AlgoType").value=="F"){
			var sql = "select a.calcode,b.remark,a.FACTORGRADE from PD_LMCalFactor a,PD_LMCalMode b where a.calcode=b.calcode and  factorcode='"+fm.all("AlgoCode").value+"' "; 
	    	//-------- add by jucy
	    	sql += " and a.calcode='"+AclgCalCode+"'"
	    	//-------- end
	    	var arr = easyExecSql(sql);
			if(arr != null){
				fm.MainAlgoCode.value=arr[0][0];
				fm.MainAlgoName.value=arr[0][1];
				fm.SubAlgoGrade.value=arr[0][2];
			}
	  		fm.all("subAlgoDiv").style.display = '';
		}else{
			//-------- add by jucy
			var sql = "select a.calcode,b.remark,a.FACTORGRADE from PD_LMCalFactor a,PD_LMCalMode b where a.calcode=b.calcode and  factorcode='"+fm.all("AlgoCode").value+"' "; 
	    	sql += " and a.calcode='"+AclgCalCode+"'"
	    	var arr = easyExecSql(sql);
			if(arr != null){
				fm.MainAlgoCode.value=arr[0][0];
				fm.MainAlgoName.value=arr[0][1];
				fm.SubAlgoGrade.value=arr[0][2];
	  			fm.all("subAlgoDiv").style.display = '';
			}else{
				fm.all("subAlgoDiv").style.display = 'none';
			}
		}
	}
	queryMulline11Grid();
}

function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="要素类别";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="要素名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="要素代码";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="测试值";
		iArray[4][1]="45px";
		iArray[4][2]=100;
		iArray[4][3]=1;

		iArray[5]=new Array();
		iArray[5][0]="要素值数据类型";
		iArray[5][1]="105px";
		iArray[5][2]=100;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="要素含义说明";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initMulline11Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="子算法编码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="子算法名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;


		iArray[3]=new Array();
		iArray[3][0]="子算法优先级";
		iArray[3][1]="45px";
		iArray[3][2]=100;
		iArray[3][3]=1;


		Mulline11Grid = new MulLineEnter( "fm" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=0;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=1;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;
		Mulline11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
