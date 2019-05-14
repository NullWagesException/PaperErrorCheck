package myutils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 将word文档进行解压缩，并提取其中的document.xml
 */
public class ZipDocument {

    /**
     * 将word文档的后缀名修改为RAR，以达到压缩的目的
     * @param filename
     * @return
     */
    public static String zip(String filename){
        File file = new File("D://KDR//" + filename);
        if (file.exists()){
            System.out.println(filename);
            String newoldName = filename.substring(0, filename.lastIndexOf("."))+".rar";
            System.out.println(newoldName);
            File newFile = new File("D://KDR//",newoldName);
            boolean flag = file.renameTo(newFile);
            return "D://KDR//" + newFile.getName();
        }
        return null;
    }


    /**
     * 解压文件
     * @param zipPath 要解压的目标文件
     * @return 解压结果：成功，失败
     */
    public static boolean decompressZip(String zipPath) {
        String descDir = "D:\\KDR\\unzip\\";
        File zipFile = new File(zipPath);
        boolean flag = false;
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        try {
            zip = new ZipFile(zipFile, Charset.forName("gbk"));//防止中文目录，乱码
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                //指定解压后的文件夹+当前zip文件的名称
                String outPath = (descDir+zipEntryName).replace("/", File.separator);
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outPath).isDirectory()){
                    continue;
                }
                //保存文件路径信息（可利用md5.zip名称的唯一性，来判断是否已经解压）
                System.err.println("当前zip解压之后的路径为：" + outPath);
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[2048];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
            }
            flag = true;
            //必须关闭，要不然这个zip文件一直被占用着，要删删不掉，改名也不可以，移动也不行，整多了，系统还崩了。
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 获取word解压缩后的document.xml文档
     * @param tozip 需要进行压缩的word文档名 如：test.docx
     * @return
     */
    public static String getDocumentXml(String tozip){
        try {
            String zipfilename = zip(tozip);
            if (zipfilename == null)
                return null;
            decompressZip(zipfilename);
            return "D:\\KDR\\unzip\\word\\document.xml";
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //        zip("test.doc");
        decompressZip("D:\\KDR\\21414000_21414000_（李斌）晶澳太阳能SPC软件系统的设计与实现_2018-03-17-10-19-50.rar");
    }

}
