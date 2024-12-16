package com.example.hoteltest.dto;
import java.util.List;

import com.example.hoteltest.model.Cart;
import com.example.hoteltest.model.CartItem;
import com.example.hoteltest.model.OrderEntity;
import com.example.hoteltest.model.OrderItem;
import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;

    //that's why you provide DTO FIRST before showing the user
    private User userPlain;
    private UserDTO user;
    private RoomDTO room;
    private BookingsDto booking;
    private Product product;
    private Cart cart;
    private CartItem cartItem;
    private OrderEntity orderEntity;
    private OrderItem orderItem;
    private List<UserDTO> userList;
    private List<RoomDTO> roomList;
    private List<BookingsDto> bookingList;
    private CartItemResponseV2DTO cartItemResponseDTO;
    private List<OrderEntity> listOfOrderItems;
    private List<CartItem> listOfCartItems;
    private List<ProductDTO> listOfProduct;
    private OrderEntityDTO orderEntityDTO;
    private List<CartItemResponseV2DTO> listOfCartItemsDTO;  // Use DTOs instead of CartItem
    private ProductDTO productDTO;
    private StoreResponseDTO storeResponseDTO;
    private Long storeId;
    private List<OrderEntityDTO> listOfOrderEntityDTO;
    private ReviewDTO reviewDTO;
    
    
	public ReviewDTO getReviewDTO() {
		return reviewDTO;
	}

	public void setReviewDTO(ReviewDTO reviewDTO) {
		this.reviewDTO = reviewDTO;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}
	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public RoomDTO getRoom() {
		return room;
	}
	public void setRoom(RoomDTO room) {
		this.room = room;
	}
	public BookingsDto getBooking() {
		return booking;
	}
	public void setBooking(BookingsDto booking) {
		this.booking = booking;
	}
	public List<UserDTO> getUserList() {
		return userList;
	}
	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
	public List<RoomDTO> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<RoomDTO> roomList) {
		this.roomList = roomList;
	}
	public List<BookingsDto> getBookingList() {
		return bookingList;
	}
	public void setBookingList(List<BookingsDto> bookingList) {
		this.bookingList = bookingList;
	}

	public User getUserPlain() {
		return userPlain;
	}

	public void setUserPlain(User userPlain) {
		this.userPlain = userPlain;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public CartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}

	public OrderEntity getOrderEntity() {
		return orderEntity;
	}

	public void setOrderEntity(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public List<OrderEntity> getListOfOrderItems() {
		return listOfOrderItems;
	}

	public void setListOfOrderItems(List<OrderEntity> listOfOrderItems) {
		this.listOfOrderItems = listOfOrderItems;
	}

	public List<CartItem> getListOfCartItems() {
		return listOfCartItems;
	}

	public void setListOfCartItems(List<CartItem> listOfCartItems) {
		this.listOfCartItems = listOfCartItems;
	}

	public List<ProductDTO> getListOfProduct() {
		return listOfProduct;
	}

	public void setListOfProduct(List<ProductDTO> listOfProduct) {
		this.listOfProduct = listOfProduct;
	}

	public CartItemResponseV2DTO getCartItemResponseDTO() {
		return cartItemResponseDTO;
	}

	public void setCartItemResponseDTO(CartItemResponseV2DTO cartItemResponseDTO) {
		this.cartItemResponseDTO = cartItemResponseDTO;
	}

	public List<CartItemResponseV2DTO> getListOfCartItemsDTO() {
		return listOfCartItemsDTO;
	}

	public void setListOfCartItemsDTO(List<CartItemResponseV2DTO> listOfCartItemsDTO) {
		this.listOfCartItemsDTO = listOfCartItemsDTO;
	}

	public OrderEntityDTO getOrderEntityDTO() {
		return orderEntityDTO;
	}

	public void setOrderEntityDTO(OrderEntityDTO orderEntityDTO) {
		this.orderEntityDTO = orderEntityDTO;
	}

	public List<OrderEntityDTO> getListOfOrderEntityDTO() {
		return listOfOrderEntityDTO;
	}
	
	private String storeName;
	private Long storeIdLong;
	private Integer storeBlock;
	private Integer storeLot;
	private Integer storePhoneNumber;
	
    private String paymentMethod; //gcash or cash
    private String pickupOrDeliver; //pickup or delivery

    private boolean isGcashAvailableOnStore;
    private boolean isDeliveryAvailableOnStore;

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPickupOrDeliver() {
		return pickupOrDeliver;
	}

	public void setPickupOrDeliver(String pickupOrDeliver) {
		this.pickupOrDeliver = pickupOrDeliver;
	}

	public void setListOfOrderEntityDTO(List<OrderEntityDTO> listOfOrderEntityDTO) {
		this.listOfOrderEntityDTO = listOfOrderEntityDTO;
	}

	public StoreResponseDTO getStoreResponseDTO() {
		return storeResponseDTO;
	}

	public void setStoreResponseDTO(StoreResponseDTO storeResponseDTO) {
		this.storeResponseDTO = storeResponseDTO;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getStoreIdLong() {
		return storeIdLong;
	}

	public void setStoreIdLong(Long storeIdLong) {
		this.storeIdLong = storeIdLong;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Integer getStoreBlock() {
		return storeBlock;
	}

	public void setStoreBlock(Integer storeBlock) {
		this.storeBlock = storeBlock;
	}

	public Integer getStoreLot() {
		return storeLot;
	}

	public void setStoreLot(Integer storeLot) {
		this.storeLot = storeLot;
	}

	public Integer getStorePhoneNumber() {
		return storePhoneNumber;
	}

	public void setStorePhoneNumber(Integer storePhoneNumber) {
		this.storePhoneNumber = storePhoneNumber;
	}

	public boolean isGcashAvailableOnStore() {
		return isGcashAvailableOnStore;
	}

	public void setGcashAvailableOnStore(boolean isGcashAvailableOnStore) {
		this.isGcashAvailableOnStore = isGcashAvailableOnStore;
	}

	public boolean isDeliveryAvailableOnStore() {
		return isDeliveryAvailableOnStore;
	}

	public void setDeliveryAvailableOnStore(boolean isDeliveryAvailableOnStore) {
		this.isDeliveryAvailableOnStore = isDeliveryAvailableOnStore;
	}

	

	
    

}