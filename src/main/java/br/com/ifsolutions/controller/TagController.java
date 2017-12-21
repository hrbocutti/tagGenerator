package br.com.ifsolutions.controller;

import br.com.ifsolutions.view.TagAvulsoView;
import br.com.ifsolutions.view.TagFardoView;
import br.com.ifsolutions.view.TagMovendaView;
import br.com.ifsolutions.view.TagView;

public class TagController {
    public void renderTagMenu() {
        TagView tagView = new TagView();
        tagView.renderMenu();
    }

    public void avulso() { new TagAvulsoView(); }

    public void vendas() { new TagMovendaView(); }

    public void fardo() { new TagFardoView(); }
}