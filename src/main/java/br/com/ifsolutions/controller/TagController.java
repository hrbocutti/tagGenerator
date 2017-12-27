package br.com.ifsolutions.controller;

import br.com.ifsolutions.view.*;

public class TagController {
    public void renderTagMenu() {
        TagView tagView = new TagView();
        tagView.renderMenu();
    }

    public void avulso() { new TagAvulsoView(); }

    public void vendas() { new TagMovendaView(); }

    public void fardo() { new ClientView(); }
}