package com.example.hoteltest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.StoreRequestDTO;
import com.example.hoteltest.dto.StoreResponseDTO;
import com.example.hoteltest.model.Store;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.StoreRepository;
import com.example.hoteltest.repository.UserRepository;

@Service
public class StoreService {
	 @Autowired
	 private StoreRepository storeRepository;

	 @Autowired
	 private UserRepository userRepository;
	 
	 
	 public Response createStore(User currentUser, StoreRequestDTO storeRequestDTO) {
		 Response response = new Response();
		 try {
			//is user able to craete store?
			 //do user has already store?
			
			 //if already have throw error
			 //	else create new Store
			 System.out.println(currentUser.getStore());
			 
			 if (!currentUser.getRole().equals("SELLER")) {
				 throw new RuntimeException("User is not authorized to create a store");
			 }
			 
			 if (currentUser.getStore() != null) {
				 throw new RuntimeException("User already owns a store");
			 }
			 
			
				 Store store = new Store();
			        store.setName(storeRequestDTO.getName());
			        store.setDescription(storeRequestDTO.getDescription());
			        store.setBlock(storeRequestDTO.getBlock());
			        store.setLot(storeRequestDTO.getLot());
			        store.setPhoneNumber(storeRequestDTO.getPhoneNumber());
			        store.setOpeningTime(storeRequestDTO.getOpeningTime());
			        store.setClosingTime(storeRequestDTO.getClosingTime());
			        store.setUser(currentUser); 
			        store.setGcashNumber(storeRequestDTO.getGcashNumber());
			        store.setGcash(storeRequestDTO.isGcash());
			        store.setDoDelivery(storeRequestDTO.isDoDelivery());
			        
			        currentUser.setStore(store);
			        
			        store = storeRepository.save(store);
			        StoreResponseDTO storeResponseDTO = new StoreResponseDTO(store);
			        response.setStoreResponseDTO(storeResponseDTO);
			        response.setStatusCode(200);
		            response.setMessage("Store created successfully");
		            
		            currentUser.setStoreId(store.getId());
		            
		            //save the currentUser to repositroy... to prevent NULL
		            userRepository.save(currentUser);
			        return response;
			 
			 
			
		 }catch (Exception e) {
	            response.setStatusCode(509); //509 user already own store
	            response.setMessage("Error during registration: " + e.getMessage());			}
			return response;
			}
	 
	 
	 public Response myStore(User user) {
		 Response response = new Response();
		 Store store = storeRepository.findById(user.getStore().getId())
				 .orElseThrow(() -> new RuntimeException("store not found"));
		 response.setStoreResponseDTO(new StoreResponseDTO(store));
		 return response;
	 }

		 
		 

}

