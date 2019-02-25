package TradePackages;

import java.util.Comparator;

public class TradeObject{

    //Variables
    float id;           //trade id
    float quantity;     //number of sharse to buy
    double price;       //price to buy at (Limit order)
    double asset;        //Coin or stock to buy
    double base;         //The base coin that user has
    float userID;       //The ID of the client
    int excahngeID;     //ID number of the exchange
    int side;           //Buy/Sell 0:Buy 1:Sell
    int orderType;      //Type of order new, amend, cancel [0 : New, 1: Amend, 2 :Cancel]
    int timeInForce;    //0:GTC(Good Til Cancel),1:(IOC)Immediate Fill or Cancel,2:(FOK)Fill or Kill,3:(GTD)Good for Day

    //Contructor Needs all variables at start no default constructor
    public TradeObject(float id,
                       float quantity,
                       double price,
                       double asset,
                       double base,
                       float userID,
                       int excahngeID,
                       int side,
                       int orderType,
                       int timeInForce)
    {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.asset = asset;
        this.base = base;
        this.userID = userID;
        this.excahngeID = excahngeID;
        this.side = side;
        this.orderType = orderType;
        this.timeInForce = timeInForce;
    }

    /*
    Getter and setter methods
     */
    public float getId(){return this.id; }
    public void setId(float id){this.id = id;}

    public float getQuantity(){return this.quantity;}
    public void setQuantity(float quantity){this.quantity = quantity;}

    public double getPrice(){return this.price;}
    public void setPrice(double price) {this.price = price; }

    public double getasset(){return this.asset;}
    public void setasset(float asset) {this.asset = asset;}

    public void setBase(float base) {this.base = base;}
    public double getBase() { return this.base;}

    public float getUserId(){return this.userID; }
    public void setUserId(float userId){this.userID = userId;}

    public int getExcahngeID(){return this.excahngeID;}
    public void setExcahngeID(int excahngeID){this.excahngeID = excahngeID;}

    public int getSide() { return this.side; }
    public void setSide(int side) {this.side = side;}

    public int getOrderType(){return this.orderType;}
    public void setOrderType(int orderType){this.orderType = orderType;}

    public int getTimeInForce(){return this.timeInForce;}
    public void setTimeInForce(int timeInForce){this.timeInForce = timeInForce;}



} // End of class

