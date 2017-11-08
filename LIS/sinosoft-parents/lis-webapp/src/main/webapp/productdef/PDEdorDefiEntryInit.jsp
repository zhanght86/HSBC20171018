<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDEdorDefiEntryInit.jsp
  //程序功能：保全信息定义
  //创建日期：2009-3-16
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

//接收保全信息页面传递过来的参数
function initParam()
{
  		fm.RiskCode.value = "<%=request.getParameter("riskcode")%>";
		fm.RequDate.value = "<%=request.getParameter("requdate")%>";
		fm.RiskCode1.value = "<%=request.getParameter("riskcode")%>";

		
		var getRiskName = easyExecSql("select riskname from pd_lmrisk where riskcode='"+fm.all("RiskCode").value+"' and rownum<2");
		if(getRiskName){
			fm.all("RiskName").value=getRiskName[0][0];
		}
		
		fm.all("PdFlag").value = "<%=request.getParameter("pdflag")%>";
		fm.all("MissionID").value = "<%=request.getParameter("missionid")%>";
		fm.all("SubMissionID").value = "<%=request.getParameter("submissionid")%>";
		fm.all("ActivityID").value = "<%=request.getParameter("activityid")%>";
    	fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		var riskCode = "<%=request.getParameter("riskcode")%>";
}

function initForm() 
{
	lockPage("正在加载数据，请您稍候.");
	try{
		
		initParam();
		initMulline10Grid();
		queryMulline10Grid();
		initMulline9Grid();
		//queryMulline9Grid();
		initEdorItem();
		initMulline11Grid();
		queryMulline11Grid();
		
		//showButtons(riskCode);
		getEdorItem();
		isshowbutton();
	}
	catch(re){
		myAlert("PDEdorDefiEntryInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
	unLockPage();
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
		iArray[1][0]="险种编码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="保全项目编码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="保全项目名称";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.selBoxEventFuncName="getEdorItem"; 
		Mulline10Grid.loadMulLine(iArray);

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
		iArray[1][0]="[*]保全项目编码";
		iArray[1][1]="40px";
		iArray[1][2]=6;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="责任代码";
		iArray[2][1]="40px";
		iArray[2][2]=10;
		iArray[2][3]=0;

		
		iArray[3]=new Array();
		iArray[3][0]="保全算法类型";
		iArray[3][1]="0px";
		iArray[3][2]=6;
		iArray[3][3]=3;
		
		iArray[4]=new Array();
		iArray[4][0]="保全算法类型";
		iArray[4][1]="40px";
		iArray[4][2]=10;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="算法编码";
		iArray[5][1]="50px";
		iArray[5][2]=6;
		iArray[5][3]=1;
		
		iArray[6]=new Array();
		iArray[6][0]="算法编码";
		iArray[6][1]="0px";
		iArray[6][2]=6;
		iArray[6][3]=3;
		
		iArray[7]=new Array();
		iArray[7][0]="现金价值标记";
		iArray[7][1]="0px";
		iArray[7][2]=6;
		iArray[7][3]=3;
		
		iArray[8]=new Array();
		iArray[8][0]="现金价值标记";
		iArray[8][1]="50px"; 
		iArray[8][2]=6;
		iArray[8][3]=1;
		
		iArray[9]=new Array();
		iArray[9][0]="责任名称";
		iArray[9][1]="0px"; 
		iArray[9][2]=6;
		iArray[9][3]=3;
		
		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 
		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		//Mulline9Grid.addEventFuncName="getEdorType";
		Mulline9Grid.selBoxEventFuncName ="setEdorDetail";
		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initEdorItem(){
		var Sql = "select distinct edorcode,edorname from Lmedoritem where appobj='I' order by edorcode";
		arr =easyExecSql(Sql);
		if(arr){
			var tb = document.getElementById('edoritemTab');
      var rowNum=tb.rows.length;
      for (i=0;i<rowNum;i++) 
      {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
      } 
			var rowFlag=0;
			var newRow=document.getElementById("edoritemTab").insertRow(-1);
			for(var i=0;i<arr.length;i++){
				  if(rowFlag==6){
					  newRow=document.getElementById("edoritemTab").insertRow(-1);
					  rowFlag=0;
				  }
				  Sql="select 1 from PD_LMRiskEdorItem where riskcode = '"+fm.all("RiskCode").value+"' and edorcode='"+arr[i][0]+"'"
					var check=easyExecSql(Sql);
					if(check){
						newCell=newRow.insertCell(-1);	
						newCell.className="common";
						newCell.innerHTML="<input type='checkbox' name='EdorCode' checked=true value='"+arr[i][0]+"'>&nbsp;"+arr[i][0]+"-"+arr[i][1]+"";
					}else{
						newCell=newRow.insertCell(-1);	
						newCell.className="common";
						newCell.innerHTML="<input type='checkbox' name='EdorCode' value='"+arr[i][0]+"'>&nbsp;"+arr[i][0]+"-"+arr[i][1]+"";
					}
				  rowFlag++;
			}
			if(rowFlag==0 || rowFlag==6){
			}else{
				for(var i=0;i<4-rowFlag;i++){
					newCell=newRow.insertCell(-1);	
					newCell.className="common";
				}
			}
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
		iArray[1][0]="保全项目编码";
		iArray[1][1]="70px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="算法编码";
		iArray[2][1]="70px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="保全算法类型";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		Mulline11Grid = new MulLineEnter( "fm" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=0;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=1;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;
Mulline11Grid.selBoxEventFuncName="initCalCodeDef";
		Mulline11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initCalCodeDef()
{
	//alert('initCalCodeDef');
	 var selNo = Mulline11Grid.getSelNo();
	if(selNo == 0)
	{
	
	}
	else
	{
		var name = Mulline11Grid.getRowColData(selNo-1,2);
		var  tRiskCode=fm.all("RiskCode").value;
		initCalCodeMain(tRiskCode,name);
		
	}
}
function showButtons(riskCode){
	var strSQL = "select BonusFlag from pd_lmriskapp where riskcode = '" + riskCode + "'";
	var crr = easyExecSql(strSQL);
	
	var bonusFlag = crr[0][0];
	if(bonusFlag == 'Y'){
		
	}else{
		document.getElementById('BonusFlagId').style.display = 'none';
	}
}
</script>
