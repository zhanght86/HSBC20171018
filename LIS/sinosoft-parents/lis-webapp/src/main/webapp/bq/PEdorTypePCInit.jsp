<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2003-01-08
//创建人  ：Dingzhong
//更新记录：  更新人    更新日期     更新原因/内容
%>


<script language="JavaScript">
var str_sql = "",sql_id = "", my_sql = ""; 
function initInpBox()
{

  try
  {
    Edorquery();
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('PayLocation').value = '';
    document.all('ContType').value ="1";
    showOneCodeName("PEdorType", "EdorType", "EdorTypeName");
    
  }
  catch(ex)
  {
    alert("在PEdorTypePCInit.jsp-->InitInpBox函数中发生异常:初始化界面错误1!");
  }
}

function initForm()
{
    try
    {
        initInpBox();
        initSelQuery();
        initPolGrid();
        queryCustomerInfo();
        //initBankInfo();
        newpaytype();

    }
    catch(re)
    {
        alert("PEdorTypePCInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

function initSelQuery()
{
     //var i = 0;

   var strSql = "";
//    strSql = "select a.AppntNo,a.AppntName,a.Prem,a.Amnt,"
//               + " (select codename from ldcode where codetype = 'paylocation' and trim(code) = trim(a.paylocation)),"
//               + " a.BankCode,a.BankAccNo,a.AccName "
//               + " from lccont a where a.contno='"+top.opener.document.all('ContNo').value+"'";
   sql_id = "PEdorTypePCInputSql5";
   my_sql = new SqlClass();
   my_sql.setResourceName("bq.PEdorTypePCInputSql"); //指定使用的properties文件名
   my_sql.setSqlId(sql_id);//指定使用的Sql的id
   my_sql.addSubPara(top.opener.document.all('ContNo').value);//指定传入的参数
   str_sql = my_sql.getString();
   var arrResult = easyExecSql(str_sql, 1, 0);
   if (arrResult == null)
   {
       alert("没有相应的保单信息");
   }
   else
   {
       displayAddress(arrResult);
   }

   //turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);
     //document.all('fmtransact').value = "QUERY||PayLocation";
     //fm.submit();

}

function displayAddress(arrResult)
{
     document.all('AppntNo').value = arrResult[0][0];
     document.all('AppntName').value = arrResult[0][1];
     document.all('Prem').value = arrResult[0][2];
     document.all('Amnt').value = arrResult[0][3];
     //document.all('AccName').value = arrResult[0][1];
     document.all('PayMode').value = arrResult[0][4];

     document.all('BankCodeS').value = arrResult[0][5];
     document.all('BankAccNoS').value = arrResult[0][6];
     document.all('AccNameS').value = arrResult[0][7];
     document.all('AccName').value = arrResult[0][1];
     //document.all('PayLocationS').value = arrResult[0][8];
}

function newpaytype(){
//      var strsql = "select PayLocation,BankCode,BankAccNo,AccName,( select codename from ldcode where codetype = 'paylocation' and code=PayLocation),( select bankname from ldbank b where b.bankcode =a.bankcode) from LPCont a where 1 =1 and ContNo = '"+top.opener.document.all('ContNo').value+"' and EdorNo = '"+top.opener.document.all('EdorNo').value+"' and EdorType = '"+top.opener.document.all('EdorType').value+"'";
     sql_id = "PEdorTypePCInputSql6";
     my_sql = new SqlClass();
     my_sql.setResourceName("bq.PEdorTypePCInputSql"); //指定使用的properties文件名
     my_sql.setSqlId(sql_id);//指定使用的Sql的id
     my_sql.addSubPara(top.opener.document.all('ContNo').value);//指定传入的参数
     my_sql.addSubPara(top.opener.document.all('EdorNo').value);//指定传入的参数
     my_sql.addSubPara(top.opener.document.all('EdorType').value);//指定传入的参数
     str_sql = my_sql.getString();
     var arrResult = easyExecSql(str_sql, 1, 0);
     //alert(strsql);
     if(arrResult != null){
          document.all('PayLocation').value = arrResult[0][0];
          document.all('PayLocationName').value = arrResult[0][4];
          if(arrResult[0][0] == "0"){
             divBank.style.display="";
             document.all('BankCode').value = arrResult[0][1];
         document.all('BankAccNo').value = arrResult[0][2];
         document.all('AccNameS').value = arrResult[0][3];
         document.all('AccName').value = arrResult[0][3];
         document.all('BankCodeName').value=arrResult[0][5];
          }
     }
}

function initPolGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="80px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="证件类型";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="证件号码";
        iArray[5][1]="140px";
        iArray[5][2]=150;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="性别";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        iArray[6][21]=2;

        iArray[7]=new Array();
        iArray[7][0]="出生日期";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=8;
        iArray[7][21]=3;

        PolGrid = new MulLineEnter( "fm" , "PolGrid" );
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 3;
        PolGrid.displayTitle = 1;
        PolGrid.canSel=0;
        //PolGrid.canChk=1;
        PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);
        //这些操作必须在loadMulLine后面

    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
