<%@ page import="java.io.*"%><%@ 
page
	import="com.sinosoft.lis.f1print.AccessVtsFile"%><%@ 
page
	import="com.sinosoft.utility.*"%><%
////loggerDebug("F1BatchPrintKernelJ1","F1BatchPrintKernelJ1");
	try {
		ByteArrayOutputStream dataStream;
		InputStream ins = (InputStream) session.getValue("PrintStream");
		if (ins != null) {
			String strTemplatePath = application.getRealPath("f1print/MStemplate/")	+ "/";
			F1PrintParser fp = null;

			ins = (InputStream) session.getValue("PrintStream");
			session.removeAttribute("PrintStream");

			if (ins == null) {
				XmlExport xmlExport = new XmlExport();

				xmlExport.createDocument("new.vts", "printer");

				fp = new F1PrintParser(xmlExport.getInputStream(),strTemplatePath);
			} else {
				fp = new F1PrintParser(ins, strTemplatePath);
			}

			dataStream = new ByteArrayOutputStream();
			// Output VTS file to a buffer
			if (!fp.output(dataStream)) {
				// //loggerDebug("F1BatchPrintKernelJ1","F1PrintKernel.jsp : fail to parse print data");
			}
		} else {

			int idx = Integer.parseInt(request.getParameter("idx"));
			String[] strVFPathNameList = (String[]) session.getValue("vfp");
			String strVFPathName = null;
			if (strVFPathNameList != null&& strVFPathNameList[idx] != null) {
				strVFPathName = strVFPathNameList[idx];

				//loggerDebug("F1BatchPrintKernelJ1","strVFPathName===" + strVFPathName);
				dataStream = new ByteArrayOutputStream();
				// Load VTS file to buffer
				//strVFPathName="e:\\ui\\f1print\\MStemplate\\new.vts";
AccessVtsFile.loadToBuffer(dataStream, strVFPathName);
				//loggerDebug("F1BatchPrintKernelJ1","==> Read VTS file from disk ");
				// Put a stream from buffer which contains VTS file to client

				byte[] bArr = dataStream.toByteArray();
				////loggerDebug("F1BatchPrintKernelJ1",bArr.length);
				OutputStream ous = response.getOutputStream();
				ous.write(bArr);
				ous.flush();

				ous.close();
			}

		}

	} catch (java.net.MalformedURLException urlEx) {
		urlEx.printStackTrace();
	} catch (java.io.IOException ioEx) {
		ioEx.printStackTrace();
	} catch (Exception ex) {
		ex.printStackTrace();
	}
%>
