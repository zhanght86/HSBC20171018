
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

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
  try
  {
  	fm.reset( );
    
    fm.SendOutCom.value ='';
    fm.ReceiveCom.value ='';
    
    fm.Handler.value = '<%= strOperator %>';
    fm.HandleDate.value = '<%= strOperateDate %>';
    
    fm.Operator.value = '<%= strOperator %>';
    fm.OperateDate.value = '<%= strOperateDate %>';
    fm.ComCode.value = '<%= strComCode %>';
        
    fm.btnOp.disabled = false;
  } catch(ex) {
    alert("��CertifySendOutInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
      iArray[0][2]=1;          			//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֤����";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="60px";        			//�п�
      iArray[1][2]=20;          			//�����ֵ
      iArray[1][3]=2;              		//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="CertifyCode";     //�Ƿ����ô���:null||""Ϊ������
      iArray[1][5]="1|2";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";             //��������з������ô����еڼ�λֵ
      iArray[1][9]="��֤����|code:CertifyCode&NOTNULL";

      iArray[2]=new Array();
      iArray[2][0]="��֤����";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[2][1]="80px";        		  //�п�
      iArray[2][2]=20;          			//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�����߿��";    	    //����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�����߿��";    	    //����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��ʼ����";    	    //����
      iArray[5][1]="90px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][9]="��ʼ����|INT";

      iArray[6]=new Array();
      iArray[6][0]="��ֹ����";    	    //����
      iArray[6][1]="90px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][9]="��ֹ����|INT";

      iArray[7]=new Array();
      iArray[7][0]="����";    	        //����
      iArray[7][1]="50px";            		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][9]="����|INT";

      CertifyList = new MulLineEnter( "document" , "CertifyList" );
      CertifyList.mulLineCount = 5;   
      CertifyList.displayTitle = 1;
      CertifyList.locked=0;      
      CertifyList.loadMulLine(iArray);  
    } catch(ex) {
      alert(ex);
    }
}

</script>
