<%
//PEdorTypeICInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">

var str_sql = "",sql_id = "", my_sql = "";
function initForm()
{
    try
    {
        initInpBox();
	      initPolNewGrid();
	      initInpPCustomerInfo();
        initInpCustomerInfo();
        queryRelationPol();
    }
    catch (ex)
    {
        alert("PEdorTypeICInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

function initInpBox()
{

  try
  {
    Edorquery();
	  document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    getInsuredNo();
    showOneCodeName('EdorType','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("在PEdorTypeCMInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在PEdorTypeICInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}


function queryRelationPol()
{
    var iArray;
//     var strSQL = "select a.polno,a.riskcode,  a.CValidate, " +
//                 "a.Prem, a.Amnt,a.paytodate,case a.payintv when 0 then '趸交' when 12 then '年交' when 6 then '半年交' when 3 then '季交' when 1 then '月交' else '不定期交' end,a.payenddate,a.currency " +       
//                 "from lcpol a where contno='" +document.all('ContNo').value+"' and appflag in ('1') and cvalidate <= '"+document.all('EdorValiDate').value +"' and enddate > '"+document.all('EdorValiDate').value +"'";
                sql_id = "PEdorTypeICInputSql5";
                my_sql = new SqlClass();
                my_sql.setResourceName("bq.PEdorTypeICInputSql"); //指定使用的properties文件名
                my_sql.setSqlId(sql_id);//指定使用的Sql的id
                my_sql.addSubPara(document.all('ContNo').value);//指定传入的参数
                my_sql.addSubPara(document.all('EdorValiDate').value);//指定传入的参数
                my_sql.addSubPara(document.all('EdorValiDate').value);//指定传入的参数
                str_sql = my_sql.getString();

    turnPage.strQueryResult  = easyExecSql(str_sql);
    var brrResult = easyExecSql(str_sql);
    if (brrResult)
    {
    displayMultiline(brrResult,PolNewGrid);
    }else
    {
    	alert("查询相关客户相关保单失败或者本客户下没有保单，请撤销本次申请重新选择客户号");
    	return false;
    }
}

function getInsuredNo()
{
	    var tRole="";
	    var tAppntNo="";
	    var tInsuredNo="";
// 		  var strSQL =  " select remark,appntno,insuredno from lpconttempinfo where state='0' and contno='"+document.all('ContNo').value+"' and edorno='"+getLastEdorNo()+"'";
		  sql_id = "PEdorTypeICInputSql6";
          my_sql = new SqlClass();
          my_sql.setResourceName("bq.PEdorTypeICInputSql"); //指定使用的properties文件名
          my_sql.setSqlId(sql_id);//指定使用的Sql的id
          my_sql.addSubPara(document.all('ContNo').value);//指定传入的参数
          my_sql.addSubPara(getLastEdorNo());//指定传入的参数
          str_sql = my_sql.getString();
		  var brr = easyExecSql(str_sql);
			if ( brr )
			{
				//alert("已经申请保存过");
				brr[0][0]==null||brr[0][0]=='null'?'0':tRole= brr[0][0];
				brr[0][1]==null||brr[0][1]=='null'?'0':tAppntNo= brr[0][1];
				brr[0][2]==null||brr[0][2]=='null'?'0':tInsuredNo= brr[0][2];				
			}
			else
			{
				alert("获取客户资料变更临时表数据失败");
				return "";
			}	 
			//投保人   
	    if(tRole=='0' || tRole==0)
	    {
	     	document.all('CustomerNo').value=tAppntNo;	    	
	    }else
	    {	    			
		    document.all('CustomerNo').value=tInsuredNo;	     			
	    }

	    

	
	}
function initPolNewGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保单号码";
        iArray[1][1]="100px";
        iArray[1][2]=60;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="60px";
        iArray[2][2]=60;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="生效日期";
        iArray[3][1]="60px";
        iArray[3][2]=80;
        iArray[3][3]=8;

        iArray[4]=new Array();
        iArray[4][0]="保费标准";
        iArray[4][1]="60px";
        iArray[4][2]=80;
        iArray[4][3]=7;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="基本保额";
        iArray[5][1]="60px";
        iArray[5][2]=80;
        iArray[5][3]=7;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="交至日期";
        iArray[6][1]="50px";
        iArray[6][2]=80;
        iArray[6][3]=8;

        iArray[7]=new Array();
        iArray[7][0]="缴费间隔";
        iArray[7][1]="60px";
        iArray[7][2]=80;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="缴费期间";
        iArray[8][1]="60px";
        iArray[8][2]=80;
        iArray[8][3]=8;
        
        iArray[9]=new Array();
				iArray[9][0]="币种";
				iArray[9][1]="60px";
				iArray[9][2]=100;
				iArray[9][3]=2;
				iArray[9][4]="currency";
        
      PolNewGrid = new MulLineEnter( "fm" , "PolNewGrid" );
      //这些属性必须在loadMulLine前
      PolNewGrid.mulLineCount = 1;
      PolNewGrid.displayTitle = 1;
      PolNewGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolNewGrid.hiddenSubtraction=1;
      PolNewGrid.loadMulLine(iArray);
      PolNewGrid.selBoxEventFuncName ="queryRelationPol" ;
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>