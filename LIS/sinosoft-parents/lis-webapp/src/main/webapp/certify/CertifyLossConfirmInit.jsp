
<%
	//�������ƣ�CertifySendOutInit.jsp
	//�����ܣ�
	//�������ڣ�2002-08-07
	//������  ����ƽ
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	String strComCode = globalInput.ComCode;
	String strOperator = globalInput.Operator;
	String strOperateDate = PubFun.getCurrentDate();
%>

<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
  try
  {
  	fm.reset( );
    
    fm.Operator.value = '<%= strOperator %>';
    fm.OperateDate.value = '<%= strOperateDate %>';
    fm.Handler.value = '';
    fm.HandleDate.value = '';
    fm.ComCode.value = '<%= strComCode %>';
    
    fm.PublishDate.value = '';
    fm.Newspaper.value = '';       
  } catch(ex) {
    alert("��CertifySendOutInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initCertifyList();
  }
  catch(re)
  {
    alert("CertifySendOutInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��ʼ���š���ֹ������Ϣ�б�ĳ�ʼ��
function initCertifyList()
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
      iArray[1][0]="�������";    	    //����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=180;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="��֤����";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[2][1]="80px";        			//�п�
      iArray[2][2]=80;          			//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[1][4]="CertifyCode";     //�Ƿ����ô���:null||""Ϊ������
      //iArray[1][5]="1|2";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      //iArray[1][6]="0|1";             //��������з������ô����еڼ�λֵ
      //iArray[1][9]="��֤����|code:CertifyCode&NOTNULL";

      iArray[3]=new Array();
      iArray[3][0]="��֤����";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[3][1]="120px";        		  //�п�
      iArray[3][2]=180;          			//�����ֵ
      iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������";    	    //����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=180;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="������";    	    //����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=180;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��ʼ����";    	    //����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=180;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][9]="��ʼ����|INT";

      iArray[7]=new Array();
      iArray[7][0]="��ֹ����";    	    //����
      iArray[7][1]="120px";            		//�п�
      iArray[7][2]=180;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][9]="��ֹ����|INT";

      iArray[8]=new Array();
      iArray[8][0]="����";    	        //����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="��ʧ��ע";    	    //����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=180;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��ʧ��";    	        //����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="��ʧ����";    	    //����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=180;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      CertifyList = new MulLineEnter( "document" , "CertifyList" );
      CertifyList.displayTitle = 1;
      CertifyList.hiddenSubtraction=1;
	  CertifyList.hiddenPlus=1;
      CertifyList.canSel=0;
      CertifyList.canChk=1;
      CertifyList.loadMulLine(iArray);
      CertifyList.delBlankLine("CertifyList");
    } catch(ex) {
      alert(ex);
    }
}

</script>
