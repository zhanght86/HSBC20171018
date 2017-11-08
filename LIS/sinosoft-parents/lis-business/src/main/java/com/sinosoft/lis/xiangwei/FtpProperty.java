package com.sinosoft.lis.xiangwei;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class FtpProperty
{
private static Logger logger = Logger.getLogger(FtpProperty.class);

	String propertyFileName = null;
	String fileName = "ftpProperty.xml";
	private String filePath = null;
	
	Properties pps;
//	public FtpProperty()
//	{
//		filePath = System.getenv("NCPAI_FTP_XML_PATH");
//		if ( filePath == null )
//		{
//			File fi = new File("");
//			filePath = fi.getAbsolutePath();
//		}
//		StringBuffer sb = new StringBuffer();
//		sb.append(filePath);
//		sb.append("/");
//		sb.append(fileName);
//		propertyFileName = sb.toString();
//		pps = new Properties();
//		loadProperty();
//	}
	
//	public void loadProperty()
//	{
//		try
//        {
//	        pps.loadFromXML(new FileInputStream( propertyFileName ));
//        }
////        catch (InvalidPropertiesFormatException e)
////        {
////	        e.printStackTrace();
////        }
//        catch (FileNotFoundException e)
//        {
//	        e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//	        e.printStackTrace();
//        }
//	}
	
	
	public void displayProperty()
	{
		pps.list(System.out);
	}
	/**
	 * @param args
	 * @param 
	 * @return 	
	 */
	public static void main(String[] args)
	{	
		FtpProperty fp = new FtpProperty();
		fp.displayProperty();
		logger.debug(fp.pps.getProperty("ncpai-ip"));
		File fi = new File("");
		try
        {
	        logger.debug(fi.getCanonicalPath());
	        logger.debug(fi.getAbsolutePath());
        }
        catch (IOException e)
        {
	        e.printStackTrace();
        }

	}

}
