<%
//-------------------------------------------------
//程序名称：LDUnifyCodeTypeInit.jsp
//程序功能：系统统一编码维护
//创建日期：2012-01-01
//创建人  ：刘锦祥
//修改人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//-------------------------------------------------
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK" %>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	String mtransact = "";//操作状态

	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI");
	
	//判断界面是否失效
	if(tGI == null){
		FlagStr = "Fail";
		Content = "页面失效,请重新登陆";
		System.out.println("页面失效,请重新登陆");    
	}
	//获得界面传入的值

	mtransact = request.getParameter("fmtransact");
	//获得编码类型
	LDUnifyCodeTypeSchema tLDUnifyCodeTypeSchema = new LDUnifyCodeTypeSchema();
	tLDUnifyCodeTypeSchema.setSysCode(request.getParameter("SysCode"));
	tLDUnifyCodeTypeSchema.setCodeType(request.getParameter("CodeType").trim());
	tLDUnifyCodeTypeSchema.setCodeTypeName(request.getParameter("CodeTypeName").trim());
	tLDUnifyCodeTypeSchema.setMaintainFlag(request.getParameter("MaintainFlag").trim());
	tLDUnifyCodeTypeSchema.setCodeTypeDescription(request.getParameter("CodeTypeDescription"));
	
	LDUnifyCodeSchema tLDUnifyCodeSchema = new LDUnifyCodeSchema();
	
	tLDUnifyCodeSchema.setSysCode(request.getParameter("SysCode1").trim());
	tLDUnifyCodeSchema.setCodeType(request.getParameter("CodeType1").trim());
	tLDUnifyCodeSchema.setCode(request.getParameter("Code").trim());
	tLDUnifyCodeSchema.setCodeName(request.getParameter("CodeName").trim());
	tLDUnifyCodeSchema.setCodeAlias(request.getParameter("CodeAlias"));
	// 准备传输数据 VData
	VData tVData = new VData();	
	tVData.add(tGI);
	tVData.add(tLDUnifyCodeTypeSchema);
	tVData.add(tLDUnifyCodeSchema);
	try {
		// 数据传输
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, mtransact, "LDUnifyCodeTypeUI")) {
			Content = tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}else {
			Content = " 数据提交成功! ";
			FlagStr = "Succ";
		} 
	}catch(Exception ex)  {
		Content = "数据提交tComBL失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
