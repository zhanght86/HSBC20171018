<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDCheckFieldInit.jsp
  //程序功能：投保规则
  //创建日期：2009-3-14
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">
function initInput()
{
	try{
		document.getElementById('CheckField').value= '';
		document.getElementById('Serialno').value= '';
		document.getElementById('CalCode').value= '';
		document.getElementById('Msg').value= '';
		document.getElementById('CheckFieldName').value = '';
		document.getElementById('STANDBYFLAG1Name').value = '';
		document.getElementById('STANDBYFLAG1').value = '';
		initCalCodeMain('','');
		isshowbutton();
		}
	catch(Ex){
			}
}

function initForm()
{
	try{
		lockPage("数据加载中...........");
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		initMulline10Grid();
		//initMulline9Grid();
		queryMulline9Grid();
		queryMulline10Grid();
		updateDisplayState();
		initInput();
		
		
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		
	}
	catch(re){
		myAlert("PDCheckFieldInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
	unLockPage();
}

function updateDisplayState()
{
 
  var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
	
	}
	else
	{
		var name = Mulline10Grid.getRowColData(selNo-1,4);
		var  tRiskCode=fm.all("RiskCode").value;
		initCalCodeMain(tRiskCode,name);
		document.getElementById('CheckField').value= Mulline10Grid.getRowColData(selNo-1,2);
		document.getElementById('Serialno').value= Mulline10Grid.getRowColData(selNo-1,3);
		document.getElementById('CalCode').value= Mulline10Grid.getRowColData(selNo-1,4);
		document.getElementById('Msg').value= Mulline10Grid.getRowColData(selNo-1,5);
		document.getElementById('STANDBYFLAG1').value= Mulline10Grid.getRowColData(selNo-1,6);
		document.getElementById('CheckFieldName').value = '';
		document.getElementById('STANDBYFLAG1Name').value = '';
		showOneCodeName('pd_lc_checkfield','CheckField','CheckFieldName');
		showOneCodeName('pd_systype','STANDBYFLAG1','STANDBYFLAG1Name');
	}
	
 /*
 // rowCount:显示的字段数量
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:下拉项对应的selectcode的数组
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1); 

 var j = 0;
 
 for(var i = 0; i < rowCount; i++)
 {
   if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.all("riskcode").value,null,"readonly"); 		 
	 }else 
	 {
	 	var selNo = Mulline10Grid.getSelNo();
 		if(selNo==0 || selNo == null)
 		{
 			Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
 		}else
 		{
 			if(Mulline9Grid.getRowColData(i,2) == "FIELDNAME")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,2),null,"readonly"); 		 
	 		}else if(Mulline9Grid.getRowColData(i,2) == "SERIALNO")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,3),null,"readonly"); 		 
	 		}else
	 		{ 			
	 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.all("tableName").value+" where riskcode = '"+fm.all('RiskCode').value+"' and FIELDNAME = '"+Mulline10Grid.getRowColData(selNo-1,2)+"' and SERIALNO = '"+Mulline10Grid.getRowColData(selNo-1,3)+"'";
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
 }*/
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
		iArray[2][0]="控制类型";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="控制顺序号";
		iArray[3][1]="75px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="保單行政管理費演算法";
		iArray[4][1]="30px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="校验提示信息";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="适用系统";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		iArray[6][4]="pd_systype";


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
		iArray[1][1]="200px";
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
		iArray[4][1]="200px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="官方字段描述";
		iArray[5][1]="400px";
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
