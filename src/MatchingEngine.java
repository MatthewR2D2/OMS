/*
* @Description This is the matching engine which matches a sell side to a buy side order
* @params to - this is the actual submitted order from a user
* @params buyBook - This is the buy side order book
* @params sellBook - This is the sell side order book
*
* @returns this returns a string of the results
* TODO: return needs to change to be more meaninful
*
* What is supported:
* Market Orders Buy + Sell
* Limit Orders  Buy + Sell
*
* TIME In Force
* Support:
* Not Supported: GTC (Good Til Cancel), (IOC) Immediate Fill or Cancel, (FOK)Fill or Kill,(GTD) Good for Day
*
* */

import TradePackages.TradeObject;

import java.util.List;

/**+
 * Matching engine
 */
public class MatchingEngine {

    // Constructor Needs no optiion
    public MatchingEngine(){}

    /**+
     *This is the main method of the matching engine
     * @param to  Trade
     * @param buyBook buy side orderbook
     * @param sellBook sell side orderbook
     * @return returns a string of the results
     */
    public String orderMatch(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook )
    {
		System.out.println("Hello Class");
        String result = "";
        //Check what type of order it is
        // [0 : New, 1: Amend, 2 :Cancel]
        if(to.getOrderType() == 2) //Cancel
        {
            result = "Canceling order";
            //Send cancel request to the DB
            // and remove exsiting orderr from order book
            if(to.getSide() == 0) //Buy side
            {
                result = cancelOrder(to, buyBook);

            }else
            {
                result = cancelOrder(to, sellBook);
            }
        }else if(to.getOrderType() == 1) //Amend Order
        {
            if(to.getSide() == 0) //Buy Side
            {
                result = amendOrder(to, buyBook);
            }else
            {
                result = amendOrder(to, sellBook);
            }

        }else if (to.getOrderType() == 0) //New Order
        {
            //Find side then send to the right matching method
            if(validateOrder(to))
            {
                result = "New Order and valid";
                //Check to see what side the order is one
                //0 = buy
                //1 = Sell
                if(to.getSide() == 0){ buyOrderMatching(to, buyBook, sellBook);}
                else { sellOrderMatching(to, buyBook, sellBook); }

            }else{result = "New Order but not valid and rejected";}
        }
        else {result = "Unknow Order Type";}



        return result;
    }

    /**+
     *This is a validator for each order. It must come through this before being worked on
     * @param to trade
     * @return returns boolean if it a valid trade or not
     */
    private Boolean validateOrder(TradeObject to)
    {
        boolean valid = false;
        float baseMinAmount = (float) 0.001;
        float assetMinAmount = (float) 0.01;
        if (to.getQuantity() > 0 &&
            to.getBase() >= baseMinAmount &&
            to.getasset() >= assetMinAmount &&
            to.getPrice() >= 0)
        {
            // pass basic check
            valid = true;

        }else {valid = false;} //Return flase if it fails and is not valid order
        return valid;
    }

    /**+
     *This is for matching a sell order for both limit and market orders
     * @param to Trade
     * @param buyBook buy side order book
     * @param sellBook sell side order book
     */
    private void sellOrderMatching(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
    {
        System.out.println("Sell Order");
        //Check to see if there is a limit price or not
        //If 0 then its a market order
        if(to.getPrice() > 0.00)
        {
            //Limit Order
            //Find the closest price to the order
            int index = findSellPriceLimitOrder(buyBook, to.getPrice());
            // Fill the order for the found order book order
            fillOrder(to, buyBook, sellBook, index);

        }else
        {
            // Market order
            //Loop through buy side orderbook
            //Assuming that it is sorted by price
            // First price should be best price
            System.out.println(buyBook.size());
            fillSellMarketOrder(to, buyBook, sellBook); //Start out at 0 or the first and lowest price best price
            if (to.getQuantity() == 0)
            {
                System.out.println("Order Fully filled");
            }
        }
    }

    /**+
     *This is for matching a buy order for both limit and market orders
     * @param to  Trade
     * @param buyBook buyside orderbook
     * @param sellBook sell side orderbook
     */
    private void buyOrderMatching(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
    {
        System.out.println("Buy Order");
        //Check to see if there is a limit price or not
        //If 0 then its a market order
        if(to.getPrice() > 0.00)
        {
            //Limit Order
            //Find the closest price to the order
            int index = findBuyPriceLimitOrder(sellBook, to.getPrice());
            //System.out.println("Order Number: " + sellBook.get(index).getOrderId());
            // Fill the order for the found order book order
            fillOrder(to, sellBook, buyBook, index);
        }else
        {
            // Market order
            //Loop through sell side orderbook
            //Assuming that it is sorted by price
            // First price should be best/lowest price
            System.out.println(sellBook.size());
            fillBuyMarketOrder(to, buyBook, sellBook); //Start out at 0 or the first and lowest price best price
            if (to.getQuantity() == 0)
            {
                System.out.println("Order Fully filled");
            }
        }

    }

    /*
    * This method will fill all limit orders for both sides
    * */

    /**+
     * This if for filling orders and removal of fully filled orders from the order book. It also adds a partially filled
     * order to the same side book
     * @param to The trade
     * @param orderBook the opposite side order book
     * @param sameSideBook the same side orderbook
     * @param index the index of the orderbook of the matching order
     */
    private void fillOrder(TradeObject to, List<TradeObject> orderBook, List<TradeObject> sameSideBook, int index)
    {
        // If no order was found then add it to the order book from here
        if(index == -1)
        {
            System.out.println("Order is not filled and will be sent to the order book");
            sameSideBook.add(to);
        }
        else if(to.getQuantity() > orderBook.get(index).getQuantity())
        {
            System.out.println("Best price match ID: " + orderBook.get(index).getOrderId() + " Side: " + orderBook.get(index).getSide() + " Price:" + orderBook.get(index).getPrice());
            //Partial Fill set the new quantitiy for the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(to.getQuantity() - orderBook.get(index).getQuantity());
            //Remove Orderbook from array
            orderBook.remove(index);
            //Get the next order
            if(to.getQuantity() > 0 && !orderBook.isEmpty())
            {
                if (to.getSide() == 0) //Buy side
                {
                    int newIndex = findBuyPriceLimitOrder(orderBook, to.getPrice());
                    System.out.println("Order not fully filled Getting next order");
                    fillOrder(to, orderBook, sameSideBook, newIndex); //Recursive call back to start
                }else
                    {
                        int newIndex =findSellPriceLimitOrder(orderBook, to.getPrice());
                        System.out.println("Order not fully filled Getting next order");
                        fillOrder(to, orderBook, sameSideBook, newIndex); //Recursive call back to start

                    }


            }else
            {
                //Add the unfill order to the order book.
                System.out.println("Order is not filled and will be sent to the order book");
                sameSideBook.add(to);
            }

        }else if(to.getQuantity() == orderBook.get(index).getQuantity())
        {
            //Fill the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(0);
            //remove order from order book
            orderBook.remove(index);
            System.out.println("Order Fully filled");
        }else  //Orderbook has more quantity than the order
        {
            //TODO: Make a fill method to actually fill the order
            //remove volume from the orderbook
            orderBook.get(index).setQuantity(orderBook.get(index).getQuantity() - to.getQuantity());
            to.setQuantity(0);
            System.out.println("Order Fully filled Other order being updated");
        }

    }

    // These will fill solong as there is a opposite order

    /**+
     *This is for filing a market order for the buy side
     * @param to The trade
     * @param buyBook buy side orderbook
     * @param sellBook sell side orderbook
     */
    private void fillBuyMarketOrder(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
    {
        System.out.println("Starting to Fill order");
        System.out.println("Index:" + 0 + "Size:" + sellBook.size());
        if(to.getQuantity() > sellBook.get(0).getQuantity())
        {
            //Partial Fill set the new quantitiy for the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(to.getQuantity() - sellBook.get(0).getQuantity());
            //Remove Orderbook from array
            sellBook.remove(0);
            //Get the next order
            if(to.getQuantity() > 0 && !sellBook.isEmpty())
            {
                fillBuyMarketOrder(to, buyBook, sellBook); //Recursive call back to start
            }else
                {
                    //Add the unfill order to the order book.
                    System.out.println("Order is not filled ans will be sent to the order book");
                    buyBook.add(to);
                }

        }else if(to.getQuantity() == sellBook.get(0).getQuantity())
        {
            //Fill the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(0);
            //remove order from order book
            sellBook.remove(0);
        }else  //Orderbook has more quantity than the order
        {
            //TODO: Make a fill method to actually fill the order
            //remove volume from the orderbook
            sellBook.get(0).setQuantity(sellBook.get(0).getQuantity() - to.getQuantity());
            to.setQuantity(0);
        }
    }



    /**+
     *This is for filling the sell side market orders
     * @param to The order
     * @param buyBook The buy side orderbook
     * @param sellBook the sell side orderbook
     */
    private void fillSellMarketOrder(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
    {
        System.out.println("Starting to Fill order");
        System.out.println("Index:" + 0 + "Size:" + buyBook.size());
        if(to.getQuantity() > buyBook.get(0).getQuantity())
        {
            //Partial Fill set the new quantitiy for the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(to.getQuantity() - buyBook.get(0).getQuantity());
            //Remove Orderbook from array
            buyBook.remove(0);
            //Get the next order
            if(to.getQuantity() > 0 && !buyBook.isEmpty())
            {
                fillSellMarketOrder(to, buyBook, sellBook); //Recursive call back to start
            }else
            {
                //Add the unfill order to the order book.
                System.out.println("Order is not filled ans will be sent to the order book");
                sellBook.add(to);
            }

        }else if(to.getQuantity() == buyBook.get(0).getQuantity())
        {
            //Fill the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(0);
            //remove order from order book
            buyBook.remove(0);
        }else  //Orderbook has more quantity than the order
        {
            //TODO: Make a fill method to actually fill the order
            //remove volume from the orderbook
            buyBook.get(0).setQuantity(buyBook.get(0).getQuantity() - to.getQuantity());
            to.setQuantity(0);
        }
    }





    /**+
     *
     * @param to This is the trade object
     * @param orderBook The order book for the same side as the trade
     * @return String that is the results of canceling
     */
    private String cancelOrder(TradeObject to, List<TradeObject> orderBook)
    {
        String canResult = "";
        for(int i = 0; i < orderBook.size(); i++)
        {
            if(to.getOrderId() == orderBook.get(i).getOrderId())
            {
                canResult = "Order: " + orderBook.get(i).getOrderId() + " is being canceled";
                // Remove and cancel the order based on ID
                orderBook.remove(i);

            }else
            {
                canResult = "Cannot Find Order please check you order ID and retry";
            }
        }
        return canResult;
    }

    /**+
     *
     * @param to This is the trade object
     * @param orderBook The order book for the same side as the trade
     * @return String that is the results of canceling
     */
    private String amendOrder(TradeObject to, List<TradeObject> orderBook)
    {
        String amendResult = "";
        for(int i = 0; i < orderBook.size(); i++)
        {
            if(to.getOrderId() == orderBook.get(i).getOrderId())
            {
                amendResult = "Order: " + orderBook.get(i).getOrderId() + " is being amended";
                // Amaned to order and cancel the order based on ID
                orderBook.set(i, to);

            }else
            {
                amendResult = "Cannot Find Order please check you order ID and retry";
            }
        }
        return amendResult;
    }

    /**+
     * This find the best price that is less than or equal to the buy price
     * @param sellBook  The sell order book
     * @param price  The limit price
     * @return This returns the index of the found order from the order book
     */
    private int findBuyPriceLimitOrder(List<TradeObject> sellBook, double price )
    {
        int currentBestPriceIndex = -1;
        for (TradeObject trade: sellBook) {
            if(price >= trade.getPrice())
            {
                //Found the trade that matches then write it to the index
                currentBestPriceIndex = sellBook.indexOf(trade);
            }else {break;} // if no match less than or equal to
        }

        return currentBestPriceIndex;
    }

    /**+
     * Find the best price that is greater than or equal to the limit price
     * @param buyBook Buy side order book
     * @param price limit price from order
     * @return returns the index that is the best price closest to the price
     */
    private int findSellPriceLimitOrder(List<TradeObject> buyBook, double price )
    {
        int currentBestPriceIndex = -1;
        for (TradeObject trade: buyBook) {
            if(price <= trade.getPrice())
            {
                //Found the trade that matches then write it to the index
                currentBestPriceIndex = buyBook.indexOf(trade);
            }else {break;} // if no match less than or equal to
        }

        return currentBestPriceIndex;
    }


}
