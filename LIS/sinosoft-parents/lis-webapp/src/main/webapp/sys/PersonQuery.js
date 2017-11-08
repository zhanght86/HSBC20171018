//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var tDisplay;
var turnPage = new turnPageClass();
var turnPageCustomerGrid = new turnPageClass();
var turnPageAddressGrid = new turnPageClass();
var turnPageAccountGrid = new turnPageClass();
var turnPagePolyGrid = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";


// 查询客户信息
function QueryClick()
{
	//初始化相关联信息
	  initAddressGrid();
      initAccountGrid();
      initDisplayButton();
    
    //判断是否有查询条件
    /**if(document.all('Name').value =="")
    {
        alert("请输入客户姓名！");
        document.all('Name').focus();
      return;
    }**/
    //var strsql="select CustomerNo,GrpNo,Name,Sex,Birthday,IDType,IDNo from ldperson where Name='"+document.all('Name').value+"'";
    /**
    if(document.all('IDNo').value !=""&&document.all('IDNo').value !=""){
    strsql=strsql+ " and  IDNo='"+document.all('IDNo').value+"'";
    }
    if(document.all('Sex').value !=""&&document.all('Sex').value !=""){
    strsql=strsql+" and Sex='"+document.all('Sex').value+"'";
    }
    if(document.all('BIRTHDAY').value !=""&&document.all('BIRTHDAY').value !=""){
    strsql=strsql+" and BIRTHDAY='"+document.all('BIRTHDAY').value+"'";
    }
    */
    
    var mySql1=new SqlClass();
		mySql1.setResourceName("sys.PersonQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId("PersonQuerySql1");//指定使用的Sql的id
		mySql1.addSubPara(document.all('Name').value);//指定传入的参数
		mySql1.addSubPara(document.all('IDNo').value);//指定传入的参数
		mySql1.addSubPara(document.all('Sex').value);//指定传入的参数
		mySql1.addSubPara(document.all('BIRTHDAY').value);//指定传入的参数
	var strsql= mySql1.getString();
   
    try
    {
        turnPageCustomerGrid.pageDivName = "divTurnPageCustomerGrid";
        turnPageCustomerGrid.queryModal(strsql, CustomerGrid);
        if(turnPageCustomerGrid!=null){
        }
        
    }
    catch (ex) {}
}
//查询客户地址,银行账户信息
function QueryClick2(){
var tSel = CustomerGrid.getSelNo();
initDisplayButton();
    	if( tSel != 0 || tSel != null )
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        //var strsql1="select AddressNo,PostalAddress,Phone,HomeAddress,HomePhone,Mobile from lcaddress where CustomerNo='"+customerNo+"'";
        var mySql2=new SqlClass();
			mySql2.setResourceName("sys.PersonQuerySql"); //指定使用的properties文件名
			mySql2.setSqlId("PersonQuerySql2");//指定使用的Sql的id
			mySql2.addSubPara(customerNo);//指定传入的参数
		var strsql1= mySql2.getString();
        turnPageAddressGrid.pageDivName = "divTurnAddressGrid";
        turnPageAddressGrid.queryModal(strsql1, AddressGrid);
        //var strsql2="select AccKind,BankCode,BankAccNo,AccName from lcaccount where CustomerNo='"+customerNo+"'";
        var mySql3=new SqlClass();
			mySql3.setResourceName("sys.PersonQuerySql"); //指定使用的properties文件名
			mySql3.setSqlId("PersonQuerySql3");//指定使用的Sql的id
			mySql3.addSubPara(customerNo);//指定传入的参数
		var strsql2= mySql3.getString();
        turnPageAccountGrid.pageDivName = "divTurnPageAccountGrid";
        turnPageAccountGrid.queryModal(strsql2, AccountGrid);
        ButtonControl();
        }
}

// 保单查询
function PolClick()
{
    var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录，再点击明细按钮。" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("PersonPol.jsp?CustomerNo="+customerNo+"&type=1","",sFeatures);
    }
}

// 理赔查询
function ClaimClick()
{
     var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录，再点击明细按钮。" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("../uw/ClaimQueryCusInput.jsp?CustomerNo="+customerNo);
    }
}


// 保全查询
function EdorClick()
{
    var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录，再点击明细按钮。" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("../uw/EdorQueryCusInput.jsp?CustomerNo="+customerNo);
    }
}

// 领取查询
function GetClick()
{
    var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录，再点击明细按钮。" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("../sys/GetQuery.jsp?CustomerNo="+customerNo);
    }
}

// 既往收费查询
function PayClick()
{
    var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录，再点击明细按钮。" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("../sys/PayQueryInput.jsp?CustomerNo="+customerNo);
    }
}

//初始化按钮控制
function initDisplayButton()
{
        fm.Pol.disabled=true;
        fm.Edor.disabled=true;
        fm.Claim.disabled=true;
        fm.Pay.disabled=true;
        fm.Get.disabled=true;
  
}

//按钮明暗控制
function ButtonControl(){
var tSel = CustomerGrid.getSelNo();
    	if( tSel != 0 || tSel != null )
    {
	var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
	//既往投保
	//var  strsql="select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where appntno='"+customerNo+"'";
	    var mySql4=new SqlClass();
			mySql4.setResourceName("sys.PersonQuerySql"); //指定使用的properties文件名
			mySql4.setSqlId("PersonQuerySql4");//指定使用的Sql的id
			mySql4.addSubPara(customerNo);//指定传入的参数
		var strsql= mySql4.getString();
	var  arr = easyExecSql(strsql);
  	if(arr!=null){
  	fm.Pol.disabled='';
  	}
  	
  	//既往保全
 	//var sqlind="select a.edorno,d.appntname,a.edorappdate,a.edorvalidate,(select b.codename from ldcode b where b.codetype='edorcontuwstate' and trim(a.uwstate)=trim(b.code)) " + " from LPEdorMain a,lccont d  where  d.contno=a.contno  and d.appntno='"+customerNo+"'";
    
    var sqlid1="EdorQueryCusSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.EdorQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(customerNo);//指定传入的参数
	    var sqlind=mySql1.getString();
	   
    var arrind = easyExecSql(sqlind);
	   //判断是否查询成功
 	 if(arrind!=null){
        document.all('Edor').disabled='';
      }
  //既往理赔
 	/**
  var sqlind= " select a.rptno, c.name, b.MedAccDate, b.AccDate, (case (select clmstate from llregister where rgtno=a.rptno)"
			+ " when '10' then '报案' when '20' then '立案' when '30' then"
			+ " '审核' when '40' then '审批' when '50' then '结案' when '60' then"
			+ " '完成' when '70' then '关闭' else '报案' end)"
			+ " from llreport a, llsubreport b, ldperson c"
			+ " where a.rptno = b.subrptno"
			+ " and b.customerno = c.customerno"
			//新加按客户查找
			+ "and b.customerno='"+customerNo +"'";
			*/
  var sqlid1="ClaimQueryCusSql1";
	var mySql1=new SqlClass();
		mySql1.setResourceName("uw.ClaimQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(customerNo);//指定传入的参数
	var sqlind=mySql1.getString();	
  var arrind = easyExecSql(sqlind);
	   //判断是否查询成功
 	 if(arrind!=null){
        document.all('Claim').disabled='';
      }


  //既往收费
 	
  //var strsql="select l.TempFeeNo,c.PayMoney,l.APPntName,c.InBankAccNo,c.InAccName,c.IDNo,l.PayDate from LJTempFee l,LJTempFeeClass c where l.TempFeeNo=c.TempFeeNo and l.APPntName =(select name from ldperson where CustomerNo='"+ customerNo+"')" ;
  //var strsql="select l.TempFeeNo,c.PayMoney,l.APPntName,c.InBankAccNo,c.InAccName,c.IDNo,l.PayDate from LJTempFee l, LJTempFeeClass c ,ldperson p  where l.TempFeeNo=c.TempFeeNo and  l.APPntName = p.name and p.CustomerNo='"+ customerNo+"'" ;
 	
 	var mySql5=new SqlClass();
		mySql5.setResourceName("sys.PersonQuerySql"); //指定使用的properties文件名
		mySql5.setSqlId("PersonQuerySql5");//指定使用的Sql的id
		mySql5.addSubPara(customerNo);//指定传入的参数
	var strsql= mySql5.getString();
	
  var arrind = easyExecSql(strsql);
	   //判断是否查询成功
 	 if(arrind!=null){
        document.all('Pay').disabled='';
      }
      
  //既往付费
 	
  //var strsql="select AccName,SumGetMoney,Drawer,DrawerID,BankCode,BankAccNo from ljaget where AppntNo='"+customerNo+"'";
 	
 	var mySql6=new SqlClass();
		mySql6.setResourceName("sys.PersonQuerySql"); //指定使用的properties文件名
		mySql6.setSqlId("PersonQuerySql6");//指定使用的Sql的id
		mySql6.addSubPara(customerNo);//指定传入的参数
	var strsql= mySql6.getString();

  var arrind = easyExecSql(strsql);
	   //判断是否查询成功
 	 if(arrind!=null){
        document.all('Get').disabled='';
      }
  }
}