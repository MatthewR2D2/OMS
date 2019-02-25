
/*
*This is the main tester class for the macthing engine
 */
import HelperClasses.PrettyPrinterUtil;
import TradePackages.TradeObject;

import java.util.ArrayList;
import java.util.List;

/**+
 * Main Method
 */
public class MainTestClass {

    public static void main(String[] args) {

        //Help with printing
        PrettyPrinterUtil ppu = new PrettyPrinterUtil();

        //Sample trade objects
        TradeObject buy = new TradeObject(
                6,
                500,
                1.50,
                1.02,
                2.01,
                1234,
                2,
                0,
                0,
                0);

        TradeObject ob4 = new TradeObject(
                5,
                200,
                0.95,
                1.2,
                0.01,
                1234,
                2,
                1,
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
                4,
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
                2.95,
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
                1.00,
                1.02,
                2.01,
                1234,
                2,
                1,
                0,
                0);

        TradeObject b1 = new TradeObject(
                8,
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
                7,
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
                6,
                200,
                100.01,
                1.2,
                0.01,
                1234,
                2,
                0,
                0,
                0);




        List<TradeObject> sellOrderBook = new ArrayList<>();
        sellOrderBook.add(ob4);
        sellOrderBook.add(ob1);
        sellOrderBook.add(ob2);
        sellOrderBook.add(ob3);


        List<TradeObject> buyOrderBook = new ArrayList<>();
        buyOrderBook.add(b1);
        buyOrderBook.add(b2);
        buyOrderBook.add(b3);





        MatchingEngine me = new MatchingEngine();
        //String result = me.orderMatch(buy, buyOrderBook, sellOrderBook);
        String result = me.orderMatch(sell, buyOrderBook, sellOrderBook);
        ppu.print(result);


    }

}
