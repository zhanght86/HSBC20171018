<%
//�������ƣ�GrpPersonPrintInit_IDGF.jsp
//�����ܣ�
//�������ڣ�2007-04-06 10:00
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
  String strManageCom = globalInput.ComCode;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {
  }
  catch(ex)
  {
    alert("��GrpPersonPrintInit_IDGF.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
  	
    initInpBox();
    initPolGrid();
    //document.all('ManageCom').value = <%=strManageCom%>;
  }
  catch(re)
  {
    alert("GrpPersonPrintInit_IDGF.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}                                                                 

var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
var GrpPolGrid;       //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��

// ������Ϣ�б�ĳ�ʼ��
function initGrpPolGrid()
{
  var iArray = new Array();
      
  try
  {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���屣����";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="100px";                     //�п�
      iArray[2][2]=100;                        //�����ֵ
      iArray[2][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";                 //����
      iArray[3][1]="0px";                     //�п�
      iArray[3][2]=100;                        //�����ֵ
      iArray[3][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="Ͷ��������";               //����
      iArray[4][1]="180px";            	       //�п�
      iArray[4][2]=100;                        //�����ֵ
      iArray[4][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����������";               //����
      iArray[5][1]="80px";            	       //�п�
      iArray[5][2]=100;                        //�����ֵ
      iArray[5][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6]=new Array();
      iArray[6][0]="��Ч����";         	       //����
      iArray[6][1]="80px";            	       //�п�
      iArray[6][2]=100;                        //�����ֵ
      iArray[6][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������

      GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpPolGrid.mulLineCount = 1;   
      GrpPolGrid.displayTitle = 1;
      GrpPolGrid.hiddenPlus = 1;
      GrpPolGrid.hiddenSubtraction = 1;
      GrpPolGrid.canSel = 0;
      GrpPolGrid.canChk = 0;
 
      GrpPolGrid.loadMulLine(iArray);
      
      //��Щ����������loadMulLine����
      //GrpPolGrid.setRowColData(1,1,"asdf");
  }
  catch(ex)
  {
    alert(ex);
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
{
  var iArray = new Array();
      
  try
  {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���˱�����";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="���屣����";         		//����
      iArray[2][1]="140px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="ӡˢ��";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="Ͷ��������";         		//����
      iArray[5][1]="180px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="��Ч����";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;                          //�Ƿ���������,1��ʾ����0��ʾ������

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.canSel = 0;
      PolGrid.canChk = 1;
 
      PolGrid.loadMulLine(iArray);
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
  }
  catch(ex)
  {
    alert(ex);
  }
}

</script>
