#### 订单小票数据表结构

```sql
create table MAG_CUSTOMER_BGY_0087
(
  transno               varchar(30) not null comment '小票号',
  transitemno           decimal(18) default 0 not null comment '小票序号',
  itemcode              varchar(30) comment '商品编号',
  itemname              varchar(100) comment '商品名称',
  itemweight            decimal(22,10) default 0.00 not null comment '商品重量',
  saleunitcode          varchar(6) comment '计量单位',
  saleunitname          varchar(200) comment '计量单位名称',
  taxrate               decimal(22,10) default 0.00 not null comment '税率',
  intaxprice            decimal(22,10) default 0.00 not null comment '进价',
  notaxprice            decimal(22,10) default 0.00 not null comment '无税单价',
  outprice              decimal(38,10) default 0.00 not null comment '售价',
  quantity              decimal(38,10) default 0.00 not null comment '销售数量',
  discount              decimal(22,10) default 0.00 not null comment '折扣率',
  saleamt               decimal(38,10) default 0.00 not null comment '销售金额',
  discountamt           decimal(22,10) default 0.00 not null comment '折扣金额',
  usercode              varchar(15) comment '操作用户',
  userdesc              varchar(60) comment '操作用户名称',
  functioncode          decimal(18) default 0 not null comment 'SYSLIST:RmgPayment  功能号 1	单品
11	预售
51	合计
21	人民币支付
22	微信支付
23	预售提货支付
24	银行卡支付(Z支付)
25	主钱包/零钱包/果币支付
26	支付宝支付
27	线上百果园支付
28	异常支付
29	信用卡支付
30	找零
31	优惠支付
32	线上支付
36	网上支付
37	好吃卡支付
38	月结支付

48	总清
98	签到
99	删除单品
97	挂账

8	活动赠送
9	外卖订单',
  transdate             CHAR(10) comment '销售日期',
  transtime             CHAR(10) comment '销售时间',
  monitor               varchar(60) comment '销售好吃卡时，记录卡号。',
  machinecode           varchar(15) comment 'POS机编号',
  itemtypecode          varchar(15) comment '商品类别',
  storecostcode         varchar(15) comment '专柜代码  三方平台销售时，记录对应的appid',
  entrydate             varchar(30) comment '生成日期',
  customerbalancedate   CHAR(10) comment '',
  enterprisebalancedate CHAR(10) comment '',
  cardcode              varchar(30) comment '会员编号',
  employeecode          varchar(15) comment '营业员编号',
  employeename          varchar(60) comment '营业员名称,目前记录会员的手机号码',
  prmschemecode         varchar(15) comment '促销编号',
  prmlevelcode          varchar(15) comment '促销等级',
  profit                decimal(22,10) comment '毛利',
  plusscore             decimal(22,10) comment '积分',
  produceno             varchar(30) comment '批次号（目前整单退时，记录原小票的编号）',
  lotno                 varchar(30) comment '批号',
  seqno                 decimal(38) comment '',
  eorderno              varchar(30) comment '电商订单编号',
  o2oorderid            varchar(60) comment '三方平台订单号',
  barcode               varchar(30) comment '条码',
  couponamt             decimal(22,10) comment '活动送果币时，当前商品需要分担金额（目前都没有值）',
  itemcouponamt         decimal(22,10) comment '单品买赠时，当前商品需要分担金额（目前都没有值）',
  memdiscountamt        decimal(22,10) comment '会员优惠金额',
  retailprice           decimal(22,10) comment '原价',
  saletype              decimal(4) comment '班次',
  merchantdiscount      decimal(22,10) comment '商家优惠金额',
  platformdiscount      decimal(22,10) comment '平台优惠金额',
  memberid              decimal(18) comment '会员ID ',
  memprice              decimal(22,10) comment '会员价',
  sysdatetime           varchar(30) comment '',
  transstatus           decimal(4) comment '',
  returnamt             decimal(22,10) comment '',
  returnqty             decimal(22,10) comment ''
);

```