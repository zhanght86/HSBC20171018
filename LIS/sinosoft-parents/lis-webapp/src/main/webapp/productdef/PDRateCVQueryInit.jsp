<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDAlgoTempLibQueryInit.jsp
  //�����ܣ��㷨ģ����ѯ����
  //�������ڣ�2009-3-18
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
	fm.RiskCode.value = '<%=request.getParameter("RiskCode")%>';
	}
	catch(re){
		myAlert("PDAlgoTempLibQueryInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}

function initMulline9Grid()
{
	if(fm.TableName.value == null || fm.TableName.value == "")
	{
		myAlert("��ѡ�б����ٲ�ѯ");
		return;
	}
	Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 
	Mulline9Grid.displayTitle=1;
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=200;
		iArray[0][3]=0;
		
		var index = 0;
		var sql = "select factorname from user_tab_cols a, Pd_Scheratecalfactor_Lib b where a.column_name = upper(factorcode) and table_name = '" + fm.TableName.value + "'";
		sql="select  column_name from user_tab_cols where table_name =upper('" + fm.TableName.value + "') order by column_id";
		var arr = easyExecSql(sql);
		if(arr != null)
		{
			for(var i = 0; i < arr.length; i++)
			{
				index++;
				if(i==0){
				QuerySQLCache+=arr[i][0];
				}else{
				QuerySQLCache+=","+arr[i][0];
				}
				iArray[index]=new Array();
				iArray[index][0]= arr[i][0];
				iArray[index][1]="35px";
				iArray[index][2]=100;
				iArray[index][3]=0;
			}
		}else{
		myAlert("�÷��ʱ����ڣ�");
		iArray[0][1]="0px";
		QuerySQLCache="";
		Mulline9Grid.displayTitle=0;
		}



		Mulline9Grid.mulLineCount=0;

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

