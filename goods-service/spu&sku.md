# spu 
表设计 

spu
- 商品 spu 

```sql

CREATE TABLE `tb_spu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'spu id',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `sub_title` varchar(255) DEFAULT '' COMMENT '子标题',
  `cid1` bigint(20) NOT NULL COMMENT '1级类目id',
  `cid2` bigint(20) NOT NULL COMMENT '2级类目id',
  `cid3` bigint(20) NOT NULL COMMENT '3级类目id',
  `brand_id` bigint(20) NOT NULL COMMENT '商品所属品牌id',
  `saleable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否上架，0下架，1上架',
  `valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，0已删除，1有效',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=utf8 COMMENT='spu表，该表描述的是一个抽象的商品，比如 iphone8';
```

- SPU的详情
```sql
CREATE TABLE `tb_spu_detail` (
  `spu_id` bigint(20) NOT NULL,
  `description` text COMMENT '商品描述信息',
  `specifications` varchar(3000) NOT NULL DEFAULT '' COMMENT '全部规格参数数据',
  `spec_template` varchar(1000) NOT NULL COMMENT '特有规格参数及可选值信息，json格式',
  `packing_list` varchar(1000) DEFAULT '' COMMENT '包装清单',
  `after_service` varchar(1000) DEFAULT '' COMMENT '售后服务',
  PRIMARY KEY (`spu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

商品类型
category 

品牌类型
brand



# sku 
表设计

```sql
CREATE TABLE `tb_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'sku id',
  `spu_id` bigint(20) NOT NULL COMMENT 'spu id',
  `title` varchar(255) NOT NULL COMMENT '商品标题',
  `images` varchar(1000) DEFAULT '' COMMENT '商品的图片，多个图片以‘,’分割',
  `price` bigint(15) NOT NULL DEFAULT '0' COMMENT '销售价格，单位为分',
  `indexes` varchar(100) COMMENT '特有规格属性在spu属性模板中的对应下标组合',
  `own_spec` varchar(1000) COMMENT 'sku的特有规格参数，json格式，反序列化时应使用linkedHashMap，保证有序',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，0无效，1有效',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `last_update_time` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `key_spu_id` (`spu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sku表,该表表示具体的商品实体,如黑色的64GB的iphone 8';
```

```sql
CREATE TABLE `tb_stock` (
  `sku_id` bigint(20) NOT NULL COMMENT '库存对应的商品sku id',
  `seckill_stock` int(9) DEFAULT '0' COMMENT '可秒杀库存',
  `seckill_total` int(9) DEFAULT '0' COMMENT '秒杀总数量',
  `stock` int(9) NOT NULL COMMENT '库存数量',
  PRIMARY KEY (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存表，代表库存，秒杀库存等信息';

```




==================================
分类表:
(商品分类编号, 分类名称, 父分类编号)
(1, 电子产品, 0)
(2, 手机, 1)

商品表:
(商品编号, 商品名称, 商品分类编号, 卖家编号, SPU销量, 评论数)
(1, 'iPhoneX', 2, 1, 0)

SKU表(库存表):
(SKU编号, 商品编号, SKU属性, 价格, 库存, SKU销量)
(1, 1, [1:1,2:3,3:5], 99, 400, 0) [1:1,2:3,3:5] 表示 "颜色为黑色,容量为16G，品牌为苹果"
(4, 1, [1:2,2:4,3:5], 99, 100, 0) [1:2,2:4,3:5] 表示 "颜色为白色,容量为64G，品牌为苹果"

属性名:
(属性名编号, 属性名, 商品分类编号, 父属性编号)
(1, 颜色, 2, 0)
(2, 内存, 2, 0)
(3, 品牌, 2, 0)

属性值:
(属性值编号, 属性值, 属性名编号)
(1, 黑色, 1)
(2, 白色, 1)
(3, 16G,  2)
(4, 64G, 2)
(5, 苹果, 3)
(6, 三星, 3)

商品和属性关系表:
(自增编号, 商品编号, 属性名编号, 属性值编号)
(1, 1, 1, 1) 商品1颜色为黑色
(2, 1, 1, 2) 商品1颜色为白色
(3, 1, 2, 3) 商品1尺码为16G
(4, 1, 2, 4) 商品1尺码为64G
(4, 1, 3, 5) 商品1品牌为苹果

商品和属性筛选表:
(商品编号, 商品具有的属性值编号)
(1, [1,2,3,4,5])

商品搜索表:
(商品编号, 商品标题和内容)