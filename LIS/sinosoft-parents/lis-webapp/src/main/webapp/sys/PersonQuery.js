//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

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


// ��ѯ�ͻ���Ϣ
function QueryClick()
{
	//��ʼ���������Ϣ
	  initAddressGrid();
      initAccountGrid();
      initDisplayButton();
    
    //�ж��Ƿ��в�ѯ����
    /**if(document.all('Name').value =="")
    {
        alert("������ͻ�������");
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
		mySql1.setResourceName("sys.PersonQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("PersonQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all('Name').value);//ָ������Ĳ���
		mySql1.addSubPara(document.all('IDNo').value);//ָ������Ĳ���
		mySql1.addSubPara(document.all('Sex').value);//ָ������Ĳ���
		mySql1.addSubPara(document.all('BIRTHDAY').value);//ָ������Ĳ���
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
//��ѯ�ͻ���ַ,�����˻���Ϣ
function QueryClick2(){
var tSel = CustomerGrid.getSelNo();
initDisplayButton();
    	if( tSel != 0 || tSel != null )
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        //var strsql1="select AddressNo,PostalAddress,Phone,HomeAddress,HomePhone,Mobile from lcaddress where CustomerNo='"+customerNo+"'";
        var mySql2=new SqlClass();
			mySql2.setResourceName("sys.PersonQuerySql"); //ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId("PersonQuerySql2");//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(customerNo);//ָ������Ĳ���
		var strsql1= mySql2.getString();
        turnPageAddressGrid.pageDivName = "divTurnAddressGrid";
        turnPageAddressGrid.queryModal(strsql1, AddressGrid);
        //var strsql2="select AccKind,BankCode,BankAccNo,AccName from lcaccount where CustomerNo='"+customerNo+"'";
        var mySql3=new SqlClass();
			mySql3.setResourceName("sys.PersonQuerySql"); //ָ��ʹ�õ�properties�ļ���
			mySql3.setSqlId("PersonQuerySql3");//ָ��ʹ�õ�Sql��id
			mySql3.addSubPara(customerNo);//ָ������Ĳ���
		var strsql2= mySql3.getString();
        turnPageAccountGrid.pageDivName = "divTurnPageAccountGrid";
        turnPageAccountGrid.queryModal(strsql2, AccountGrid);
        ButtonControl();
        }
}

// ������ѯ
function PolClick()
{
    var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼���ٵ����ϸ��ť��" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("PersonPol.jsp?CustomerNo="+customerNo+"&type=1","",sFeatures);
    }
}

// �����ѯ
function ClaimClick()
{
     var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼���ٵ����ϸ��ť��" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("../uw/ClaimQueryCusInput.jsp?CustomerNo="+customerNo);
    }
}


// ��ȫ��ѯ
function EdorClick()
{
    var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼���ٵ����ϸ��ť��" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("../uw/EdorQueryCusInput.jsp?CustomerNo="+customerNo);
    }
}

// ��ȡ��ѯ
function GetClick()
{
    var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼���ٵ����ϸ��ť��" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("../sys/GetQuery.jsp?CustomerNo="+customerNo);
    }
}

// �����շѲ�ѯ
function PayClick()
{
    var arrReturn = new Array();
    var tSel = CustomerGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼���ٵ����ϸ��ť��" );
    else
    {
        var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
        if (customerNo == "")
            return;
            window.open("../sys/PayQueryInput.jsp?CustomerNo="+customerNo);
    }
}

//��ʼ����ť����
function initDisplayButton()
{
        fm.Pol.disabled=true;
        fm.Edor.disabled=true;
        fm.Claim.disabled=true;
        fm.Pay.disabled=true;
        fm.Get.disabled=true;
  
}

//��ť��������
function ButtonControl(){
var tSel = CustomerGrid.getSelNo();
    	if( tSel != 0 || tSel != null )
    {
	var customerNo = CustomerGrid.getRowColData(tSel - 1,1);
	//����Ͷ��
	//var  strsql="select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where appntno='"+customerNo+"'";
	    var mySql4=new SqlClass();
			mySql4.setResourceName("sys.PersonQuerySql"); //ָ��ʹ�õ�properties�ļ���
			mySql4.setSqlId("PersonQuerySql4");//ָ��ʹ�õ�Sql��id
			mySql4.addSubPara(customerNo);//ָ������Ĳ���
		var strsql= mySql4.getString();
	var  arr = easyExecSql(strsql);
  	if(arr!=null){
  	fm.Pol.disabled='';
  	}
  	
  	//������ȫ
 	//var sqlind="select a.edorno,d.appntname,a.edorappdate,a.edorvalidate,(select b.codename from ldcode b where b.codetype='edorcontuwstate' and trim(a.uwstate)=trim(b.code)) " + " from LPEdorMain a,lccont d  where  d.contno=a.contno  and d.appntno='"+customerNo+"'";
    
    var sqlid1="EdorQueryCusSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.EdorQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
	    var sqlind=mySql1.getString();
	   
    var arrind = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind!=null){
        document.all('Edor').disabled='';
      }
  //��������
 	/**
  var sqlind= " select a.rptno, c.name, b.MedAccDate, b.AccDate, (case (select clmstate from llregister where rgtno=a.rptno)"
			+ " when '10' then '����' when '20' then '����' when '30' then"
			+ " '���' when '40' then '����' when '50' then '�᰸' when '60' then"
			+ " '���' when '70' then '�ر�' else '����' end)"
			+ " from llreport a, llsubreport b, ldperson c"
			+ " where a.rptno = b.subrptno"
			+ " and b.customerno = c.customerno"
			//�¼Ӱ��ͻ�����
			+ "and b.customerno='"+customerNo +"'";
			*/
  var sqlid1="ClaimQueryCusSql1";
	var mySql1=new SqlClass();
		mySql1.setResourceName("uw.ClaimQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
	var sqlind=mySql1.getString();	
  var arrind = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind!=null){
        document.all('Claim').disabled='';
      }


  //�����շ�
 	
  //var strsql="select l.TempFeeNo,c.PayMoney,l.APPntName,c.InBankAccNo,c.InAccName,c.IDNo,l.PayDate from LJTempFee l,LJTempFeeClass c where l.TempFeeNo=c.TempFeeNo and l.APPntName =(select name from ldperson where CustomerNo='"+ customerNo+"')" ;
  //var strsql="select l.TempFeeNo,c.PayMoney,l.APPntName,c.InBankAccNo,c.InAccName,c.IDNo,l.PayDate from LJTempFee l, LJTempFeeClass c ,ldperson p  where l.TempFeeNo=c.TempFeeNo and  l.APPntName = p.name and p.CustomerNo='"+ customerNo+"'" ;
 	
 	var mySql5=new SqlClass();
		mySql5.setResourceName("sys.PersonQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId("PersonQuerySql5");//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(customerNo);//ָ������Ĳ���
	var strsql= mySql5.getString();
	
  var arrind = easyExecSql(strsql);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind!=null){
        document.all('Pay').disabled='';
      }
      
  //��������
 	
  //var strsql="select AccName,SumGetMoney,Drawer,DrawerID,BankCode,BankAccNo from ljaget where AppntNo='"+customerNo+"'";
 	
 	var mySql6=new SqlClass();
		mySql6.setResourceName("sys.PersonQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId("PersonQuerySql6");//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(customerNo);//ָ������Ĳ���
	var strsql= mySql6.getString();

  var arrind = easyExecSql(strsql);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind!=null){
        document.all('Get').disabled='';
      }
  }
}