<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDRelaBillInit.jsp
  //�����ܣ����θ����˵�����
  //�������ڣ�2009-3-16
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

var tResourceName = "productdef.PDRelaBillInputSql";
function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		fm.all("GETDUTYCODE").value = "<%=request.getParameter("GetDutyCode")%>";
		fm.all("GETDUTYKIND").value = "<%=request.getParameter("GetDutyKind")%>";
	var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRelaBillInputSql5";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.all("GETDUTYCODE").value);//ָ������Ĳ���
	  mySql.addSubPara(fm.all("GETDUTYKIND").value);//ָ������Ĳ���
   strSQL = mySql.getString();
    var arrInfo = easyExecSql(strSQL);
   if(arrInfo.length>0)
   {
   		fm.all("GETDUTYNAME").value = arrInfo[0][0];
   }
		
		initMulline10Grid();
		queryMulline10Grid();
		
		//initMulline8Grid();
		//initMulline9Grid();
				
		//queryMulline9Grid();
		
		//queryMulline8Grid();

		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDRelaBillInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
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


		iArray[1]=new Array();
		iArray[1][0]="��������";
		iArray[1][1]="90px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="�˵���Ŀ����";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="�˵���Ŀ����";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="���ü��㷽ʽ";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="������ϸ���㹫ʽ";
		iArray[7][1]="90px";
		iArray[7][2]=100;
		iArray[7][3]=0;
		

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
function initMulline8Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�˵���Ŀ����";
		iArray[1][1]="20px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�˵���Ŀ����";
		iArray[2][1]="20px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		Mulline8Grid = new MulLineEnter( "fm" , "Mulline8Grid" ); 
           
		Mulline8Grid.mulLineCount=0;
		Mulline8Grid.displayTitle=1;
		Mulline8Grid.canSel=0
		Mulline8Grid.canChk=1;
		Mulline8Grid.hiddenPlus=1;
		Mulline8Grid.hiddenSubtraction=1;
           
		Mulline8Grid.loadMulLine(iArray);

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
		iArray[1][0]="�˵���Ŀ����";
		iArray[1][1]="20px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�˵���Ŀ����";
		iArray[2][1]="20px";
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
function updateDisplayState()
{
	var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRelaBillInputSql1";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara("0");//ָ������Ĳ���
   strSQL = mySql.getString();
    //tongmeng 2011-07-13 modify
   // 
   var arrInfo = easyExecSql(strSQL);
   if(arrInfo.length>0)
   {
   	for(i=0;i<arrInfo.length;i++)
   	{
   		try{
   		var tFieldName = arrInfo[i][1];
   		var tTitle = arrInfo[i][4];
   		if(tTitle!=null&&tTitle!='')
   		{
   			// tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').setAttribute('title',\"" + newData + "\")";
   			document.getElementById(tFieldName).title = tTitle;
   		}
   		}
   		catch(Ex)
   		{
   			}
   	}
   }

}
</script>
