package myutils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pojo.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 对document.xml进行解析
 */
public class XmlAnalysis {

    private static boolean isbookmark = false;

    public static Set<Result> Analysis(String filepath) throws DocumentException {
        SAXReader reader = new SAXReader();
        File file = new File(filepath);
        Document document = reader.read(file);
        Element root=document.getRootElement();//获取根节点
        List<Element> root_list = root.elements();
        Element body = root_list.get(0);//获取body节点
        List<Element> body_list = body.elements();

        int index = 0;
        //遍历body，拿到所有的p节点
        for (Element body_element : root_list) {
            List<Element> p_list = body_element.elements();
            Set<Result> resultSet = new LinkedHashSet<>();
            //遍历p，拿到所有r和pPr节点
            for (Element p_element : p_list) {
                Result result = new Result();
                //当前节点的名称、文本内容和属性
                List<Element> r_list = p_element.elements();
                for (Element element : r_list) {
                    List<Element> elements = element.elements();
                    //获取一个段落中的r节点
                    if (element.getName().equals("r")) {
                        //对该r节点进行解析,其中包含rPr,t
                        for (Element r_elements : elements) {
                            if (r_elements.getName().equals("t")){
                                String content = r_elements.getTextTrim();
                                index++;
                                if (!content.equals(""))
                                    result.setContent(content);
                            }
                            if (r_elements.getName().equals("rPr")){
                                //对rPr解析
                                List<Element> rPrelements = r_elements.elements();
                                for (Element rPrelement : rPrelements) {
                                    if (rPrelement.getName().equals("rFonts")){
                                        List attributes = rPrelement.attributes();
                                        for (Object attribute : attributes) {
                                            //获取字体
                                            String font = attribute.toString().split("value")[1].split("]")[0].trim();
                                            if (!font.equals(""))
                                                result.setFont(font);
                                        }
                                    }
                                    if (rPrelement.getName().equals("color")){
                                        List attributes = rPrelement.attributes();
                                        for (Object attribute : attributes) {
                                            //获取颜色
                                            String color = attribute.toString().split("value")[1].split("]")[0].trim();
                                            if (!color.equals(""))
                                                result.setColor(color);
                                        }
                                    }
                                    if (rPrelement.getName().equals("sz")){
                                        List attributes = rPrelement.attributes();
                                        for (Object attribute : attributes) {
                                            //获取字体大小
                                            String sz = attribute.toString().split("value")[1].split("]")[0].trim();
                                            if (!sz.equals(""))
                                                result.setSz(sz);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //获取一个段落中的pPr节点
                    if (element.getName().equals("pPr")) {
                        //对该pPr节点进行解析,其中包含jc,rPr,spacing,ind
                        for (Element pPr_elements : elements) {
                            if (pPr_elements.getName().equals("jc")){
                                //获取文本位置
                                String jc = pPr_elements.attributes().toString().split("value")[1].split("]")[0].trim();
                                result.setJc(jc);
                            }
                            if (pPr_elements.getName().equals("spacing")){
                                //获取字间距
                                String spacing = pPr_elements.attributes().toString().split("value")[1].split("]")[0].trim();
                                result.setSpacing(spacing);
                            }

                            if (pPr_elements.getName().equals("ind")){
                                //获取缩进
                                String ind = pPr_elements.attributes().toString().split("value")[1].split("]")[0].trim();
                                result.setInd(ind);
                            }

                            }
                        }
                    resultSet.add(result);

                }
            }
            return resultSet;
        }
        return null;
    }

    public static List<Result> getStandardList() throws DocumentException {
        Set<Result> resultSet = Analysis("D:\\KDR\\unzip\\word\\document.xml");
        List<Result> analysis = new ArrayList<>();//需要返回的list

        for (Result result1 : resultSet) {
            System.out.println(result1);
            if (result1.getContent()!=null && result1.isIsbookmark() && result1.getSpacing() != null
                    && (result1.getFont() != null || result1.getSz() != null)) {
                analysis.add(result1);
            }
        }
        System.out.println(analysis.size());

        List<Result> StandardList = new ArrayList<>();
        Map<String,List<Result>> count = new HashMap<>();
        for (Result result : analysis) {
            if (count.get(result.getSpacing()) == null){
                ArrayList<Result> list = new ArrayList<>();
                list.add(result);
                count.put(result.getSpacing(),list);
            }else{
                List<Result> temp = count.get(result.getSpacing());
                temp.add(result);
                count.put(result.getSpacing(),temp);
            }
        }

        for (Map.Entry<String, List<Result>> entry : count.entrySet()) {
            Result tempresult = new Result();
            List<Result> value = entry.getValue();
            for (Result result : value) {
                if (result.getSpacing() != null)
                    tempresult.setSpacing(result.getSpacing());
                if (result.getSz() != null)
                    tempresult.setSz(result.getSz());
                if (result.getFont() != null) {
                    if (result.getFont().equals("\"黑体\"")) {
                        result.setFont("eastAsia");
                    }
                    tempresult.setFont(result.getFont());
                }
                if (result.getInd() != null)
                    tempresult.setInd(result.getInd());
            }
            StandardList.add(tempresult);
        }
        return StandardList;
    }

    public static Map<String,List<Result>> getTargetList() throws DocumentException {
        Set<Result> resultSet = Analysis("D:\\KDR\\unzip\\word\\document.xml");
        List<Result> analysis = new ArrayList<>();//需要返回的list

        for (Result result1 : resultSet) {
            if (result1.getContent()!=null && result1.isIsbookmark() && result1.getSpacing() != null
                    && (result1.getFont() != null || result1.getSz() != null)) {
                analysis.add(result1);
            }
        }
        Map<String,List<Result>> count = new HashMap<>();
        for (Result result : analysis) {
            if (count.get(result.getSpacing()) == null){
                ArrayList<Result> list = new ArrayList<>();
                list.add(result);
                count.put(result.getSpacing(),list);
            }else{
                List<Result> temp = count.get(result.getSpacing());
                temp.add(result);
                count.put(result.getSpacing(),temp);
            }
        }
        return count;
    }

    public static List<String> getTitleData(String standardTitle,String targetTitle) throws DocumentException {
        List<String> TitleData = new ArrayList<>();
        List<List<String>> resultStr = new ArrayList<>();


        ZipDocument.getDocumentXml(standardTitle);
        //获取标准文档中标题样式集合
        List<Result> standardList = getStandardList();


        ZipDocument.getDocumentXml(targetTitle);

        //获取对比文档中标题样式集合
        Map<String, List<Result>> targetList = getTargetList();

        for (Map.Entry<String, List<Result>> entry : targetList.entrySet()) {
            for (Result result : standardList) {
                //如果属于同一spacing
                if (entry.getKey().equals(result.getSpacing())){
                    List<Result> value = entry.getValue();
                    //创建结果集
                    List<String> stringList = new ArrayList<>();
                    for (Result result1 : value) {
                        StringBuilder sb = new StringBuilder();

                        if (result1.getFont() != null && result.getFont() != null && !result.getFont().equals(result1.getFont()))
                            if (!(result1.getFont().equals("\"黑体\"") && result.getFont().equals("\"eastAsia\"")))
                                sb.append("[标准字体为：" + result.getFont() + ",当前字体为：" + result1.getFont() + "].");
                        if (result1.getSz() != null && result.getSz() != null && !result.getSz().equals(result1.getSz()))
                            sb.append("[标准字号为：" + result.getSz() + ",当前字号为：" + result1.getSz() + "].");
                        if (result1.getSpacing() != null && result.getSpacing() != null && !result.getSpacing().equals(result1.getSpacing()))
                            sb.append("[标准缩进为：" + result.getSpacing() + ",当前缩进为：" + result1.getSpacing() + "].");
                        if (result1.getColor() != null && result.getColor() != null && !result.getColor().equals(result1.getColor()))
                            sb.append("[标准颜色为：" + result.getColor() + ",当前颜色为：" + result1.getColor() + "].");
                        //如果有发生错误，加入结果集
                        if (sb.length() > 4){
                            sb.append("错误位置：" + result1.getContent());
                            stringList.add(sb.toString());
                        }
                    }
                    //将结果集加入
                    resultStr.add(stringList);
                }
            }
        }
        for (List<String> stringList : resultStr) {
            TitleData.addAll(stringList);
        }
        return TitleData;
    }


    public static void main(String[] args) throws DocumentException {

        String standardTitle = "D:\\KDR\\21414000_21414000_（梁晓琦）基于HTML5的移动学习平台设计与实现_2018-03-17-16-43-37.docx";
        String targetTitle= "D:\\KDR\\21414000_21414000_（李斌）晶澳太阳能SPC软件系统的设计与实现_2018-03-17-10-19-50.docx";
        List<String> titleData = getTitleData(standardTitle, targetTitle);
        for (String titleDatum : titleData) {
            System.out.println(titleDatum);
        }

    }
}
