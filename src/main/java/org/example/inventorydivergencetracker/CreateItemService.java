package org.example.inventorydivergencetracker;

import javax.swing.*;

public class CreateItemService {

    StockItem stockItem = new StockItem();

    public void CreateCodeItem() {
        String prefixCode = "BRA_";
        String codeItem = JOptionPane.showInputDialog("Digite o codigo do Item: ");

        if (!codeItem.isEmpty() && codeItem.matches("^[a-zA-Z0-9]+$")){
            String codigoCompleto = prefixCode + codeItem;
            stockItem.setCodigoItem(codigoCompleto);
        } else  {
            JOptionPane.showMessageDialog(null, "código invalido , o código não permite simbolos e valores em branco");
        }


    }
}
