
<%
	//�������ƣ���֤���,���ҳ��ؼ��ĳ�ʼ���� CertifyGetInInit.jsp
	//�����ܣ���֤ӡˢ�󡢵�֤���ŵ��¼�����ʱ����Ҫ���ջ�������֤������
	//�������ڣ�2009-01-04
	//������  ��mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	String strOperator = globalInput.Operator;
	String managecom = globalInput.ManageCom;
	String strCurTime = PubFun.getCurrentDate();
%>

<script language="JavaScript">
function initForm()
{
  try
  {
    initInpBox();
    initCertifyListGrid();
  }
  catch(re)
  {
    alert("CertifyGetInInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{
  try
  {
  	fm.reset();
  	fm.managecom.value = '<%= managecom %>';
    fm.Operator.value = '<%= strOperator %>';
    fm.OperateDate.value = '<%= strCurTime %>';
    fm.operateFlag.value = "";//�������ͱ�־��INSERTΪȷ����⣬CANCELΪ�ܾ����
  } catch(ex) {
    alert("��CertifyGetInInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


// ����ⵥ֤�б�ĳ�ʼ��
function initCertifyListGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";        			//�п�
      iArray[0][2]=50;          			//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֤����";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="60px";        			//�п�
      iArray[1][2]=80;          			//�����ֵ
      iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[1][4]="CertifyCode";     //�Ƿ����ô���:null||""Ϊ������
      //iArray[1][5]="1|2";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      //iArray[1][6]="0|1";             //��������з������ô����еڼ�λֵ
      //iArray[1][9]="��֤����|code:CertifyCode&NOTNULL";

      iArray[2]=new Array();
      iArray[2][0]="��֤����";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[2][1]="120px";        		  //�п�
      iArray[2][2]=180;          			//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���Ż���";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[3][1]="80px";        		  //�п�
      iArray[3][2]=180;          			//�����ֵ
      iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ջ���";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[4][1]="80px";        		  //�п�
      iArray[4][2]=180;          			//�����ֵ
      iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��ʼ����";    	    //����
      iArray[5][1]="150px";            		//�п�
      iArray[5][2]=180;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][9]="��ʼ����|INT";

      iArray[6]=new Array();
      iArray[6][0]="��ֹ����";    	    //����
      iArray[6][1]="150px";            		//�п�
      iArray[6][2]=180;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][9]="��ֹ����|INT";

      iArray[7]=new Array();
      iArray[7][0]="����";    	        //����
      iArray[7][1]="50px";            		//�п�
      iArray[7][2]=50;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][9]="����|INT";

      iArray[8]=new Array();
      iArray[8][0]="��֤��Դ";    	        //����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="ӡˢ���κ�";    	        //����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=50;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="���˻�ܾ����ԭ��";    	        //����
      iArray[10][1]="140px";            		//�п�
      iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=2;              			//��ʾ�Ǵ���ѡ��
      iArray[10][10]="cancelGetInReason";			//����ԭ��
      iArray[10][11]="0|^1|��������^2|�ϼ�����^3|���Ŵ���^4|��֤���^5|��֤��ʧ";
      
      iArray[11]=new Array();
      iArray[11][0]="����";    	        //����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=50;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      CertifyListGrid = new MulLineEnter( "document" , "CertifyListGrid" );
      CertifyListGrid.mulLineCount = 5; 
      CertifyListGrid.displayTitle = 1;
      CertifyListGrid.hiddenSubtraction=1;
	  CertifyListGrid.hiddenPlus=1;
      CertifyListGrid.canSel=0;
      CertifyListGrid.canChk=1;
      CertifyListGrid.loadMulLine(iArray);
    } catch(ex) {
      alert(ex);
    }
}

</script>
