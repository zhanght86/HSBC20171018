<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDEdorZT1Init.jsp
  //�����ܣ����屣ȫ��Ŀ����2
  //�������ڣ�2009-3-16
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		fm.all("EdorType").value = "<%=request.getParameter("edortype")%>";
		queryCodeName();
		initMulline10Grid();
		queryMulline10Grid();
		initMulline9Grid();
		queryMulline9Grid();
		initMulline11Grid();
		queryMulline11Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDEdorZT1Init.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}
function updateDisplayState()
{
 // rowCount:��ʾ���ֶ�����
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:�������Ӧ��selectcode������
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1); 

 var j = 0;
 
 for(var i = 0; i < rowCount; i++)
 {
     if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,"<%=request.getParameter("riskcode")%>",null,"readonly"); 		 
	 }
	 else
	 {
	 	var selNo = Mulline11Grid.getSelNo();
 		if(selNo==0 || selNo == null)
 		{
 			Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
 		}else
 		{
 			if(Mulline9Grid.getRowColData(i,2) == "DUTYCODE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline11Grid.getRowColData(selNo-1,2),null,"readonly"); 		 
	 		}else if(Mulline9Grid.getRowColData(i,2) == "PAYPLANCODE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline11Grid.getRowColData(selNo-1,3),null,"readonly"); 		 
	 		}else if(Mulline9Grid.getRowColData(i,2) == "SURRCALTYPE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline11Grid.getRowColData(selNo-1,4),null,"readonly"); 		 
	 		}else if(Mulline9Grid.getRowColData(i,2) == "CYCPAYINTVTYPE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline11Grid.getRowColData(selNo-1,5),null,"readonly"); 		 
	 		}
	 		else
	 		{ 			
	 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.all("tableName").value+" where dutycode = '"+Mulline11Grid.getRowColData(selNo-1,2) +"' and riskcode = '"+fm.all('RiskCode').value+"' and PAYPLANCODE = '"+Mulline11Grid.getRowColData(selNo-1,3)+"' and SURRCALTYPE = '"+Mulline11Grid.getRowColData(selNo-1,4)+"' and CYCPAYINTVTYPE = '"+Mulline11Grid.getRowColData(selNo-1,5)+"'";
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
		iArray[1][0]="���ִ���";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���δ���";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���νɷѴ���";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="���νɷ�����";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=0;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;

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
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��������";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���Դ���";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="������������";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="����ֵ";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="�ٷ��ֶ�����";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="ҵ����Ա��ע";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;


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
		iArray[1][0]="���ֱ���";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���α���";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ɷѼƻ�����";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�˱���������";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�ڽ�ʱ����";
		iArray[5][1]="120px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="�㷨����";
		iArray[6][1]="120px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="�Ƿ��˻��˱�";
		iArray[7][1]="90px";
		iArray[7][2]=100;
		iArray[7][3]=2;
		iArray[7][10]="pdpaybyacc";
		iArray[7][11]="0|^1|��^0|��";
		
		iArray[8]=new Array();
		iArray[8][0]="�ǰ����ѻ��Ǳ������";
		iArray[8][1]="90px";
		iArray[8][2]=100;
		iArray[8][3]=2;
		iArray[8][10]="pdpaycaltype";
		iArray[8][11]="0|^1|���~^0|���M";


		Mulline11Grid = new MulLineEnter( "fm" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=0;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=1;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;
		Mulline11Grid.selBoxEventFuncName ="updateDisplayState";

		Mulline11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>