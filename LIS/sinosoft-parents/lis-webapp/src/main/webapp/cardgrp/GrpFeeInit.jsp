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
    fm.all('ProposalGrpContNo').value = ProposalGrpContNo;
    fm.all('GrpContNo').value = ProposalGrpContNo;
   // alert(fm.all('GrpContNo').value);
    
  }
  catch(ex)
  {
    alert("��GroupPolInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm(){
	try{
		initInpBox();
		//initGrpFeeGrid();
		initContPlanGrid();
		//easyQueryClick();
	        if(this.LoadFlag=="4"||this.LoadFlag=="16")
                {
                    divRiskSave.style.display="none";
                }
		initContPlanDutyGrid();
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
      ContPlanGrid.mulLineCount = 0;   
      ContPlanGrid.displayTitle = 1;
      ContPlanGrid.hiddenPlus = 1;
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
</script>
