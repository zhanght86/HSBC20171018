//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var mFlag;
var sqlresourcename = "bq.PEdorTypeIOInputSql";

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage1 = new turnPageClass();
var turnPagePolOldGrid = new turnPageClass();
var turnPagePolNewGrid = new turnPageClass();

function resetForm()
{
    //initForm();
    parent.location.reload();
}

function saveEdorTypeIO()
{
    if ((fm.OccupationCode.value == fm.OccupationCode_S.value) && (fm.OccupationType.value == fm.OccupationType_S.value))
    {
        alert('����������δ���������');
        return;
    }
    if(fm.OccupationType.value == '�ܱ�')
    {
        //alert('ְҵ���ܱ�,���ܱ������룡');
        //return;
    }
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('fmtransact').value = "QUERY||DETAIL";
    fm.submit();
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=350;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            querynewPol();
            queryBackFee();
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
}

//��ѯ �ñ����˻������������µ�ְҵ������Ҫ���
function queryOccupationInfo()
{
    var tContNo=  document.all('ContNo').value;
    var tInsuredNo = document.all('InsuredNo').value;

    var strSQL;

/*    strSQL =  " Select 1 From lccont Where contno <> '"
                        + tContNo + "' And insuredno='"
                        + tInsuredNo + "'";
            //alert(strSQL);
*/    
	var sqlid1="PEdorTypeIOInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(tInsuredNo);
	strSQL=mySql1.getString();
    
    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        var showStr="���ѣ��ñ����˻������������µ�ְҵ������Ҫ�����" ;
            var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
            //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
  }
}

/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{
    var arrResult = new Array();

    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;

        document.all( 'CustomerNo' ).value = arrResult[0][1];
        document.all( 'name').value = arrResult[0][2];
        /**
        alert("aa:"+arrResult[0][5]);
        document.all('Nationality').value = arrResult[0][5];
        document.all('Marriage').value=arrResult[0][6];
        document.all('Stature').value=arrResult[0][7];
        document.all('Avoirdupois').value=arrResult[0][8];
        document.all('ICNo').value=arrResult[0][9];
        document.all('HomeAddressCode').value=arrResult[0][10];
        document.all('HomeAddress').value=arrResult[0][11];
        document.all('PostalAddress').value=arrResult[0][12];
        document.all('ZipCode').value=arrResult[0][13];
        document.all('Phone').value=arrResult[0][14];
        document.all('Mobile').value=arrResult[0][15];
        document.all('EMail').value=arrResult[0][16];
        */
        // ��ѯ������ϸ
        queryInsuredDetail();
    }
}
function queryInsuredDetail()
{
    var tEdorNO;
    var tEdorType;
    var tPolNo;
    var tCustomerNo;

    tEdorNo = document.all('EdorNO').value;
    //alert(tEdorNo);
    tEdorType=document.all('EdorType').value;
    //alert(tEdorType);
    tPolNo=document.all('PolNo').value;
    //alert(tPolNo);
    tCustomerNo = document.all('CustomerNo').value;
    //alert(tCustomerNo);
    //top.location.href = "./InsuredQueryDetail.jsp?EdorNo=" + tEdorNo+"&EdorType="+tEdorType+"&PolNo="+tPolNo+"&CustomerNo="+tCustomerNo;
    parent.fraInterface.fm.action = "./InsuredQueryDetail.jsp";
    fm.submit();
    parent.fraInterface.fm.action = "./PedorTypeIOSubmit.jsp";
}

/**
 * ����������
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}


function querytImpartGrid() {
     var tEdorNo = document.all('EdorNo').value;
     var tContNo = document.all('ContNo').value;
     var tInsuredNo = document.all('InsuredNo').value;
     
//     var Strvar = "select impartver,impartcode,impartcontent,impartparammodle from lpcustomerimpart where edortype = 'IO' and edorno = '"+ tEdorNo + "' and customerno = '" + tInsuredNo + "'";
     
    var Strvar = "";
    var sqlid2="PEdorTypeIOInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql2.addSubPara(tInsuredNo);
	Strvar=mySql2.getString();
    
     var arrSelected = new Array();
     turnPage.strQueryResult  = easyQueryVer3(Strvar, 1, 0, 1);
     //�ж��Ƿ��ѯ�ɹ�
     if (!turnPage.strQueryResult) {
          return false;
     }
     //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
     turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
     //��ѯ�ɹ������ַ��������ض�ά����
     turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
     //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
     turnPage.pageDisplayGrid = ImpartGrid;
     //����SQL���
     turnPage.strQuerySql = Strvar;
     //���ò�ѯ��ʼλ��
     turnPage.pageIndex = 0;
     //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
     arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
     //����MULTILINE������ʾ��ѯ���
     displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

/*********************************************************************
 * ǰ̨��ѯ�ͻ�������Ϣ
 *********************************************************************
 */

 function QueryCustomerInfo()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL=""
    if(tContNo!=null || tContNo !='')
      {
/*      strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
                            +"CONTNO='"+tContNo+"'";
*/    
    var sqlid3="PEdorTypeIOInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql3.getString();
    
    }
    else
    {
        alert('û�пͻ���Ϣ��');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
  if(!turnPage.strQueryResult){
        alert("��ѯʧ��");
    }
    else
    {
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //Ͷ��������
  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //Ͷ����֤������
  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //Ͷ����֤������
  //try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //����������
  //try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //������֤������
  //try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //������֤������

  var tInsuredNo=top.opener.document.all('InsuredNo').value;
    var strSQL2="";
    if(tInsuredNo!=null || tInsuredNo !='')
      {
/*      strSQL2 = "Select name,idtype,idno From lcinsured WHERE 1=1 AND "
                            +"INSUREDNO='"+tInsuredNo+"' and contno = '" +top.opener.document.all('ContNo').value+ "'";
*/    
    var sqlid4="PEdorTypeIOInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tInsuredNo);//ָ������Ĳ���
	mySql4.addSubPara(top.opener.document.all('ContNo').value);
	strSQL2=mySql4.getString();
    
    }
    else
    {
        alert('û�пͻ���Ϣ��');
    }
    var aResult = easyExecSql(strSQL2);
      if(aResult == null){
          alert("��ѯ��������Ϣʧ�ܣ�");
          return false;
      }
      try {document.all('InsuredName').value = aResult[0][0];} catch(ex) { }; //����������
  try {document.all('InsuredIDType').value = aResult[0][1];} catch(ex) { }; //������֤������
  try {document.all('InsuredIDNo').value = aResult[0][2];} catch(ex) { }; //������֤������

  showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
  showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');

  }
}
/*********************************************************************
 * ǰ̨��ѯ������ְҵ���ְҵ����
 *********************************************************************
 */

 function queryInsuredOccupationInfo()
{
    var tInsuredNo=top.opener.document.all('InsuredNo').value;
    var strSQL2="";
    if(tInsuredNo!=null || tInsuredNo !='')
      {
/*      strSQL = "Select occupationtype,occupationcode,worktype From lcinsured WHERE 1=1 AND "
                            +"INSUREDNO='"+tInsuredNo+"' and contno = '" +top.opener.document.all('ContNo').value+ "'";
*/    
    var sqlid5="PEdorTypeIOInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(tInsuredNo);//ָ������Ĳ���
	mySql5.addSubPara(top.opener.document.all('ContNo').value);
	strSQL2=mySql5.getString();
    
    }
    else
    {
        alert('û�пͻ���Ϣ��');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL2, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
  if(!turnPage.strQueryResult){
        alert("��ѯʧ��");
    }
    else
    {
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  try {document.all('OccupationCode_S').value = arrSelected[0][1];} catch(ex) { }; //ְҵ����
  try {document.all('OccupationType_S').value = arrSelected[0][0];} catch(ex) { }; //ְҵ���
  try {document.all('Occupation_S').value = arrSelected[0][2];} catch(ex) { }; //ְҵ���
  
  //showOneCodeName('OccupationCode','OccupationCode_S','OccupationCode_SName');
  fm.OccupationCode_SName.value=getOccupationCodeName(document.all('OccupationCode_S').value);
  showOneCodeName('occupationtype','OccupationType_S','OccupationType_SName');
  }
}
/*********************************************************************
 * ǰ̨��ѯĳһ��������������Ϣ����Grid��
 *********************************************************************
 */

function queryPolInfo()
{

    var tContNo = top.opener.document.all('ContNo').value;
    var tInsuredNo =top.opener.document.all('InsuredNo').value;
    var tDate =top.opener.document.all('EdorValiDate').value;

//    var str33 = "select * from lcpol where contno = '" + tContNo + "' and insuredno = '" + tInsuredNo + "'";
    
    var str33 = "";
    var sqlid6="PEdorTypeIOInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(tContNo);//ָ������Ĳ���
	mySql6.addSubPara(tInsuredNo);
	str33=mySql6.getString();
    
    var ssResult = easyExecSql(str33,1,0);
    if (ssResult == null)
    {
//        str33 = "select insuredno from lcpol where contno = '" + tContNo + "'";
        
    var sqlid7="PEdorTypeIOInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(tContNo);//ָ������Ĳ���
	str33=mySql7.getString();
        
        ssResult = easyExecSql(str33,1,0);
        tInsuredNo =ssResult[0][0];
    }
    var strSQL="";

    //strSQL =  "Select distinct a.polno,m.riskname, case when u.amntflag = 1 then a.amnt else a.mult end,a.prem - nvl(f.addprem, 0),nvl(f.addprem, 0) From lcpol a Left Join lmrisk m on m.riskcode = a.riskcode left join lcduty b on b.polno = a.polno left join lmduty u on trim(u.dutycode) = substr(b.dutycode, 1, 6) left join (Select c.polno, Sum(prem) addprem From lcprem c Where payplantype = '02' and ContNo = '"+top.opener.document.all('ContNo').value+"' group by c.polno) f On f.polno = a.polno Where a.insuredno = '"+tInsuredNo+"' and a.ContNo = '"+top.opener.document.all('ContNo').value+"' and a.appflag in ('1','9') and a.cvalidate <= '" + tDate + "' and a.enddate > '" + tDate + "'";
    //modify by jiaqiangli 2009-11-10 ��ȫ����ղ����� 
    //����Ҳ��������cvalidate��enddate ֻ��Ҫappflag='1'������ ǰ�߿��ܶ�������ԤԼ���������գ������ų��˿�ĩ����ı�ȫ���һ���ڶ�����
//    strSQL =  "Select distinct a.polno,m.riskname, case when u.amntflag = 1 then a.amnt else a.mult end,a.prem - nvl(f.addprem, 0),nvl(f.addprem, 0) From lcpol a Left Join lmrisk m on m.riskcode = a.riskcode left join lcduty b on b.polno = a.polno left join lmduty u on trim(u.dutycode) = substr(b.dutycode, 1, 6) left join (Select c.polno, Sum(prem) addprem From lcprem c Where payplantype = '02' and ContNo = '"+top.opener.document.all('ContNo').value+"' group by c.polno) f On f.polno = a.polno Where a.insuredno = '"+tInsuredNo+"' and a.ContNo = '"+top.opener.document.all('ContNo').value+"' and a.appflag in ('1')";
    //alert(strSQL);
    
    var sqlid8="PEdorTypeIOInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(top.opener.document.all('ContNo').value);//ָ������Ĳ���
	mySql8.addSubPara(tInsuredNo);
	mySql8.addSubPara(top.opener.document.all('ContNo').value);
	strSQL=mySql8.getString();
    
    try
    {
        turnPagePolOldGrid.pageDivName = "divTurnPagePolOldGrid";
        turnPagePolOldGrid.queryModal(strSQL, PolOldGrid);
    }
    catch (ex) {}
}

/***************************************************************
 * ��ְҵ�����ѯ��ְҵ������
 ***************************************************************
 */
function getdetailwork()
{
//var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";

	var strSql = "";
	var sqlid9="PEdorTypeIOInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(fm.OccupationCode.value);//ָ������Ĳ���
	strSql=mySql9.getString();

var arrResult = easyExecSql(strSql);
if (arrResult != null)
{
   fm.OccupationType.value = arrResult[0][0];
   showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
}
else
{
   fm.OccupationType.value = '';
}
}


function showOccupatioinInfo()
{
  var tInsuredNo=top.opener.document.all('InsuredNo').value;
  var tEdorNo=top.opener.document.all('EdorNo').value;
    var tEdorType=top.opener.document.all('EdorType').value;

  var strSQL="";

  if(tInsuredNo!=null || tInsuredNo !='')
    {
/*        strSQL = "select ";
      strSQL = "Select b.prem,b.prem1,occupationtype,occupationcode,worktype From lPinsured,(Select Sum(standprem) prem,Sum(prem-sumprem) prem1 From lppol Where edorno='"+tEdorNo+"' And edortype='IO')b Where insuredno='"+tInsuredNo+"' And contno='"+fm.ContNo.value+"' And edortype='"+fm.EdorType.value+"' and EdorNo = '"+top.opener.document.all('EdorType').value+"'";
      //alert("-----------"+strSQL+"------------");
*/      
	var sqlid10="PEdorTypeIOInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
	mySql10.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql10.addSubPara(tInsuredNo);
	mySql10.addSubPara(fm.ContNo.value);
	mySql10.addSubPara(fm.EdorType.value);
	mySql10.addSubPara(top.opener.document.all('EdorType').value);
	strSQL=mySql10.getString();
      
    }
    else
    {
        alert('û�пͻ���Ϣ��');
    }
    var arrSelected = new Array();
    turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
  //alert(strSQL);
  //alert(turnPage1.strQueryResult);
  if(!turnPage1.strQueryResult){
        //alert("��ѯʧ��");
    }
    else
    {
  arrSelected = decodeEasyQueryResult(turnPage1.strQueryResult);
  try {document.all('OccupationType').value = arrSelected[0][2];} catch(ex) { }; //ְҵ����
  try {document.all('OccupationCode').value = arrSelected[0][3];} catch(ex) { }; //ְҵ���
  try {document.all('Occupation').value = arrSelected[0][4];} catch(ex) { }; //ְҵ

  fm.OccupationCodeName.value=getOccupationCodeName(document.all('OccupationCode').value);
  //showOneCodeName('occupationcode','OccupationCode','OccupationCodeName');
  showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
}

}
function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = top.opener.document.all("ActivityID").value;;
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}


function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}

function querychgOcc(){
    var tInsuredNo=top.opener.document.all('InsuredNo').value;
    var tEdorNo = top.opener.document.all('EdorNo').value;
    var strSQL=""
    if(tInsuredNo!=null || tInsuredNo !='')
      {
/*      strSQL = "Select occupationtype,occupationcode,worktype From lpinsured WHERE 1=1 AND "
                            +"INSUREDNO='"+tInsuredNo+"' and EdorNo = '" + tEdorNo +"'";
    //alert("-----------"+strSQL+"------------");
*/    
    var sqlid11="PEdorTypeIOInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(tInsuredNo);//ָ������Ĳ���
	mySql11.addSubPara(tEdorNo);
	strSQL=mySql11.getString();
    
    }
    else
    {
        alert('û�пͻ���Ϣ��');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
    if (turnPage.strQueryResult)
    {
        arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
        try {document.all('OccupationCode').value = arrSelected[0][1];} catch(ex) { }; //ְҵ����
        try {document.all('OccupationType').value = arrSelected[0][0];} catch(ex) { }; //ְҵ���
        try {document.all('Occupation').value = arrSelected[0][2];} catch(ex) { }; //ְҵ
            
        fm.OccupationCodeName.value=getOccupationCodeName(document.all('OccupationCode').value);
      //  showOneCodeName('occupationcode','OccupationCode','OccupationCodeName');
        showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
        querynewPol();
    }
}

function querynewPol()
{
    var tInsuredNo=document.all('InsuredNo').value;
    var tEdorno = document.all('EdorNo').value;
    var strSQL="";
/*    strSQL =  "Select distinct a.polno,m.riskname,case when u.amntflag = 1 then a.amnt else a.mult end,a.prem,nvl((select sum(getmoney) from LJSGetEndorse where polno = a.polno and endorsementno = a.edorno),0) From lppol a Left Join lmrisk m on m.riskcode = a.riskcode Left Join lcduty b on b.polno = a.polno Left Join LMDuty u on trim(u.dutycode) = substr(b.dutycode, 1, 6) where a.edortype = 'IO' and a.EdorNo = '"+tEdorno+"' and a.ContNo = '" + fm.ContNo.value + "'";
    //alert(strSQL);
*/    
    var sqlid12="PEdorTypeIOInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	mySql12.addSubPara(tEdorno);//ָ������Ĳ���
	mySql12.addSubPara(fm.ContNo.value);
	strSQL=mySql12.getString();
    
    try
    {
        turnPagePolNewGrid.pageDivName = "divTurnPagePolNewGrid";
        turnPagePolNewGrid.queryModal(strSQL, PolNewGrid);
    }
    catch (ex) {}
}

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}

function getOccupationCodeName(OccupationCode)
{
//	var tSQL="select  OccupationName from LDOccupation where OccupationCode='"+OccupationCode+"'";
	
	var tSQL="";
	var sqlid13="PEdorTypeIOInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
	mySql13.addSubPara(OccupationCode);//ָ������Ĳ���
	tSQL=mySql13.getString();
	
	var ssResult = easyExecSql(tSQL,1,0);
    if (ssResult != null)
    {
        return ssResult[0][0];
    }		
	}    