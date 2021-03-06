package com.tanl.kitserver.model.bean;

import java.util.List;

/**
 * 此类封装查询订单返回的订单简单实体。
 *
 * 订单有些信息用户不必要看到：订单号。
 * Created by Administrator on 2016/6/1.
 */
public class IndentViewDo {

	private String buyerId;
	private String shopkeeperId;

	private List<String> pictureUrls;
	private String goodsId;
	private String goodsName;
	private String goodsDesc;
	private String indentState;

	public String getBuyerId () {

		return buyerId;
	}

	public void setBuyerId (String buyerId) {

		this.buyerId = buyerId;
	}

	public List<String> getPictureUrls () {

		return pictureUrls;
	}

	public void setPictureUrls (List<String> pictureUrls) {

		this.pictureUrls = pictureUrls;
	}

	public String getGoodsId () {

		return goodsId;
	}

	public void setGoodsId (String goodsId) {

		this.goodsId = goodsId;
	}

	public String getGoodsName () {

		return goodsName;
	}

	public void setGoodsName (String goodsName) {

		this.goodsName = goodsName;
	}

	public String getGoodsDesc () {

		return goodsDesc;
	}

	public void setGoodsDesc (String goodsDesc) {

		this.goodsDesc = goodsDesc;
	}

	public String getIndentState () {

		return indentState;
	}

	public void setIndentState (String indentState) {

		this.indentState = indentState;
	}

	public String getShopkeeperId () {

		return shopkeeperId;
	}

	public void setShopkeeperId (String shopkeeperId) {

		this.shopkeeperId = shopkeeperId;
	}
}
