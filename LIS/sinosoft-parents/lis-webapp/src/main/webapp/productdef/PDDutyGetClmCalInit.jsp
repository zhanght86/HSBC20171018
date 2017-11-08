<%@include file="../i18n/language.jsp"%>
<%
  //ç¨‹åºåç§°ï¼šPDDutyGetClmCalInit.jsp
  //ç¨‹åºåŠŸèƒ½ï¼šè´£ä»»ç»™ä»˜èµ”ä»˜æ‰©å……è®¡ç®—å…¬å¼?
  //åˆ›å»ºæ—¥æœŸï¼?009-3-16
  //åˆ›å»ºäº? ï¼šCM
  //æ›´æ–°è®°å½•ï¼? æ›´æ–°äº?   æ›´æ–°æ—¥æœŸ     æ›´æ–°åŽŸå› /å†…å®¹
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		initMulline9Grid();
		queryMulline9Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	}
	catch(re){
		myAlert("PDDutyGetClmCalInit.jsp-->"+"³õÊ¼»¯½çÃæ´íÎó!");
	}
}
function updateDisplayState()
{
 // rowCount:æ˜¾ç¤ºçš„å­—æ®µæ•°é‡?
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:ä¸‹æ‹‰é¡¹å¯¹åº”çš„selectcodeçš„æ•°ç»?
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1); 

 var j = 0;
 
 for(var i = 0; i < rowCount; i++)
 {
     Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1]);
 }

}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="ÐòºÅ";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="ÊôÐÔÃû³Æ";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="ÊôÐÔ´úÂë";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="ÊôÐÔÊý¾ÝÀàÐÍ";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="ÊôÐÔÖµ";
		iArray[4][1]="102px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="¹Ù·½×Ö¶ÎÃèÊö";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="ÒµÎñÈËÔ±±¸×¢";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;


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
