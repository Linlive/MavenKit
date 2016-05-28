package com.tanl.kitserver.model.bean;

import java.io.Serializable;

/**
 * 购物车对象实例，便于数据库的存取
 * Created by Administrator on 2016/5/28.
 */
public class ShoppingCartDo implements Serializable {

	private String userId;
	private String shopKeeperId;

	private String goodsId;

	private int goodsChooseNumber;
	private float goodsPrice;
	private int goodsSize;
	private int goodsColor;
	private int goodsType;

	public String getUserId () {

		return userId;
	}

	public void setUserId (String userId) {

		this.userId = userId;
	}

	public String getShopKeeperId () {

		return shopKeeperId;
	}

	public void setShopKeeperId (String shopKeeperId) {

		this.shopKeeperId = shopKeeperId;
	}

	public String getGoodsId () {

		return goodsId;
	}

	public void setGoodsId (String goodsId) {

		this.goodsId = goodsId;
	}

	public int getGoodsChooseNumber () {

		return goodsChooseNumber;
	}

	public void setGoodsChooseNumber (int goodsChooseNumber) {

		this.goodsChooseNumber = goodsChooseNumber;
	}

	public float getGoodsPrice () {

		return goodsPrice;
	}

	public void setGoodsPrice (float goodsPrice) {

		this.goodsPrice = goodsPrice;
	}

	public int getGoodsSize () {

		return goodsSize;
	}

	public void setGoodsSize (int goodsSize) {

		this.goodsSize = goodsSize;
	}

	public int getGoodsColor () {

		return goodsColor;
	}

	public void setGoodsColor (int goodsColor) {

		this.goodsColor = goodsColor;
	}

	public int getGoodsType () {

		return goodsType;
	}

	public void setGoodsType (int goodsType) {

		this.goodsType = goodsType;
	}
}
