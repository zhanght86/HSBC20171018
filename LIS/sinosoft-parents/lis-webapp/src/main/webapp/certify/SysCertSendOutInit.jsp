<%
//�������ƣ�SysCertSendOutInit.jsp
//�����ܣ�
//�������ڣ�2002-08-07
//������  ����ƽ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try {                                   
    fm.reset();
    
  } catch(ex) {
    alert("��SysCertSendOutInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try {
    initInpBox();
    initCertifyList();
    
  } catch(ex) {
    alert("SysCertSendOutInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    
  }
}

// ��֤�����б�ĳ�ʼ��
function initCertifyList()
{
    var iArray = new Array();

    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         		  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="50px";        				//�п�
      iArray[0][2]=50;          				//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֤����"; 	        //����
      iArray[1][1]="280px";            		//�п�
      iArray[1][2]=280;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][9]="��֤����|INT";

      iArray[2]=new Array();
      iArray[2][0]="��Ч����";    	    //����
      iArray[2][1]="280px";            		//�п�
      iArray[2][2]=280;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][9]="��Ч����|DATE";     //�����ֶε�У��

      CertifyList = new MulLineEnter( "document" , "CertifyList" );
      //��Щ���Ա�����loadMulLineǰ
	  CertifyList.mulLineCount =5;
	  CertifyList.canSel = 1;
	 
      CertifyList.displayTitle = 1;
      CertifyList.loadMulLine(iArray);
      CertifyList.delBlankLine("CertifyList");
	  
	
	  
	  
    } catch(ex) {
      alert(ex);
    }
}
</script>
