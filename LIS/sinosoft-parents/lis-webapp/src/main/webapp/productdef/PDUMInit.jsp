<%@include file="../i18n/language.jsp"%>
<script type="text/javascript">
function initInput()
{
	try{
		document.getElementById('UWCODE').value= '';
		document.getElementById('RELAPOLTYPE').value= '';
		document.getElementById('RELAPOLTYPEName').value= '';
		document.getElementById('UWTYPE').value= '';
		document.getElementById('UWTYPEName').value= '';
		document.getElementById('UWORDER').value= '';
		document.getElementById('REMARK').value = '';
		document.getElementById('CALCODE').value = '';
		document.getElementById('STANDBYFLAG1').value = '';
		document.getElementById('STANDBYFLAG1NAME').value = '';
		document.getElementById('STANDBYFLAG2').value = '';
		document.getElementById('STANDBYFLAG2NAME').value = '';
		fm.CalCodeSwitchFlag[0].checked=true;
		fm.CalCodeSwitchFlag[1].checked=false;
		initCalCodeMain('','');
		}
	catch(Ex){
			}
}



function initForm()
{
	try{
		//-------- delete by jucy
		if(mType=='TB'||mType=='EDOR'){
			fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		}
		//-------- end
		//var tNameSQL = "select riskname from Pd_LMRisk where riskcode = '"+fm.all("RiskCode").value+"'";	
		//var tNameArr = easyExecSql(tNameSQL,1,1,1); 
		//if(tNameArr){
		//	fm.all("RiskName").value = tNameArr[0][0];
		//}
		//initMulline9Grid();
		queryMulline9Grid();
		initMulline10Grid();
		queryMulline10Grid();
		initInput();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		try{
		isshowbutton();
		}catch(ex){}
	}
	catch(re){
		myAlert("PDUMInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}
function updateDisplayState()
{
	
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
	
	}
	else
	{
		var name = Mulline10Grid.getRowColData(selNo-1,2);
		if(mType=='TB'||mType=='EDOR'){
			var  tRiskCode=fm.all("RiskCode").value;
		}else{
			var  tRiskCode=fm.all("QueRiskCode").value;
		}
		initCalCodeMain(tRiskCode,name);
		try{
				document.getElementById('STANDBYFLAG1').value= '';
				document.getElementById('STANDBYFLAG1').value= Mulline10Grid.getRowColData(selNo-1,8);
				showOneCodeName('pd_systype','STANDBYFLAG1','STANDBYFLAG1NAME');
				if(document.getElementById('STANDBYFLAG1').value==''){
					document.getElementById('STANDBYFLAG1NAME').value='';
				}
		}catch(ex){}	
		try{	document.getElementById('STANDBYFLAG2').value= '';
				document.getElementById('STANDBYFLAG2').value= Mulline10Grid.getRowColData(selNo-1,9);
				showOneCodeName('ibrmsbusinessrule','STANDBYFLAG2','STANDBYFLAG2NAME');	
				if(document.getElementById('STANDBYFLAG2').value==''){
					document.getElementById('STANDBYFLAG2NAME').value='';
				}
		}catch(ex){}
		document.getElementById('UWCODE').value= Mulline10Grid.getRowColData(selNo-1,1);
		document.getElementById('RELAPOLTYPE').value= Mulline10Grid.getRowColData(selNo-1,6);
		document.getElementById('UWTYPE').value= Mulline10Grid.getRowColData(selNo-1,7);
		document.getElementById('UWORDER').value= Mulline10Grid.getRowColData(selNo-1,4);
		document.getElementById('REMARK').value= Mulline10Grid.getRowColData(selNo-1,5);
		document.getElementById('CALCODE').value= Mulline10Grid.getRowColData(selNo-1,2);
		//-------- add by jucy
		//加入险种代码初始化
		document.getElementById('RiskCode').value= Mulline10Grid.getRowColData(selNo-1,3);
		//-------- end
		try{		document.getElementById('VALIFLAG').value= Mulline10Grid.getRowColData(selNo-1,10);
		}catch(ex){}
		document.getElementById('RELAPOLTYPEName').value= '';
		document.getElementById('UWTYPEName').value= '';

		try{showOneCodeName('pd_relapoltype','RELAPOLTYPE','RELAPOLTYPEName');}catch(ex){}
		try{
		showOneCodeName('pd_uwtype','UWTYPE','UWTYPEName');
		}catch(ex){}
	
		try{
			var sql="select edorname from lmedoritem where  edorcode='"+document.getElementById('UWTYPE').value+"' and appobj='I' ";
			var tNameArr = easyExecSql(sql,1,1,1);
			if(tNameArr && tNameArr[0][0]){
				document.getElementById('UWTYPEName').value= tNameArr[0][0];
			}
		}catch(ex){
			
		}
	}
	/*
 // rowCount:显示的字段数量
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:下拉项对应的selectcode的数组
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1); 
 
 for(var i = 0; i < rowCount; i++)
 {
     if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.all("riskcode").value,null,"readonly"); 		 
	 }else if(Mulline9Grid.getRowColData(i,2) == "RISKNAME")
	 {
		 var tNameSQL = "select riskname from Pd_LMRisk where riskcode = '"+fm.all("riskcode").value+"'";
		 var tNameArr = easyExecSql(tNameSQL,1,1,1); 
   	     var tRiskName = tNameArr[0][0]; 
	 	 Mulline9Grid.setRowColDataCustomize(i,4,tRiskName,null,"readonly");
	 }
	 else
	 {
	 	var selNo = Mulline10Grid.getSelNo();
 		if(selNo==0 || selNo == null)
 		{
 			Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
 		}else
 		{	 
 			if(Mulline9Grid.getRowColData(i,2) == "UWCODE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,1),null,"readonly"); 		 
	 		}else if(Mulline9Grid.getRowColData(i,2) == "CALCODE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,2),null,"readonly"); 		 
	 		}else
	 		{ 	
 	 
	 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.all("tableName").value+" where riskcode = '"+fm.all('RiskCode').value+"' and UWCODE = '"+Mulline10Grid.getRowColData(selNo-1,1)+"'";
   	 		var tContent = easyExecSql(tDefaultValueSQL);
   	 				var tContent = easyExecSql(tDefaultValueSQL);
   	 		var cData = null;
   	 		if(tContent!=null&&tContent[0][0]!="null")
   	 		{
   	 	 		cData = tContent[0][0];
   	 		}
     		Mulline9Grid.setRowColDataCustomize(i,4,cData,null,rowcode[i][1]);
     	}
    }
  }
 }
 */
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
		iArray[1][0]="核保编码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="算法编码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="险种代码";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="核保顺序";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="校验提示信息";
		iArray[5][1]="250px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="关联保单类型";
		iArray[6][1]="80px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="核保类型";
		iArray[7][1]="50px";
		iArray[7][2]=100;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="适用系统";
		iArray[8][1]="90px";
		iArray[8][2]=100;
		iArray[8][3]=0;
		iArray[8][4]="pd_systype";
		
		iArray[9]=new Array();
		iArray[9][0]="业务模块";
		iArray[9][1]="90px";
		iArray[9][2]=100;
		iArray[9][3]=0;
		
		iArray[10]=new Array();
		iArray[10][0]="校验提醒";
		iArray[10][1]="90px";
		iArray[10][2]=100;
		iArray[10][3]=3;
		


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.selBoxEventFuncName ="updateDisplayState";

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
		iArray[1][0]="属性名称";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="属性代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="属性数据类型";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="属性值";
		iArray[4][1]="103px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="官方字段描述";
		iArray[5][1]="200px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="业务人员备注";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=3;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=0;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
