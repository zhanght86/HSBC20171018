package com.sinosoft.lis.easyscan.service.util;
import org.apache.log4j.Logger;

public class DocPageCheck {
private static Logger logger = Logger.getLogger(DocPageCheck.class);
	public static boolean isOnlyOnePage(String subtype, int page){
		final String[] onepagesubtypes=new String[]{
			//"UR201"	
		};
		for (int i = 0; i < onepagesubtypes.length; i++) {
			if(onepagesubtypes[i].equals(subtype)){
				if(page>1)
					return false;
			}
		}
		return true;
	}
	/**
	 * 校验 影像件页数【docPage】是否大于业务要求的页数【checkPage】
	 * @param subtype
	 * @param docPage
	 * @param checkPage
	 * @return
	 */
	public static boolean isMoreNumPage(String subtype, int docPage ,int checkPage ){
		final String[] onepagesubtypes=new String[]{
			//"UA401"	
		};
		for (int i = 0; i < onepagesubtypes.length; i++) {
			if(onepagesubtypes[i].equals(subtype)){
				if(docPage < checkPage)
					return false;
			}
		}
		return true;
	}
}
