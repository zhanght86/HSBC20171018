<%
//GEdorTypeMultiDetailInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>                       

<script language="JavaScript">  

function initInpBox() { 
  try {        
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('GrpContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  }
  catch(ex) {
    alert("��GEdorTypeMultiRiskInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}                                     

function initForm() {
  try {
    initInpBox();
    
    initRiskGrid();
    
    initRisk2Grid();
  }
  catch(re) {
    alert("GEdorTypeMultiRiskInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��Ϣ�б�ĳ�ʼ��
function initRiskGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���ֱ�����";    	//����1
    iArray[1][1]="150px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="���ֱ���";         			//����2
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="Ͷ��������";         			//����8
    iArray[3][1]="80px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����";         		//����5
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="����";         		//����6
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


    RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    RiskGrid.mulLineCount = 0;    
    RiskGrid.displayTitle = 1;
    RiskGrid.canChk = 1;
    RiskGrid.hiddenPlus = 1; 
    RiskGrid.hiddenSubtraction = 1;
    RiskGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

// ��Ϣ�б�ĳ�ʼ��
function initRisk2Grid() {
  var iArray = new Array();
      
   try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���ֱ�����";    	//����1
    iArray[1][1]="150px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="���ֱ���";         			//����2
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="Ͷ��������";         			//����8
    iArray[3][1]="80px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����";         		//����5
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="����";         		//����6
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


    Risk2Grid = new MulLineEnter( "fm" , "Risk2Grid" ); 
    //��Щ���Ա�����loadMulLineǰ
    Risk2Grid.mulLineCount = 0;    
    Risk2Grid.displayTitle = 1;
    Risk2Grid.canChk = 1;
    Risk2Grid.hiddenPlus = 1; 
    Risk2Grid.hiddenSubtraction = 1;
    Risk2Grid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

</script>
