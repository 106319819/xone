package com.gosun.birip.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util
{

	public static Pageable parse(String content) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		Pageable pageable = null;
		JsonNode root = mapper.readTree(content);
		int page = root.get("page").asInt() -1 ;
		int size = root.get("size").asInt();
		if(root.has("sort")){
			List<Order> orders = new ArrayList<Order>();
			JsonNode sort = root.get("sort");
			for(int i=0; i< sort.size(); i++){
				String direction = sort.get(i).get("direction").asText();
				String property = sort.get(i).get("property").asText();
				
				orders.add(new Order(Direction.valueOf(direction),property));
			}			
			pageable = PageRequest.of(page, size, Sort.by(orders));
		}else{
			pageable = PageRequest.of(page, size);
		}
		return pageable;
	}
}
