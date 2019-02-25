package HelperClasses;

import TradePackages.TradeObject;

import java.util.List;

public class BinarySearch {


    /**
     * +
     * This method find for best price for each order for limit order only not market order!
     *
     * @param orderBook - The other side order book
     * @param target    The submitted order price (Limit order price)
     * @return this should return a index of the array where the matching order is located
     */
    private int findClosestNumber(TradeObject to, List<TradeObject> orderBook, double target) {
        int n = orderBook.size();

        //Corner cases
        if (target <= orderBook.get(0).getPrice()) return 0;
        if (target >= orderBook.get(n - 1).getPrice()) return n - 1;

        int i = 0, j = n, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;
            // Check to see if target is the mid value
            if (orderBook.get(mid).getPrice() == target) {
                return mid;
            }

            //Check to see if target is less than mid then serach left
            if (target < orderBook.get(mid).getPrice()) {
                // Get the closest of the two values of the target
                if (mid > 0 && target > orderBook.get(mid - 1).getPrice()) {
                    //Check mid -1 and mid
                    return getClosest(to, orderBook,
                            mid,
                            target);
                }
                // Repeat for 2nd half of the array
                j = mid;
            } else // If target is greater than mid
            {
                if (mid < n - 1 && target < orderBook.get(mid + 1).getPrice()) {
                    return getClosest(to, orderBook,
                            mid,
                            target);
                }
                i = mid + 1; //update i
            }
        }
        return mid;
    }

    /**
     * +
     *
     * @param orderBook - The orderbook for the opposite side of the order
     * @param mid       The mid point that is used to find the middle
     * @param target    The target value (Price)
     * @return this returns the index of the best possible price
     */
    private int getClosest(TradeObject to, List<TradeObject> orderBook, int mid, double target) {

        if (to.getSide() == 0) // Buy side
        {
            //Find LimitPrice <= Price
            if (target - orderBook.get(mid - 1).getPrice() >= orderBook.get(mid).getPrice() - target) {
                return orderBook.indexOf(orderBook.get(mid - 1));
            } else {
                return orderBook.indexOf(orderBook.get(mid));
            }
        } else {
            if (target - orderBook.get(mid + 1).getPrice() >= orderBook.get(mid).getPrice() - target) {
                return orderBook.indexOf(orderBook.get(mid - 1));
            } else {
                return orderBook.indexOf(orderBook.get(mid));
            }
        }


    }

    {
    }
}
