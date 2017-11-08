<%@include file="../i18n/language.jsp"%>

<%
  //程序名称：PDRiskFeeInit.jsp
  //程序功能：账户管理费定义
  //创建日期：2009-3-14
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
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
		myAlert("PDRiskFeeInit.jsp-->"+""+"InitForm函数中发生异常:初始化界面错误!");
	}
}
	function updateDisplayState()
{
 //var sql = "select count(1) from Pd_Basefield where tablecode = upper('PD_LMRiskFee') and isdisplay = 1";
 var sql = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskFeeInputSql4";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara('PD_LMRiskFee');//指定传入的参数
	
	   
   sql = mySql.getString();
   
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 //sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('PD_LMRiskFee') and isdisplay = 1 order by Pd_Basefield.Displayorder";
// var sql = "";
   var mySql2=new SqlClass();
	 var sqlid = "PDRiskFeeInputSql5";
	 mySql2.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql2.setSqlId(sqlid);//指定使用的Sql的id
	 mySql2.addSubPara('PD_LMRiskFee');//指定传入的参数
	
	   
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
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="属性名称";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="属性代码";
		iArray[2][1]="0px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="属性数据类型";
		iArray[3][1]="0px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="属性值";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="官方字段描述";
		iArray[5][1]="250px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="业务人员备注";
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
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="管理费编码";
		iArray[1][1]="75px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="保险帐户号码";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="管理费分类";
		iArray[3][1]="75px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="费用项目分类";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="费用收取位置";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="计算方式";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="管理费计算公式";
		iArray[7][1]="105px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="缴费/给付项编码";
		iArray[8][1]="105px";
		iArray[8][2]=100;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="险种编码";
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
