<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpFeeInit.jsp
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

function initInpBox()
{ 
  try
  {                                   
    document.all('ProposalGrpContNo').value = ProposalGrpContNo;
    document.all('GrpContNo').value = ProposalGrpContNo;
    try { document.all('AppntNo').value = mSwitch.getVar( "GrpNo" ); if(document.all('AppntNo').value=="false"){document.all('AppntNo').value="";} } catch(ex) { };
   // alert(document.all('GrpContNo').value);
    
  }
  catch(ex)
  {
    alert("��GroupPolInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm(){
	try{
		//alert(scantype);
    if(scantype=="scan"){

	  	document.all('RiskCode').focus();

      setFocus();
		  
		}		
		
		initInpBox();
		//initGrpFeeGrid();
		initContPlanGrid();
		//easyQueryClick();
        if(this.LoadFlag=="4"||this.LoadFlag=="16")
        {
            document.getElementById("divRiskSave").style.display="none";
            document.getElementById("divRiskAdd").style.display="none";
            document.all("btnDefPubAcc").style.display="none";
            document.all("btnDefPubAmnt").style.display="none";
        }
		initContPlanDutyGrid();
		initPubAmntGrid();
		initPubAccGrid();
	}
	catch(re){
		alert("GrpFeeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

var ContPlanGrid; 

// ҪԼ��Ϣ�б�ĳ�ʼ��
function initContPlanGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="��������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=150;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      
      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";    	        //����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
     	
      
      iArray[3]=new Array();
      iArray[3][0]="���α���";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]= 60;            			//�����ֵ
      iArray[3][3]= 3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][10]="DutyCode";   
      
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[5]=new Array();
      iArray[5][0]="����Ҫ��";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;  
      iArray[5][10]="FactorCode";            			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="Ҫ������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=150;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="Ҫ��˵��";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=150;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="Ҫ��ֵ";         		//����
      iArray[8][1]="50px";            		//�п�
      iArray[8][2]=150;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�ر�˵��";         		//����
      iArray[9][1]="200px";            		//�п�
      iArray[9][2]=150;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="���ְ汾";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=10;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="���屣�����ֺ���";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=10;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="���ձ���";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=10;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="����";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=10;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ContPlanGrid = new MulLineEnter( "fm" , "ContPlanGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ContPlanGrid = new MulLineEnter( "fm" , "ContPlanGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ContPlanGrid.mulLineCount = 0;   
      ContPlanGrid.displayTitle = 1;
      ContPlanGrid.hiddenPlus = 1;
      //ContPlanGrid.hiddenSubtraction = 1;
      ContPlanGrid.hiddenSubtraction = 1;
      ContPlanGrid.canChk=0; 
      ContPlanGrid.loadMulLine(iArray); 
      
      
      //��Щ����������loadMulLine����
      //ContPlanGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

// ҪԼ��Ϣ�б�ĳ�ʼ��
function initContPlanDutyGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���α���";    	        //����
      iArray[1][1]="155px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="155px";            		//�п�
      iArray[2][2]=150;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][10]="RiskCode";

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="155px";            		//�п�
      iArray[3][2]= 60;            			//�����ֵ
      iArray[3][3]= 0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="155px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ContPlanDutyGrid = new MulLineEnter( "fm" , "ContPlanDutyGrid" );
      //��Щ���Ա�����loadMulLineǰ
      ContPlanDutyGrid.mulLineCount = 0;
      ContPlanDutyGrid.displayTitle = 1;
      ContPlanDutyGrid.hiddenPlus = 1;
      ContPlanDutyGrid.hiddenSubtraction = 1;
      ContPlanDutyGrid.canChk=1;
      ContPlanDutyGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      //ContPlanGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

// �����ʻ��б�ĳ�ʼ��
function initPubAccGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";    	        //����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      
      iArray[2]=new Array();
      iArray[2][0]="��������";    	        //����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[3]=new Array();
      iArray[3][0]="�����ʻ�����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=150;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ɷѽ��";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]= 60;            			//�����ֵ
      iArray[4][3]= 1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���ֱ���";    	        //����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      
      iArray[6]=new Array();
      iArray[6][0]="���α���";    	        //����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[7]=new Array();
      iArray[7][0]="�Զ�Ӧ�ù����ʻ�";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��
      iArray[7][4]="yesno"; 
      
      iArray[8]=new Array();
      iArray[8][0]="�ͻ�����";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��
      
      iArray[9]=new Array();
      iArray[9][0]="��ͬ��";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��

      iArray[10]=new Array();
      iArray[10][0]="�����ͬ��";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��
      
      iArray[11]=new Array();
      iArray[11][0]="�ʻ����";         		//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��

      iArray[12]=new Array();
      iArray[12][0]="���˱�����";         		//����
      iArray[12][1]="100px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��




      PubAccGrid = new MulLineEnter( "fm" , "PubAccGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PubAccGrid.canSel=1;
      PubAccGrid.mulLineCount = 1;
      PubAccGrid.displayTitle = 1;
      PubAccGrid.addEventFuncName="addNewLine";
      //PubAccGrid.tableWidth   ="500px";
      PubAccGrid.hiddenPlus = 1;
      PubAccGrid.hiddenSubtraction = 1;
      PubAccGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      //PubAccGrid.setRowColData(1,1,"asdf");

    }
    catch(ex) {
      alert(ex);
    }
}

// ���������б�ĳ�ʼ��
function initPubAmntGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";    	        //����
      iArray[1][1]="155px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      
      iArray[2]=new Array();
      iArray[2][0]="��������";    	        //����
      iArray[2][1]="140px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[3]=new Array();
      iArray[3][0]="������������";         		//����
      iArray[3][1]="130px";            		//�п�
      iArray[3][2]=150;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="130px";            		//�п�
      iArray[4][2]= 60;            			//�����ֵ
      iArray[4][3]= 1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="130px";            		//�п�
      iArray[5][2]= 60;            			//�����ֵ
      iArray[5][3]= 1;              			//�Ƿ���������,1��ʾ����0��ʾ������
         
      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";    	        //����
      iArray[6][1]="140px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[7]=new Array();
      iArray[7][0]="���α���";         		//����
      iArray[7][1]="120px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ�� 
      
      iArray[8]=new Array();
      iArray[8][0]="�ͻ�����";         		//����
      iArray[8][1]="120px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��
      
      iArray[9]=new Array();
      iArray[9][0]="��ͬ��";         		//����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��

      iArray[10]=new Array();
      iArray[10][0]="�����ͬ��";         		//����
      iArray[10][1]="120px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��
      

      PubAmntGrid = new MulLineEnter( "fm" , "PubAmntGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PubAmntGrid.canSel=1;
      PubAmntGrid.mulLineCount = 1;
      PubAmntGrid.displayTitle = 1;
      PubAmntGrid.addEventFuncName="addNewLine";
      //PubAmntGrid.tableWidth   ="500px";
      PubAmntGrid.hiddenPlus = 1;
      PubAmntGrid.hiddenSubtraction = 1;
      PubAmntGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      //PubAmntGrid.setRowColData(1,1,"asdf");

    }
    catch(ex) {
      alert(ex);
    }
}

// ҪԼ��Ϣ�б�ĳ�ʼ��
//function initImpartGrid(tImpContPlanCode) {
function initImpartGrid() {
    var tImpContPlanCode="";
    var iArray = new Array();
    //alert("impartgrid");

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[1]=new Array();
      iArray[1][0]="��������";    	        //����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
            
      iArray[2]=new Array();
      iArray[2][0]="�ƻ�����";    	        //����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
            
      iArray[3]=new Array();
      iArray[3][0]="�ƻ�����";    	        //����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
            
      iArray[4]=new Array();
      iArray[4][0]="Ҫ�����";    	        //����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
            
      iArray[5]=new Array();
      iArray[5][0]="Ҫ��Ŀ�����";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
      iArray[6]=new Array();
      iArray[6][0]="��������";    	        //����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
            
      iArray[7]=new Array();
      iArray[7][0]="Ҫ�ؼ������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
      iArray[8]=new Array();
      iArray[8][0]="Ҫ������";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
      iArray[9]=new Array();
      iArray[9][0]="Ҫ������";    	        //����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      
      iArray[10]=new Array();
      iArray[10][0]="Ҫ��˵��";    	        //����
      iArray[10][1]="120px";            		//�п�
      iArray[10][2]=80;            			//�����ֵ
      iArray[10][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
            
      iArray[11]=new Array();
      iArray[11][0]="Ҫ��ֵ";         		//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=150;            			//�����ֵ
      iArray[11][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
      
      iArray[12]=new Array();
      iArray[12][0]="�ƻ�����";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=150;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
      iArray[13]=new Array();
      iArray[13][0]="���ְ汾";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=150;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
      iArray[14]=new Array();
      iArray[14][0]="�����������";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=150;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
      iArray[15]=new Array();
      iArray[15][0]="���ձ���";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=150;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
      iArray[16]=new Array();
      iArray[16][0]="���հ汾";         		//����
      iArray[16][1]="0px";            		//�п�
      iArray[16][2]=150;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 1;
      ImpartGrid.displayTitle = 1;
      ImpartGrid.addEventFuncName="addNewLine";
      //ImpartGrid.tableWidth   ="500px";

      ImpartGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
      }
    catch(ex) {
      alert(ex);
    }
 }
</script>
