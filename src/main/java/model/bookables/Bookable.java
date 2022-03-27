package model.bookables;

import java.util.List;

public abstract class Bookable implements Comparable<Bookable> {
    protected String id;
    protected int row;
    protected String col;
    protected double price;
    protected List<String> amenities;

    /**
     * Constructor for a bookable, with a row and a col
     * @param row: int for the row it is in (would be plane row or hotel floor)
     * @param col: string for the colum (would be the plane column or hotel roomnumber without the floor)
     * column is a string so that the plane can have seats have letters
     */
    public Bookable(int row, String col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets bookable's ID
     * @return UUID
     */
    protected String getId() {
        return this.id;
    }

    /**
     * Gets the row number
     * @return integer of the row
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Gets the col
     * @return string for the column
     */
    public String getCol(){
        return this.col;
    }

    /**
     * Gets the price
     * @return double for the price
     */
    public double getPrice () {
        return this.price;
    }

    /**
     * Method to compare a bookable to another
     * @return 0 if the compared bookable is null or the same as this bookable
     * returns less than 0 if this bookable has a lower row, or a lower column if the rows are the same
     * returns greater than 0 if this bookable has a higher row, or a higher column if the rows are the same
     */
    public int compareTo(Bookable other){
        if(other==null)
            return 0;
        if(this.row!=other.getRow())
            return this.row-other.getRow();
        return this.col.compareTo(other.getCol());
    }

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", row:" + row +
                ", col:'" + col + '\'' +
                ", price:" + price +
                ", amenities:" + amenities +
                '}';
    }
}