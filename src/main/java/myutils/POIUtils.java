package myutils;

import com.sun.media.sound.SoftTuning;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.List;

public class POIUtils {

    private static String Filepath;
    private static FileInputStream fis;

    public static void getDocContext(InputStream istream) throws IOException {
        HWPFDocument doc = new HWPFDocument(istream);
        Range range = doc.getRange();// Returns the range which covers the whole
        // of the document, but excludes any
        // headers and footers.
        for (int i = 0; i < range.numParagraphs(); i++) {
            Paragraph poiPara = range.getParagraph(i);
            int j = 0;
            while (true) {
                CharacterRun run = poiPara.getCharacterRun(j++);
                System.out.println("字体颜色：" + run.getColor());//颜色
                System.out.println("字体大小：" + run.getFontSize());//字体大小
                System.out.println("字体名称：" + run.getFontName());//字体名称
                System.out.println("是否加粗：" + run.isBold());
                System.out.println("是否斜体：" + run.isItalic());
                System.out.println("文本内容：" + run.text());//文本内容
                if (run.getEndOffset() == poiPara.getEndOffset()) {
                    break;
                }
            }
        }

    }

    public static void getDocxContext(InputStream istream) throws IOException {
        XWPFDocument docx = new XWPFDocument(istream);
        List<XWPFParagraph> paraGraph = docx.getParagraphs();
        for(XWPFParagraph para :paraGraph ){
            List<XWPFRun> run = para.getRuns();
            for(XWPFRun r : run){
                int i = 0;
                System.out.println();
                System.out.println("字体颜色："+r.getColor());
                System.out.println("字体名称:"+r.getFontFamily());
                System.out.println("字体大小："+r.getFontSize());
                System.out.println("粗体："+r.isBold());
                System.out.println("斜体："+r.isItalic());
                System.out.println("文本内容:"+r.getText(i++));
                System.out.println();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Filepath = "C:\\Users\\62400\\Documents\\WeChat Files\\zfguang77\\FileStorage\\File\\2019-05\\毕设论文.doc";
        fis = new FileInputStream(Filepath);


        Filepath = "C:\\Users\\62400\\Documents\\WeChat Files\\zfguang77\\FileStorage\\File\\2019-05\\21414000_21414000_（李斌）晶澳太阳能SPC软件系统的设计与实现_2018-03-17-10-19-50.docx";
        fis = new FileInputStream(Filepath);
        getDocxContext(fis);

    }

}
