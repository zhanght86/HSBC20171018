<%@include file="../i18n/language.jsp"%>

<%
  //�������ƣ�PDRiskFeeInit.jsp
  //�����ܣ��˻�����Ѷ���
  //�������ڣ�2009-3-14
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

var tResourceName = "productdef.PDRiskFeeInputSql";
function initForm()
{
	try{
		fm.RiskCode.value = "<%=request.getParameter("riskcode")%>";
		//initMulline9Grid();
		queryMulline9Grid();
		initMulline10Grid();
		queryMulline10Grid();
		//updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDRiskFeeInit.jsp-->"+""+"InitForm�����з����쳣:��ʼ���������!");
	}
}
	function updateDisplayState()
{
 //var sql = "select count(1) from Pd_Basefield where tablecode = upper('PD_LMRiskFee') and isdisplay = 1";
 var sql = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskFeeInputSql4";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara('PD_LMRiskFee');//ָ������Ĳ���
	
	   
   sql = mySql.getString();
   
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 //sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('PD_LMRiskFee') and isdisplay = 1 order by Pd_Basefield.Displayorder";
// var sql = "";
   var mySql2=new SqlClass();
	 var sqlid = "PDRiskFeeInputSql5";
	 mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql2.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql2.addSubPara('PD_LMRiskFee');//ָ������Ĳ���
	
	   
   sql = mySql2.getString();
 
 var rowcode = easyExecSql(sql,1,1,1);
 
 for(var i = 0; i < rowCount; i++)
 {
 	 //tongmeng 2011-07-25 modify
     if(rowcode[i][1]!=null&&(rowcode[i][1]=='pd_dutypaygetcode'||rowcode[i][1]=='pd_riskinsuacc'))
     {
     		//alert("i:"+i+":rowcode[i][1]:"+rowcode[i][1]);
     		Mulline9Grid.setRowColDataShowCodeList(i,4,null,Mulline9Grid,rowcode[i][1],"<%=request.getParameter("riskcode")%>");
     		
      }
      else
      {
  			Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");
  		}
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
		iArray[1][0]="����ѱ���";
		iArray[1][1]="75px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�����ʻ�����";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="����ѷ���";
		iArray[3][1]="75px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="������Ŀ����";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="������ȡλ��";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="���㷽ʽ";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="����Ѽ��㹫ʽ";
		iArray[7][1]="105px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="�ɷ�/���������";
		iArray[8][1]="105px";
		iArray[8][2]=100;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="���ֱ���";
		iArray[9][1]="80px";
		iArray[9][2]=100;
		iArray[9][3]=3;
		
		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		//Mulline10Grid.selBoxEventFuncName ="updateDisplayState";
		Mulline10Grid.selBoxEventFuncName ="getOneData";
		
		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
