<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRSchemaSave.jsp
//�����ܣ�
//�������ڣ�2007-2-1
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page import="java.util.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 	<%@page import="com.sinosoft.lis.reinsure.*"%>
 	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>

<%
	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getAttribute("GI"));
	
	RIPreceptSchema mRIPreceptSchema = new RIPreceptSchema();
	RIDivisionLineDefSet mRIDivisionLineDefSet= new RIDivisionLineDefSet();
	RIIncomeCompanySet mRIIncomeCompanySet = new RIIncomeCompanySet();
	RIRiskDivideSet mRIRiskDivideSet=new RIRiskDivideSet();
	RICalFactorValueSet mRICalFactorValueSet = new RICalFactorValueSet();
	//LRPreceptBL mLRPreceptBL = new LRPreceptBL();
  	BusinessDelegate blBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	String mRIPreceptNo = request.getParameter("RIPreceptNo");
	System.out.println("�ٱ��������: "+mRIPreceptNo);
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
	String upDe=request.getParameter("UpDe");
	System.out.println("bbbbbbbbbbbbbbbbb:��"+upDe);
	if(mOperateType.equals("SCHMINSERT"))
	{
		mRIPreceptSchema.setRIContNo(			request.getParameter("RIContNo"));
		mRIPreceptSchema.setRIPreceptNo(		request.getParameter("RIPreceptNo"));
		mRIPreceptSchema.setAccumulateDefNO(request.getParameter("AccumulateDefNO"));
		mRIPreceptSchema.setDivisionNum(		request.getParameter("DivisionNum"));
		mRIPreceptSchema.setCompanyNum(			request.getParameter("CompanyNum"));
		mRIPreceptSchema.setRIPreceptName(	request.getParameter("RIPreceptName"));
		mRIPreceptSchema.setPreceptType(		request.getParameter("PreceptType"));
		mRIPreceptSchema.setArithmeticID(		request.getParameter("ArithmeticID"));
		mRIPreceptSchema.setStandbyString1(request.getParameter("ReinsuranceSubType"));
		mRIPreceptSchema.setStandbyString2(request.getParameter("ReinsuranceType"));
		mRIPreceptSchema.setStandbyString3(request.getParameter("HierarchyNumType"));
		mRIPreceptSchema.setState(request.getParameter("PreceptState"));
	}
	if(mOperateType.equals("DELETE"))
	{
		mRIPreceptSchema.setRIContNo(				request.getParameter("RIContNo"));
		mRIPreceptSchema.setRIPreceptNo(		request.getParameter("RIPreceptNo"));
		mRIPreceptSchema.setAccumulateDefNO(request.getParameter("AccumulateDefNO"));
		mRIPreceptSchema.setDivisionNum(		request.getParameter("DivisionNum"));
		mRIPreceptSchema.setCompanyNum(			request.getParameter("CompanyNum"));
		mRIPreceptSchema.setRIPreceptName(	request.getParameter("RIPreceptName"));
	}
	if(mOperateType.equals("UPDATE"))
	{
		mRIPreceptSchema.setRIContNo(				request.getParameter("RIContNo"));
		mRIPreceptSchema.setRIPreceptNo(		request.getParameter("RIPreceptNo"));
		mRIPreceptSchema.setAccumulateDefNO(request.getParameter("AccumulateDefNO"));
		mRIPreceptSchema.setDivisionNum(		request.getParameter("DivisionNum"));
		mRIPreceptSchema.setCompanyNum(			request.getParameter("CompanyNum"));
		mRIPreceptSchema.setRIPreceptName(	request.getParameter("RIPreceptName"));
		mRIPreceptSchema.setPreceptType(		request.getParameter("PreceptType"));
	 	mRIPreceptSchema.setArithmeticID(		request.getParameter("ArithmeticID"));
	 	
		mRIPreceptSchema.setStandbyString1(request.getParameter("ReinsuranceSubType"));
		mRIPreceptSchema.setStandbyString2(request.getParameter("ReinsuranceType"));
		mRIPreceptSchema.setStandbyString3(request.getParameter("HierarchyNumType"));
		
	 	mRIPreceptSchema.setState(request.getParameter("PreceptState"));
	}
	if(mOperateType.equals("DIVCOMINSERT")){
		String[] StrNum1						=request.getParameterValues("ContCessGridNo");
		String[] divisionLineValue	=request.getParameterValues("ContCessGrid3");
		String[] divisionLineType 	=request.getParameterValues("ContCessGrid5");
		String[] DivisionFactor		=request.getParameterValues("ContCessGrid6");
		
		String[] StrNum2						=request.getParameterValues("ScaleLineGridNo");
		String[] incomeCompanyNo		=request.getParameterValues("ScaleLineGrid1"); 
		String[] incomeCompanyType	=request.getParameterValues("ScaleLineGrid4"); 
		if(StrNum1!=null)
		{
			RIDivisionLineDefSchema tRIDivisionLineDefSchema;
			for(int i=0;i<StrNum1.length;i++)
			{ 
				tRIDivisionLineDefSchema = new RIDivisionLineDefSchema();
				tRIDivisionLineDefSchema.setRIContNo(request.getParameter("RIContNo"));
				tRIDivisionLineDefSchema.setRIPreceptNo(request.getParameter("RIPreceptNo"));
				tRIDivisionLineDefSchema.setDivisionLineOrder(i+1);
				tRIDivisionLineDefSchema.setDivisionLineValue(divisionLineValue[i]);
				tRIDivisionLineDefSchema.setDivisionLineType(divisionLineType[i]); 
				tRIDivisionLineDefSchema.setDivisionFactor(DivisionFactor[i]);
				tRIDivisionLineDefSchema.setStandByOne(request.getParameter("HierarchyNumType"));
				mRIDivisionLineDefSet.add(tRIDivisionLineDefSchema);
			}
		}
		if(StrNum2!=null)
		{
			RIIncomeCompanySchema tRIIncomeCompanySchema;
			for(int i=0;i<StrNum2.length;i++)
			{
				tRIIncomeCompanySchema=new RIIncomeCompanySchema(); 
				tRIIncomeCompanySchema.setRIContNo(request.getParameter("RIContNo")); 
				tRIIncomeCompanySchema.setRIPreceptNo(request.getParameter("RIPreceptNo"));
				tRIIncomeCompanySchema.setIncomeCompanyOrder(i+1);
				tRIIncomeCompanySchema.setIncomeCompanyNo(incomeCompanyNo[i]); 
				tRIIncomeCompanySchema.setIncomeCompanyType(incomeCompanyType[i]); 
				
				mRIIncomeCompanySet.add(tRIIncomeCompanySchema);
			}
		}
	}
	
	if(mOperateType.equals("SCALEINSERT"))
	{
		String[] StrNum1						=request.getParameterValues("ContCessGridNo");
		String[] divisionLineValue	=request.getParameterValues("ContCessGrid3"); //�����ֵ
		String[] divisionLineType 	=request.getParameterValues("ContCessGrid5"); //��������ʹ���
	  
		//ȥ����ͷֳ�����  
		ArrayList lineList=new ArrayList();
		for(int i=0;i<divisionLineType.length;i++)
		{
			// �����������ʹ��벻���ڡ�04��----��ͷֳ������뵽linelist��
			if(!divisionLineType[i].equals("04"))
			{
				lineList.add(divisionLineValue[i]);
			}
		}
	  
		//�� ����õ���lineListֵת�����ַ������� �� ����divLine����
		String[] divLine=(String[])lineList.toArray(new String[0]); 
		
		//�õ��ֱ�����������
		String[] StrNum2						=request.getParameterValues("ScaleLineGridNo");
		String[] incomeCompanyNo		=request.getParameterValues("ScaleLineGrid1"); 
		String[] incomeCompanyType	=request.getParameterValues("ScaleLineGrid4"); 
  	
		String com=request.getParameter("Com");
		String line=request.getParameter("Line");
		
		int comNum=Integer.parseInt(com);   //����
		int lineNum=Integer.parseInt(line); //����
		
		//���ֱ���������mulLine�е�ֵ������ά����
		String scaleSet[][]=new String[comNum][lineNum]; 
		for(int i=1;i<=comNum;i++){ //��˾
			scaleSet[i-1]=request.getParameterValues("CessScaleGrid"+(i+1)); 
		}
		//����
		for(int i=0;i<comNum;i++){ //��˾
			for(int j=0;j<lineNum ;j++){ //�����
				System.out.println(" "+i+" ,  "+j+" "+scaleSet[i][j]);
			}
		}
	 	//����ֱ�����
		RIRiskDivideSchema tRIRiskDivideSchema;
		int k=1; 
		for(int i=divLine.length-1;i>=0;i--)
		{	//�Ա�����ѭ��
			for(int j=0;j<comNum;j++)
			{ 	//�Թ�˾ѭ��
				tRIRiskDivideSchema=new RIRiskDivideSchema();
				tRIRiskDivideSchema.setRIContNo(request.getParameter("RIContNo"));
				tRIRiskDivideSchema.setRIPreceptNo(request.getParameter("RIPreceptNo"));
				
				//��¼�������
				String areaId;
				if(k<10){           
					areaId="0"+k;
				}else{
					areaId=k+"";
				}
				tRIRiskDivideSchema.setAreaID(areaId);
				//���ò㼶
				tRIRiskDivideSchema.setAreaLevel(lineNum-i);
				//��ӷֱ���˾����
				tRIRiskDivideSchema.setIncomeCompanyNo(incomeCompanyNo[j]); 
				//������������
				tRIRiskDivideSchema.setUpperLimit(divLine[divLine.length-1-i]); 
				//������������
				if(i==divLine.length-1){
					tRIRiskDivideSchema.setLowerLimit(0);
				}
				else{
					tRIRiskDivideSchema.setLowerLimit(divLine[divLine.length-2-i]);
				}
				tRIRiskDivideSchema.setPossessScale(scaleSet[j][i]);
				
				mRIRiskDivideSet.add(tRIRiskDivideSchema);
				k++;
			}
		}
	}
  
	if(mOperateType.equals("FACTORINSERT"))
	{
		String[] StrNum=request.getParameterValues("FactorGridNo");
		String[] factorName=request.getParameterValues("FactorGrid1");
		String[] factorCode=request.getParameterValues("FactorGrid2");
		String[] factorValue=request.getParameterValues("FactorGrid3");
	  
		if(StrNum!=null)
		{
		  	RICalFactorValueSchema tRICalFactorValueSchema ;
		  	for(int i=0;i<StrNum.length;i++)
		  	{
				tRICalFactorValueSchema=new RICalFactorValueSchema();
		  	    tRICalFactorValueSchema.setReContCode(request.getParameter("RIContNo"));
		  	    tRICalFactorValueSchema.setRIPreceptNo(request.getParameter("RIPreceptNo"));
		  	    tRICalFactorValueSchema.setFactorCode(factorCode[i]);
		  	    tRICalFactorValueSchema.setFactorName(factorName[i]);
		  	    tRICalFactorValueSchema.setFactorValue(factorValue[i]);
		  	    tRICalFactorValueSchema.setFactorClass("02");
		  	    
		  	    mRICalFactorValueSet.add(tRICalFactorValueSchema);
		  	}
		}
		System.out.println(" FACTORINSERT.length: "+mRICalFactorValueSet.size());
	}
  
	if(mOperateType.equals("FEERATEINSERT"))
	{
		String tRIPreceptNo 		= request.getParameter("RIPreceptNo");
		String[] StrNum					= request.getParameterValues("FeeRateGridNo");
		String[] AreaID					= request.getParameterValues("FeeRateGrid5"); 
		String[] PremFeeTableNo = request.getParameterValues("FeeRateGrid6");
		String[] ComFeeTableNo 	= request.getParameterValues("FeeRateGrid8");	  

		if(StrNum!=null)
		{
			RIRiskDivideSchema tRIRiskDivideSchema ;
			for(int i=0;i<StrNum.length;i++)
			{
				tRIRiskDivideSchema = new RIRiskDivideSchema();
				tRIRiskDivideSchema.setRIPreceptNo(tRIPreceptNo);
				tRIRiskDivideSchema.setAreaID(AreaID[i]);
				tRIRiskDivideSchema.setPremFeeTableNo(PremFeeTableNo[i]);
				tRIRiskDivideSchema.setComFeeTableNo(ComFeeTableNo[i]);		  	
				mRIRiskDivideSet.add(tRIRiskDivideSchema);
			}
		}
	}
  if(mOperateType.equals("SCHMINSERT")){
    mDescType = ""+"�����ٱ�����"+"";
  }
  if(mOperateType.equals("DIVCOMINSERT")){
    mDescType = ""+"���������,�����ٱ���˾��"+"";
  }
  if(mOperateType.equals("SCALEINSERT")){
    mDescType = ""+"�ֱ�����"+"";
  }
  if(mOperateType.equals("FACTORINSERT")){
    mDescType = ""+"�������ֱ�Ҫ����Ϣ"+"";
  }
  if(mOperateType.equals("UPDATE")){
    mDescType = ""+"�޸��ٱ�������Ϣ"+"";
  }
  if(mOperateType.equals("DELETE")){
    mDescType = ""+"ɾ���ٱ�����"+"";
  }
  if(mOperateType.equals("QUERY")){
    mDescType = ""+"��ѯ�ٱ�����"+"";
  }
  if(mOperateType.equals("FEERATEINSERT")){
    mDescType = ""+"������������"+"";
  }
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("RIPreceptNo",mRIPreceptNo);
	tVData.addElement(tTransferData);
  try{
  	tVData.addElement(globalInput);
  	if(mOperateType.equals("SCHMINSERT"))
	  {
	    tVData.addElement(mRIPreceptSchema);
	  }
	  if(mOperateType.equals("DELETE"))
	  {
	    tVData.addElement(mRIPreceptSchema);
	  }
	  if(mOperateType.equals("UPDATE"))
  	{
  	  tVData.addElement(mRIPreceptSchema);
  	  tVData.addElement(upDe);                 
  	}
	  if(mOperateType.equals("DIVCOMINSERT"))
	  {
	    tVData.addElement(mRIDivisionLineDefSet);
	    tVData.addElement(mRIIncomeCompanySet);
	  }
	  if(mOperateType.equals("SCALEINSERT"))
	  {
	    tVData.addElement(mRIRiskDivideSet);
	  }
	  if(mOperateType.equals("FACTORINSERT"))
	  {
    	tVData.addElement(mRICalFactorValueSet);
  	}
  	if(mOperateType.equals("FEERATEINSERT"))
    {
	    tVData.addElement(mRIRiskDivideSet);
    } 
    if(!blBusinessDelegate.submitData(tVData,mOperateType,"LRPreceptBL")){
    	tError = blBusinessDelegate.getCErrors(); 
    	System.out.println(" ʧ�ܣ�ԭ����:" + tError.getFirstError());
    }
  }
  catch(Exception ex){
    Content = mDescType+""+"ʧ�ܣ�ԭ����:"+"" + ex.toString(); 
    FlagStr = "Fail"; 
  }
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  String result = "";
  if (FlagStr==""){
    tError = blBusinessDelegate.getCErrors(); 
    if (!tError.needDealError())
    {
      TransferData sTransferData = (TransferData)blBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
	  result = (String)sTransferData.getValueByName("String");
      Content = mDescType+""+"�ɹ���"+""+" "+"�ٱ�������"+""+result; 
      if(mOperateType.equals("SCHMINSERT")){
  		  Content = Content+""+"������������ߺͷֱ�������!"+"";
  		}
  		if(mOperateType.equals("DIVCOMINSERT")){
		    Content = Content+""+"�������÷ֱ�����!"+"";
		  }
		  if(mOperateType.equals("SCALEINSERT")){
		    Content = Content;
		  }
		  if(mOperateType.equals("FACTORINSERT")){
    		Content = Content;
  		}
  		if(mOperateType.equals("UPDATE")){
  		  mDescType = ""+"�޸��ٱ�������Ϣ"+"";
  		}
  		if(mOperateType.equals("DELETE")){
  		  mDescType = ""+"ɾ���ٱ�����"+"";
  		}
  		if(mOperateType.equals("QUERY")){
  		  mDescType = ""+"��ѯ�ٱ�����"+"";
  		}else{
  			mDescType = ""+"�����ɹ�"+"";
  		}
    	FlagStr = "Succ";
    }
    else{
    	Content = mDescType+" "+"ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>"); 
</script>
</html>