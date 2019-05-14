package myutils;

import java.util.Date;

/**
 * 不同类型文献的解析方式
 */
public class DFormat {

    /**
     * 将传入的参考文献进行格式解析
     * @param text 参考文献
     * @return 文献类型/错误信息
     */
    public static String getStandardType(String text){
        String str = null;
        int index = 0;
        if (text.contains("[J]")){
            str = "J";//期刊文章
            index++;
        }
        if (text.contains("[M]")){
            str =  "M";//专著
            index++;
        }
        if (text.contains("[C]")){
            str =  "C";//论文集
            index++;
        }
        if (text.contains("[D]")){
            str =  "D";//学位论文
            index++;
        }
        if (text.contains("[P]")){
            str =  "P";//专利
            index++;
        }
        if (text.contains("[S]")){
            str =  "S";//标准
            index++;
        }
        if (text.contains("[N]")){
            str =  "N";//报纸
            index++;
        }
        if (text.contains("[R]")){
            str =  "R";//报告
            index++;
        }
        if (text.contains("[Z]")){
            str =  "Z";//其他
            index++;
        }
        if (index == 1)
            return str;
        else
            return "error";

    }


    /**
     * 将某段参考文献按标准格式进行重排
     * @param str
     * @param type
     * @return
     */
    public static String getDdata(String str,String type,String standard){
        String result = "";
        if (type.equals("error"))
            return str + "[无法识别,缺少文献类型]";
        //如果为中文
        if (Language.isChinese(str)) {
            if (type.equals("M")) {
                result = InitType_M_CN(str, result,standard);
            }
            if (type.equals("C")) {
                result = InitType_C_CN(str, result,standard);
            }
            if (type.equals("N")) {
                result = InitType_N_CN(str, result,standard);
            }
            if (type.equals("J")) {
                result = InitType_J_CN(str, result,standard);
            }
            if (type.equals("D")) {
                result = InitType_D_CN(str, result,standard);
            }
            if (type.equals("R")) {
                result = InitType_R_CN(str, result,standard);
            }
            if (type.equals("P")) {
                result = InitType_P_CN(str, result,standard);
            }
            if (type.equals("S")) {
                result = InitType_S_CN(str, result,standard);
            }
        }else{
            //对英文的格式分析
            if (type.equals("M")) {
                result = InitType_M_EN(str, result,standard);
            }
            if (type.equals("C")) {
                result = InitType_C_EN(str, result,standard);
            }
            if (type.equals("N")) {
                result = InitType_N_EN(str, result,standard);
            }
            if (type.equals("J")) {
                result = InitType_J_EN(str, result,standard);
            }
            if (type.equals("D")) {
                result = InitType_D_EN(str, result,standard);
            }
            if (type.equals("R")) {
                result = InitType_R_EN(str, result,standard);
            }
            if (type.equals("P")) {
                result = InitType_P_EN(str, result,standard);
            }
            if (type.equals("S")) {
                result = InitType_S_EN(str, result,standard);
            }
        }
        return result;
    }

    private static String InitType_J_EN(String str, String result, String standard) {

        String num;
        String bookmaseeage = "";
        String place;
        String time = "";
        String page;
        //先分割获取作者信息部分
        String[] split = str.split("\\[J\\]");
        String str_author = split[0];
        //获取标注以及作者
        try {
            String[] split_author = str_author.split("]");
            if (!split_author[0].contains("["))
                num = "[标注错误或附近有格式错误]";
            else
                num = split_author[0] + "]";
        }catch (Exception e){
            e.printStackTrace();
            num = "[标注错误或附近有错误]";
        }

        try {
            String[] split_author = str_author.split("\\["+num+"\\]");
            for (String s : split_author) {
                bookmaseeage += s;
            }
        }catch (Exception e){
            e.printStackTrace();
            bookmaseeage = "[姓名或篇名错误或附近有错误]";
        }

        result += num + bookmaseeage;



        String str_other;

        //获取文献出版信息部分
        try {
            str_other = split[1];
        }catch (Exception e){
            e.printStackTrace();
            str_other = "[缺少书名信息或附近有错误]";
            result += str_other;
        }

        //出版社信息
        try {
            place ="[J]" +  str_other.split(",")[0];
            System.out.println(place);
        }catch (Exception e){
            e.printStackTrace();
            place = "[缺少出版社信息或附近有错误]";
        }

        System.out.println(place);



        //出版年份
        try {
            String[] split1 = str_other.split(",");
            for (int i = 1;i<split1.length;i++){
                time += split1[i];
            }
            System.out.println(time);
        }catch (Exception e){
            e.printStackTrace();
            time = "[缺少出版年份或附近有错误]";
        }


        result += place + "," + time;

        //页码
        try {
            page = str_other.split(":")[1];
        }catch (Exception e){
            e.printStackTrace();
            page = "[缺失页码或附近有错误]";
        }
        result += ":" +  page;

        if (standard.equals("IEEE")){
            result = "";
            result += num + "," + bookmaseeage + "," + place + "," + page + "," + time + ".";
            return result;
        }

        return result;

    }

    private static String InitType_N_EN(String str, String result, String standard) {
        String num;
        String bookmaseeage = "";
        String newsname;
        String time = "";
        String[] split = str.split("\\[N\\]");
        String str_author = split[0];
        //获取标注以及作者
        try {
            String[] split_author = str_author.split("]");
            if (!split_author[0].contains("["))
                num = "[标注错误或附近有格式错误]";
            else
                num = split_author[0] + "]";
        }catch (Exception e){
            e.printStackTrace();
            num = "[标注错误或附近有错误]";
        }

        try {
            String[] split_author = str_author.split("\\["+num+"\\]");
            for (String s : split_author) {
                bookmaseeage += s;
            }
        }catch (Exception e){
            e.printStackTrace();
            bookmaseeage = "[姓名或篇名错误或附近有错误]";
        }

        //获取文献出版信息部分
        String str_other;
        try {
            str_other = split[1];
        }catch (Exception e){
            e.printStackTrace();
            str_other = "[缺少书名信息或附近有错误]";
            result += str_other;
        }

        //出版社信息
        try {
            newsname ="[N]" +   str_other.split(",")[0];
        }catch (Exception e){
            e.printStackTrace();
            newsname = "[缺少报纸名信息或附近有错误]";
        }

        System.out.println(newsname);

        //出版年份
        try {
            String[] split1 = str_other.split(",");
            for (int i = 1;i<split1.length;i++){
                time += split1[i];
            }
        }catch (Exception e){
            e.printStackTrace();
            time = "[缺少出版年份或附近有错误]";
        }

        result += num + bookmaseeage;
        result += newsname + "," + time;

        return result;
    }

    private static String InitType_C_EN(String str, String result, String standard) {
        String num;
        String bookmaseeage = "";
        String place;
        String time = "";
        String page;
        //先分割获取作者信息部分
        String[] split = str.split("\\[C\\]");
        String str_author = split[0];
        //获取标注以及作者
        try {
            String[] split_author = str_author.split("]");
            if (!split_author[0].contains("["))
                num = "[标注错误或附近有格式错误]";
            else
                num = split_author[0] + "]";
        }catch (Exception e){
            e.printStackTrace();
            num = "[标注错误或附近有错误]";
        }

        try {
            String[] split_author = str_author.split("\\["+num+"\\]");
            for (String s : split_author) {
                bookmaseeage += s;
            }
        }catch (Exception e){
            e.printStackTrace();
            bookmaseeage = "[姓名或篇名错误或附近有错误]";
        }

        result += num + bookmaseeage;



        String str_other;

        //获取文献出版信息部分
        try {
            str_other = split[1];
        }catch (Exception e){
            e.printStackTrace();
            str_other = "[缺少书名信息或附近有错误]";
            result += str_other;
        }

        //出版社信息
        try {
            place ="[C]" +  str_other.split(",")[0];
        }catch (Exception e){
            e.printStackTrace();
            place = "[缺少出版社信息或附近有错误]";
        }


        //出版年份
        try {
            String[] split1 = str_other.split(",");
            for (int i = 1;i<split1.length;i++){
                time += split1[i];
            }
        }catch (Exception e){
            e.printStackTrace();
            time = "[缺少出版年份或附近有错误]";
        }


        result += place + "," + time;

        //页码
        try {
            page = str_other.split(":")[1];
        }catch (Exception e){
            e.printStackTrace();
            page = "[缺失页码或附近有错误]";
        }
        result += ":" +  page;


        return result;
    }

    private static String InitType_J_CN(String str, String result, String standard) {
        String num;
        String name;
        String bookname;
        String Pname;
        String time;
        String number;
        String page;
        //先按小数点分割获取作者信息部分
        String[] split = str.split("\\.");
        String str_author = "";
        try {
            str_author = split[0];
        }catch (Exception e){
            e.printStackTrace();
            result += "[缺少标注或作者信息]";
        }
        //获取标注以及作者
        try {
            String[] split_author = str_author.split("]");
            if (!split_author[0].contains("["))
                num = "[标注错误或附近有格式错误]";
            else
                num = split_author[0] + "]";
        }catch (Exception e){
            e.printStackTrace();
            num = "[标注错误或附近有错误]";
        }
        try {
            String[] split_author = str_author.split("]");
            name = split_author[1].trim();
            String[] strings = name.split(",");
            name="";
            for (String string : strings) {
                name += string + ",";
            }
            name = name.substring(0,name.length()-1);
        }catch (Exception e){
            e.printStackTrace();
            name = "[姓名格式错误或附近有错误]";
        }

        result += num + " " + name;

        //获取文献出版信息部分
        try {
            bookname = split[1];
        }catch (Exception e){
            e.printStackTrace();
            bookname = "[缺少书名信息或附近有错误]";
        }

        result += "." + bookname + ".";

        //获取书名信息部分
        String str_publish = "";
        try {
            str_publish = split[2];
        }catch (Exception e){
            e.printStackTrace();
            result +=  "[缺少出版信息或附近有错误]";
        }

        //根据英文分割
        String[] split_book = str_publish.split(":");
        if (split_book.length == 0)
            result +=  "[缺少信息或附近有错误]";

        //刊名信息
        Pname = split_book[0].split(",")[0];
        if (Pname.equals(""))
            Pname = "[缺少刊名信息或附近有错误]";

        //出版年份
        try {
            time = split_book[0].split(",")[1];
        }catch (Exception e){
            e.printStackTrace();
            time = "[缺少出版年份或附近有错误]";
        }

        //卷号信息
        try {
            number = split_book[0].split(",")[2];
        }catch (Exception e){
            e.printStackTrace();
            number = "[缺少卷号信息或附近有错误]";
        }


        result += Pname + "," + time + "," + number;

        //页码
        try {
            page = split_book[1];
        }catch (Exception e){
            e.printStackTrace();
            page = "[缺失页码或附近有错误]";
        }
        result += ":" +  page + ".";

        if (standard.equals("IEEE")){
            result = "";
            result += num + "," + name + "," + bookname + "," + Pname + "," + number + "," + page + "," + time + ".";
            return result;
        }
        return result;
    }

    private static String InitType_N_CN(String str, String result, String standard) {
        String num;
        String name;
        String bookname;
        String newsname;
        String time;
        String edition;
        //先按小数点分割获取作者信息部分
        String[] split = str.split("\\.");
        String str_author = "";
        try {
            str_author = split[0];
        }catch (Exception e){
            e.printStackTrace();
            result += "[缺少标注或作者信息]";
        }
        //获取标注以及作者
        try {
            String[] split_author = str_author.split("]");
            if (!split_author[0].contains("["))
                num = "[标注错误或附近有格式错误]";
            else
                num = split_author[0] + "]";
        }catch (Exception e){
            e.printStackTrace();
            num = "[标注错误或附近有错误]";
        }
        try {
            String[] split_author = str_author.split("]");
            name = split_author[1].trim();
            String[] strings = name.split(",");
            name="";
            for (String string : strings) {
                name += string + ",";
            }
            name = name.substring(0,name.length()-1);
        }catch (Exception e){
            e.printStackTrace();
            name = "[姓名格式错误或附近有错误]";
        }

        result += num + " " + name;

        //获取文献出版信息部分
        try {
            bookname = split[1];
        }catch (Exception e){
            e.printStackTrace();
            bookname = "[缺少书名信息或附近有错误]";
        }

        result += "." + bookname + ".";

        //获取书名信息部分
        String str_publish = "";
        try {
            str_publish = split[2];
        }catch (Exception e){
            e.printStackTrace();
            result +=  "[缺少出版信息或附近有错误]";
        }

        //可根据中文或者英文分割
        String[] split_book = str_publish.split(",");
        if (split_book.length == 0)
            result +=  "[缺少出版地信息或附近有错误]";

        //报纸名信息
        newsname = split_book[0].split(":")[0];
        if (newsname.equals(""))
            newsname = "[缺少报纸名信息或附近有错误]";

        //出版日期信息
        try {
            time = split_book[1].split("\\(")[0];
        }catch (Exception e){
            e.printStackTrace();
            time = "[缺少出版日期信息或附近有错误]";
        }

        //版次信息
        try {
            edition = split_book[1].split("\\(")[1].split("\\)")[0];
            if (edition.equals(""))
                edition = "(缺少版次信息或附近有错误)";
            else
                edition = "(" + edition + ")";
        }catch (Exception e){
            e.printStackTrace();
            edition = "(缺少版次信息或附近有错误)";
        }

       result += newsname + "," + time + edition;
        return result;
    }

    //对论文集类型的文献进行重新排版
    private static String InitType_C_CN(String str, String result, String standard) {

        String num;
        String name;
        String bookname;
        String place;
        String press;
        String time;
        String page;
        //先按小数点分割获取作者信息部分
        String[] split = str.split("\\.");
        String str_author = "";
        try {
            str_author = split[0];
        }catch (Exception e){
            e.printStackTrace();
            result += "[缺少标注或作者信息]";
        }
        //获取标注以及作者
        try {
            String[] split_author = str_author.split("]");
            if (!split_author[0].contains("["))
                num = "[标注错误或附近有格式错误]";
            else
                num = split_author[0] + "]";
        }catch (Exception e){
            e.printStackTrace();
            num = "[标注错误或附近有错误]";
        }
        try {
            String[] split_author = str_author.split("]");
            name = split_author[1].trim();
            String[] strings = name.split(",");
            name="";
            for (String string : strings) {
                name += string + ",";
            }
            name = name.substring(0,name.length()-1);
        }catch (Exception e){
            e.printStackTrace();
            name = "[姓名格式错误或附近有错误]";
        }

        result += num + " " + name;

        //获取文献出版信息部分
        try {
            bookname = split[1];
        }catch (Exception e){
            e.printStackTrace();
            bookname = "[缺少书名信息或附近有错误]";
        }

        result += "." + bookname + ".";

        //获取书名信息部分
        String str_publish = "";
        try {
            str_publish = split[2];
        }catch (Exception e){
            e.printStackTrace();
            result +=  "[缺少出版信息或附近有错误]";
        }

        //可根据中文或者英文分割
        String[] split_book = str_publish.split(",");
        if (split_book.length == 0)
            result +=  "[缺少出版地信息或附近有错误]";

        //出版地信息
        place = split_book[0].split(":")[0];
        if (place.equals(""))
            place = "[缺少出版地信息或附近有错误]";

        //出版社信息
        try {
            press = split_book[0].split(":")[1];
        }catch (Exception e){
            e.printStackTrace();
            press = "[缺少出版社信息或附近有错误]";
        }

        //出版年份
        try {
            time = split_book[1].split(":")[0];
        }catch (Exception e){
            e.printStackTrace();
            time = "[缺少出版年份或附近有错误]";
        }


        result += place + "," + press + "," + time;

        //页码
        try {
            page = split_book[1].split(":")[1];
        }catch (Exception e){
            e.printStackTrace();
            page = "[缺失页码或附近有错误]";
        }
        result += ":" +  page + ".";
        return result;
    }

    //对专著类型的文献进行重新排版
    private static String InitType_M_CN(String str,String result, String standard){
            String num;
            String name;
            String bookname;
            String place;
            String press;
            String time;
            String page;
            //先按小数点分割获取作者信息部分
            String[] split = str.split("\\.");
            String str_author = "";
            try {
                str_author = split[0];
            }catch (Exception e){
                e.printStackTrace();
                result += "[缺少标注或作者信息]";
            }
            //获取标注以及作者
            try {
                String[] split_author = str_author.split("]");
                if (!split_author[0].contains("["))
                    num = "[标注错误或附近有格式错误]";
                else
                    num = split_author[0] + "]";
            }catch (Exception e){
                e.printStackTrace();
                num = "[标注错误或附近有错误]";
            }
            try {
                String[] split_author = str_author.split("]");
                name = split_author[1].trim();
                String[] strings = name.split(",");
                name="";
                for (String string : strings) {
                    name += string + ",";
                }
                name = name.substring(0,name.length()-1);
            }catch (Exception e){
                e.printStackTrace();
                name = "[姓名格式错误或附近有错误]";
            }

            result += num + " " + name;

            //获取文献出版信息部分
            try {
                bookname = split[1];
            }catch (Exception e){
                e.printStackTrace();
                bookname = "[缺少书名信息或附近有错误]";
            }

            result += "." + bookname + ".";

            //获取书名信息部分
            String str_publish = "";
            try {
                str_publish = split[2];
            }catch (Exception e){
                e.printStackTrace();
                result +=  "[缺少出版信息或附近有错误]";
            }

            //可根据中文或者英文分割
            String[] split_book = str_publish.split(",");
            if (split_book.length == 0)
                result +=  "[缺少出版地信息或附近有错误]";

            //出版地信息
            place = split_book[0].split(":")[0];
            if (place.equals(""))
                place = "[缺少出版地信息或附近有错误]";

            //出版社信息
            try {
                press = split_book[0].split(":")[1];
            }catch (Exception e){
                e.printStackTrace();
                press = "[缺少出版社信息或附近有错误]";
            }

            //出版年份
            try {
                time = split_book[1].split(":")[0];
            }catch (Exception e){
                e.printStackTrace();
                time = "[缺少出版年份或附近有错误]";
            }


            result += place + "," + press + "," + time;

            //页码（可选）
            try {
                page = split_book[1].split(":")[1];
                result += ":" +  page;
            }catch (Exception e){
                e.printStackTrace();
                page = "";
            }
            result += ".";
            if (standard.equals("IEEE")){
                result = "";
                result += num + name + "," + bookname + "," + place + "," + press + "," + time + "," + page + ".";
                return result;
            }
            return result;

    }

    //对专著类型的文献进行重新排版
    private static String InitType_M_EN(String str,String result, String standard){
        String num;
        String name;
        String bookname;
        String place;
        String press;
        String time;
        String page;
        //先按小数点分割获取作者信息部分
        String[] split = str.split("\\.");
        String str_author = "";
        try {
            str_author = split[0];
        }catch (Exception e){
            e.printStackTrace();
            result += "[缺少标注或作者信息]";
        }
        //获取标注以及作者
        try {
            String[] split_author = str_author.split("]");
            if (!split_author[0].contains("["))
                num = "[标注错误或附近有格式错误]";
            else
                num = split_author[0] + "]";
        }catch (Exception e){
            e.printStackTrace();
            num = "[标注错误或附近有错误]";
        }
        try {
            String[] split_author = str_author.split("]");
            name = split_author[1].trim();
            String[] strings = name.split(",");
            name="";
            for (String string : strings) {
                name += string + ",";
            }
            name = name.substring(0,name.length()-1);
        }catch (Exception e){
            e.printStackTrace();
            name = "[姓名格式错误或附近有错误]";
        }

        result += num + " " + name;

        //获取文献出版信息部分
        try {
            bookname = split[1];
        }catch (Exception e){
            e.printStackTrace();
            bookname = "[缺少书名信息或附近有错误]";
        }

        result += "." + bookname + ".";

        //获取书名信息部分
        String str_publish = "";
        try {
            str_publish = split[2];
        }catch (Exception e){
            e.printStackTrace();
            result +=  "[缺少出版信息或附近有错误]";
        }

        //可根据中文或者英文分割
        String[] split_book = str_publish.split(",");
        if (split_book.length == 0)
            result +=  "[缺少出版地信息或附近有错误]";

        //出版地信息
        place = split_book[0].split(":")[0];
        if (place.equals(""))
            place = "[缺少出版地信息或附近有错误]";

        //出版社信息
        try {
            press = split_book[0].split(":")[1];
        }catch (Exception e){
            e.printStackTrace();
            press = "[缺少出版社信息或附近有错误]";
        }

        //出版年份
        try {
            
            time = split_book[1].split(":")[0];
        }catch (Exception e){
            e.printStackTrace();
            time = "[缺少出版年份或附近有错误]";
        }


        result += place + "," + press + "," + time;

        //页码（可选）
        try {
            page = split_book[1].split(":")[1];
            result += ":" +  page;
        }catch (Exception e){
            e.printStackTrace();
            page = "";
        }
        result += ".";

        if (standard.equals("IEEE")){
            result = "";
            result += num + name + "," + bookname + "," + place + "," + press + "," + time + "," + page + ".";
            return result;
        }
        return result;

    }

    public static String InitType_D_CN(String str, String result, String standard){
        String num;
        String name;
        String bookname;
        String place;
        String press;
        String time;
        String page;
        //先按小数点分割获取作者信息部分
        String[] split = str.split("\\.");
        String str_author = "";
        try {
            str_author = split[0];
        }catch (Exception e){
            e.printStackTrace();
            result += "[缺少标注或作者信息]";
        }
        //获取标注以及作者
        try {
            String[] split_author = str_author.split("]");
            if (!split_author[0].contains("["))
                num = "[标注错误或附近有格式错误]";
            else
                num = split_author[0] + "]";
        }catch (Exception e){
            e.printStackTrace();
            num = "[标注错误或附近有错误]";
        }
        try {
            String[] split_author = str_author.split("]");
            name = split_author[1].trim();
            String[] strings = name.split(",");
            name="";
            for (String string : strings) {
                name += string + ",";
            }
            name = name.substring(0,name.length()-1);
        }catch (Exception e){
            e.printStackTrace();
            name = "[姓名格式错误或附近有错误]";
        }

        result += num + " " + name;

        //获取书名信息部分
        try {
            bookname = split[1];
        }catch (Exception e){
            e.printStackTrace();
            bookname = "[缺少书名信息或附近有错误]";
        }

        result += "." + bookname + ".";

        //获取文献出版信息部分
        String str_publish = "";
        try {
            str_publish = split[2];
        }catch (Exception e){
            e.printStackTrace();
            result +=  "[缺少出版信息或附近有错误]";
        }

        //可根据中文或者英文分割
        String[] split_book = str_publish.split(",");
        if (split_book.length == 0)
            result +=  "[缺少出版地信息或附近有错误]";

        //出版地信息
        place = split_book[0].split(":")[0];
        if (place.equals(""))
            place = "[缺少出版地信息或附近有错误]";

        //出版社信息
        try {
            press = split_book[0].split(":")[1];
            if (press.contains("大学")){
                press = press.split("大学")[0] + "大学" + press.split("大学")[1];
            }
            if (press.contains("学院")){
                press = press.split("学院")[0] + "学院" + press.split("学院")[1];
            }
        }catch (Exception e){
            e.printStackTrace();
            press = "[缺少出版社信息或附近有错误]";
        }


        //出版年份
        try {
            time = split_book[1].split(":")[0];
        }catch (Exception e){
            e.printStackTrace();
            time = "[缺少出版年份或附近有错误]";
        }


        result += place + ":" + press + "," + time;

        //页码（可选）
        try {
            page = split_book[1].split(":")[1];
            result += ":" +  page;
        }catch (Exception e){
            e.printStackTrace();
            page = "";
        }
        result += ".";

        if (standard.equals("ACM")){
            result = num + name + "." + bookname + press + "," + time;
        }

        return result;
    }
    public static String InitType_R_CN(String str, String result, String standard){
        String num;
        String name;
        String bookname;
        String place;
        String press;
        String time;
        String page;
        //先按小数点分割获取作者信息部分
        String[] split = str.split("\\.");
        String str_author = "";
        try {
            str_author = split[0];
        }catch (Exception e){
            e.printStackTrace();
            result += "[缺少标注或作者信息]";
        }
        //获取标注以及作者
        try {
            String[] split_author = str_author.split("]");
            if (!split_author[0].contains("["))
                num = "[标注错误或附近有格式错误]";
            else
                num = split_author[0] + "]";
        }catch (Exception e){
            e.printStackTrace();
            num = "[标注错误或附近有错误]";
        }
        try {
            String[] split_author = str_author.split("]");
            name = split_author[1].trim();
            String[] strings = name.split(",");
            name="";
            for (String string : strings) {
                name += string + ",";
            }
            name = name.substring(0,name.length()-1);
        }catch (Exception e){
            e.printStackTrace();
            name = "[姓名格式错误或附近有错误]";
        }

        result += num + " " + name;

        //获取文献出版信息部分
        try {
            bookname = split[1];
        }catch (Exception e){
            e.printStackTrace();
            bookname = "[缺少书名信息或附近有错误]";
        }

        result += "." + bookname + ".";

        //获取书名信息部分
        String str_publish = "";
        try {
            str_publish = split[2];
        }catch (Exception e){
            e.printStackTrace();
            result +=  "[缺少出版信息或附近有错误]";
        }

        //可根据中文或者英文分割
        String[] split_book = str_publish.split(",");
        if (split_book.length == 0)
            result +=  "[缺少出版地信息或附近有错误]";

        //出版地信息
        place = split_book[0].split(":")[0];
        if (place.equals(""))
            place = "[缺少出版地信息或附近有错误]";

        //出版社信息
        try {
            press = split_book[0].split(":")[1];
        }catch (Exception e){
            e.printStackTrace();
            press = "[缺少出版社信息或附近有错误]";
        }

        //出版年份
        try {
            time = split_book[1].split(":")[0];
        }catch (Exception e){
            e.printStackTrace();
            time = "[缺少出版年份或附近有错误]";
        }


        result += place + ":" + press + "," + time;

        //页码（可选）
        try {
            page = split_book[1].split(":")[1];
            result += ":" +  page;
        }catch (Exception e){
            e.printStackTrace();
            page = "";
        }
        result += ".";

        if (standard.equals("ACM")){
            result = num + name + "." + bookname + "." + place + ":" + press + "," + time;
        }

        return result;
    }
    public static String InitType_P_CN(String str, String result, String standard){
        String author = null;
        String name = null;
        String country = null;
        String number = null;
        String time = null;
        String path = null;

        String[] split = str.split(",");

        try {
            author = split[0].split(":")[0].split("\\.")[0];
        } catch (Exception e) {
            author = "[专利申请者或所有者处有错误]";
            e.printStackTrace();
        }
        try {
            name = split[0].split(":")[0].split("\\.")[1];
        } catch (Exception e) {
            name = "[专利题名处有错误]";
            e.printStackTrace();
        }
        try {
            country = split[0].split(":")[1];
        } catch (Exception e) {
            country = "[专利国别处有错误]";
            e.printStackTrace();
        }
        try {
            number = split[1].split("]")[0] + "]";
        } catch (Exception e) {
            number = "[专利号处有错误]";
            e.printStackTrace();
        }
        try {
            time = split[1].split("]")[1] + "]";
        } catch (Exception e) {
            time = ".[公告日期或引用日期处有错误]";
            e.printStackTrace();
        }
        try {
            path = split[1].split("]")[2];
        } catch (Exception e) {
            path = ".[获取和访问路径处有错误].";
            e.printStackTrace();
        }

        result += author + "." + name + ":" + country + "." + number + time + path;
        return result;
    }
    public static String InitType_S_CN(String str, String result, String standard){
        String code = null;
        String name = null;
        String split[] = str.split("\\.");

        try {
            if (split[0].contains("[")){
                name = split[0];
                code = split[1];
            } else {
                code = split[0];
                name = split[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
            result += "[标准代码或标准名称错误]";
        }
        result += code + "." + name;
        return result;
    }
    public static String InitType_D_EN(String str, String result, String standard){
        String time = null;
        String author = null;
        String place = null;
        String name = null;
        String split[] = str.split(":");

        try {
            author = split[0];
        } catch (Exception e) {
            e.printStackTrace();
            result += "[作者名错误]";
        }

        try {
            name = split[1].split("]")[0] + "]";
        } catch (Exception e) {
            e.printStackTrace();
            result += "[论文题目错误]";
        }

        try {
            place = split[1].split("]")[1].split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
            result += "[论文发表机构错误]";
        }

        try {
            time = split[1].split("]")[1].split(",")[1];
        } catch (Exception e) {
            e.printStackTrace();
            result += "[论文发表时间错误]";
        }

        result += author + ":" + name + place + "," + time;
        return result;
    }
    public static String InitType_R_EN(String str, String result, String standard){
        String time = null;
        String author = null;
        String place = null;
        String name = null;
        String split[] = str.split(":");

        try {
            author = split[0];
        } catch (Exception e) {
            e.printStackTrace();
            result += "[作者名错误]";
        }

        try {
            name = split[1].split("]")[0] + "]";
        } catch (Exception e) {
            e.printStackTrace();
            result += "[论文题目错误]";
        }

        try {
            place = split[1].split("]")[1].split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
            result += "[论文发表机构错误]";
        }

        try {
            time = split[1].split("]")[1].split(",")[1];
        } catch (Exception e) {
            e.printStackTrace();
            result += "[论文发表时间错误]";
        }

        result += author + ":" + name + place + "," + time;
        return result;
    }
    public static String InitType_P_EN(String str, String result, String standard){
        String time = null;
        String author = null;
        String place = null;
        String name = null;
        String split[] = str.split(":");

        try {
            author = split[0];
        } catch (Exception e) {
            e.printStackTrace();
            result += "[作者名错误]";
        }

        try {
            name = split[1].split("]")[0] + "]";
        } catch (Exception e) {
            e.printStackTrace();
            result += "[论文题目错误]";
        }

        try {
            place = split[1].split("]")[1].split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
            result += "[论文发表机构错误]";
        }

        try {
            time = split[1].split("]")[1].split(",")[1];
        } catch (Exception e) {
            e.printStackTrace();
            result += "[论文发表时间错误]";
        }

        result += author + ":" + name + place + "," + time;
        return result;
    }
    public static String InitType_S_EN(String str, String result, String standard){
        String code = null;
        String name = null;
        String split[] = str.split("\\.");

        try {
            if (split[0].contains("[")){
                name = split[0];
                code = split[1];
            } else {
                code = split[0];
                name = split[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
            result += "[标准代码或标准名称错误]";
        }
        result += code + "." + name;
        return result;
    }
    public static void main(String[] args) {
        Date date = new Date();
        String str = "" + date.getTime();
    }

}
