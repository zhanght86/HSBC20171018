<%@include file="../i18n/language.jsp"%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //�������ƣ�PDRequRiskInit.jsp
  //�����ܣ���Ʒ�������ѯ
  //�������ڣ�2009-3-12
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">
<%
	String currentDate = PubFun.getCurrentDate();
%>	
var RequDate='<%=currentDate%>';
function initForm(){
	try{
		initMulline9Grid();
		//	initMulline10Grid();
		//fm.all("ViewMode").value = "";
		fm.all("RiskCode").value = "";
		//fm.all("RequDate").value = "<%=currentDate%>";
		<%
			session.setAttribute("LoadFlagButton1","1");
			System.out.println("loadFlagButton1:"+session.getAttribute("LoadFlagButton1"));
		%>
	}catch(re){
		myAlert("PDRequRiskInit.jsp-->"+""+"InitForm�����з����쳣:��ʼ���������!");
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
		iArray[1][0]="��Ʒ���ִ���";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="��Ʒ��������";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=1;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=1;

		iArray[4]=new Array();
		iArray[4][0]="������";
		iArray[4][1]="45px";
		iArray[4][2]=100;
		iArray[4][3]=1;
		
		iArray[5]=new Array();
		iArray[5][0]="missionid";
		iArray[5][1]="45px";
		iArray[5][2]=100;
		iArray[5][3]=3;
		
		iArray[6]=new Array();
		iArray[6][0]="submissionid";
		iArray[6][1]="45px";
		iArray[6][2]=100;
		iArray[6][3]=3;
		
		iArray[7]=new Array();
		iArray[7][0]="activityid";
		iArray[7][1]="45px";
		iArray[7][2]=100;
		iArray[7][3]=3;

		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		Mulline9Grid.selBoxEventFuncName="ShowDetail";
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
		iArray[1][0]="��Ʒ���ִ���";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="��Ʒ��������";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=1;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=1;

		iArray[4]=new Array();
		iArray[4][0]="������";
		iArray[4][1]="45px";
		iArray[4][2]=100;
		iArray[4][3]=1;
	
		iArray[5]=new Array();
		iArray[5][0]="missionid";
		iArray[5][1]="45px";
		iArray[5][2]=100;
		iArray[5][3]=3;
		
		iArray[6]=new Array();
		iArray[6][0]="submissionid";
		iArray[6][1]="45px";
		iArray[6][2]=100;
		iArray[6][3]=3;
		
		iArray[7]=new Array();
		iArray[7][0]="activityid";
		iArray[7][1]="45px";
		iArray[7][2]=100;
		iArray[7][3]=3;
			
		iArray[8]=new Array();
		iArray[8][0]="��������";
		iArray[8][1]="60px";
		iArray[8][2]=100;
		iArray[8][3]=1;


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
</script>
