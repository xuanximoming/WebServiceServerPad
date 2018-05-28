package com.service;

import com.util.FileOperate;
import java.io.File;

import javax.jws.WebService;

@WebService
public class GeneralOpFileImpl implements IGeneralOpFile {
	@Override
	public String tranferFileStringWithEncode(String fileName) {
		return FileOperate.bin2XmlString("D://file" + File.separator + fileName);
	}

	@Override
	public String fetchFileStringWithEncode(String fileName, String fileContent) {
		try {
			FileOperate.OpenOrCreateFile(fileName);
			FileOperate.xmlString2Bin(fileContent, new File("D://file" + File.separator + fileName));
			return "上传成功";
		} catch (Exception e) {
		}
		return "上传失败";
	}
}