
<%
	//�������ƣ�CertifyLossRegisterInit.jsp
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

    fm.Handler.value = '<%= strOperator %>';
    fm.HandleDate.value = '<%= strOperateDate %>';
    
    fm.Operator.value = '<%= strOperator %>';
    fm.OperateDate.value = '<%= strOperateDate %>';
    
    fm.ComCode.value = '<%= strComCode %>';
    fm.Reason.value = '';
       
    fm.btnOp.disabled = false;
  } catch(ex) {
    alert("��CertifyLossRegisterInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("CertifyLossRegisterInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[0][1]="50px";        			//�п�
      iArray[0][2]=50;          			//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֤����";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="80px";        			//�п�
      iArray[1][2]=80;          			//�����ֵ
      iArray[1][3]=2;              		//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="CertifyCode";     //�Ƿ����ô���:null||""Ϊ������
      iArray[1][5]="1|2";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";             //��������з������ô����еڼ�λֵ
      iArray[1][9]="��֤����|code:CertifyCode&NOTNULL";

      iArray[2]=new Array();
      iArray[2][0]="��֤����";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[2][1]="180px";        		  //�п�
      iArray[2][2]=180;          			//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="��ʼ����";    	    //����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=180;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][9]="��ʼ����|INT&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="��ֹ����";    	    //����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=180;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��ֹ����|INT&NOTNULL";

      iArray[5]=new Array();
      iArray[5][0]="����";    	        //����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][9]="����|INT";

      CertifyList = new MulLineEnter( "document" , "CertifyList" );
      CertifyList.displayTitle = 1;
      CertifyList.loadMulLine(iArray);
      CertifyList.delBlankLine("CertifyList");
    } catch(ex) {
      alert(ex);
    }
}

</script>
