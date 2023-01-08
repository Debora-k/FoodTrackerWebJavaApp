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
public class ServingsComparator implements Comparator<FoodRecord> {

    public ServingsComparator() {
    }

    @Override
    public int compare(FoodRecord o1, FoodRecord o2) {
        return Integer.compare(o1.getServings(),o2.getServings());
    }
    
}
