<%
//�������ƣ�GrpHealthFactoryQueryInit.jsp
//�����ܣ�
//�������ڣ�2005-04-10
//������  ��mqhu
//���¼�¼��  ������ ��������    ����ԭ��/����
%>
<!--�û�У����-->
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox(){
  try{
	// ������ѯ����
  }
  catch(ex)
  {
    alert("AscriptionRuleInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox(){
  try{
  }
  catch(ex)
  {
    alert("AscriptionRuleInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm(){
  try{
    // alert("here 0");
    initInpBox();
   // alert("here 1");
    initSelBox();
   //  alert(" here 2");
    if(this.LoadFlag=="4"||this.LoadFlag=="16"||this.LoadFlag=="14")
    {
       divRiskPlanSave.style.display="none";
    }
    //alert("here 3");
   GrpPerPolDefine();
   //alert("here 4");
    GrpPerPolDefineOld();
    initAscriptionRuleGrid();
  }
  catch(re)
  {
    alert("AscriptionRuleInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var ContPlanGrid;
function initAscriptionRuleOldGrid(){
	var iArray = new Array();
	try{
	iArray[0]=new Array();
	iArray[0][0]="���";
	iArray[0][1]="30px";
	iArray[0][2]=10;
	iArray[0][3]=0;
	//alert("not here");
        iArray[1]= new Array();
	iArray[1][0]="������������";
	iArray[1][1]="100px";
	iArray[1][2]=10;
	iArray[1][3]=0;
	
	iArray[2]=new Array();
	iArray[2][0]="���еĹ�������˵��";
	iArray[2][1]="300px";
	iArray[2][2]=10;
	iArray[2][3]=0;
	
	  AscriptionRuleOldGrid = new MulLineEnter( "fm" , "AscriptionRuleOldGrid" );
      //��Щ���Ա�����loadMulLineǰ
      AscriptionRuleOldGrid.mulLineCount = 0;
      AscriptionRuleOldGrid.displayTitle = 1;
      AscriptionRuleOldGrid.hiddenPlus = 1;
      AscriptionRuleOldGrid.hiddenSubtraction = 1;
      AscriptionRuleOldGrid.canSel=1;
      AscriptionRuleOldGrid.selBoxEventFuncName = "ShowAscriptionRule"; 
      AscriptionRuleOldGrid.loadMulLine(iArray);
	}
	    catch(ex) {
      alert(ex);
    }
    }


// ҪԼ��Ϣ�б�ĳ�ʼ��
function initAscriptionRuleNewGrid(tImpGrpContNo) {
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1] = "30px";            		//�п�
      iArray[0][2] = 10;            			//�����ֵ
      iArray[0][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[1] = new Array();
      iArray[1][0] = "���ֱ���";    	        //����
      iArray[1][1] = "60px";            		//�п�
      iArray[1][2] = 100;            			//�����ֵ
      iArray[1][3] = 2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[1][10] = "RiskRuleCode";
      iArray[1][11] = tImpGrpContNo;
      iArray[1][12] = "1|9";	//multine�ϵ���λ
      iArray[1][13] = "0|2";	//��ѯ�ֶε�λ��
      iArray[1][19] = 1;
    

      iArray[2] = new Array();
      iArray[2][0] = "Ҫ�����";    	        //����
      iArray[2][1] = "60px";            		//�п�
      iArray[2][2] = 100;            			//�����ֵ
      iArray[2][3] = 2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[2][4] = "RiskAscriptionRuleFactoryType";
      iArray[2][5] = "2|8";
      iArray[2][6] = "0|2";
      iArray[2][15] = "RiskCode";
      iArray[2][17] = "1";
      iArray[2][19] = 1;


      iArray[3] = new Array();
      iArray[3][0] = "Ҫ��Ŀ�����";         		//����
      iArray[3][1] = "80px";            		//�п�
      iArray[3][2] = 60;            			//�����ֵ
      iArray[3][3] = 2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4] = "RiskAscriptionRuleFactoryNo";
      iArray[3][9] = "Ҫ��Ŀ�����|len<=6";
      iArray[3][15] = "RiskCode";
      iArray[3][17] = "8";

      iArray[4] = new Array();
      iArray[4][0] = "Ҫ�ؼ������";         		//����
      iArray[4][1] = "80px";            		//�п�
      iArray[4][2] = 60;            			//�����ֵ
      iArray[4][3] = 2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][4] = "RiskAscriptionRuleFactory";
      iArray[4][5] = "4|5|6|7";
      iArray[4][6] = "0|1|2|3";
      iArray[4][9] = "Ҫ�ؼ������|len<=4";
      iArray[4][15] = "RiskCode";
      iArray[4][17] = "8";

      iArray[5] = new Array();
      iArray[5][0] = "Ҫ������";         		//����
      iArray[5][1] = "300px";            		//�п�
      iArray[5][2] = 200;            			//�����ֵ
      iArray[5][3] = 1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6] = new Array();
      iArray[6][0] = "Ҫ��ֵ";         		//����
      iArray[6][1] = "80px";            		//�п�
      iArray[6][2] = 150;            			//�����ֵ
      iArray[6][3] = 1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7] = new Array();
      iArray[7][0] = "Ҫ������";         		//����
      iArray[7][1] = "0px";            		//�п�
      iArray[7][2] = 150;            			//�����ֵ
      iArray[7][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8] = new Array();
      iArray[8][0] = "��ѯ����";         		//����
      iArray[8][1] = "0px";            		//�п�
      iArray[8][2] = 150;            			//�����ֵ
      iArray[8][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9] = new Array();
      iArray[9][0] = "���屣�����ֺ���";         		//����
      iArray[9][1] = "0px";            		//�п�
      iArray[9][2] = 150;            			//�����ֵ
      iArray[9][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      AscriptionRuleNewGrid = new MulLineEnter( "fm" , "AscriptionRuleNewGrid" );
      //��Щ���Ա�����loadMulLineǰ
      AscriptionRuleNewGrid.mulLineCount = 1;
      AscriptionRuleNewGrid.displayTitle = 1;
      AscriptionRuleNewGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}
function initAscriptionRuleGrid(tImpGrpContNo) {
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1] = "30px";            		//�п�
      iArray[0][2] = 10;            			//�����ֵ
      iArray[0][3] = 0;           			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1] = new Array();
      iArray[1][0] = "��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1] = "30px";            		//�п�
      iArray[1][2] = 10;            			//�����ֵ
      iArray[1][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������

       iArray[2] = new Array();
      iArray[2][0] = "����˵��";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[2][1] = "130px";            		//�п�
      iArray[2][2] = 10;            			//�����ֵ
      iArray[2][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3] = new Array();
      iArray[3][0] = "��������";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[3][1] = "250px";            		//�п�
      iArray[3][2] = 10;            			//�����ֵ
      iArray[3][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
         iArray[4] = new Array();
      iArray[4][0] = "����Ҫ�ص�ֵ";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[4][1] = "50px";            		//�п�
      iArray[4][2] = 10;            			//�����ֵ
      iArray[4][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      


      AscriptionRuleGrid = new MulLineEnter( "fm" , "AscriptionRuleGrid" );
      //��Щ���Ա�����loadMulLineǰ
      AscriptionRuleGrid.mulLineCount = 1;
      AscriptionRuleGrid.displayTitle = 1;
      AscriptionRuleGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert("Ascriptionrulegrid error");
    }
}
</script>
