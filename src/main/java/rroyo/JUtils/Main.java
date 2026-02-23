package rroyo.JUtils;

import rroyo.JUtils.Utils.CustomFonts;
import rroyo.JUtils.Utils.Information;

import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static void main(String[] args) {

        CustomFonts.addFont("BGD", "src/main/java/rroyo/JUtils/Tests/BitcountGridDouble.ttf", 30f);
        CustomFonts.addFont("GSC", "src/main/java/rroyo/JUtils/Tests/GoogleSansCode.ttf", 14f);

        new Information(
                "src/main/java/rroyo/JUtils/Tests/example.data",
                "title-ttt", "content-ttt",
                CustomFonts.getFont("BGD"), CustomFonts.getFont("GSC"),
                Color.cyan, Color.black
        );
        new Information(
                "src/main/java/rroyo/JUtils/Tests/example.data",
                "title-sttt", "content-sttt",
                CustomFonts.getFont("BGD"), CustomFonts.getFont("GSC"),
                Color.cyan, Color.black
        );

    }

}
