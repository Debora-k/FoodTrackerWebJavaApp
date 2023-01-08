/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets;

import java.util.Comparator;
import models.FoodRecord;

/**
 *
 * @author debor
 */
public class DateComparator implements Comparator<FoodRecord> {

    public DateComparator() {
    }

    @Override
    public int compare(FoodRecord o1, FoodRecord o2) {
        return o1.getDate().compareTo(o2.getDate());

    }
    
}
