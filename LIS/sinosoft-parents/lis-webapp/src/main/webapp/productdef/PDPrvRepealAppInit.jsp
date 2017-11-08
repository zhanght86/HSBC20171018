<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDPrvRepealAppInit.jsp
  //程序功能：条款废止申请
  //创建日期：
  //创建人  ：
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<%
  String type = request.getParameter("type");
%>

<script type="text/javascript">

	function initForm()
	{
		try{
			fm.all("Type").value = "<%=type%>";
			initMulline9Grid();
			//initMulline10Grid();
		}
		catch(re){
			myAlert("PDContDefiInit.jsp-->"+"初始化界面错误!");
		}
	}
	
	function initMulline9Grid()
	{
		var iArray = new Array();
		try
		{
			iArray[0]=new Array();
			iArray[0][0]="序号";
			iArray[0][1]="0px";
			iArray[0][2]=200;
			iArray[0][3]=0;
	
			iArray[1]=new Array();
			iArray[1][0]="条款编码";
			iArray[1][1]="90px";
			iArray[1][2]=100;
			iArray[1][3]=0;
	
			iArray[2]=new Array();
			iArray[2][0]="条款描述";
			iArray[2][1]="90px";
			iArray[2][2]=100;
			iArray[2][3]=0;
	
			iArray[3]=new Array();
			iArray[3][0]="申请日期";
			iArray[3][1]="75px";
			iArray[3][2]=100;
			iArray[3][3]=0;
	
			iArray[4]=new Array();
			iArray[4][0]="申请人";
			iArray[4][1]="45px";
			iArray[4][2]=100;
			iArray[4][3]=0;
			
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
	
			Mulline9Grid.loadMulLine(iArray);
	
		}
		catch(ex){
			myAlert(ex);
		}
	}
</script>
