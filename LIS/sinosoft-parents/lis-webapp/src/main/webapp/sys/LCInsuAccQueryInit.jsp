<%
//�������ƣ�
//�����ܣ�
//�������ڣ�
//������  ��ck
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();
    initPolGridClass();
    initPolGridTrace();
    //initLOArrearageGrid();
   // easyQueryClick();
  }
  catch(re)
  {
    alert("OmniAccQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
  try
  {
    // ������ѯ����
    document.all('ContNo').value = tContNo;
    document.all('PolNo').value = tPolNo;
    document.all('RiskCode').value = tRiskCode;
//    var strsql = "select appntname,insuredname from lccont where contno = '"+tContNo+"'";
    
        var sqlid1="LCInsuAccQueryInitSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.LCInsuAccQueryInitSql");
	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	 	mySql1.addSubPara(tContNo);//ָ���������
	 	var strsql = mySql1.getString();
    
    var brr = easyExecSql(strsql);
    if(brr)
    {
       document.all('AppntName').value = brr[0][0];
       document.all('InsuredName').value = brr[0][1];
    }
  }
  catch(ex)
  {
    alert("��OmniQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б��ĳ�ʼ��
function initPolGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="Ͷ���ʻ�����";                //����
      iArray[1][1]="80px";                 //�п�
      iArray[1][2]=100;                     //�����ֵ
      iArray[1][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������
			//iArray[1][4]="InsuAcc";
			iArray[1][4]="findinsuacc";
			iArray[1][15]="riskcode";
     iArray[1][17]="6";

			
      iArray[2]=new Array();
      iArray[2][0]="Ԥ���ֶ�";                //����
      iArray[2][1]="100px";                 //�п�
      iArray[2][2]=200;                     //�����ֵ
      iArray[2][3]=3;                       //�Ƿ���������,1��ʾ������0��ʾ������
			
      iArray[3]=new Array();
      iArray[3][0]="�����ͬ��";                //����
      iArray[3][1]="100px";                 //�п�
      iArray[3][2]=200;                     //�����ֵ
      iArray[3][3]=3;                       //�Ƿ���������,1��ʾ������0��ʾ������
              
      iArray[4]=new Array();
      iArray[4][0]="���屣�����ֺ�";                //����
      iArray[4][1]="100px";                 //�п�
      iArray[4][2]=200;                     //�����ֵ
      iArray[4][3]=3;                       //�Ƿ���������,1��ʾ������0��ʾ������
              
      iArray[5]=new Array();
      iArray[5][0]="ӡˢ��";                //����
      iArray[5][1]="100px";                 //�п�
      iArray[5][2]=100;                     //�����ֵ
      iArray[5][3]=3;                       //�Ƿ���������,1��ʾ������0��ʾ������
             
      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";              //����
      iArray[6][1]="80px";                  //�п�
      iArray[6][2]=100;                     //�����ֵ
      iArray[6][3]=3;
      iArray[6][4]="RiskCode";                          //�Ƿ����ô���:null||""Ϊ������
              
      iArray[7]=new Array();
      iArray[7][0]="�˻�����";              //����
      iArray[7][1]="80px";                  //�п�
      iArray[7][2]=100;                     //�����ֵ
      iArray[7][3]=3;                                   //�Ƿ���������,1��ʾ������0��ʾ������
              
      iArray[8]=new Array();
      iArray[8][0]="Ͷ������";              //����
      iArray[8][1]="80px";                  //�п�
      iArray[8][2]=100;                     //�����ֵ
      iArray[8][3]=3;                                   //�Ƿ���������,1��ʾ������0��ʾ������
              
      iArray[9]=new Array();
      iArray[9][0]="�����˿ͻ���";              //����
      iArray[9][1]="80px";                  //�п�
      iArray[9][2]=100;                     //�����ֵ
      iArray[9][3]=3;                                   //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="Ͷ���˿ͻ���";              //����
      iArray[10][1]="80px";                  //�п�
      iArray[10][2]=100;                     //�����ֵ
      iArray[10][3]=3;                                   //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="�˻�������";               //����
      iArray[11][1]="80px";                 //�п�
      iArray[11][2]=100;                        //�����ֵ
      iArray[11][3]=3;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[12]=new Array();
      iArray[12][0]="�ۼ���ȡ";                 //����
      iArray[12][1]="80px";                 //�п�
      iArray[12][2]=100;                        //�����ֵ
      iArray[12][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[12][23]="0";
               
      iArray[13]=new Array();
      iArray[13][0]="�ۼƽ���";                 //����
      iArray[13][1]="80px";                 //�п�
      iArray[13][2]=100;                        //�����ֵ
      iArray[13][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[13][23]="0";
               
      iArray[14]=new Array();
      iArray[14][0]="�˻���������";                 //����
      iArray[14][1]="80px";                 //�п�
      iArray[14][2]=100;                        //�����ֵ
      iArray[14][3]=8;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[15]=new Array();
      iArray[15][0]="�ڳ��ֽ���";                 //����
      iArray[15][1]="80px";                    //�п�
      iArray[15][2]=100;                        //�����ֵ
      iArray[15][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[15][23]="0"; 
               
      iArray[16]=new Array();
      iArray[16][0]="�ڳ���λ��";                 //����
      iArray[16][1]="0px";                    //�п�
      iArray[16][2]=100;                        //�����ֵ
      iArray[16][3]=0;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[17]=new Array();
      iArray[17][0]="�ڳ���λ�۸�";                 //����
      iArray[17][1]="0px";                    //�п�
      iArray[17][2]=100;                        //�����ֵ
      iArray[17][3]=0;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[18]=new Array();
      iArray[18][0]="�˻���������";                 //����
      iArray[18][1]="80px";                 //�п�
      iArray[18][2]=100;                        //�����ֵ
      iArray[18][3]=8;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[19]=new Array();
      iArray[19][0]="�˻���ֵ";                 //����
      iArray[19][1]="80px";                 //�п�
      iArray[19][2]=100;                        //�����ֵ
      iArray[19][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[19][23]="0";
               
      iArray[20]=new Array();
      iArray[20][0]="�˻���λ��";                 //����
      iArray[20][1]="80px";                 //�п�
      iArray[20][2]=100;                        //�����ֵ
      iArray[20][3]=0;                                  //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[21]=new Array();
      iArray[21][0]="�˻���λ�����۸�";                 //����
      iArray[21][1]="100px";                 //�п�
      iArray[21][2]=100;                        //�����ֵ
      iArray[21][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[21][23]="0";
               
      iArray[22]=new Array();
      iArray[22][0]="�˻�������";                 //����
      iArray[22][1]="80px";                 //�п�
      iArray[22][2]=100;                        //�����ֵ
      iArray[22][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[22][23]="0";
               
      iArray[23]=new Array();
      iArray[23][0]="������";                 //����
      iArray[23][1]="80px";                 //�п�
      iArray[23][2]=100;                        //�����ֵ
      iArray[23][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[23][23]="0";
               
      iArray[24]=new Array();
      iArray[24][0]="�˻�״̬";                 //����
      iArray[24][1]="80px";                 //�п�
      iArray[24][2]=100;                        //�����ֵ
      iArray[24][3]=3;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[25]=new Array();
      iArray[25][0]="��������";                 //����
      iArray[25][1]="80px";                 //�п�
      iArray[25][2]=100;                        //�����ֵ
      iArray[25][3]=3;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[26]=new Array();
      iArray[26][0]="����Ա";               //����
      iArray[26][1]="80px";                 //�п�
      iArray[26][2]=100;                        //�����ֵ
      iArray[26][3]=3;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[27]=new Array();
			iArray[27][0]="����";
			iArray[27][1]="60px";
			iArray[27][2]=100;
			iArray[27][3]=2;
			iArray[27][4]="currency";


      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
    //  PolGrid.canSel = 1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.hiddenPlus = 1;
    //  PolGrid.selBoxEventFuncName = "showAccDetail";
      PolGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б��ĳ�ʼ��
function initPolGridClass()
  {
  	
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="Ͷ���ʻ�����";                //����
      iArray[1][1]="80px";                 //�п�
      iArray[1][2]=100;                     //�����ֵ
      iArray[1][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][4]="InsuAcc";
			
      iArray[2]=new Array();
      iArray[2][0]="�����˻�����";                //����
      iArray[2][1]="80px";                 //�п�
      iArray[2][2]=100;                     //�����ֵ
      iArray[2][3]=2;
      iArray[2][4]="payplancode";
      iArray[2][17]="2";
      iArray[2][15]="payplancode";

      iArray[3]=new Array();
      iArray[3][0]="���屣�����ֺ�";                //����
      iArray[3][1]="100px";                 //�п�
      iArray[3][2]=200;                     //�����ֵ
      iArray[3][3]=3;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="ӡˢ��";                //����
      iArray[4][1]="100px";                 //�п�
      iArray[4][2]=100;                     //�����ֵ
      iArray[4][3]=3;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ֱ���";              //����
      iArray[5][1]="80px";                  //�п�
      iArray[5][2]=100;                     //�����ֵ
      iArray[5][3]=3;
      iArray[5][4]="RiskCode";                          //�Ƿ����ô���:null||""Ϊ������

      iArray[6]=new Array();
      iArray[6][0]="�˻�����";              //����
      iArray[6][1]="80px";                  //�п�
      iArray[6][2]=100;                     //�����ֵ
      iArray[6][3]=3;                                   //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="Ͷ������";              //����
      iArray[7][1]="80px";                  //�п�
      iArray[7][2]=100;                     //�����ֵ
      iArray[7][3]=3;                                   //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�����˿ͻ���";              //����
      iArray[8][1]="80px";                  //�п�
      iArray[8][2]=100;                     //�����ֵ
      iArray[8][3]=3;                                   //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="Ͷ���˿ͻ���";              //����
      iArray[9][1]="80px";                  //�п�
      iArray[9][2]=100;                     //�����ֵ
      iArray[9][3]=3;                                   //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�˻�������";               //����
      iArray[10][1]="80px";                 //�п�
      iArray[10][2]=100;                        //�����ֵ
      iArray[10][3]=3;                                  //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="�ۼƽ���";                 //����
      iArray[11][1]="80px";                 //�п�
      iArray[11][2]=100;                        //�����ֵ
      iArray[11][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[11][23]="0";

      iArray[12]=new Array();
      iArray[12][0]="�ۼ���ȡ";                 //����
      iArray[12][1]="80px";                 //�п�
      iArray[12][2]=100;                        //�����ֵ
      iArray[12][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[12][23]="0";

      iArray[13]=new Array();
      iArray[13][0]="�˻���������";                 //����
      iArray[13][1]="80px";                 //�п�
      iArray[13][2]=100;                        //�����ֵ
      iArray[13][3]=8;                                  //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[14]=new Array();
      iArray[14][0]="�ڳ��˻��ֽ���";                 //����
      iArray[14][1]="100px";                    //�п�
      iArray[14][2]=100;                        //�����ֵ
      iArray[14][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[14][23]="0";

      iArray[15]=new Array();
      iArray[15][0]="�ڳ��˻���λ��";                 //����
      iArray[15][1]="0px";                    //�п�
      iArray[15][2]=100;                        //�����ֵ
      iArray[15][3]=0;                                  //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[16]=new Array();
      iArray[16][0]="�ڳ��˻���λ�۸�";                 //����
      iArray[16][1]="0px";                    //�п�
      iArray[16][2]=100;                        //�����ֵ
      iArray[16][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[16][23]="0";
               
      iArray[17]=new Array();
      iArray[17][0]="�˻���������";                 //����
      iArray[17][1]="80px";                 //�п�
      iArray[17][2]=100;                        //�����ֵ
      iArray[17][3]=8;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[18]=new Array();
      iArray[18][0]="�˻���ֵ";                 //����
      iArray[18][1]="80px";                 //�п�
      iArray[18][2]=100;                        //�����ֵ
      iArray[18][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[18][23]="0";
               
      iArray[19]=new Array();
      iArray[19][0]="�˻���λ��";                 //����
      iArray[19][1]="80px";                 //�п�
      iArray[19][2]=100;                        //�����ֵ
      iArray[19][3]=0;                                  //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[20]=new Array();
      iArray[20][0]="�˻���λ�����۸�";                 //����
      iArray[20][1]="100px";                 //�п�
      iArray[20][2]=100;                        //�����ֵ
      iArray[20][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[20][23]="0";
               
      iArray[21]=new Array();
      iArray[21][0]="�˻�������";                 //����
      iArray[21][1]="80px";                 //�п�
      iArray[21][2]=100;                        //�����ֵ
      iArray[21][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[21][23]="0";
               
      iArray[22]=new Array();
      iArray[22][0]="������";                 //����
      iArray[22][1]="80px";                 //�п�
      iArray[22][2]=100;                        //�����ֵ
      iArray[22][3]=7;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[22][23]="0";
               
      iArray[23]=new Array();
      iArray[23][0]="�˻�״̬";                 //����
      iArray[23][1]="80px";                 //�п�
      iArray[23][2]=100;                        //�����ֵ
      iArray[23][3]=3;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[24]=new Array();
      iArray[24][0]="��������";                 //����
      iArray[24][1]="80px";                 //�п�
      iArray[24][2]=100;                        //�����ֵ
      iArray[24][3]=3;                                  //�Ƿ���������,1��ʾ������0��ʾ������
               
      iArray[25]=new Array();
      iArray[25][0]="����Ա";               //����
      iArray[25][1]="80px";                 //�п�
      iArray[25][2]=100;                        //�����ֵ
      iArray[25][3]=3;                                  //�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[26]=new Array();
			iArray[26][0]="����";
			iArray[26][1]="60px";
			iArray[26][2]=100;
			iArray[26][3]=2;
			iArray[26][4]="currency";


      PolGridClass = new MulLineEnter("fm", "PolGridClass");
      //��Щ���Ա�����loadMulLineǰ
      PolGridClass.mulLineCount = 0;
      PolGridClass.displayTitle = 1;
     PolGridClass.locked = 1;
     PolGridClass.canSel = 0;
      PolGridClass.hiddenSubtraction = 1;
     PolGridClass.hiddenPlus = 1;
    //  PolGrid2.selBoxEventFuncName = "";
      PolGridClass.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
          alert(ex);
      }
}

// ������Ϣ�б��ĳ�ʼ��
function initPolGridTrace()
  {
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������


      iArray[1]=new Array();
      iArray[1][0]="�����ͬ����";
      iArray[1][1]="130px";
      iArray[1][2]=100;
      iArray[1][3]=3;

      iArray[2]=new Array();
      iArray[2][0]="���屣�����ֺ���";
      iArray[2][1]="130px";
      iArray[2][2]=100;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="��ͬ����";
      iArray[3][1]="130px";
      iArray[3][2]=100;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="�������ֺ���";
      iArray[4][1]="0px";
      iArray[4][2]=0;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="��ˮ��";
      iArray[5][1]="120px";
      iArray[5][2]=200;
      iArray[5][3]=3;

      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";
      iArray[6][1]="80px";
      iArray[6][2]=80;
      iArray[6][3]=2;
      iArray[6][4]="riskind";
      iArray[6][15]="risktype3";   
      iArray[6][16]="#3#";
      
      iArray[7]=new Array();
      iArray[7][0]="Ͷ���˻�����";
      iArray[7][1]="80px";
      iArray[7][2]=80;
      iArray[7][3]=2;
      iArray[7][4]="findinsuacc";
      iArray[7][15]="riskcode";
      iArray[7][17]="6";

      iArray[8]=new Array();
      iArray[8][0]="�����˻�����";
      iArray[8][1]="80px";
      iArray[8][2]=80;
      iArray[8][3]=2;
      iArray[8][4]="payplancode";
      iArray[8][17]="8";
      iArray[8][15]="payplancode";

      iArray[9]=new Array();
      iArray[9][0]="��Ӧ��������";
      iArray[9][1]="120px";
      iArray[9][2]=100;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="��Ӧ������������";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=0;

      iArray[11]=new Array();
      iArray[11][0]="�˻���������";
      iArray[11][1]="0px";
      iArray[11][2]=100;
      iArray[11][3]=0;

      iArray[12]=new Array();
      iArray[12][0]="��������";
      iArray[12][1]="80px";
      iArray[12][2]=100;
      iArray[12][3]=0;

      iArray[13]=new Array();
      iArray[13][0]="����������";
      iArray[13][1]="0px";
      iArray[13][2]=100;
      iArray[13][3]=2;
      iArray[13][4]="feecode";

      iArray[14]=new Array();
      iArray[14][0]="���";
      iArray[14][1]="60px";
      iArray[14][2]=100;
      iArray[14][3]=7;
      iArray[14][23]="0";
               
      iArray[15]=new Array();
      iArray[15][0]="��λ������";
      iArray[15][1]="70px";
      iArray[15][2]=100;
      iArray[15][3]=0;
               
      iArray[16]=new Array();
      iArray[16][0]="�Ƽ�״̬";
      iArray[16][1]="80px";
      iArray[16][2]=100;
      iArray[16][3]=0;
      
      iArray[17]=new Array();
      iArray[17][0]="��Ч����";
      iArray[17][1]="80px";
      iArray[17][2]=100;
      iArray[17][3]=8;

      iArray[18]=new Array();
      iArray[18][0]="Ӧ�üƼ�����";
      iArray[18][1]="80px";
      iArray[18][2]=100;
      iArray[18][3]=8;
               
      iArray[19]=new Array();
      iArray[19][0]="ʵ�ʴ�������";
      iArray[19][1]="80px";
      iArray[19][2]=100;
      iArray[19][3]=8;

      iArray[20]=new Array();
      iArray[20][0]="������";
      iArray[20][1]="60px";
      iArray[20][2]=100;
      iArray[20][3]=7;
      iArray[20][23]="0";
               
      iArray[21]=new Array();
      iArray[21][0]="�����";
      iArray[21][1]="60px";
      iArray[21][2]=100;
      iArray[21][3]=7;
      iArray[21][23]="0";
               
      iArray[22]=new Array();
      iArray[22][0]="������������";
      iArray[22][1]="0px";
      iArray[22][2]=0;
      iArray[22][3]=3;
                  
      iArray[23]=new Array();
      iArray[23][0]="��������";
      iArray[23][1]="0px";
      iArray[23][2]=0;
      iArray[23][3]=0;
               
      iArray[24]=new Array();
      iArray[24][0]="��������";
      iArray[24][1]="80px";
      iArray[24][2]=100;
      iArray[24][3]=3;
               
      iArray[25]=new Array();
      iArray[25][0]="����Ա";
      iArray[25][1]="80px";
      iArray[25][2]=100;
      iArray[25][3]=3;
      
      iArray[26]=new Array();
			iArray[26][0]="����";
			iArray[26][1]="60px";
			iArray[26][2]=100;
			iArray[26][3]=2;
			iArray[26][4]="currency";

      PolGridTrace = new MulLineEnter("fm", "PolGridTrace");
      //��Щ���Ա�����loadMulLineǰ
     
     PolGridTrace.mulLineCount = 0;
      PolGridTrace.displayTitle = 1;
      PolGridTrace.hiddenPlus = 1;
   
      PolGridTrace.hiddenSubtraction = 1;
     // PolGrid3.canSel=1;
     PolGridTrace.selBoxEventFuncName = "ShowPlan";
      PolGridTrace.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      }
      catch (ex)
      {
        alert(ex);
      }
}


//Ƿ����Ϣ
function initLOArrearageGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="��������";    				//����1
    iArray[1][1]="30px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="Ƿ��ҵ������";    				//����1
    iArray[2][1]="20px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=8;              			//�Ƿ���������,1��ʾ������0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="��������";         			//����2
    iArray[3][1]="20px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=8;              			//�Ƿ���������,1��ʾ������0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="Ƿ����";         			//����8
    iArray[4][1]="20px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=7;              			//�Ƿ���������,1��ʾ������0��ʾ������
    iArray[4][23]="0";
    
    iArray[5]=new Array();
    iArray[5][0]="�����־";    				//����1
    iArray[5][1]="30px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
    
    
    LOArrearageGrid = new MulLineEnter( "fm" , "LOArrearageGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LOArrearageGrid.mulLineCount = 0;   
    LOArrearageGrid.displayTitle = 1;
    LOArrearageGrid.canSel = 0;
    LOArrearageGrid.hiddenPlus = 1; 
    LOArrearageGrid.HiddenSubtraction = 1;
    //LPInsuAccGrid.selBoxEventFuncName = "reportDetail2Click";
    LOArrearageGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    LOArrearageGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

</script>