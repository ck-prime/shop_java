/* Programming 1 S1 2023 A3 Final Assessment 30%
   Due date/time: 12/June 9:00 am, submit your zipped .java files on Canvas

   Student Name:  ????? ???? ????
   Student ID:    s??????? 
   Higest level attempted:      ??               (PA/CR/DI/HD)

   Java Grocery Shop must be compilable & runnable on command line 
*/


import java.io.*;
import java.util.*;

class Item {
    String name;
    double unitCost;
    double unitPrice;
    int stock;

    public Item(String name, double unitCost, double unitPrice, int stock) {
        this.name = name;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }
}

class Order {
    String customer;
    int[] orderedItems;
    final int capacity = 20;
    double fee = 9.99;

    public Order(String customer, int[] orderedItems) {
        this.customer = customer;
        this.orderedItems = orderedItems;
    }

    public void charge(double fee) {
        this.fee = fee;
    }
}

class InvalidItemException extends RuntimeException
{
    InvalidItemException()
    {
        super("Check items.txt, remove all invalid item values and restart the program");
    }
}

class InvalidOrderException extends RuntimeException
{
    InvalidOrderException()
    {
        super("Check the order file, fix errors of all orders and restart the program");
    }
}

class NotEnoughStockException extends RuntimeException
{
    NotEnoughStockException()
    {
        super("   ** Not Enough Stock **");
    }
}

public class A3_P1_2023 {
    public static void main(String[] args) {
        System.out.println("========= Welcome to Java Grocery ========");

        Item[] items = readItems();
        Order[] orders = readOrders();
        int[] ordered = new int[7];
        double allsum=0;
        for( int i=0; i<7; i++)
        {
            System.out.print(i+1 +" ");
            System.out.print(String.format("%1$10s", items[i].name));
            System.out.format("%7.2f",items[i].unitCost);
            System.out.format("%7.2f",items[i].unitPrice);
            System.out.format("%7d",items[i].stock);
            System.out.println();
        }
        System.out.println("============= " + orders.length + " Order(s) =============");
        for (Order order : orders) {
            //System.out.print(order.customer + ": ");
            System.out.println("# " + order.customer);
            int i=0;
            double sums=0;

            for (int item : order.orderedItems)
             {
               if (item!=0){
                
                System.out.print(i+1 + " ");
                System.out.print(String.format("%1$10s", items[i].name));
                System.out.print(" : ");
                System.out.format("%3d",item);
                System.out.format("%9.2f",items[i].unitPrice);
                try{
                if ( (items[i].stock- (item + ordered[i]))<0){
                    throw new NotEnoughStockException();
                }else{
                    System.out.format("%9.2f",item*items[i].unitPrice);
                    ordered[i]=ordered[i]+item;
                    sums=sums + (item*items[i].unitPrice);
                }
            }catch(NotEnoughStockException e){
                    System.out.println(e.getMessage());
                }
                
                System.out.println();
               }
               i++;
            }
            
            System.out.print("TOTAL"+"-------- ");
            if( order.customer.charAt(1)=='_')
            {
            if ( order.customer.charAt(0)=='P')
            {
            System.out.format("%5.2f",sums);
            System.out.print(" + 0.00 = ");
            System.out.format("%5.2f",sums);
            }else{
                if(sums > 50){
                sums = sums - ((sums-50)* 0.15);}
            System.out.format("%5.2f",sums);
            System.out.print(" + 5.00 = ");
            sums = sums+5.00;
            System.out.format("%5.2f",sums);
                }
            }
            else{
            System.out.format("%5.2f",sums);
            System.out.print(" + 9.99 = ");
            sums = sums+9.99;
            System.out.format("%5.2f",sums);
            }
            System.out.println(" --------");
            allsum=allsum+sums;
            System.out.println();
        }
        System.out.print("All orders together:   ");
        System.out.format("%5.2f",allsum);
        System.out.println();

        System.out.println("============= Updated Item List =============");
        for( int i=0; i<7; i++)
        {
            System.out.print(i+1 +" ");
            System.out.print(String.format("%1$10s", items[i].name));
            System.out.format("%7.2f",items[i].unitCost);
            System.out.format("%7.2f",items[i].unitPrice);
            System.out.format("%7d",items[i].stock - ordered[i]);
            System.out.println();
        }

    }

    public static Item[] readItems() {
        Item[] items = new Item[7];
        try {
            File file = new File("items.txt");
            Scanner scanner = new Scanner(file);

            int i = 0;
            while (scanner.hasNextLine() && i < 7) {
                String[] data = scanner.nextLine().split(" ");
                String name = data[0];
                int nsize = name.length();
                double unitCost = Double.parseDouble(data[1]);
                double unitPrice = Double.parseDouble(data[2]);
                int stock = Integer.parseInt(data[3]);
                    try {
                        if (nsize>10 || unitCost <0 || unitPrice<0 || stock<0 || unitCost>1000 || unitPrice>1000|| stock>1000)
                      {
                        throw new InvalidItemException();
                      } }catch (InvalidItemException e) {
                     System.out.println(e.getMessage());
                    System.exit(0);
                 }
                items[i] = new Item(name, unitCost, unitPrice, stock);
                i++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static Order[] readOrders() {
        Order[] orders = null;
        try {
            File file = new File("orders2.txt");
            Scanner scanner = new Scanner(file);

            int totalOrders = Integer.parseInt(scanner.nextLine());
            orders = new Order[totalOrders];

            int i = 0;
            while (scanner.hasNextLine() && i < totalOrders) {
                String[] data = scanner.nextLine().split(" ");
                String customer = data[0];
                int[] orderedItems = new int[7];
                int orderdid = 0;
                for (int j = 0; j < 7; j++) {
                    orderedItems[j] = Integer.parseInt(data[j + 1]);
                    orderdid+= orderedItems[j];
                }
                try{
                if ( orderdid>20){
                    throw new InvalidOrderException();
                }}catch(InvalidOrderException e){
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
                orders[i] = new Order(customer, orderedItems);
                i++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return orders;
    }
}