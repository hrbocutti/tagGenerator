package br.com.ifsolutions.model;

import br.com.ifsolutions.dao.ProductDao;
import br.com.ifsolutions.entity.Produtos;

import java.util.List;

public class TagModel {
    public List<Produtos> loadProducts() {
        ProductDao productDao = new ProductDao();
        return productDao.findAll();
    }
}
