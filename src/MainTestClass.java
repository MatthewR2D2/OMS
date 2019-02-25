
/*
*This is the main tester class for the macthing engine
 */
import HelperClasses.PrettyPrinterUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainTestClass {

    public static void main(String[] args) {

        //Help with printing
        PrettyPrinterUtil ppu = new PrettyPrinterUtil();

        //Sample trade objects
        TradeObject buy = new TradeObject(
                1,
                1000000,
                0,
                1.02,
                2.01,
                1234,
                2,
                0,
                0,
                0);

        TradeObject ob1 = new TradeObject(
                2,
                200,
                1.01,
                1.2,
                0.01,
                1234,
                2,
                1,
                0,
                0);
        TradeObject ob2 = new TradeObject(
                2,
                200,
                1.31,
                1.2,
                0.01,
                1234,
                2,
                1,
                0,
                0);
        TradeObject ob3 = new TradeObject(
                3,
                200,
                100.01,
                1.2,
                0.01,
                1234,
                2,
                1,
                0,
                0);

        TradeObject ob4 = new TradeObject(
                3,
                200,
                0.95,
                1.2,
                0.01,
                1234,
                2,
                1,
                0,
                0);

        //Sample trade objects
        TradeObject sell = new TradeObject(
                9,
                1000000,
                0,
                1.02,
                2.01,
                1234,
                2,
                1,
                0,
                0);

        TradeObject b1 = new TradeObject(
                2,
                200,
                1.01,
                1.2,
                0.01,
                1234,
                2,
                0,
                0,
                0);
        TradeObject b2 = new TradeObject(
                2,
                200,
                1.31,
                1.2,
                0.01,
                1234,
                2,
                0,
                0,
                0);
        TradeObject b3 = new TradeObject(
                3,
                200,
                100.01,
                1.2,
                0.01,
                1234,
                2,
                0,
                0,
                0);

        TradeObject b4 = new TradeObject(
                3,
                200,
                0.95,
                1.2,
                0.01,
                1234,
                2,
                0,
                0,
                0);


        List<TradeObject> sellOrderBook = new ArrayList<>();
        sellOrderBook.add(ob1);
        sellOrderBook.add(ob2);
        sellOrderBook.add(ob3);
        sellOrderBook.add(ob4);

        List<TradeObject> buyOrderBook = new ArrayList<>();
        sellOrderBook.add(b1);
        sellOrderBook.add(b2);
        sellOrderBook.add(b3);
        sellOrderBook.add(b4);


        MatchingEngine me = new MatchingEngine();
        String result = me.orderMatch(buy, buyOrderBook, sellOrderBook);
        ppu.print(result);


    }

}
