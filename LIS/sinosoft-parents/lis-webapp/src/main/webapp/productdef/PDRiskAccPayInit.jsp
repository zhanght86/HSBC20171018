<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDRiskAccPayInit.jsp
  //�����ܣ��˻������ֶ���
  //�������ڣ�2009-3-14
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{ 
	try{
		fm.RISKCODE.value = "<%=request.getParameter("riskcode")%>";
		initMulline11Grid();
		queryMulline11Grid();
		//initMulline9Grid();
		queryMulline9Grid();
		initMulline10Grid();
		queryMulline10Grid();
		initMulline12Grid();
		queryMulline12Grid();
		//updateDisplayState();
		fm.IsReadOnly.value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDRiskAccPayInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
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
	 	// var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.all("tableName").value+" where RiskCode = '"+"<%=request.getParameter("riskcode")%>" +"'";
   	// var tContent = easyExecSql(tDefaultValueSQL);
   	 
   	 var cData = null;
   	// if(tContent!=null&&tContent[0][0]!="null")
   	// {
   	// 	 cData = tContent[0][0];
   	// }
   	 
    
     //tongmeng 2011-07-25 modify
     if(rowcode[i][1]!=null&&(rowcode[i][1]=='pd_payplancode'||rowcode[i][1]=='pd_riskinsuacc'))
     {
     		//alert("i:"+i+":rowcode[i][1]:"+rowcode[i][1]);
     		Mulline9Grid.setRowColDataShowCodeList(i,4,cData,Mulline9Grid,rowcode[i][1],"<%=request.getParameter("riskcode")%>");
     		
      }
      else
      {
      	 Mulline9Grid.setRowColDataCustomize(i,4,cData,null,rowcode[i][1]);
      }
    }
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
		iArray[2][0]="�����˻�����";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�����˻�����";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		Mulline11Grid = new MulLineEnter( "fm" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=0;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=0;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;

		Mulline11Grid.loadMulLine(iArray);

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
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���Դ���";
		iArray[2][1]="0px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="������������";
		iArray[3][1]="0px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="����ֵ";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=2;
		iArray[4][16]='';

		iArray[5]=new Array();
		iArray[5][0]="�ٷ��ֶ�����";
		iArray[5][1]="250px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="ҵ����Ա��ע";
		iArray[6][1]="0px";
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
		iArray[1][0]="���ֱ���";
		iArray[1][1]="20px";
		iArray[1][2]=20;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�����ʻ�����";
		iArray[2][1]="20px";
		iArray[2][2]=20;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="Ĭ�ϱ���";
		iArray[3][1]="20px";
		iArray[3][2]=20;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="�Ƿ���Ҫ¼��";
		iArray[4][1]="20px";
		iArray[4][2]=20;
		iArray[4][3]=3;

		iArray[5]=new Array();
		iArray[5][0]="�˻�����λ��";
		iArray[5][1]="20px";
		iArray[5][2]=20;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="�ɷѱ���";
		iArray[6][1]="50px";
		iArray[6][2]=50;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="�ʻ��ɷ�ת��λ��";
		iArray[7][1]="20px";
		iArray[7][2]=20;
		iArray[7][3]=3;

		iArray[8]=new Array();
		iArray[8][0]="�Ƿ�������";
		iArray[8][1]="20px";
		iArray[8][2]=20;
		iArray[8][3]=3;


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
		iArray[1][0]="��Ʒ����";
		iArray[1][1]="20px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��Ʒ�ɷѱ���";
		iArray[2][1]="20px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��Ʒ����";
		iArray[3][1]="20px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="�ɷ�/����/�˻�����";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�ɷ�/����/�˻�����";
		iArray[5][1]="20px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="�˻�����";
		iArray[6][1]="20px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		Mulline12Grid = new MulLineEnter( "fm" , "Mulline12Grid" ); 

		Mulline12Grid.mulLineCount=0;
		Mulline12Grid.displayTitle=1;
		Mulline12Grid.canSel=1;
		Mulline12Grid.canChk=0;
		Mulline12Grid.hiddenPlus=1;
		Mulline12Grid.hiddenSubtraction=1;
		Mulline12Grid.selBoxEventFuncName ="getOneData12";
		Mulline12Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>