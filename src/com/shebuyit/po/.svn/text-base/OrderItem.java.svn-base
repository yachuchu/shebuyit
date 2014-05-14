package com.shebuyit.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
@Table(name="orderitem")
public class OrderItem implements Serializable {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static final long serialVersionUID = 0;

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public OrderItem() {
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Long id;
	
	private String sku;	
	
	private String orderNumber;
	
	private double price;
	
	private double original_price;
	
	private Integer quantity;	
		
	private String taobao_order_number;
	
	private double taobao_price;
	
	private double 	profit;
	
	private double 	profitRate;
	
	private Date  created_time;
		
	private String time_start;
	
	private String time_end;
	
	private Orders orders;
	
	
		
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public Long getId() {
		// begin-user-code
		return id;
		// end-user-code
	}

	/** 
	 * @param batch_ID the batch_ID to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setId(Long id) {
		// begin-user-code
		this.id = id;
		// end-user-code
	}

	@Column(length = 30)
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}


	@Column(length = 30)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}



	@Column(scale = 8)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(scale = 8)
	public double getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(double original_price) {
		this.original_price = original_price;
	}

	@Column(scale = 8)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(length = 50)
	public String getTaobao_order_number() {
		return taobao_order_number;
	}

	public void setTaobao_order_number(String taobao_order_number) {
		this.taobao_order_number = taobao_order_number;
	}

	@Column(scale = 8)
	public double getTaobao_price() {
		return taobao_price;
	}

	public void setTaobao_price(double taobao_price) {
		this.taobao_price = taobao_price;
	}
	
	
	@Column(scale = 8)
	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}
		
	@Column(scale = 8)
	public double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(double profitRate) {
		this.profitRate = profitRate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date  created_time) {
		this.created_time = created_time;
	}
	
	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "order_id")})
	public Orders getOrders() {
		return orders;
	}


	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
	


}
