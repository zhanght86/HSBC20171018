/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2007 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zengyg <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2007-05-24
 * @direction: ��ȫ�����ղ�����ȡjs
 ******************************************************************************/
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "bq.PEdorTypeOPInputSql";

/**
 * ����������
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
        top.opener.getEdorItemGrid();
    }
    catch (ex) {}
}
function queryAccData()
{
	document.all('fmtransact').value ="OP||QUERY"; 
    var showStr="���ڽ��м��㣬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action="./PEdorTypeOPSubmit.jsp"
	fm.submit();
}
function afterCalData(aInsuAccBala,aCanGetMoney,aWorkNoteFee)
{
	showInfo.close();
  fm.InsuAccBala.value = aInsuAccBala;
  fm.CanGetMoney.value = aCanGetMoney;
  fm.WorkNoteFee.value = aWorkNoteFee;
  showActuWantMoney();
}
function edorTypeOPSave()
{
    if(document.all('tAAType').value == "1"){
        alert("�ñ���������ԥ���ڣ���������ȡ��");
        return; 
    }
    if(parseFloat(fm.GetMoney.value)>parseFloat(fm.CanGetMoney.value))
   {
  	    alert("������ȡ�����ڿ��ý�");
        return; 
  	
  	}
  	if(parseFloat(fm.GetMoney.value) < 1000 || parseFloat(fm.GetMoney.value) % 100 != 0)
  	{
  		  alert("��ȡ�������1000Ԫ,��Ϊ100Ԫ����������");
        return; 
  		}
	document.all('fmtransact').value ="OP||MAIN";  
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action="./PEdorTypeOPSubmit.jsp"
	fm.submit();
	//showActuWantMoney();
}

function afterSubmit( FlagStr, content,Result)
{
	showInfo.close();	
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showActuWantMoney();
	//initBackFeePolGrid();       
  //initBackFeeDetailGrid();   
	queryBackFee();              //�ύ֮���ٴβ�ѯ���˷���ϸ

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

function initRemark()
{
	  var edorAccpetNo  = document.all("EdorAcceptNo").value;
	  //var contNO = document.all("ContNo").value;
	  var edorType = document.all("EdorType").value;
//	  var strSql = "select standbyflag3 from lpedoritem where edoracceptno = '" + edorAccpetNo + "' and edortype = '" + edorType +"'";
    
    var strSql = "";
	var sqlid1="PEdorTypeOPInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(edorAccpetNo);//ָ������Ĳ���
	mySql1.addSubPara(edorType);
	strSql=mySql1.getString();
    
    var brr = easyExecSql(strSql);
    //�õ�ʱ��Ҫ�жϽ�����Ƿ���������������鲻���ᱨ��
    if(brr){
        document.all('Remark').value = brr[0][0];
    }
}

function showActuWantMoney(){
    var EdorNo = document.all('EdorNo').value;
    var edorAccpetNo  = document.all("EdorAcceptNo").value;
    //��ȡ���
    var tGetMoney,tGetMoneyFee;
/*    var strSql="select nvl(abs(sum(getmoney)),0.0) from lpedoritem where edoracceptno='"+edorAccpetNo
            +"'";
*/    
    var strSql = "";
	var sqlid2="PEdorTypeOPInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(edorAccpetNo);//ָ������Ĳ���
	strSql=mySql2.getString();
    
    var brr = easyExecSql(strSql);        
    if(brr){
        tGetMoney = brr[0][0];
    }else{
        tGetMoney = 0.0;
    }
   //��ѯ��ȡ������
/*   var strSql0="select nvl(StandbyFlag1,'') from lpedoritem where EdorNo='"+EdorNo
            +"' and edorType = 'OP'";
*/    
    var strSql0 = "";
	var sqlid3="PEdorTypeOPInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(EdorNo);//ָ������Ĳ���
	strSql0=mySql3.getString();
    
    var crr = easyExecSql(strSql0);
    //alert(crr);        
    if(crr){
        tGetMoneyFee = crr[0][0];
    }else{
        tGetMoneyFee = 0.0;
    }
    document.all('GetMoney').value=tGetMoney;
    document.all('WorkNoteFee').value=tGetMoneyFee;
}

function getPolInfo()
{
    
    var tContNo= document.all('ContNo').value;		  
    // ��дSQL���
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,grpcontno from LCPol where polno = mainpolno and ContNo='"+tContNo+"'";
    
    var strSQL = "";
	var sqlid4="PEdorTypeOPInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql4.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) 
    {
        return false;
    }
    
    //��ѯ�ɹ������ַ��������ض�ά����
    
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����

    turnPage.pageDisplayGrid = PolGrid;    
    //����SQL���
    turnPage.strQuerySql = strSQL; 
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;  
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
    
    
}