<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDLFRiskInit.jsp
  //�����ܣ�����ᱨ������
  //�������ڣ�2009-3-16
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript"><!--

function initForm()
{
	try{
		var riskcode = '<%=request.getParameter("riskcode")%>';
		var batchNo = '<%=request.getParameter("deploybatch")%>';
		initMulline9Grid();	
		
	}
	catch(re){
		alert("PDLFRiskInit.jspInitForm�����з����쳣:��ʼ���������!");
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
		iArray[1][0]="��Ʒ����";
		iArray[1][1]="80px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�޸���������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�޸�����";
		iArray[3][1]="150px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="ԭֵ";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="��ֵ";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

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
		alert(ex);
	}
}
--></script>
