<%
//�������ƣ�ClaimGetQueryInit.jsp
//�����ܣ�
//�������ڣ�2003-4-2
//������  ��lh
//�޸��ˣ�������
//�޸����ڣ�2004-2-17
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
var tClaimNo = "<%=tClaimNo%>";
var tCustomerNo = "<%=tCustomerNo%>";
var tCustomerName = "<%=tCustomerName%>";

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {       	   
          
  }
  catch(ex)
  {
    alert("��ClaimGetQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initSelBox();	 		
     
     if (tClaimNo==null && tCustomerNo==null)
     {
        fm.RgtNo.value="";        
        fm.InsuredNo.value="";                  
     }else
     {        
        fm.InsuredNo.value=tCustomerNo; 
        fm.InsuredName.value=tCustomerName;
        fm.RgtNo.value=tClaimNo;                        
     }  		
		
  }
  catch(re)
  {
    alert("ClaimGetQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>