<%
//�������ƣ�ProposalInput.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  
  }
  catch(ex)
  {
    alert("��ProposalInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

// ��������Ϣ�б�ĳ�ʼ��

function initSubInsuredGrid()
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
      iArray[1][0]="���������˿ͻ���";    	//����
      iArray[1][1]="180px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="����";         			//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ա�";         			//����
      iArray[3][1]="140px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="140px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�뱻���˹�ϵ";         		//����
      iArray[5][1]="160px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      SubInsuredGrid = new MulLineEnter( "fm" , "SubInsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SubInsuredGrid.mulLineCount = 1;   
      SubInsuredGrid.displayTitle = 1;
      //SubInsuredGrid.tableWidth = 200;
      SubInsuredGrid.hiddenPlus = 1;

      SubInsuredGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //SubInsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ��������Ϣ�б�ĳ�ʼ��
function initBnfGrid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="���"; 			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";		//�п�
    iArray[0][2]=10;			//�����ֵ
    iArray[0][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
  
    iArray[1]=new Array();
    iArray[1][0]="���������"; 		//����
    iArray[1][1]="80px";		//�п�
    iArray[1][2]=40;			//�����ֵ
    iArray[1][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

   
    iArray[2]=new Array();
    iArray[2][0]="����������"; 	//����
    iArray[2][1]="80px";		//�п�
    iArray[2][2]=40;			//�����ֵ
    iArray[2][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
   
    iArray[3]=new Array();
    iArray[3][0]="�Ա�"; 	//����
    iArray[3][1]="30px";		//�п�
    iArray[3][2]=100;			//�����ֵ
    iArray[3][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

  
    iArray[4]=new Array();
    iArray[4][0]="֤������"; 		//����
    iArray[4][1]="60px";		//�п�
    iArray[4][2]=80;			//�����ֵ
    iArray[4][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

  
    iArray[5]=new Array();
    iArray[5][0]="֤������"; 		//����
    iArray[5][1]="150px";		//�п�
    iArray[5][2]=80;			//�����ֵ
    iArray[5][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
   
  
    iArray[6]=new Array();
    iArray[6][0]="��������"; 		//����
    iArray[6][1]="80px";		//�п�
    iArray[6][2]=80;			//�����ֵ
    iArray[6][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

  
    iArray[7]=new Array();
    iArray[7][0]="�뱻���˹�ϵ"; 	//����
    iArray[7][1]="90px";		//�п�
    iArray[7][2]=100;			//�����ֵ
    iArray[7][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

  
    iArray[8]=new Array();
    iArray[8][0]="�������"; 		//����
    iArray[8][1]="60px";		//�п�
    iArray[8][2]=80;			//�����ֵ
    iArray[8][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
   
  
    iArray[9]=new Array();
    iArray[9][0]="����˳��"; 		//����
    iArray[9][1]="60px";		//�п�
    iArray[9][2]=80;			//�����ֵ
    iArray[9][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

  
    iArray[10]=new Array();
    iArray[10][0]="��ϵ��ַ"; 		//����
    iArray[10][1]="400px";		//�п�
    iArray[10][2]=240;			//�����ֵ
    iArray[10][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������0

  
    iArray[11]=new Array();
    iArray[11][0]="�ʱ�"; 		//����
    iArray[11][1]="60px";		//�п�
    iArray[11][2]=80;			//�����ֵ
    iArray[11][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

  
    iArray[12]=new Array();
    iArray[12][0]="�绰"; 		//����
    iArray[12][1]="100px";		//�п�
    iArray[12][2]=80;			//�����ֵ
    iArray[12][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

  
    BnfGrid = new MulLineEnter( "fm" , "BnfGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    BnfGrid.mulLineCount = 1; 
    BnfGrid.displayTitle = 1;
    BnfGrid.hiddenPlus = 1;

    BnfGrid.loadMulLine(iArray); 

  } catch(ex) {
    alert(ex);
  }
}

// ��֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪�ͻ�����";         		//����
      iArray[1][1]="90px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������



      iArray[3]=new Array();
      iArray[3][0]="��֪���";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������



      iArray[4]=new Array();
      iArray[4][0]="��֪����";         		//����
      iArray[4][1]="360px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��д����";         		//����
      iArray[5][1]="200px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 1;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.hiddenPlus = 1;

      ImpartGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// �ر�Լ����Ϣ�б�ĳ�ʼ��
function initSpecGrid() {                               
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��Լ����";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="��Լ����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="��Լ����";         		//����
      iArray[3][1]="740px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" );
      //��Щ���Ա�����loadMulLineǰ
      SpecGrid.mulLineCount = 1;   
      SpecGrid.displayTitle = 1;
      SpecGrid.hiddenPlus = 1;

      SpecGrid.loadMulLine(iArray);           
      //��Щ����������loadMulLine����
      //SpecGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�����б�
function initDutyGrid()
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
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��ȡ����";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      DutyGrid.mulLineCount = 0;   
      DutyGrid.displayTitle = 1;
      DutyGrid.hiddenPlus = 1;
      DutyGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


function emptyForm() {
	emptyFormElements();     //���ҳ�������������COMMON��JS��ʵ��
	
	//initInpBox();
	//initSelBox();    
	initSubInsuredGrid();
	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	
}

function initForm() {
	try	{
	    
	     
		if (loadFlag == "2") {	//�����¸���Ͷ����¼��
			var tRiskCode = parent.VD.gVSwitch.getVar( "RiskCode" );
			getRiskInput( tRiskCode, loadFlag );
			
		}
		
		if (loadFlag == "3") {	//���˱�����ϸ��ѯ
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			//alert("polno:"+tPolNo);
			queryPolDetail( tPolNo );
			
		}

		if (loadFlag == "4") {	//�����¸��˱�����ϸ��ѯ
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			
            //alert("polno:"+tPolNo);
			queryPolDetail( tPolNo );
		}

		if (loadFlag == "5") {	//����Ͷ������ϸ��ѯ
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}
		if (loadFlag == "6") {	//��������������ϸ��ѯ
		   	var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryDescPolDetail( tPolNo );
		}
		if (loadFlag == "7") {	//�����¸�������������ϸ��ѯ
		    var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryDescPolDetail( tPolNo );
		}
		
	} catch(ex) {
	}			
}

//ȡ�ü���Ͷ��������Ϣ
function getGrpPolInfo()
{

	try { document.all( 'ContNo' ).value = parent.VD.gVSwitch.getVar( "ContNo" ); } catch(ex) { };
	try { document.all( 'RiskCode' ).value = parent.VD.gVSwitch.getVar( "RiskCode" ); } catch(ex) { };
	try { document.all( 'RiskVersion' ).value = parent.VD.gVSwitch.getVar( "RiskVersion" ); } catch(ex) { };
	try { document.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate" ); } catch(ex) { };

	try { document.all( 'PrtNo' ).value = parent.VD.gVSwitch.getVar( "PrtNo" ); } catch(ex) { };
	try { document.all( 'GrpPolNo' ).value = parent.VD.gVSwitch.getVar( "GrpProposalNo" ); } catch(ex) { };

	try { document.all( 'ManageCom' ).value = parent.VD.gVSwitch.getVar( "ManageCom" ); } catch(ex) { };
	try { document.all( 'SaleChnl' ).value = parent.VD.gVSwitch.getVar( "SaleChnl" ); } catch(ex) { };
	try { document.all( 'AgentCom' ).value = parent.VD.gVSwitch.getVar( "AgentCom" ); } catch(ex) { };
	try { document.all( 'AgentCode' ).value = parent.VD.gVSwitch.getVar( "AgentCode" ); } catch(ex) { };
	try { document.all( 'AgentGroup' ).value = parent.VD.gVSwitch.getVar( "AgentGroup" ); } catch(ex) { };
	try { document.all( 'AgentCode1' ).value = parent.VD.gVSwitch.getVar( "AgentCode1" ); } catch(ex) { };

	try { document.all('AppGrpNo').value = parent.VD.gVSwitch.getVar('GrpNo'); } catch(ex) { };
	try { document.all('Password').value = parent.VD.gVSwitch.getVar('Password'); } catch(ex) { };
	try { document.all('AppGrpName').value = parent.VD.gVSwitch.getVar('GrpName'); } catch(ex) { };
	try { document.all('AppGrpAddressCode').value = parent.VD.gVSwitch.getVar('GrpAddressCode'); } catch(ex) { };
	try { document.all('AppGrpAddress').value = parent.VD.gVSwitch.getVar('GrpAddress'); } catch(ex) { };
	try { document.all('AppGrpZipCode').value = parent.VD.gVSwitch.getVar('GrpZipCode'); } catch(ex) { };
	try { document.all('BusinessType').value = parent.VD.gVSwitch.getVar('BusinessType'); } catch(ex) { };
	try { document.all('GrpNature').value = parent.VD.gVSwitch.getVar('GrpNature'); } catch(ex) { };
	try { document.all('Peoples').value = parent.VD.gVSwitch.getVar('Peoples'); } catch(ex) { };
	try { document.all('RgtMoney').value = parent.VD.gVSwitch.getVar('RgtMoney'); } catch(ex) { };
	try { document.all('Asset').value = parent.VD.gVSwitch.getVar('Asset'); } catch(ex) { };
	try { document.all('NetProfitRate').value = parent.VD.gVSwitch.getVar('NetProfitRate'); } catch(ex) { };
	try { document.all('MainBussiness').value = parent.VD.gVSwitch.getVar('MainBussiness'); } catch(ex) { };
	try { document.all('Corporation').value = parent.VD.gVSwitch.getVar('Corporation'); } catch(ex) { };
	try { document.all('ComAera').value = parent.VD.gVSwitch.getVar('ComAera'); } catch(ex) { };
	try { document.all('LinkMan1').value = parent.VD.gVSwitch.getVar('LinkMan1'); } catch(ex) { };
	try { document.all('Department1').value = parent.VD.gVSwitch.getVar('Department1'); } catch(ex) { };
	try { document.all('HeadShip1').value = parent.VD.gVSwitch.getVar('HeadShip1'); } catch(ex) { };
	try { document.all('Phone1').value = parent.VD.gVSwitch.getVar('Phone1'); } catch(ex) { };
	try { document.all('E_Mail1').value = parent.VD.gVSwitch.getVar('E_Mail1'); } catch(ex) { };
	try { document.all('Fax1').value = parent.VD.gVSwitch.getVar('Fax1'); } catch(ex) { };
	try { document.all('LinkMan2').value = parent.VD.gVSwitch.getVar('LinkMan2'); } catch(ex) { };
	try { document.all('Department2').value = parent.VD.gVSwitch.getVar('Department2'); } catch(ex) { };
	try { document.all('HeadShip2').value = parent.VD.gVSwitch.getVar('HeadShip2'); } catch(ex) { };
	try { document.all('Phone2').value = parent.VD.gVSwitch.getVar('Phone2'); } catch(ex) { };
	try { document.all('E_Mail2').value = parent.VD.gVSwitch.getVar('E_Mail2'); } catch(ex) { };
	try { document.all('Fax2').value = parent.VD.gVSwitch.getVar('Fax2'); } catch(ex) { };
	try { document.all('Fax').value = parent.VD.gVSwitch.getVar('Fax'); } catch(ex) { };
	try { document.all('Phone').value = parent.VD.gVSwitch.getVar('Phone'); } catch(ex) { };
	try { document.all('GetFlag').value = parent.VD.gVSwitch.getVar('GetFlag'); } catch(ex) { };
	try { document.all('Satrap').value = parent.VD.gVSwitch.getVar('Satrap'); } catch(ex) { };
	try { document.all('EMail').value = parent.VD.gVSwitch.getVar('EMail'); } catch(ex) { };
	try { document.all('FoundDate').value = parent.VD.gVSwitch.getVar('FoundDate'); } catch(ex) { };
	try { document.all('BankAccNo').value = parent.VD.gVSwitch.getVar('BankAccNo'); } catch(ex) { };
	try { document.all('BankCode').value = parent.VD.gVSwitch.getVar('BankCode'); } catch(ex) { };
	try { document.all('GrpGroupNo').value = parent.VD.gVSwitch.getVar('GrpGroupNo'); } catch(ex) { };
	try { document.all('State').value = parent.VD.gVSwitch.getVar('State'); } catch(ex) { };
	try { document.all('Remark').value = parent.VD.gVSwitch.getVar('Remark'); } catch(ex) { };
	try { document.all('BlacklistFlag').value = parent.VD.gVSwitch.getVar('BlacklistFlag'); } catch(ex) { };
	
}

</script>
