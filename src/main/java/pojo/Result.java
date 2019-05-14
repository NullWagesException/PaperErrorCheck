package pojo;

public class Result {

    private String ind;//缩进
    private String font;//字体
    private String color;//颜色
    private String sz;//大小
    private String content;//文字内容
    private String jc;//文字位置
    private String spacing;
    private boolean isbookmark;

    public boolean isIsbookmark() {
        return isbookmark;
    }

    public void setIsbookmark(boolean isbookmark) {
        this.isbookmark = isbookmark;
    }

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    public String getSpacing() {
        return spacing;
    }

    public void setSpacing(String spacing) {
        this.spacing = spacing;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof Result) {
            Result result = (Result) obj;

            // 比较每个属性的值 一致时才返回true
            if (result.ind.equals(this.ind) && result.font.equals(this.font)
                    && result.color.equals(this.color) && result.sz.equals(this.sz)
                    && result.content.equals(this.content) && result.jc.equals(this.jc)
                    && result.spacing.equals(this.spacing))
                return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "Result{" +
                "ind='" + ind + '\'' +
                ", font='" + font + '\'' +
                ", color='" + color + '\'' +
                ", sz='" + sz + '\'' +
                ", content='" + content + '\'' +
                ", jc='" + jc + '\'' +
                ", spacing='" + spacing + '\'' +
                '}';
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
