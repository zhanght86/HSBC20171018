<%
//程序名称：CardProposalSignInit.jsp
//程序功能：
//创建日期：2006-12-05 17:30:36
//创建人  ：CR
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

     var turnPage = new turnPageClass();
// 输入框的初始化（单记录部分）
function getAddFeeReason(){
    var tSelNo = AddFeeGrid.getSelNo()-1;
    var tProposalNo = AddFeeGrid.getRowColData(tSelNo,15);
    if( tSelNo <0)
    {
       alert("请选择一天信息！");
       return;
    }else{
//       var tSql = "select addpremreason from lcuwmaster where proposalno = '"+tProposalNo+"'";
       var tSql = "";
       var sqlid1="RiskAddFeeQueryInitSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.RiskAddFeeQueryInitSql"); 
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tProposalNo);
		tSql=mySql1.getString();	
       var tAddReason = easyExecSql(tSql);
       if(tAddReason !=null)
           fm.AddReason.value = tAddReason;
    }
}
function queryFee(tContNo,tInsuredNo){
    //初始化查询加费数据
/**
	var tSQL = "select b.riskcode,(select riskname from lmriskapp where riskcode=b.riskcode),a.dutycode, "
	         + " a.payplantype,a.addfeedirect,a.suppriskscore,a.prem,'',a.paystartdate,a.payenddate,'',a.polno,"
	         + " c.mainpolno,a.payplancode,c.proposalno "
	         + " from lcprem a,lmriskduty b,lcpol c  where a.dutycode=b.dutycode and a.polno=c.polno"
	         + " and c.contno='"+tContNo+"' and c.insuredno='"+tInsuredNo+"' "
	         + " and a.payplancode like '000000%%' ";*/
	 var tSQL = "";
     var sqlid2="RiskAddFeeQueryInitSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.RiskAddFeeQueryInitSql"); 
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
		mySql2.addSubPara(tInsuredNo);
		tSQL=mySql2.getString();
	 turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);          
	//判断是否查询成功
  if (turnPage.strQueryResult){
     turnPage.queryModal(tSQL,AddFeeGrid);
  }else{
      alert("没有加费信息！");
  }
}
                          
function initForm(tContNo,tInsuredNo)
{
    try
    {
        initAddFeeGrid();
        queryFee(tContNo,tInsuredNo);
    }
    catch(re)
    {
        alert("RiskAddFeeQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

// 保单信息列表的初始化
function initAddFeeGrid()
{                               
      var iArray = new Array();
      try
      {
          iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			  //列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
//险种编码,险种名称,加费类型,加费类别,加费方式,评点,加费金额,加费原因,起期,止期,下发状态,保单号,主险保单号
   		iArray[1]=new Array();
      iArray[1][0]="险种编码";    	        //列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=1;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     
      
	  iArray[2]=new Array();
      iArray[2][0]="险种名称";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;    //列最大值
      iArray[2][3]=2;         			
  


      iArray[3]=new Array();
      iArray[3][0]="加费责任编码";    	        //列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

		  iArray[4]=new Array();
      iArray[4][0]="加费类别";    	        //列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[4][10] = "PlanPay";
      iArray[4][11] = "0|^01|健康加费||^02|职业加费|03|^03|居住地加费|03|^04|爱好加费|03|";
      iArray[4][12] = "4|5|6";
      iArray[4][13] = "0|2|3";
      iArray[4][19] = 1;


		  iArray[5]=new Array();
      iArray[5][0]="加费方式";    	        //列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[5][10] = "AddFeeMethod";
      iArray[5][11] = "0|^01|按EM值|^02|按保费比例|^03|按保额|";
      iArray[5][12] = "5|6";
      iArray[5][13] = "0|2";
      iArray[5][19] = 1;

/*
			iArray[5]=new Array();
      iArray[5][0]="加费方式";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[5][4] = "addfeetypemethod";
      iArray[5][5] = "5";
      iArray[5][6] = "0";
*/      
			iArray[6]=new Array();
      iArray[6][0]="评点/比例";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

			iArray[7]=new Array();
      iArray[7][0]="加费金额";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
			iArray[7][7]="AutoCalAddFee";

      iArray[8]=new Array();
      iArray[8][0]="加费原因";    	        //列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     

      iArray[9]=new Array();
      iArray[9][0]="加费起期";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[10]=new Array();
      iArray[10][0]="加费止期";         		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[11]=new Array();
      iArray[11][0]="下发状态";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[12]=new Array();
      iArray[12][0]="险种号";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[13]=new Array();
      iArray[13][0]="主险险种号";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[14]=new Array();
      iArray[14][0]="缴费计划编码";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
    
      iArray[15]=new Array();
      iArray[15][0]="投保单险种号";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
    
          AddFeeGrid = new MulLineEnter( "fm" , "AddFeeGrid" ); 
          AddFeeGrid.mulLineCount = 0;   
          AddFeeGrid.displayTitle = 1;
          AddFeeGrid.hiddenPlus = 1;
          AddFeeGrid.hiddenSubtraction = 1;
          AddFeeGrid.canSel = 1;
          AddFeeGrid.canChk = 0;
          AddFeeGrid.selBoxEventFuncName = "getAddFeeReason";
          AddFeeGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
