package org.example.inventorydivergencetracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateStockItemTest {

    @Test
    void createCodeItem(){
        StockItem stockItem = new StockItem();
        String prefixCodigo = "BRA_";
        String codeItem = "ENFERMARIA";

        if (!codeItem.isBlank() && codeItem.matches("^[a-zA-Z0-9]+$")) {
            String codigoCompleto = prefixCodigo + codeItem;
            stockItem.setCodigoItem(codigoCompleto);
        } else {
            System.out.println("tente novamente o código não aceita simbolos ,e valores em branco");
        }

        Assertions.assertEquals("BRA_ENFERMARIA", stockItem.getCodigoItem());
    }
    @Test
    void createNameItem(){
        StockItem stockItem = new StockItem();
        String nameItem = "KIT ENFERMARIA";

        if (!nameItem.isEmpty() && nameItem.matches("^[A-Z0-9_ ]+$")) {
            stockItem.setNome(nameItem);
        } else {
            System.out.println("Nome invalido , não é permitido simbolos, e letras minusculas");
        }
        Assertions.assertEquals("KIT ENFERMARIA", stockItem.getNome());
    }
    @Test
    void validateDescription(){
        StockItem stockItem = new StockItem();
        String descriptionItem = "esse item contem 10 porcas";

        if(!descriptionItem.isBlank() && descriptionItem.matches("^[a-zA-Z0-9_ ]+$")) {
            stockItem.setDescricao(descriptionItem);
        } else {
            System.err.println("Na descrição não é permitido simbolos e valores em branco");
        }
        Assertions.assertEquals("esse item contem 10 porcas", stockItem.getDescricao());
    }
}
