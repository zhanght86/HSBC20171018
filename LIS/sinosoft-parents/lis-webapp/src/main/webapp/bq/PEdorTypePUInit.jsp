<%
//�������ƣ�PEdorInputInit.jsp
//�����ܣ�
//�������ڣ�2003-01-08 
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/���� 
//						Lihs			2005-4-27 01:50����  ������
%>                          

<script language="JavaScript">  
function initInpBox()
{ 
  try
  {   
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value; 
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value; 
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value; 
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;  
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('TempSaveField').value = document.all('InsuYear').value + document.all('GetDutyKind').value;
    document.all('IninSuccessFlag').value = "0";
    //document.all('ContType').value ="1";
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    QueryCustomerInfo();
    initQueryPolInfo();
    //initLCInsuredGrid();
    //QueryEdorInfo();
    queryNewData();
  }
 catch(re)
  {
    alert("PEdorTypePUInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initQueryPolInfo()
{
	var tPolNo=top.opener.document.all('PolNo').value;
	var strSQL=""
	if(tPolNo!=null || tPolNo !='')
	{
	  strSQL = "SELECT M.RISKCODE,M.RISKNAME,C.APPNTNO,C.APPNTNAME,C.INSUREDNO,C.INSUREDNAME,C.MULT,C.PREM,C.AMNT,C.PAYTODATE FROM LCPOL C,LMRISK M WHERE C.RISKCODE = M.RISKCODE AND "
							+"POLNO='"+tPolNo+"'";
	}
	else
	{
		alert('û����Ӧ�����ִ��룡');
	}
	var arrSelected = new Array();		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
  if(!turnPage.strQueryResult)
  {
		alert("��ѯʧ�ܣ�");
	}
	else
	{
	  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	  try {document.all('RiskCode').value = arrSelected[0][0];} catch(ex) { }; //���ִ���
	  try {document.all('RiskName').value = arrSelected[0][1];} catch(ex) { }; //��������
	  //try {document.all('AppntNo').value = arrSelected[0][2];} catch(ex) { }; //Ͷ���˺���
	  try {document.all('AppntName').value = arrSelected[0][3];} catch(ex) { }; //Ͷ��������
	  //try {document.all('InsuredNo').value = arrSelected[0][4];} catch(ex) { }; //�����˺���
	  //try {document.all('InsuredName').value = arrSelected[0][5];} catch(ex) { }; //����������
	  try {document.all('Mult').value = arrSelected[0][6];} catch(ex) { }; //����
	  try {document.all('Prem').value = arrSelected[0][7];} catch(ex) { }; //����
	  try {document.all('Amnt').value = arrSelected[0][8];} catch(ex) { }; //����
	  try {document.all('PaytoDate').value = arrSelected[0][9];}catch(ex){}; //��������
	  //���������жϸ���ʾ����Ϣ����Ը������֣����ڽ�����̶�д
	  strSQL = " select distinct 1 from Lmrisksort where riskcode = '" + document.all('RiskCode').value + "' and risksorttype='31' ";
  
  	var insu = easyExecSql(strSQL);
		
		if(insu!=null)
	  
	  {
	   	showDiv(divIntv,"true");
	  	document.all('InsuYear').verify = "�����ڼ�|notnull&num";
	  	document.all('SpecialRiskFlag').value = "Y";
	  	
	  }
	 	strSQL = " select distinct 1 from Lmrisksort where riskcode = '" + document.all('RiskCode').value + "' and risksorttype='32' ";
  
  	var gdkind = easyExecSql(strSQL);
  	
	  if(gdkind!=null)
	  
	  {
	  	showDiv(divGetMode,"true");
	  	document.all('GetDutyKind').verify = "��ȡ��ʽ|notnull&num";
	  	document.all('SpecialRiskFlag').value = "Y";
	  }
  }
}

 function QueryCustomerInfo()
{
	var tContNo=top.opener.document.all('ContNo').value;
	var strSQL=""
	//alert("------"+tContNo+"---------");
	if(tContNo!=null || tContNo !='')
	  {
	  strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
							+"CONTNO='"+tContNo+"'";
	//alert("-----------"+strSQL+"------------");
	}
	else
	{
		alert('û�пͻ���Ϣ��');
	}
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	
	//arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0); 
  if(!turnPage.strQueryResult){
		alert("��ѯʧ�ܣ�");
	}
	else
	{
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //Ͷ��������
  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //Ͷ����֤������
  showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //Ͷ����֤������ 
  try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //����������
  try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //������֤������
  showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
  try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //������֤������ 
  
  //showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
  //showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
  }
}
//������Ϣ�б�
function initLCInsuredGrid()
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
      iArray[1][0]="���ִ���";					//����1
      iArray[1][1]="50px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����2
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������/����";         		//����8
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�ۻ���������";         		//����8
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���˺���";         		//����8
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="���ѱ�׼";         		//����8
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;
      
      iArray[7]=new Array();
      iArray[7][0]="���Ѷ�Ӧ��";         		//����8
      iArray[7][1]="50px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;

      LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      //LCInsuredGrid.mulLineCount = 4;   
      //LCInsuredGrid.displayTitle = 1;
      //LCInsuredGrid.canSel=0;
      //LCInsuredGrid.hiddenPlus = 1; 
      //LCInsuredGrid.hiddenSubtraction = 1;
      //LCInsuredGrid.selBoxEventFuncName ="";
      //LCInsuredGrid.loadMulLine(iArray);  
      //LCInsuredGrid.detailInfo="������ʾ��ϸ��Ϣ";
	    LCInsuredGrid.displayTitle = 1;
	    LCInsuredGrid.hiddenPlus = 1;
	    LCInsuredGrid.hiddenSubtraction = 1;
	    LCInsuredGrid.loadMulLine(iArray);  

      var strSQL ="select RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Amnt,'','',Prem from LCPol where ContNo='"+document.all('ContNo').value+"'";
      easyQueryVer3Modal(strSQL,LCInsuredGrid);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initPolGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ֺ�";
        iArray[1][1]="140px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="���ִ���";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="170px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��������ִ���";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;        
        
        iArray[5]=new Array();
        iArray[5][0]="�������������";
        iArray[5][1]="170px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="�ۼƺ�������";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="���˺���";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=3;
                         
        iArray[8]=new Array();
        iArray[8][0]="�ֽ��ֵ";
        iArray[8][1]="0px";
        iArray[8][2]=100;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="�����ֱ��� ";
        iArray[9][1]="0px";
        iArray[9][2]=100;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="ԭ��������";
        iArray[10][1]="0px";
        iArray[10][2]=100;
        iArray[10][3]=3;
        
        iArray[11]=new Array();
        iArray[11][0]="ԭ���ѱ�׼";
        iArray[11][1]="0px";
        iArray[11][2]=100;
        iArray[11][3]=3;
        
        iArray[12]=new Array();
        iArray[12][0]="ԭ����";
        iArray[12][1]="0px";
        iArray[12][2]=100;
        iArray[12][3]=3;
                 
        iArray[13]=new Array();
        iArray[13][0]="ԭ����";
        iArray[13][1]="0px";
        iArray[13][2]=100;
        iArray[13][3]=3;

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid.selBoxEventFuncName ="getSelNo" ;     //���RadioBoxʱ��Ӧ��JS����
      PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);        
      //��Щ����������loadMulLine����
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>