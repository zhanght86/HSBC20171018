<%
//GEdorTypeICInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">  
//����ʱ��ѯ
function reportDetailClick(parm1,parm2)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  	ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  	divLPInsuredDetail.style.left=ex;
  	divLPInsuredDetail.style.top =ey;
   	detailQueryClick();
}
function initInpBox()
{ 
	//alert("this is initInpBox method form!");
  try
  {        
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('GrpContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
    document.all('EdorTypeCal').value = top.opener.document.all('EdorTypeCal').value;
    document.all('ContNo').value = top.opener.document.all('ContNoBak').value;
    document.all('CustomerNo').value = top.opener.document.all('CustomerNoBak').value;
    document.all('CustomerNoBak').value = top.opener.document.all('CustomerNoBak').value;
    document.all('Name').value = '';
    document.all('Sex').value ='';
    document.all('Birthday').value = '';
    document.all('IDType').value = '';
    document.all('IDNo').value = '';
    document.all('ContType').value ='2';
    document.all('Marriage').value = '';
    document.all('NativePlace').value = '';
    document.all('OccupationType').value = '';
    document.all('OccupationCode').value = '';
  }
  catch(ex)
  {
    alert("��GEdorTypeICInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��GEdorTypeICInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
	//alert("this is initInpBox method form!");
  try
  {
    initInpBox();
    initSelBox(); 
    initPolDetailGrid();
    initQuery(); 
//    ctrlGetEndorse(); 
  }
  catch(re)
  {
    alert("GEdorTypeICInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
// ��Ϣ�б�ĳ�ʼ��
function initPolDetailGrid()
{
    var iArray = new Array();
      
      try
      {

      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���˱�����";					//����1
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ���";         			//����2
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ֱ�����";         		//����8
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ִ���";         			//����5
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;						//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����5
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[6]=new Array();
      iArray[6][0]="����";     //����6
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[7]=new Array();
		iArray[7][0]="����";         		
		iArray[7][1]="50px";            		 
		iArray[7][2]=60;            			
		iArray[7][3]=2;              		
		iArray[7][4]="Currency";              	  
		iArray[7][9]="����|code:Currency";

      PolDetailGrid = new MulLineEnter( "fm" , "PolDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolDetailGrid.mulLineCount = 5;   
      PolDetailGrid.displayTitle = 1;
      PolDetailGrid.canSel=0;
      PolDetailGrid.hiddenPlus = 1; 
      PolDetailGrid.hiddenSubtraction = 1;
      PolDetailGrid.loadMulLine(iArray);  
      }     
      
      catch(ex)
      {
        alert(ex);
      }
}

function initMoneyDetailGrid()
{
    var iArray = new Array();
    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;
      
      iArray[1]=new Array();
      iArray[1][0]="��/�˷�֪ͨ�����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="80px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��/�˷Ѳ�������";					//����1
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�������ֺ���";         			//����2
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

	 		iArray[4]=new Array();
      iArray[4][0]="����";         			//����2
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��/�˷�����";         		//����8
      iArray[5][1]="70px";            		//�п�
      iArray[5][2]=30;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";     //����6
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=50;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="���α���";     //����6
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=50;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="��������";     //����6
      iArray[8][1]="70px";            		//�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�ɷѼƻ�����";     //����6
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=50;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�ɷѼƻ�����";     //����6
      iArray[10][1]="70px";            		//�п�
      iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="�ο���/�˷ѽ��(Ԫ)";         		//����8
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=30;            			//�����ֵ
      iArray[11][3]=0;


      iArray[12]=new Array();
      iArray[12][0]="Э�鲹/�˷ѽ��(Ԫ)";         		//����8
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=30;            			//�����ֵ
      iArray[12][3]=2;
	  iArray[12][9]="��/�˷�|NOTNULL&NUM";  

		iArray[13]=new Array();
		iArray[13][0]="����";         		
		iArray[13][1]="50px";            		 
		iArray[13][2]=60;            			
		iArray[13][3]=2;              		
		iArray[13][4]="Currency";              	  
		iArray[13][9]="����|code:Currency"; 

      MoneyDetailGrid = new MulLineEnter( "fm" , "MoneyDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ        
      MoneyDetailGrid.mulLineCount = 10;   
      MoneyDetailGrid.displayTitle = 1;
      MoneyDetailGrid.canSel=0;
      MoneyDetailGrid.hiddenPlus = 1; 
      MoneyDetailGrid.hiddenSubtraction = 1;
      MoneyDetailGrid.selBoxEventFuncName ="";
      MoneyDetailGrid.loadMulLine(iArray);  
      MoneyDetailGrid.detailInfo="������ʾ��ϸ��Ϣ";      
      MoneyDetailGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initQuery()
{	
  //var tsql = "select name,Sex,Birthday,IDType,decode(IDType,'0',decode(length(IDNo),15,trans1(IDNo),IDNo),IDNo) ,OccupationType,OccupationCode,(select occupationname from ldoccupation where OccupationCode = lcinsured.OccupationCode),nvl(socialinsuflag,0) from lcinsured where contno='"+document.all('ContNo').value+"' and insuredno='"+document.all('CustomerNo').value+"'";
  
  var sqlid903103416="DSHomeContSql903103416";
var mySql903103416=new SqlClass();
mySql903103416.setResourceName("bq.GEdorTypeICInitSql"); //ָ��ʹ�õ�properties�ļ���
mySql903103416.setSqlId(sqlid903103416); //ָ��ʹ�õ�Sql��id
mySql903103416.addSubPara(document.all('ContNo').value); //ָ������Ĳ���
mySql903103416.addSubPara(document.all('CustomerNo').value); //ָ������Ĳ���
var tsql=mySql903103416.getString();
  
//  var tsql = "select name,Sex,Birthday,IDType,IDNo,OccupationType,OccupationCode,(select occupationname from ldoccupation where OccupationCode = lcinsured.OccupationCode),nvl(socialinsuflag,0) from lcinsured where contno='"+document.all('ContNo').value+"' and insuredno='"+document.all('CustomerNo').value+"'";
  var arrResult = easyExecSql(tsql);  
  try {document.all('NameBak').value = arrResult[0][0];} catch(ex) { };  
  try {document.all('SexBak').value = arrResult[0][1];} catch(ex) { };
  try {document.all('BirthdayBak').value = arrResult[0][2];} catch(ex) { };
  try {document.all('IDTypeBak').value = arrResult[0][3];} catch(ex) { };
  try {document.all('IDNoBak').value = arrResult[0][4];} catch(ex) { };
  try {document.all('OccupationTypeBak').value = arrResult[0][5];} catch(ex) { };  
  try {document.all('OccupationCodeBak').value = arrResult[0][6];} catch(ex) { };
  try {document.all('OccupationCodeNameBak').value = arrResult[0][7];} catch(ex) { };
  try {document.all('SocialInsuFlagBak').value = arrResult[0][8];} catch(ex) { };    
  
  //var psql = "select name,Sex,Birthday,IDType,decode(IDType,'0',decode(length(IDNo),15,trans1(IDNo),IDNo),IDNo),OccupationType,OccupationCode,(select occupationname from ldoccupation where OccupationCode = lpinsured.OccupationCode),nvl(socialinsuflag,0) from lpinsured where edorno='"+document.all('EdorNo').value+"' and edortype='IC' and contno='"+document.all('ContNo').value+"' and insuredno='"+document.all('CustomerNo').value+"'";
  
  var sqlid903103613="DSHomeContSql903103613";
var mySql903103613=new SqlClass();
mySql903103613.setResourceName("bq.GEdorTypeICInitSql"); //ָ��ʹ�õ�properties�ļ���
mySql903103613.setSqlId(sqlid903103613); //ָ��ʹ�õ�Sql��id
mySql903103613.addSubPara(document.all('EdorNo').value); //ָ������Ĳ���
mySql903103613.addSubPara(document.all('ContNo').value); //ָ������Ĳ���
mySql903103613.addSubPara(document.all('CustomerNo').value); //ָ������Ĳ���
var psql=mySql903103613.getString();
  
//  var psql = "select name,Sex,Birthday,IDType,IDNo,OccupationType,OccupationCode,(select occupationname from ldoccupation where OccupationCode = lpinsured.OccupationCode),nvl(socialinsuflag,0) from lpinsured where edorno='"+document.all('EdorNo').value+"' and edortype='IC' and contno='"+document.all('ContNo').value+"' and insuredno='"+document.all('CustomerNo').value+"'";
  arrResult = easyExecSql(psql);
  //alert(arrResult);
  if(arrResult != null && arrResult != "" && arrResult != "null"){
  try {document.all('Name').value = arrResult[0][0];} catch(ex) { };  
  try {document.all('Sex').value = arrResult[0][1];} catch(ex) { };
  try {document.all('Birthday').value = arrResult[0][2];} catch(ex) { };
  try {document.all('IDType').value = arrResult[0][3];} catch(ex) { };
  try {document.all('IDNo').value = arrResult[0][4];} catch(ex) { };
  try {document.all('OccupationType').value = arrResult[0][5];} catch(ex) { }; 
  showOneCodeName('occupationtype', 'OccupationType', 'OccupationTypeName'); 
  try {document.all('OccupationCode').value = arrResult[0][6];} catch(ex) { }; 
  try {document.all('OccupationCodeName').value = arrResult[0][7];} catch(ex) { }; 
  try {document.all('SocialInsuFlag').value = arrResult[0][8];} catch(ex) { };      
  }else{  
  try {document.all('Name').value = document.all('NameBak').value;} catch(ex) { };  
  try {document.all('Sex').value = document.all('SexBak').value;} catch(ex) { };
  try {document.all('Birthday').value = document.all('BirthdayBak').value;} catch(ex) { };
  try {document.all('IDType').value = document.all('IDTypeBak').value;} catch(ex) { };
  try {document.all('IDNo').value = document.all('IDNoBak').value;} catch(ex) { };
  try {document.all('OccupationType').value = document.all('OccupationTypeBak').value;} catch(ex) { };  
  showOneCodeName('occupationtype', 'OccupationType', 'OccupationTypeName');
  try {document.all('OccupationCode').value = document.all('OccupationCodeBak').value;} catch(ex) { }; 
  try {document.all('OccupationCodeName').value = document.all('OccupationCodeNameBak').value;} catch(ex) { }; 
  try {document.all('SocialInsuFlag').value = document.all('SocialInsuFlagBak').value;} catch(ex) { }; 
  }  
  
  Qrygetmoney();
  //alert(11111111)
  queryPolDetail();
   	 	 
}

function CondQueryClick()
{
	var i = 0;
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	
	document.all('fmtransact').value = "QUERY||MAIN";
	var tContNo = document.all('ContNo').value;
	var tCustomerNo=document.all('CustomerNo1').value;
	
	if (tContNo==null&&tContNo==''&&tCustomerNo!=null&&tCustomerNo!='')
		mFlag = "P";
	else if(tCustomerNo==null&&tCustomerNo==''&&tContNo!=null&&tContNo!='')
		mFlag = "C";
	else if (tCustomerNo==null&&tCustomerNo==''&&tContNo==null&&tContNo=='')
		mFlag = "N";
	else
		mFlag = "A";
	fm.submit();
}

function detailQueryClick()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   	
	document.all('fmtransact').value = "QUERY||DETAIL";
	
	var tSel=LCInsuredGrid.getSelNo();
	
	if (tSel==0||tSel==null)
		alert("�����ǿռ�¼!");
	else
	{
		var tCustomerNo =LCInsuredGrid.getRowColData(tSel-1,2);
		document.all('CustomerNo1').value =tCustomerNo;
		document.all('ContNo').value =LCInsuredGrid.getRowColData(tSel-1,1);
		fm.submit();
	}
}
function initDiv()
{
	divLPInsuredDetail.style.display ='none';
	divDetail.style.display='none';
}

function Qrygetmoney()
{
  var sqlid903103731="DSHomeContSql903103731";
var mySql903103731=new SqlClass();
mySql903103731.setResourceName("bq.GEdorTypeICInitSql"); //ָ��ʹ�õ�properties�ļ���
mySql903103731.setSqlId(sqlid903103731); //ָ��ʹ�õ�Sql��id
mySql903103731.addSubPara(document.all('EdorNo').value); //ָ������Ĳ���
mySql903103731.addSubPara(document.all('ContNo').value); //ָ������Ĳ���
var tsql=mySql903103731.getString();
  
//  var tsql = "select 1 from LJSGetEndorse  where EndorsementNo = '"+document.all('EdorNo').value+"' and FeeOperationType = 'IC' and contno='"+document.all('ContNo').value+"' ";
  var arrResult = easyExecSql(tsql, 1, 0, 1);
  divGetEndorseInfo.style.display='none';
  	if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		divGetEndorseInfo.style.display='';
  		initMoneyDetailGrid();
  		
  		var sqlid903103902="DSHomeContSql903103902";
var mySql903103902=new SqlClass();
mySql903103902.setResourceName("bq.GEdorTypeICInitSql"); //ָ��ʹ�õ�properties�ļ���
mySql903103902.setSqlId(sqlid903103902); //ָ��ʹ�õ�Sql��id
mySql903103902.addSubPara(fm.EdorNo.value); //ָ������Ĳ���
mySql903103902.addSubPara(fm.EdorType.value); //ָ������Ĳ���
mySql903103902.addSubPara(fm.ContNo.value); //ָ������Ĳ���
tsql=mySql903103902.getString();

  		
//  		tsql = "Select GetNoticeNo, decode(FeeFinaType,'BF','����','TF','�˷�','����'), PolNo,(select insuredname from lccont where contno=LJSGetEndorse.contno), GetDate, RiskCode, dutycode,(select dutyname from lmduty where dutycode = LJSGetEndorse.dutycode),payplancode,(select payplanname from lmdutypay where payplancode = LJSGetEndorse.payplancode),SerialNo,GetMoney From LJSGetEndorse where otherno='"+fm.EdorNo.value+"' and FeeOperationType='"+fm.EdorType.value+"' and ContNo = '"+fm.ContNo.value+"' order by riskcode";
  		turnPage2.queryModal(tsql, MoneyDetailGrid);
  	}
  
}
</script>
