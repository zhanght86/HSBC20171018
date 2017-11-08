<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDIssueInputInit.jsp
  //程序功能：问题件录入
  //创建日期：2009-4-2
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all('RiskCode').value ="<%=request.getParameter("riskcode")%>";
		//根据进入的界面提供提出问题件的岗位
		fm.all('PostCode').value ="<%=request.getParameter("postcode")%>";
		//根据进入的界面参数提供问题件的类型
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
		myAlert("PDIssueInputInit.jsp-->"+"初始化界面错误!");
	}
}

//初始化产品申请日期
function initRequDate()
{
   //var strSQL = "SELECT MissionProp1 FROM Lwmission WHERE MissionProp2 = '"+fm.all('RiskCode').value+"'";
   
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDIssueInputInputSql2";
	 mySql.setResourceName("productdef.PDIssueInputInputSql"); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all('RiskCode').value);//指定传入的参数
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
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="问题件序列号";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=3;
		
		iArray[2]=new Array();
		iArray[2][0]="返回岗位";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="问题件内容";
		iArray[3][1]="75px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="问题件状态";
		iArray[4][1]="75px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="问题件发送时间";
		iArray[5][1]="75px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="附件路径名";
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
