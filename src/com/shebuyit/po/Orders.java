package com.shebuyit.po;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
@Table(name="orders")
public class Orders implements Serializable {
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
	public Orders() {
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Long id;
	
	private String site;
	
	private String orderNumber;
	
	private String orderNumber4px;
	
	private double subtotal;
	
	private double discount;
	
	private double totalDue;
	
	private double shipping;

	private String shipChannel;
		
	private String destination;
	
	private String city;
	
	private String shipWeight;
	
	private double shipPrice;
	
	private Date  created_time;
	
	private String  shipTime_start;
	
	private String  shipTime_end;
	
	private String time_start;
	
	private String time_end;
	
	private Integer shipStatus;
	
	private double dollarRate;
	
	
	private List<OrderItem> order_items =  new ArrayList<OrderItem>();
	
	

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

		
	@Column(length = 50)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	@Column(length = 50)
	public String getOrderNumber4px() {
		return orderNumber4px;
	}

	public void setOrderNumber4px(String orderNumber4px) {
		this.orderNumber4px = orderNumber4px;
	}

	@Column(length = 30)
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@Column(scale = 8)
	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}	


	@Column(scale = 8)
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Column(scale = 8)
	public double getTotalDue() {
		return totalDue;
	}

	public void setTotalDue(double totalDue) {
		this.totalDue = totalDue;
	}

	@Column(scale = 8)
	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	@Column(length = 30)
	public String getShipChannel() {
		return shipChannel;
	}

	public void setShipChannel(String shipChannel) {
		this.shipChannel = shipChannel;
	}

	@Column(length = 30)
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	@Column(length = 30)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(length = 30)
	public String getShipWeight() {
		return shipWeight;
	}

	public void setShipWeight(String shipWeight) {
		this.shipWeight = shipWeight;
	}

	@Column(scale = 8)
	public double getShipPrice() {
		return shipPrice;
	}

	public void setShipPrice(double shipPrice) {
		this.shipPrice = shipPrice;
	}	
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date  created_time) {
		this.created_time = created_time;
	}

	@Column(length = 30)
	public String getShipTime_start() {
		return shipTime_start;
	}

	public void setShipTime_start(String shipTime_start) {
		this.shipTime_start = shipTime_start;
	}

	@Column(length = 30)
	public String getShipTime_end() {
		return shipTime_end;
	}

	public void setShipTime_end(String shipTime_end) {
		this.shipTime_end = shipTime_end;
	}

	@OneToMany(mappedBy = "orders", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public List<OrderItem> getOrder_items() {
		return order_items;
	}

	public void setOrder_items(List<OrderItem> order_items) {
		this.order_items = order_items;
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

	@Column(scale = 8)
	public Integer getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(Integer shipStatus) {
		this.shipStatus = shipStatus;
	}

	@Column(scale = 8)
	public double getDollarRate() {
		return dollarRate;
	}

	public void setDollarRate(double dollarRate) {
		this.dollarRate = dollarRate;
	}
	

}