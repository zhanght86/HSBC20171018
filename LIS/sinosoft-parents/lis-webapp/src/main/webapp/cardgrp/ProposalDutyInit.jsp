<%
//�������ƣ�ProposalDutyInit.jsp
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
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
<%
  String tPolNo = "";
  tPolNo = request.getParameter("PolNo");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

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
    alert("��ProposalDutyInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tPolNo)
{
  try
  {
	initDutyGrid();
	initPremGrid();
	initGetGrid();
	queryDuty();
  }
  catch(re)
  {
    alert("ProposalDutyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initDutyGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      DutyGrid.mulLineCount = 1;   
      DutyGrid.displayTitle = 1;
      DutyGrid.hiddenPlus = 1;
      DutyGrid.hiddenSubtraction = 1;
      DutyGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ��������Ϣ�б�ĳ�ʼ��
function initPremGrid()
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
      iArray[1][0]="���α���";         	//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���������";         	//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=80;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���Ѽ��";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=80;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=80;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�ս�����";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=80;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PremGrid = new MulLineEnter( "fm" , "PremGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PremGrid.mulLineCount = 1;   
      PremGrid.displayTitle = 1;
      PremGrid.hiddenPlus = 1;
      PremGrid.hiddenSubtraction = 1;
      PremGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PremGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ��ȡ����Ϣ�б�ĳ�ʼ��
function initGetGrid()
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
      iArray[1][0]="���α���";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ȡ�����";         			//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��ȡ���";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      GetGrid = new MulLineEnter( "fm" , "GetGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GetGrid.mulLineCount = 1;   
      GetGrid.displayTitle = 1;
      GetGrid.hiddenPlus = 1;
      GetGrid.hiddenSubtraction = 1;
      GetGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //GetGrid.setRowColData(3,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>

<%
	// ��ʼ��ʱ�Ĳ�ѯ���εĺ���
	out.println("<script language=javascript>");
	out.println("function queryDuty()");
	out.println("{");
 	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	
	if (!tPolNo.equals(""))
		{
		// ������Ϣ
		LCDutySchema tLCDutySchema = new LCDutySchema();

		tLCDutySchema.setPolNo( tPolNo );
	
	  	// ׼���������� VData
		VData tVData = new VData();
	
		tVData.addElement( tLCDutySchema );
	
	  	// ���ݴ���
	  	DutyQueryUI tDutyQueryUI   = new DutyQueryUI();
		if (tDutyQueryUI.submitData(tVData,"QUERY||MAIN") == false)
		{
	      Content = " ��ѯʧ�ܣ�ԭ����: " + tDutyQueryUI.mErrors.getError(0).errorMessage;
	      FlagStr = "Fail";
		}
		else
		{
			tVData.clear();
			tVData = tDutyQueryUI.getResult();
			
			// ��ʾ
			// ���β���
			LCDutySet mLCDutySet = new LCDutySet(); 
			mLCDutySet.set((LCDutySet)tVData.getObjectByObjectName("LCDutySet",0));
			int dutyCount = mLCDutySet.size();
			for (int i = 1; i <= dutyCount; i++)
			{
			  	LCDutySchema mLCDutySchema = mLCDutySet.get(i);
			   	%>
		   	  		DutyGrid.addOne("DutyGrid")
			   		fm.DutyGrid1[<%=i-1%>].value="<%=mLCDutySchema.getDutyCode()%>";
			   		fm.DutyGrid3[<%=i-1%>].value="<%=mLCDutySchema.getPrem()%>";
			   		fm.DutyGrid4[<%=i-1%>].value="<%=mLCDutySchema.getAmnt()%>";
				<%
			}
			// end of for
		
			// ������
			LCPremSet mLCPremSet = new LCPremSet(); 
			mLCPremSet.set((LCPremSet)tVData.getObjectByObjectName("LCPremSet",0));
			int premCount = mLCPremSet.size();
			for (int i = 1; i <= premCount; i++)
			{
			  	LCPremSchema mLCPremSchema = mLCPremSet.get(i);
			   	%>
		   	  		PremGrid.addOne("PremGrid")
			   		fm.PremGrid1[<%=i-1%>].value="<%=mLCPremSchema.getDutyCode()%>";
			   		fm.PremGrid2[<%=i-1%>].value="<%=mLCPremSchema.getPayPlanCode()%>";
			   		fm.PremGrid3[<%=i-1%>].value="<%=mLCPremSchema.getPrem()%>";
			   		fm.PremGrid4[<%=i-1%>].value="<%=mLCPremSchema.getPayIntv()%>";
			   		fm.PremGrid5[<%=i-1%>].value="<%=mLCPremSchema.getPayStartDate()%>";
			   		fm.PremGrid6[<%=i-1%>].value="<%=mLCPremSchema.getPayEndDate()%>";
				<%
			} // end of for
	
			// ��ȡ��
			LCGetSet mLCGetSet = new LCGetSet(); 
			mLCGetSet.set((LCGetSet)tVData.getObjectByObjectName("LCGetSet",0));
			int getCount = mLCGetSet.size();
			for (int i = 1; i <= getCount; i++)
			{
			  	LCGetSchema mLCGetSchema = mLCGetSet.get(i);
			   	%>
		   	  		GetGrid.addOne("GetGrid")
			   		fm.GetGrid1[<%=i-1%>].value="<%=mLCGetSchema.getDutyCode()%>";
			   		fm.GetGrid2[<%=i-1%>].value="<%=mLCGetSchema.getGetDutyCode()%>";
			   		fm.GetGrid3[<%=i-1%>].value="<%=mLCGetSchema.getActuGet()%>";
			   		fm.GetGrid4[<%=i-1%>].value="<%=mLCGetSchema.getGetIntv()%>";
			   		fm.GetGrid5[<%=i-1%>].value="<%=mLCGetSchema.getGetStartDate()%>";
			   		fm.GetGrid6[<%=i-1%>].value="<%=mLCGetSchema.getGetEndDate()%>";
				<%
			} // end of for
		} // end of if
	} // end of if
	out.println("}");
	out.println("</script>");
%>

