<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDIssueInputInit.jsp
  //�����ܣ������¼��
  //�������ڣ�2009-4-2
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all('RiskCode').value ="<%=request.getParameter("riskcode")%>";
		//���ݽ���Ľ����ṩ���������ĸ�λ
		fm.all('PostCode').value ="<%=request.getParameter("postcode")%>";
		//���ݽ���Ľ�������ṩ�����������
		fm.all('IssueType').value ="<%=request.getParameter("issuetype")%>";
		fm.all('Filepath2').value="";
		fm.all('Filename2').value="";
		fm.all('SerialNo').value="";
		
		fm.all("MissionID").value = "<%=request.getParameter("missionid")%>";
		fm.all("SubMissionID").value = "<%=request.getParameter("submissionid")%>";
		fm.all("ActivityID").value = "<%=request.getParameter("activityid")%>";
		
		initMulline9Grid();
		queryMulline9Grid();
		initRequDate();
	}
	catch(re){
		myAlert("PDIssueInputInit.jsp-->"+"��ʼ���������!");
	}
}

//��ʼ����Ʒ��������
function initRequDate()
{
   //var strSQL = "SELECT MissionProp1 FROM Lwmission WHERE MissionProp2 = '"+fm.all('RiskCode').value+"'";
   
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDIssueInputInputSql2";
	 mySql.setResourceName("productdef.PDIssueInputInputSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.all('RiskCode').value);//ָ������Ĳ���
   strSQL = mySql.getString();
   
   var tRequDate = easyExecSql(strSQL);
   // alert(tRequDate);
   if( tRequDate != null && tRequDate != "" )
   {
	   fm.RequDate.value = tRequDate;
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
		iArray[1][0]="��������к�";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=3;
		
		iArray[2]=new Array();
		iArray[2][0]="���ظ�λ";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���������";
		iArray[3][1]="75px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�����״̬";
		iArray[4][1]="75px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="���������ʱ��";
		iArray[5][1]="75px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="����·����";
		iArray[6][1]="75px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		Mulline9Grid = new MulLineEnter( "fmImport" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=1;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
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
