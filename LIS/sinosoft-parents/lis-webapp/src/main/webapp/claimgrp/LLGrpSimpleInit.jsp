<%
//Name: LLClaimSimpleInit.jsp
//function��
//author:
//Date:
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";
var mManageCom = "";
//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    mCurrentDate = nullToEmpty("<%= CurrentDate %>");
    mNowYear = mCurrentDate.substring(0,4);
    mNowMonth = mCurrentDate.substring(5,7);
    mNowDay = mCurrentDate.substring(8,10);
    fm.all('isNew').value = nullToEmpty("<%= tIsNew %>");    
    fm.all('RptNo').value = nullToEmpty("<%= RptNo %>");
    fm.all('Flag').value = nullToEmpty("<%= Flag %>");
    fm.all('Operator').value = nullToEmpty("<%= tG.Operator %>");
    fm.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    fm.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");    
    mManageCom = nullToEmpty("<%= tG.ManageCom %>");    
    fm.all('AllManageCom').value = mManageCom;             //ȡ����½������ȫ������ ���ڲ�ѯ�����������
    fm.all('ManageCom').value = mManageCom.substring(0,2); //ȡ����½������ǰ�������� ���ڲ�ѯҽԺ����
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
  if ((string == "null") || (string == "undefined"))
  {
    string = "";
  }
  return string;
}

function initForm()
{
  //try
  //{
    
    initParam();
    initInpBox();
    //alert(53);
    initSubReportGrid();
//    initSubMeansGrid();
    initMedFeeInHosInpGrid();
    initMedFeeCaseInfoGrid();       //��������(ԭ�˲�)
    initMedFeeOtherGrid();          //���ַ��� 
    initMedFeeThreeGrid();          //�籣����������
    initDutyKindGrid();
    initPolDutyCodeGrid();
    initLPEdorItemGrid(); //��ȫ��Ŀ��Ϣ
    initPolDutyKindGrid();
    initClaimGrid();   //alert(58); 
    queryRegister();   //alert(59); 
         //zy 2009-07-28 15:07 ���Ϊ������Ʒ����ʾ�˻�������ť
	   getLLEdorTypeCA();
	   if(fm.AccRiskCode.value=='211901')
	   {
	     fm.LCInsureAcc.disabled = false;
	   }
        
    //afterMatchDutyPayQuery();
   // alert(fm.isNew.value);        
 /*  if (fm.all('isNew').value ==0)   //�ѱ�����û����
   {          
     fm.addbutton.disabled = false;
     fm.updatebutton.disabled = true;
     fm.deletebutton.disabled = true;
     fm.addbutton2.disabled = true;
     fm.updateFeebutton.disabled = true;
     fm.deleteFeebutton.disabled = true;
     //fm.QueryReport.disabled = true;
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = true;   
     fm.simpleClaim.disabled = true; 
   }else if (fm.all('isNew').value ==1)      //��������û����
   {          
     fm.addbutton.disabled = true;
     fm.updatebutton.disabled = false;
     fm.deletebutton.disabled = false;
     fm.addbutton2.disabled = false;
     fm.updateFeebutton.disabled = false;
     fm.deleteFeebutton.disabled = false;
     //fm.QueryReport.disabled = false;
     fm.dutySet.disabled = false;
     fm.QuerydutySet.disabled = false;   
     fm.simpleClaim.disabled = false; 
   } else if (fm.all('isNew').value ==2)      //����
   {               
     fm.QueryPerson.disabled = true;
     fm.addbutton.disabled = true;
     fm.updatebutton.disabled = true;
     fm.deletebutton.disabled = true;
     fm.QueryCont2.disabled = true;
     fm.QueryCont3.disabled = true;
     fm.addbutton2.disabled = true;
     fm.updateFeebutton.disabled = false;
     fm.deleteFeebutton.disabled = false;
     //fm.QueryReport.disabled = true;
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = false;   
     divSimpleClaim2.style.display= "none";
     divSimpleClaim3.style.display= "";  
     fm.ConclusionSave.disabled = true;
     fm.ConclusionBack.disabled = true;    
   }       
*/    
    //��½������������,��½��������С��4λ�Ĳ�������в���
/*
   if( fm.AllManageCom.value.length < 4){
   
    fm.addbutton.disabled=true;
    fm.updatebutton.disabled=true;
    fm.deletebutton.disabled=true;
    
    fm.addbutton2.disabled=true;
    fm.updateFeebutton.disabled=true;
    fm.deleteFeebutton.disabled=true;
    
    //fm.QueryReport.disabled=true;
    fm.dutySet.disabled=true;
    
    fm.addUpdate.disabled=true;
    fm.saveUpdate.disabled=true;
    fm.simpleClaim.disabled=true;
    fm.simpleClaim2.disabled=true;
        
    
   }else{
    fm.addbutton.disabled=false;
    fm.updatebutton.disabled=false;
    fm.deletebutton.disabled=false;
    
    fm.addbutton2.disabled=false;
    fm.updateFeebutton.disabled=false;
    fm.deleteFeebutton.disabled=false;
    
    //fm.QueryReport.disabled=false;
    fm.dutySet.disabled=false;
    fm.addUpdate.disabled=false;
    fm.saveUpdate.disabled=false;
    fm.simpleClaim.disabled=false;
    fm.simpleClaim2.disabled=false;
   }
  */
   

   
  //}
  //catch(re)
  //{
  //  alert("��LLGrpSimpleInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  //}
}

/**
  ҳ���ʼ��
*/
function initInpBox()
{
  try
  {
      fm.saveUpdate.disabled = true;
  }
  catch(ex)
  {
    alert("��LLGrpSimpleInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


/**
  ��������Ϣ��ʼ��
*/
function initSubReportGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=10;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�ͻ�����"; //ԭ�¹��߿ͻ���
        iArray[1][1]="150px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�����"; //�¹�������
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�ͻ��Ա�";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="120px";
        iArray[4][2]=100;
        iArray[4][3]=0;

		iArray[5]=new Array()
        iArray[5][0]="�籣��־����";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=3;

        iArray[6]=new Array()
        iArray[6][0]="�籣��־";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array()
        iArray[7][0]="ɨ�����Ϣ";
        iArray[7][1]="50px";
        iArray[7][2]=100;
        iArray[7][3]=3;

        iArray[8]=new Array()
        iArray[8][0]="֤������";
        iArray[8][1]="50px";
        iArray[8][2]=100;
        iArray[8][3]=3;

        iArray[9]=new Array()
        iArray[9][0]="֤�����ͱ���";
        iArray[9][1]="50px";
        iArray[9][2]=100;
        iArray[9][3]=3;
        
        iArray[10]=new Array()
        iArray[10][0]="��������";
        iArray[10][1]="0px";
        iArray[10][2]=100;
        iArray[10][3]=3;
        
        iArray[11]=new Array()
        iArray[11][0]="ɨ�����Ϣ";
        iArray[11][1]="80px";
        iArray[11][2]=80;
        iArray[11][3]=3;        
        
        SubReportGrid = new MulLineEnter("fm","SubReportGrid");
        SubReportGrid.mulLineCount = 0;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
        SubReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        SubReportGrid.selBoxEventFuncName ="SubReportGridClick"; //��Ӧ�ĺ���������������
        SubReportGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���
        SubReportGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        SubReportGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        SubReportGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}

//��������ʼ��
function initSubMeansGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";
      iArray[0][1]="30px";
      iArray[0][2]=10;
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="ҽԺ����";
      iArray[1][1]="160px";
      iArray[1][2]=130;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="ҽԺ�ȼ�";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="��ʼ����";
      iArray[3][1]="100px";
      iArray[3][2]=600;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="��������";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="����";
      iArray[5][1]="40px";
      iArray[5][2]=130;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="��������";
      iArray[6][1]="80px";
      iArray[6][2]=80;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="���ý��";
      iArray[7][1]="80px";
      iArray[7][2]=80;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="�����־";
      iArray[8][1]="80px";
      iArray[8][2]=80;
      iArray[8][3]=0;

      SubMeansGrid = new MulLineEnter("fm","SubMeansGrid");
      SubMeansGrid.mulLineCount = 0;        // ��ʾ����
      SubMeansGrid.displayTitle = 1;
      SubMeansGrid.locked = 0;
//        SubMeansGrid.canChk =1;               // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      SubMeansGrid.canSel =1;                   // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
//      SubMeansGrid.selBoxEventFuncName ="PolDutyCodeGridClick"; //��Ӧ�ĺ���������������
//      SubMeansGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���
      SubMeansGrid.hiddenPlus=1;        //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      SubMeansGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      SubMeansGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}

//����
function initMedFeeInHosInpGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=10;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ʵ�����";
        iArray[2][1]="60px";
        iArray[2][2]=80;
        iArray[2][3]=0;
        //iArray[2][4]='llfeetype';      //����Ҫ����LDcode�еĴ���
        iArray[2][5]="2|23";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
        iArray[2][6]="0|3";
        iArray[2][15]="1";
        iArray[2][16]="1 and (code=#A# or code=#B#)";

        iArray[3]=new Array();
        iArray[3][0]="ҽԺ����";
        iArray[3][1]="60px";
        iArray[3][2]=10;
        iArray[3][3]=0;
        //iArray[3][4]='commendhospital';      //����Ҫ����LDcode�еĴ���
        //iArray[3][5]="3|4";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
        //iArray[3][6]="1|0";
        //iArray[3][15]="ManageCom";
        //iArray[3][16]=fm.ManageCom.value;

        iArray[4]=new Array();
        iArray[4][0]="ҽԺ����";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="ҽԺ�ȼ�";
        iArray[5][1]="60px";
        iArray[5][2]=10;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="�ʵ�����";
        iArray[6][1]="80px";
        iArray[6][2]=10;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="100px";
        iArray[7][2]=10;
        iArray[7][3]=3;
        //iArray[7][4]="ICDName";
        //iArray[7][9]="��������|len<=120";
        //iArray[7][18]=300;

        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="60px";
        iArray[8][2]=10;
        iArray[8][3]=3;
        //iArray[8][4]="ICDCode";
        //iArray[8][5]="8|7";
        //iArray[8][6]="0|1";
        //iArray[8][9]="ICD����|len<=20";
        //iArray[8][15]="ICDName";
        //iArray[8][17]="2";
        //iArray[8][18]=700;

        iArray[9]=new Array();
        iArray[9][0]="��ʼ����";
        iArray[9][1]="80px";
        iArray[9][2]=10;
        iArray[9][3]=0;

        iArray[10]=new Array();
        iArray[10][0]="��������";
        iArray[10][1]="80px";
        iArray[10][2]=10;
        iArray[10][3]=0;

        iArray[11]=new Array();
        iArray[11][0]="����";
        iArray[11][1]="40px";
        iArray[11][2]=10;
        iArray[11][3]=0;

        iArray[12]=new Array();
        iArray[12][0]="��������";
        iArray[12][1]="60px";
        iArray[12][2]=10;
        iArray[12][3]=0;
        //iArray[12][4]="llmedfeetype";     //�Ƿ����ô���:null||""Ϊ������
        iArray[12][5]="12|13";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
        iArray[12][6]="1|0";             //��������з������ô����еڼ�λֵ
        //iArray[12][7]="showFeeType()";             //��������з������ô����еڼ�λֵ
        iArray[12][15]="codetype";
        iArray[12][16]="codetype";
        iArray[12][17]="23";             
        iArray[12][19]="1";             //��������з������ô����еڼ�λֵ

        iArray[13]=new Array();
        iArray[13][0]="��������";
        iArray[13][1]="60px";
        iArray[13][2]=10;
        iArray[13][3]=3;

        iArray[14]=new Array();
        iArray[14][0]="ԭʼ����";
        iArray[14][1]="60px";
        iArray[14][2]=10;
        iArray[14][3]=0;

        iArray[15]=new Array();
        iArray[15][0]="�۳�����";
        iArray[15][1]="60px";
        iArray[15][2]=10;
        iArray[15][3]=0;

        iArray[16]=new Array();
        iArray[16][0]="�۳�ԭ��";
        iArray[16][1]="80px";
        iArray[16][2]=10;
        iArray[16][3]=0;
        //iArray[16][4]='deductreason';      //����Ҫ����LDcode�еĴ���
        iArray[16][5]="16|17";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
        iArray[16][6]="1|0";

        iArray[17]=new Array();
        iArray[17][0]="�۳�����";
        iArray[17][1]="80px";
        iArray[17][2]=10;
        iArray[17][3]=3;

        iArray[18]=new Array();
        iArray[18][0]="�������";
        iArray[18][1]="80px";
        iArray[18][2]=10;
        iArray[18][3]=0;

        iArray[19]=new Array();
        iArray[19][0]="�籣�⸶����";
        iArray[19][1]="80px";
        iArray[19][2]=10;
        iArray[19][3]=1;
        
        iArray[20]=new Array();
        iArray[20][0]="סԺ����";
        iArray[20][1]="80px";
        iArray[20][2]=10;
        iArray[20][3]=3;

        iArray[21]=new Array();
        iArray[21][0]="�۳���ϸ";
        iArray[21][1]="200px";
        iArray[21][2]=400;
        iArray[21][3]=0;
        
        iArray[22]=new Array();
        iArray[22][0]="�˵�������ϸ";
        iArray[22][1]="100px";
        iArray[22][2]=100;
        iArray[22][3]=3;

        iArray[23]=new Array();
        iArray[23][0]="������������";
        iArray[23][1]="100px";
        iArray[23][2]=100;
        iArray[23][3]=3;

        MedFeeInHosInpGrid = new MulLineEnter("fm","MedFeeInHosInpGrid");
        MedFeeInHosInpGrid.mulLineCount = 0;
        MedFeeInHosInpGrid.displayTitle = 1;
        MedFeeInHosInpGrid.locked = 0;
        MedFeeInHosInpGrid.canChk =0;              //��ѡ��ť��1��ʾ��0����
        MedFeeInHosInpGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
        MedFeeInHosInpGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        MedFeeInHosInpGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        MedFeeInHosInpGrid.selBoxEventFuncName = "getMedFeeInHosInpGrid"; //��������
        MedFeeInHosInpGrid.selBoxEventFuncParm ="";          //����
        MedFeeInHosInpGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

//��������ʼ��
function initPolDutyCodeGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";
      iArray[0][1]="30px";
      iArray[0][2]=10;
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="���˱�����";
      iArray[1][1]="150px";
      iArray[1][2]=150;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="��������";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";
      iArray[3][1]="60px";
      iArray[3][2]=600;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="�������";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="��������";
      iArray[5][1]="130px";
      iArray[5][2]=130;
      iArray[5][3]=0;


      iArray[6]=new Array();
      iArray[6][0]="��������";
      iArray[6][1]="80px";
      iArray[6][2]=80;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="����ֹ��";
      iArray[7][1]="80px";
      iArray[7][2]=80;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="������";
      iArray[8][1]="60px";
      iArray[8][2]=60;
      iArray[8][3]=0;

      iArray[9]=new Array();
      iArray[9][0]="����";
      iArray[9][1]="60px";
      iArray[9][2]=60;
      iArray[9][3]=0;

      iArray[10]=new Array();
      iArray[10][0]="��Ⱥ���";
      iArray[10][1]="60px";
      iArray[10][2]=60;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="���˺���";
      iArray[11][1]="60px";
      iArray[11][2]=60;
      iArray[11][3]=3;

      iArray[12]=new Array();
      iArray[12][0]="������";
      iArray[12][1]="60px";
      iArray[12][2]=60;
      iArray[12][3]=0;

      iArray[13]=new Array();
      iArray[13][0]="��������";
      iArray[13][1]="60px";
      iArray[13][2]=60;
      iArray[13][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      iArray[13][4]="llpayconclusion";    //�Ƿ����ô���: null����" "Ϊ������
      iArray[13][5]="13|14";              //���ô�����Ϣ�ֱ���ڵ�13�к͵�14�У�'|'Ϊ�ָ��
      iArray[13][6]="0|1";                //���ô�������ĵ�0����룩���ڵ�1��,��1����ƣ����ڵ�2��


      iArray[14]=new Array();
      iArray[14][0]="��������";
      iArray[14][1]="60px";
      iArray[14][2]=60;
      iArray[14][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[15]=new Array();
      iArray[15][0]="�ܸ�ԭ�����";
      iArray[15][1]="100px";
      iArray[15][2]=100;
      iArray[15][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      iArray[15][4]="llprotestreason";    //�Ƿ����ô���: null����" "Ϊ������
      iArray[15][5]="15|16";              //���ô�����Ϣ�ֱ���ڵ�13�к͵�14�У�'|'Ϊ�ָ��
      iArray[15][6]="0|1";                //���ô�������ĵ�0����룩���ڵ�1��,��1����ƣ����ڵ�2��

      iArray[16]=new Array();
      iArray[16][0]="�ܸ�ԭ������";
      iArray[16][1]="100px";
      iArray[16][2]=100;
      iArray[16][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[17]=new Array();
      iArray[17][0]="�ܸ�����";
      iArray[17][1]="60px";
      iArray[17][2]=60;
      iArray[17][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[18]=new Array();
      iArray[18][0]="���ⱸע";
      iArray[18][1]="60px";
      iArray[18][2]=60;
      iArray[18][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����


      iArray[19]=new Array();
      iArray[19][0]="Ԥ�����";
      iArray[19][1]="60px";
      iArray[19][2]=60;
      iArray[19][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[20]=new Array();
      iArray[20][0]="";
      iArray[20][1]="60px";
      iArray[20][2]=60;
      iArray[20][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[21]=new Array();
      iArray[21][0]="�������";
      iArray[21][1]="60px";
      iArray[21][2]=60;
      iArray[21][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[22]=new Array();
      iArray[22][0]="����ԭ�����";
      iArray[22][1]="100px";
      iArray[22][2]=100;
      iArray[22][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[23]=new Array();
      iArray[23][0]="����ԭ������";
      iArray[23][1]="100px";
      iArray[23][2]=100;
      iArray[23][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[24]=new Array();
      iArray[24][0]="������ע";
      iArray[24][1]="60px";
      iArray[24][2]=60;
      iArray[24][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[25]=new Array();
      iArray[25][0]="Ԥ����־����";
      iArray[25][1]="60px";
      iArray[25][2]=60;
      iArray[25][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[26]=new Array();
      iArray[26][0]="Ԥ����־";
      iArray[26][1]="60px";
      iArray[26][2]=60;
      iArray[26][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[27]=new Array();
      iArray[27][0]="������Դ";
      iArray[27][1]="60px";
      iArray[27][2]=60;
      iArray[27][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[28]=new Array();
      iArray[28][0]="dutycode";
      iArray[28][1]="60px";
      iArray[28][2]=60;
      iArray[28][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[29]=new Array();
      iArray[29][0]="�ͻ���";
      iArray[29][1]="60px";
      iArray[29][2]=60;
      iArray[29][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

        iArray[30]=new Array();
        iArray[30][0]="���屣����";
        iArray[30][1]="60px";
        iArray[30][2]=60;
        iArray[30][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

        iArray[31]=new Array();
        iArray[31][0]="���˱�����";
        iArray[31][1]="60px";
        iArray[31][2]=60;
        iArray[31][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

        iArray[32]=new Array();
        iArray[32][0]="�ͻ���";
        iArray[32][1]="60px";
        iArray[32][2]=60;
        iArray[32][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        
        iArray[33]=new Array();
        iArray[33][0]="����״̬";
        iArray[33][1]="80px";
        iArray[33][2]=80;
        iArray[33][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����        

      PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
      PolDutyCodeGrid.mulLineCount = 0;        // ��ʾ����
      PolDutyCodeGrid.displayTitle = 1;
      PolDutyCodeGrid.locked = 0;
//        PolDutyCodeGrid.canChk =1;               // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      PolDutyCodeGrid.canSel =1;                   // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      //PolDutyCodeGrid.selBoxEventFuncName ="PolDutyCodeGridClick"; //��Ӧ�ĺ���������������
//        PolDutyCodeGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���
      PolDutyCodeGrid.hiddenPlus=1;        //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolDutyCodeGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolDutyCodeGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}

//�ⰸ�ܽ��
function initClaimGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�⸶���";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="Ԥ�����";
        iArray[2][1]="80px";
        iArray[2][2]=80;
        iArray[2][3]=3;

        iArray[3]=new Array();
        iArray[3][0]="������";
        iArray[3][1]="80px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�����⸶���";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="������";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=3;
        

        ClaimGrid = new MulLineEnter("fm","ClaimGrid");
        ClaimGrid.mulLineCount = 0;       // ��ʾ����
        ClaimGrid.displayTitle = 1;
        ClaimGrid.locked = 0;
    //  ClaimGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        ClaimGrid.canSel =1;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        ClaimGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        ClaimGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        ClaimGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

//�ⰸ����������
function initDutyKindGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�������ʹ���";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="������������";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�˵����";
        iArray[3][1]="80px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�Է�/�Ը����";      //Ҳ����������
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;

		iArray[5]=new Array();
        iArray[5][0]="�����⸶���";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="�籣����";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="�����⸶���";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="�����ϼ�������";
        iArray[8][1]="0px";
        iArray[8][2]=80;
        iArray[8][3]=0;

        DutyKindGrid = new MulLineEnter("fm","DutyKindGrid");
        DutyKindGrid.mulLineCount = 0;       // ��ʾ����
        DutyKindGrid.displayTitle = 1;
        DutyKindGrid.locked = 0;
    //  DutyKindGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        DutyKindGrid.canSel =0;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        DutyKindGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        DutyKindGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        DutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


function initLPEdorItemGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";
      iArray[0][1]="30px";
      iArray[0][2]=10;
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="���˱�����";
      iArray[1][1]="80px";
      iArray[1][2]=110;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="��ȫ����";
      iArray[3][1]="60px";
      iArray[3][2]=150;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="��ȫ��Ч����";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="�˷ѽ��";
      iArray[5][1]="110px";
      iArray[5][2]=110;
      iArray[5][3]=0;


//      iArray[6]=new Array();
//      iArray[6][0]="��������";
//      iArray[6][1]="80px";
//      iArray[6][2]=80;
//      iArray[6][3]=0;
        
//      iArray[7]=new Array();
//      iArray[7][0]="����ֹ��";
//      iArray[7][1]="80px";
//      iArray[7][2]=80;
//      iArray[7][3]=0;
   
    
      
      LPEdorItemGrid = new MulLineEnter("fm","LPEdorItemGrid");
      LPEdorItemGrid.mulLineCount = 0;       // ��ʾ����
      LPEdorItemGrid.displayTitle = 1;
      LPEdorItemGrid.locked = 0;
  //  LPEdorItemGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      LPEdorItemGrid.canSel =0;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      LPEdorItemGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LPEdorItemGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LPEdorItemGrid.loadMulLine(iArray);
 
  }
  catch(ex)
  {
    alert(ex);
  }  
}

//�������������ͼ����ʼ��
function initPolDutyKindGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
	    iArray[0][0]="���";
	    iArray[0][1]="30px";
	    iArray[0][2]=10;
	    iArray[0][3]=0;

	    iArray[1]=new Array();
	    iArray[1][0]="���˱�����";
	    iArray[1][1]="105px";
	    iArray[1][2]=105;
	    iArray[1][3]=0;

	    iArray[2]=new Array();
	    iArray[2][0]="������";
	    iArray[2][1]="105px";
	    iArray[2][2]=105;
	    iArray[2][3]=3;

	    iArray[3]=new Array();
	    iArray[3][0]="��������";
	    iArray[3][1]="60px";
	    iArray[3][2]=60;
	    iArray[3][3]=0;

	    iArray[4]=new Array();
	    iArray[4][0]="��Ч����";
	    iArray[4][1]="70px";
	    iArray[4][2]=60;
	    iArray[4][3]=0;

	    iArray[5]=new Array();
	    iArray[5][0]="��������";
	    iArray[5][1]="70px";
	    iArray[5][2]=60;
	    iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="���ִ���";
        iArray[6][1]="60px";
        iArray[6][2]=60;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="140px";
        iArray[7][2]=140;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="������";
        iArray[8][1]="60px";
        iArray[8][2]=60;
        iArray[8][3]=0;
        
        
	    PolDutyKindGrid = new MulLineEnter("fm","PolDutyKindGrid");
	    PolDutyKindGrid.mulLineCount = 0;       // ��ʾ����
	    PolDutyKindGrid.displayTitle = 1;
	    PolDutyKindGrid.locked = 0;
	//  PolDutyKindGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
	    PolDutyKindGrid.canSel =1;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
	    PolDutyKindGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    PolDutyKindGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    PolDutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
�˲�¼����Ϣ
=========================================================================
*/
function initMedFeeCaseInfoGrid()
{
var iArray = new Array();
try
{
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;


    iArray[1]=new Array();
    iArray[1][0]="�м�����";
    iArray[1][1]="80px";
    iArray[1][2]=10;
    iArray[1][3]=0;

    
    iArray[2]=new Array();
    iArray[2][0]="�˲м���";
    iArray[2][1]="50px";
    iArray[2][2]=10;
    iArray[2][3]=3;        
    
    iArray[3]=new Array();
    iArray[3][0]="�˲м�������";
    iArray[3][1]="100px";
    iArray[3][2]=10;
    iArray[3][3]=0;
    
    iArray[4]=new Array();
    iArray[4][0]="�˲д���";
    iArray[4][1]="80px";
    iArray[4][2]=10;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="�˲д�������";
    iArray[5][1]="100px";
    iArray[5][2]=10;
    iArray[5][3]=0;
            
    iArray[6]=new Array();
    iArray[6][0]="�м���������";
    iArray[6][1]="80px";
    iArray[6][2]=10;
    iArray[6][3]=0;                

    iArray[7]=new Array();
    iArray[7][0]="�����������";
    iArray[7][1]="80px";
    iArray[7][2]=10;
    iArray[7][3]=0;  
    
    iArray[8]=new Array();
    iArray[8][0]="ʵ�ʸ�������";
    iArray[8][1]="80px";
    iArray[8][2]=10;
    iArray[8][3]=3;
    
    iArray[9]=new Array();
    iArray[9][0]="�ⰸ��";
    iArray[9][1]="50px";
    iArray[9][2]=10;
    iArray[9][3]=3;  
    
    iArray[10]=new Array();
    iArray[10][0]="�ְ���";
    iArray[10][1]="0px";
    iArray[10][2]=10;
    iArray[10][3]=3;
//            
    iArray[11]=new Array();
    iArray[11][0]="���";
    iArray[11][1]="0px";
    iArray[11][2]=10;
    iArray[11][3]=3;  
    
    iArray[12]=new Array();
    iArray[12][0]="�����˿ͻ���";
    iArray[12][1]="0px";
    iArray[12][2]=10;
    iArray[12][3]=3;
    
    iArray[13]=new Array();
    iArray[13][0]="������������";
    iArray[13][1]="100px";
    iArray[13][2]=10;
    iArray[13][3]=0;
    
    iArray[14]=new Array();
    iArray[14][0]="����ʱ��";
    iArray[14][1]="80px";
    iArray[14][2]=10;
    iArray[14][3]=0;  

    iArray[15]=new Array();
    iArray[15][0]="��ע";
    iArray[15][1]="80px";
    iArray[15][2]=10;
    iArray[15][3]=0;  
    
    MedFeeCaseInfoGrid = new MulLineEnter("fm","MedFeeCaseInfoGrid");
    MedFeeCaseInfoGrid.mulLineCount = 0;
    MedFeeCaseInfoGrid.displayTitle = 1;
    MedFeeCaseInfoGrid.locked = 0;
//  MedFeeCaseInfoGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
    MedFeeCaseInfoGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
    MedFeeCaseInfoGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
    MedFeeCaseInfoGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
    //MedFeeCaseInfoGrid.selBoxEventFuncName = "getMedFeeCaseInfoGrid"; //��������
//    MedFeeCaseInfoGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
    MedFeeCaseInfoGrid.loadMulLine(iArray);
}
catch(ex)
{
    alert(ex);
}
}

/**=========================================================================
����¼����Ϣ
=========================================================================
*/
function initMedFeeOtherGrid()
{
var iArray = new Array();
try
{
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="��������";
    iArray[1][1]="100px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="���ַ��ô���";
    iArray[2][1]="100px";
    iArray[2][2]=100;
    iArray[2][3]=0;        
    
    iArray[3]=new Array();
    iArray[3][0]="���ַ�������";
    iArray[3][1]="120px";
    iArray[3][2]=100;
    iArray[3][3]=0;
    
    iArray[4]=new Array();
    iArray[4][0]="���ַ��ý��";
    iArray[4][1]="75px";
    iArray[4][2]=65;
    iArray[4][3]=0;


    iArray[5]=new Array();
    iArray[5][0]="�Է�/�Ը����";
    iArray[5][1]="80px";
    iArray[5][2]=80;
    iArray[5][3]=0;
    
    iArray[6]=new Array();
    iArray[6][0]="�ⰸ��";
    iArray[6][1]="80px";
    iArray[6][2]=10;
    iArray[6][3]=3;
            

    iArray[7]=new Array();
    iArray[7][0]="�ְ���";
    iArray[7][1]="30px";
    iArray[7][2]=10;
    iArray[7][3]=3;                

    iArray[8]=new Array();
    iArray[8][0]="���";
    iArray[8][1]="0px";
    iArray[8][2]=10;
    iArray[8][3]=3;  
    
    iArray[9]=new Array();
    iArray[9][0]="�����˿ͻ���";
    iArray[9][1]="80px";
    iArray[9][2]=10;
    iArray[9][3]=3;
    
    iArray[10]=new Array();
    iArray[10][0]="�����������";
    iArray[10][1]="120px";
    iArray[10][2]=10;
    iArray[10][3]=0;  
    
    iArray[11]=new Array();
    iArray[11][0]="��ʼ����";
    iArray[11][1]="80px";
    iArray[11][2]=100;
    iArray[11][3]=0;
            
    iArray[12]=new Array();
    iArray[12][0]="��������";
    iArray[12][1]="80px";
    iArray[12][2]=100;
    iArray[12][3]=0;  

    iArray[13]=new Array();
    iArray[13][0]="�ۼ�ԭ��";
    iArray[13][1]="60px";
    iArray[13][2]=20;
    iArray[13][3]=0;  

    iArray[14]=new Array();
    iArray[14][0]="�ۼ�ԭ�����";
    iArray[14][1]="100px";
    iArray[14][2]=20;
    iArray[14][3]=3;  

    iArray[15]=new Array();
    iArray[15][0]="��ע";
    iArray[15][1]="100px";
    iArray[15][2]=20;
    iArray[15][3]=0; 
//    
//    iArray[13]=new Array();
//    iArray[13][0]="�����˿ͻ���";
//    iArray[13][1]="0px";
//    iArray[13][2]=10;
//    iArray[13][3]=3;  
    
    MedFeeOtherGrid = new MulLineEnter("fm","MedFeeOtherGrid");
    MedFeeOtherGrid.mulLineCount = 0;
    MedFeeOtherGrid.displayTitle = 1;
    MedFeeOtherGrid.locked = 0;
//  MedFeeOtherGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
    MedFeeOtherGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
    MedFeeOtherGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
    MedFeeOtherGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
    //MedFeeOtherGrid.selBoxEventFuncName = "getMedFeeOtherGrid"; //��������
//    MedFeeOtherGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
    MedFeeOtherGrid.loadMulLine(iArray);
}
catch(ex)
{
    alert(ex);
}
}

/**=========================================================================
�籣����������
=========================================================================
*/
function initMedFeeThreeGrid()
{
var iArray = new Array();
try
{
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="��������";
    iArray[1][1]="100px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="���ô���";
    iArray[2][1]="100px";
    iArray[2][2]=100;
    iArray[2][3]=0;        
    
    iArray[3]=new Array();
    iArray[3][0]="��������";
    iArray[3][1]="120px";
    iArray[3][2]=100;
    iArray[3][3]=0;
    
    iArray[4]=new Array();
    iArray[4][0]="���ý��";
    iArray[4][1]="100px";
    iArray[4][2]=100;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="�ⰸ��";
    iArray[5][1]="80px";
    iArray[5][2]=10;
    iArray[5][3]=3;
            

    iArray[6]=new Array();
    iArray[6][0]="�ְ���";
    iArray[6][1]="30px";
    iArray[6][2]=10;
    iArray[6][3]=3;                

    iArray[7]=new Array();
    iArray[7][0]="���";
    iArray[7][1]="0px";
    iArray[7][2]=10;
    iArray[7][3]=3;  
    
    iArray[8]=new Array();
    iArray[8][0]="�����˿ͻ���";
    iArray[8][1]="80px";
    iArray[8][2]=10;
    iArray[8][3]=3;
    
    iArray[9]=new Array();
    iArray[9][0]="�����������";
    iArray[9][1]="120px";
    iArray[9][2]=10;
    iArray[9][3]=0;  
    
    iArray[10]=new Array();
    iArray[10][0]="��ע";
    iArray[10][1]="80px";
    iArray[10][2]=100;
    iArray[10][3]=3;
//            
//    iArray[11]=new Array();
//    iArray[11][0]="��������";
//    iArray[11][1]="80px";
//    iArray[11][2]=10;
//    iArray[11][3]=0;  
////    
//    iArray[12]=new Array();
//    iArray[12][0]="�ְ���";
//    iArray[12][1]="0px";
//    iArray[12][2]=10;
//    iArray[12][3]=3;
//    
//    iArray[13]=new Array();
//    iArray[13][0]="�����˿ͻ���";
//    iArray[13][1]="0px";
//    iArray[13][2]=10;
//    iArray[13][3]=3;  
    
    MedFeeThreeGrid = new MulLineEnter("fm","MedFeeThreeGrid");
    MedFeeThreeGrid.mulLineCount = 0;
    MedFeeThreeGrid.displayTitle = 1;
    MedFeeThreeGrid.locked = 0;
//  MedFeeThreeGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
    MedFeeThreeGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
    MedFeeThreeGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
    MedFeeThreeGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
    //MedFeeThreeGrid.selBoxEventFuncName = "getMedFeeThreeGrid"; //��������
//    MedFeeThreeGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
    MedFeeThreeGrid.loadMulLine(iArray);
}
catch(ex)
{
    alert(ex);
}
}


</script>
