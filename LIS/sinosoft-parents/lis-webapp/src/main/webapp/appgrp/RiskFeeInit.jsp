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
function initForm(){
	try{
	        if(this.LoadFlag=="4"||this.LoadFlag=="16")
                {
                     divRiskFeeSave.style.display="none";
                }
                if(this.LoadFlag=="11")
                {
                  divReturnBack.style.display="none";
                }
		initRiskFeeGrid();
		divRiskFeeParam.style.display='none';
	}
	catch(re){
		alert("GrpFeeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

//���ֹ������ϸ
function initRiskFeeGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=10;            			//�����ֵ
		iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[1]=new Array();
		iArray[1][0]="�ʻ�����";
		iArray[1][1]="60px";
		iArray[1][2]=60;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�ʻ�˵��";
		iArray[2][1]="150px";
		iArray[2][2]=150;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ɷѴ���";
		iArray[3][1]="60px";
		iArray[3][2]=60;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�ɷ�˵��";
		iArray[4][1]="150px";
		iArray[4][2]=150;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="����Ѵ���";
		iArray[5][1]="60px";
		iArray[5][2]=60;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="���������";
		iArray[6][1]="150px";
		iArray[6][2]=150;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="����˵��";
		iArray[7][1]="0px";
		iArray[7][2]=200;
		iArray[7][3]=3;

		iArray[8]=new Array();
		iArray[8][0]="���㷽ʽ";
		iArray[8][1]="60px";
		iArray[8][2]=60;
		iArray[8][3]=2;
		iArray[8][4]="FeeCalMode";

		iArray[9]=new Array();
		iArray[9][0]="�������";
		iArray[9][1]="0px";
		iArray[9][2]=200;
		iArray[9][3]=3;

		iArray[10]=new Array();
		iArray[10][0]="�̶�ֵ/�̶�����";
		iArray[10][1]="60px";
		iArray[10][2]=60;
		iArray[10][3]=1;
		iArray[10][9]= "�̶�ֵ|num"; 

		iArray[11]=new Array();
		iArray[11][0]="�Ƚ�ֵ";
		iArray[11][1]="0px";
		iArray[11][2]=200;
		iArray[11][3]=3;

		iArray[12]=new Array();
		iArray[12][0]="��������";
		iArray[12][1]="0px";
		iArray[12][2]=200;
		iArray[12][3]=3;

		iArray[13]=new Array();
		iArray[13][0]="�۳����������";
		iArray[13][1]="0px";
		iArray[13][2]=200;
		iArray[13][3]=3;

		iArray[14]=new Array();
		iArray[14][0]="�۳������������";
		iArray[14][1]="0px";
		iArray[14][2]=200;
		iArray[14][3]=3;

		iArray[15]=new Array();
		iArray[15][0]="ȱʡ���";
		iArray[15][1]="0px";
		iArray[15][2]=200;
		iArray[15][3]=3;

		iArray[16]=new Array();
		iArray[16][0]="��¼״̬";
		iArray[16][1]="50px";
		iArray[16][2]=200;
		iArray[16][3]=0;

		RiskFeeGrid = new MulLineEnter( "fm" , "RiskFeeGrid" );
		//��Щ���Ա�����loadMulLineǰ
		//RiskFeeGrid.mulLineCount = 10;
		RiskFeeGrid.displayTitle = 5;
		RiskFeeGrid.locked = 1;
		RiskFeeGrid.canSel = 0;
		RiskFeeGrid.hiddenPlus = 1;
		RiskFeeGrid.hiddenSubtraction = 1;
		//RiskFeeGrid.selBoxEventFuncName = "QueryRiskFeeParam"; 
		RiskFeeGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}

//���ֹ������ϸ����
function initRiskFeeParamGrid() {                     
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
      iArray[2][0]="��������";    	        //����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[3]=new Array();
      iArray[3][0]="����ѱ���";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]= 60;            			//�����ֵ
      iArray[3][3]= 1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����ѱ���";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]= 60;            			//�����ֵ
      iArray[4][3]= 3;              			//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[5]=new Array();
		iArray[5][0]="��¼״̬";
		iArray[5][1]="0px";
		iArray[5][2]=200;
		iArray[5][3]=0;
		
      RiskFeeParamGrid = new MulLineEnter( "fm" , "RiskFeeParamGrid" );
      //��Щ���Ա�����loadMulLineǰ
      RiskFeeParamGrid.mulLineCount = 5;
      RiskFeeParamGrid.displayTitle = 1;
      RiskFeeParamGrid.hiddenPlus = 1;
      RiskFeeParamGrid.hiddenSubtraction = 0;
      RiskFeeParamGrid.canChk=0;
      RiskFeeParamGrid.loadMulLine(iArray);

    }
    catch(ex) {
      alert(ex);
    }
}
</script>
