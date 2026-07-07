package org.example.inventorydivergencetracker;

import javax.swing.*;

public class CreateItemService {

    Item item = new Item();

    public void CreateCodeItem() {
        String prefixoCodigo = "BRA_";
        String codigoItem = JOptionPane.showInputDialog("Digite o codigo do Item: ");

        if (!codigoItem.isEmpty() && codigoItem.matches("^[a-zA-Z0-9]+$")){
            String codigoCompleto = prefixoCodigo + codigoItem;
            item.setCodigoItem(codigoCompleto);
        } else  {
            JOptionPane.showMessageDialog(null, "código invalido , o código não permite simbolos e valores em branco");
        }


    }
}
