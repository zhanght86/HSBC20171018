<%
//GEdorTypeDetailInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>                       

<script language="JavaScript">  

function initInpBox() { 
  try {        
  document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
  document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;	
  document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
  document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  document.all('CustomerNo').value = "";	
  }
  catch(ex) {
    //alert("��GEdorTypeICInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}                                     

function initForm() {
  try {
    initInpBox();
    initLCGrpInsuAccGrid();
    initLPInsuAccGrid();
    initTempOutInsuAccGrid();
    QueryEdorInfo();
    queryClick();
    showInsuAccIn("");
  }
  catch(re) {
    //alert("GEdorTypeICInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��Ϣ�б�ĳ�ʼ��
function initLCGrpInsuAccGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ͻ�����";    				//����1
    iArray[1][1]="50px";            		//�п�
    iArray[1][2]=50;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�������ֺ���";         			//����2
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="Ͷ���˻�";         			//����8
    iArray[3][1]="50px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="Ͷ���˻�����";         			//����5
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

  //  iArray[5]=new Array();
  //  iArray[5][0]="�����˻�";         		//����6
  //  iArray[5][1]="0px";            		//�п�
  //  iArray[5][2]=100;            			//�����ֵ
  //  iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ����?
  //  
  //  iArray[6]=new Array();
  //  iArray[6][0]="�����˻�����";         		//����6
  //  iArray[6][1]="0px";            		//�п�
  //  iArray[6][2]=100;            			//�����ֵ
  //  iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������?
    
    iArray[5]=new Array();
    iArray[5][0]="Ͷ�ʵ�λ����";         		//����6
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][23]="0";
    
    iArray[6]=new Array();
    iArray[6][0]="��ת����λ";         		//����6
    iArray[6][1]="80px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="�Ѷ��ᵥλ��";         		//����6
    iArray[7][1]="70px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="��λ�۸�";         		//����6
    iArray[8][1]="60px";            		//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[8][23]="0";
    
    iArray[9]=new Array();
    iArray[9][0]="ת����λ";         		//����6
    iArray[9][1]="50px";            		//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="ת�����";         		//����6
    iArray[10][1]="0px";            		//�п�
    iArray[10][2]=100;            			//�����ֵ
    iArray[10][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[10][23]="0";
    
    iArray[11]=new Array();
		iArray[11][0]="����";
		iArray[11][1]="60px";
		iArray[11][2]=100;
		iArray[11][3]=0;
		//iArray[11][4]="currency";
    
    LCGrpInsuAccGrid = new MulLineEnter( "fm" , "LCGrpInsuAccGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LCGrpInsuAccGrid.mulLineCount = 0;    
    LCGrpInsuAccGrid.displayTitle = 1;
    LCGrpInsuAccGrid.canSel = 1;
    LCGrpInsuAccGrid.hiddenPlus = 1; 
    LCGrpInsuAccGrid.hiddenSubtraction = 1;
    //LCGrpInsuAccGrid.selBoxEventFuncName = "showInsuAccIn";
    LCGrpInsuAccGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    LCGrpInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}


// ��Ϣ�б�ĳ�ʼ��
function initLCInsuAccGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ͻ�����";    				//����1
    iArray[1][1]="50px";            		//�п�
    iArray[1][2]=50;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�������ֺ���";         			//����2
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="Ͷ���˻�";         			//����8
    iArray[3][1]="50px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�˻�����";         			//����5
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

  //  iArray[5]=new Array();
  //  iArray[5][0]="�����˻�";         		//����6
  //  iArray[5][1]="0px";            		//�п�
  //  iArray[5][2]=100;            			//�����ֵ
  //  iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
  //
  //  iArray[6]=new Array();
  //  iArray[6][0]="�����˻�����";         		//����6
  //  iArray[6][1]="0px";            		//�п�
  //  iArray[6][2]=100;            			//�����ֵ
  //  iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="�˻���λ";         		//����6
    iArray[5][1]="50px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="��λ�۸�";         		//����6
    iArray[6][1]="50px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[6][23]="0";
    
    iArray[7]=new Array();
    iArray[7][0]="ת�����";         		//����6
    iArray[7][1]="50px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="ת����";         		//����6
    iArray[8][1]="0px";            		//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[8][23]="0";
		
		iArray[9]=new Array();
		iArray[9][0]="����";
		iArray[9][1]="60px";
		iArray[9][2]=100;
		iArray[9][3]=0;
		//iArray[9][4]="currency";
		
    LCInsuAccGrid = new MulLineEnter( "fm" , "LCInsuAccGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LCInsuAccGrid.mulLineCount = 0;    
    LCInsuAccGrid.displayTitle = 1;
    LCInsuAccGrid.canChk = 1;
    LCInsuAccGrid.hiddenPlus = 1; 
    LCInsuAccGrid.hiddenSubtraction = 1;
    //LCInsuAccGrid.selBoxEventFuncName = "reportDetailClick";
    LCInsuAccGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    LCInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

// ��Ϣ�б�ĳ�ʼ��
function initLPInsuAccGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="ת��ͻ�����";    				//����1
    iArray[1][1]="50px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="ת�뱣�����ֺ���";    				//����1
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="ת���˻�";         			//����2
    iArray[3][1]="50px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="ת�뽻���˻�";         			//����8
    iArray[4][1]="0px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    
    iArray[5]=new Array();
    iArray[5][0]="ת�����";    				//����1
    iArray[5][1]="50px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    //iArray[6]=new Array();
    //iArray[6][0]="ת�����";    				//����1
    //iArray[6][1]="0px";            		//�п�
    //iArray[6][2]=100;            			//�����ֵ
    //iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    //
    //iArray[7]=new Array();
    //iArray[7][0]="ת��ͻ�����";    				//����1
    //iArray[7][1]="0px";            		//�п�
    //iArray[7][2]=100;            			//�����ֵ
    //iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    //
    //iArray[8]=new Array();
    //iArray[8][0]="ת�뱣�����ֺ���";         			//����5
    //iArray[8][1]="0px";            		//�п�
    //iArray[8][2]=100;            			//�����ֵ
    //iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
    //
    //iArray[9]=new Array();
    //iArray[9][0]="ת���˻�";         		//����6
    //iArray[9][1]="50px";            		//�п�
    //iArray[9][2]=100;            			//�����ֵ
    //iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    //
    //iArray[10]=new Array();
    //iArray[10][0]="ת�뽻���˻�";         		//����6
    //iArray[10][1]="50px";            		//�п�
    //iArray[10][2]=100;            			//�����ֵ
    //iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    //
    //iArray[11]=new Array();
    //iArray[11][0]="ת�����";         		//����6
    //iArray[11][1]="50px";            		//�п�
    //iArray[11][2]=100;            			//�����ֵ
    //iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    //
    //iArray[12]=new Array();
    //iArray[12][0]="ת����";         		//����6
    //iArray[12][1]="0px";            		//�п�
    //iArray[12][2]=100;            			//�����ֵ
    //iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    //    
    //iArray[13]=new Array();
    //iArray[13][0]="������ˮ��";         		//����6
    //iArray[13][1]="80px";            		//�п�
    //iArray[13][2]=100;            			//�����ֵ
    //iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    LPInsuAccGrid = new MulLineEnter( "fm" , "LPInsuAccGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LPInsuAccGrid.mulLineCount = 0;   
    LPInsuAccGrid.displayTitle = 1;
    LPInsuAccGrid.canSel = 0;
    LPInsuAccGrid.hiddenPlus = 1; 
    LPInsuAccGrid.hiddenSubtraction = 1;
    //LPInsuAccGrid.selBoxEventFuncName = "reportDetail2Click";
    LPInsuAccGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    LPInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}




//2007-09-07----³��
// ��Ϣ�б�ĳ�ʼ��
function initTempOutInsuAccGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="ת���ͻ�����";    				//����1
    iArray[1][1]="50px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="ת���������ֺ���";    				//����1
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="ת���˻�";         			//����2
    iArray[3][1]="100px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="ת�������˻�";         			//����8
    iArray[4][1]="0px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    
    iArray[5]=new Array();
    iArray[5][0]="ת����λ";    				//����1
    iArray[5][1]="50px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="��������";    				//����1
    iArray[6][1]="70px";            		//�п�
    iArray[6][2]=70;            			//�����ֵ
    iArray[6][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="����ʱ��";    				//����1
    iArray[7][1]="70px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="����";    				//����1
    iArray[8][1]="0px";            		//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//
//    iArray[8]=new Array();
//    iArray[8][0]="ת�뱣�����ֺ���";         			//����5
//    iArray[8][1]="0px";            		//�п�
//    iArray[8][2]=100;            			//�����ֵ
//    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
//
//    iArray[9]=new Array();
//    iArray[9][0]="ת���˻�";         		//����6
//    iArray[9][1]="50px";            		//�п�
//    iArray[9][2]=100;            			//�����ֵ
//    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//
//    iArray[10]=new Array();
//    iArray[10][0]="ת�뽻���˻�";         		//����6
//    iArray[10][1]="50px";            		//�п�
//    iArray[10][2]=100;            			//�����ֵ
//    iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//
//    iArray[11]=new Array();
//    iArray[11][0]="ת�����";         		//����6
//    iArray[11][1]="50px";            		//�п�
//    iArray[11][2]=100;            			//�����ֵ
//    iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//    
//    iArray[12]=new Array();
//    iArray[12][0]="ת����";         		//����6
//    iArray[12][1]="0px";            		//�п�
//    iArray[12][2]=100;            			//�����ֵ
//    iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//        
//    iArray[13]=new Array();
//    iArray[13][0]="������ˮ��";         		//����6
//    iArray[13][1]="80px";            		//�п�
//    iArray[13][2]=100;            			//�����ֵ
//    iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    TempOutInsuAccGrid = new MulLineEnter( "fm" , "TempOutInsuAccGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    TempOutInsuAccGrid.mulLineCount = 0;   
    TempOutInsuAccGrid.displayTitle = 1;
    TempOutInsuAccGrid.canSel = 1;
    TempOutInsuAccGrid.hiddenPlus = 1; 
    TempOutInsuAccGrid.hiddenSubtraction = 1;
    TempOutInsuAccGrid.selBoxEventFuncName = "reportDetail2Click";
    TempOutInsuAccGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    TempOutInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

</script>