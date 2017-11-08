
<%
	//**************************************************************************************************
	//Name：StandPayInfoListInit.jsp
	//Function：立案__预估金额__初始化页面
	//Author：pd
	//Date: 2005-11-1
	//Desc:
	//**************************************************************************************************
%>
<%
	String CaseNo = request.getParameter("CaseNo");
	String customerName = request.getParameter("customerName");
	String customerNo = request.getParameter("customerNo");
	String GrpContNo = request.getParameter("GrpContNo");
	String RiskCode = request.getParameter("RiskCode");
%>
<script language="JavaScript" type="text/javascript">
var turnPage = new turnPageClass();

/**
 *FUNCTION:initForm()
 *DESC :初始化__FORM__表单
 */
function initForm()
{
  try
  {
  fm.CaseNo.value="<%=CaseNo%>";
  fm.customerName.value="<%=customerName%>";
  fm.customerNo.value="<%=customerNo%>";
  fm.GrpContNo.value="<%=GrpContNo%>"; 
  fm.RiskCode.value="<%=RiskCode%>"; 
  
  initAffixGrid();   //初始化__预估金额__GRID
  initList();
  }
  catch(ex)
  {
  alert("StandPayInfoListInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+ ex.message);
  }
}

/**
 *FUNCTION:initAffixGrid()
 *DESC :初始化__预估金额__GRID
 */
function initAffixGrid()
{
  var iArray = new Array();
    try
    {
    var sql="";
    if(fm.RiskCode.value!=null &&  fm.RiskCode.value!=""){
      sql=" and riskcode=#" + "<%=RiskCode%>" + "# ";
    }
    
     iArray[0]=new Array("序号","30px","0",1);
     
     iArray[1]=new Array();
     iArray[1][0]="险种代码";     //列名（此列为顺序号，列名无意义，而且不显示）
     iArray[1][1]="50px";       //列宽
     iArray[1][2]=10;           //列最大值
     iArray[1][3]=2;            //是否允许输入,1表示允许，0表示不允许
     iArray[1][4]="grpriskcode"
     iArray[1][5]="1|2";                 //引用代码对应第几列，'|'为分割符
     iArray[1][6]="0|1";
     iArray[1][9]="险种代码|NOTNULL";
     iArray[1][15]="1";								//控件名称
     iArray[1][16]=" 1 " + sql + " and riskcode in (select riskcode from lcgrppol where 1=1 and GrpContNo = #"+"<%=GrpContNo%>"+"#)";     //该控件的值
     //iArray[1][17]="1"
	 //alert(iArray[1][16]);

     iArray[2]=new Array("险种名称","180px","20",0);
     
     iArray[3]=new Array();
	      iArray[3][0]="币种";      	   		//列名
	      iArray[3][1]="80px";            			//列宽
	      iArray[3][2]=20;            			//列最大值
	      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
	      iArray[3][4]="currency";
	      iArray[3][5]="3";              	                //引用代码对应第几列，'|'为分割符
	      iArray[3][6]="0";              	        //上面的列中放置引用代码中第几位值
	      iArray[3][9]="币种|code:currency&NOTNULL";
     
     iArray[4]=new Array();
     iArray[4][0]="预估金额";     //列名（此列为顺序号，列名无意义，而且不显示）
     iArray[4][1]="100px";      //列宽
     iArray[4][2]=20;           //列最大值
     iArray[4][3]=7;            //是否允许输入,1表示允许，0表示不允许
     iArray[4][9]="预估金额|NOTNULL&NUM";
     iArray[4][22]="col3"; //由币种决定
     
     //iArray[3]=new Array("预估金额","100px","20",1);

     AffixGrid = new MulLineEnter( "fm" , "AffixGrid" );
     AffixGrid.mulLineCount = 0;
     AffixGrid.displayTitle = 1;
     AffixGrid.canChk = 0;
     AffixGrid.hiddenPlus=0;
     AffixGrid.hiddenSubtraction=0;
     AffixGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
  alert("CaseAffixListInit.jsp-->initAffixGrid函数中发生异常:初始化界面错误!"+ ex.message);
    }
  }


</script>
