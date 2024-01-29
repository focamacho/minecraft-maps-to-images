import java.util.HashMap;
import java.util.Map;

public class BasicColor {

    static Map<Integer, BasicColor> colors;
    static {
        colors = new HashMap<>();
        colors.put(0, new BasicColor(0));
        colors.put(1, new BasicColor(8368696));
        colors.put(2, new BasicColor(16247203));
        colors.put(3, new BasicColor(13092807));
        colors.put(4, new BasicColor(16711680));
        colors.put(5, new BasicColor(10526975));
        colors.put(6, new BasicColor(10987431));
        colors.put(7, new BasicColor(31744));
        colors.put(8, new BasicColor(16777215));
        colors.put(9, new BasicColor(10791096));
        colors.put(10, new BasicColor(9923917));
        colors.put(11, new BasicColor(7368816));
        colors.put(12, new BasicColor(4210943));
        colors.put(13, new BasicColor(9402184));
        colors.put(14, new BasicColor(16776437));
        colors.put(15, new BasicColor(14188339));
        colors.put(16, new BasicColor(11685080));
        colors.put(17, new BasicColor(6724056));
        colors.put(18, new BasicColor(15066419));
        colors.put(19, new BasicColor(8375321));
        colors.put(20, new BasicColor(15892389));
        colors.put(21, new BasicColor(5000268));
        colors.put(22, new BasicColor(10066329));
        colors.put(23, new BasicColor(5013401));
        colors.put(24, new BasicColor(8339378));
        colors.put(25, new BasicColor(3361970));
        colors.put(26, new BasicColor(6704179));
        colors.put(27, new BasicColor(6717235));
        colors.put(28, new BasicColor(10040115));
        colors.put(29, new BasicColor(1644825));
        colors.put(30, new BasicColor(16445005));
        colors.put(31, new BasicColor(6085589));
        colors.put(32, new BasicColor(4882687));
        colors.put(33, new BasicColor(55610));
        colors.put(34, new BasicColor(8476209));
        colors.put(35, new BasicColor(7340544));
        colors.put(36, new BasicColor(13742497));
        colors.put(37, new BasicColor(10441252));
        colors.put(38, new BasicColor(9787244));
        colors.put(39, new BasicColor(7367818));
        colors.put(40, new BasicColor(12223780));
        colors.put(41, new BasicColor(6780213));
        colors.put(42, new BasicColor(10505550));
        colors.put(43, new BasicColor(3746083));
        colors.put(44, new BasicColor(8874850));
        colors.put(45, new BasicColor(5725276));
        colors.put(46, new BasicColor(8014168));
        colors.put(47, new BasicColor(4996700));
        colors.put(48, new BasicColor(4993571));
        colors.put(49, new BasicColor(5001770));
        colors.put(50, new BasicColor(9321518));
        colors.put(51, new BasicColor(2430480));
    }

    public final int r, g, b;
    public final int colorValue;

    public BasicColor(int color) {
        this.r = Util.getR(color);
        this.g = Util.getG(color);
        this.b = Util.getB(color);
        this.colorValue = color;
    }

    public int shaded(int index) {
        int i = 220;

        switch (index) {
            case 3:
                i = 135;
                break;
            case 2:
                i = 255;
                break;
            case 0:
                i = 180;
        }

        int j = this.r * i / 255;
        int k = this.g * i / 255;
        int l = this.b * i / 255;
        return -16777216 | j << 16 | k << 8 | l;
    }


}
