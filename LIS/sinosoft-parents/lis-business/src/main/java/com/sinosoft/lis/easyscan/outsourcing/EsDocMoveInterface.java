package com.sinosoft.lis.easyscan.outsourcing;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: XML生成的简单工厂模式——接口</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author liuxin
 * @version 1.0
 */
import org.jdom.Document;

import com.sinosoft.utility.VData;

public interface EsDocMoveInterface {
	// 以下方法需要在EsDocMove.java中具体实现

	/** *************从传入的数据cInputData中取数据创建xml*************** */
	public Document GetXMLDoc(VData cInputData);

	/** *************从传入的数据cInputData中取数据获取xml的名称********** */
	public String getXMLName(VData cInputData);

	/** *************获取影像文件和xml文件在应用服务器上的临时下载路径****** */
	public VData getTempFolder(VData cInputData);

	/** *************获取影像文件和xml文件在外包服务器上的临时下载路径****** */
	public VData getDesFolder(VData cInputData);

}
