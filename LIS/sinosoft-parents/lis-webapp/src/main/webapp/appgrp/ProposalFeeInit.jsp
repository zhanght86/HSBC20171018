<%
//�������ƣ�ProposalInput.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
<%
	String tPolNo = request.getParameter("polNo");
	String polType = request.getParameter("polType");
	String prtNo = request.getParameter("prtNo");
  loggerDebug("ProposalFeeInit","prtNo:" + prtNo);
%>
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
	fm.all('PolNo').value = "<%=tPolNo%>";
	fm.all('polType').value = "<%=polType%>";
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
    alert("��ProposalFeeInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
  	initInpBox();
	initFeeGrid();
	queryFee();
  }
  catch(re)
  {
    alert("ProposalFeeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��ȡ����Ϣ�б�ĳ�ʼ��
function initFeeGrid()
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
      iArray[1][0]="�ݽ����վݺ�";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ѽ��";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      FeeGrid = new MulLineEnter( "fm" , "FeeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      FeeGrid.mulLineCount = 0;   
      FeeGrid.displayTitle = 1;
      FeeGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //FeeGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
<%
	// ��ʼ��ʱ�Ĳ�ѯ�ݽ��ѵĺ���
	out.println("<script language=javascript>");
	out.println("function queryFee()");
	out.println("{");
	//�������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	if (!tPolNo.equals(""))
	{	
		// �ݽ�����Ϣ
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			
		tLJTempFeeSchema.setOtherNo( tPolNo );
		if( polType.equals( "GROUP" ))
			tLJTempFeeSchema.setOtherNoType( "1" );
		if( polType.equals( "PROPOSAL" ))
			tLJTempFeeSchema.setOtherNoType( "0" );
			
	  tLJTempFeeSchema.setTempFeeNo( prtNo );
	  tLJTempFeeSchema.setOperState("0");    
	
	  	// ׼���������� VData
		VData tVData = new VData();
	
		tVData.addElement( tLJTempFeeSchema );
	
	  	// ���ݴ���
	  	ProposalFeeUI tProposalFeeUI   = new ProposalFeeUI();
		if (tProposalFeeUI.submitData(tVData,"QUERY||MAIN") == false)
		{
	      Content = " ��ѯʧ�ܣ�ԭ����: " + tProposalFeeUI.mErrors.getError(0).errorMessage;
	      FlagStr = "Fail";
		}
		else
		{
			tVData.clear();
			tVData = tProposalFeeUI.getResult();
			
			// ��ʾ
			LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet(); 
			mLJTempFeeSet.set((LJTempFeeSet)tVData.getObjectByObjectName("LJTempFeeSet",0));
			int feeCount = mLJTempFeeSet.size();
			for (int i = 1; i <= feeCount; i++)
			{
			  	LJTempFeeSchema mLJTempFeeSchema = mLJTempFeeSet.get(i);
			   	%>
			   	  FeeGrid.addOne();
			   	  FeeGrid.setRowColData(<%=i-1%>, 1, "<%=mLJTempFeeSchema.getTempFeeNo()%>");
			   	  FeeGrid.setRowColData(<%=i-1%>, 2, "<%=mLJTempFeeSchema.getPayMoney()%>");
			   	  FeeGrid.setRowColData(<%=i-1%>, 3, "<%=mLJTempFeeSchema.getPayDate()%>");
			   	  FeeGrid.setRowColData(<%=i-1%>, 4, "<%=mLJTempFeeSchema.getEnterAccDate()%>");
				<%
			} // end of for
		} // end of if
	} // end of if
	out.println("}");
	out.println("</script>");
	loggerDebug("ProposalFeeInit","ProposalFeeQuery End:" + Content + "\n" + FlagStr);
%>
	
