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

        if (!codigoItem.isBlank() && codigoItem.matches("^[a-zA-Z0-9]+$")) {
            String codigoCompleto = prefixoCodigo + codigoItem;
            item.setCodigoItem(codigoCompleto);
            System.out.println("novo código gerado: "+item.getCodigoItem());
        } else {
            System.out.println("tente novamente o código não aceita simbolos ,e valores em branco");
        }



        Assertions.assertEquals("BRA_ENFERMARIA",item.getCodigoItem());
    }
}
