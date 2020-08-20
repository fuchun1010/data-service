package com.tank.dto;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author tank198435163.com
 */
@Getter
@Setter
public class PagodaOrderDto {

  /**
   * posDeviceNo : -
   * orgCode : 0087
   * pickUpCode : 核销码
   * brand : 10
   * saleModel : 1
   * orderModel : 1
   * orderType : 90
   * orderChannel : 5
   * dispatchType : 30
   * tickType : 0
   * submittedTime : 2020-07-22 09:12:13
   * member : {"memberCode":"20200410000002","mobile":"123123123","memberId":12}
   * payment : {"status":"UN_PAYMENT","details":[{"type":1,"value":100,"tradeNo":""}]}
   * receiver : {"name":"demoData","gender":1,"mobile":"demoData","secondPhone":"demoData","address":"demoData","addressNote":"demoData","orderNote":"demoData","province":"demoData","city":"demoData","hometown":"demoData","street":"demoData","longitude":"demoData","latitude":"demoData"}
   * items : [{"goodsId":52228,"name":"招牌- 甘美西瓜（中）带皮3.2-3.7kg","salesPrice":4450,"weight":0,"unitPrice":1000,"quantity":1,"itemSnapshotId":"","goodsSn":"","orgCode":"","channelId":"","goodsTradeType":"","goodsType":"","serviceList":[{"goodsId":52228,"name":"招牌- 甘美西瓜（中）带皮3.2-3.7kg","salesPrice":4450,"weight":0,"unitPrice":1000,"quantity":1,"itemSnapshotId":"","goodsSn":"","orgCode":"","channelId":"","goodsTradeType":"","goodsType":""}]}]
   * invoice : {"status":"","type":0,"titleType":"","title":"","identifyNo":"","email":"","money":"","note":""}
   * activity : [{"activityCode":"123","type":"1","goodsList":["1"]}]
   * groupActivity : {"groupModel":"","activityCode":"","groupCount":7,"openTime":"","endTime":""}
   * A :
   * preTradeInfo : {"tradeChannel":1111111,"uuid":"59038EAA354D4B0096CBAA7C5300C68D","tradeType":"C","tradeScene":"O","memberIdentifyType":"a","merchantId":"32","payAmount":500,"blackCheckInfo":{"clientIp":"127.0.0.1","deviceNo":"4587652132587452","longitude":"","latitude":""},"assetInfo":{"integralDeduction":500,"couponList":[{"discountAmount":10,"couponId":"10"}]},"costList":[{"costCode":"P","costAmount":10}],"extensionInfo":""}
   * uploadTradeInfo : {"tradeType":"","bizOrderNo":"","channelNum":"","consoleNum":"","merchantId":"","brand":"","activity":[{"activityCode ":"","activityName":"","discountAmount":"","sendDetail":[{"goodsType":"","goodsCode":"","count":"","unitDescribe":""}],"saleDetail":[{"goodsCode":"","count":""}]}],"payDetail":[{"payChannel":"","payWay ":"","payAmount ":"","payPreferentialAmount":"","paySerialNo":"","payAccount":"","gatheringAccount":"","consoleNum":"","payTime":"","notifyUrl":"","attach":"","saleDetail":[{"goodsCode":"","count":""}]}],"costDetail":[{"costCode":"","costName":"","costAmount":""}]}
   */

  private String posDeviceNo;
  private String orgCode;
  private String pickUpCode;
  private int brand;
  private int saleModel;
  private int orderModel;
  private int orderType;
  private int orderChannel;
  private int dispatchType;
  private int tickType;
  private String submittedTime;
  private MemberBean member;
  private PaymentBean payment;
  private ReceiverBean receiver;
  private InvoiceBean invoice;
  private GroupActivityBean groupActivity;
  private String A;
  private PreTradeInfoBean preTradeInfo;
  private UploadTradeInfoBean uploadTradeInfo;
  private List<ItemsBean> items = Lists.newArrayList();
  private List<ActivityBeanX> activity = Lists.newArrayList();


  @Getter
  @Setter
  public static class MemberBean {
    /**
     * memberCode : 20200410000002
     * mobile : 123123123
     * memberId : 12
     */

    private String memberCode;
    private String mobile;
    private int memberId;
  }

  @Getter
  @Setter
  public static class PaymentBean {
    /**
     * status : UN_PAYMENT
     * details : [{"type":1,"value":100,"tradeNo":""}]
     */

    private String status;
    private List<DetailsBean> details;


    @Getter
    @Setter
    public static class DetailsBean {
      /**
       * type : 1
       * value : 100
       * tradeNo :
       */

      private int type;
      private int value;
      private String tradeNo;


    }
  }

  @Getter
  @Setter
  public static class ReceiverBean {
    /**
     * name : demoData
     * gender : 1
     * mobile : demoData
     * secondPhone : demoData
     * address : demoData
     * addressNote : demoData
     * orderNote : demoData
     * province : demoData
     * city : demoData
     * hometown : demoData
     * street : demoData
     * longitude : demoData
     * latitude : demoData
     */

    private String name;
    private int gender;
    private String mobile;
    private String secondPhone;
    private String address;
    private String addressNote;
    private String orderNote;
    private String province;
    private String city;
    private String hometown;
    private String street;
    private String longitude;
    private String latitude;


  }

  @Getter
  @Setter
  public static class InvoiceBean {
    /**
     * status :
     * type : 0
     * titleType :
     * title :
     * identifyNo :
     * email :
     * money :
     * note :
     */

    private String status;
    private int type;
    private String titleType;
    private String title;
    private String identifyNo;
    private String email;
    private String money;
    private String note;
  }

  @Getter
  @Setter
  public static class GroupActivityBean {
    /**
     * groupModel :
     * activityCode :
     * groupCount : 7
     * openTime :
     * endTime :
     */

    private String groupModel;
    private String activityCode;
    private int groupCount;
    private String openTime;
    private String endTime;

  }

  @Setter
  @Getter
  public static class PreTradeInfoBean {
    /**
     * tradeChannel : 1111111
     * uuid : 59038EAA354D4B0096CBAA7C5300C68D
     * tradeType : C
     * tradeScene : O
     * memberIdentifyType : a
     * merchantId : 32
     * payAmount : 500
     * blackCheckInfo : {"clientIp":"127.0.0.1","deviceNo":"4587652132587452","longitude":"","latitude":""}
     * assetInfo : {"integralDeduction":500,"couponList":[{"discountAmount":10,"couponId":"10"}]}
     * costList : [{"costCode":"P","costAmount":10}]
     * extensionInfo :
     */

    private int tradeChannel;
    private String uuid;
    private String tradeType;
    private String tradeScene;
    private String memberIdentifyType;
    private String merchantId;
    private int payAmount;
    private BlackCheckInfoBean blackCheckInfo;
    private AssetInfoBean assetInfo;
    private String extensionInfo;
    private final List<CostListBean> costList = Lists.newArrayList();


    @Getter
    @Setter
    public static class BlackCheckInfoBean {
      /**
       * clientIp : 127.0.0.1
       * deviceNo : 4587652132587452
       * longitude :
       * latitude :
       */

      private String clientIp;
      private String deviceNo;
      private String longitude;
      private String latitude;

    }

    @Getter
    @Setter
    public static class AssetInfoBean {
      /**
       * integralDeduction : 500
       * couponList : [{"discountAmount":10,"couponId":"10"}]
       */

      private int integralDeduction;
      private List<CouponListBean> couponList;


      @Getter
      @Setter
      public static class CouponListBean {
        /**
         * discountAmount : 10
         * couponId : 10
         */
        private int discountAmount;
        private String couponId;
      }
    }

    @Getter
    @Setter
    public static class CostListBean {
      /**
       * costCode : P
       * costAmount : 10
       */

      private String costCode;
      private int costAmount;


    }
  }

  @Getter
  @Setter
  public static class UploadTradeInfoBean {
    /**
     * tradeType :
     * bizOrderNo :
     * channelNum :
     * consoleNum :
     * merchantId :
     * brand :
     * activity : [{"activityCode ":"","activityName":"","discountAmount":"","sendDetail":[{"goodsType":"","goodsCode":"","count":"","unitDescribe":""}],"saleDetail":[{"goodsCode":"","count":""}]}]
     * payDetail : [{"payChannel":"","payWay ":"","payAmount ":"","payPreferentialAmount":"","paySerialNo":"","payAccount":"","gatheringAccount":"","consoleNum":"","payTime":"","notifyUrl":"","attach":"","saleDetail":[{"goodsCode":"","count":""}]}]
     * costDetail : [{"costCode":"","costName":"","costAmount":""}]
     */

    private String tradeType;
    private String bizOrderNo;
    private String channelNum;
    private String consoleNum;
    private String merchantId;
    private String brand;
    private final List<ActivityBean> activity = Lists.newArrayList();
    private final List<PayDetailBean> payDetail = Lists.newArrayList();
    private final List<CostDetailBean> costDetail = Lists.newArrayList();


    public static class ActivityBean {
      /**
       * activityCode  :
       * activityName :
       * discountAmount :
       * sendDetail : [{"goodsType":"","goodsCode":"","count":"","unitDescribe":""}]
       * saleDetail : [{"goodsCode":"","count":""}]
       */

      private String activityCode;
      private String activityName;
      private String discountAmount;
      private final List<SendDetailBean> sendDetail = Lists.newArrayList();
      private final List<SaleDetailBean> saleDetail = Lists.newArrayList();


      @Getter
      @Setter
      public static class SendDetailBean {
        /**
         * goodsType :
         * goodsCode :
         * count :
         * unitDescribe :
         */

        private String goodsType;
        private String goodsCode;
        private String count;
        private String unitDescribe;

      }

      @Getter
      @Setter
      public static class SaleDetailBean {
        /**
         * goodsCode :
         * count :
         */

        private String goodsCode;
        private String count;


      }
    }

    @Getter
    @Setter
    public static class PayDetailBean {
      /**
       * payChannel :
       * payWay  :
       * payAmount  :
       * payPreferentialAmount :
       * paySerialNo :
       * payAccount :
       * gatheringAccount :
       * consoleNum :
       * payTime :
       * notifyUrl :
       * attach :
       * saleDetail : [{"goodsCode":"","count":""}]
       */

      private String payChannel;
      private String payWay;
      private String payAmount;
      private String payPreferentialAmount;
      private String paySerialNo;
      private String payAccount;
      private String gatheringAccount;
      private String consoleNum;
      private String payTime;
      private String notifyUrl;
      private String attach;
      private List<SaleDetailBeanX> saleDetail = Lists.newLinkedList();

      @Setter
      @Getter
      public static class SaleDetailBeanX {
        /**
         * goodsCode :
         * count :
         */
        private String goodsCode;
        private String count;
      }
    }

    @Setter
    @Getter
    public static class CostDetailBean {
      /**
       * costCode :
       * costName :
       * costAmount :
       */

      private String costCode;
      private String costName;
      private String costAmount;
    }
  }

  @Getter
  public static class ItemsBean {
    /**
     * goodsId : 52228
     * name : 招牌- 甘美西瓜（中）带皮3.2-3.7kg
     * salesPrice : 4450
     * weight : 0
     * unitPrice : 1000
     * quantity : 1
     * itemSnapshotId :
     * goodsSn :
     * orgCode :
     * channelId :
     * goodsTradeType :
     * goodsType :
     * serviceList : [{"goodsId":52228,"name":"招牌- 甘美西瓜（中）带皮3.2-3.7kg","salesPrice":4450,"weight":0,"unitPrice":1000,"quantity":1,"itemSnapshotId":"","goodsSn":"","orgCode":"","channelId":"","goodsTradeType":"","goodsType":""}]
     */

    private int goodsId;
    private String name;
    private int salesPrice;
    private int weight;
    private int unitPrice;
    private int quantity;
    private String itemSnapshotId;
    private String goodsSn;
    private String orgCode;
    private String channelId;
    private String goodsTradeType;
    private String goodsType;
    private final List<ServiceListBean> serviceList = Lists.newArrayList();

    @Getter
    @Setter
    public static class ServiceListBean {
      /**
       * goodsId : 52228
       * name : 招牌- 甘美西瓜（中）带皮3.2-3.7kg
       * salesPrice : 4450
       * weight : 0
       * unitPrice : 1000
       * quantity : 1
       * itemSnapshotId :
       * goodsSn :
       * orgCode :
       * channelId :
       * goodsTradeType :
       * goodsType :
       */

      private int goodsId;
      private String name;
      private int salesPrice;
      private int weight;
      private int unitPrice;
      private int quantity;
      private String itemSnapshotId;
      private String goodsSn;
      private String orgCode;
      private String channelId;
      private String goodsTradeType;
      private String goodsType;

    }
  }

  @Getter
  @Setter
  public static class ActivityBeanX {
    /**
     * activityCode : 123
     * type : 1
     * goodsList : ["1"]
     */

    private String activityCode;
    private String type;
    private final List<String> goodsList = Lists.newArrayList();
  }
}
