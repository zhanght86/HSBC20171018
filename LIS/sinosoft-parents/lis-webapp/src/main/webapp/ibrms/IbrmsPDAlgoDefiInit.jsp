<%
  //�������ƣ�PDAlgoDefiInit.jsp
  //�����ܣ��㷨�������
  //�������ڣ�2009-3-17
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">

function initForm()
{
	try{
		document.all("RiskCode").value = '<%=request.getParameter("riskcode")%>';
	
		if(document.all("RiskCode").value == null || document.all("RiskCode").value == "")
		{
			document.all("RiskCode").value = '';
		}
		
		document.all("IsReadOnly").value = '<%=session.getValue("IsReadOnly")%>';	
		
		document.all("AlgoCode").value = '<%=request.getParameter("AlgoCode")%>';	
		
		if(document.all("AlgoCode").value != "null" && document.all("AlgoCode").value != "")
		{
//			var sql = "select calcode,type,Remark,Calsql from Pd_Lmcalmode where calcode = '" + document.all("AlgoCode").value +"'";
			var sql = "";
			var sqlid1="IbrmsPDAlgoDefiInitSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("ibrms.IbrmsPDAlgoDefiSql"); 
			mySql1.setSqlId(sqlid1);
			mySql1.addSubPara(document.all("AlgoCode").value);
			sql = mySql1.getString();
			var arr = easyExecSql(sql);
			
			if(arr != null)
			{
				document.all("AlgoCode").value = arr[0][0];
				document.all("AlgoType").value = arr[0][1];
				document.all("AlgoDesc").value = arr[0][2];
				document.all("AlgoContent").value = arr[0][3]; 
			}
		}
		else
		{
			document.all("AlgoCode").value = ""; 
		}
		initMulline9Grid();
		queryMulline9Grid();
		//initMulline10Grid();
		//initMulline11Grid();
		//queryMulline11Grid();
	}
	catch(re){
		alert("PDAlgoDefiInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}



function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�㷨����";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���ֱ���";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�㷨ģ������";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�㷨����";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�㷨����";
		iArray[5][1]="0px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="�㷨��������";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=3;

		Mulline9Grid = new MulLineEnter( "document" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=5;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		Mulline9Grid.selBoxEventFuncName = "afterSel";

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}

function afterSel()
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo > 0)
	{
		document.all("AlgoCode").value = Mulline9Grid.getRowColData(selNo-1,1);
		document.all("AlgoType").value = Mulline9Grid.getRowColData(selNo-1,3);
		document.all("AlgoDesc").value = Mulline9Grid.getRowColData(selNo-1,5);
		document.all("AlgoContent").value = Mulline9Grid.getRowColData(selNo-1,4);
		document.all("AlgoTypeName").value = Mulline9Grid.getRowColData(selNo-1,6);
		if(document.all("AlgoType").value=="F"){
			var sql = "";
			if(_DBT==_DBO){
				sql = "select a.calcode,b.remark,a.FACTORGRADE from PD_LMCalFactor a,PD_LMCalMode b where a.calcode=b.calcode and  factorcode='"+document.all("AlgoCode").value+"' and rownum<2";
			}else if(_DBT==_DBM){
				sql = "select a.calcode,b.remark,a.FACTORGRADE from PD_LMCalFactor a,PD_LMCalMode b where a.calcode=b.calcode and  factorcode='"+document.all("AlgoCode").value+"' limit 0,1";
			}
			var sql = "";
			var sqlid2="IbrmsPDAlgoDefiInitSql2";
			if(_DBT==_DBO){
				sqlid2="IbrmsPDAlgoDefiInitSql2";
			}else if(_DBT==_DBM){
				sqlid2="IbrmsPDAlgoDefiInitSql3";
			}
			var mySql2=new SqlClass();
			mySql2.setResourceName("ibrms.IbrmsPDAlgoDefiSql"); 
			mySql2.setSqlId(sqlid1);
			mySql2.addSubPara(document.all("AlgoCode").value);
			sql = mySql1.getString();
	    var arr = easyExecSql(sql);
			if(arr != null){
				fm.MainAlgoCode.value=arr[0][0];
				fm.MainAlgoName.value=arr[0][1];
				fm.SubAlgoGrade.value=arr[0][2];
			}
	  	document.all("subAlgoDiv").style.display = '';
	  }else{
	  	document.all("subAlgoDiv").style.display = 'none';
	  }
	}
	
	queryMulline11Grid();
}

function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="Ҫ������";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="Ҫ������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="Ҫ�ش���";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="����ֵ";
		iArray[4][1]="45px";
		iArray[4][2]=100;
		iArray[4][3]=1;

		iArray[5]=new Array();
		iArray[5][0]="Ҫ��ֵ��������";
		iArray[5][1]="105px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="Ҫ�غ���˵��";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;


		Mulline10Grid = new MulLineEnter( "document" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=5;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
function initMulline11Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���㷨����";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���㷨����";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;


		iArray[3]=new Array();
		iArray[3][0]="���㷨���ȼ�";
		iArray[3][1]="45px";
		iArray[3][2]=100;
		iArray[3][3]=1;


		Mulline11Grid = new MulLineEnter( "document" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=5;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=1;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;
		Mulline11Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
</script>
