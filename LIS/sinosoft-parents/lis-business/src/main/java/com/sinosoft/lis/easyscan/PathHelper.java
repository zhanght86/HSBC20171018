package com.sinosoft.lis.easyscan;

/**
 * <p>一个静态类。提供了方法对路径进行拼接、拆分、修剪等操作</p>
 * @author Wang Zhang
 *  */
public class PathHelper {
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PathHelper.class);
	/**
	 * 获取文件路径的片段
	 * @param strPageCode
	 * @param strFileName
	 * @param tES_DOC_PAGESSet
	 * @return 一个包含有2个元素的一维String数组，其为文件路径的片段 例如 String[] { 
	 *     "D:/lis_hx/ui/||xerox/EasyScan/||86/2005/10/11/", 
	 *     "F10012563.tif" }
	 */
	public static String[] getPathPatterns(String strPageCode, String strFileName, com.sinosoft.lis.vschema.ES_DOC_PAGESSet tES_DOC_PAGESSet){
		String[] result = new String[]{"", ""};
		for(int i = 1; i <= tES_DOC_PAGESSet.size(); i++){
    		if(tES_DOC_PAGESSet.get(i).getPageCode() == Integer.parseInt(strPageCode.trim())){
    			
    			// 从UploadPrepare传过来的路径
    			// Example: D:/lis_hx/ui/||xerox/EasyScan/||86/2005/10/11/
    			String strPath = tES_DOC_PAGESSet.get(i).getPicPath();
    			result[0] = new String(strPath);    			
    			int intDotPos = strFileName.lastIndexOf(".");
				String strSuffix = "";
				if(intDotPos >= 0)
					strSuffix =	strFileName.substring(intDotPos);
				String filename = "F"
						+ (int) tES_DOC_PAGESSet.get(i).getPageID() + strSuffix;
    			result[1] = filename;
    			break;
    		}
		}
		return result;
	}
	
	/**
	 * 自动修剪路径。
	 * 如果路径不是绝对路径则去除头部的dirSeparator; 
	 * 如果路径包含文件名则去除尾部的dirSeparator, 反之则加上尾部的dirSeparator
	 * @param oldPath 待修剪的路径
	 * @param isAbsolutePath 是否是绝对路径
	 * @param isFilePath 路径中是否包含文件名
	 * @param dirSeparator 目录分割字符，如 '/' 或 '\'
	 * @return 修剪后的路径
	 */
	public static String autoTrimPath(String oldPath, boolean isAbsolutePath, boolean isFilePath, char dirSeparator){
		String result = new String(oldPath);
		if(!isAbsolutePath){
			while(result.length() > 0 && result.charAt(0) == dirSeparator){
				result = result.substring(1);
			}
		}
		while(result.length() > 0 && result.charAt(result.length() - 1) == dirSeparator){
			result = result.substring(0, result.length() - 1);
		}
		if(!isFilePath){			
			if(result.length() > 0 && result.charAt(result.length() - 1) != dirSeparator){
				result = result + new String(new char[]{dirSeparator});
			}
		}
		return result;
	}
	
	/**
	 * 根据DOCCODE找到所有的DOCPAGES然后从中拼接出文件的路径，并返回所有或得到的路径
	 * @param docCode
	 * @return 所有得到的路径. 单个路径的样例: "xerox/EasyScan/2016/05/06/861100/F10011975.tif"
	 */
	public static java.util.ArrayList<String> getPagesFilePaths(String docCode){
		java.util.ArrayList<String> result = new java.util.ArrayList<String>();
		com.sinosoft.lis.db.ES_DOC_MAINDB tES_DOC_MAINDB = new com.sinosoft.lis.db.ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(docCode);
		com.sinosoft.lis.vschema.ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.query();
		if(tES_DOC_MAINSet == null || tES_DOC_MAINSet.size() <= 0){
			return result;
		}
		for(int i = 1; i <= tES_DOC_MAINSet.size(); i++){
			com.sinosoft.lis.schema.ES_DOC_MAINSchema tES_DOC_MAINSchema = tES_DOC_MAINSet.get(i);
			com.sinosoft.lis.db.ES_DOC_PAGESDB tES_DOC_PAGESDB = new com.sinosoft.lis.db.ES_DOC_PAGESDB();
			tES_DOC_PAGESDB.setDocID(tES_DOC_MAINSchema.getDocID());
			com.sinosoft.lis.vschema.ES_DOC_PAGESSet tES_DOC_PAGESSet = tES_DOC_PAGESDB.query();
			if(tES_DOC_PAGESSet == null || tES_DOC_PAGESSet.size() <= 0){
				continue;
			}
			for(int j = 1; j <= tES_DOC_PAGESSet.size(); j++){
				com.sinosoft.lis.schema.ES_DOC_PAGESSchema tES_DOC_PAGESSchema = tES_DOC_PAGESSet.get(j);
				String picPath = tES_DOC_PAGESSchema.getPicPath();
				String pageName = tES_DOC_PAGESSchema.getPageName();
				String pageSuffix = tES_DOC_PAGESSchema.getPageSuffix();
				String pageType = tES_DOC_PAGESSchema.getPageType();
				int pageCode = tES_DOC_PAGESSchema.getPageCode();
				String tmp = PathHelper.autoTrimPath(picPath, false, false, '/') + 
						PathHelper.autoTrimPath(pageName, false, true, '/') + PathHelper.autoTrimPath(pageSuffix, false, true, '/');
				result.add(tmp);
				if(pageSuffix.compareToIgnoreCase(".gif") == 0){
					pageSuffix = ".tif";
					String tmp2 = PathHelper.autoTrimPath(picPath, false, false, '/') + 
							PathHelper.autoTrimPath(pageName, false, true, '/') + PathHelper.autoTrimPath(pageSuffix, false, true, '/');
					result.add(tmp2);
				}
			    logger.debug("根据docCode: " + docCode + " 找到pageType为 " + pageType.trim() + "的docPage, \r\n PageCode = " + pageCode + ", 路径为: \"" + tmp + "\"");
			}
		}
		return result;
	}
	
	/**
	 * 根据docCode, 获得此档案的所在服务器的 ServerBasePath.
	 * @param docCode 例如: 86113141592653.
	 * @return 此档案的所在服务器的 ServerBasePath 例如 "D:/Documents/SVN_Folder/SourceCodeNew/ui/".
	 */
    public static String getServerBasePath(String docCode){
    	String result = null;
		try {
			com.sinosoft.lis.db.ES_DOC_MAINDB tES_DOC_MAINDB = new com.sinosoft.lis.db.ES_DOC_MAINDB();
			tES_DOC_MAINDB.setDocCode(docCode);
			com.sinosoft.lis.vschema.ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.query();

			if (tES_DOC_MAINSet == null || tES_DOC_MAINSet.size() <= 0) {
				return null;
			}
			for (int i = 1; i <= tES_DOC_MAINSet.size(); i++) {
				com.sinosoft.lis.schema.ES_DOC_MAINSchema tES_DOC_MAINSchema = tES_DOC_MAINSet.get(i);
				String tManageCom = tES_DOC_MAINSchema.getManageCom();
				if (tManageCom == null) {
					continue;
				}
				com.sinosoft.utility.ExeSQL esql = new com.sinosoft.utility.ExeSQL();
				String getHostNameSqlString = "SELECT hostname FROM es_com_server WHERE managecom = " + tManageCom;

				String hostName = esql.getOneValue(getHostNameSqlString);
				if (hostName == null) {
					continue;
				}
				String getServerBasePathSqlString = "SELECT serverbasepath FROM ES_SERVER_INFO WHERE hostname = \'"
						+ hostName + "\'";
				String serverBasePath = esql.getOneValue(getServerBasePathSqlString);
				if (serverBasePath != null) {
					result = serverBasePath;
					break;
				} else {
					continue;
				}
			}
		} catch (Exception e) {

		}
    	return result;
	}
    
    /**
     * 根据docCode获得此资料的可获得到的第一个签名图的 ES_DOC_PAGESSchema
     * @param docCode  例如: 86112039928377.
     * @return 一个 com.sinosoft.lis.schema.ES_DOC_PAGESSchema 实例，表示一个 EasyScan的页面文件的Schema
     */
	public static com.sinosoft.lis.schema.ES_DOC_PAGESSchema getFirstSignaturePAGESSchemaByDocCode(String docCode){
		com.sinosoft.lis.schema.ES_DOC_PAGESSchema result = null;
		com.sinosoft.lis.db.ES_DOC_MAINDB tES_DOC_MAINDB = new com.sinosoft.lis.db.ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(docCode);
		com.sinosoft.lis.vschema.ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.query();
		if(tES_DOC_MAINSet == null || tES_DOC_MAINSet.size() <= 0){
			return result;
		}
		for(int i = 1; i <= tES_DOC_MAINSet.size(); i++){
			com.sinosoft.lis.schema.ES_DOC_MAINSchema tES_DOC_MAINSchema = tES_DOC_MAINSet.get(i);
			com.sinosoft.lis.db.ES_DOC_PAGESDB tES_DOC_PAGESDB = new com.sinosoft.lis.db.ES_DOC_PAGESDB();
			tES_DOC_PAGESDB.setDocID(tES_DOC_MAINSchema.getDocID());
			tES_DOC_PAGESDB.setPageType("7");
			com.sinosoft.lis.vschema.ES_DOC_PAGESSet tES_DOC_PAGESSet = tES_DOC_PAGESDB.query();
			if(tES_DOC_PAGESSet == null || tES_DOC_PAGESSet.size() <= 0){
				continue;
			}
			for(int j = 1; j <= tES_DOC_PAGESSet.size(); j++){
				com.sinosoft.lis.schema.ES_DOC_PAGESSchema tES_DOC_PAGESSchema = tES_DOC_PAGESSet.get(j);
				if(tES_DOC_PAGESSchema == null){
					continue;
				}
				if(tES_DOC_PAGESSchema.getPageName() != null){
					result = tES_DOC_PAGESSchema;
					return result;
				}
			}
		}
		return result;
	}
}
