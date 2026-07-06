package org.example.inventorydivergencetracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateItemTest {

    @Test
    void criacaoDoCodigoDoMaterial(){
        Item item = new Item();
        String prefixoCodigo = "BRA_";
        String codigoItem = "ENFERMARIA";

        while(!codigoItem.isBlank()) {
            String codigoCompleto = prefixoCodigo + codigoItem;
            item.setCodigoItem(codigoCompleto);
            System.out.println(codigoCompleto);
        }

        Assertions.assertEquals("BRA_ENFERMARIA",item.getCodigoItem);
    }
}
