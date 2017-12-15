package br.com.ifsolutions.controller;

import br.com.ifsolutions.entity.Produtos;
import br.com.ifsolutions.entity.Venda;
import br.com.ifsolutions.model.TagModel;
import br.com.ifsolutions.view.TagAvulso;
import br.com.ifsolutions.view.TagMovendaView;
import br.com.ifsolutions.view.TagView;

import java.util.List;

public class TagController {
    public void renderTagMenu() {
        TagView tagView = new TagView();
        tagView.renderMenu();
    }

    public void listarAvulso() {

        List<Produtos> products;

        //carregar os produtos
        TagModel tagModel = new TagModel();
        products = tagModel.loadProducts();

        //TagView tagView = new TagView();
        //tagView.renderTagAvulso(products);

        TagAvulso tagAvulso = new TagAvulso();
        tagAvulso.renderView(products);
    }

    public void listarVendas() {
        List<Venda> vendas;

        //carregar as vendas
        TagModel tagModel = new TagModel();
        vendas = tagModel.loadVendas();

        TagMovendaView tagMovendaView = new TagMovendaView();
        tagMovendaView.renderView(vendas);
    }

    public void listarFardo() {
    }
}
