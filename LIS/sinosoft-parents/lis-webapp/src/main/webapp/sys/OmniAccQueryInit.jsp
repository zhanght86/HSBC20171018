<%
//�������ƣ�
//�����ܣ�
//�������ڣ�
//������  ��Howie
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
    initPolGrid2();
    initPolGrid3();
    easyQueryClick();
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
 //   var strsql = "select appntname,insuredname from lccont where contno = '"+tContNo+"'";
    
    	var sqlid1="OmniAccQueryInitSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.OmniAccQueryInitSql");
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


var PolGrid;
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����ʻ���";                //����
      iArray[1][1]="78px";                 //�п�
      iArray[1][2]=100;                     //�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�����ͬ��";                //����
      iArray[2][1]="100px";                 //�п�
      iArray[2][2]=200;                     //�����ֵ
      iArray[2][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���屣�����ֺ�";                //����
      iArray[3][1]="100px";                 //�п�
      iArray[3][2]=200;                     //�����ֵ
      iArray[3][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="ӡˢ��";                //����
      iArray[4][1]="100px";                 //�п�
      iArray[4][2]=100;                     //�����ֵ
      iArray[4][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ֱ���";              //����
      iArray[5][1]="60px";                  //�п�
      iArray[5][2]=100;                     //�����ֵ
      iArray[5][3]=2;
      iArray[5][4]="RiskCode";                          //�Ƿ����ô���:null||""Ϊ������
      iArray[5][5]="5";                                 //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[5][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[5][18]=250;
      iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="�˻�����";              //����
      iArray[6][1]="80px";                  //�п�
      iArray[6][2]=100;                     //�����ֵ
      iArray[6][3]=3;                                   //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="Ͷ������";              //����
      iArray[7][1]="80px";                  //�п�
      iArray[7][2]=100;                     //�����ֵ
      iArray[7][3]=3;                                   //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�����˿ͻ���";              //����
      iArray[8][1]="80px";                  //�п�
      iArray[8][2]=100;                     //�����ֵ
      iArray[8][3]=3;                                   //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="Ͷ���˿ͻ���";              //����
      iArray[9][1]="80px";                  //�п�
      iArray[9][2]=100;                     //�����ֵ
      iArray[9][3]=0;                                   //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�˻�������";               //����
      iArray[10][1]="80px";                 //�п�
      iArray[10][2]=100;                        //�����ֵ
      iArray[10][3]=3;                                  //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="�ۼƽ���";                 //����
      iArray[11][1]="60px";                 //�п�
      iArray[11][2]=100;                        //�����ֵ
      iArray[11][3]=7;                                  //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[11][23]="0";

      iArray[12]=new Array();
      iArray[12][0]="�ۼ���ȡ";                 //����
      iArray[12][1]="60px";                 //�п�
      iArray[12][2]=100;                        //�����ֵ
      iArray[12][3]=7;                                  //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[12][23]="0"; 

      iArray[13]=new Array();
      iArray[13][0]="�����˻����";                 //����
      iArray[13][1]="80px";                    //�п�
      iArray[13][2]=100;                        //�����ֵ
      iArray[13][3]=7;                                  //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[13][23]="0";

      iArray[14]=new Array();
      iArray[14][0]="��ǰ�ʻ����";                 //����
      iArray[14][1]="80px";                 //�п�
      iArray[14][2]=100;                        //�����ֵ
      iArray[14][3]=7;                                  //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[14][23]="0";


      iArray[15]=new Array();
      iArray[15][0]="�˻�������";                 //����
      iArray[15][1]="80px";                 //�п�
      iArray[15][2]=100;                        //�����ֵ
      iArray[15][3]=7;                                  //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[15][23]="0";

      iArray[16]=new Array();
      iArray[16][0]="�������";                 //����
      iArray[16][1]="80px";                 //�п�
      iArray[16][2]=100;                        //�����ֵ
      iArray[16][3]=3;                                  //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[17]=new Array();
      iArray[17][0]="����Ա";               //����
      iArray[17][1]="80px";                 //�п�
      iArray[17][2]=100;                        //�����ֵ
      iArray[17][3]=3;                                  //�Ƿ���������,1��ʾ����0��ʾ������


      iArray[18]=new Array();
      iArray[18][0]="��ʼ����";               //����
      iArray[18][1]="80px";                 //�п�
      iArray[18][2]=100;                        //�����ֵ
      iArray[18][3]=7;                                  //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[18][23]="0";
      
      iArray[19]=new Array();
			iArray[19][0]="����";
			iArray[19][1]="60px";
			iArray[19][2]=100;
			iArray[19][3]=2;
			iArray[19][4]="currency";


      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.selBoxEventFuncName = "showAccDetail";
      PolGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}



var PolGrid2;
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid2()
  {
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����ͬ����";
      iArray[1][1]="0px";
      iArray[1][2]=100;
      iArray[1][3]=3;

      iArray[2]=new Array();
      iArray[2][0]="���屣������";
      iArray[2][1]="0px";
      iArray[2][2]=200;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="�������";
      iArray[3][1]="0px";
      iArray[3][2]=200;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="���Ѽƻ�����";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="��Ӧ��������";
      iArray[5][1]="80px";
      iArray[5][2]=100;
      iArray[5][3]=3;

      iArray[6]=new Array();
      iArray[6][0]="�˻���������";
      iArray[6][1]="80px";
      iArray[6][2]=100;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="�˻�����";
      iArray[7][1]="80px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="�˻����㷽ʽ";
      iArray[8][1]="100px";
      iArray[8][2]=100;
      iArray[8][3]=0;

      iArray[9]=new Array();
      iArray[9][0]="��������";
      iArray[9][1]="80px";
      iArray[9][2]=100;
      iArray[9][3]=8;

      iArray[10]=new Array();
      iArray[10][0]="����ʱ��";
      iArray[10][1]="80px";
      iArray[10][2]=100;
      iArray[10][3]=8;

      iArray[11]=new Array();
      iArray[11][0]="�ۼƽ���";
      iArray[11][1]="80px";
      iArray[11][2]=100;
      iArray[11][3]=7;
      iArray[11][23]="0";

      iArray[12]=new Array();
      iArray[12][0]="�ۼ���ȡ";
      iArray[12][1]="80px";
      iArray[12][2]=100;
      iArray[12][3]=7;
      iArray[12][23]="0";

      iArray[13]=new Array();
      iArray[13][0]="�ڳ��˻��ֽ����";
      iArray[13][1]="80px";
      iArray[13][2]=100;
      iArray[13][3]=7;
      iArray[13][23]="0";

      iArray[14]=new Array();
      iArray[14][0]="�ڳ��˻���λ��";
      iArray[14][1]="80px";
      iArray[14][2]=100;
      iArray[14][3]=0;

      iArray[15]=new Array();
      iArray[15][0]="�ڳ��˻���λ�۸�";
      iArray[15][1]="80px";
      iArray[15][2]=100;
      iArray[15][3]=7;
      iArray[15][23]="0";

      iArray[16]=new Array();
      iArray[16][0]="�����ʻ��ֽ����";
      iArray[16][1]="80px";
      iArray[16][2]=100;
      iArray[16][3]=7;
      iArray[16][23]="0";

      iArray[17]=new Array();
      iArray[17][0]="�����ʻ���λ��";
      iArray[17][1]="80px";
      iArray[17][2]=100;
      iArray[17][3]=0;

      iArray[18]=new Array();
      iArray[18][0]="�����˻�������";
      iArray[18][1]="80px";
      iArray[18][2]=100;
      iArray[18][3]=7;
      iArray[18][23]="0";

      iArray[19]=new Array();
      iArray[19][0]="������";
      iArray[19][1]="80px";
      iArray[19][2]=100;
      iArray[19][3]=7;
      iArray[19][23]="0";

      iArray[20]=new Array();
      iArray[20][0]="״̬";
      iArray[20][1]="40px";
      iArray[20][2]=100;
      iArray[20][3]=0;

      iArray[21]=new Array();
      iArray[21][0]="����Ա";
      iArray[21][1]="80px";
      iArray[21][2]=100;
      iArray[21][3]=0;
      
      iArray[22]=new Array();
			iArray[22][0]="����";
			iArray[22][1]="60px";
			iArray[22][2]=100;
			iArray[22][3]=2;
			iArray[22][4]="currency";

      PolGrid2 = new MulLineEnter("fm", "PolGrid2");
      //��Щ���Ա�����loadMulLineǰ
      PolGrid2.mulLineCount = 0;
      PolGrid2.displayTitle = 1;
      PolGrid2.locked = 1;
      PolGrid2.canSel = 0;
      PolGrid2.hiddenSubtraction = 1;
      PolGrid2.hiddenPlus = 1;
      PolGrid2.selBoxEventFuncName = "";
      PolGrid2.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
          alert(ex);
      }
}

var PolGrid3;
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid3()
  {
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������


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
      iArray[4][2]=200;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="��ˮ��";
      iArray[5][1]="100px";
      iArray[5][2]=200;
      iArray[5][3]=3;


      iArray[6]=new Array();
      iArray[6][0]="�����ʻ�����";
      iArray[6][1]="100px";
      iArray[6][2]=100;
      iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="���ֱ���";
      iArray[7][1]="100px";
      iArray[7][2]=100;
      iArray[7][3]=3;
      iArray[7][4]="RiskCode";
      iArray[7][5]="7";
      iArray[7][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[7][18]=250;
      iArray[7][19]= 0 ;

      iArray[8]=new Array();
      iArray[8][0]="���Ѽƻ�����";
      iArray[8][1]="100px";
      iArray[8][2]=100;
      iArray[8][3]=3;

      iArray[9]=new Array();
      iArray[9][0]="��Ӧ��������";
      iArray[9][1]="100px";
      iArray[9][2]=100;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="��Ӧ������������";
      iArray[10][1]="80px";
      iArray[10][2]=100;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="�˻���������";
      iArray[11][1]="80px";
      iArray[11][2]=100;
      iArray[11][3]=3;

      iArray[12]=new Array();
      iArray[12][0]="�������";
      iArray[12][1]="120px";
      iArray[12][2]=100;
      iArray[12][3]=0;

      iArray[13]=new Array();
      iArray[13][0]="���ν��";
      iArray[13][1]="100px";
      iArray[13][2]=100;
      iArray[13][3]=7;
      iArray[13][23]="0";

      iArray[14]=new Array();
      iArray[14][0]="���ε�λ��";
      iArray[14][1]="80px";
      iArray[14][2]=100;
      iArray[14][3]=3;

      iArray[15]=new Array();
      iArray[15][0]="��������";
      iArray[15][1]="80px";
      iArray[15][2]=100;
      iArray[15][3]=8;

      iArray[16]=new Array();
      iArray[16][0]="״̬";
      iArray[16][1]="40px";
      iArray[16][2]=100;
      iArray[16][3]=3;

      iArray[17]=new Array();
      iArray[17][0]="�������";
      iArray[17][1]="80px";
      iArray[17][2]=100;
      iArray[17][3]=3;

      iArray[18]=new Array();
      iArray[18][0]="����ѱ���";
      iArray[18][1]="80px";
      iArray[18][2]=100;
      iArray[18][3]=3;

      iArray[19]=new Array();
      iArray[19][0]="�˻�������";
      iArray[19][1]="80px";
      iArray[19][2]=100;
      iArray[19][3]=3;

      iArray[20]=new Array();
      iArray[20][0]="����������";
      iArray[20][1]="80px";
      iArray[20][2]=100;
      iArray[20][3]=3;

      iArray[21]=new Array();
      iArray[21][0]="����Ա";
      iArray[21][1]="80px";
      iArray[21][2]=100;
      iArray[21][3]=3;

      iArray[22]=new Array();
      iArray[22][0]="�޸�����";
      iArray[22][1]="80px";
      iArray[22][2]=100;
      iArray[22][3]=8;

      iArray[23]=new Array();
      iArray[23][0]="�޸�ʱ��";
      iArray[23][1]="80px";
      iArray[23][2]=100;
      iArray[23][3]=3;

      iArray[24]=new Array();
      iArray[24][0]="��������";
      iArray[24][1]="80px";
      iArray[24][2]=100;
      iArray[24][3]=0;
      
      iArray[25]=new Array();
      iArray[25][0]="�ʻ���ֵ";
      iArray[25][1]="80px";
      iArray[25][2]=100;
      iArray[25][3]=7;
      iArray[25][23]="0";
      
      iArray[26]=new Array();
			iArray[26][0]="����";
			iArray[26][1]="60px";
			iArray[26][2]=100;
			iArray[26][3]=2;
			iArray[26][4]="currency";

      PolGrid3 = new MulLineEnter("fm", "PolGrid3");
      //��Щ���Ա�����loadMulLineǰ
      PolGrid3.mulLineCount = 0;
      PolGrid3.displayTitle = 1;
      PolGrid3.locked = 1;
      PolGrid3.canSel = 0;
      PolGrid3.hiddenSubtraction=1;
      PolGrid3.hiddenPlus = 1;
      PolGrid3.selBoxEventFuncName = "";
      PolGrid3.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      }
      catch (ex)
      {
        alert(ex);
      }
}

</script>
