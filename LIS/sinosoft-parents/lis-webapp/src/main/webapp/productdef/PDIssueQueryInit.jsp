<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDIssueQueryInit.jsp
  //程序功能：问题件录入
  //创建日期：2009-4-3
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm() 
{
	try{
	    isshowbutton();
		fm.all('RiskCode').value ="<%=request.getParameter("riskcode")%>";
		showOneCodeName("pdriskdefing","RiskCode","RiskCodeName");
		//根据进入的界面提供提出问题件的岗位
		fm.all('BackPost').value ="<%=request.getParameter("postcode")%>";
		showOneCodeName("pd_issuepost","BackPost","BackPostName1");
		fm.all('SerialNo').value ='';
		fm.all('FindFlag').value="<%=request.getParameter("findflag")%>";
		showCodeName();
		initMulline10Grid();
		QueryIssue();
		fm.all('hiddenRiskCode').value =fm.all('RiskCode').value;
		fm.all('hiddenBackPost').value =fm.all('BackPost').value;
		if(fm.all('FindFlag').value!='1'){
			fm.all('RiskCode').readonly="readonly";
			fm.all('BackPost').readonly="readonly";	
		}
	}
	catch(re){
		myAlert("PDIssueQueryInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}


function initMulline10Grid()
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
		iArray[1][1]="0px";
		iArray[1][2]=100;
		iArray[1][3]=3;
		
		iArray[2]=new Array();
		iArray[2][0]="返回岗位";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="问题件内容";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="问题件状态";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="问题件发送时间";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="附件路径名";
		iArray[6][1]="75px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="回复人";
		iArray[7][1]="60px";
		iArray[7][2]=100;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="回复内容";
		iArray[8][1]="100px";
		iArray[8][2]=100;
		iArray[8][3]=0;

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
