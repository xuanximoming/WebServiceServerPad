package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperate
{
  public static final String FILE_PATH = "D://file";

  public static void OpenOrCreateFile(String filename)
  {
    File destDir = new File("D://file");
    if (!destDir.exists())
      destDir.mkdir();
    File myFile = new File(destDir + File.separator + filename);

    if (myFile.exists()) return;
    try {
      myFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean xmlString2Bin(String string, File file)
  {
    FileOutputStream output = null;
    boolean ret = false;
    try {
      byte[] data = string.getBytes("ISO-8859-1");
      output = new FileOutputStream(file);
      output.write(data);
      output.close();
      ret = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }

  public static String bin2XmlString(String filename) {
    byte[] data = (byte[])null;
    FileInputStream input = null;
    String ret = null;
    try {
      data = new byte[(int)new File(filename).length()];
      input = new FileInputStream(new File(filename));
      input.read(data);
      input.close();
      ret = new String(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }
}