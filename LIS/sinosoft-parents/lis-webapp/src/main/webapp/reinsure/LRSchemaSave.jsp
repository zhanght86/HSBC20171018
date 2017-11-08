<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRSchemaSave.jsp
//程序功能：
//创建日期：2007-2-1
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
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
	System.out.println("再保方案编号: "+mRIPreceptNo);
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String mDescType = ""; //将操作标志的英文转换成汉字的形式
	String upDe=request.getParameter("UpDe");
	System.out.println("bbbbbbbbbbbbbbbbb:　"+upDe);
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
		String[] divisionLineValue	=request.getParameterValues("ContCessGrid3"); //溢额数值
		String[] divisionLineType 	=request.getParameterValues("ContCessGrid5"); //溢额线类型代码
	  
		//去除最低分出额线  
		ArrayList lineList=new ArrayList();
		for(int i=0;i<divisionLineType.length;i++)
		{
			// 如果溢额线类型代码不等于‘04’----最低分出额，则加入到linelist中
			if(!divisionLineType[i].equals("04"))
			{
				lineList.add(divisionLineValue[i]);
			}
		}
	  
		//把 上面得到的lineList值转换成字符串数组 并 付给divLine数组
		String[] divLine=(String[])lineList.toArray(new String[0]); 
		
		//得到分保比例线设置
		String[] StrNum2						=request.getParameterValues("ScaleLineGridNo");
		String[] incomeCompanyNo		=request.getParameterValues("ScaleLineGrid1"); 
		String[] incomeCompanyType	=request.getParameterValues("ScaleLineGrid4"); 
  	
		String com=request.getParameter("Com");
		String line=request.getParameter("Line");
		
		int comNum=Integer.parseInt(com);   //行数
		int lineNum=Integer.parseInt(line); //列数
		
		//将分保比例设置mulLine中的值赋给二维数组
		String scaleSet[][]=new String[comNum][lineNum]; 
		for(int i=1;i<=comNum;i++){ //公司
			scaleSet[i-1]=request.getParameterValues("CessScaleGrid"+(i+1)); 
		}
		//测试
		for(int i=0;i<comNum;i++){ //公司
			for(int j=0;j<lineNum ;j++){ //溢额线
				System.out.println(" "+i+" ,  "+j+" "+scaleSet[i][j]);
			}
		}
	 	//保存分保比例
		RIRiskDivideSchema tRIRiskDivideSchema;
		int k=1; 
		for(int i=divLine.length-1;i>=0;i--)
		{	//对比例线循环
			for(int j=0;j<comNum;j++)
			{ 	//对公司循环
				tRIRiskDivideSchema=new RIRiskDivideSchema();
				tRIRiskDivideSchema.setRIContNo(request.getParameter("RIContNo"));
				tRIRiskDivideSchema.setRIPreceptNo(request.getParameter("RIPreceptNo"));
				
				//记录区域编码
				String areaId;
				if(k<10){           
					areaId="0"+k;
				}else{
					areaId=k+"";
				}
				tRIRiskDivideSchema.setAreaID(areaId);
				//设置层级
				tRIRiskDivideSchema.setAreaLevel(lineNum-i);
				//添加分保公司编码
				tRIRiskDivideSchema.setIncomeCompanyNo(incomeCompanyNo[j]); 
				//插入区域上限
				tRIRiskDivideSchema.setUpperLimit(divLine[divLine.length-1-i]); 
				//插入区域下限
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
    mDescType = ""+"新增再保方案"+"";
  }
  if(mOperateType.equals("DIVCOMINSERT")){
    mDescType = ""+"设置溢额线,设置再保公司数"+"";
  }
  if(mOperateType.equals("SCALEINSERT")){
    mDescType = ""+"分保比例"+"";
  }
  if(mOperateType.equals("FACTORINSERT")){
    mDescType = ""+"方案级分保要素信息"+"";
  }
  if(mOperateType.equals("UPDATE")){
    mDescType = ""+"修改再保方案信息"+"";
  }
  if(mOperateType.equals("DELETE")){
    mDescType = ""+"删除再保方案"+"";
  }
  if(mOperateType.equals("QUERY")){
    mDescType = ""+"查询再保方案"+"";
  }
  if(mOperateType.equals("FEERATEINSERT")){
    mDescType = ""+"方案费率设置"+"";
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
    	System.out.println(" 失败，原因是:" + tError.getFirstError());
    }
  }
  catch(Exception ex){
    Content = mDescType+""+"失败，原因是:"+"" + ex.toString(); 
    FlagStr = "Fail"; 
  }
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  String result = "";
  if (FlagStr==""){
    tError = blBusinessDelegate.getCErrors(); 
    if (!tError.needDealError())
    {
      TransferData sTransferData = (TransferData)blBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
	  result = (String)sTransferData.getValueByName("String");
      Content = mDescType+""+"成功，"+""+" "+"再保方案："+""+result; 
      if(mOperateType.equals("SCHMINSERT")){
  		  Content = Content+""+"，请设置溢额线和分保比例线!"+"";
  		}
  		if(mOperateType.equals("DIVCOMINSERT")){
		    Content = Content+""+"，请设置分保比例!"+"";
		  }
		  if(mOperateType.equals("SCALEINSERT")){
		    Content = Content;
		  }
		  if(mOperateType.equals("FACTORINSERT")){
    		Content = Content;
  		}
  		if(mOperateType.equals("UPDATE")){
  		  mDescType = ""+"修改再保方案信息"+"";
  		}
  		if(mOperateType.equals("DELETE")){
  		  mDescType = ""+"删除再保方案"+"";
  		}
  		if(mOperateType.equals("QUERY")){
  		  mDescType = ""+"查询再保方案"+"";
  		}else{
  			mDescType = ""+"操作成功"+"";
  		}
    	FlagStr = "Succ";
    }
    else{
    	Content = mDescType+" "+"失败，原因是:"+"" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>"); 
</script>
</html>