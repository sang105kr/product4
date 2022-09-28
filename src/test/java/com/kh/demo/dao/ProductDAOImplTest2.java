package com.kh.demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

/**
 * 테스트 시나리오 적용
 */
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDAOImplTest2 {

  @Autowired
  private ProductDAO productDAO;
  private static Long productId;

  @Test
  @DisplayName("상품등록")
  @Order(1)
  void save() {
    Product product = new Product();
    product.setPname("컴퓨터");
    product.setQuantity(10L);
    product.setPrice(1000000L);
    Long pid = productDAO.save(product);
    this.productId = pid;
    Assertions.assertThat(pid).isGreaterThan(0L);
  }

  @Test
  @DisplayName("상품조회")
  @Order(2)
  void findByProductId() {
    Optional<Product> findedProduct = productDAO.findByProductId(this.productId);
    Product product = null;
    Assertions.assertThat(findedProduct.stream().count()).isEqualTo(1);
  }

  @Test
  @DisplayName("상품수정")
  @Order(3)
  void update() {
    Product product = new Product();
    product.setQuantity(20L);
    product.setPrice(900000L);
    int affedtedRow = productDAO.update(this.productId, product);
    Optional<Product> updatedProduct = productDAO.findByProductId(this.productId);

    Assertions.assertThat(affedtedRow).isEqualTo(1);
    Product findedProduct = updatedProduct.orElseThrow();
    Assertions.assertThat(findedProduct.getQuantity())
              .isEqualTo(20L);
    Assertions.assertThat(findedProduct.getPrice())
              .isEqualTo(900000L);
  }

  @Test
  @DisplayName("상품목록")
  @Order(4)
  void findAll() {
    List<Product> list = productDAO.findAll();
    list.stream().forEach(product->log.info("product={}",product));
    Assertions.assertThat(list.size()).isGreaterThan(0);
  }

  @Test
  @DisplayName("상품삭제")
  @Order(5)
  void deleteByProductId() {
    int affedtedRow = productDAO.deleteByProductId(productId);
    Assertions.assertThat(affedtedRow).isEqualTo(1);
  }

}