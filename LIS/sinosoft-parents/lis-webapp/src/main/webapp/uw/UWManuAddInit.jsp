<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuAddInit.jsp
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
  String tContNo = "";
  String tPolNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tProposalNo = "";
  String tInsuredNo="";
tContNo = request.getParameter("ContNo");
//tPolNo = request.getParameter("PolNo");
tMissionID = request.getParameter("MissionID");
tSubMissionID = request.getParameter("SubMissionID");
tInsuredNo = request.getParameter("InsuredNo");
loggerDebug("UWManuAddInit",tInsuredNo);

%>

<SCRIPT  src="../common/javascript/Common.js" type=""></SCRIPT>

<script language="JavaScript" type="">
var tContNo12="";
var tInsuredNo12 = "";
var tsubmissionId12 = "";
var str = "";
var tRow = "";

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
try
  {
	document.all('AddReason').value = '';
   }
  catch(ex)
  {
    alert("��PEdorUWManuAddInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
//    setOption("t_sex","0=��&1=Ů&2=����");
//    setOption("sex","0=��&1=Ů&2=����");
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");
  }
  catch(ex)
  {
    alert("��PEdorUWManuAddInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm(tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo)
{
	tContNo12 = tContNo;
	tInsuredNo12 =tInsuredNo;
	tsubmissionId12 = tSubMissionID;
  var str = "";
  try
  {
	initInpBox();

	initPolAddGrid();

	initHide(tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo);

	//����ǲ�ѯ�򽫰�ť��onclick�¼���Ϊtop.close()
	//alert(tQueryFlag);
	if(tQueryFlag=="1"){
    fm.button1.style.display="none";
    fm.button3.style.display="none";
	}
	QueryPolAddGrid(tContNo,tInsuredNo);
}
  catch(re)
  {
   alert("function");
   alert("��PEdorUWManuAddInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
// ������Ϣ�б��ĳ�ʼ��
function initUWSpecGrid(str)
  {
    var iArray = new Array();

      try
      {

      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			  //�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������ 2��ʾ����ѡ��

      iArray[1]=new Array();
      iArray[1][0]="�ӷ�����";    	        //����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������ 2��ʾ����ѡ��
      iArray[1][10] = "DutyCode";
      iArray[1][11] = str;
      iArray[1][12] = "1|3|4";
      iArray[1][13] = "0|1|2";
      iArray[1][19] = 1;

      iArray[2]=new Array();
      iArray[2][0]="�ӷ�ԭ��";    	        //����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������ 2��ʾ����ѡ��
      iArray[2][10] = "PlanPay";
      iArray[2][11] = "0|^01|�����ӷ�^02|ְҵ�ӷ�";
      iArray[2][12] = "2";
      iArray[2][13] = "0";
      iArray[2][19]= 1 ;

      iArray[3]=new Array();
      iArray[3][0]="��ʼ����";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������ 2��ʾ����ѡ��

      iArray[4]=new Array();
      iArray[4][0]="��ֹ����";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������ 2��ʾ����ѡ��

      iArray[5]=new Array();
      iArray[5][0]="�ӷ�����";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������ 2��ʾ����ѡ��

      iArray[6]=new Array();
      iArray[6][0]="�ڶ��������˼ӷ�����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������ 2��ʾ����ѡ��



      iArray[7]=new Array();
      iArray[7][0]="�ӷѶ���";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=2;
      iArray[7][10] = "PayObject";
      iArray[7][11] = "0|^01|Ͷ����^02|��������^03|�౻������^04|�ڶ���������";
      iArray[7][12] = "7";
      iArray[7][13] = "0";
      iArray[7][19]= 1 ;

      iArray[8]=new Array();
      iArray[8][0]="�ӷѽ��";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������ 2��ʾ����ѡ��
      iArray[8][7]="CalHealthAddFee";


      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" );
      //��Щ���Ա�����loadMulLineǰ
      SpecGrid.mulLineCount = 1;
      SpecGrid.canSel = 1;
      SpecGrid.displayTitle = 1;
      SpecGrid.loadMulLine(iArray);
       //��Щ����������loadMulLine����
      //SpecGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б��ĳ�ʼ��
function initPolAddGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="���ձ�����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ӡˢ��";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=80;            			//�����ֵ
      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[4][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[4][5]="4";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[4][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[4][18]=250;
      iArray[4][19]=0 ;

      iArray[5]=new Array();
      iArray[5][0]="���ְ汾";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="Ͷ����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="������";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="����";         		  //����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������

      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolAddGrid.mulLineCount = 3;
      PolAddGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.loadMulLine(iArray);
      PolAddGrid.selBoxEventFuncName = "getPolGridCho";

      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tPolNo,tContNo,tMissionID, tSubMissionID,tInsuredNo)
{

	document.all('PolNo').value = tPolNo;
	document.all('ContNo').value = tContNo;
	document.all('MissionID').value = tMissionID;
	document.all('SubMissionID').value = tSubMissionID;
	document.all('InsuredNo').value = tInsuredNo;

}
</script>

