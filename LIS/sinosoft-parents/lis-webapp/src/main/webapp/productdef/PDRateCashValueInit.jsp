<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDRateCashValueInit.jsp
  //�����ܣ����ݱ���ֽ��ֵ����
  //�������ڣ�2009-3-17
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.RiskCode.value = "<%=request.getParameter("riskcode")%>";
		
		fm.PayCode.value = "<%=request.getParameter("payplancode")%>";
		initMulline9Grid();
		queryMulline9Grid();
		initMulline10Grid();
		initMulline11Grid();
		queryMulline11Grid();
		//initMulline12Grid();
		//queryMulline12Grid();
		//initMulline13Grid();
		//initMulline14Grid();
		//queryMulline14Grid();
		
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		uploadForm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		dealUpload.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		//fm2.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		//uploadCV.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	}
	catch(re){
		myAlert("PDRateCashValueInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
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
		iArray[1][0]="Ҫ������";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="Ҫ�ر���";
		iArray[2][1]="60px";
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
function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="Ҫ��˳��";
		iArray[0][1]="60px";
		iArray[0][2]=200;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="Ҫ��˳��";
		iArray[1][1]="0px";
		iArray[1][2]=100;
		iArray[1][3]=3;

		iArray[2]=new Array();
		iArray[2][0]="Ҫ������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="Ҫ�ر���";
		iArray[3][1]="40px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=0;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=0;

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
		iArray[2][0]="�ɷ����α���";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���ݱ����";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="���ݱ�����";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		Mulline11Grid = new MulLineEnter( "fm" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=0;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=1;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;
		Mulline11Grid.selBoxEventFuncName ="setMulline9Grid";

		Mulline11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function setMulline9Grid()
{
	var selNo = Mulline11Grid.getSelNo();
	
	var sql = "select b.Column_Id,a.factorname,a.factorcode from Pd_Scheratecalfactor_Lib a right outer join User_Tab_Cols b"
	+" on upper(a.Factorcode) = b.Column_Name where b.Table_Name = upper('"
	+ Mulline11Grid.getRowColData(selNo-1,3)
	+ "') order by b.Column_Id ";
 
    Mulline10GridTurnPage.pageLineNum = 10;
    Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
    
    initMulline15Grid();
    queryMulline15Grid();
}

function initMulline12Grid()
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
		iArray[2][0]="Ҫ�ر���";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

	
		Mulline12Grid = new MulLineEnter( "fm2" , "Mulline12Grid" ); 

		Mulline12Grid.mulLineCount=0;
		Mulline12Grid.displayTitle=1;
		Mulline12Grid.canSel=1;
		Mulline12Grid.canChk=0;
		Mulline12Grid.hiddenPlus=1;
		Mulline12Grid.hiddenSubtraction=1;

		Mulline12Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initMulline13Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="Ҫ��˳��";
		iArray[0][1]="60px";
		iArray[0][2]=200;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="Ҫ��˳��";
		iArray[1][1]="0px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="Ҫ������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="Ҫ�ر���";
		iArray[3][1]="0px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		Mulline13Grid = new MulLineEnter( "fm2" , "Mulline13Grid" ); 

		Mulline13Grid.mulLineCount=0;
		Mulline13Grid.displayTitle=1;
		Mulline13Grid.canSel=1;
		Mulline13Grid.canChk=0;
		Mulline13Grid.hiddenPlus=1;
		Mulline13Grid.hiddenSubtraction=0;

		Mulline13Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initMulline14Grid()
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
		iArray[2][0]="�ֽ��ֵ����";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		Mulline14Grid = new MulLineEnter( "fm2" , "Mulline14Grid" ); 

		Mulline14Grid.mulLineCount=0;
		Mulline14Grid.displayTitle=1;
		Mulline14Grid.canSel=1;
		Mulline14Grid.canChk=0;
		Mulline14Grid.hiddenPlus=1;
		Mulline14Grid.hiddenSubtraction=1;
		Mulline14Grid.selBoxEventFuncName ="setMulline13Grid";

		Mulline14Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function setMulline13Grid()
{
	var selNo = Mulline14Grid.getSelNo();
	
	var sql = "select b.Column_Id,a.factorname,a.factorcode from Pd_Scheratecalfactor_Lib a right outer join User_Tab_Cols b"
	+" on upper(a.Factorcode) = b.Column_Name where b.Table_Name = upper('"
	+ Mulline14Grid.getRowColData(selNo-1,2)
	+ "') order by b.Column_Id ";
 
    Mulline13GridTurnPage.pageLineNum = 3125;
    Mulline13GridTurnPage.queryModal(sql,Mulline13Grid);
}

function initMulline15Grid()
{
    var selNo = Mulline11Grid.getSelNo();
    var sql = "select column_name from User_Tab_Cols a "
	+" where a.Table_Name = upper('"
	+ Mulline11Grid.getRowColData(selNo-1,3)
	+ "') order by a.Column_Id ";
	
    var arr15 = easyExecSql( sql );
    
    if (arr15.length > 0) {

    
    var iArray = new Array();
	try{
	    iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;
		
		for (var n = 1; n<=arr15.length;n++) {
		  iArray[n]=new Array();
		  iArray[n][0]=arr15[n-1][0];
		  iArray[n][1]="30px";
		  iArray[n][2]=100;
		  iArray[n][3]=0;
		}

		Mulline15Grid = new MulLineEnter( "fm" , "Mulline15Grid" ); 

		Mulline15Grid.mulLineCount=10;
		Mulline15Grid.displayTitle=1;
		Mulline15Grid.canSel=0;
		Mulline15Grid.canChk=0;
		Mulline15Grid.hiddenPlus=1;
		Mulline15Grid.hiddenSubtraction=1;
		
		Mulline15Grid.loadMulLine(iArray);	

	}
	catch(ex){
		myAlert(ex);
	}
	    }
}
</script>
